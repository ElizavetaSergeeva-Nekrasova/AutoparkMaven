package Autopark.Parser;

import Autopark.FileUtils.FileUtils;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Rent.Rent;
import Autopark.Service.TechnicalSpecialist;
import Autopark.Vehicle.Vehicle;
import Autopark.Vehicle.VehicleType;
import Autopark.Vehicle.Color;
import Autopark.Vehicle.Engine.Startable;
import Autopark.Vehicle.Engine.GasolineEngine;
import Autopark.Vehicle.Engine.DieselEngine;
import Autopark.Vehicle.Engine.ElectricalEngine;

import java.util.ArrayList;
import java.util.List;

public class VehicleParser {
    private VehicleTypeParser vehicleTypeParser;
    private RentParser rentParser;
    private List<VehicleType> vehicleTypeList;
    private List<Rent> rentList;
    private static final int NUMBER_OF_PARAMETERS_FOR_COMBUSTION_ENGINES = 12;
    private String vehiclesFile;

    @Autowired
    TechnicalSpecialist technicalSpecialist;

    public VehicleParser() {
    }

    @InitMethod
    public void init() {
        vehiclesFile = "src/main/resources/vehicles.csv";
        vehicleTypeParser = new VehicleTypeParser();
        rentParser = new RentParser();
        vehicleTypeList = vehicleTypeParser.loadTypes();
        rentList = rentParser.loadRents();
    }

    public VehicleTypeParser getVehicleTypeParser() {
        return vehicleTypeParser;
    }

    public void setVehicleTypeParser(VehicleTypeParser vehicleTypeParser) {
        this.vehicleTypeParser = vehicleTypeParser;
    }

    public RentParser getRentParser() {
        return rentParser;
    }

    public void setRentParser(RentParser rentParser) {
        this.rentParser = rentParser;
    }

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setVehicleTypeList(List<VehicleType> vehicleTypeList) {
        this.vehicleTypeList = vehicleTypeList;
    }

    public List<Rent> getRentList() {
        return rentList;
    }

    public void setRentList(List<Rent> rentList) {
        this.rentList = rentList;
    }

    public String getVehiclesFile() {
        return vehiclesFile;
    }

    public void setVehiclesFile(String vehiclesFile) {
        this.vehiclesFile = vehiclesFile;
    }

    public TechnicalSpecialist getTechnicalSpecialist() {
        return technicalSpecialist;
    }

    public void setTechnicalSpecialist(TechnicalSpecialist technicalSpecialist) {
        this.technicalSpecialist = technicalSpecialist;
    }

    public List<Vehicle> loadVehicles() {
        List<String> list = FileUtils.readInfo(vehiclesFile);
        List<Vehicle> vehiclesList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            vehiclesList.add(createVehicle(list.get(i), vehicleTypeList));
        }

        return vehiclesList;
    }

    private Vehicle createVehicle(String csvString, List<VehicleType> vehicleTypeList) {
        String[] fields = ParserStringFormatter.splitLineWithComma(csvString);

        Vehicle vehicle = new Vehicle(
                Integer.parseInt(fields[0]),
                getRentListForVehicle(Integer.parseInt(fields[0])),
                getVehicleTypeById(Integer.parseInt(fields[1]), vehicleTypeList),
                fields[2],
                fields[3],
                Double.parseDouble(fields[4]),
                Integer.parseInt(fields[5]),
                Integer.parseInt(fields[6]),
                Color.valueOf(fields[7]),
                createEngine(fields)
        );

        return vehicle;
    }

    private Startable createEngine(String[] fields) {
        if (fields.length == NUMBER_OF_PARAMETERS_FOR_COMBUSTION_ENGINES) {
            if (fields[8].equals("Gasoline")) {
                return createGasolineEngine(fields);
            }

            return createDieselEngine(fields);
        }

        return createElectricalEngine(fields);
    }

    private GasolineEngine createGasolineEngine(String[] fields) {
        return new GasolineEngine(
                Double.parseDouble(fields[9]),
                Double.parseDouble(fields[10]),
                Double.parseDouble(fields[11])
        );
    }

    private DieselEngine createDieselEngine(String[] fields) {
        return new DieselEngine(
                Double.parseDouble(fields[9]),
                Double.parseDouble(fields[10]),
                Double.parseDouble(fields[11])
        );
    }

    private ElectricalEngine createElectricalEngine(String[] fields) {
        return new ElectricalEngine(
                Double.parseDouble(fields[9]),
                Double.parseDouble(fields[10])
        );
    }

    private VehicleType getVehicleTypeById(int id, List<VehicleType> vehicleTypeList) {
        return vehicleTypeList.get(id - 1);
    }

    private List<Rent> getRentListForVehicle(int id) {
        List<Rent> rents = new ArrayList<>();

        for (int i = 0; i < rentList.size(); i++) {
            if (rentList.get(i).getVehicleId() == id) {
                rents.add(rentList.get(i));
            }
        }

        return rents;
    }
}