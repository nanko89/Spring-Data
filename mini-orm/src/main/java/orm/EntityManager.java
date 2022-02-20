package orm;

import annotations.Entity;
import annotations.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Arrays;

public class EntityManager<E> implements DbContext<E> {
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(E entity) throws IllegalAccessException {
        Field primaryKey = getIdColumn(entity.getClass());
        primaryKey.setAccessible(true);

        Object value = primaryKey.get(entity);

        if (value == null || (long) value <= 0 ){
            return doInsert(entity,primaryKey);
        }
        return doUpdate(entity,primaryKey);
    }

    private boolean doUpdate(E entity, Field primaryKey) {
        return false;
    }

    private boolean doInsert(E entity, Field primaryKey) {
        String tableName = getTableName(entity.getClass());

        getColumnsWithoutId(entity.getClass());
        String insertQuery = String.format("INSERT INTO %s (%s) VALUES ???", tableName);
        return false;
    }

    private void getColumnsWithoutId(Class<?> aClass) {
        Arrays.stream(getClass().getDeclaredFields())
                .filter(f->!f.isAnnotationPresent(Id.class))
                .map()
    }

    private String getTableName(Class<?> aClass) {
        Entity[] annotationsByType = aClass.getAnnotationsByType(Entity.class);

        if (annotationsByType.length == 0){
            throw new UnsupportedOperationException("Class must be Entity");
        }
        return annotationsByType[0].name();
    }

    @Override
    public Iterable<E> find(Class<E> table) {
        return null;
    }

    @Override
    public Iterable<E> find(Class<E> table, String where) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table) {
        return null;
    }

    @Override
    public E findFirst(Class<E> table, String where) {
        return null;
    }

    private Field getIdColumn(Class<?> entity) {
        return Arrays
                .stream(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity doesn't have primary key"));
    }
}
