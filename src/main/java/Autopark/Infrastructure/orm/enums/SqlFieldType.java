package Autopark.Infrastructure.orm.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@Getter
public enum SqlFieldType {
    INTEGER(Integer.class, "integer", "%s"),
    //тут интегер?
    LONG(Long.class, "integer", "%s"),
    DOUBLE(Double.class, "decimal", "%s"),
    STRING(String.class, "varchar(255)", "'%s'"),
    //тут java.util.Date или java.sql.Date?
    DATE(Date.class, "date", "'%s'");

    private final Class<?> type;
    private final String sqlType;
    private final String insertPattern;
}