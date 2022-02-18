package ExerciseThree;

import ConnectionManager.ConnectorManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        connection = ConnectorManager
                .getConnection("minions_db");

        System.out.println("Enter villain id:");
        int villainId = Integer.parseInt(reader.readLine());

        String villainName = findValidEntityById(villainId, "villains");

        if (villainName == null){

            System.out.printf("No villain with ID %d exists in the database.%n",
                    villainId);
        }else {

            String query = "SELECT m.name, m.age " +
                    "FROM " +
                    "minions_villains mv " +
                    "    JOIN " +
                    "minions m on m.id = mv.minion_id " +
                    "WHERE mv.villain_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, villainId);

            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Villain: " + villainName);

            int number = 1;

            while (resultSet.next()){

                String minionName = resultSet.getString("name");
                int minionAge = resultSet.getInt("age");

                 System.out.printf("%d. %s %d%n",number++, minionName,minionAge );
            }
        }
    }

    private static String findValidEntityById(int entityId, String tableName) throws SQLException, IOException {

        String query = String.format("SELECT name FROM %s WHERE id = %d;", tableName,entityId);
        PreparedStatement preparedStatement = connection.prepareStatement(query);


        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()){
            return null;
        }
        return resultSet.getString("name");
    }
}

