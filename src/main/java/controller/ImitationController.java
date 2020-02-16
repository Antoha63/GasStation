package controller;

import Log.Log;
import TimeControl.TimeState;
import elements.CashBox;
import elements.Exit;
import elements.FuelTank;
import elements.PetrolStation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import lombok.Getter;
import topologyObjects.Vehicle;
import views.WindowRepository;
import views.WindowType;
import visualize.Grid;

import java.io.IOException;


public class ImitationController extends Controller {
    private MoveController moveController;

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
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private AnchorPane anchorPane;
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
        back_button.setOnAction(event -> backToModeller());
        closeButton.setOnAction(event -> WindowRepository.getWindow(WindowType.IMITATIONWINDOW).close());
        drawGrid();
        playButton.setOnAction(actionEvent -> playImitation());
        pauseButton.setOnAction(actionEvent -> pauseImitation());
        stopButton.setOnAction(actionEvent -> stopImitation());
    }

    private void backToModeller() {
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
        for (int i = 0; i < Grid.getWidth(); i++) {
            for (int j = 0; j < Grid.getHeight() + 1; j++) {
                anchorPane.getChildren().remove(Grid.getGrid()[i][j]);
                Grid.getGrid()[i][j].setOnMouseEntered(null);
                Grid.getGrid()[i][j].setOnMouseExited(null);
            }
        }
        for (Line line : Grid.getLines()) {
            anchorPane.getChildren().remove(line);
        }
        for (int k = 1; k < Grid.getWidth() - 3; k++) {
            int finalK = k;
            Grid.getGrid()[k][Grid.getHeight()].setOnMouseClicked(event -> Grid.deleteEntryExit(finalK));
        }
        for (int j = 0; j < Grid.getHeight(); j++) {
            int finalJ = j;
            Grid.getGrid()[Exit.getX() - 1][j].setOnMouseClicked(dragEvent -> Grid.deleteCashBox(finalJ));
        }
        for (int i = 0; i < Grid.getWidth() - 3; i++)
            for (int j = 0; j < Grid.getHeight(); j++) {
                int finalI = i;
                int finalJ = j;
                Grid.getGrid()[i][j].setOnMouseClicked(event -> Grid.deletePetrolStation(finalI, finalJ));
            }
        for (int j = 1; j < Grid.getHeight(); j++) {
            int finalJ = j;
            Grid.getGrid()[Grid.getWidth() - 2][j].setOnMouseClicked(dragEvent ->
                    Grid.deleteFuelTank(finalJ));
        }

        stopImitation();

        WindowRepository.getWindow(WindowType.IMITATIONWINDOW).hide();
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
    }

    public void drawGrid() {
        for (int i = 0; i < Grid.getWidth(); i++) {
            for (int j = 0; j < Grid.getHeight() + 1; j++) {
                anchorPane.getChildren().add(Grid.getGrid()[i][j]);
                //TODO: (high priority) при возврате с имитации в параметры не заходит обратно в имитацию
                Grid.getGrid()[i][j].setOnMouseClicked(null);
                //TODO: (high priority) добавить возможность удаления элементов при возвращении в контруктор
            }
        }
        for(int i = 0; i < Grid.getListOfPetrolStations().size(); i++){
            Grid.getGrid()[Grid.getListOfPetrolStations().get(i).getX()][Grid.getListOfPetrolStations().get(i).getY()].
                    setOnMouseEntered(mouseEvent -> {
                        petrolStationPopup.setVisible(!petrolStationPopup.isVisible());
                        cashBoxPopup.setVisible(false);
                        fuelTankPopup.setVisible(false);

                        petrolstationSpeed.setText(PetrolStation.getSpeed() + " л/мин");
                    });
        }
        for(int i = 0; i < Grid.getListOfPetrolStations().size(); i++){
            Grid.getGrid()[Grid.getListOfPetrolStations().get(i).getX()][Grid.getListOfPetrolStations().get(i).getY()].
                    setOnMouseExited(mouseEvent -> {
                        petrolStationPopup.setVisible(!petrolStationPopup.isVisible());
                        cashBoxPopup.setVisible(false);
                        fuelTankPopup.setVisible(false);
                    });
        }
        for (int i = 0; i < Grid.getListOfFuelTanks().size();i++){
            int finalI = i;
            Grid.getGrid()[Grid.getListOfFuelTanks().get(i).getX()][Grid.getListOfFuelTanks().get(i).getY()].
                    setOnMouseEntered(mouseEvent -> {
                        fuelTankPopup.setVisible(!fuelTankPopup.isVisible());
                        cashBoxPopup.setVisible(false);
                        petrolStationPopup.setVisible(false);

                        FuelTank tempFuelTank = Grid.getListOfFuelTanks().get(finalI);
                        fueltankFuelValue.setText(tempFuelTank.getFuel());
                        fueltankCriticalLevel.setText((int) FuelTank.getCriticalLevel() + " %");
                        fueltankCurrentVolume.setText(tempFuelTank.getCurrentVolume() + " л");
                        fueltankVolumeValue.setText(FuelTank.getVolume() + " л");
                    });
        }
        for (int i = 0; i < Grid.getListOfFuelTanks().size();i++){
            Grid.getGrid()[Grid.getListOfFuelTanks().get(i).getX()][Grid.getListOfFuelTanks().get(i).getY()].
                    setOnMouseExited(mouseEvent -> {
                        fuelTankPopup.setVisible(!fuelTankPopup.isVisible());
                        cashBoxPopup.setVisible(false);
                        petrolStationPopup.setVisible(false);
                    });
        }
        Grid.getGrid()[CashBox.getX()][CashBox.getY()].
                setOnMouseEntered(mouseEvent -> {
                    cashBoxPopup.setVisible(!cashBoxPopup.isVisible());
                    fuelTankPopup.setVisible(false);
                    petrolStationPopup.setVisible(false);

                    cashboxBalanceValue.setText(CashBox.getBalance() + " р");
                    cashboxCriticalValue.setText(CashBox.getCriticalLevel() + " р");
                    cashboxProfitValue.setText(CashBox.getProfit() + " р");
                });
        Grid.getGrid()[CashBox.getX()][CashBox.getY()].
                setOnMouseExited(mouseEvent -> {
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
