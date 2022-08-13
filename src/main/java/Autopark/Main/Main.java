package Autopark.Main;

import Autopark.EntitiesService.TypesService;
import Autopark.Entity.Types;
import Autopark.Infrastructure.core.impl.ApplicationContext;
import Autopark.Service.BadMechanicService;
import Autopark.Service.Fixer;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, BadMechanicService.class);
        ApplicationContext applicationContext = new ApplicationContext("Autopark", interfaceToImplementation);

        TypesService typesService = applicationContext.getObject(TypesService.class);
        Types type = new Types(Long.valueOf(1), "unknown", 1.2);
        typesService.save(type);
        Types typeFromTable = typesService.get(17L);
        Types typeFromTable2 = typesService.get(15L);
        System.out.println(typeFromTable2);
    }
}