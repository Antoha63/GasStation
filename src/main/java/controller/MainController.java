package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
//    MoveController moveController;
    @FXML
    private ImageView imageView1;

    public void initialize(){
//        moveController = new MoveController();
//        this.imageView1 = moveController.getImageView();
//        imageView1.setViewport(moveController.getViewports()[0]);
//        imageView1.setImage(new Image(getClass().getClassLoader().getResource("pics/sprites.png").toString()));
    }
    public void createTopology() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/developerInfo.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 384, 275));
        primaryStage.show();
    }

    public void downloadTopology() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("КОНСТРУКТОР");
        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }

    public void developerInfo() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/developerInfo.fxml"));
        primaryStage.setTitle("О РАЗРАБОТЧИКЕ");
        primaryStage.setScene(new Scene(root, 384, 275));
        primaryStage.show();
    }

    public void systemInfo() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/systemInfo.fxml"));
        primaryStage.setTitle("О СИСТЕМЕ");
        primaryStage.setScene(new Scene(root, 384, 275));
        primaryStage.show();
    }
}
