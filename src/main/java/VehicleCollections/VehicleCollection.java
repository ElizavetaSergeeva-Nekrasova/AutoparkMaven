package VehicleCollections;

import Comparators.ComparatorByTax;
import Parser.RentParser;
import Parser.VehicleParser;
import Parser.VehicleTypeParser;
import Rent.Rent;
import Vehicle.Vehicle;
import Vehicle.VehicleType;

import java.util.*;

public class VehicleCollection {
    private List<VehicleType> vehicleTypeList;
    private List<Vehicle> vehicleList;
    private List<Rent> rentList;

    public VehicleCollection(String types, String vehicles, String rents) {
        String typesFile = "src/main/resources/" + types + ".csv";
        String vehiclesFile = "src/main/resources/" + vehicles + ".csv";
        String rentsFile = "src/main/resources/" + rents + ".csv";

        vehicleTypeList = VehicleTypeParser.loadTypes(typesFile);
        rentList = RentParser.loadRents(rentsFile);
        vehicleList = VehicleParser.loadVehicles(vehiclesFile, vehicleTypeList);
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public List<Rent> getRentList() {
        return rentList;
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
                "Weight (kg)", "Year", "Mileage", "Vehicle.Color", "Income", "Tax", "Profit");

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