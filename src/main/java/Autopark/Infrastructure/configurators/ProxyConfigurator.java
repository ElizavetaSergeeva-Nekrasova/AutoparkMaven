package Autopark.Infrastructure.configurators;

import Autopark.Infrastructure.core.Context;

public interface ProxyConfigurator {
    <T> T makeProxy(T object, Class<T> implementation, Context context);
}
