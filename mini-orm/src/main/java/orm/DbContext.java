package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DbContext<E> {

    boolean persist(E entity) throws IllegalAccessException, SQLException;

    Iterable<E> find(Class<E> table) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;

    Iterable<E> find(Class<E> table, String where);

    E findFirst(Class<E> table) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    E findFirst(Class<E> table, String where) throws
            SQLException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException;

    void doCreate(Class<E> entityClass) throws SQLException;

    void doAlter(E entity) throws SQLException;
}
