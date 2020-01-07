package controller.dbControllers;

import entities.Car;
import entities.Fuel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.CarRepository;
import repositories.FuelRepository;

public class AddCarController {

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private CarRepository carRepository = context.getBean(CarRepository.class);

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
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }
    public void add() {
        Car car = new Car();
        car.setModel(model.getText());
        car.setFuelType(fuelType.getText());
        car.setTankVolume(Integer.parseInt(tankVolume.getText()));
        carRepository.save(car);
    }
}
