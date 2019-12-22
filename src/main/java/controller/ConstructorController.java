package controller;

import animation.MoveController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import visualize.Grid;

import java.io.IOException;

public class ConstructorController {

    @FXML
    private Spinner<Integer> topologyHeight;

    @FXML
    private Spinner<Integer> topologyWidth;

    public void createConstructor() throws IOException {
        Stage primaryStage = new Stage();

        AnchorPane root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("КОНСТРУКТОР");
        int x0 = 270;
        int y0 = 25;
        Grid.setGrid(x0, y0, topologyHeight.getValue(), topologyWidth.getValue());
        for (Line line : Grid.getLineList()) {
            root.getChildren().add(line);
        }

        MoveController moveController = new MoveController();
        moveController.go(root);

        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }
}
