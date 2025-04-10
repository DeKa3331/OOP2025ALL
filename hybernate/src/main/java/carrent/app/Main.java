package carrent.app;

import carrent.models.Rental;
import carrent.models.User;
import carrent.models.Vehicle;
import carrent.repositories.RentalHibernateRepository;
import carrent.repositories.UserHibernateRepository;
import carrent.repositories.VehicleHibernateRepository;
import carrent.services.RentalHibernateService;
import carrent.services.RentalService;
import carrent.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        // Tworzymy repozytoria
        RentalHibernateRepository rentalRepo = new RentalHibernateRepository();
        VehicleHibernateRepository vehicleRepo = new VehicleHibernateRepository();
        UserHibernateRepository userRepo = new UserHibernateRepository();

        // Tworzymy serwis wypożyczeń
        RentalService rentalService = new RentalHibernateService(rentalRepo, vehicleRepo, userRepo);

        // Przykładowi użytkownik i pojazd
        User user = new User("3", "adminn", "pass", "ADMIN");
        Vehicle vehicle = Vehicle.builder()
                .id("v1")
                .price(100.0)
                .category("CAR")
                .brand("Toyota")
                .model("Corolla")
                .year(2020)
                .plate("ABC123")
                .build();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Rozpoczynamy transakcję
            Transaction tx = session.beginTransaction();

            // Ustawiamy sesje dla repozytoriów
            userRepo.setSession(session);
            vehicleRepo.setSession(session);

            // Zapisujemy użytkownika i pojazd w bazie danych
            System.out.println("Zapisuję użytkownika...");
            userRepo.save(user);
            System.out.println("Zapisuję pojazd...");
            vehicleRepo.save(vehicle);

            // Zatwierdzamy transakcję
            tx.commit();
            System.out.println("Dane zapisane w bazie!");
        } catch (Exception e) {
            System.err.println("Błąd podczas zapisu do bazy: " + e.getMessage());
            e.printStackTrace();
        }

        // Próba wypożyczenia pojazdu
        try {
            Rental rental = rentalService.rent("v1", "1");
            System.out.println("Wypożyczono: " + rental);
        } catch (Exception e) {
            System.err.println("Błąd podczas wypożyczania pojazdu: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
