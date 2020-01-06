package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

import java.io.IOException;

public class BoundsController {
    @FXML
    private Spinner<Integer> topologyHeight;
    @FXML
    private Spinner<Integer> topologyWidth;

    public int getTopologyHeight(){
        return topologyHeight.getValue();
    }
    public int getTopologyWidth(){
        return topologyWidth.getValue();
    }

    public BoundsController() {
    }

    @FXML
    public void createConstructor() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
