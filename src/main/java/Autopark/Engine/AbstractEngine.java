package Autopark.Engine;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class AbstractEngine implements Startable{
    private String typeName;
    private double taxByEngineType;
    private double consumptionPerKilometer;
    private double tankCapacity;

    public AbstractEngine(String typeName, double taxByEngineType, double consumptionPerKilometer, double tankCapacity) {
        this.typeName = typeName;
        this.taxByEngineType = taxByEngineType;
        this.consumptionPerKilometer = consumptionPerKilometer;
        this.tankCapacity = tankCapacity;
    }

    @Override
    public double getTaxPerMonth() {
        return getTaxByEngineType();
    }

    @Override
    public double getMaxKilometers() {
        return tankCapacity / consumptionPerKilometer;
    }
}