package Comparators;

import Service.MechanicService;
import Vehicle.Vehicle;

import java.util.Comparator;

public class ComparatorByDefectCount implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        MechanicService mechanicService = new MechanicService();
        return Integer.compare(mechanicService.getSumNumberOfBreaks(o2), mechanicService.getSumNumberOfBreaks(o1));
    }
}