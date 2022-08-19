package Autopark.Servlets;

import Autopark.DTO.VehiclesDto;

import java.util.List;

public class CalculationUtils {
    public static double calculateAverageTax(List<VehiclesDto> dtoList) {
        double sum = 0.0d;
        double count = 0.0d;

        for (VehiclesDto v:
             dtoList) {
            sum += v.getTax();
            count++;
        }

        return (sum / count);
    }

    public static double calculateAverageIncome(List<VehiclesDto> dtoList) {
        double sum = 0.0d;
        double count = 0.0d;

        for (VehiclesDto v:
                dtoList) {
            sum += v.getIncome();
            count++;
        }

        return (sum / count);
    }

    public static double calculateTotalProfit(List<VehiclesDto> dtoList) {
        double sum = 0.0d;

        for (VehiclesDto v:
                dtoList) {
            sum += v.getProfit();
        }

        return sum;
    }
}