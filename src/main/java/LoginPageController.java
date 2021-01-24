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

    public void initialize() {

        initButton();

    }

    public Parent getPane() {
        return Pane;
    }

    private void initButton() {
        LogButton.setOnMouseClicked( value -> {
        });
    }

}
