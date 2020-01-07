package controller;

import elements.CashBox;
import elements.FuelTank;
import elements.PetrolStation;
import entities.Fuel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.FuelRepository;
import topologyObjects.Vehicle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.effect.BlendMode.MULTIPLY;

public class ModellerController {
    private double xOffset;
    private double yOffset;

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private FuelRepository fuelRepository = context.getBean(FuelRepository.class);

    @FXML
    private AnchorPane anchorPane;
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

        addRadioButtons();
    }

    @FXML
    public void createImitation() throws IOException {
        //Vehicle.setProbabilityOfArrival(probabilityOfArrival.getValue());//TODO: fix it
        PetrolStation.setSpeed(petrolStationSpeed.getValue());
        // листу FuelTanks нужно каждому FT присвоить ft.setCurrentVolume = fuelTankVolume.getValue()
        // ЛИБО ЭТО СДЕЛАТЬ В КОНСТРУКТОРЕ ФУЕЛТАНКОВ!!!!!! Я думаю так лучше (Никита)
        FuelTank.setVolume(fuelTankVolume.getValue());
        FuelTank.setCriticalLevel(fuelTankCriticalLevel.getValue());
        CashBox.setCriticalLevel(cashboxCriticalLevel.getValue());



        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/imitation.fxml"));
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        primaryStage.setTitle("ИМИТАЦИЯ");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    public void createDBWork() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/dbWorkViews/dbWork.fxml"));
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void addRadioButtons(){
        List<RadioButton> radioButtonList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<Fuel> fuelList = fuelRepository.findAll();

        for (Fuel fuel: fuelList) {
            nameList.add(fuel.getName());
        }

        int i = 0;
        ToggleGroup fuelType = new ToggleGroup();
        for(String name: nameList){
            RadioButton radioButton = new RadioButton();
            radioButton.setText(name);
            radioButton.setLayoutX(240);
            radioButton.setLayoutY(300 + i*30);
            radioButton.setBlendMode(MULTIPLY);
            radioButton.setToggleGroup(fuelType);
            radioButtonList.add(radioButton);
            i++;
        }

        anchorPane.getChildren().addAll(radioButtonList);
    }
}
