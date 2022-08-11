package Autopark.Service;

import Autopark.Vehicle.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class BadMechanicService implements Fixer{
    public BadMechanicService() {
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        Map<String, Integer> map = new HashMap<>();
        return map;
    }

    @Override
    public void repair(Vehicle vehicle) {

    }

    @Override
    public boolean detectAndRepair(Vehicle vehicle) {
        return false;
    }

    @Override
    public boolean isBroken(Vehicle vehicle) {
        return false;
    }
}