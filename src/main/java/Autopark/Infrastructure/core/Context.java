package Autopark.Infrastructure.core;

import Autopark.Infrastructure.config.Config;

public interface Context {
    <T> T getObject(Class<T> type);
    Config getConfig();
}