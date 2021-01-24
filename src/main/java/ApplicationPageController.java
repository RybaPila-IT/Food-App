import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class ApplicationPageController {

    @FXML
    private Button RestaurantsButton;
    @FXML
    private Button FindMenuButton;
    @FXML
    private Button MakeOrderButton;
    @FXML
    private TextField RestaurantNameField;
    @FXML
    private TextField MealsField;

    private ApplicationPage manager;

    public void initialize() {
        setRestaurantsButton();
        setFindMenuButton();
    }

    public void setApplicationPageManager(ApplicationPage manager) {
        this.manager = manager;
    }


    private void setRestaurantsButton() {
        RestaurantsButton.setOnMouseClicked(event -> manager.showRestaurants());
    }

    private void setFindMenuButton() {
        FindMenuButton.setOnMouseClicked(event -> manager.showMenu(RestaurantNameField.getText()));
    }


}
