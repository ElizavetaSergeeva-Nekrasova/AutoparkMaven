package Autopark.Comparators;

import Autopark.Entity.Vehicles;

import java.util.Comparator;

public class ComparatorByTax implements Comparator<Vehicles> {
    @Override
    public int compare(Vehicles o1, Vehicles o2) {
        return Double.compare(o1.getCalcTaxPerMonth(), o2.getCalcTaxPerMonth());
    }
}