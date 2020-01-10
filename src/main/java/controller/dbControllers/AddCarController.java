package controller.dbControllers;

import entities.Car;
import entities.Fuel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.CarRepository;
import repositories.FuelRepository;

import java.io.IOException;

public class AddCarController {

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private CarRepository carRepository = context.getBean(CarRepository.class);

    private double xOffset;
    private double yOffset;

    @FXML
    private TextField model;

    @FXML
    private TextField fuelType;

    @FXML
    private TextField tankVolume;

    @FXML
    private Button closeButton;

    public void initialize(){
        closeButton.setOnAction(event -> {
            setCloseButton();
        });
    }
    public void add() throws IOException {
        Car car = new Car();
        car.setModel(model.getText());
        car.setFuelType(fuelType.getText());
        car.setTankVolume(Integer.parseInt(tankVolume.getText()));
        carRepository.save(car);

        setCloseButton();

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

    private void setCloseButton(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
