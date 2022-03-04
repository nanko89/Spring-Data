package ExerciseTwo;

import ConnectionManager.ConnectorManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        Connection connection =
                ConnectorManager.getConnection("minions_db");

        String query = "SELECT v.name, COUNT(DISTINCT mv.minion_id) AS count_minions " +
                "FROM villains As v " +
                "    JOIN " +
                "minions_villains mv on v.id = mv.villain_id " +
                "GROUP BY mv.villain_id " +
                "HAVING count_minions > 15;";

        PreparedStatement stmt = connection.prepareStatement(query);

        ResultSet resultSet = stmt.executeQuery();

        while (resultSet.next()){
            String name = resultSet.getString("name");
            int countMinions = resultSet.getInt("count_minions");
            System.out.println(name + " " + countMinions);
        }

    }
}
