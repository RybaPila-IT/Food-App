import javafx.animation.ScaleTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ApplicationPage {

    private static final String TITLE = "Food-App";
    private static final String APP_PAGE_PATH = "/FXML/ApplicationPage.fxml";

    private LoginPage loginPage;

    private Stage stage;
    private DatabaseConnector connector;
    private ApplicationPageController controller;

    public ApplicationPage(DatabaseConnector connector) throws IOException {

        this.connector = connector;

        FXMLLoader loader = new FXMLLoader(getClass().getResource(APP_PAGE_PATH));

        try {

            Parent root = loader.load();
            this.controller = loader.getController();
            this.controller.setApplicationPageManager(this);
            Scene appScene = new Scene(root);
            this.stage = new Stage();
            this.stage.setScene(appScene);
            this.stage.setTitle(TITLE);
            this.stage.show();
            this.stage.setOnCloseRequest(value -> closeApplicationPage());

        } catch (IOException e) {
            System.err.println("Error has occured in App class. Unable to load FXML file with main page.");
            System.err.println("Please, check whether the file " + APP_PAGE_PATH + " exists or is corrupted");
            throw new RuntimeException("Unable to load ApplicationPage");
        }


    }

    public void setLoginPageManager(LoginPage manager) {
        loginPage = manager;
    }

    public void showRestaurants() {

        try {
            connector.makeRestaurantsQuery();
        } catch (SQLException e) {
            System.err.println("Unable to get restaurants names. " + e.getMessage());
        }
    }

    public void makeOrder(String meal) {

        try {
            connector.orderMeals(meal.split(", "));
        } catch (RuntimeException | SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void showMenu(String restaurant) {

        try {
            connector.makeMenuForRestaurantQuery(restaurant);
        } catch (SQLException e) {
            System.err.println("Unable to perform query. " + e.getMessage());
        }

    }

    private void closeApplicationPage() {

        try {
            connector.endConnection();
        } catch (SQLException e) {
            System.err.println("Unable to close sql connection. " + e.getMessage());
        }

        stage.close();
        loginPage.showLoginPage();
    }


}
