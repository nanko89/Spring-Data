package ExerciseSix;

import ConnectionManager.ConnectorManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static ExeciseFour.Main.findExistEntityIdByName;

public class Main {
    public static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        connection = ConnectorManager
                .getConnection("minions_db");

        System.out.println("Enter villain id:");

        int villainId = Integer.parseInt(reader.readLine());

        String villainName = findExistEntityNameById(villainId, "villains");

        if (villainName == null){
            System.out.println("No such villain was found");
        }else {
            int releaseMinion = releasesMinionsFromVillain(villainId);
            removeVillain(villainId);
            System.out.println(villainName + " was deleted");
            System.out.println(releaseMinion + " minions released");
        }
    }

    private static String findExistEntityNameById(int entityId, String tableName) throws SQLException {
        String query = String.format("SELECT name FROM %s WHERE id = ?", tableName);
        PreparedStatement stmt = connection
                .prepareStatement(query);
        stmt.setInt(1,entityId);
        ResultSet resultSet = stmt.executeQuery();

        if (!resultSet.next()){
            return null;
        }
        return resultSet.getString("name");
    }

    private static void removeVillain(int villainId) throws SQLException {
        PreparedStatement stmt = connection
                .prepareStatement("DELETE FROM villains WHERE id = ?");

        stmt.setInt(1,villainId);
        stmt.execute();
    }

    private static int releasesMinionsFromVillain(int villainId) throws SQLException {
        PreparedStatement stmt = connection
                .prepareStatement("DELETE FROM minions_villains WHERE villain_id = ? ;");

        stmt.setInt(1,villainId);
        return stmt.executeUpdate();
    }
}
