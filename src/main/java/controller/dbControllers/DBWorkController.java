package controller.dbControllers;

import entities.Car;
import entities.Fuel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.CarRepository;
import repositories.FuelRepository;

import java.io.IOException;
import java.util.List;

public class DBWorkController {

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private FuelRepository fuelRepository = context.getBean(FuelRepository.class);
    private CarRepository carRepository = context.getBean(CarRepository.class);

    private ObservableList<Fuel> fuelObservableList = FXCollections.observableArrayList();
    private ObservableList<Car> carObservableList = FXCollections.observableArrayList();

    private List<Fuel> fuelList;
    private List<Car> carList;

    @FXML
    public TableView<Fuel> fuelTable;

    @FXML
    public TableView<Car> carTable;

    @FXML
    public TableColumn<Fuel, String> columnName;
    public TableColumn<Fuel, String> columnPrice;

    @FXML
    public TableColumn<Car, String> columnId;
    public TableColumn<Car, String> columnModel;
    public TableColumn<Car, String> columnFuelType;
    public TableColumn<Car, String> columnTankVolume;

    @FXML
    private Button closeButton;

    @FXML
    public void initialize(){
        closeButton.setOnAction(event -> {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    });
        initData();

        columnName.setCellValueFactory(new PropertyValueFactory<Fuel, String>("name"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<Fuel, String>("price"));

        columnId.setCellValueFactory(new PropertyValueFactory<Car, String>("id"));
        columnModel.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
        columnFuelType.setCellValueFactory(new PropertyValueFactory<Car, String>("fuelType"));
        columnTankVolume.setCellValueFactory(new PropertyValueFactory<Car, String>("tankVolume"));

        fuelTable.setItems(fuelObservableList);
        carTable.setItems(carObservableList);
    }

    private void initData() {
        fuelList = fuelRepository.findAll();
        for (Fuel fuel: fuelList) {
            fuel.getName();
            fuel.getPrice();
            fuelObservableList.add(fuel);
        }

        carList = carRepository.findAll();
        for (Car car: carList) {
            car.getId();
            car.getModel();
            car.getFuelType();
            car.getTankVolume();
            carObservableList.add(car);
        }
    }

    public void addFuel() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/dbWorkViews/fuelParametersAdd.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void changeFuel() throws IOException {
        fuelRepository.delete(fuelRepository.findByName(fuelTable.getSelectionModel().getSelectedItem().getName()));

        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/dbWorkViews/fuelParametersAdd.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void removeFuel() {
        int row = fuelTable.getSelectionModel().getSelectedIndex();
        fuelRepository.delete(fuelRepository.findByName(fuelTable.getSelectionModel().getSelectedItem().getName()));
        fuelTable.getItems().remove(row);
    }

    public void addCar() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/dbWorkViews/carParametersAdd.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void changeCar() throws IOException {
        carRepository.delete(carRepository.getOne(carTable.getSelectionModel().getSelectedItem().getId()));

        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/dbWorkViews/carParametersAdd.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void removeCar() {
        int row = carTable.getSelectionModel().getSelectedIndex();
        carRepository.delete(carRepository.getOne(carTable.getSelectionModel().getSelectedItem().getId()));
        carTable.getItems().remove(row);
    }
}
