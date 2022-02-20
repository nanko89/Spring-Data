package ExerciseFive;

import ConnectionManager.ConnectorManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        connection = ConnectorManager
                .getConnection("minions_db");

        System.out.println("Enter country:");

        String countryName = reader.readLine();

        PreparedStatement preparedStatement = connection.
                prepareStatement("UPDATE towns SET name = UPPER(name) WHERE country = ?");

        preparedStatement.setString(1, countryName);

        int affectedRows = preparedStatement.executeUpdate();

        if (affectedRows == 0) {
            System.out.println("No town names were affected.");

        } else {
            System.out.println(affectedRows + " town names were affected.");

            PreparedStatement preparedStatementTowns = connection
                    .prepareStatement("SELECT name FROM towns WHERE country = ?");

            preparedStatementTowns.setString(1, countryName);

            ResultSet resultSet = preparedStatementTowns.executeQuery();

            List<String> town_name = new ArrayList<>();

            while (resultSet.next()) {
                town_name.add(resultSet.getString("name"));
            }

            System.out.println(town_name);

        }
    }
}
