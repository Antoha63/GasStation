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

    private double xOffset;
    private double yOffset;

    @FXML
    private TextField name;

    @FXML
    private TextField price;

    @FXML
    private Button closeButton;

    public void initialize() {
        closeButton.setOnAction(event -> {
            setCloseButton();
        });

        if (DBWorkController.getName() != null || DBWorkController.getPrice() != 0) {
            name.setText(DBWorkController.getName());
            price.setText(String.valueOf(DBWorkController.getPrice()));
        }
    }

    public void add() throws IOException {
        if (DBWorkController.getName() != null) {
            fuelRepository.delete(fuelRepository.findByName(DBWorkController.getName()));
        }
        Fuel fuel = new Fuel();
        fuel.setName(name.getText());
        fuel.setPrice(Integer.parseInt(price.getText()));
        fuelRepository.save(fuel);

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

        DBWorkController.setName(null);
        DBWorkController.setPrice(0);
    }

    private void setCloseButton() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
