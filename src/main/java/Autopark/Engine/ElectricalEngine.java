package Autopark.Engine;

import Autopark.Exceptions.NotVehicleException;
import Autopark.Service.TechnicalSpecialist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElectricalEngine extends AbstractEngine{
    public ElectricalEngine(double consumptionPerKilometer, double tankCapacity) {
        super("Electrical", 0.1d, consumptionPerKilometer, tankCapacity);
        try {
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

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(" + super.toString() + ")";
    }
}