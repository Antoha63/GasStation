package controller;

import Log.LogMessage;
import TimeControl.TimeState;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import visualize.Grid;
import visualize.GridElement;

import java.io.IOException;

public class ImitationController {
    @FXML
    private AnchorPane cahshBoxPopup;
    @FXML
    private Label cashboxProfitValue;
    @FXML
    private Label cashboxBalanceValue;
    @FXML
    private Label cashboxCriticalValue;
    @FXML
    private AnchorPane fuelTankPopup;
    @FXML
    private Label fueltankFuelValue;
    @FXML
    private Label fueltankVolumeValue;
    @FXML
    private Label fueltankCriticalLevel;
    @FXML
    private Label fueltankCurrentVolume;
    @FXML
    private AnchorPane petrolStationPopup;
    @FXML
    private Label petrolstationSpeed;
    @FXML
    private Label petrolstationStatus;
    @FXML
    private MoveController moveController = new MoveController(this);
    @FXML
    private AnchorPane dragableArea;
    @FXML
    private AnchorPane threadButtons;
    @FXML
    private AnchorPane statistics;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private AnchorPane backButtons;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label infoLabel;
    @FXML
    private TextArea log_list;
    @FXML
    private Label cash_value_label;
    @FXML
    private Label automobileCountLabel;
    @FXML
    private Label fuelValueLabel;
    @FXML
    private Button closeButton;
    @FXML
    private AnchorPane anchorPaneMode;
    @FXML
    private Slider sliderMode;
    @FXML
    private Button inConstructorButton;

    public void initialize() {
        positionElements();
        setOnActionCloseWindow();
        drawGrid();
        setOnActionPlay();
        setOnActionPause();
        setOnActionStop();
        MoveController.setSliderMode((int)sliderMode.getValue());
        sliderMode.setOnMouseClicked(event -> {
            MoveController.setSliderMode((int)sliderMode.getValue());
        });
        LogMessage.setImitationController(this);
    }

    public void addMessageLog(String message){
        log_list.setText(log_list.getText() + "\n" + message);
    }

    private void positionElements() {
        int spacing = 10;
        backButtons.setLayoutX(spacing);
        backButtons.setLayoutY(threadButtons.getLayoutY() + threadButtons.getPrefHeight() + spacing);
        log_list.setLayoutX(Grid.getGrid()[Grid.getWidth() - 1][0].getTranslateX() +
                GridElement.getElementWidth() + spacing);
        infoLabel.setLayoutX(log_list.getLayoutX() +
                log_list.getPrefWidth() / 2 - infoLabel.getPrefWidth() / 2);
        statistics.setLayoutX(log_list.getLayoutX() +
                log_list.getPrefWidth() / 2 - statistics.getPrefWidth() / 2);

        Stage stage = ModellerController.getPrimaryStage();
        stage.setWidth(log_list.getLayoutX() + log_list.getPrefWidth() + spacing);
        stage.setHeight(Grid.getGrid()[0][Grid.getHeight()].getTranslateY() + GridElement.getElementHeight() + spacing * 3);

        log_list.setPrefHeight(stage.getHeight() - log_list.getLayoutY() - statistics.getPrefHeight() - 2 * spacing);
        statistics.setLayoutY(log_list.getLayoutY() + log_list.getPrefHeight() + spacing);
        dragableArea.setPrefWidth(stage.getWidth() - 2);
        //anchorPaneMode.setLayoutX(spacing + inConstructorButton.getPrefWidth() / 2 - anchorPaneMode.getPrefWidth() / 2);
        //anchorPaneMode.setLayoutY(backButtons.getLayoutY() + inConstructorButton.getPrefHeight() * 3 + spacing * 3);
    }

    private void setOnActionCloseWindow() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void drawGrid() {
        for (int i = 0; i < Grid.getWidth(); i++) {
            for (int j = 0; j < Grid.getHeight() + 1; j++) {
                anchorPane.getChildren().add(Grid.getGrid()[i][j]);
            }
        }
        for (Line line : Grid.getLineList()) {
            anchorPane.getChildren().add(line);
        }
    }

    private void setOnActionPlay() {
        playButton.setOnAction(event -> {
            try {
                if(moveController.getTimeState() == TimeState.START || moveController.getTimeState() == TimeState.PAUSE){

                }
                else{
                    moveController.setTimeState(TimeState.START);
                    moveController.go(anchorPane);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void setOnActionPause() {
        pauseButton.setOnAction(event -> {
            if (moveController.getTimeState() == TimeState.START)
                moveController.setTimeState(TimeState.PAUSE);
            else if (moveController.getTimeState() == TimeState.PAUSE) {
                moveController.setTimeState(TimeState.START);
            }
        });
    }

    private void setOnActionStop() {
        stopButton.setOnAction(event -> {
            moveController.setTimeState(TimeState.STOP);
            moveController = new MoveController(this);
        });
    }
}
