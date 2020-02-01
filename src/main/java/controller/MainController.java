package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import views.WindowRepository;
import views.WindowType;

import java.io.IOException;

import static views.WindowType.BOUNDSWINDOW;
import static views.WindowType.DOWNLOADTOPOLOGYWINDOW;

public class MainController extends Controller {

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Button closeButton;

    public void initialize(){
        ControllersRepository.addController(ControllerType.MAINCONTROLLER, this);
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    public void createTopology() throws IOException {
        WindowRepository windowRepository = Controller.getWindowRepository();
        windowRepository.addWindow(BOUNDSWINDOW);
        WindowRepository.getWindow(WindowType.BOUNDSWINDOW).show();
        WindowRepository.getWindow(WindowType.MAINWINDOW).close();
    }

    public void downloadTopology() throws IOException {
        WindowRepository windowRepository = Controller.getWindowRepository();
        windowRepository.addWindow(DOWNLOADTOPOLOGYWINDOW);
        WindowRepository.getWindow(WindowType.DOWNLOADTOPOLOGYWINDOW).show();
        WindowRepository.getWindow(WindowType.MAINWINDOW).close();
    }

    public void developerInfo() throws IOException {
        WindowRepository.getWindow(WindowType.DEVINFOWINDOW).show();
    }

    public void systemInfo() throws IOException {
        WindowRepository.getWindow(WindowType.SYSINFOWINDOW).show();
    }
}
