package ExerciseSeven;

import ConnectionManager.ConnectorManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;

public class Main {
    public static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        connection = ConnectorManager
                .getConnection("minions_db");

        ArrayDeque<String> minions = new ArrayDeque<>();

        PreparedStatement stmt = connection
                .prepareStatement("SELECT name FROM minions;");

        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()){
            minions.add(resultSet.getString("name"));
        }

        while (!minions.isEmpty()){
            System.out.println(minions.removeFirst());
            if (!minions.isEmpty()){
                System.out.println(minions.removeLast());
            }
        }
    }
}
