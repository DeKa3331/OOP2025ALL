package carrent.repositories;

import carrent.models.Rental;
import carrent.models.User;
import carrent.models.Vehicle;

import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    List<Rental> findAll();
    Optional<Rental> findById(String id);
    List<Rental> findByVehicleId(String vehicleId);
    List<Rental> findByUserId(String userId);
    Rental save(Rental rental);
    void deleteById(String id);
    public Optional<Rental> findByVehicleIdAndReturnDateIsNull (String vehicleId);
}