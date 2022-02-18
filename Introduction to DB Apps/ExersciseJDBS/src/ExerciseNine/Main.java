package ExerciseNine;

import ConnectionManager.ConnectorManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class Main {

    public static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        connection = ConnectorManager
                .getConnection("minions_db");


        System.out.println("Enter minions id:");

        int id = Integer.parseInt(reader.readLine());

        incrementAgeOfMinions(id);

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, age FROM minions\n" +
                "WHERE id = ?;");

        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        System.out.printf("%s %d %n",
                resultSet.getString("name"), resultSet.getInt("age"));
    }

    private static void incrementAgeOfMinions(int id) throws SQLException {
        CallableStatement callableStatement = connection
                .prepareCall("CALL usp_get_older (?)");

        callableStatement.setInt(1, id);

        int affected = callableStatement.executeUpdate();

        if (affected == 0) {
            System.out.println("Not valid id");
        }
    }
}


