package Autopark.Rent;

import java.util.Date;

public class Rent {
    private int vehicleId;
    private Date date;
    private double cost;

    public Rent(int vehicleId, Date date, double cost) {
        this.vehicleId = vehicleId;
        this.date = date;
        this.cost = cost;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Rent() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return vehicleId + ", " +
                date + ", " +
                cost;
    }
}