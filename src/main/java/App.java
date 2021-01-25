import Managers.LoginPage;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {

        try {
            LoginPage page = new LoginPage(primaryStage);
            page.showLoginPage();
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
