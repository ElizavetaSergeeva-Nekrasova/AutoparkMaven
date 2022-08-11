package Autopark.Infrastructure.config.impl;

import Autopark.Infrastructure.config.Config;
import Autopark.Infrastructure.core.Scanner;
import lombok.AllArgsConstructor;

import java.util.Map;
import java.util.Set;

@AllArgsConstructor
public class JavaConfig implements Config {
    private final Scanner scanner;
    private final Map<Class<?>, Class<?>> interfaceToImplementation;

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> target) {
        Set set = scanner.getSubTypesOf(target);

        if (set.size() != 1) {
            if (interfaceToImplementation.containsKey(target)) {
                return (Class<? extends T>) interfaceToImplementation.get(target);
            }

            throw new RuntimeException("target interface has 0 or more than 1 implementation" + target.getName());
        }

        return (Class<? extends T>) set.iterator().next();
    }

    @Override
    public Scanner getScanner() {
        return scanner;
    }
}