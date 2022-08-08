package Infrastructure.core;

public interface ObjectFactory {
    <T> T createObject(Class<T> implementation);
}