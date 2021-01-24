import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationPage {

    private static final String TITLE = "Food-App";
    private static final String APP_PAGE_PATH = "/FXML/ApplicationPage.fxml";

    private Stage stage;
    private DatabaseConnector connector;
    private ApplicationPageController controller;

    public ApplicationPage(DatabaseConnector connector) throws IOException {

        this.connector = connector;

        FXMLLoader loader = new FXMLLoader(getClass().getResource(APP_PAGE_PATH));

        try {

            Parent root = loader.load();
            this.controller = loader.getController();
            Scene appScene = new Scene(root);
            this.stage = new Stage();
            this.stage.setScene(appScene);
            this.stage.setTitle(TITLE);
            this.stage.show();

        } catch (IOException e) {
            System.err.println("Error has occured in App class. Unable to load FXML file with main page.");
            System.err.println("Please, check whether the file " + APP_PAGE_PATH + " exists or is corrupted");
            throw new RuntimeException("Unable to load ApplicationPage");
        }


    }




}
