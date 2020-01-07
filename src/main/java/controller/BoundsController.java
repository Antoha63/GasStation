package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class BoundsController {
    private double xOffset;
    private double yOffset;
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @FXML
    private Spinner<Integer> topologyHeight;
    @FXML
    private Spinner<Integer> topologyWidth;

    @FXML
    private Button okButton;

    @FXML
    private Button closeButton;

    public void initialize() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }


    @FXML
    public void createConstructor() throws IOException {
        ConstructorController constructorController = new ConstructorController();
        constructorController.setBounds(topologyWidth.getValue(), topologyHeight.getValue());
        primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
