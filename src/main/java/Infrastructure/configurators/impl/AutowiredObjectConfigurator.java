package Infrastructure.configurators.impl;

import Infrastructure.configurators.ObjectConfigurator;
import Infrastructure.core.Context;
import Infrastructure.core.annotations.Autowired;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AutowiredObjectConfigurator implements ObjectConfigurator {
    @SneakyThrows
    @Override
    public void configure(Object object, Context context) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                String setterName = ConfiguratorUtils.deriveSetterNameFromFieldName(field);
                Method setterMethod = object.getClass().getMethod(setterName, field.getType());

                setterMethod.invoke(object, context.getObject(field.getType()));
            }
        }
    }
}