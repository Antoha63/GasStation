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

    private FrameAnimation frameAnimation;
    private double poao = 0.1;
    private TransportVehicle vehicle;

    public MoveController() {
        frameAnimation = new FrameAnimation(0, 0);
        viewports = frameAnimation.getViewports();
    }

    public void go(AnchorPane root) throws IOException {
        imageView = frameAnimation.getImageView();
        vehicle = new Vehicle(Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getX(),
                Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getY(), 0.1);
        imageView.setX(vehicle.getX());
        imageView.setY(vehicle.getY());

        root.getChildren().addAll(imageView);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    if(vehicle != null) {
                        vehicle.go(imageView);
                        if (vehicle.getX() <= Grid.getGrid()[0][0].getX()) {
                            root.getChildren().remove(imageView);
                            vehicle = null;
                        }
                        System.out.println("+++++++++++++++");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        animationTimer.start();
    }
}
