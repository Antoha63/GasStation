package controller;

import elements.CashBox;
import elements.FuelTank;
import elements.PetrolStation;
import entities.Car;
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
import repositories.CarRepository;
import repositories.FuelRepository;
import topologyObjects.Vehicle;
import value.DeterministicDistribution;
import value.ExponentialDistribution;
import value.UniformDistribution;
import visualize.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.effect.BlendMode.MULTIPLY;

public class ModellerController {
    private double xOffset;
    private double yOffset;
    private static Stage constructorStage;
    private static List<Car> carList;
    private static List<Fuel> fuelList;
    private static List<Fuel> usabledFuelList;
    private List<CheckBox> checkBoxes = new ArrayList<>();

    public static Stage getConstructorStage() {
        return constructorStage;
    }

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private FuelRepository fuelRepository = context.getBean(FuelRepository.class);
    private CarRepository carRepository = context.getBean(CarRepository.class);

    @FXML
    private Button back_button;
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
    private Spinner<Double> time;
    @FXML
    private Spinner<Double> matO;
    @FXML
    private Spinner<Double> dispersion;
    @FXML
    private Spinner<Double> intens;
    @FXML
    private Label timeLabel;
    @FXML
    private Label matOLabel;
    @FXML
    private Label dispersionLabel;
    @FXML
    private Label intensLabel;
    @FXML
    private Spinner<Double> probabilityOfArrival;

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

    public static List<Car> getCarList(){
        return carList;
    }
    public static List<Fuel> getFuelList(){
        return fuelList;
    }
    public static List<Fuel> getUsabledFuelList(){
        return usabledFuelList;    }

    public void initialize() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
        back_button.setOnAction(event -> {
            Stage stage = (Stage) back_button.getScene().getWindow();
            stage.close();
            ConstructorController.setBounds(Grid.getWidth(), Grid.getHeight());
            constructorStage = new Stage();
            constructorStage.initStyle(StageStyle.TRANSPARENT);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            root.setOnMousePressed(mouseEvent -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });
            root.setOnMouseDragged(mouseEvent -> {
                constructorStage.setX(mouseEvent.getScreenX() - xOffset);
                constructorStage.setY(mouseEvent.getScreenY() - yOffset);
            });
            constructorStage.setTitle("");
            constructorStage.setScene(new Scene(root));
            constructorStage.show();
        });
        radioButtonDeterministicDistribution.setOnAction(event -> {
            labelTime.setVisible(true);
            timeLabel.setVisible(true);
            time.setVisible(true);

            labelMatO.setVisible(false);
            matOLabel.setVisible(false);
            matO.setVisible(false);

            labelDispersion.setVisible(false);
            dispersionLabel.setVisible(false);
            dispersion.setVisible(false);

            labelIntens.setVisible(false);
            intensLabel.setVisible(false);
            intens.setVisible(false);
        });
        radioButtonUniformDistribution.setOnAction(event -> {
            labelTime.setVisible(false);
            timeLabel.setVisible(false);
            time.setVisible(false);

            labelMatO.setVisible(true);
            matOLabel.setVisible(true);
            matO.setVisible(true);

            labelDispersion.setVisible(true);
            dispersionLabel.setVisible(true);
            dispersion.setVisible(true);

            labelIntens.setVisible(false);
            intensLabel.setVisible(false);
            intens.setVisible(false);
        });
        radioButtonExponentialDistribution.setOnAction(event -> {
            labelTime.setVisible(false);
            timeLabel.setVisible(false);
            time.setVisible(false);

            labelMatO.setVisible(false);
            matOLabel.setVisible(false);
            matO.setVisible(false);

            labelDispersion.setVisible(false);
            dispersionLabel.setVisible(false);
            dispersion.setVisible(false);

            labelIntens.setVisible(true);
            intensLabel.setVisible(true);
            intens.setVisible(true);
        });

        addRadioButtons();
    }

    @FXML
    public void createImitation() throws Exception {
        carList = carRepository.findAll();
        fuelList = fuelRepository.findAll();
        usabledFuelList = new ArrayList<>();
        for (CheckBox rb : checkBoxes){
            if (rb.isSelected()){
                for (Fuel fuel : fuelList) {
                    if (fuel.getName().equals(rb.getText())){
                        usabledFuelList.add(fuel);}
                }
            }
        }
        if (usabledFuelList.size() == Grid.getListOfFuelTanks().size())
        {
            PetrolStation.setSpeed(petrolStationSpeed.getValue());
            FuelTank.setVolume(fuelTankVolume.getValue());
            FuelTank.setCriticalLevel(fuelTankCriticalLevel.getValue());
            CashBox.setCriticalLevel(cashboxCriticalLevel.getValue());
            Vehicle.setProbabilityOfArrival(probabilityOfArrival.getValue());//TODO: fix it
            if (radioButtonDeterministicDistribution.isSelected()) {
                MoveController.setDistribution(new DeterministicDistribution(time.getValue()));
            } else if (radioButtonExponentialDistribution.isSelected()) {
                MoveController.setDistribution(new ExponentialDistribution(intens.getValue()));
            } else if (radioButtonUniformDistribution.isSelected()) {
                MoveController.setDistribution(new UniformDistribution(matO.getValue(), dispersion.getValue()));
            }
            for (int i = 0; i < Grid.getListOfFuelTanks().size(); i++) {
                Grid.getListOfFuelTanks().get(i).setFuel(usabledFuelList.get(i).getName());
                Grid.getListOfFuelTanks().get(i).setCurrentVolume(FuelTank.getVolume());
            }
            constructorStage = new Stage();
            constructorStage.initStyle(StageStyle.TRANSPARENT);
            Parent root = FXMLLoader.load(getClass().getResource("/views/imitation.fxml"));
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                constructorStage.setX(event.getScreenX() - xOffset);
                constructorStage.setY(event.getScreenY() - yOffset);
            });
            constructorStage.setTitle("ИМИТАЦИЯ");
            constructorStage.setScene(new Scene(root));
            constructorStage.show();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Число выбранных видов топлива должно соответствовать количеству топливных баков!");
            alert.showAndWait();
        }
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

    private void addRadioButtons() {
        List<String> nameList = new ArrayList<>();
        List<Fuel> fuelList = fuelRepository.findAll();

        for (Fuel fuel : fuelList) {
            nameList.add(fuel.getName());
        }

        int i = 0;
        for (String name : nameList) {
            CheckBox checkBox = new CheckBox();
            checkBox.setText(name);
            checkBox.setLayoutX(240);
            checkBox.setLayoutY(300 + i * 30);
            checkBox.setBlendMode(MULTIPLY);
            checkBoxes.add(checkBox);
            i++;
        }

        anchorPane.getChildren().addAll(checkBoxes);
    }
}
