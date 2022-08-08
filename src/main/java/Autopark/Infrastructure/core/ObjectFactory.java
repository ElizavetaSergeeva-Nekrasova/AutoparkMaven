package Autopark.Infrastructure.core;

public interface ObjectFactory {
    <T> T createObject(Class<T> implementation);
}