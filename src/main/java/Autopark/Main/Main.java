package Autopark.Main;

import Autopark.DateUtils.DateUtils;
import Autopark.EntitiesService.RentsService;
import Autopark.EntitiesService.VehiclesService;
import Autopark.Entity.Rents;
import Autopark.Entity.Vehicles;
import Autopark.Infrastructure.core.impl.ApplicationContext;
import Autopark.Service.Fixer;
import Autopark.Service.MechanicService;
import Autopark.Service.Workroom;
import Autopark.Vehicle.Vehicle;
import Autopark.VehicleCollections.VehicleCollection;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        ApplicationContext applicationContext = new ApplicationContext("Autopark", interfaceToImplementation);

        VehicleCollection vehicleCollection = applicationContext.getObject(VehicleCollection.class);
        Workroom workroom = applicationContext.getObject(Workroom.class);

        List<Vehicle> vehicleList = vehicleCollection.getVehicleList();

        Rents rents = new Rents(1L, 23L, DateUtils.formatStringToDate("03.03.2003"), 5.5d);
        RentsService rentsService = applicationContext.getObject(RentsService.class);
        rentsService.save(rents);
        List<Rents> rentsList = rentsService.getAll();
        System.out.println(rentsList);
    }
}