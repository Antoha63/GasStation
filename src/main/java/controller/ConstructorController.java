package controller;

import TopologyObjects.TransportVehicle;
import animation.MoveController;
import TopologyObjects.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import visualize.Grid;

import java.io.IOException;

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

        MoveController moveController = new MoveController();
        moveController.go();
    }
    public void initImageVIew(ImageView imageView){

    }
}
