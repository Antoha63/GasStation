package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import views.WindowRepository;

import static views.WindowType.DEVINFOWINDOW;

public class DevInfoController {

    @FXML
    private Button devInfoCloseButton;

    public void initialize(){
        devInfoCloseButton.setOnAction(event -> {
            WindowRepository.getWindow(DEVINFOWINDOW).close();
        });
    }
}
