package Parser;

import FileUtils.FileUtils;
import Infrastructure.core.annotations.Autowired;
import Infrastructure.core.annotations.InitMethod;
import Rent.Rent;
import Service.TechnicalSpecialist;
import Vehicle.Vehicle;
import Vehicle.VehicleType;
import Vehicle.Color;
import Vehicle.Engine.Startable;
import Vehicle.Engine.GasolineEngine;
import Vehicle.Engine.DieselEngine;
import Vehicle.Engine.ElectricalEngine;

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

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
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