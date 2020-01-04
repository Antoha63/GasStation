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

    private ArrayList<VisualisedTransportVehicle> vehicleArrayList = new ArrayList<VisualisedTransportVehicle>();

    public MoveController() {
        frameAnimation = new FrameAnimation(2,
                0,
                100,
                50,
                3);
    }

    public void go(AnchorPane root) throws IOException {
        ExponentialDistribution exponentialDistribution = new ExponentialDistribution(1);

        final int[] i = {0};
        final int[] j = {0};
        final int[] numOfVehicle = {0};
        final int[] temp = {666};
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if(i[0]>=350){i[0] = 0;}
                i[0]++;
                if((int)(exponentialDistribution.getTimes()[j[0]] * 100) == i[0]){
                    VisualisedTransportVehicle visualisedTransportVehicle = new VisualisedTransportVehicle((int)Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateX(),
                            (int)Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateY(),
                            0.1, AUTOMOBILE);
                    vehicleArrayList.add(visualisedTransportVehicle);
                    root.getChildren().add(vehicleArrayList.get(numOfVehicle[0]).getFrameAnimation().getImageView());
                    numOfVehicle[0]++;
                    j[0]++;
                }
                for(int k = 0; k < vehicleArrayList.size(); k++) {
                    try {
                        if (vehicleArrayList.get(k) != null) {
                            if (vehicleArrayList.get(k).getTransportVehicle().getX() <= Grid.getGrid()[0][0].getTranslateX()) {
                                temp[0] = k;
                                numOfVehicle[0]--;
                            }
                            vehicleArrayList.get(k).go();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (vehicleArrayList.size()!=0 && temp[0] != 666) {
                    root.getChildren().remove(vehicleArrayList.get(temp[0]).getFrameAnimation().getImageView());
                    vehicleArrayList.remove(temp[0]);
                    temp[0] = 666;
                }
            }
        };
        animationTimer.start();
    }
}