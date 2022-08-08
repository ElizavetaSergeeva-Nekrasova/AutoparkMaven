package Autopark.Vehicle.Engine;

import Autopark.Exceptions.NotVehicleException;
import Autopark.Service.TechnicalSpecialist;

public class ElectricalEngine extends AbstractEngine{
    private double batteryCharge;
    private double electricityConsumptionPerKilometer;

    public ElectricalEngine(double batteryCharge, double electricityConsumptionPerKilometer) {
        super("Electrical", 0.1);
        try {
            if (TechnicalSpecialist.validateBatteryCharge(batteryCharge)) {
                this.batteryCharge = batteryCharge;
            } else {
                throw new NotVehicleException("Wrong battery charge: " + batteryCharge);
            }
            if (TechnicalSpecialist.validateElectricityConsumptionPerKilometer(electricityConsumptionPerKilometer)) {
                this.electricityConsumptionPerKilometer = electricityConsumptionPerKilometer;
            } else {
                throw new NotVehicleException("Wrong electricity consumption per kilometer: " + electricityConsumptionPerKilometer);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public double getBatteryCharge() {
        return batteryCharge;
    }

    public void setBatteryCharge(double batteryCharge) {
        this.batteryCharge = batteryCharge;
    }

    public double getElectricityConsumptionPerKilometer() {
        return electricityConsumptionPerKilometer;
    }

    public void setElectricityConsumptionPerKilometer(double electricityConsumptionPerKilometer) {
        this.electricityConsumptionPerKilometer = electricityConsumptionPerKilometer;
    }

    @Override
    public double getTaxPerMonth() {
        return getTaxByEngineType();
    }

    @Override
    public double getMaxKilometers() {
        return batteryCharge / electricityConsumptionPerKilometer;
    }
}