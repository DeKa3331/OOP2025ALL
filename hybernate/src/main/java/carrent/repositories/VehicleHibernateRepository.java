package carrent.repositories;

import carrent.models.Vehicle;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class VehicleHibernateRepository implements VehicleRepository {
    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public List<Vehicle> findAll() {
        return session.createQuery("FROM Vehicle", Vehicle.class).list();
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    // Pozostałe metody
}

