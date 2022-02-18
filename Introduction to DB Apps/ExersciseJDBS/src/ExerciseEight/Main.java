package ExerciseEight;

import ConnectionManager.ConnectorManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {
    public static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        connection = ConnectorManager
                .getConnection("minions_db");

        System.out.println("Enter minions id(separated by whitespace):");

        int[] minionsId = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        PreparedStatement  stmt = connection
                .prepareStatement("UPDATE minions SET name = LCASE(name), age = age + 1 WHERE id = ?");

        for (int i = 0; i < minionsId.length; i++) {
            stmt.setInt(1, minionsId[i]);
            stmt.execute();
        }


        PreparedStatement preparedStatement = connection.prepareStatement("SELECT name, age FROM minions");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
        }
    }
}
