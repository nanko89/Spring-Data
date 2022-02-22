import entitties.User;
import orm.EntityManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import static orm.MyConnector.createConnection;
import static orm.MyConnector.getConnection;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException {
        createConnection("root", "0a953cf8", "custom-orm");
        Connection connection = getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);

//        userEntityManager.doCreate(User.class);
        User user = new User("gosho", 20, LocalDate.now());
//        user.setId(2);
        userEntityManager.persist(user);
    }
}
