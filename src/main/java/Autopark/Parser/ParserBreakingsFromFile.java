package Autopark.Parser;

import Autopark.FileUtils.FileUtils;
import Autopark.Infrastructure.core.annotations.InitMethod;

import java.util.List;

public class ParserBreakingsFromFile {
    private String ordersFile;

    public ParserBreakingsFromFile() {
    }

    @InitMethod
    public void init() {
        ordersFile = "src/main/resources/orders.csv";
    }

    public List<String> getOrders() {
        return FileUtils.readInfo(ordersFile);
    }
}