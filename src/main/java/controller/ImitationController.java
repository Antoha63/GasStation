package controller;

import Log.LogMessage;
import Log.LogStatistic;
import TimeControl.TimeState;
import elements.CashBox;
import elements.FuelTank;
import elements.PetrolStation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import topologyObjects.Vehicle;
import visualize.Grid;
import visualize.GridElement;

import java.io.IOException;

public class ImitationController {
    private double xOffset;
    private double yOffset;
    @FXML
    private Button back_button;
    @FXML
    private AnchorPane cashBoxPopup;
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
        setOnActionBackButton();
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
        LogStatistic.setImitationController(this);
    }

    private void setOnActionBackButton() {
        back_button.setOnAction(event -> {

            Stage primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/views/modeller.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            root.setOnMousePressed(mouseEvent -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });
            root.setOnMouseDragged(mouseEvent -> {
                primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                primaryStage.setY(mouseEvent.getScreenY() - yOffset);
            });
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            PetrolStation.setSpeed(0);
            FuelTank.setVolume(0);
            FuelTank.setCriticalLevel(0);
            CashBox.setCriticalLevel(0);
            Vehicle.setProbabilityOfArrival(0);
            //Grid.setGrid(null);

            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
            try {
                this.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    public void statisticRefresh(int profit, int countCars, int countLitres){
        cash_value_label.setText(Integer.toString(profit));
        automobileCountLabel.setText(Integer.toString(countCars));
        fuelValueLabel.setText(Integer.toString(countLitres));
    }

    public void addMessageLog(String message){
        log_list.setText(message + "\n" + log_list.getText());
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

        Stage stage = ModellerController.getConstructorStage();
        stage.setWidth(log_list.getLayoutX() + log_list.getPrefWidth() + spacing);
        stage.setHeight(Grid.getGrid()[0][Grid.getHeight()].getTranslateY() + GridElement.getElementHeight() + spacing * 3);

        log_list.setPrefHeight(stage.getHeight() - log_list.getLayoutY() - statistics.getPrefHeight() - 2 * spacing);
        statistics.setLayoutY(log_list.getLayoutY() + log_list.getPrefHeight() + spacing);
        dragableArea.setPrefWidth(stage.getWidth() - 2);
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
                Grid.getGrid()[i][j].setOnMouseClicked(null);
            }
        }
        for(int i = 0; i < Grid.getListOfPetrolStations().size(); i++){
            int finalI = i;
            Grid.getGrid()[Grid.getListOfPetrolStations().get(i).getX()][Grid.getListOfPetrolStations().get(i).getY()].
                    setOnMouseClicked(mouseEvent -> {
                        petrolStationPopup.setLayoutX(10);
                        petrolStationPopup.setLayoutY(threadButtons.getLayoutY() + playButton.getPrefHeight() + 10);
                        petrolStationPopup.setVisible(!petrolStationPopup.isVisible());
                        cashBoxPopup.setVisible(false);
                        fuelTankPopup.setVisible(false);
                    });
        }
        for (int i = 0; i < Grid.getListOfFuelTanks().size();i++){
            Grid.getGrid()[Grid.getListOfFuelTanks().get(i).getX()][Grid.getListOfFuelTanks().get(i).getY()].
                    setOnMouseClicked(mouseEvent -> {
                        fuelTankPopup.setLayoutX(10);
                        fuelTankPopup.setLayoutY(threadButtons.getLayoutY() + playButton.getPrefHeight() + 10);
                        fuelTankPopup.setVisible(!fuelTankPopup.isVisible());
                        cashBoxPopup.setVisible(false);
                        petrolStationPopup.setVisible(false);
                    });
        }
        Grid.getGrid()[CashBox.getX()][CashBox.getY()].
                setOnMouseClicked(mouseEvent -> {
                    cashBoxPopup.setLayoutX(10);
                    cashBoxPopup.setLayoutY(threadButtons.getLayoutY() + playButton.getPrefHeight() + 10);
                    cashBoxPopup.setVisible(!cashBoxPopup.isVisible());
                    fuelTankPopup.setVisible(false);
                    petrolStationPopup.setVisible(false);
                });
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
            CashBox.setStatus(true);
            CashBox.setBalance(0);
            CashBox.setProfit(0);
            Vehicle.setCountCars(0);
            Vehicle.setCountLitres(0);
            new LogStatistic(0,0,0);
            for (int i = 0; i < Grid.getListOfPetrolStations().size(); i++){
                Grid.getListOfPetrolStations().get(i).setStatus(true);
            }
            for (int i = 0; i < Grid.getListOfFuelTanks().size(); i++){
                Grid.getListOfFuelTanks().get(i).setStatus(true);
                Grid.getListOfFuelTanks().get(i).setCurrentVolume(FuelTank.getVolume());
            }
            log_list.clear();
        });
    }
}
