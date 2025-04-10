package carrent.services;

import carrent.models.Rental;
import carrent.models.User;
import carrent.models.Vehicle;
import carrent.repositories.RentalHibernateRepository;
import carrent.repositories.UserHibernateRepository;
import carrent.repositories.VehicleHibernateRepository;
import carrent.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class RentalHibernateService implements RentalService {
    private final RentalHibernateRepository rentalRepo;
    private final VehicleHibernateRepository vehicleRepo;
    private final UserHibernateRepository userRepo;

    public RentalHibernateService(RentalHibernateRepository rentalRepo, VehicleHibernateRepository vehicleRepo,
                                  UserHibernateRepository userRepo) {
        this.rentalRepo = rentalRepo;
        this.vehicleRepo = vehicleRepo;
        this.userRepo = userRepo;
    }

    @Override
    public boolean isVehicleRented(String vehicleId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            rentalRepo.setSession(session);
            return rentalRepo.findByVehicleIdAndReturnDateIsNull(vehicleId).isPresent();
        }
    }

    @Override
    public Rental rent(String vehicleId, String userId) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            rentalRepo.setSession(session);
            vehicleRepo.setSession(session);
            userRepo.setSession(session);
            if (rentalRepo.findByVehicleIdAndReturnDateIsNull(vehicleId).isPresent()) {
                throw new IllegalStateException("Vehicle is rented");
            }
            Vehicle vehicle = vehicleRepo.findById(vehicleId)
                    .orElseThrow(() -> new RuntimeException("Vehicle not found"));
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Rental rental = Rental.builder()
                    .id(UUID.randomUUID().toString())
                    .vehicle(vehicle)
                    .user(user)
                    .rentDate(LocalDateTime.now())
                    .build();
            rentalRepo.save(rental);
            tx.commit();

            return rental;
        } catch (Exception e) {
            if (tx != null && tx.getStatus().canRollback()) {
                try {
                    tx.rollback();
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;
        }
    }

    @Override
    public boolean returnRental(String vehicleId, String userId) {
        return false;
    }

    @Override
    public List<Rental> findAll() {
        return null;
    }
}

