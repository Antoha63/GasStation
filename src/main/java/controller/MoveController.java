package controller;

import frameModule.FrameAnimation;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import value.ExponentialDistribution;
import visualize.Grid;
import visualize.VisualisedTransportVehicle;

import java.io.IOException;
import java.util.ArrayList;

import static topologyObjects.TransportVehicleType.*;

@Getter
/*Class describes all animation module*/
public class MoveController {
    private ImageView imageView;

    private FrameAnimation frameAnimation;
    private double poao = 0.1;

    private ArrayList<VisualisedTransportVehicle> automobiles = new ArrayList<VisualisedTransportVehicle>();
    VisualisedTransportVehicle collectorFuel;
    VisualisedTransportVehicle collectorCashBox;

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
        final int[] trigger = {0};
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                /*AUTOMOBILE*/
                i[0]++;
                trigger[0]++;
                if ((int) (exponentialDistribution.getTimes()[j[0]] * 100) == i[0]) {//TODO: fix distributions
                    VisualisedTransportVehicle visualisedTransportVehicle = new VisualisedTransportVehicle((int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateX(),
                            (int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateY(),
                            0.5, AUTOMOBILE);
                    automobiles.add(visualisedTransportVehicle);
                    root.getChildren().add(automobiles.get(numOfVehicle[0]).getFrameAnimation().getImageView());
                    numOfVehicle[0]++;
                    i[0] = 0;
                    if (j[0] == 99) j[0] = 0;
                    j[0]++;
                }
                for (int k = 0; k < automobiles.size(); k++) {
                    try {
                        if (automobiles.get(k) != null) {
                            if (automobiles.get(k).getTransportVehicle().getX() <= Grid.getGrid()[0][0].getTranslateX()) {
                                temp[0] = k;
                                numOfVehicle[0]--;
                            }
                            automobiles.get(k).go();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (automobiles.size() != 0 && temp[0] != 666) {
                    root.getChildren().remove(automobiles.get(temp[0]).getFrameAnimation().getImageView());
                    automobiles.remove(temp[0]);
                    temp[0] = 666;
                }
                /*COLLECTORFUEL*/
                if (trigger[0] == 1) {
                    collectorFuel = new VisualisedTransportVehicle((int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateX(),
                            (int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateY(),
                            0.5, COLLECTORFUEL);
                    root.getChildren().add(collectorFuel.getFrameAnimation().getImageView());
                }
                try {
                    if (collectorFuel != null) {
                        collectorFuel.go();
                        if (collectorFuel.getTransportVehicle().getX() <= Grid.getGrid()[0][0].getTranslateX()) {
                            root.getChildren().remove(collectorFuel.getFrameAnimation().getImageView());
                            collectorFuel = null;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*COLLECTORCASHBOX*/
                if (trigger[0] == 1) {
                    collectorCashBox = new VisualisedTransportVehicle((int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateX(),
                            (int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateY(),
                            0.5, COLLECTORCASHBOX);
                    root.getChildren().add(collectorCashBox.getFrameAnimation().getImageView());
                }
                try {
                    if (collectorCashBox != null) {
                        collectorCashBox.go();
                        if (collectorCashBox.getTransportVehicle().getX() <= Grid.getGrid()[0][0].getTranslateX()) {
                            root.getChildren().remove(collectorCashBox.getFrameAnimation().getImageView());
                            collectorCashBox = null;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        animationTimer.start();
    }
}