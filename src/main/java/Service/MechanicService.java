package Service;

import FileUtils.FileUtils;
import RandomizeUtils.Randomizer;
import Vehicle.Vehicle;
import Comparators.ComparatorByDefectCount;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MechanicService implements Fixer {
    private static final String[] DETAILS = {"Фильтр", "Втулка", "Вал", "Ось", "Свеча",  "Масло", "ГРМ", "ШРУС"};
    private static final int NUMBER_OF_DETAILS = 8;
    private static final int MAX_NUMBER_OF_BROKEN_DETAILS = 5;
    private static final int MAX_NUMBER_OF_BREAKS = 5;

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        Map<String, Integer> map = new HashMap<>();
        int numberOfBrokenDetails = Randomizer.getRandomFromZeroToNMinusOne(MAX_NUMBER_OF_BROKEN_DETAILS);

        int i = 0;
        while (i < numberOfBrokenDetails) {
            setMapOfBrokenDetails(map);
            i++;
        }

        if (!map.isEmpty()) {
            writeToFile(vehicle, map);
        }

        return map;
    }

    @Override
    public void repair(Vehicle vehicle) {
        List<String> list = FileUtils.readInfo("src/main/resources/orders.csv");
        String regex = vehicle.getId() + ".*";

        list.removeIf(i -> i.matches(regex));
        FileUtils.writeListToFile(list, "src/main/resources/orders.csv");
    }

    @Override
    public boolean isBroken(Vehicle vehicle) {
        if (getLineFromOrdersFile(vehicle) != null) {
            return true;
        }

        return false;
    }

    public static int getSumNumberOfBreaks(Vehicle vehicle) {
        int sum = 0;

        String regex = "\\s{1}\\d{1}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(getLineFromOrdersFile(vehicle));

        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group().trim());
        }

        return sum;
    }

    public List<Vehicle> getBrokenVehicles(List<Vehicle> vehicleList) {
        return vehicleList
                .stream()
                .filter(x -> !this.detectBreaking(x).isEmpty())
                .collect(Collectors.toList());
    }

    public static List<Vehicle> sortByNumberOfBrokenDetails(List<Vehicle> brokenVehicleList) {
        return brokenVehicleList
                .stream()
                .sorted(new ComparatorByDefectCount())
                .collect(Collectors.toList());
    }

    private static void setMapOfBrokenDetails(Map<String, Integer> map) {
        String detail = DETAILS[Randomizer.getRandomFromZeroToNMinusOne(NUMBER_OF_DETAILS)];
        int numberOfBreaks = Randomizer.getRandomFromOneToN(MAX_NUMBER_OF_BREAKS);
        map.put(detail, numberOfBreaks);
    }

    private static String getLineFromOrdersFile(Vehicle vehicle) {
        List<String> list = FileUtils.readInfo("src/main/resources/orders.csv");
        String regex = vehicle.getId() + ".*";

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).matches(regex)) {
                return list.get(i);
            }
        }

        return null;
    }

    private static void writeToFile(Vehicle vehicle, Map<String, Integer> map) {
        String line = getBreakLine(vehicle, map);

        FileUtils.writeLineToFile(line, "src/main/resources/orders.csv");
    }

    private static String getBreakLine(Vehicle vehicle, Map<String, Integer> map) {
        String line = String.valueOf(vehicle.getId());

        for (Map.Entry<String, Integer> entry:
                map.entrySet()) {
            line = line + ", " + entry.getKey() + ", " + entry.getValue();
        }

        line = line + "\n";

        return line;
    }
}