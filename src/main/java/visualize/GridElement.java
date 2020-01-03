package visualize;

import elements.CashBox;
import elements.Entry;
import elements.Exit;
import elements.FuelTank;
import elements.PetrolStation;
import elements.ElementType;
import elements.mainElement.MainStaticElement;
import frameModule.FrameAnimation;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class GridElement extends Pane {
    private boolean isOccupied;
    private static int width;
    private static int height;
    private int i;
    private int j;
    private MainStaticElement mainStaticElement;
    private FrameAnimation frameAnimation;
    private static Random rand = new Random();

    public GridElement(int i, int j, boolean isOccupied){
        this.isOccupied = isOccupied;
        this.setPrefWidth(50);
        this.setPrefHeight(50);
        this.i = i;
        this.j = j;
        this.setTranslateX(Grid.getX0() + i * GridElement.getElementWidth());
        this.setTranslateY(Grid.getY0() + j * GridElement.getElementHeight());
        width = (int)getPrefWidth();
        height = (int)getPrefHeight();
    }

    public void createElement(ElementType type, double rotate){
        switch(type){
            case PETROLSTATION:
                this.mainStaticElement = new PetrolStation(i, j);
                this.setOccupied(true);
                System.out.println(this.mainStaticElement);
                this.frameAnimation = new FrameAnimation(0, 3, 50, 50, 3);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case FUELTANK:
                this.mainStaticElement = new FuelTank(i, j);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(1, 3, 50, 50, 3);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case CASHBOX:
                this.mainStaticElement = new CashBox(i, j);
                this.setOccupied(true);
                System.out.println(this.mainStaticElement);
                this.frameAnimation = new FrameAnimation(2, 3, 50, 50, 3);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case EXIT:
                Exit.setX(i);
                Exit.setY(j);
                Exit.setFlag(true);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case ENTRY:
                Entry.setX(i);
                Entry.setY(j);
                Entry.setFlag(true);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case TURNROAD:
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(5, 4,50,50,6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case ROAD:
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation((int) (rand.nextDouble() * 3),4,50,50,6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case CROSSROAD:
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(4,4,50,50,6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case EMPTYPLACE:
                this.setOccupied(false);
                this.frameAnimation = new FrameAnimation(3,4,50,50,6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
        }
    }

    public void deleteElement(){
        frameAnimation = null;
        mainStaticElement = null;
    }

    public static int getElementWidth(){
        return width;
    }
    public static int getElementHeight(){
        return height;
    }
    public boolean getIsOccupied(){
        return isOccupied;
    }
}
