package Autopark.Main;

import Autopark.Infrastructure.core.impl.ApplicationContext;
import Autopark.Infrastructure.orm.ConnectionFactory;
import Autopark.Infrastructure.orm.service.PostgreDataBaseService;
import Autopark.Service.BadMechanicService;
import Autopark.Service.Fixer;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, BadMechanicService.class);
        ApplicationContext applicationContext = new ApplicationContext("Autopark", interfaceToImplementation);

        PostgreDataBaseService postgreDataBaseService = applicationContext.getObject(PostgreDataBaseService.class);
    }
}