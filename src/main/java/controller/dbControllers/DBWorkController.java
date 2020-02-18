package controller.dbControllers;

import controller.Controller;
import controller.ControllerType;
import controller.ControllersRepository;
import entities.Car;
import entities.Fuel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.CarRepository;
import repositories.FuelRepository;
import views.WindowRepository;
import views.WindowType;

import java.io.IOException;
import java.util.List;

public class DBWorkController extends Controller {
    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private FuelRepository fuelRepository = context.getBean(FuelRepository.class);
    private CarRepository carRepository = context.getBean(CarRepository.class);

    private ObservableList<Fuel> fuelObservableList = FXCollections.observableArrayList();
    private ObservableList<Car> carObservableList = FXCollections.observableArrayList();

    private List<Fuel> fuelList;
    private List<Car> carList;

    private Stage primaryStage = new Stage();
    private static String name;
    private static double price;

    private static long carId;
    private static String model;
    private static double tankVolume;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public TableView<Fuel> fuelTable;

    @FXML
    public TableView<Car> carTable;

    @FXML
    public TableColumn<Fuel, String> columnName;
    public TableColumn<Fuel, String> columnPrice;

    @FXML
    public TableColumn<Car, String> columnModel;
    public TableColumn<Car, String> columnFuelType;
    public TableColumn<Car, String> columnTankVolume;

    @FXML
    private Button closeButton;

    public static String getModel() {
        return model;
    }

    public static void setModel(String model) {
        DBWorkController.model = model;
    }

    public static double getTankVolume() {
        return tankVolume;
    }

    public static void setTankVolume(int tankVolume) {
        DBWorkController.tankVolume = tankVolume;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        DBWorkController.name = name;
    }

    public static double getPrice() {
        return price;
    }

    public static void setPrice(int price) {
        DBWorkController.price = price;
    }

    public static long getCarId() {
        return carId;
    }

    public static void setCarId(long carId) {
        DBWorkController.carId = carId;
    }

    @FXML
    public void initialize() {
        ControllersRepository.addController(ControllerType.DBWORKCONTROLLER, this);
        fuelTable.getItems().clear();
        carTable.getItems().clear();

        primaryStage.initStyle(StageStyle.TRANSPARENT);

        closeButton.setOnAction(event -> {
            try {
                WindowRepository.getWindow(WindowType.MODELLERWINDOW).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            WindowRepository.getWindow(WindowType.DBWORKWINDOW).close();
        });

        columnName.setCellValueFactory(new PropertyValueFactory<Fuel, String>("name"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<Fuel, String>("price"));

        columnModel.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
        columnFuelType.setCellValueFactory(new PropertyValueFactory<Car, String>("fuelType"));
        columnTankVolume.setCellValueFactory(new PropertyValueFactory<Car, String>("tankVolume"));

        initData();
    }

    private void initData() {
        fuelList = fuelRepository.findAll();
        for (Fuel fuel : fuelList) {
            fuel.getName();
            fuel.getPrice();
            fuelObservableList.add(fuel);
        }

        carList = carRepository.findAll();
        for (Car car : carList) {
            car.getModel();
            car.getFuelType();
            car.getTankVolume();
            carObservableList.add(car);
        }

        fuelTable.setItems(fuelObservableList);
        carTable.setItems(carObservableList);
    }

    public void addFuel() throws IOException {
        getParentFuel();
    }

    public void changeFuel() throws IOException {
        if (fuelTable.getSelectionModel().getSelectedItem() == null)
            showAlert("Сначала выберите запись из таблицы");
        else {
            name = fuelTable.getSelectionModel().getSelectedItem().getName();
            price = fuelTable.getSelectionModel().getSelectedItem().getPrice();

            getParentFuel();
        }
    }

    public void removeFuel() throws IOException {
        if (fuelTable.getSelectionModel().getSelectedItem() == null)
            showAlert("Сначала выберите запись из таблицы");
        else {
            int row = fuelTable.getSelectionModel().getSelectedIndex();
            fuelRepository.delete(fuelRepository.findByName(fuelTable.getSelectionModel().getSelectedItem().getName()));
            fuelTable.getItems().remove(row);
        }
    }

    public void addCar() throws IOException {
        getParentCar();
    }

    public void changeCar() throws IOException {
        if (carTable.getSelectionModel().getSelectedItem() == null)
            showAlert("Сначала выберите запись из таблицы");
        else {
            model = carTable.getSelectionModel().getSelectedItem().getModel();
            tankVolume = carTable.getSelectionModel().getSelectedItem().getTankVolume();
            carId = carTable.getSelectionModel().getSelectedItem().getId();

            getParentCar();
        }
    }

    public void removeCar() throws IOException {
        if (carTable.getSelectionModel().getSelectedItem() == null)
            showAlert("Сначала выберите запись из таблицы");
        else {
            int row = carTable.getSelectionModel().getSelectedIndex();
            carRepository.delete(carRepository.getOne(carTable.getSelectionModel().getSelectedItem().getId()));
            carTable.getItems().remove(row);
        }
    }

    private void getParentFuel() throws IOException {
        WindowRepository.getWindow(WindowType.ADDFUELWINDOW).show();
    }

    private void getParentCar() throws IOException {
        List<Fuel> fuelList = fuelRepository.findAll();
        if (!fuelList.isEmpty()) {
            WindowRepository.getWindow(WindowType.ADDCARWINDOW).show();
        } else
            showAlert("Создайте хотя бы 1 вид топлива!");
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
