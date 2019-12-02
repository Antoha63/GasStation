package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;

public class ConstructorController {

    public void initialize(){
//        moveController = new MoveController();
//        this.imageView1 = moveController.getImageView();
//        imageView1.setViewport(moveController.getViewports()[0]);
//        imageView1.setImage(new Image(getClass().getClassLoader().getResource("pics/sprites.png").toString()));
    }

    public void setTopologySize() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("КОНСТРУКТОР");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }
}
