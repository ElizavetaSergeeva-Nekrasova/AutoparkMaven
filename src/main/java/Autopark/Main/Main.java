package Autopark.Main;

import Autopark.Infrastructure.core.impl.ApplicationContext;
import Autopark.Service.Workroom;
import Autopark.Vehicle.Vehicle;
import Autopark.VehicleCollections.VehicleCollection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        ApplicationContext applicationContext = new ApplicationContext("Autopark", interfaceToImplementation);

        VehicleCollection vehicleCollection = applicationContext.getObject(VehicleCollection.class);
        Workroom workroom = applicationContext.getObject(Workroom.class);

        List<Vehicle> vehicleList = vehicleCollection.getVehicleList();

    }
}