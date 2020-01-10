package controller.dbControllers;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.CarRepository;
import repositories.FuelRepository;

import java.io.IOException;
import java.util.List;

public class AddCarController {

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
        closeButton.setOnAction(event -> {
            setCloseButton();
            try {
                getDBWorkStage();
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
        comboBox.setValue(fuelTypes.get(0));
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

            setCloseButton();
            getDBWorkStage();
        } catch (NumberFormatException e) {
            showAlert();
        }
        DBWorkController.setModel(null);
        DBWorkController.setTankVolume(0);
    }

    private void setCloseButton() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void getDBWorkStage() throws IOException {
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

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText("Неверный тип данных");
        alert.initStyle(StageStyle.TRANSPARENT);

        alert.showAndWait();
    }
}
