package Parser;

import FileUtils.FileUtils;
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
    private static final int NUMBER_OF_PARAMETERS_FOR_COMBUSTION_ENGINES = 12;
    public static List<Vehicle> loadVehicles(String vehiclesFile, List<VehicleType> vehicleTypeList) {
        List<String> list = FileUtils.readInfo(vehiclesFile);
        List<Vehicle> vehiclesList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            vehiclesList.add(createVehicle(list.get(i), vehicleTypeList));
        }

        return vehiclesList;
    }

    private static Vehicle createVehicle(String csvString, List<VehicleType> vehicleTypeList) {
        String[] fields = ParserStringFormatter.splitLineWithComma(csvString);

        Vehicle vehicle = new Vehicle(
                Integer.parseInt(fields[0]),
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

    private static Startable createEngine(String[] fields) {
        if (fields.length == NUMBER_OF_PARAMETERS_FOR_COMBUSTION_ENGINES) {
            if (fields[8].equals("Gasoline")) {
                return createGasolineEngine(fields);
            }

            return createDieselEngine(fields);
        }

        return createElectricalEngine(fields);
    }

    private static GasolineEngine createGasolineEngine(String[] fields) {
        return new GasolineEngine(
                Double.parseDouble(fields[9]),
                Double.parseDouble(fields[10]),
                Double.parseDouble(fields[11])
        );
    }

    private static DieselEngine createDieselEngine(String[] fields) {
        return new DieselEngine(
                Double.parseDouble(fields[9]),
                Double.parseDouble(fields[10]),
                Double.parseDouble(fields[11])
        );
    }

    private static ElectricalEngine createElectricalEngine(String[] fields) {
        return new ElectricalEngine(
                Double.parseDouble(fields[9]),
                Double.parseDouble(fields[10])
        );
    }

    private static VehicleType getVehicleTypeById(int id, List<VehicleType> vehicleTypeList) {
        return vehicleTypeList.get(id - 1);
    }
}