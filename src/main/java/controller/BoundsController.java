package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import views.WindowRepository;
import views.WindowType;
import visualize.Grid;

import java.io.IOException;

public class BoundsController extends Controller{
    @Getter
    @FXML
    private Spinner<Integer> topologyHeight;
    @Getter
    @FXML
    private Spinner<Integer> topologyWidth;
    @FXML
    private Button back_button;
    @FXML
    private Button closeButton;
    @FXML
    private Button okButton;

    public void initialize() {
        closeButton.setOnAction(event -> {
            WindowRepository.getWindow(WindowType.BOUNDSWINDOW).close();
        });
        back_button.setOnAction(event -> {
            WindowRepository.getWindow(WindowType.BOUNDSWINDOW).close();
            try {
                WindowRepository.getWindow(WindowType.MAINWINDOW).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void createConstructor() throws IOException {
        ControllersRepository.addController(ControllerType.BOUNDSCONTROLLER, this);
        WindowRepository.getWindow(WindowType.CONSTRUCTORWINDOW).show();
        WindowRepository.getWindow(WindowType.BOUNDSWINDOW).close();
    }
}
