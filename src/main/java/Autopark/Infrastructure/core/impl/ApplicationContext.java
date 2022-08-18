package Autopark.Infrastructure.core.impl;

import Autopark.Infrastructure.config.Config;
import Autopark.Infrastructure.core.Cache;
import Autopark.Infrastructure.core.Context;
import Autopark.Infrastructure.core.ObjectFactory;
import Autopark.Infrastructure.config.impl.JavaConfig;

import java.util.Map;

public class ApplicationContext implements Context {
    private final Config config;
    private final Cache cache;
    private final ObjectFactory factory;

    private volatile static ApplicationContext applicationContext;

    private ApplicationContext(String packageToScan, Map<Class<?>, Class<?>> interfaceToImplementation) {
        this.config = new JavaConfig(new ScannerImpl(packageToScan), interfaceToImplementation);
        this.cache = new CacheImpl();
        cache.put(Context.class, this);
        this.factory = new ObjectFactoryImpl(this);
    }

    public static ApplicationContext getInstance(String packageToScan, Map<Class<?>, Class<?>> interfaceToImplementation) {
        if (applicationContext == null) {
            synchronized (ApplicationContext.class) {
                if (applicationContext == null) {
                    applicationContext = new ApplicationContext(packageToScan, interfaceToImplementation);
                }
            }
        }

        return applicationContext;
    }

    @Override
    public <T> T getObject(Class<T> type) {
        if (cache.contains(type)) {
            return cache.get(type);
        }

        T object = null;
        if (type.isInterface()) {
            Class implementation = config.getImplementation(type);
            object = (T) factory.createObject(implementation);
        } else {
            object = (T) factory.createObject(type);
        }

        cache.put(type, object);

        return object;
    }

    @Override
    public Config getConfig() {
        return config;
    }
}