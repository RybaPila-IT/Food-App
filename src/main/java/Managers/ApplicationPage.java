package Managers;

import FXML.Controllers.ApplicationPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;

public class ApplicationPage {

    private static final String ICON_PATH = "/Graphics/pizza.png";
    private static final String TITLE = "Food-App";
    private static final String APP_PAGE_PATH = "/FXML/ApplicationPage.fxml";

    private final Stage stage;
    private final DatabaseConnector connector;
    private final ApplicationPageController controller;

    private LoginPage loginPage;

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
            this.stage.setResizable(false);
            this.stage.show();
            this.stage.getIcons().add(new Image(ICON_PATH));
            this.stage.setOnCloseRequest(value -> closeApplicationPage());

        } catch (IOException e) {
            System.err.println("Error has occured in App class. Unable to load FXML file with main page.");
            System.err.println("Please, check whether the file " + APP_PAGE_PATH + " exists or is corrupted");
            throw new RuntimeException("Unable to load Managers.ApplicationPage");
        }


    }

    public void setLoginPageManager(LoginPage manager) {
        loginPage = manager;
    }

    public void showRestaurants() {

        try {
            Collection<String> restaurants = connector.makeRestaurantsQuery();
            createPopUpWithTable(restaurants, "Restauracje");
        } catch (SQLException e) {
            System.err.println("Unable to get restaurants names. " + e.getMessage());
        }
    }

    public void makeOrder(String meal) {

        try {
            String orderID = connector.orderMeals(meal.split(", "));
            createPopUpWithOrderID(orderID);
        } catch (SQLException e) {
            System.err.println("Unable to make valid order. " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    public void showMenu(String restaurant) {

        try {
            Collection<String> menu = connector.makeMenuForRestaurantQuery(restaurant);
            createPopUpWithTable(menu, "Menu");
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

    private void createPopUpWithOrderID(String orderID) {
        controller.createPopUpWithOrderID(orderID);
    }

    private void createPopUpWithTable(Collection<String> list, String title) {
        controller.createListPopUp(list, title);
    }


}
