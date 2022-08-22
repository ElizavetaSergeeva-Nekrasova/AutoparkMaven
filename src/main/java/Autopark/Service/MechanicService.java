package Autopark.Service;

import Autopark.Entity.Orders;
import Autopark.Entity.Vehicles;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Parser.ParserOrdersFromDB;
import Autopark.RandomizeUtils.Randomizer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Setter
@Getter
@NoArgsConstructor
public class MechanicService implements Fixer {
    private static final String[] DETAILS = {"Фильтр", "Втулка", "Вал", "Ось", "Свеча",  "Масло", "ГРМ", "ШРУС"};
    private static final int NUMBER_OF_DETAILS = 8;
    private static final int MAX_NUMBER_OF_BROKEN_DETAILS = 5;
    private static final int MAX_NUMBER_OF_BREAKS = 5;

    List<Orders> orders;

    @Autowired
    private ParserOrdersFromDB ordersParser;

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
            saveOrder(vehicles, map);
        }

        return map;
    }

    @Override
    public void repair(Vehicles vehicle) {
        orders = ordersParser.getOrders();

        Long vehicleId = vehicle.getId();

        for (Orders order:
             orders) {
            if (order.getVehicleId() == vehicleId) {
                ordersParser.delete(order);
            }
        }
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

    private static void setMapOfBrokenDetails(Map<String, Integer> map) {
        String detail = DETAILS[Randomizer.getRandomFromZeroToNMinusOne(NUMBER_OF_DETAILS)];
        int numberOfBreaks = Randomizer.getRandomFromOneToN(MAX_NUMBER_OF_BREAKS);
        map.put(detail, numberOfBreaks);
    }

    private String getLineFromOrdersFile(Vehicles vehicles) {
        orders = ordersParser.getOrders();
        Long vehicleId = vehicles.getId();

        for (Orders order:
             orders) {
            if (order.getVehicleId() == vehicleId) {
                return order.getOrderLine();
            }
        }
        return null;
    }

    private void saveOrder(Vehicles vehicles, Map<String, Integer> map) {
        Orders orders = createOrders(vehicles, map);
        ordersParser.saveOrder(orders);
    }

    private static Orders createOrders(Vehicles vehicles, Map<String, Integer> map) {
        String line = String.valueOf(vehicles.getId());

        for (Map.Entry<String, Integer> entry:
                map.entrySet()) {
            line = line + ", " + entry.getKey() + ", " + entry.getValue();
        }

        line = line + "\n";

        return new Orders(vehicles.getId(), line);
    }
}