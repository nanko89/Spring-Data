import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;

public class Main {

    private static final String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";

    public static final String DB_NAME = "minions_db";

    public static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static Connection connection;

    public static void main(String[] args) throws SQLException, IOException {

        connection = getConnection();

        chooseExercise();

    }

    private static void chooseExercise() throws SQLException, IOException {

        System.out.println("Enter exercise number:");

        int number = Integer.parseInt(reader.readLine());

        switch (number) {
            case 2:
                exerciseTwo();
                chooseExercise();
                break;
            case 3:
                exerciseThree();
                chooseExercise();
                break;
            case 4:
                exerciseFour();
                chooseExercise();
                break;
            case 5:
                exerciseFive();
                chooseExercise();
                break;
            case 6:
                exerciseSix();
                chooseExercise();
                break;
            case 7:
                exerciseSeven();
                chooseExercise();
                break;
            case 8:
                exerciseEight();
                chooseExercise();
                break;
            case 9:
                exerciseNine();
                chooseExercise();
                break;
            default:
                System.out.println("No exist exercise");
        }
    }

    private static void exerciseTwo() throws SQLException {

        String query = "SELECT v.name, COUNT(DISTINCT mv.minion_id) AS c_minion " +
                "FROM villains AS v " +
                "JOIN " +
                "minions_villains mv on v.id = mv.villain_id " +
                "GROUP BY v.name " +
                "HAVING c_minion > ?;";

        PreparedStatement preparedStatement = connection
                .prepareStatement(query);

        preparedStatement.setInt(1, 15);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.printf("%s %d %n",
                    resultSet.getString(1), resultSet.getInt(2));
        }
    }

    private static void exerciseThree() throws IOException, SQLException {

        System.out.println("Enter villain id:");

        int villainID = Integer.parseInt(reader.readLine());

        String villainName = findValidEntityNameById("villains", villainID);

        if (villainName != null) {
            System.out.println("Villain: " + villainName);

            List<String> minions = getAllMinionsByVillainId(villainID);

            minions.forEach(System.out::println);
        } else {
            System.out.println("No villain with ID " + villainID + " exists in the database");
        }
    }

    private static void exerciseFour() throws IOException, SQLException {
        System.out.println("Enter minions info: (name, age, town with whitespace)");
        String[] minionsInfo = reader.readLine().split(" ");

        System.out.println("Enter villains name:");
        String villainName = reader.readLine();

        String minionName = minionsInfo[0];
        int minionAge = Integer.parseInt(minionsInfo[1]);
        String townName = minionsInfo[2];

        int townId = getEntityIdByName(townName, "towns");

        if (townId == 0) {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO towns(name) " +
                            "VALUES (?);");
            preparedStatement.setString(1, townName);
            preparedStatement.execute();
            System.out.printf("Town %s was added to the database.%n", townName);
            townId = getEntityIdByName(townName, "towns");
        }

        int minionId = getEntityIdByName(minionName, "minions");

        if (minionId == 0) {

            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO minions(name, age, town_id) " +
                            "VALUE (?,?,?);");
            preparedStatement.setString(1, minionName);
            preparedStatement.setInt(2, minionAge);
            preparedStatement.setInt(3, townId);
            preparedStatement.execute();
        } else {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("UPDATE minions\n" +
                            "SET town_id =  (?), age = (?)\n" +
                            "WHERE id = (?);");
            preparedStatement.setInt(1, townId);
            preparedStatement.setInt(2, minionAge);
            preparedStatement.setInt(3, minionId);
        }


        int villainId = getEntityIdByName(villainName, "villains");

        if (villainId == 0) {

            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO villains(name, evilness_factor) VALUES (?,?);");
            stmt.setString(1, villainName);
            stmt.setString(2,"evil");
            stmt.execute();
            villainId = getEntityIdByName(villainName,"villains");
            System.out.printf("Villain %s was added to the database%n", villainName);
        }

        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO minions_villains " +
                        "VALUES (?,?);");

        preparedStatement.setInt(1, minionId);
        preparedStatement.setInt(2, villainId);
        System.out.printf("Successfully added %s to be minion of %s%n", minionName, villainName);
    }

    private static void exerciseFive() throws IOException, SQLException {
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

    private static void exerciseSix() throws IOException, SQLException {
        System.out.println("Enter villain id:");
        int villainsId = Integer.parseInt(reader.readLine());

        int affectedRows = deleteMinionsByVillainId(villainsId);

        if (affectedRows != 0) {

            String villainName = findValidEntityNameById("villains", villainsId);

            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE  FROM  villains WHERE  id = ?;");

            preparedStatement.setInt(1, villainsId);

            preparedStatement.executeUpdate();

            System.out.println(villainName + " was deleted");
            System.out.println(affectedRows + " minions released");
        } else {
            System.out.println("No such villain was found");
        }

    }

    private static void exerciseSeven() throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name FROM minions;");

        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayDeque<String> minionsName = new ArrayDeque<>();

        while (resultSet.next()) {
            minionsName.add(resultSet.getString("name"));
        }
//        int count = 0;
//        for (int i = 0; i < minionsName.size()/2; i++) {
//            System.out.println(minionsName.get(count));
//            System.out.println(minionsName.get(minionsName.size()-1 -count));
//            count++;
//        }
//
//        if(minionsName.size() % 2 != 0){
//            System.out.println(minionsName.get(minionsName.size() /2 + 1));
//        }

        while (!minionsName.isEmpty()) {

            System.out.println(minionsName.removeFirst());

            if (!minionsName.isEmpty()) {
                System.out.println(minionsName.removeLast());
            } else {
                break;
            }
        }
    }

    private static void exerciseEight() throws IOException, SQLException {
        //TODO: You have to call this procedure before start eight and nine exercise

//        DELIMITER &&

//        CREATE PROCEDURE usp_get_older (minions_id INT)
//        BEGIN
//        UPDATE minions
//        SET age = age + 1
//        WHERE id = minions_id;
//        END &&

//        DELIMITER ;

        System.out.println("Enter minions ids:(with whitespace)");
        int[] minionsId = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int i = 0; i < minionsId.length; i++) {
            incrementAgeOfMinions(minionsId[i]);
            setLowerCaseName(minionsId[i]);
        }

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT name, age FROM  minions; ");

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " "
                    + resultSet.getInt("age"));
        }


    }

    private static void exerciseNine() throws IOException, SQLException {


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

    private static void setLowerCaseName(int id) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE minions  " +
                        "SET name = LCASE(name) WHERE id = ?;");

        preparedStatement.setInt(1, id);
        preparedStatement.execute();
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

    private static int deleteMinionsByVillainId(int villainsId) throws SQLException {

        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM  minions_villains WHERE villain_id =?");

        preparedStatement.setInt(1, villainsId);

        return preparedStatement.executeUpdate();
    }

    private static int getEntityIdByName(String entityName, String tableName) throws SQLException {

        String query = String.format("SELECT id FROM %s WHERE name = ?", tableName);

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, entityName);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next() ? resultSet.getInt(1) : 0;

    }

    private static List<String> getAllMinionsByVillainId(int villainID) throws SQLException {
        List<String> minions = new ArrayList<>();

        String query = "SELECT m.name, m.age FROM  minions m " +
                "JOIN minions_villains mv on m.id = mv.minion_id " +
                "WHERE mv.villain_id = ?; ";

        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setInt(1, villainID);

        ResultSet resultSet = preparedStatement.executeQuery();

        int counter = 1;

        while (resultSet.next()) {
            minions.add(String.format("%d. %s %d",
                    counter++, resultSet.getString(1), resultSet.getInt(2)));
        }
        return minions;
    }

    private static String findValidEntityNameById(String tableNames, int entityID) throws SQLException {

        String query = String.format("SELECT name FROM %s WHERE id = ?;", tableNames);

        PreparedStatement preparedStatement = connection
                .prepareStatement(query);

        preparedStatement.setInt(1, entityID);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            return null;
        }
        return resultSet.getString("name");
    }

    private static Connection getConnection() throws SQLException, IOException {

        System.out.println("Enter user:");
        String user = reader.readLine();

        System.out.println("Enter password:");
        String password = reader.readLine();

        Properties props = new Properties();

        props.setProperty("user", user);
        props.setProperty("password", password);

        return DriverManager
                .getConnection(CONNECTION_STRING + DB_NAME, props);
    }
}

