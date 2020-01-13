package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import visualize.Grid;

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
    @FXML
    private Button back_button;

    public void initialize() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
        back_button.setOnAction(event -> {
            if(primaryStage != null) primaryStage = null;
            Stage primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
                root.setOnMousePressed(mouseEvent -> {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                });
                root.setOnMouseDragged(mouseEvent -> {
                    primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                    primaryStage.setY(mouseEvent.getScreenY() - yOffset);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setTitle("МЕНЮ");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
            try {
                this.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
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
