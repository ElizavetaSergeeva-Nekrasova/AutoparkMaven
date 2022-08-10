package Autopark.Service;

import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Vehicle.Color;
import Autopark.Vehicle.VehicleType;

public class TechnicalSpecialist {
    public static final int LOWER_LIMIT_MANUFACTURE_YEAR = 1886;

    public TechnicalSpecialist() {
    }

    static public boolean validateManufactureYear(int year) {
        return year >= LOWER_LIMIT_MANUFACTURE_YEAR
                && year < 9999;
    }

    static public boolean validateMileage(int mileage) {
        return mileage >= 0;
    }

    static public boolean validateWeight(double weight) {
        return weight >= 0;
    }

    static public boolean validateColor(Color color) {
        return color != null;
    }

    static public boolean validateVehicleType(VehicleType vehicleType) {
        return vehicleType != null
                && vehicleType.getTaxCoefficient() >= 0.0d
                && vehicleType.getTypeName() != null
                && !vehicleType.getTypeName().isEmpty();
    }

    static public boolean validateRegistrationNumber(String number) {
            String regex = "\\d{4}\\s{1}[A-Z]{2}(-)\\d{1}";

            return number != null && number.matches(regex);
    }

    static public boolean validateModelName(String name) {
        return name != null && !name.isEmpty();
    }

    static public boolean validateEngineCapacity(double engineCapacity) {return engineCapacity > 0;}

    static public boolean validateFuelConsumptionPer100(double fuelConsumptionPer100) {return fuelConsumptionPer100 > 0;}

    static public boolean validateFuelTankCapacity(double fuelTankCapacity) {return fuelTankCapacity > 0;}

    static public boolean validateBatteryCharge(double batteryCharge){return batteryCharge > 0;}

    static public boolean validateElectricityConsumptionPerKilometer(double electricityConsumptionPerKilometer){return electricityConsumptionPerKilometer > 0;}
}