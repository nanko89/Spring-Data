package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "diablo";
    public static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {


        connection = createConnection();

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT user_name, first_name, last_name, COUNT( ug.game_id) AS count_game " +
                        "FROM users " +
                        "         join " +
                        "     users_games ug on users.id = ug.user_id " +
                        "WHERE user_name = ? " +
                        "GROUP BY user_name;");


        System.out.println("Enter user_name:");
        String user_name = reader.readLine();
        preparedStatement.setString(1, user_name);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String user = resultSet.getString(1);
            String first_name = resultSet.getString(2);
            String last_name = resultSet.getString(3);
            int count = resultSet.getInt(4);

            System.out.printf("User: %s%n" +
                    "%s %s has played %d games%n", user, first_name, last_name, count);
        } else {
            System.out.println("No such user exists");
        }

    }

    private static Connection createConnection() throws IOException, SQLException {
            System.out.println("Enter user:");
            String user = reader.readLine();
            System.out.println("Enter password:");
            String password = reader.readLine();

            Properties properties = new Properties();
            properties.setProperty("user", user);
            properties.setProperty("password", password);

            return DriverManager
                    .getConnection(CONNECTION_STRING + DB_NAME, properties);

        
    }
}