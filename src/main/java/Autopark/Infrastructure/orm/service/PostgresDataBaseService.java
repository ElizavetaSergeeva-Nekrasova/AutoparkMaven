package Autopark.Infrastructure.orm.service;

import Autopark.Infrastructure.StringUtils;
import Autopark.Infrastructure.core.Context;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Infrastructure.orm.ConnectionFactory;
import Autopark.Infrastructure.orm.annotations.Column;
import Autopark.Infrastructure.orm.annotations.ID;
import Autopark.Infrastructure.orm.annotations.Table;
import Autopark.Infrastructure.orm.enums.SqlFieldType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Setter
@Getter
@NoArgsConstructor
public class PostgresDataBaseService {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private Context context;

    private Connection connection;
    private Statement statement;

    private Map<String, String> classToSql = new HashMap<>();
    private Map<String, String> insertPatternByClass = new HashMap<>();
    private Map<String, String> insertByClassPattern = new HashMap<>();
    private Map<String, String> deleteByClassPattern = new HashMap<>();

    private static final String CREATE_TABLE_SQL_PATTERN =
            "CREATE TABLE IF NOT EXISTS %s (\n" +
                    "%s SERIAL PRIMARY KEY, " +
                    "%S\n);";
    private static final String INSERT_SQL_PATTERN =
            "INSERT INTO %s(%s)\n" +
                    "VALUES (%s)\n" +
                    "RETURNING %s ;";
    private static final String DELETE_SQL_PATTERN =
            "DELETE FROM %s\n" +
                    "WHERE id = %s;";

    @InitMethod
    public void init() {
        connection = connectionFactory.getConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SqlFieldType[] sqlFieldTypes = SqlFieldType.values();

        Arrays.stream(sqlFieldTypes).forEach(x -> classToSql.put(x.getType().getName(), x.getSqlType()));
        Arrays.stream(sqlFieldTypes).forEach(x -> insertPatternByClass.put(x.getType().getName(), x.getInsertPattern()));

        Set<Class<?>> entities =
                context.getConfig().getScanner().getReflections().getTypesAnnotatedWith(Table.class);

        validateEntities(entities);
        createTablesIfNotExists(entities);

        initializeInsertByClassPattern(entities);
        initializeDeleteByClassPattern(entities);
    }

    public void save(Object obj) {
        String valuesLine = getValuesLine(obj);

        String sql = String.format(insertByClassPattern.get(obj.getClass().getName()), valuesLine);

        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> Optional<T> get(Long id, Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("This class has no Table annotation");
        }

        Optional optional = null;

        String sql = "SELECT * FROM " + clazz.getAnnotation(Table.class).name() +
                " WHERE " + findIdField(clazz) + " = " + id;

        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                optional = Optional.of(makeObject(resultSet, clazz));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return optional;
    }

    public <T> List<T> getAll(Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("This class has no Table annotation");
        }

        String tableName = clazz.getAnnotation(Table.class).name();
        String sql = "SELECT * FROM " + tableName;
        List<T> list = new ArrayList<>();

        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                list.add(makeObject(resultSet, clazz));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @SneakyThrows
    private <T> T makeObject(ResultSet resultSet, Class<T> clazz) {
        T object = clazz.getConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field :
                fields) {
            if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(ID.class)) {
                String setterName = StringUtils.deriveSetterNameFromFieldName(field);
                Method setterMethod = object.getClass().getMethod(setterName, field.getType());
                invokeSetterMethodByFieldType(setterMethod, field, object, resultSet);
            }
        }

        for (Method method: clazz.getMethods()) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(object);
            }
        }

        return object;
    }

    public void delete(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();

        Long id = null;

        for (Field field :
                fields) {
            if (field.isAnnotationPresent(ID.class)) {
                Method method = null;
                try {
                    method = obj.getClass().getMethod(StringUtils.deriveGetterNameFromFieldName(field));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                try {
                    id = (Long) method.invoke(obj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        String sql = String.format(deleteByClassPattern.get(obj.getClass().getName()), id);

        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private void invokeSetterMethodByFieldType(Method setterMethod, Field field, Object object, ResultSet resultSet) {
        if (field.getType() == String.class) {
            setterMethod.invoke(object, resultSet.getString(field.getName()));
        }

        if (field.getType() == Integer.class) {
            setterMethod.invoke(object, resultSet.getInt(field.getName()));
        }

        if (field.getType() == Long.class) {
            setterMethod.invoke(object, resultSet.getLong(field.getName()));
        }

        if (field.getType() == Double.class) {
            setterMethod.invoke(object, resultSet.getDouble(field.getName()));
        }

        if (field.getType() == Date.class) {
            setterMethod.invoke(object, resultSet.getDate(field.getName()));
        }
    }

    private String getValuesLine(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();

        StringBuilder stringBuilder = new StringBuilder("");

        for (Field field :
                fields) {
            if (field.isAnnotationPresent(Column.class)) {
                try {
                    Method method = obj.getClass().getMethod(StringUtils.deriveGetterNameFromFieldName(field));
                    try {
                        stringBuilder.append("'" + method.invoke(obj) + "'" + ", ");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
        String valuesLine = String.valueOf(stringBuilder);

        return valuesLine;
    }

    private void validateEntities(Set<Class<?>> entities) {
        Iterator setIterator = entities.iterator();

        while (setIterator.hasNext()) {
            Class<?> clazz = (Class<?>) setIterator.next();
            validateClazz(clazz);
        }
    }

    private void validateClazz(Class<?> clazz) {
        checkIfExistsLongFieldWithIdAnnotation(clazz);
        checkIfFieldsHaveCorrectColumnNamesAndTypes(clazz);
    }

    private void checkIfExistsLongFieldWithIdAnnotation(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field :
                fields) {
            if (field.isAnnotationPresent(ID.class)) {
                if (field.getType() == Long.class) {
                    return;
                }
            }
        }

        throw new RuntimeException("There is no field with ID-annotation and type Long in class " + clazz.getName());
    }

    private void checkIfFieldsHaveCorrectColumnNamesAndTypes(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field :
                fields) {
            if (field.getType().isPrimitive()) {
                throw new RuntimeException("There is primitive data type field " + field + " in class " + clazz.getName());
            }
        }
    }

    private void createTablesIfNotExists(Set<Class<?>> entities) {
        for (Class clazz :
                entities) {
            createTable(clazz);
        }
    }

    private void createTable(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String idField = findIdField(clazz);
        String fields = String.valueOf(createFieldsLine(clazz));

        String sql = String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, fields);
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String findIdField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String id = null;
        for (Field field :
                fields) {
            if (field.isAnnotationPresent(ID.class)) {
                id = field.getName();
                return id;
            }
        }

        return id;
    }

    private StringBuilder createFieldsLine(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldsLine = new StringBuilder("");

        for (Field field :
                fields) {
            if (field.isAnnotationPresent(Column.class)) {

                fieldsLine.append(field.getName() + " " +
                        classToSql.get(field.getType().getName()) + " " +
                        checkNullable(field) + " " +
                        checkUnique(field) + ", ");
            }
        }

        fieldsLine.delete(fieldsLine.length() - 2, fieldsLine.length() - 1);

        return fieldsLine;
    }

    private String checkNullable(Field field) {
        if (field.getAnnotation(Column.class).nullable()) {
            return "NOT NULL";
        } else {
            return "";
        }
    }

    private String checkUnique(Field field) {
        if (field.getAnnotation(Column.class).unique()) {
            return "UNIQUE";
        } else {
            return "";
        }
    }

    private void initializeInsertByClassPattern(Set<Class<?>> entities) {
        entities.stream().forEach(x -> putInsertPatternToMap(x));
    }

    private void putInsertPatternToMap(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String insertField = String.valueOf(createFieldsLineForInsert(clazz));
        String idFieldName = findIdField(clazz);

        String sql = String.format(INSERT_SQL_PATTERN, tableName, insertField, "%s", idFieldName);

        insertByClassPattern.put(clazz.getName(), sql);
    }

    private void initializeDeleteByClassPattern(Set<Class<?>> entities) {
        entities.stream().forEach(x -> putDeletePatternToMap(x));
    }

    private void putDeletePatternToMap(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();

        String sql = String.format(DELETE_SQL_PATTERN, tableName, "%s");

        deleteByClassPattern.put(clazz.getName(), sql);
    }

    private StringBuilder createFieldsLineForInsert(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldsLine = new StringBuilder("");

        for (Field field :
                fields) {
            if (field.isAnnotationPresent(Column.class)) {
                fieldsLine.append(field.getName() + ", ");
            }
        }

        fieldsLine.delete(fieldsLine.length() - 2, fieldsLine.length() - 1);

        return fieldsLine;
    }
}