package Autopark.Infrastructure.orm.service;

import Autopark.Infrastructure.core.Context;
import Autopark.Infrastructure.core.annotations.Autowired;
import Autopark.Infrastructure.core.annotations.InitMethod;
import Autopark.Infrastructure.orm.ConnectionFactory;
import Autopark.Infrastructure.orm.annotations.Column;
import Autopark.Infrastructure.orm.annotations.ID;
import Autopark.Infrastructure.orm.annotations.Table;
import Autopark.Infrastructure.orm.enums.SqlFieldType;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.swing.text.html.Option;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

@Setter
@Getter
public class PostgreDataBaseService {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private Context context;

    private Map<String, String> classToSql;
    private Map<String, String> insertPatternByClass;
    private Map<String, String> insertByClassPattern;

    private static final String SEQ_NAME = "id_seq";
    private static final String CHECK_SEQ_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "SELECT FROM information_schema.sequences \n" +
                    "WHERE sequence_schema = 'public'\n" +
                    "AND sequence_name = '%s'\n" +
                    ");";
    private static final String CREATE_ID_SEQ_PATTERN =
            "CREATE SEQUENCE %S\n" +
                    "INCREMENT 1\n" +
                    "START 1;";
    private static final String CHECK_TABLE_SQL_PATTERN =
            "SELECT EXISTS (\n" +
                    "SELECT FROM information_schema.tables \n" +
                    "WHERE table_schema = 'public'\n" +
                    "AND table_name = '%s'\n" +
                    ");";
    private static final String CREATE_TABLE_SQL_PATTERN =
            "CREATE TABLE %s (\n" +
                    "%s integer PRIMARY KEY DEFAULT nextval('%s')" +
                    "%S\n);";
    private static final String INSERT_SQL_PATTERN =
            "INSERT INTO %s(%s)\n" +
                    "VALUES (%s)\n" +
                    "RETURNING %s ;";

    public PostgreDataBaseService() {
    }

    @InitMethod
    public void init() {
        Arrays.stream(SqlFieldType.values())
                .forEach(x -> classToSql.put(String.valueOf(x.getType()), x.getSqlType()));
        Arrays.stream(SqlFieldType.values())
                .forEach(x -> insertPatternByClass.put(String.valueOf(x.getType()), x.getInsertPattern()));

        if (!checkId_seq()) {
            createId_seq();
        }

        Set<Class<?>> entities =
                context.getConfig().getScanner().getReflections().getTypesAnnotatedWith(Table.class);

        validateEntities(entities);
        checkIfTablesExistsAndCreate(entities);

        initializeInsertByClassPattern(entities);
    }

    //return - это заглушка
    public Long save(Object obj) {
        Object[] values = getValuesArray(obj);

        String sql = String.format(insertByClassPattern.get(obj.getClass().getName()), values);

        Connection connection = connectionFactory.getConnection();

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Long.valueOf(1);
    }

    public <T> Optional<T> get(Long id, Class<T> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            throw new RuntimeException("This class has no Table annotation");
        }

        Optional optional = null;

        String sql = "SELECT * FROM " + clazz.getAnnotation(Table.class).name() +
                " WHERE " + findIdField(clazz) + " = " + id;

        Connection connection = connectionFactory.getConnection();

        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            optional = Optional.of(makeObject(resultSet, clazz));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return optional;
    }

    //ЗАГЛУШКА
    public <T> List<T> getAll(Class<T> clazz) {
        return new ArrayList<>();
    }

    @SneakyThrows
    private <T> T makeObject(ResultSet resultSet, Class<T> clazz) {
        T object = clazz.getConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field:
             fields) {
            if (field.isAnnotationPresent(Column.class)) {
                String setterName = deriveSetterNameFromFieldName(field);
                Method setterMethod = object.getClass().getMethod(setterName, field.getType());

                if (field.getType() == String.class) {
                    setterMethod.invoke(object, resultSet.getString(field.getName()));
                }

                if (field.getType() == Integer.class) {
                    setterMethod.invoke(object, resultSet.getInt(field.getName()));
                }

                if (field.getType() == Long.class) {
                    setterMethod.invoke(object, resultSet.getLong(field.getName()));
                }

                if (field.getType() == String.class) {
                    setterMethod.invoke(object, resultSet.getDouble(field.getName()));
                }

                if (field.getType() == Date.class) {
                    setterMethod.invoke(object, resultSet.getDate(field.getName()));
                }
            }
        }

        return object;
    }

    private static String deriveSetterNameFromFieldName(Field field) {
        String fieldName = field.getName();

        return "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
    }

    private Object[] getValuesArray(Object obj) {
        Field[] fields  = obj.getClass().getDeclaredFields();

        List<Object> list = new ArrayList<>();

        for (Field field:
            fields) {
            if (field.isAnnotationPresent(Column.class)) {
                try {
                    list.add(field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return list.toArray();
    }

    private boolean checkId_seq() {
        String sql = String.format(CHECK_SEQ_SQL_PATTERN, SEQ_NAME);

        Connection connection = connectionFactory.getConnection();

        Statement statement = null;
        ResultSet resultSet = null;
        boolean ifExists = false;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ifExists = resultSet.getBoolean("exists");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ifExists;
    }

    private void createId_seq() {
        String sql = String.format(CREATE_ID_SEQ_PATTERN, SEQ_NAME);

        Connection connection = connectionFactory.getConnection();

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

        for (Field field:
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

        for (Field field:
                fields) {
            if (field.getType().isPrimitive()) {
                throw new RuntimeException("There is primitive data type field " + field + " in class " + clazz.getName());
            }

            if (!field.getAnnotation(Column.class).unique()) {
                throw new RuntimeException("Field " + field + " in class " + clazz.getName() + " isn't unique");
            }
        }
    }

    private void checkIfTablesExistsAndCreate(Set<Class<?>> entities) {
        for (Class clazz:
             entities) {
            if (!checkIfTableExists(clazz)) {
                createTable(clazz);
            }
        }
    }

    private boolean checkIfTableExists(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String sql = String.format(CHECK_TABLE_SQL_PATTERN, tableName);

        Connection connection = connectionFactory.getConnection();

        Statement statement = null;
        ResultSet resultSet = null;
        boolean ifExists = false;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            ifExists = resultSet.getBoolean("exists");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ifExists;
    }

    private void createTable(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String idField = findIdField(clazz);
        String fields = String.valueOf(createFieldsLine(clazz));

        String sql = String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, SEQ_NAME, fields);

        Connection connection = connectionFactory.getConnection();

        Statement statement = null;

        try {
            statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String findIdField(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String id = null;
        for (Field field:
                fields) {
            if (field.isAnnotationPresent(ID.class)) {
                id = field.getName();
                return id;
            }
        }

        return id;
    }

    //добавить проверку на nullable и unique
    private StringBuilder createFieldsLine(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldsLine = new StringBuilder("");

        for (Field field:
             fields) {
            if (field.isAnnotationPresent(Column.class)) {
                fieldsLine.append(field.getName() + " " +  classToSql.get(field.getType()) + ", ");
            }
        }

        fieldsLine.delete(fieldsLine.length() - 2, fieldsLine.length() - 1);

        return fieldsLine;
    }

//    private String checkNullable(Field field) {
//        if (field.getAnnotation(Column.class).nullable()) {
//            return "NOT NULL";
//        } else {
//            return "";
//        }
//    }
//
//    private String checkUnique(Field field) {
//        if (field.getAnnotation(Column.class).unique()) {
//            return "UNIQUE";
//        } else {
//            return "";
//        }
//    }

    private void initializeInsertByClassPattern(Set<Class<?>> entities) {
        entities.stream().forEach(x -> putInsertPatternToMap(x));
    }

    private void putInsertPatternToMap(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String insertField = String.valueOf(createFieldsLineForInsert(clazz));
        String values = String.valueOf(createValuesLineForInsert(clazz));
        String idFieldName = findIdField(clazz);

        String sql = String.format(INSERT_SQL_PATTERN, tableName, insertField, values, idFieldName);

        insertByClassPattern.put(clazz.getName(), sql);
    }

    private StringBuilder createFieldsLineForInsert(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldsLine = new StringBuilder("");

        for (Field field:
                fields) {
            if (field.isAnnotationPresent(Column.class)) {
                fieldsLine.append(field.getName() + ", ");
            }
        }

        fieldsLine.delete(fieldsLine.length() - 2, fieldsLine.length() - 1);

        return fieldsLine;
    }

    private StringBuilder createValuesLineForInsert(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder fieldsLine = new StringBuilder("");

        for (Field field:
                fields) {
            if (field.isAnnotationPresent(Column.class)) {
                fieldsLine.append(insertPatternByClass.get(String.valueOf(field.getType())) + ", ");
            }
        }

        fieldsLine.delete(fieldsLine.length() - 2, fieldsLine.length() - 1);

        return fieldsLine;
    }
}