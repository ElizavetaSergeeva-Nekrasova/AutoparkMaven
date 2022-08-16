package Autopark.Service;

import Autopark.Entity.Vehicles;

import java.util.List;

public class VehicleGarage {
    public static void pullIntoAndLeaveGarage(List<Vehicles> vehicleList) {
        pullIntoGarage(vehicleList);
        leaveGarage(vehicleList);
    }

    private static void pullIntoGarage(List<Vehicles> vehicleList) {
        vehicleList
                .stream()
                .forEach(vehicle -> System.out.println("Auto " + vehicle.getId() + " заехало в гараж"));
    }

    private static void leaveGarage(List<Vehicles> vehicleList) {
        vehicleList
                .stream()
                .sorted((v1, v2) -> Math.toIntExact(v2.getId() - v1.getId()))
                .forEach(vehicle -> System.out.println("Auto " + vehicle.getId() + " выехало из гаража"));
    }
}