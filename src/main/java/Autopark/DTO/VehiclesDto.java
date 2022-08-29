package Autopark.DTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VehiclesDto {
    private Long id;
    private Long typeId;
    private String typeName;
    private Double coefTaxes;
    private String model;
    private String stateNumber;
    private Double weight;
    private Integer year;
    private Integer mileage;
    private String color;
    private String engineName;
    private Double tankCapacity;
    private Double engineCapacity;
    private Double per100Kilometers;
    private Double maxKilometers;
    private Double tax;
    private Double income;
    private Double profit;
}