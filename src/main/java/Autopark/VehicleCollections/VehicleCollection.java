package Autopark.VehicleCollections;

import Autopark.Comparators.ComparatorByTax;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Parser.VehicleParser;
import Autopark.Vehicle.Vehicle;
import Autopark.Vehicle.VehicleType;

import java.util.*;

public class VehicleCollection {
    private List<VehicleType> vehicleTypeList;
    private List<Vehicle> vehicleList;

    @Autowired
    private VehicleParser vehicleParser;

    public VehicleCollection() {
    }

    @InitMethod
    public void init() {
        vehicleList = vehicleParser.loadVehicles();
        vehicleTypeList = vehicleParser.getVehicleTypeList();
    }

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setVehicleTypeList(List<VehicleType> vehicleTypeList) {
        this.vehicleTypeList = vehicleTypeList;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public VehicleParser getVehicleParser() {
        return vehicleParser;
    }

    public void setVehicleParser(VehicleParser vehicleParser) {
        this.vehicleParser = vehicleParser;
    }

    public void insert(int index, Vehicle v) {
        if(index >= vehicleList.size() || index < 0) {
            vehicleList.add(v);
        }
    }

    public int delete(int index) {
        if(index >= vehicleList.size() || index < 0) {
            return -1;
        }

        vehicleList.remove(index);

        return index;
    }

    public Vehicle getVehicleWithMaxTax() {
        return vehicleList
                .stream()
                .max(new ComparatorByTax())
                .get();
    }

    public double sumTotalProfit() {
        double sum = 0;

        for (int i = 0; i < vehicleList.size(); i++) {
            sum += vehicleList.get(i).getTotalProfit(this);
        }

        return sum;
    }

    public void sort(Comparator<Vehicle> comparator) {
        Collections.sort(vehicleList, comparator);
    }

    public void display() {
        String templateForHeader = "\n%3s%10s%15s%25s%25s%15s%15s%15s%15s%12s%15s";
        System.out.printf(templateForHeader, "Id", "Type", "ModelName", "Number",
                "Weight (kg)", "Year", "Mileage", "Color", "Income", "Tax", "Profit");

        String templateForLines = "%-7d%-10s%-29s%-20s%-22.2f%-12d%-15d%-15s%-15.2f%-15.2f%-15.2f";

        int i = 0;
        while (i < vehicleList.size()) {
            Vehicle v = vehicleList.get(i);
            System.out.println();
            System.out.format(templateForLines, v.getId(), v.getVehicleType().getTypeName(), v.getModel(),
                    v.getStateNumber(), v.getWeight(), v.getYear(), v.getMileage(),
                    v.getColor(), v.getTotalIncome(this), v.getCalcTaxPerMonth(), v.getTotalProfit(this));
            i++;
        }

        System.out.println();
        System.out.printf("%-160s%.2f", "Total", sumTotalProfit());
    }
}