package visualize;

import controller.ImitationController;
import elements.CashBox;
import elements.Entry;
import elements.Exit;
import elements.FuelTank;
import elements.PetrolStation;
import elements.ElementType;
import elements.mainElement.MainStaticElement;
import frameModule.FrameAnimation;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

import static elements.ElementType.*;

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
//        if(frameAnimation != null)
//            this.getChildren().add(this.frameAnimation.getImageView());
    }

    public void createElement(ElementType type, double rotate) {
        switch (type) {
            case PETROLSTATION:
                this.mainStaticElement = new PetrolStation(i, j);
                mainStaticElement.setElementType(PETROLSTATION);
                this.setOccupied(true);
                System.out.println(this.mainStaticElement);
                this.frameAnimation = new FrameAnimation(0, 3, 50, 50, 3);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case FUELTANK:
                this.mainStaticElement = new FuelTank(i, j);
                mainStaticElement.setElementType(FUELTANK);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(1, 3, 50, 50, 3);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case CASHBOX:
                this.mainStaticElement = new CashBox(i, j);
                CashBox.setStatus(true);
                mainStaticElement.setElementType(CASHBOX);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(2, 3, 50, 50, 3);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case EXIT:
                this.mainStaticElement = new Exit(i, j, true);
                mainStaticElement.setElementType(EXIT);
                Exit.setStatus(true);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case ENTRY:
                this.mainStaticElement = new Entry(i, j, true);
                mainStaticElement.setElementType(ENTRY);
                Entry.setStatus(true);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case TURNROAD:
                //mainStaticElement.setElementType(TURNROAD);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(5, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case ROAD:
                //mainStaticElement.setElementType(ROAD);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation((int) (rand.nextDouble() * 3), 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case CROSSROAD:
                //this.getMainStaticElement().setElementType(CROSSROAD);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case EMPTYPLACE:
                //this.getMainStaticElement().setElementType(EMPTYPLACE);
                this.setOccupied(false);
                this.frameAnimation = new FrameAnimation(3, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
        }
    }

    public void deleteElement() {
        //if (!elementType.equals(ROAD) && !elementType.equals(CROSSROAD))
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
