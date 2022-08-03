package Main;

import Service.MechanicService;
import Service.VehicleGarage;
import Service.VehicleWash;
import Vehicle.Vehicle;
import VehicleCollections.VehicleCollection;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        VehicleCollection vehicleCollection = new VehicleCollection("types", "vehicles", "rents");
        List<Vehicle> vehicleList = vehicleCollection.getVehicleList();
        MechanicService mechanicService = new MechanicService();

        List<Vehicle> brokenVehicleList = mechanicService.getBrokenVehicles(vehicleList);
        showVehicles(brokenVehicleList);

        List<Vehicle> sortedBrokenVehicleList = MechanicService.sortByNumberOfBrokenDetails(brokenVehicleList);
        showVehicles(sortedBrokenVehicleList);

        showVolkswagenVehicles(vehicleList);
        showNewestVolkswagen(vehicleList);

        VehicleWash.washVehicles(vehicleList);
        VehicleGarage.pullIntoAndLeaveGarage(vehicleList);

        repairVehicleList(vehicleList, mechanicService);
    }

    private static void showVehicles(List<Vehicle> vehicleList) {
        vehicleList.forEach(System.out::println);
    }

    private static void repairVehicleList(List<Vehicle> vehicleList, MechanicService mechanicService) {
        vehicleList
                .stream()
                .forEach(vehicle -> {
                    showTechnicalCondition(vehicle, mechanicService);
                });

        System.out.println("Теперь все транспортные средства исправны");
    }

    private static void showTechnicalCondition(Vehicle vehicle, MechanicService mechanicService) {
        if (!mechanicService.isBroken(vehicle)) {
            System.out.println("Auto " + vehicle.getId() + " исправно");
        } else {
            System.out.println("Auto " + vehicle.getId() + " неисправно, чиним...");
            mechanicService.repair(vehicle);
        }
    }

    private static void showVolkswagenVehicles(List<Vehicle> vehicleList) {
        findVolkswagenVehicles(vehicleList)
                .forEach(System.out::println);
    }

    private static void showNewestVolkswagen(List<Vehicle> vehicleList) {
        Vehicle newestVehicle = findVolkswagenVehicles(vehicleList)
                .max((v1, v2) -> v1.getYear() - v2.getYear()).get();

        System.out.println("The newest Volkswagen: " + newestVehicle);
    }

    private static Stream<Vehicle> findVolkswagenVehicles(List<Vehicle> vehicleList) {
        return vehicleList
                .stream()
                .filter(vehicle -> vehicle.getModel().matches("(.)*Volkswagen(.)*"));
    }
}