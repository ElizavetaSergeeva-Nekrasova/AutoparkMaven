package Comparators;

import Vehicle.Vehicle;

import java.util.Comparator;

public class ComparatorByTax implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle o1, Vehicle o2) {
        return Double.compare(o1.getCalcTaxPerMonth(), o2.getCalcTaxPerMonth());
    }
}