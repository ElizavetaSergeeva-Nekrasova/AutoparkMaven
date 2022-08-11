package Autopark.Service;

import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Vehicle.Vehicle;

import java.util.List;

public class Workroom {
    @Autowired
    private Fixer mechanic;

    public Workroom() {
    }

    public Fixer getMechanic() {
        return mechanic;
    }

    public void setMechanic(Fixer mechanic) {
        this.mechanic = mechanic;
    }

    public void checkAllVehicle(List<Vehicle> vehicles) {
        vehicles
                .stream()
                .forEach(vehicle -> {
                    showTechnicalCondition(vehicle);
                });

        System.out.println("Теперь все транспортные средства исправны");
    }

    private void showTechnicalCondition(Vehicle vehicle) {
        if (!mechanic.isBroken(vehicle)) {
            System.out.println("Auto " + vehicle.getId() + " исправно");
        } else {
            System.out.println("Auto " + vehicle.getId() + " неисправно, чиним...");
            mechanic.repair(vehicle);
        }
    }
}