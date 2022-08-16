package Autopark.Engine;

public class GasolineEngine extends CombustionEngine{
    private static final String ENGINE_TYPE = "Gasoline";

    public GasolineEngine(double engineCapacity, double fuelConsumptionPer100, double fuelTankCapacity) {
        super(ENGINE_TYPE, 1.1, engineCapacity, fuelConsumptionPer100, fuelTankCapacity);
    }
}