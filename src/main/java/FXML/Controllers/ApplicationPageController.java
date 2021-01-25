package FXML.Controllers;

import Managers.ApplicationPage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Collection;

public class ApplicationPageController {

    private static final String LABEL_TEXT = "Numer twojego zamówienia to: ";
    private static final String STYLE_SHEET = "/Graphics/LoginPage.css";

    private static final double TABLE_WIDTH = 500.0;

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
        setMakeOrderButton();
    }

    public void setApplicationPageManager(ApplicationPage manager) {
        this.manager = manager;
    }

    public void createPopUpWithOrderID(String orderID) {

        Label label = new Label(LABEL_TEXT + orderID);
        Scene scene = new Scene(label);
        scene.getStylesheets().add(STYLE_SHEET);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void createListPopUp(Collection<String> list, String title) {

        ObservableList<String> details = FXCollections.observableArrayList(list);
        TableView<String> tableView = new TableView<>();
        tableView.setPrefWidth(TABLE_WIDTH);

        TableColumn<String, String> col1 = new TableColumn<>(title);
        col1.setPrefWidth(TABLE_WIDTH - 2);
        tableView.getColumns().add(col1);

        col1.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        tableView.setItems(details);

        StackPane sp = new StackPane(tableView);
        Scene scene = new Scene(sp);
        scene.getStylesheets().add(STYLE_SHEET);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void setRestaurantsButton() {
        RestaurantsButton.setOnMouseClicked(event -> manager.showRestaurants());
    }

    private void setFindMenuButton() {
        FindMenuButton.setOnMouseClicked(event -> manager.showMenu(RestaurantNameField.getText()));
    }

    private void setMakeOrderButton() {
        MakeOrderButton.setOnMouseClicked(event -> manager.makeOrder(MealsField.getText()));
    }

}
