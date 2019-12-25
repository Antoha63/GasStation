package controller;

import topologyObjects.TransportVehicle;
import frameModule.FrameAnimation;
import topologyObjects.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import value.ExponentialDistribution;
import visualize.Grid;
import visualize.GridElement;
import visualize.VisualisedTransportVehicle;

import java.io.IOException;
import java.util.ArrayList;

import static topologyObjects.TransportVehicleType.AUTOMOBILE;

@Getter
/*Class describes all animation module*/
public class MoveController {
    private ImageView imageView;

    private FrameAnimation frameAnimation;
    private double poao = 0.1;
    private TransportVehicle vehicle;


    private ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
    private ArrayList<VisualisedTransportVehicle> vehicleArrayList = new ArrayList<VisualisedTransportVehicle>();

    public void go(AnchorPane root) throws IOException {

        ExponentialDistribution exponentialDistribution = new ExponentialDistribution(1);

        final int[] j = {0};
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if((int) exponentialDistribution.getTimes()[j[0]]== 0){
                    VisualisedTransportVehicle visualisedTransportVehicle = new VisualisedTransportVehicle();
                    visualisedTransportVehicle.createTransportVehicle((int)Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateX(),
                            (int)Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateY(), 0.1, AUTOMOBILE);
                    vehicleArrayList.add(visualisedTransportVehicle);

                }
                if(j[0]>=99){j[0] = 0;}
                j[0]++;
                for(int k = 0; k < vehicleArrayList.size(); k++) {
                    try {
                        if (vehicleArrayList.get(k) != null) {
                            vehicleArrayList.get(k).go();
                            if (vehicleArrayList.get(k).getTransportVehicle().getX() <= Grid.getGrid()[0][0].getTranslateX()) {
                                root.getChildren().remove(imageView);
                                vehicleArrayList.remove(k);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

/*        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    if(vehicle != null) {
                        vehicle.go(imageView);
                        if (vehicle.getX() <= Grid.getGrid()[0][0].getTranslateX()) {
                            root.getChildren().remove(imageView);
                            vehicle = null;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        };
        animationTimer.start();
    }
}
