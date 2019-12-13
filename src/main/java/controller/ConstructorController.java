package controller;

import animation.MoveController;
import elements.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import visualize.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstructorController {
    @FXML
    Spinner topologyWidth;

    @FXML
    Spinner topologyHeight;

    @FXML
    private AnchorPane anchorPaneField;

    public void initialize(){
//        moveController = new MoveController();
//        this.imageView1 = moveController.getImageView();
//        imageView1.setViewport(moveController.getViewports()[0]);
//        imageView1.setImage(new Image(getClass().getClassLoader().getResource("pics/sprites.png").toString()));
    }

    public void createConstructor() throws IOException {
        Stage primaryStage = new Stage();

        AnchorPane root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("КОНСТРУКТОР");
        int x0 = 270;
        int y0 = 25;
        Grid petrolGrid = new Grid(x0, y0,10, 10);
        int elementWidth = 40;
        for(Line line: petrolGrid.getLineList()){
            root.getChildren().add(line);
        }

//        Grid roadwayGrid = new Grid(x0 - elementWidth, y0 + elementWidth * (petrolGrid.getHeight() - 1), 15, 2);
//        for(Line line: roadwayGrid.getLineList()){
//            root.getChildren().add(line);
//        }

        MoveController moveController = new MoveController();
        root.getChildren().add(moveController.initImageView());
        Vehicle vehicle = new Vehicle(0.1);

        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    root.getChildren().add(vehicle.go(moveController.initImageView()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        animationTimer.start();
    }
    public void initImageVIew(ImageView imageView){

    }
}
