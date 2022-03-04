package ExeciseFour;

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

        System.out.println("Enter minions info:(name,age,town name with whitespace):");
        String[] infoMinion = reader.readLine().split(" ");

        System.out.println("Enter villains name:");
        String villainName = reader.readLine();

        String minionName = infoMinion[0];
        int minionAge = Integer.parseInt(infoMinion[1]);
        String townName = infoMinion[2];

        int town_id = findExistEntityIdByName(townName, "towns");

        if (town_id == 0){
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO towns(name) VALUES (?);");
            stmt.setString(1, townName);
            stmt.execute();

            town_id = findExistEntityIdByName(townName,"towns");

            System.out.printf("Town %s was added to the database.%n", townName);
        }

        int minionId = findExistEntityIdByName(minionName, "minions");

        if (minionId == 0){
            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO minions(name,age,town_id) VALUES (?,?,?)");
            stmt.setString(1,minionName);
            stmt.setInt(2,minionId);
            stmt.setInt(3, town_id);
        }else {
            PreparedStatement stmt= connection
                    .prepareStatement("UPDATE minions " +
                            "SET age = ?, town_id = ? " +
                            "WHERE id = ?;");

            stmt.setInt(1,minionAge);
            stmt.setInt(2,town_id);
            stmt.setInt(3,minionId);
        }

        int villainId = findExistEntityIdByName(villainName, "villains");

        if (villainId == 0){
            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO villains(name, evilness_factor) VALUES (?,?);");
            stmt.setString(1, villainName);
            stmt.setString(2,"evil");
            stmt.execute();

            villainId = findExistEntityIdByName(villainName,"villains");
            System.out.println("Villain "+ villainName +" was added to the database.");
        }

        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO minions_villains " +
                        "VALUES (?,?);");

        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villainId);

        System.out.printf("Successfully added %s to be minion of %s%n",
                minionName, villainName);

    }

    public static int findExistEntityIdByName(String entityName, String tableName) throws SQLException {

        String query = String.format("SELECT id FROM %s WHERE name = ?", tableName);
        PreparedStatement stmt = connection
                .prepareStatement(query);
        stmt.setString(1,entityName);
        ResultSet resultSet = stmt.executeQuery();

        if (!resultSet.next()){
            return 0;
        }
        return resultSet.getInt("id");

    }
}
