package Service;

import Vehicle.Vehicle;

import java.util.List;

public class VehicleGarage {
    public static void pullIntoAndLeaveGarage(List<Vehicle> vehicleList) {
        pullIntoGarage(vehicleList);
        leaveGarage(vehicleList);
    }

    private static void pullIntoGarage(List<Vehicle> vehicleList) {
        vehicleList
                .stream()
                .forEach(vehicle -> System.out.println("Auto " + vehicle.getId() + " заехало в гараж"));
    }

    private static void leaveGarage(List<Vehicle> vehicleList) {
        vehicleList
                .stream()
                .sorted((v1, v2) -> v2.getId() - v1.getId())
                .forEach(vehicle -> System.out.println("Auto " + vehicle.getId() + " выехало из гаража"));
    }
}