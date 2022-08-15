package Autopark.Engine;

public class DieselEngine extends CombustionEngine{
    private static final String ENGINE_TYPE = "Diesel";

    public DieselEngine(double engineCapacity, double fuelConsumptionPer100, double fuelTankCapacity) {
        super(ENGINE_TYPE, 1.2, engineCapacity, fuelConsumptionPer100, fuelTankCapacity);
    }
}