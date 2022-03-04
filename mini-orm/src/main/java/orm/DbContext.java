package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface DbContext<E> {

    boolean persist(E entity) throws IllegalAccessException, SQLException;

    void doCreate(Class<E> entityClass) throws SQLException;

    void doAlter(E entity) throws SQLException;

    Iterable<E> find(Class<E> table) throws SQLException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException;

    Iterable<E> find(Class<E> table, String where) throws SQLException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    E findFirst(Class<E> table) throws SQLException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException;

    E findFirst(Class<E> table, String where) throws
            SQLException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException;

    boolean delete(E toDelete) throws IllegalAccessException, SQLException;

}
