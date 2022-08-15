package Autopark.Engine;

import Autopark.Exceptions.NotVehicleException;
import Autopark.Service.TechnicalSpecialist;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CombustionEngine extends AbstractEngine{
    private double engineCapacity;
    
    public CombustionEngine(String typeName, double taxByEngineType, double engineCapacity, double consumptionPerKilometer, double tankCapacity) {
        super(typeName, taxByEngineType, consumptionPerKilometer, tankCapacity);
        
        try {
            if (TechnicalSpecialist.validateEngineCapacity(engineCapacity)) {
                this.engineCapacity = engineCapacity;
            } else {
                throw new NotVehicleException("Wrong engine capacity: " + engineCapacity);
            }
            if (TechnicalSpecialist.validateTankCapacity(tankCapacity)) {
                setTankCapacity(tankCapacity);
            } else {
                throw new NotVehicleException("Wrong fuel tank capacity: " + tankCapacity);
            }
            if (TechnicalSpecialist.validateConsumptionPerKilometer(consumptionPerKilometer)) {
                setConsumptionPerKilometer(consumptionPerKilometer);
            } else {
                throw new NotVehicleException("Wrong fuel consumption per kilometer:  " + consumptionPerKilometer);
            }
        } catch (NotVehicleException e) {
            e.printStackTrace();
        }
    }
}