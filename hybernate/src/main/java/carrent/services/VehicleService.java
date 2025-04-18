package carrent.services;
import carrent.models.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<Vehicle> findAll();
    Optional<Vehicle> findById(String id);
    Vehicle save(Vehicle vehicle);
    List<Vehicle> findAvailableVehicles();
    boolean isAvailable(String vehicleId);
}
