package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import views.WindowRepository;
import views.WindowType;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static views.WindowType.*;

public class MainController extends Controller {
    @FXML
    AnchorPane anchorPane;
    @FXML
    private Button closeButton;

    public void initialize(){
        ControllersRepository.addController(ControllerType.MAINCONTROLLER, this);
        closeButton.setOnAction(event -> {
            WindowRepository.getWindow(MAINWINDOW).close();
        });
    }

    public void createTopology() throws IOException {
        WindowRepository.getWindow(WindowType.BOUNDSWINDOW).show();
        WindowRepository.getWindow(WindowType.MAINWINDOW).close();
    }

    public void downloadTopology() throws IOException {
        WindowRepository.getWindow(WindowType.DOWNLOADTOPOLOGYWINDOW).show();
        WindowRepository.getWindow(WindowType.MAINWINDOW).close();
    }

    public void developerInfo() throws IOException {
        WindowRepository.getWindow(WindowType.DEVINFOWINDOW).show();
    }

    public void systemInfo() throws IOException {
        String url = "C:/Users/user/IdeaProjects/GasStation/src/main/resources/html/help.html";
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }
}