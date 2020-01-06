package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import visualize.Grid;
import visualize.GridElement;

import javax.swing.*;
import java.io.IOException;

public class ConstructorController {

    @FXML
    private AnchorPane anchorPane;

    public void initialize() throws IOException {
        //BoundsController boundsController = FXMLLoader.load(getClass().getResource("/views/topologySize.fxml"));

        int x0 = 220;
        int y0 = 0;

        int topologyWidth = 7;//boundsController.getTopologyWidth();
        int topologyHeight = 3;//boundsController.getTopologyHeight();
        Grid.initGrid(x0, y0, topologyWidth, topologyHeight);
        for (int i = 0; i < topologyWidth; i++) {
            for (int j = 0; j < topologyHeight + 1; j++) {
                anchorPane.getChildren().add(Grid.getGrid()[i][j]);
            }
        }
        for (Line line : Grid.getLineList()) {
            anchorPane.getChildren().add(line);
        }

    }


    @FXML
    private Button startButton;
    @FXML
    void startModelling(ActionEvent event) throws IOException {
        MoveController moveController = new MoveController();
        moveController.go(anchorPane);
    }

    @FXML
    private ImageView entry;
    @FXML
    void entryOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }
    @FXML
    void entryOnDragDetectedEvent(MouseEvent event) {
        Dragboard dragboard = entry.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(entry.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }
    @FXML
    void entryOnMouseClicked(MouseEvent event) {
        entry.setRotate(entry.getRotate() + 90);
    }


    @FXML
    private ImageView exit;
    @FXML
    void exitOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }
    @FXML
    void exitOnDragDetectedEvent(MouseEvent event) {
        Dragboard dragboard = exit.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(exit.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }
    @FXML
    void exitOnMouseClicked(MouseEvent event) {
        exit.setRotate(exit.getRotate() + 90);
    }


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
    private ImageView petrolStation;
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


}
