package Autopark.Parser;

import Autopark.DateUtils.DateUtils;
import Autopark.FileUtils.FileUtils;
import Autopark.Rent.Rent;

import java.util.ArrayList;
import java.util.List;

public class RentParser {
    private String rentsFile = "src/main/resources/rents.csv";

    public RentParser() {
    }

    public List<Rent> loadRents() {
        List<String> list = FileUtils.readInfo(rentsFile);
        List<Rent> rentsList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            rentsList.add(createRent(list.get(i)));
        }

        return rentsList;
    }

    private Rent createRent(String csvString) {
        String[] fields = ParserStringFormatter.splitLineWithComma(csvString);

        Rent rent = new Rent(
                Integer.parseInt(fields[0]),
                DateUtils.formatStringToDate(fields[1]),
                Double.parseDouble(fields[2])
        );

        return rent;
    }
}