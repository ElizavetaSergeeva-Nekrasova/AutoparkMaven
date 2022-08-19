package Autopark.Infrastructure.core.impl;

import Autopark.Infrastructure.configurators.ObjectConfigurator;
import Autopark.Infrastructure.configurators.ProxyConfigurator;
import Autopark.Infrastructure.core.Context;
import Autopark.Infrastructure.core.ObjectFactory;
import Autopark.Infrastructure.core.Scanner;
import Autopark.Infrastructure.core.annotations.InitMethod;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ObjectFactoryImpl implements ObjectFactory {
    private final Context context;
    private final List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private final List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();

    @SneakyThrows
    public ObjectFactoryImpl(Context context) {
        this.context = context;
        Scanner scanner = context.getConfig().getScanner();
        initializeObjectConfigurators(scanner);
        initializeProxyConfigurators(scanner);
    }

    @SneakyThrows
    @Override
    public <T> T createObject(Class<T> implementation) {
        T object = create(implementation);
        configure(object);
        initialize(implementation, object);
        object = makeProxy(implementation, object);

        return object;
    }

    private <T> T create(Class <T> implementation) throws Exception {
        return implementation.getConstructor().newInstance();
    }

    private <T> void configure(T object) {
        for (int i = 0; i <objectConfigurators.size() ; i++) {
            objectConfigurators.get(i).configure(object, context);
        }
    }

    private <T> void initialize(Class<T> implementation, T object) throws Exception {
        for (Method method: implementation.getMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(object);
            }
        }
    }

    private <T> T makeProxy(Class<T> implClass, T object) {
        for (ProxyConfigurator proxyConfigurator:
             proxyConfigurators) {
            object = (T) proxyConfigurator.makeProxy(object, implClass, context);
        }

        return object;
    }

    private void initializeObjectConfigurators(Scanner scanner) {
        Set set = scanner.getSubTypesOf(ObjectConfigurator.class);
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            Class<? extends ObjectConfigurator> subClass = (Class<? extends ObjectConfigurator>) iterator.next();
            try {
                objectConfigurators.add(subClass.getConstructor().newInstance());
            } catch (InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    private void initializeProxyConfigurators(Scanner scanner) {
        Set set = scanner.getSubTypesOf(ProxyConfigurator.class);
        Iterator iterator = set.iterator();

        while (iterator.hasNext()) {
            Class<? extends ProxyConfigurator> subClass = (Class<? extends ProxyConfigurator>) iterator.next();
            try {
                proxyConfigurators.add(subClass.getConstructor().newInstance());
            } catch (InstantiationException |
                    IllegalAccessException |
                    InvocationTargetException |
                    NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}