package Autopark.Main;

import Autopark.EntityCollection.EntityCollection;
import Autopark.Infrastructure.core.impl.ApplicationContext;
import Autopark.Service.Fixer;
import Autopark.Service.MechanicService;
import Autopark.Service.Workroom;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        ApplicationContext applicationContext = new ApplicationContext("Autopark", interfaceToImplementation);

        EntityCollection entityCollection = applicationContext.getObject(EntityCollection.class);
        System.out.println(entityCollection.getVehiclesList());
        System.out.println(entityCollection.getRentsList());
        System.out.println(entityCollection.getTypesList());
        Workroom workroom = applicationContext.getObject(Workroom.class);
        workroom.checkAllVehicle(entityCollection.getVehiclesList());
    }
}