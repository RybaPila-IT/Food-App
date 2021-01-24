import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginPage {

    private static final String LOGIN_BOARD = "/FXML/LoginPage.fxml";
    private static final String TITLE = "Food-App";

    private final Stage stage;

    private LoginPageController controller;


    public LoginPage(Stage stage) {

        this.stage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource(LOGIN_BOARD));

        try {
            // Attempt to load board layout.
            Parent root = loader.load();
            this.controller = loader.getController();
            this.controller.setLoginPageManager(this);
            Scene loggingScene = new Scene(root);
            this.stage.setScene(loggingScene);
            this.stage.setTitle(TITLE);

        } catch (IOException e) {
            System.err.println("Error has occured in App class. Unable to load FXML file with logging page.");
            System.err.println("Please, check whether the file " + LOGIN_BOARD + " exists or is corrupted");

            throw new RuntimeException("Unable to load LoginPage");
        }

    }

    public void showLoginPage() {
        stage.show();
    }

    public void tryToLogToDatabase(String user, String password) {

        try {
            DatabaseConnector connector = new DatabaseConnector(user, password);
            ApplicationPage application = new ApplicationPage(connector);
            connector.endConnection();

        } catch (SQLException e) {
            System.err.println("Unable to perform proper connection with Database");
        } catch (IOException e) {
            System.err.println("Unable to load FXML file with Application layout");
        }


    }


}
