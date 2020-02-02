package controller;

import TimeControl.TimeState;
import elements.CashBox;
import elements.FuelTank;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;
import value.DeterministicDistribution;
import value.Distribution;
import value.ExponentialDistribution;
import visualize.Grid;
import visualize.VisualisedTransportVehicle;

import java.io.IOException;
import java.util.ArrayList;

import static topologyObjects.TransportVehicleType.*;

@Getter
@Setter
/*Class describes all animation module*/
public class MoveController extends Controller {
    private ArrayList<VisualisedTransportVehicle> automobiles = new ArrayList<VisualisedTransportVehicle>();
    private VisualisedTransportVehicle collectorFuel;
    private VisualisedTransportVehicle collectorCashBox;
    private static Distribution distribution = new ExponentialDistribution(1);
    private AnimationTimer animationTimer;
    private TimeState timeState;

    public static void setDistribution(Distribution distribution) {
        MoveController.distribution = distribution;
    }

    public void go(AnchorPane root) throws IOException {

        distribution.modelFunc();

        final int[] i = {0};
        final int[] j = {0};
        final int[] numOfVehicle = {0};
        final int[] temp = {666};
        final int[] trigger = {0};
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                switch (timeState) {
                    case START:
                        trigger[0]++;
                        while ((int) (distribution.getTimes()[j[0]] * 61) == i[0]) {//TODO: fix distributions
                            VisualisedTransportVehicle visualisedTransportVehicle =
                                    new VisualisedTransportVehicle(
                                            (int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateX(),
                                            (int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateY(),
                                            AUTOMOBILE);
                            automobiles.add(visualisedTransportVehicle);
                            root.getChildren().add(automobiles.get(numOfVehicle[0]).getFrameAnimation().getImageView());
                            numOfVehicle[0]++;
                            i[0] = 0;
                            if (j[0] == 99) j[0] = 0;
                            j[0]++;
                        }
                        i[0]++;
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
                        for (FuelTank ft : Grid.getListOfFuelTanks()){
                            if (ft.checkFuelTank()) {
                                collectorFuel = new VisualisedTransportVehicle((int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateX(),
                                        (int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateY(), COLLECTORFUEL, ft.getFuel());
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
                        }
                        /*COLLECTORCASHBOX*/
                        if (CashBox.checkCashbox()) {
                            collectorCashBox = new VisualisedTransportVehicle((int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateX(),
                                    (int) Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getTranslateY(), COLLECTORCASHBOX);
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
                        break;
                    case STOP:
                        animationTimer.stop();
                        distribution.modelFunc();
                        for (VisualisedTransportVehicle automobile : automobiles){
                            root.getChildren().remove(automobile.getFrameAnimation().getImageView());
                        }
                        for (int i = 0; i < automobiles.size(); i++){
                            automobiles.remove(i);
                        }
                        if (collectorCashBox != null)
                            root.getChildren().remove(collectorCashBox.getFrameAnimation().getImageView());
                        if (collectorFuel != null)
                            root.getChildren().remove(collectorFuel.getFrameAnimation().getImageView());

                        i[0] = 0;
                        j[0] = 0;
                        numOfVehicle[0] = 0;
                        temp[0] = 666;
                        trigger[0] = 0;
                        return;
                    case PLAY:
                        break;
                    case PAUSE:
                        break;
                }
                /*AUTOMOBILE*/

            }
        };
        animationTimer.start();
    }
}