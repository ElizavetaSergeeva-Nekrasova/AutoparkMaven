package Autopark.Service;

import Autopark.Entity.Vehicles;
import Autopark.FileUtils.FileUtils;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Parser.ParserBreakingsFromFile;
import Autopark.RandomizeUtils.Randomizer;
import Autopark.Comparators.ComparatorByDefectCount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class MechanicService implements Fixer {
    private static final String[] DETAILS = {"Фильтр", "Втулка", "Вал", "Ось", "Свеча",  "Масло", "ГРМ", "ШРУС"};
    private static final int NUMBER_OF_DETAILS = 8;
    private static final int MAX_NUMBER_OF_BROKEN_DETAILS = 5;
    private static final int MAX_NUMBER_OF_BREAKS = 5;

    List<String> orders;

    @Autowired
    private ParserBreakingsFromFile ordersParser;

    @Override
    public Map<String, Integer> detectBreaking(Vehicles vehicles) {
        Map<String, Integer> map = new HashMap<>();
        int numberOfBrokenDetails = Randomizer.getRandomFromZeroToNMinusOne(MAX_NUMBER_OF_BROKEN_DETAILS);

        int i = 0;
        while (i < numberOfBrokenDetails) {
            setMapOfBrokenDetails(map);
            i++;
        }

        if (!map.isEmpty()) {
            writeToFile(vehicles, map);
        }

        return map;
    }

    @Override
    public void repair(Vehicles vehicle) {
        orders = ordersParser.getOrders();
        String regex = vehicle.getId() + ".*";

        orders.removeIf(i -> i.matches(regex));
        FileUtils.writeListToFile(orders, "src/main/resources/orders.csv");
    }

    @Override
    public boolean isBroken(Vehicles vehicles) {
        if (getLineFromOrdersFile(vehicles) != null) {
            return true;
        }

        return false;
    }

    public int getSumNumberOfBreaks(Vehicles vehicles) {
        int sum = 0;

        String regex = "\\s{1}\\d{1}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(getLineFromOrdersFile(vehicles));

        while (matcher.find()) {
            sum += Integer.parseInt(matcher.group().trim());
        }

        return sum;
    }

    public List<Vehicles> getBrokenVehicles(List<Vehicles> vehicleList) {
        return vehicleList
                .stream()
                .filter(x -> !this.detectBreaking(x).isEmpty())
                .collect(Collectors.toList());
    }

    public List<Vehicles> sortByNumberOfBrokenDetails(List<Vehicles> brokenVehicleList) {
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

    private String getLineFromOrdersFile(Vehicles vehicles) {
        orders = ordersParser.getOrders();
        String regex = vehicles.getId() + ".*";

        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).matches(regex)) {
                return orders.get(i);
            }
        }

        return null;
    }

    private static void writeToFile(Vehicles vehicles, Map<String, Integer> map) {
        String line = createBreakLine(vehicles, map);

        FileUtils.writeLineToFile(line, "src/main/resources/orders.csv");
    }

    private static String createBreakLine(Vehicles vehicles, Map<String, Integer> map) {
        String line = String.valueOf(vehicles.getId());

        for (Map.Entry<String, Integer> entry:
                map.entrySet()) {
            line = line + ", " + entry.getKey() + ", " + entry.getValue();
        }

        line = line + "\n";

        return line;
    }
}