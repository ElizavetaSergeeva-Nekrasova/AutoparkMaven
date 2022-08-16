package Autopark.Service;

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

    static public boolean validateColor(String color) {
        return color != null;
    }

    static public boolean validateCoefTaxes(double coefTaxes) {
        return coefTaxes >= 0.0d;
    }

    static public boolean validateTypeName(String typeName) {
        return typeName != null && !typeName.isEmpty();
    }

    static public boolean validateRegistrationNumber(String number) {
            String regex = "\\d{4}\\s{1}[A-Z]{2}(-)\\d{1}";

            return number != null && number.matches(regex);
    }

    static public boolean validateModelName(String name) {
        return name != null && !name.isEmpty();
    }

    static public boolean validateCost(double cost) {
        return cost >= 0;
    }

    static public boolean validateEngineCapacity(double engineCapacity) {return engineCapacity > 0;}

    static public boolean validateConsumptionPerKilometer(double consumptionPerKilometer) {return consumptionPerKilometer > 0;}

    static public boolean validateTankCapacity(double tankCapacity) {return tankCapacity > 0;}
}