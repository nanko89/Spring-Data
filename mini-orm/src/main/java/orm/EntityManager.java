package orm;

import annotations.Column;
import annotations.Entity;
import annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException, SQLException {
        Field primaryKey = getIdColumn(entity.getClass());
        primaryKey.setAccessible(true);

        Object value = primaryKey.get(entity);

        if (value == null || (long) value <= 0) {
            return doInsert(entity, primaryKey);
        }
        return doUpdate(entity, primaryKey);
    }

    @Override
    public Iterable<E> find(Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(table);
        String selectFirstQuery = String.format
                ("SELECT * FROM %s", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(selectFirstQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        //TODO:Finish method
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String tableName = getTableName(table);
        String selectFirstQuery = String.format
                ("SELECT * FROM %s LIMIT 1", tableName);
        ResultSet resultSet = connection.prepareStatement(selectFirstQuery).executeQuery();
        E entity = table.getDeclaredConstructor().newInstance();
        resultSet.next();
        fillingEntity(table,resultSet,entity);
        return entity;
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String tableName = getTableName(table);
        String selectFirstQuery = String.format
                ("SELECT * FROM %s %s LIMIT 1", tableName, where != null ? "WHERE" + where : "");
        ResultSet resultSet = connection.prepareStatement(selectFirstQuery).executeQuery();
        E entity = table.getDeclaredConstructor().newInstance();
        resultSet.next();
        fillingEntity(table,resultSet,entity);
        return entity;
    }

    private boolean doUpdate(E entity, Field primaryKey) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());

        String tableFieldAndValue = getColumnNameAndValue(entity);

        String updateQuery = String.format("UPDATE %s SET %s WHERE id = %s", tableName,tableFieldAndValue,primaryKey.get(entity) );

        return connection.prepareStatement(updateQuery).execute();
    }

    private boolean doInsert(E entity, Field primaryKey) throws SQLException, IllegalAccessException {
        String tableName = getTableName(entity.getClass());

        String tableFields = getColumnsWithoutId(entity.getClass());

        String tableValues = getColumnsValuesWithoutId(entity);

        String insertQuery = String.format("INSERT INTO %s (%s) VALUES (%s)",
                tableName, tableFields, tableValues);

        return connection.prepareStatement(insertQuery).execute();
    }

    private Field getIdColumn(Class<?> entity) {
        return Arrays
                .stream(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity doesn't have primary key"));
    }

    private String getColumnsValuesWithoutId(E entity) throws IllegalAccessException {

        List<Field> fields = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());

        List<String> values = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(entity);

            if (o instanceof String || o instanceof LocalDate) {
                values.add("'" + o + "'");
            } else {
                values.add(o.toString());
            }
        }
        return String.join(", ", values);
    }

    private String getColumnsWithoutId(Class<?> aClass) {
        return Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> f.getAnnotationsByType(Column.class))
                .map(a -> a[0].name())
                .collect(Collectors.joining(", "));
    }

    private String getColumnNameAndValue(E entity) throws IllegalAccessException {
        Class<?> aClass = entity.getClass();

        List<Field> fields = Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());

        List<String> values = new ArrayList<>();

        for (Field field : fields) {
            field.setAccessible(true);
            Object o = field.get(entity);
            String fieldName = field.isAnnotationPresent(Id.class)
                    ? "id" : field.getAnnotation(Column.class).name();
            String value;
            if (o instanceof String || o instanceof LocalDate) {
                value = ("'" + o + "'");
            } else {
                value = o.toString();
            }
            values.add(String.format("%s  = %s", fieldName,value));
        }
        return String.join(", ", values);
    }

    private String getTableName(Class<?> aClass) {
        Entity[] annotationsByType = aClass.getAnnotationsByType(Entity.class);

        if (annotationsByType.length == 0) {
            throw new UnsupportedOperationException("Class must be Entity");
        }
        return annotationsByType[0].name();
    }

    private void fillingEntity(Class<E> table, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        Field[] fields = Arrays.stream(table.getDeclaredFields())
                .toArray(Field[]::new);
        for (Field field : fields) {
            field.setAccessible(true);
            fillField(field,resultSet,entity);
        }
    }

    private void fillField(Field field, ResultSet resultSet, E entity) throws SQLException, IllegalAccessException {
        if (field.getType() == int.class || field.getType() == long.class ){
            field.set(entity,resultSet.getInt(field.getName()));
        }else if (field.getType() == LocalDate.class){
            field.set(entity, LocalDate.parse(resultSet.getString(field.getName())));
        }else {
            field.set(entity, resultSet.getString(field.getName()));
        }
    }
}
