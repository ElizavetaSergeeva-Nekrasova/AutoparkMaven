package Service;

import FileUtils.FileUtils;
import Infrastructure.core.annotations.Autowired;
import Parser.ParserBreakingsFromFile;
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
    List<String> orders;

    @Autowired
    private ParserBreakingsFromFile ordersParser;

    public MechanicService() {
    }
    public ParserBreakingsFromFile getOrdersParser() {
        return ordersParser;
    }

    public void setOrdersParser(ParserBreakingsFromFile ordersParser) {
        this.ordersParser = ordersParser;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

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
        orders = ordersParser.getOrders();
        String regex = vehicle.getId() + ".*";

        orders.removeIf(i -> i.matches(regex));
        FileUtils.writeListToFile(orders, "src/main/resources/orders.csv");
    }

    @Override
    public boolean isBroken(Vehicle vehicle) {
        if (getLineFromOrdersFile(vehicle) != null) {
            return true;
        }

        return false;
    }

    public int getSumNumberOfBreaks(Vehicle vehicle) {
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

    public List<Vehicle> sortByNumberOfBrokenDetails(List<Vehicle> brokenVehicleList) {
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

    private String getLineFromOrdersFile(Vehicle vehicle) {
        orders = ordersParser.getOrders();
        String regex = vehicle.getId() + ".*";

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).matches(regex)) {
                return orders.get(i);
            }
        }

        return null;
    }

    private static void writeToFile(Vehicle vehicle, Map<String, Integer> map) {
        String line = createBreakLine(vehicle, map);

        FileUtils.writeLineToFile(line, "src/main/resources/orders.csv");
    }

    private static String createBreakLine(Vehicle vehicle, Map<String, Integer> map) {
        String line = String.valueOf(vehicle.getId());

        for (Map.Entry<String, Integer> entry:
                map.entrySet()) {
            line = line + ", " + entry.getKey() + ", " + entry.getValue();
        }

        line = line + "\n";

        return line;
    }
}