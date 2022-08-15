package Autopark.Main;

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




    }
}