package Parser;

import FileUtils.FileUtils;
import Vehicle.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class VehicleTypeParser {
    public static List<VehicleType> loadTypes(String typesFile) {
        List<String> list = FileUtils.readInfo(typesFile);
        List<VehicleType> typesList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            typesList.add(createType(list.get(i)));
        }

        return typesList;
    }

    private static VehicleType createType(String csvString) {
        String[] fields = ParserStringFormatter.splitLineWithComma(csvString);

        VehicleType vehicleType = new VehicleType(
                Integer.parseInt(fields[0]),
                fields[1],
                Double.parseDouble(fields[2])
        );

        return vehicleType;
    }
}