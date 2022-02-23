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
import java.util.*;
import java.util.stream.Collectors;

public class EntityManager<E> implements DbContext<E> {
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void doAlter(E entity) throws SQLException {
        String tableName = getTableName(entity.getClass());

        String addColumnStatement = addColumnStatementsForNewFields(entity.getClass());

        String queryDoAlter = String.format("ALTER TABLE %s %s;", tableName,addColumnStatement );

        statementExecute(queryDoAlter);
    }

    @Override
    public void doCreate(Class<E> entityClass) throws SQLException {
        String tableName = getTableName(entityClass);
        
        String fieldsWithTypes = getSQLFieldsWithTypes(entityClass);
        
        String queryCreteTable = String.format
                ("CREATE TABLE %s " +
                        "(id INT PRIMARY KEY AUTO_INCREMENT," +
                        "%s);", tableName, fieldsWithTypes);
        
        statementExecute(queryCreteTable);
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
    public Iterable<E> find(Class<E> table) throws SQLException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {

        return find(table,null);
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) throws SQLException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {

        String tableName = getTableName(table);

        String selectFirstQuery = String.format
                ("SELECT * FROM %s %s;", tableName, where != null ? "WHERE " + where : "");

        PreparedStatement preparedStatement = connection.prepareStatement(selectFirstQuery);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<E> result = new ArrayList<>();

        while (resultSet.next()){
            E entity = table.getDeclaredConstructor().newInstance();
            fillingEntity(table, resultSet, entity);
            result.add(entity);
        }
        return result;
    }

    @Override
    public E findFirst(Class<E> table) throws SQLException, NoSuchMethodException, 
            IllegalAccessException, InvocationTargetException, InstantiationException {
        return findFirst(table,null);
    }

    @Override
    public E findFirst(Class<E> table, String where) throws SQLException, NoSuchMethodException, 
            InvocationTargetException, InstantiationException, IllegalAccessException {
        
        String tableName = getTableName(table);
        
        String selectFirstQuery = String.format
                ("SELECT * FROM %s %s LIMIT 1;", tableName, where != null ? "WHERE " + where : "");
        
        PreparedStatement statement = connection.prepareStatement(selectFirstQuery);
        
        ResultSet resultSet = statement.executeQuery();

        resultSet.next();

        E entity = table.getDeclaredConstructor().newInstance();
        
        fillingEntity(table, resultSet, entity);
        
        return entity;
    }

    private boolean doUpdate(E entity, Field primaryKey) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());

        String tableFieldAndValue = String.join(", ", getColumnNameAndValue(entity));

        String updateQuery = String.format("UPDATE %s SET %s WHERE id = %s;",
                tableName, tableFieldAndValue, primaryKey.get(entity));

        return statementExecute(updateQuery);
    }

    private boolean doInsert(E entity, Field primaryKey) throws SQLException, IllegalAccessException {
        
        String tableName = getTableName(entity.getClass());
        
        String tableFields = String.join(", ", getColumnsWithoutId(entity.getClass()));

        String tableValues = String.join(", ", getColumnsValuesWithoutId(entity));

        String insertQuery = String.format("INSERT INTO %s (%s) VALUES (%s);",
                tableName, tableFields, tableValues);

        return statementExecute(insertQuery);
    }


    @Override
    public boolean delete(E toDelete) throws IllegalAccessException, SQLException {
        String tableName = getTableName(toDelete.getClass());

        Field idColumn = getIdColumn(toDelete.getClass());

        String idColumnName = idColumn.getAnnotationsByType(Column.class)[0].name();

        idColumn.setAccessible(true);

        Object idColumnValue = idColumn.get(toDelete);

        String queryDelete = String.format("DELETE FROM %s WHERE %s = %s",
                tableName, idColumnName, idColumnValue );

        return statementExecute(queryDelete);
    }

    private Field getIdColumn(Class<?> entity) {
        return Arrays
                .stream(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity doesn't have primary key"));
    }

    private List<String> getColumnsValuesWithoutId(E entity) throws IllegalAccessException {

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
        return  values;
    }

    private List<String> getColumnsWithoutId(Class<?> aClass) {
        return Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> f.getAnnotationsByType(Column.class))
                .map(a -> a[0].name())
                .collect(Collectors.toList());
    }

    private List<String> getColumnNameAndValue(E entity) throws IllegalAccessException {
//        Class<?> aClass = entity.getClass();
//
//        List<Field> fields = Arrays.stream(aClass.getDeclaredFields())
//                .filter(f -> !f.isAnnotationPresent(Id.class))
//                .filter(f -> f.isAnnotationPresent(Column.class))
//                .collect(Collectors.toList());
//
//        List<String> values = new ArrayList<>();
//
//        for (Field field : fields) {
//            field.setAccessible(true);
//            Object o = field.get(entity);
//            String fieldName = field.isAnnotationPresent(Id.class)
//                    ? "id" : field.getAnnotation(Column.class).name();
//            String value;
//            if (o instanceof String || o instanceof LocalDate) {
//                value = ("'" + o + "'");
//            } else {
//                value = o.toString();
//            }
//            values.add(String.format("%s  = %s", fieldName, value));
//        }

        List<String> tableFields = getColumnsWithoutId(entity.getClass());
        List<String> tableValues = getColumnsValuesWithoutId(entity);

        List<String> setStatement = new ArrayList<>();

        for (int i = 0; i < tableFields.size(); i++) {
            setStatement.add(tableFields.get(i) + " = " + tableValues.get(i));
        }
        return  setStatement;
    }

    private String getTableName(Class<?> aClass) {
        Entity[] annotationsByType = aClass.getAnnotationsByType(Entity.class);

        if (annotationsByType.length == 0) {
            throw new UnsupportedOperationException("Class must be Entity");
        }
        return annotationsByType[0].name();
    }

    private String getSQLFieldsWithTypes(Class<E> entityClass) {

        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .map(f -> {
                    String fieldName = f.getAnnotationsByType(Column.class)[0].name();
                    Class<?> type = f.getType();

                    String sqlType = getSqlType(type);

                    return fieldName + " " + sqlType;
                }).collect(Collectors.joining(", "));
    }

    private String getSqlType(Class<?> type) {
        String sqlType = "";
        if (type == Integer.class || type == int.class) {
            sqlType = "INT";
        } else if (type == String.class) {
            sqlType = "VARCHAR(200)";
        } else if (type == LocalDate.class) {
            sqlType = "DATE";
        }else if (type == Long.class || type == long.class){
            sqlType = "LONG";
        }
        return sqlType;
    }

    private void fillingEntity(Class<E> table, ResultSet resultSet, E entity) throws SQLException,
            IllegalAccessException {
        Field[] fields = table.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            fillField(field, resultSet, entity);
        }
    }

    private void fillField(Field declaredField, ResultSet resultSet, E entity) throws SQLException,
            IllegalAccessException {

        Class<?> fieldType = declaredField.getType();

        String fieldName = declaredField.getAnnotationsByType(Column.class)[0].name();

        if (fieldType == int.class || fieldType == Integer.class) {
            int value = resultSet.getInt(fieldName);

            declaredField.set(entity, value);
        } else if (fieldType == long.class || fieldType == Long.class) {
            long value = resultSet.getLong(fieldName);

            declaredField.set(entity, value);
        } else if (fieldType == LocalDate.class) {
            LocalDate value = LocalDate.parse(resultSet.getString(fieldName));

            declaredField.set(entity, value);
        } else {
            String value = resultSet.getString(fieldName);

            declaredField.set(entity, value);
        }
    }

    private String addColumnStatementsForNewFields(Class<?> aClass) throws SQLException {
        Set<String> sqlColumns = getSQLColumnName(aClass);

        List<Field> fields = Arrays.stream(aClass.getDeclaredFields())
                .filter(f -> !f.isAnnotationPresent(Id.class))
                .filter(f -> f.isAnnotationPresent(Column.class))
                .collect(Collectors.toList());

        List<String> result = new ArrayList<>();


        for (Field field : fields) {
            String fieldName = field.getAnnotationsByType(Column.class)[0].name();

            if (sqlColumns.contains(fieldName)){
                continue;
            }

            String sqlType = getSqlType(field.getType());
           result.add( String.format("ADD COLUMN %s %s", fieldName, sqlType));
        }

       return String.join(", ", result);
    }

    private Set<String> getSQLColumnName(Class<?> aClass) throws SQLException {
        String schemaQuery = "SELECT c.COLUMN_NAME FROM information_schema.COLUMNS c " +
                "WHERE c.TABLE_SCHEMA  = 'custom-orm'" +
                " AND COLUMN_NAME != 'id' " +
                " AND TABLE_NAME = 'users';";

        PreparedStatement preparedStatement = connection.prepareStatement(schemaQuery);

        ResultSet resultSet = preparedStatement.executeQuery();

        Set<String> result = new HashSet<>();
        while (resultSet.next()){
            String columnName = resultSet.getString("COLUMN_NAME");
            result.add(columnName);
        }

        return result;
    }

    private boolean statementExecute(String insertQuery) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(insertQuery);

        return statement.execute();
    }
}
