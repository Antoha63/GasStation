package controller;

import elements.CashBox;
import elements.FuelTank;
import elements.PetrolStation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import topologyObjects.Vehicle;

import java.io.IOException;

public class ModellerController {

    @FXML
    private Spinner<Integer> fuelTankVolume;
    @FXML
    private Spinner<Integer> fuelTankCriticalLevel;
    @FXML
    private Spinner<Integer> petrolStationSpeed;
    @FXML
    private Spinner<Integer> cashboxCriticalLevel;
    @FXML
    private Spinner<Integer> time;
    @FXML
    private Spinner<Integer> matO;
    @FXML
    private Spinner<Integer> dispersion;
    @FXML
    private Spinner<Integer> intens;
    @FXML
    private Spinner<Integer> probabilityOfArrival;

    @FXML
    private RadioButton radioButtonDeterministicDistribution;
    @FXML
    private RadioButton radioButtonUniformDistribution;
    @FXML
    private RadioButton radioButtonExponentialDistribution;

    @FXML
    private Label labelTime;
    @FXML
    private Label labelMatO;
    @FXML
    private Label labelDispersion;
    @FXML
    private Label labelIntens;

    @FXML
    private Button closeButton;

    public void initialize (){
        closeButton.setOnAction(event -> {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    });
        radioButtonDeterministicDistribution.setOnAction(event -> {
            labelTime.setVisible(true);
            time.setVisible(true);
            labelMatO.setVisible(false);
            matO.setVisible(false);
            labelDispersion.setVisible(false);
            dispersion.setVisible(false);
            labelIntens.setVisible(false);
        });
        radioButtonUniformDistribution.setOnAction(event -> {
            labelTime.setVisible(false);
            time.setVisible(false);
            labelMatO.setVisible(true);
            matO.setVisible(true);
            labelDispersion.setVisible(true);
            dispersion.setVisible(true);
            labelIntens.setVisible(false);
            intens.setVisible(false);
        });
        radioButtonExponentialDistribution.setOnAction(event -> {
            labelTime.setVisible(false);
            time.setVisible(false);
            labelMatO.setVisible(false);
            matO.setVisible(false);
            labelDispersion.setVisible(false);
            dispersion.setVisible(false);
            labelIntens.setVisible(true);
            intens.setVisible(true);
        });
    }

    @FXML
    public void createImitation() throws IOException {
        Vehicle.setProbabilityOfArrival(probabilityOfArrival.getValue());//TODO: fix it
        PetrolStation.setSpeed(petrolStationSpeed.getValue());
        // листу FuelTanks нужно каждому FT присвоить ft.setCurrentVolume = fuelTankVolume.getValue()
        // ЛИБО ЭТО СДЕЛАТЬ В КОНСТРУКТОРЕ ФУЕЛТАНКОВ!!!!!! Я думаю так лучше (Никита)
        FuelTank.setVolume(fuelTankVolume.getValue());
        FuelTank.setCriticalLevel(fuelTankCriticalLevel.getValue());
        CashBox.setCriticalLevel(cashboxCriticalLevel.getValue());



        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/imitation.fxml"));
        primaryStage.setTitle("ИМИТАЦИЯ");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }

    @FXML
    public void createDBWork() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/dbWorkViews/dbWork.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
