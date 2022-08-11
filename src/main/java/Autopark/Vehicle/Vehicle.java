package Autopark.Vehicle;

import Autopark.Exceptions.NotVehicleException;
import Autopark.Rent.Rent;
import Autopark.Service.TechnicalSpecialist;
import Autopark.Vehicle.Engine.Startable;
import Autopark.VehicleCollections.VehicleCollection;

import java.util.List;
import java.util.Objects;

public class Vehicle implements Comparable<Vehicle> {
    private List<Rent> rents;
    private int id;
    private VehicleType vehicleType;
    private String model;
    private String stateNumber;
    private double weight;
    private int year;
    private int mileage;
    private Color color;
    private Startable engine;

    public Vehicle() {
    }

    public Vehicle(int id, List<Rent> rents, VehicleType vehicleType, String model, String stateNumber,
                   double weight, int year, int mileage, Color color, Startable engine) {
        this.rents = rents;
        this.id = id;
        this.engine = engine;
        try {
            if (TechnicalSpecialist.validateVehicleType(vehicleType)) {
                this.vehicleType = vehicleType;
            } else {
                throw new NotVehicleException("Wrong vehicle type: " + vehicleType);
            }
            if (TechnicalSpecialist.validateModelName(model)) {
                this.model = model;
            } else {
                throw new NotVehicleException("Wrong model" + model);
            }
            if (TechnicalSpecialist.validateRegistrationNumber(stateNumber)) {
                this.stateNumber = stateNumber;
            } else {
                throw new NotVehicleException("Wrong state number: " + stateNumber);
            }
            if (TechnicalSpecialist.validateWeight(weight)) {
                this.weight = weight;
            } else {
                throw new NotVehicleException("Wrong weight: " + weight);
            }
            if (TechnicalSpecialist.validateManufactureYear(year)) {
                this.year = year;
            } else {
                throw  new NotVehicleException("Wrong year: " + year);
            }
            if (TechnicalSpecialist.validateMileage(mileage)) {
                this.mileage = mileage;
            } else {
                throw new NotVehicleException("Wrong mileage: " + mileage);
            }
            if (TechnicalSpecialist.validateColor(color)) {
                this.color = color;
            } else {
                throw new NotVehicleException("Wrong color" + color);
            }

        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        try {
            if (TechnicalSpecialist.validateVehicleType(vehicleType)) {
                this.vehicleType = vehicleType;
            } else {
                throw new NotVehicleException("Wrong vehicle type: " + vehicleType);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        try {
            if (TechnicalSpecialist.validateModelName(model)) {
                this.model = model;
            } else {
                throw new NotVehicleException("Wrong model" + model);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public String getStateNumber() {
        return stateNumber;
    }

    public void setStateNumber(String stateNumber) {
        try {
            if (TechnicalSpecialist.validateRegistrationNumber(stateNumber)) {
                this.stateNumber = stateNumber;
            } else {
                throw new NotVehicleException("Wrong state number: " + stateNumber);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        try {
            if (TechnicalSpecialist.validateWeight(weight)) {
                this.weight = weight;
            } else {
                throw new NotVehicleException("Wrong weight: " + weight);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        try {
            if (TechnicalSpecialist.validateManufactureYear(year)) {
                this.year = year;
            } else {
                throw  new NotVehicleException("Wrong year: " + year);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        try {
            if (TechnicalSpecialist.validateMileage(mileage)) {
                this.mileage = mileage;
            } else {
                throw new NotVehicleException("Wrong mileage: " + mileage);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        try {
            if (TechnicalSpecialist.validateColor(color)) {
                this.color = color;
            } else {
                throw new NotVehicleException("Wrong color" + color);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public Startable getEngine() {
        return engine;
    }

    public void setEngine(Startable engine) {
        this.engine = engine;
    }

    public double getCalcTaxPerMonth() {
        return (this.weight * 0.0013) + (this.vehicleType.getTaxCoefficient() * this.engine.getTaxPerMonth() * 30) + 5;
    }

    public double getTotalIncome(VehicleCollection vehicleCollection) {
        double sum = 0;

        for (int i = 0; i < rents.size(); i++) {
            sum += rents.get(i).getCost();
        }

        return sum;
    }

    public double getTotalProfit(VehicleCollection vehicleCollection) {
        return getTotalIncome(vehicleCollection) - getCalcTaxPerMonth();
    }

    @Override
    public String toString() {
        return vehicleType.getString() + ", "
                + model + ", "
                + stateNumber + ", "
                + weight + ", "
                + year + ", "
                + mileage + ", "
                + color + ", "
                + this.getCalcTaxPerMonth() + ", "
                + engine.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(vehicleType, vehicle.vehicleType) && Objects.equals(model, vehicle.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleType, model);
    }

    @Override
    public int compareTo(Vehicle o) {
        if (this.year != o.year) {
            return this.year - o.year;
        } else {
            return this.mileage - o.mileage;
        }
    }
}