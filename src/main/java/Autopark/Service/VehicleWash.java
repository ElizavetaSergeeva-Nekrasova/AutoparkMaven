package Autopark.Service;

import Autopark.Entity.Vehicles;

import java.util.List;

public class VehicleWash {
    public static void washVehicles(List<Vehicles> vehicleList) {
        vehicleList
                .stream()
                .forEach(vehicle -> System.out.println("Auto " + vehicle.getId() + " вымыто"));
    }
}