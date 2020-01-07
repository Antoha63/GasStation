package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ImitationController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextArea log_list;

    @FXML
    private Label cash_value_label;

    @FXML
    private Label automobileCountLabel;

    @FXML
    private Label fuelValueLabel;

    @FXML
    private Button closeButton;

    public void initialize() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }
}
