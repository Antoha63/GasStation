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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.TopologyRepository;
import sun.nio.ch.WindowsAsynchronousChannelProvider;
import views.Window;
import views.WindowRepository;
import views.WindowType;

import java.io.IOException;
import java.util.List;

public class DownloadTopologyController extends Controller {
    private double xOffset;
    private double yOffset;
    private static Stage primaryStage;
    @FXML
    private Button closeButton;
    @FXML
    private Button back_button;

    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    private TopologyRepository topologyRepository = context.getBean(TopologyRepository.class);

    private List<Topology> topologyList;

    private static String topologyName;

    private ObservableList<Topology> topologyObservableList = FXCollections.observableArrayList();

    @FXML
    public TableColumn<Topology, String> columnName;

    @FXML
    private TableView<Topology> tableView;

    @FXML
    private Button buttonSelect;

    @FXML
    private Button buttonDelete;

    public static void setPrimaryStage(Stage newPrimaryStage) {
        primaryStage = newPrimaryStage;
    }

    @FXML
    public void initialize() {
        ControllersRepository.addController(ControllerType.DOWNLOADTOPOLOGYCONTROLLER, this);
        columnName.setCellValueFactory(new PropertyValueFactory<Topology, String>("name"));
        initData();
    }

    private void initData() {
        closeButton.setOnAction(event -> {
            WindowRepository.getWindow(WindowType.DOWNLOADTOPOLOGYWINDOW).close();
        });
        back_button.setOnAction(event -> {
            WindowRepository.getWindow(WindowType.DOWNLOADTOPOLOGYWINDOW).close();
            try {
                WindowRepository.getWindow(WindowType.MAINWINDOW).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        topologyList = topologyRepository.findAll();
        for (Topology topology : topologyList) {
            topology.getName();
            topologyObservableList.add(topology);
        }

        tableView.setItems(topologyObservableList);
    }

    public void selectTopology() throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() == null)
            showAlert();
        else {
            topologyName = tableView.getSelectionModel().getSelectedItem().getName();
            WindowRepository.getWindow(WindowType.CONSTRUCTORWINDOW).show();
            WindowRepository.getWindow(WindowType.DOWNLOADTOPOLOGYWINDOW).close();
        }
    }

    public void deleteTopology() throws IOException {
        if (tableView.getSelectionModel().getSelectedItem() == null)
            showAlert();
        else {
            int row = tableView.getSelectionModel().getSelectedIndex();
            topologyRepository.delete(topologyRepository.getOne(tableView.getSelectionModel().getSelectedItem().getId()));
            tableView.getItems().remove(row);
        }
    }

    public static String getTopologyName() {
        return topologyName;
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText("Сначала выберите запись из таблицы");
        alert.initStyle(StageStyle.TRANSPARENT);

        alert.showAndWait();
    }
}
