import entitties.Addresses;
import entitties.User;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static orm.MyConnector.createConnection;
import static orm.MyConnector.getConnection;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
        createConnection("root", "0a953cf8", "custom-orm");
        Connection connection = getConnection();

//        EntityManager<User> userEntityManager = new EntityManager<>(connection);
//        userEntityManager.doCreate(User.class);
//        User user2 = new User("gosho", 20, LocalDate.now());
//        User user1 = new User("pepi", 30, LocalDate.now());
//        User user3 = new User("mimi", 40, LocalDate.now());
//
//        user.setId(1);
//        user.setAge(15);
//        userEntityManager.doAlter(user);
//        userEntityManager.persist(user1);
//        userEntityManager.persist(user2);
//        userEntityManager.persist(user3);
//
//        Iterable<User> users = userEntityManager.find(User.class, "id < 5");
//        System.out.println(users.toString());

        EntityManager<Addresses> addressesEntityManager = new EntityManager<>(connection);
        addressesEntityManager.doCreate(Addresses.class);



    }

}
