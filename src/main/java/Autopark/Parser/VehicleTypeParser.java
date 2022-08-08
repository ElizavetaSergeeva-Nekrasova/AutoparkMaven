package Autopark.Parser;

import Autopark.FileUtils.FileUtils;
import Autopark.Vehicle.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class VehicleTypeParser {
    private String typesFile = "src/main/resources/types.csv";

    public VehicleTypeParser() {
    }

//    @InitMethod
//    public void init() {
//        typesFile = "src/main/resources/types.csv";
//    }

    public List<VehicleType> loadTypes() {
        List<String> list = FileUtils.readInfo(typesFile);
        List<VehicleType> typesList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            typesList.add(createType(list.get(i)));
        }

        return typesList;
    }

    private VehicleType createType(String csvString) {
        String[] fields = ParserStringFormatter.splitLineWithComma(csvString);

        VehicleType vehicleType = new VehicleType(
                Integer.parseInt(fields[0]),
                fields[1],
                Double.parseDouble(fields[2])
        );

        return vehicleType;
    }
}