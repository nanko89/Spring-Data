package ConnectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectorManager {

    public static Connection getConnection (String database) throws SQLException, IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String connectionString =  "jdbc:mysql://localhost:3306/";

//        System.out.println("Enter username:");
//        String username = bf.readLine();
//
//        System.out.println("Enter password:");
//        String password = bf.readLine();

        Properties props = new Properties();

        props.setProperty("user", "root");
        props.setProperty("password", "0a953cf8");

        return DriverManager.getConnection(connectionString+database, props);
    }
}
