package Managers;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

import oracle.jdbc.pool.OracleDataSource;


public class DatabaseConnector {

    private static final String PORT = "1521";
    private static final String HOST = "ora4.ii.pw.edu.pl";
    private static final String SERV = "pdb1.ii.pw.edu.pl";

    private Connection connection;

    public DatabaseConnector(String user, String password) throws SQLException {
        setConnection(user, password);
    }

    public void endConnection() throws SQLException {
        connection.close();
    }

    public Collection<String> makeRestaurantsQuery() throws SQLException {

        Collection<String> restaurants = new ArrayList<>();
        Statement stat = connection.createStatement();
        ResultSet rs = stat.executeQuery("select name from restaurants");

        while (rs.next())
            restaurants.add(rs.getString(1));

        rs.close();
        stat.close();

        return restaurants;
    }

    public Collection<String> makeMenuForRestaurantQuery(String restaurant) throws SQLException {

        Collection<String> menu = new ArrayList<>();

        String restaurantID = getOneRecordWithSpecifiedValue(
                "SELECT restaurant_id FROM restaurants WHERE name = ?", restaurant
        );

        PreparedStatement preparedStatement = connection
            .prepareStatement("select name from meals where restaurant_id = ?");

        preparedStatement.setString(1, restaurantID);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next())
            menu.add(rs.getString(1));

        rs.close();

        return menu;
    }

    public String orderMeals(String[] meals) throws SQLException, RuntimeException {

        String orderID;

        connection.setAutoCommit(false);

        String restaurantID = validateMeals(meals);
        String tableID = getOneRecordWithSpecifiedValue(
                "SELECT table_id FROM tables WHERE restaurant_id = ?", restaurantID
        );

        PreparedStatement makeOrderStatement = connection
                .prepareStatement("insert into orders (restaurant_id, table_id,  order_status_id) values (?, ?, 1) ");

        makeOrderStatement.setString(1, restaurantID);
        makeOrderStatement.setString(2, tableID);
        makeOrderStatement.executeQuery();

        orderID = getOrderID();
            
        PreparedStatement addMealsToOrder = connection
                .prepareStatement("insert into meals_orders (order_id, meal_id, quantity) values (?, ?, 1)");

        addMealsToOrder.setString(1, orderID);
            
        for (String meal : meals) {

            String mealID = getOneRecordWithSpecifiedValue(
                    "SELECT meal_id FROM meals WHERE name = ?", meal
            );

            addMealsToOrder.setString(2, mealID);
            addMealsToOrder.executeQuery();
        }

        connection.commit();

        return orderID;
    }

    private String validateMeals(String[] meals) throws SQLException, RuntimeException {

        String restaurantID = "";

        for (String meal : meals) {

            String mealRestID = getOneRecordWithSpecifiedValue(
                    "SELECT restaurant_id FROM meals WHERE name = ?", meal);

            if (restaurantID.isEmpty())
                restaurantID = mealRestID;
            else if (!mealRestID.equals(restaurantID))
                throw new RuntimeException("Attempt to order meals from different restaurants.");
        }

        return restaurantID;
    }

     private String getOrderID() throws SQLException {
         PreparedStatement preparedStatement = connection
                 .prepareStatement("SELECT order_id FROM orders WHERE order_id = (select max(order_id) from orders)");
         ResultSet rs = preparedStatement.executeQuery();
         rs.next();

         return rs.getString(1);
     }

    private String getOneRecordWithSpecifiedValue(String query, String value) throws SQLException {

        PreparedStatement preparedStatement = connection
                .prepareStatement(query);
        preparedStatement.setString(1, value);
        ResultSet rs = preparedStatement.executeQuery();
        rs.next();

        return rs.getString(1);
    }

    private void setConnection(String user, String password) throws SQLException {

        String connectionString = String.format(
                "jdbc:oracle:thin:%s/%s@//%s:%s/%s",
                user, password, HOST, PORT, SERV);

        OracleDataSource ods = new OracleDataSource();
        ods.setURL(connectionString);
        connection = ods.getConnection();
    }

}
