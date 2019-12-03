package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import vizualize.Grid;

import java.io.IOException;

public class ConstructorController {
    @FXML
    Spinner topologyWidth;

    @FXML
    Spinner topologyHeight;

    public void initialize(){
//        moveController = new MoveController();
//        this.imageView1 = moveController.getImageView();
//        imageView1.setViewport(moveController.getViewports()[0]);
//        imageView1.setImage(new Image(getClass().getClassLoader().getResource("pics/sprites.png").toString()));
    }

    public void createConstructor() throws IOException {
        Grid grid = new Grid((int) topologyWidth.getValue(),(int) topologyHeight.getValue());
        Stage primaryStage = new Stage();
        // A line in Ox Axis
        Line oxLine1 = new Line(0, 0, 400, 0);

        // Stroke Width
        oxLine1.setStrokeWidth(5);
        oxLine1.setStroke(Color.BLACK);

        // An other Line
        Line line = new Line();
        line.setStartX(10.0f);
        line.setStartY(20.0f);
        line.setEndX(30.0f);
        line.setEndY(7.0f);
        line.setStrokeWidth(10);
        line.setStroke(Color.BLACK);

        AnchorPane root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("КОНСТРУКТОР");
        root.setPadding(new Insets(15));
        root.getChildren().addAll(oxLine1, line);

        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }
}
