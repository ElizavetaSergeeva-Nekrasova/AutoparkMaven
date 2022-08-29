package Autopark.Servlets;

import Autopark.Service.Fixer;
import Autopark.Service.MechanicService;

import java.util.HashMap;
import java.util.Map;

public class ContextUtil {
    public static Map<Class<?>, Class<?>> getInterfaceToImplementation() {
        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);

        return interfaceToImplementation;
    }
}