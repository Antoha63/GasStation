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
import views.WindowRepository;
import views.WindowType;
import visualize.Grid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.effect.BlendMode.MULTIPLY;

public class ModellerController extends Controller {
    private static List<Car> carList;
    private static List<Fuel> fuelList;
    private static List<Fuel> usabledFuelList;
    private List<CheckBox> checkBoxes = new ArrayList<>();

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

    public void initialize() {
        ControllersRepository.addController(ControllerType.MODELLERCONTROLLER, this);
        closeButton.setOnAction(event -> {
            WindowRepository.getWindow(WindowType.MODELLERWINDOW).hide();
        });
        back_button.setOnAction(event -> {
            WindowRepository.getWindow(WindowType.MODELLERWINDOW).hide();
            ConstructorController constructorController = (ConstructorController)
                    ControllersRepository.getController(ControllerType.CONSTRUCTORCONTROLLER);
            constructorController.drawGrid(Grid.getWidth(), Grid.getHeight());
            try {
                WindowRepository.getWindow(WindowType.CONSTRUCTORWINDOW).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            Vehicle.setProbabilityOfArrival(probabilityOfArrival.getValue());
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
            if(ControllersRepository.getControllers().containsKey
                    (ControllerType.IMITATIONCONTROLLER)) {
                ImitationController imitationController = (ImitationController)
                    ControllersRepository.getController(ControllerType.IMITATIONCONTROLLER);
                imitationController.drawGrid();
            }
            WindowRepository.getWindow(WindowType.IMITATIONWINDOW).show();
            WindowRepository.getWindow(WindowType.MODELLERWINDOW).hide();
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
        WindowRepository.getWindow(WindowType.DBWORKWINDOW).show();
        WindowRepository.getWindow(WindowType.MODELLERWINDOW).close();
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
            checkBox.setLayoutX(20);
            checkBox.setLayoutY(300 + i * 30);
            checkBox.setBlendMode(MULTIPLY);
            checkBoxes.add(checkBox);
            i++;
        }

        anchorPane.getChildren().addAll(checkBoxes);
    }
}
