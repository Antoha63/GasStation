package controller;

import animation.MoveController;
import animation.framePackage.Frame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import visualize.Grid;
import visualize.GridElement;

import java.io.IOException;

public class ConstructorController {

    @FXML
    private Spinner<Integer> topologyHeight;

    @FXML
    private Spinner<Integer> topologyWidth;
    private Grid grid;

    public void createConstructor() throws IOException {
        Stage primaryStage = new Stage();

        AnchorPane root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("КОНСТРУКТОР");
        int x0 = 270;
        int y0 = 25;

        Grid.setGrid(270, 25, topologyWidth.getValue(), topologyHeight.getValue(), root);

        MoveController moveController = new MoveController();
        moveController.go(root);

        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }
}
