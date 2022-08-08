package Infrastructure.core;

import Infrastructure.config.Config;

public interface Context {
    <T> T getObject(Class<T> type);
    Config getConfig();
}