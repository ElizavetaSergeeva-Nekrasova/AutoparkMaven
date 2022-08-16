package Autopark.Entity;

import Autopark.Engine.EngineFactory;
import Autopark.Engine.Startable;
import Autopark.EntityCollection.EntityCollection;
import Autopark.Exceptions.NotVehicleException;
import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Infrastructure.orm.annotations.Column;
import Autopark.Infrastructure.orm.annotations.ID;
import Autopark.Infrastructure.orm.annotations.Table;


import Autopark.Service.TechnicalSpecialist;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Table(name = "Vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {
    @ID
    private Long id;

    @Column(name = "typeId")
    private Long typeId;

    @Column(name = "model")
    private String model;

    @Column(name = "stateNumber", unique = true)
    private String stateNumber;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "year")
    private Integer year;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "color")
    private String color;

    @Column(name = "engineName")
    private String engineName;

    @Column(name = "consumptionPerKilometer")
    private Double consumptionPerKilometer;

    @Column(name = "tankCapacity")
    private Double tankCapacity;

    @Column(name = "engineCapacity")
    private Double engineCapacity;

    private Startable engine;

    private Map<Long, Types> typeIdToType;

    public Vehicles(Long id, Long typeId, String model, String stateNumber, Double weight, Integer year, Integer mileage, String color, String engineName,  Double engineCapacity, Double consumptionPerKilometer, Double tankCapacity) {
        this.id = id;
        this.typeId = typeId;
        setModel(model);
        setStateNumber(stateNumber);
        setWeight(weight);
        setYear(year);
        setMileage(mileage);
        setColor(color);
        this.engineName = engineName;
        this.consumptionPerKilometer = consumptionPerKilometer;
        this.tankCapacity = tankCapacity;
        this.engineCapacity = engineCapacity;

        try {
            engine = EngineFactory.create(this);
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    @InitMethod
    public void init() {
        try {
            engine = EngineFactory.create(this);
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }

        initializeTypeIdToType();
    }

    public void setModel(String model) {
        try {
            if (!TechnicalSpecialist.validateModelName(model)) {
                throw new NotVehicleException("Vehicle model name is wrong" + model);
            }
            this.model = model;
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public void setStateNumber(String stateNumber) {
        try {
            if (!TechnicalSpecialist.validateRegistrationNumber(stateNumber)) {
                throw new NotVehicleException("Vehicle state number is wrong" + stateNumber);
            }
            this.stateNumber = stateNumber;
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public void setWeight(Double weight) {
        try {
            if (!TechnicalSpecialist.validateWeight(weight)) {
                throw new NotVehicleException("Vehicle weight is wrong" + weight);
            }
            this.weight = weight;
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public void setYear(Integer year) {
        try {
            if (!TechnicalSpecialist.validateManufactureYear(year)) {
                throw new NotVehicleException("Vehicle year is wrong" + year);
            }
            this.year = year;
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public void setMileage(Integer mileage) {
        try {
            if (!TechnicalSpecialist.validateMileage(mileage)) {
                throw new NotVehicleException("Vehicle mileage wrong" + mileage);
            }
            this.mileage = mileage;
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public void setColor(String color) {
        try {
            if (!TechnicalSpecialist.validateColor(color)) {
                throw new NotVehicleException("Vehicle color is wrong" + color);
            }
            this.color = color;
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }

    public double getCalcTaxPerMonth() {
        return (weight * 0.0013) + (typeIdToType.get(typeId).getCoefTaxes() *
                engine.getTaxPerMonth() * 30) + 5;
    }

    public double getTotalIncome(EntityCollection entityCollection) {
        List<Rents> rents = entityCollection.getRentsListForVehicle(id);
        double sum = 0;

        for (int i = 0; i < rents.size(); i++) {
            sum += rents.get(i).getCost();
        }

        return sum;
    }

    public double getTotalProfit(EntityCollection entityCollection) {
        return getTotalIncome(entityCollection) - getCalcTaxPerMonth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicles vehicles = (Vehicles) o;
        return Objects.equals(typeId, vehicles.typeId) && Objects.equals(model, vehicles.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeId, model);
    }

    private void initializeTypeIdToType() {
        typeIdToType = new HashMap<>();
        typeIdToType.put(1L, new Types(1L, "Bus", 1.2d));
        typeIdToType.put(2L, new Types(2L, "Car", 1.0d));
        typeIdToType.put(3L, new Types(3L, "Rink", 1.5d));
        typeIdToType.put(4L, new Types(4L, "Tractor", 1.3d));
    }
}