package Autopark.Main;

import Autopark.EntityCollection.EntityCollection;
import Autopark.Infrastructure.core.impl.ApplicationContext;
import Autopark.Service.Fixer;
import Autopark.Service.MechanicService;
import Autopark.Service.ScheduledCheck;
import Autopark.Service.Workroom;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        ApplicationContext applicationContext = new ApplicationContext("Autopark", interfaceToImplementation);

        EntityCollection entityCollection = applicationContext.getObject(EntityCollection.class);
        Workroom workroom = applicationContext.getObject(Workroom.class);
        ScheduledCheck scheduledCheck = applicationContext.getObject(ScheduledCheck.class);

        scheduledCheck.scheduledCheck(workroom, entityCollection);

        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}