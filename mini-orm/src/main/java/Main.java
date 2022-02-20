import entitties.User;
import orm.EntityManager;
import orm.MyConnector;

import java.sql.Connection;
import java.sql.SQLException;

import static orm.MyConnector.createConnection;
import static orm.MyConnector.getConnection;

public class Main {

    public static void main(String[] args) throws SQLException {
        createConnection("root", "0a953cf8", "custom-orm");
        Connection connection = getConnection();

        EntityManager<User> userEntityManager = new EntityManager<>(connection);
    }
}
