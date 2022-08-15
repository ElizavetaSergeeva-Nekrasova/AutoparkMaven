package Autopark.Comparators;

import Autopark.Entity.Vehicles;

import java.util.Comparator;

public class ModelComparator implements Comparator<Vehicles> {
    @Override
    public int compare(Vehicles o1, Vehicles o2) {
        return o1.getModel().compareTo(o2.getModel());
    }
}