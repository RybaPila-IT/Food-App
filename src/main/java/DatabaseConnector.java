import java.sql.*;

import oracle.jdbc.pool.OracleDataSource;


public class DatabaseConnector {

    private static final String PORT = "1521";
    private static final String HOST = "ora4.ii.pw.edu.pl";
    private static final String SERV = "pdb1.ii.pw.edu.pl";

    private static final String REST_QUERY = "select name from restaurants";

    private Connection connection;

    public DatabaseConnector(String user, String password) throws SQLException {
        setConnection(user, password);
    }

    public void endConnection() throws SQLException {

        connection.close();
        //System.out.println("Polaczenia z baza danych zamkniete");

    }

    public void makeRestaurantsQuery() throws SQLException {

        System.out.println("Lista restauracji:");

        Statement stat = connection.createStatement(); // Statement przechowujacy polecenie SQL

        // wydajemy zapytanie oraz zapisujemy rezultat w obiekcie typu ResultSet
        ResultSet rs = stat.executeQuery(REST_QUERY);

        System.out.println("---------------------------------");
        // iteracyjnie odczytujemy rezultaty zapytania
        while (rs.next())
            System.out.println(rs.getString(1));
        System.out.println("---------------------------------");

        rs.close();
        stat.close();
    }

    public void makeMenuForRestaurantQuery(String restaurant) throws SQLException {

        System.out.println("Menu z restauracji:");

        try {
            String restaurantID = findRestaurantID(restaurant);

            PreparedStatement preparedStatement = connection
                    .prepareStatement("select name from meals where restaurant_id = ?");

            System.out.println("Menu z restauracji o id " + restaurantID + ":");

            preparedStatement.setString(1, restaurantID);
            ResultSet rs = preparedStatement.executeQuery(); // Wykonaj zapytanie oraz zapamietaj zbior rezultatow

            System.out.println("---------------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1));		}
            System.out.println("---------------------------------");

        } catch (SQLException e) {
            System.err.println("Unable to connect to database. " + e.getMessage());
        }

        Statement stat = connection.createStatement();


    }

    private String findRestaurantID(String restaurant) throws SQLException {

        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT restaurant_id FROM restaurants WHERE name = ?");

        preparedStatement.setString(1, restaurant);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        return rs.getString(1);
    }


    private void setConnection(String user, String password) throws SQLException {

        String connectionString = String.format(
                "jdbc:oracle:thin:%s/%s@//%s:%s/%s",
                user, password, HOST, PORT, SERV);

        //System.out.println(connectionString);

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(connectionString);
        connection = ods.getConnection();

        //DatabaseMetaData meta = connection.getMetaData();
        //System.out.println("Polaczenie do bazy danych nawiazane.");
        //System.out.println("Baza danych:" + " " + meta.getDatabaseProductVersion());

    }

}
