package Autopark.Rent;

import Autopark.Exceptions.DefectedVehicleException;
import Autopark.Service.MechanicService;
import Autopark.Vehicle.Vehicle;

import java.util.Date;

public class RentVehicle {
    public static Rent rentVehicle(Vehicle vehicle, double cost, MechanicService mechanicService)
            throws DefectedVehicleException {
        if (mechanicService.isBroken(vehicle)) {
            throw new DefectedVehicleException("Vehicle is defected");
        }
        return new Rent(vehicle.getId(), new Date(), cost);
    }
}