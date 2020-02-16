import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import views.WindowRepository;

import static views.WindowType.*;

public class MainApp extends Application {

//    private ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");

    private double xOffset;
    private double yOffset;
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        WindowRepository windowRepository = Controller.getWindowRepository();

        windowRepository.addWindow(MAINWINDOW);
        windowRepository.addWindow(SAVETOPOLOGYWINDOW);
        windowRepository.addWindow(DEVINFOWINDOW);
        windowRepository.addWindow(CONSTRUCTORWINDOW);
        windowRepository.addWindow(MODELLERWINDOW);
        windowRepository.addWindow(DBWORKWINDOW);
        windowRepository.addWindow(ADDFUELWINDOW);
        windowRepository.addWindow(ADDCARWINDOW);
        windowRepository.addWindow(IMITATIONWINDOW);

        WindowRepository.getWindow(MAINWINDOW).show();
    }
}