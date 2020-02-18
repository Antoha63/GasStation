package visualize;

import controller.ConstructorController;
import controller.ControllerType;
import controller.ControllersRepository;
import controller.ImitationController;
import elements.CashBox;
import elements.Entry;
import elements.Exit;
import elements.FuelTank;
import elements.PetrolStation;
import elements.ElementType;
import elements.mainElement.MainStaticElement;
import frameModule.FrameAnimation;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

import static elements.ElementType.*;
import static visualize.Grid.setRoundRoad;

@Getter
@Setter
public class GridElement extends Pane {
    private boolean isOccupied;
    private static int width = 50;
    private static int height = 50;
    private int i;
    private int j;
    private MainStaticElement mainStaticElement;
    private FrameAnimation frameAnimation;
    private static Random rand = new Random();

    public GridElement(int i, int j, boolean isOccupied) {
        this.isOccupied = isOccupied;
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.i = i;
        this.j = j;
        this.setTranslateX(Grid.getX0() + i * GridElement.getElementWidth());
        this.setTranslateY(Grid.getY0() + j * GridElement.getElementHeight());
    }

    public void createElement(ElementType type, double rotate) {
        switch (type) {
            case PETROLSTATION:
                this.mainStaticElement = new PetrolStation(i, j);
                this.mainStaticElement.setElementType(PETROLSTATION);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(0, 3, 50, 50, 3);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case FUELTANK:
                this.mainStaticElement = new FuelTank(i, j);
                this.mainStaticElement.setElementType(FUELTANK);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(1, 3, 50, 50, 3);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case CASHBOX:
                this.mainStaticElement = new CashBox(i, j);
                CashBox.setStatus(true);
                this.mainStaticElement.setElementType(CASHBOX);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(2, 3, 50, 50, 3);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case EXIT:
                this.mainStaticElement = new Exit(i, j, true);
                this.mainStaticElement.setElementType(EXIT);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case ENTRY:
                this.mainStaticElement = new Entry(i, j, true);
                this.mainStaticElement.setElementType(ENTRY);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case TURNROAD:
                //mainStaticElement.setElementType(TURNROAD);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation
                        (5, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case ROAD:
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation
                        ((int) (rand.nextDouble() * 3),
                                4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case CROSSROAD:
                this.setOccupied(true);
                if(this.frameAnimation != null &&
                        this.frameAnimation.getImageOffsetX() < 3 &&
                        this.frameAnimation.getImageOffsetY() == 4)
                    this.getChildren().remove(this.getChildren().size() - 1); //deleting road image from pane
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case EMPTYPLACE:
                this.setOccupied(false);
                this.frameAnimation = new FrameAnimation(3, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
        }
    }

    public void deleteElement() {
        this.getChildren().remove(frameAnimation.getImageView());
        frameAnimation = null;
        mainStaticElement = null;
        isOccupied = false;
    }

    public static int getElementWidth() {
        return width;
    }

    public static int getElementHeight() {
        return height;
    }

    public boolean getIsOccupied() {
        return isOccupied;
    }
}
