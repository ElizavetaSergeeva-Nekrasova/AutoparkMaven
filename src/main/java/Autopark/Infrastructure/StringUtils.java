package Autopark.Infrastructure;

import java.lang.reflect.Field;

public class StringUtils {
    public static String deriveSetterNameFromFieldName(Field field) {
        String fieldName = field.getName();

        return "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
    }
    public static String deriveGetterNameFromFieldName(Field field) {
        String fieldName = field.getName();

        return "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
    }
}
