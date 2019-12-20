package animation;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import topologyObjects.TransportVehicle;
import animation.framePackage.FrameAnimation;
import topologyObjects.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import lombok.Getter;
import visualize.Grid;

import java.io.IOException;

@Getter
/*Class describes all animation module*/
public class MoveController {
    private Rectangle2D[] viewports;
    private ImageView imageView;

    private int topologyHeight;
    private int topologyWidth;
    private FrameAnimation frameAnimation;
    private double poao = 0.1;
    private TransportVehicle vehicle;
    //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    //CarRepository carRepository = context.getBean(CarRepository.class);
    //FuelRepository fuelRepository = context.getBean(FuelRepository.class);

    public MoveController(int topologyHeight, int topologyWidth) {
        this.topologyHeight = topologyHeight;
        this.topologyWidth = topologyWidth;
        frameAnimation = new FrameAnimation(0, 0);
        viewports = frameAnimation.getViewports();
    }

    public void go() throws IOException {
        Stage primaryStage = new Stage();

        AnchorPane root = FXMLLoader.load(getClass().getResource("/views/constructor.fxml"));
        primaryStage.setTitle("КОНСТРУКТОР");
        int x0 = 270;
        int y0 = 25;
        Grid.setGrid(x0, y0, topologyHeight, topologyWidth);
        for (Line line : Grid.getLineList()) {
            root.getChildren().add(line);
        }

        imageView = frameAnimation.getImageView();
        vehicle = new Vehicle(Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getX(),
                Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getY(), 0.1);
        imageView.setX(vehicle.getX());
        imageView.setY(vehicle.getY());

        root.getChildren().addAll(imageView);
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                vehicle.moveX(-1);
                imageView.setTranslateX(vehicle.getTranslateX());
                imageView.setTranslateY(vehicle.getTranslateY());
            }
        };
        animationTimer.start();

        primaryStage.setScene(new Scene(root, 1000, 500));
        primaryStage.show();
    }
}
