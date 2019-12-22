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

    public void createConstructor() throws IOException {
        Stage primaryStage = new Stage();

        AnchorPane root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("КОНСТРУКТОР");
        int x0 = 270;
        int y0 = 25;
        Grid.setGrid(x0, y0, topologyHeight.getValue(), topologyWidth.getValue());
        Grid.initGrid();
/*        for (Line line : Grid.getLineList()) {
            root.getChildren().add(line);
        }*/

        GridPane gridpane = new GridPane();
        gridpane.setGridLinesVisible(true);
        gridpane.setTranslateX(x0);
        gridpane.setTranslateY(y0);
        GridElement gridElement = new GridElement();
        gridpane.setPrefWidth(topologyWidth.getValue() * gridElement.getWidth());
        gridpane.setPrefWidth(topologyHeight.getValue() * gridElement.getHeight());
        for(int i = 0; i < topologyWidth.getValue(); i++)
            gridpane.getColumnConstraints().add(new ColumnConstraints(40)); // column 0 is 40 wide
         for(int i = 0; i < topologyHeight.getValue() + 1; i++)
        gridpane.getRowConstraints().add(new RowConstraints(40)); // column 0 is 40 wide

        root.getChildren().add(gridpane);
        MoveController moveController = new MoveController();
        moveController.go(root);

        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }
}
