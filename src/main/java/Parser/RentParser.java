package Parser;

import FileUtils.FileUtils;
import Rent.Rent;

import java.util.ArrayList;
import java.util.List;

public class RentParser {
    public static List<Rent> loadRents(String rentsFile) {
        List<String> list = FileUtils.readInfo(rentsFile);
        List<Rent> rentsList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            rentsList.add(createRent(list.get(i)));
        }

        return rentsList;
    }

    private static Rent createRent(String csvString) {
        String[] fields = ParserStringFormatter.splitLineWithComma(csvString);

        Rent rent = new Rent(
                Integer.parseInt(fields[0]),
                ParserStringFormatter.formatStringToDate(fields[1]),
                Double.parseDouble(fields[2])
        );

        return rent;
    }
}