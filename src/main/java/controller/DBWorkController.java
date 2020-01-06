package controller;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.FuelRepository;

import java.io.IOException;
import java.util.List;

public class DBWorkController {

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private FuelRepository fuelRepository = context.getBean(FuelRepository.class);
    private ObservableList<Fuel> fuelObservableList = FXCollections.observableArrayList();
    private List<Fuel> fuelList;

    @FXML
    public TableView<Fuel> fuelTable;

    @FXML
    public TableColumn<Fuel, String> columnId;
    public TableColumn<Fuel, String> columnName;
    public TableColumn<Fuel, String> columnPrice;

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

        fuelTable.setItems(fuelObservableList);
    }

    private void initData() {
        fuelList = fuelRepository.findAll();
        for (Fuel fuel: fuelList) {
            fuel.getName();
            fuel.getPrice();
            fuelObservableList.add(fuel);
        }
    }

    public void addFuel() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/fuelParametersAdd.fxml"));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void removeFuel() {
        System.out.println(fuelTable.getSelectionModel().getSelectedIndex());
        int row = fuelTable.getSelectionModel().getSelectedIndex();
        fuelTable.getItems().remove(row);
    }
}
