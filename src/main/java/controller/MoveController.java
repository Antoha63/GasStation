package controller;

import topologyObjects.TransportVehicle;
import frameModule.FrameAnimation;
import topologyObjects.Vehicle;
import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import visualize.Grid;
import visualize.GridElement;

import java.io.IOException;
import java.util.ArrayList;

@Getter
/*Class describes all animation module*/
public class MoveController {
    private ImageView imageView;

    private FrameAnimation frameAnimation;
    private double poao = 0.1;
    private TransportVehicle vehicle;


    private ArrayList<ImageView> imageViewList = new ArrayList<ImageView>();
    private ArrayList<Vehicle> vehicleArrayList = new ArrayList<Vehicle>();

    public MoveController() {
        frameAnimation = new FrameAnimation(2,
                                            0,
                                            100,
                                            50,
                                            3);
    }

    public void go(AnchorPane root) throws IOException {
        imageView = frameAnimation.getImageView();
        imageView.setFitWidth(80);
        imageView.setFitHeight(40);
        vehicle = new Vehicle( Grid.getX0() + (Grid.getWidth() - 1) * GridElement.getElementWidth(),
                Grid.getY0() + (Grid.getHeight()) * GridElement.getElementHeight(),
                        0.1);
        imageView.setX(vehicle.getX());
        imageView.setY(vehicle.getY());

        root.getChildren().addAll(imageView);
        /*        ExponentialDistribution exponentialDistribution = new ExponentialDistribution(1);
        final double[] i = {0};
        final int[] j = {0};
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(i[0] > 400){
                    i[0] = 0;
                }
                if((int)(exponentialDistribution.getTimes()[j[0]] * 100) == i[0]){
                    vehicleArrayList.add(new Vehicle(Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getX(),
                            Grid.getGrid()[Grid.getWidth() - 1][Grid.getHeight()].getY(), 0.1));
                    imageViewList.add(frameAnimation.getImageView());
                    j[0]++;
                }
                for(int k = 0; k < imageViewList.size(); k++){
                    Vehicle tempVehicle = vehicleArrayList.get(k);
                    tempVehicle.moveX(-1);
                    vehicleArrayList.set(k, tempVehicle);

                    ImageView tempImageView = imageViewList.get(k);
                    tempImageView.setTranslateX(vehicleArrayList.get(k).getTranslateX());
                    tempImageView.setTranslateY(vehicleArrayList.get(k).getTranslateY());
                    imageViewList.set(k, tempImageView);
                }
*//*                vehicle.moveX(-1);
                imageView.setTranslateX(vehicle.getTranslateX());
                imageView.setTranslateY(vehicle.getTranslateY());*//*
                i[0]++;*/
        AnimationTimer animationTimer = new AnimationTimer() {
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
                }
            }
        };
        animationTimer.start();
    }
}
