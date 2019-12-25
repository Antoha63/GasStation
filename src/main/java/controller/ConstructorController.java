package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
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

    @FXML
    private ImageView petrolStation;

    @FXML
    private ImageView cashBox;
    @FXML
    void cashBoxOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }
    @FXML
    void cashBoxOnDragDetectedEvent(MouseEvent event) {
        Dragboard dragboard = cashBox.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(cashBox.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }


    @FXML
    void petrolStationOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }
    @FXML
    void petrolStationOnDragDetectedEvent(MouseEvent event) {
        Dragboard dragboard = petrolStation.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(petrolStation.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }


    @FXML
    private ImageView fuelTank;
    @FXML
    void fuelTankOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }
    @FXML
    void fuelTankOnDragDetectedEvent(MouseEvent event) {
        Dragboard dragboard = fuelTank.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(fuelTank.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }


    public void createConstructor() throws IOException {
        Stage primaryStage = new Stage();
        AnchorPane root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("КОНСТРУКТОР");
        int x0 = 220;
        int y0 = 0;

        Grid.initGrid(x0, y0, topologyWidth.getValue(), topologyHeight.getValue());
        for (int i = 0; i < topologyWidth.getValue(); i++) {
            for (int j = 0; j < topologyHeight.getValue() + 1; j++) {
                root.getChildren().add(Grid.getGrid()[i][j]);
            }
        }
        for (Line line : Grid.getLineList()) {
            root.getChildren().add(line);
        }

        MoveController moveController = new MoveController();
        moveController.go(root);

        primaryStage.setScene(new Scene(root, root.getPrefWidth(), root.getPrefHeight()));
        primaryStage.show();
    }
}
