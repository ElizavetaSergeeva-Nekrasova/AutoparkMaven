package Autopark.Comparators;

import Autopark.Entity.Vehicles;
import Autopark.Service.MechanicService;

import java.util.Comparator;

public class ComparatorByDefectCount implements Comparator<Vehicles> {
    @Override
    public int compare(Vehicles o1, Vehicles o2) {
        MechanicService mechanicService = new MechanicService();
        return Integer.compare(mechanicService.getSumNumberOfBreaks(o2), mechanicService.getSumNumberOfBreaks(o1));
    }
}