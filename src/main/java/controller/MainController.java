package controller;

import animation.MoveController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private ImageView imageView;

    public void initialize(){
//<<<<<<< HEAD
//        moveController = new MoveController();
//        this.imageView1 = moveController.getImageView();
//        imageView1.setViewport(moveController.getViewports()[0]);
//        imageView1.setImage(new Image(getClass().getClassLoader().getResource("pics/sprites.png").toString()));
    }
    public void createTopology() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/topologySize.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 384, 275));
        primaryStage.show();
    }

    public void downloadTopology() throws IOException {
        Stage primaryStage = new Stage();

        GridPane gridPane = new GridPane();

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(30);
        gridPane.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(40);
        gridPane.getColumnConstraints().add(column2);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(30);
        gridPane.getColumnConstraints().add(column3);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(55);
        gridPane.getRowConstraints().add(row1);

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(45);
        gridPane.getRowConstraints().add(row2);

        gridPane.setGridLinesVisible(true);

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
/*=======
        moveController = new MoveController(new Image(getClass().getClassLoader().getResource("pics/sprites.png").toString()));
        imageView.setImage(moveController.getImage());
        imageView.setViewport(moveController.getViewports()[2]);
>>>>>>> b1d67dc162446ca9f7b24e23a0e0f0533a14c460*/
    }
}
