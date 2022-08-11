package Autopark.Service;

import Autopark.Vehicle.Vehicle;

import java.util.List;

public class VehicleWash {
    public static void washVehicles(List<Vehicle> vehicleList) {
        vehicleList
                .stream()
                .forEach(vehicle -> System.out.println("Auto " + vehicle.getId() + " вымыто"));
    }
}