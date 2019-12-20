package controller;

import animation.MoveController;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ConstructorController {

    @FXML
    private Spinner<Integer> topologyHeight;

    @FXML
    private Spinner<Integer> topologyWidth;

    public void createConstructor() throws IOException {
        MoveController moveController = new MoveController(topologyHeight.getValue(), topologyWidth.getValue());
        moveController.go();
    }
}
