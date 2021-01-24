import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;

public class LoginPageController {

    @FXML
    private PasswordField PasswordInput;
    @FXML
    private Button LogButton;
    @FXML
    private AnchorPane Pane;
    @FXML
    private TextField LoginInput;

    private LoginPage loginPageManager;

    public void initialize() {
        initButton();
    }

    public void setLoginPageManager(LoginPage loginPage) {
        loginPageManager = loginPage;
    }

    private void initButton() {
        LogButton.setOnMouseClicked( value ->
            loginPageManager.tryToLogToDatabase(LoginInput.getText(), PasswordInput.getText()));
    }

}
