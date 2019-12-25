package visualize;

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

    public void createElement(ElementType type){
        switch(type){
            case PETROLSTATION:
                this.mainStaticElement = new PetrolStation(i, j);
                System.out.println(this.mainStaticElement);
                this.frameAnimation = new FrameAnimation(0, 3, 50, 50, 3);
                break;
            case FUELTANK:
                this.mainStaticElement = new FuelTank(i, j);
                System.out.println(this.mainStaticElement);
                this.frameAnimation = new FrameAnimation(1, 3, 50, 50, 3);
                break;
            case CASHBOX:
                this.mainStaticElement = new CashBox(i, j);
                System.out.println(this.mainStaticElement);
                this.frameAnimation = new FrameAnimation(2, 3, 50, 50, 3);
                break;
            case EXIT:
                this.mainStaticElement = new Exit(i, j);
                System.out.println(this.mainStaticElement);
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
                break;
            case ENTRY:
                this.mainStaticElement = new Entry(i, j);
                System.out.println(this.mainStaticElement);
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
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

}
