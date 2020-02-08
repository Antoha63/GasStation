package controller;

import Log.Log;
import TimeControl.TimeState;
import elements.CashBox;
import elements.FuelTank;
import elements.PetrolStation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import lombok.Getter;
import topologyObjects.Vehicle;
import views.WindowRepository;
import views.WindowType;
import visualize.Grid;
import visualize.GridElement;

import java.io.IOException;

public class ImitationController extends Controller {
    private MoveController moveController;

/*    {
        ControllersRepository.addController(ControllerType.IMITATIONCONTROLLER, this);
    }*/
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
    @FXML
    private AnchorPane threadButtons;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
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
    @Getter
    private Slider sliderMode;

    public void initialize() {
        ControllersRepository.addController(ControllerType.IMITATIONCONTROLLER, this);
        moveController = new MoveController();
        setOnActionBackButton();
        setOnActionCloseWindow();
        drawGrid();
        playButton.setOnAction(actionEvent -> {
            playImitation();
        });
        pauseButton.setOnAction(actionEvent -> {
            pauseImitation();
        });
        stopButton.setOnAction(actionEvent -> {
            stopImitation();
        });
    }

    private void setOnActionBackButton() {
        back_button.setOnAction(event -> {
            try {
                WindowRepository.getWindow(WindowType.MODELLERWINDOW).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            PetrolStation.setSpeed(0);
            FuelTank.setVolume(0);
            FuelTank.setCriticalLevel(0);
            CashBox.setCriticalLevel(0);
            Vehicle.setProbabilityOfArrival(0);

            stopImitation();

            WindowRepository.getWindow(WindowType.IMITATIONWINDOW).hide();
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

    private void setOnActionCloseWindow() {
        closeButton.setOnAction(event -> {
            WindowRepository.getWindow(WindowType.IMITATIONWINDOW).close();
        });
    }

    public void drawGrid() {
        GridElement[][] tempGrid = Grid.getGrid();
        for (int i = 0; i < Grid.getWidth(); i++) {
            for (int j = 0; j < Grid.getHeight() + 1; j++) {
                anchorPane.getChildren().add(tempGrid[i][j]);
                tempGrid[i][j].setOnMouseClicked(null);
            }
        }
        for(int i = 0; i < Grid.getListOfPetrolStations().size(); i++){
            int finalI = i;
            tempGrid[Grid.getListOfPetrolStations().get(i).getX()][Grid.getListOfPetrolStations().get(i).getY()].
                    setOnMouseClicked(mouseEvent -> {
                        petrolStationPopup.setVisible(!petrolStationPopup.isVisible());
                        cashBoxPopup.setVisible(false);
                        fuelTankPopup.setVisible(false);
                    });
        }
        for (int i = 0; i < Grid.getListOfFuelTanks().size();i++){
            int finalI = i;
            tempGrid[Grid.getListOfFuelTanks().get(i).getX()][Grid.getListOfFuelTanks().get(i).getY()].
                    setOnMouseClicked(mouseEvent -> {
                        fuelTankPopup.setVisible(!fuelTankPopup.isVisible());
                        cashBoxPopup.setVisible(false);
                        petrolStationPopup.setVisible(false);

                        FuelTank tempFuelTank = Grid.getListOfFuelTanks().get(finalI);
                        fueltankFuelValue.setText(tempFuelTank.getFuel());
                        fueltankCriticalLevel.setText(String.valueOf(FuelTank.getCriticalLevel()));
                        fueltankCurrentVolume.setText(String.valueOf(tempFuelTank.getCurrentVolume()));
                        fueltankVolumeValue.setText(String.valueOf(FuelTank.getVolume()));
                    });
        }
        tempGrid[CashBox.getX()][CashBox.getY()].
                setOnMouseClicked(mouseEvent -> {
                    cashBoxPopup.setVisible(!cashBoxPopup.isVisible());
                    fuelTankPopup.setVisible(false);
                    petrolStationPopup.setVisible(false);
                });
        for (Line line : Grid.getLines()) {
            anchorPane.getChildren().add(line);
        }
    }

    private void playImitation() {
        if(ControllersRepository.getController(ControllerType.IMITATIONCONTROLLER) != this)
            ControllersRepository.addController(ControllerType.IMITATIONCONTROLLER, this);
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
    }

    private void pauseImitation() {
        if (moveController.getTimeState() == TimeState.START)
            moveController.setTimeState(TimeState.PAUSE);
        else if (moveController.getTimeState() == TimeState.PAUSE) {
            moveController.setTimeState(TimeState.START);
        }
    }

    private void stopImitation() {
        moveController.setTimeState(TimeState.STOP);
        moveController = new MoveController();
        CashBox.setStatus(true);
        CashBox.setBalance(0);
        CashBox.setProfit(0);
        Vehicle.setCountCars(0);
        Vehicle.setCountLitres(0);
        Log.sendMessage(0, 0, 0);
        for (int i = 0; i < Grid.getListOfPetrolStations().size(); i++){
            Grid.getListOfPetrolStations().get(i).setStatus(true);
        }
        for (int i = 0; i < Grid.getListOfFuelTanks().size(); i++){
            Grid.getListOfFuelTanks().get(i).setStatus(true);
            Grid.getListOfFuelTanks().get(i).setCurrentVolume(FuelTank.getVolume());
        }

        log_list.clear();
    }
}
