package Autopark.Engine;

import Autopark.Entity.Vehicles;
import Autopark.Exceptions.NotVehicleException;

public class EngineFactory {
    public static Startable create(Vehicles vehicles) throws NotVehicleException {
        if (vehicles.getEngineName().equals("Electrical")) {
            return new ElectricalEngine(vehicles.getConsumptionPerKilometer(), vehicles.getTankCapacity());
        }

        if (vehicles.getEngineName().equals("Diesel")) {
            return new DieselEngine(vehicles.getEngineCapacity(), vehicles.getConsumptionPerKilometer(), vehicles.getTankCapacity());
        }

        if (vehicles.getEngineName().equals("Gasoline")) {
            return new DieselEngine(vehicles.getEngineCapacity(), vehicles.getConsumptionPerKilometer(), vehicles.getTankCapacity());
        }
        
        else throw new NotVehicleException("Wrong engine type" + vehicles.getEngineName());
    }
}