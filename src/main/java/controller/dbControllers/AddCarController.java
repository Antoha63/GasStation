package controller.dbControllers;

import controller.Controller;
import controller.ControllerType;
import controller.ControllersRepository;
import entities.Car;
import entities.Fuel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.CarRepository;
import repositories.FuelRepository;
import views.WindowRepository;
import views.WindowType;

import java.io.IOException;
import java.util.List;

public class AddCarController extends Controller {

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private CarRepository carRepository = context.getBean(CarRepository.class);
    private FuelRepository fuelRepository = context.getBean(FuelRepository.class);

    private double xOffset;
    private double yOffset;

    private ObservableList<String> fuelTypes = FXCollections.observableArrayList();

    @FXML
    private TextField model;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField tankVolume;

    @FXML
    private Button closeButton;

    public void initialize() {
        ControllersRepository.addController(ControllerType.ADDCARCONTROLLER, this);
        closeButton.setOnAction(event -> {
            try {
                closeWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        if (DBWorkController.getModel() != null || DBWorkController.getTankVolume() != 0) {
            model.setText(DBWorkController.getModel());
            tankVolume.setText(String.valueOf(DBWorkController.getTankVolume()));
        }

        List<Fuel> fuelList = fuelRepository.findAll();
        for (Fuel fuel : fuelList) {
            fuelTypes.add(fuel.getName());
        }

        comboBox.setItems(fuelTypes);
        try {
            comboBox.setValue(fuelTypes.get(0));
        } catch (IndexOutOfBoundsException e) {
            showAlert("Необходимо добавить как минимум 1 вид топлива!");
        }
    }

    public void add() throws IOException {
        if (DBWorkController.getCarId() != 0) {
            carRepository.delete(carRepository.getOne(DBWorkController.getCarId()));
            DBWorkController.setCarId(0);
        }
        Car car = new Car();
        car.setModel(model.getText());

        car.setFuelType(comboBox.getValue());
        try {
            car.setTankVolume(Double.parseDouble(tankVolume.getText()));
            carRepository.save(car);
            closeWindow();
        } catch (NumberFormatException e) {
            showAlert("Неверный тип данных");
        }
        DBWorkController.setModel(null);
        DBWorkController.setTankVolume(0);
    }

    private void closeWindow() throws IOException {
        WindowRepository.getWindow(WindowType.ADDCARWINDOW).close();
        DBWorkController dbWorkController = (DBWorkController) ControllersRepository.
                getController(ControllerType.DBWORKCONTROLLER);
        dbWorkController.refreshData();
    }

    private void showAlert(String value) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(value);
        alert.initStyle(StageStyle.TRANSPARENT);

        alert.showAndWait();
    }
}
