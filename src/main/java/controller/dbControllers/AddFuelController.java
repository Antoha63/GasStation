package controller.dbControllers;

import controller.Controller;
import controller.ControllerType;
import controller.ControllersRepository;
import entities.Fuel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.FuelRepository;
import views.Window;
import views.WindowRepository;
import views.WindowType;

import java.io.IOException;

public class AddFuelController extends Controller {

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private FuelRepository fuelRepository = context.getBean(FuelRepository.class);

    private double xOffset;
    private double yOffset;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private Button closeButton;

    public void initialize() {
        ControllersRepository.addController(ControllerType.ADDFUELCONTROLLER, this);
        closeButton.setOnAction(event -> {
            closeWindow();
        });

        if (DBWorkController.getName() != null || DBWorkController.getPrice() != 0) {
            name.setText(DBWorkController.getName());
            price.setText(String.valueOf(DBWorkController.getPrice()));
        }
    }

    public void add() throws IOException {
        if (DBWorkController.getName() != null)
            fuelRepository.delete(fuelRepository.findByName(DBWorkController.getName()));

        Fuel fuel = new Fuel();
        fuel.setName(name.getText());
        try {
            fuel.setPrice(Double.parseDouble(price.getText()));
            fuelRepository.save(fuel);

            closeWindow();
        } catch (NumberFormatException e) {
            showAlert();
        }
        DBWorkController.setName(null);
        DBWorkController.setPrice(0);
    }

    private void closeWindow() {
        WindowRepository.getWindow(WindowType.ADDFUELWINDOW).close();
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
