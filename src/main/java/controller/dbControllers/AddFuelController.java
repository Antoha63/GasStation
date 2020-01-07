package controller.dbControllers;

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
import repositories.FuelRepository;

import java.io.IOException;

public class AddFuelController {

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private FuelRepository fuelRepository = context.getBean(FuelRepository.class);

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private Button closeButton;

    public void initialize(){
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }
    public void add() throws IOException {
        Fuel fuel = new Fuel();
        fuel.setName(name.getText());
        fuel.setPrice(Integer.parseInt(price.getText()));
        fuelRepository.save(fuel);
    }
}
