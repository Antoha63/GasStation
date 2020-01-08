package controller;

import entities.Car;
import entities.Fuel;
import entities.Topology;
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
import repositories.TopologyRepository;

import java.io.IOException;
import java.util.List;

public class DownloadTopologyController {
    private double xOffset;
    private double yOffset;
    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private TopologyRepository topologyRepository = context.getBean(TopologyRepository.class);

    private List<Topology> topologyList;

    private static String topologyName;

    private ObservableList<Topology> topologyObservableList = FXCollections.observableArrayList();
    private static Stage primaryStage = new Stage();

    @FXML
    public TableColumn<Topology, String> columnName;

    @FXML
    private TableView<Topology> tableView;

    @FXML
    private Button button;

    @FXML
    public void initialize(){
        columnName.setCellValueFactory(new PropertyValueFactory<Topology, String>("name"));
        initData();
    }

    private void initData() {
        topologyList = topologyRepository.findAll();
        for (Topology topology: topologyList) {
            topology.getName();
            topologyObservableList.add(topology);
        }

        tableView.setItems(topologyObservableList);
    }

    public void selectTopology() throws IOException {
        Topology topology = topologyRepository.findByName(tableView.getSelectionModel().getSelectedItem().getName());
        topologyName = tableView.getSelectionModel().getSelectedItem().getName();
        System.out.println(topology.getWidth() + "     " + topology.getHeight());

        ConstructorController constructorController = new ConstructorController();
        constructorController.setBounds(topology.getWidth(), topology.getHeight());
        primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
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
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static String getTopologyName(){
        return topologyName;
    }
}
