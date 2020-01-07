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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.CarRepository;
import repositories.FuelRepository;

import java.io.IOException;
import java.util.List;

public class DBWorkController {
    private double xOffset;
    private double yOffset;

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private FuelRepository fuelRepository = context.getBean(FuelRepository.class);
    private CarRepository carRepository = context.getBean(CarRepository.class);

    private ObservableList<Fuel> fuelObservableList = FXCollections.observableArrayList();
    private ObservableList<Car> carObservableList = FXCollections.observableArrayList();

    private List<Fuel> fuelList;
    private List<Car> carList;

    private Stage primaryStage = new Stage();

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
    public TableColumn<Car, String> columnId;
    public TableColumn<Car, String> columnModel;
    public TableColumn<Car, String> columnFuelType;
    public TableColumn<Car, String> columnTankVolume;

    @FXML
    private Button closeButton;

    @FXML
    public void initialize(){
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        closeButton.setOnAction(event -> {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    });
        columnName.setCellValueFactory(new PropertyValueFactory<Fuel, String>("name"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<Fuel, String>("price"));

        columnId.setCellValueFactory(new PropertyValueFactory<Car, String>("id"));
        columnModel.setCellValueFactory(new PropertyValueFactory<Car, String>("model"));
        columnFuelType.setCellValueFactory(new PropertyValueFactory<Car, String>("fuelType"));
        columnTankVolume.setCellValueFactory(new PropertyValueFactory<Car, String>("tankVolume"));

        initData();
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

        fuelTable.setItems(fuelObservableList);
        carTable.setItems(carObservableList);
    }

    public void addFuel() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/dbWorkViews/fuelParametersAdd.fxml"));
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

    public void changeFuel() throws IOException {
        fuelRepository.delete(fuelRepository.findByName(fuelTable.getSelectionModel().getSelectedItem().getName()));

        Parent root = FXMLLoader.load(getClass().getResource("/views/dbWorkViews/fuelParametersAdd.fxml"));
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

    public void removeFuel() {
        int row = fuelTable.getSelectionModel().getSelectedIndex();
        fuelRepository.delete(fuelRepository.findByName(fuelTable.getSelectionModel().getSelectedItem().getName()));
        fuelTable.getItems().remove(row);
    }

    public void addCar() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/dbWorkViews/carParametersAdd.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void changeCar() throws IOException {
        carRepository.delete(carRepository.getOne(carTable.getSelectionModel().getSelectedItem().getId()));

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

    public void updateTables() throws IOException {
        fuelTable.getItems().clear();
        carTable.getItems().clear();

        initData();
    }
}
