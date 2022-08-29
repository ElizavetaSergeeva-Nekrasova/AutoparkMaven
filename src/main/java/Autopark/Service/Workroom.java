package Autopark.Service;

import Autopark.Entity.Vehicles;
import Autopark.Infrastructure.core.annotations.Autowired;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Workroom {
    @Autowired
    private Fixer mechanic;

    public List<Long> getRepairedVehiclesId(List<Vehicles> vehicles) {
        List<Long> list = new ArrayList<>();

        vehicles
                .stream()
                .forEach(vehicle -> {
                    mechanic.detectBreaking(vehicle);
                    if (mechanic.isBroken(vehicle)) {
                        list.add(vehicle.getId());
                        mechanic.repair(vehicle);
                    }
                });

        return list;
    }

    public void checkAllVehicle(List<Vehicles> vehicles) {
        vehicles
                .stream()
                .forEach(vehicle -> {
                    mechanic.detectBreaking(vehicle);
                    showTechnicalCondition(vehicle);
                });

        System.out.println("Теперь все транспортные средства исправны");
    }

    private void showTechnicalCondition(Vehicles vehicles) {
        if (!mechanic.isBroken(vehicles)) {
            System.out.println("Auto " + vehicles.getId() + " исправно");
        } else {
            System.out.println("Auto " + vehicles.getId() + " неисправно, чиним...");
            mechanic.repair(vehicles);
        }
    }
}