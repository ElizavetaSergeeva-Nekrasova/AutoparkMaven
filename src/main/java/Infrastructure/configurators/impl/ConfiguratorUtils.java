package Infrastructure.configurators.impl;

import java.lang.reflect.Field;

public class ConfiguratorUtils {
    public static String deriveSetterNameFromFieldName(Field field) {
        String fieldName = field.getName();

        return "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
    }
}