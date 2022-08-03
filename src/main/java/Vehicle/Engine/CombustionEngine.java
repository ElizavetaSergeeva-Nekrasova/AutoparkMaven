package Vehicle.Engine;

import Exceptions.NotVehicleException;
import Service.TechnicalSpecialist;

public class CombustionEngine extends AbstractEngine{
    private double engineCapacity;
    private double fuelTankCapacity;
    private double fuelConsumptionPer100;

    public CombustionEngine(String typeName, double taxByEngineType, double engineCapacity, double fuelConsumptionPer100, double fuelTankCapacity) {
        super(typeName, taxByEngineType);
        try {
            if (TechnicalSpecialist.validateEngineCapacity(engineCapacity)) {
                this.engineCapacity = engineCapacity;
            } else {
                throw new NotVehicleException("Wrong engine capacity: " + engineCapacity);
            }
            if (TechnicalSpecialist.validateFuelTankCapacity(fuelTankCapacity)) {
                this.fuelTankCapacity = fuelTankCapacity;
            } else {
                throw new NotVehicleException("Wrong fuel tank capacity: " + fuelTankCapacity);
            }
            if (TechnicalSpecialist.validateFuelConsumptionPer100(fuelConsumptionPer100)) {
                this.fuelConsumptionPer100 = fuelConsumptionPer100;
            } else {
                throw new NotVehicleException("Wrong fuel consumption per 100:  " + fuelConsumptionPer100);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(double engineCapacity) {
        try {
            if (TechnicalSpecialist.validateEngineCapacity(engineCapacity)) {
                this.engineCapacity = engineCapacity;
            } else {
                throw new NotVehicleException("Wrong engine capacity: " + engineCapacity);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public double getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(double fuelTankCapacity) {
        try {
            if (TechnicalSpecialist.validateFuelTankCapacity(fuelTankCapacity)) {
                this.fuelTankCapacity = fuelTankCapacity;
            } else {
                throw new NotVehicleException("Wrong fuel tank capacity: " + fuelTankCapacity);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public double getFuelConsumptionPer100() {
        return fuelConsumptionPer100;
    }

    public void setFuelConsumptionPer100(double fuelConsumptionPer100) {
        try {
            if (TechnicalSpecialist.validateFuelConsumptionPer100(fuelConsumptionPer100)) {
                this.fuelConsumptionPer100 = fuelConsumptionPer100;
            } else {
                throw new NotVehicleException("Wrong fuel consumption per 100:  " + fuelConsumptionPer100);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getTaxPerMonth() {
        return getTaxByEngineType();
    }

    @Override
    public double getMaxKilometers() {
        return fuelTankCapacity * 100 / fuelConsumptionPer100;
    }
}