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
//        if(frameAnimation != null)
//            this.getChildren().add(this.frameAnimation.getImageView());
    }

    public void setEvents(){
        setDragOverEvent();
        setDragNDropEvent();
        setMouseClickEvent();
    }

    public void setDragOverEvent() {
        this.setOnDragOver(event -> {
            switch (event.getDragboard().getString()){
                case "entry":
                case "exit":
                    event.acceptTransferModes(TransferMode.COPY);
                    break;
                case "petrolStation":
                case "cashBox":
                case "fuelTank":
                    if (event.getDragboard().hasString() && !isOccupied
                            && Exit.getStatus() && Entry.getStatus()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                    break;
            }
        });
    }

    public void setDragNDropEvent() {
        this.setOnDragDropped(event -> {
            switch (event.getDragboard().getString()) {
                case "entry":
                    if(!Entry.getStatus())
                        createElement(ENTRY, 180);
                    break;
                case "exit":
                    if (i <= Entry.getX() && Entry.getStatus() && !Exit.getStatus())
                        createElement(EXIT, 180);
                    if (Entry.getStatus() && Exit.getStatus() && Entry.getX() > Exit.getX()) {
                        ConstructorController constructorController =
                                (ConstructorController) ControllersRepository.
                                        getController(ControllerType.CONSTRUCTORCONTROLLER);
                        constructorController.disableElements(false);
                        Grid.setRoundRoad();
                    }
                    break;
                case "petrolStation":
                    if(Exit.getStatus() && Entry.getStatus()){
                        createElement(PETROLSTATION, 0);
                        Grid.setPetrolRoad(i, j);
                    }
                    break;
                case "cashBox":
                    if(Exit.getStatus() && Entry.getStatus()){
                        createElement(CASHBOX, 0);
                    }
                    break;
                case "fuelTank":
                    if(Exit.getStatus() && Entry.getStatus()){
                        createElement(FUELTANK, 0);
                    }
                    break;
            }
        });
    }

    public void setMouseClickEvent() {
        this.setOnMouseClicked(event -> {
            switch (mainStaticElement.getElementType()){
                case ENTRY:
                    if(isOccupied){
                        System.out.println(3);
                        Grid.removeRoundRoad();
                        this.deleteElement();
                        Entry.setX(0);
                        Entry.setY(0);
                        Entry.setStatus(false);
                    }
                    break;
                case EXIT:
                    if(isOccupied){
                        Grid.removeRoundRoad();
                        this.deleteElement();
                        Exit.setX(0);
                        Exit.setY(0);
                        Exit.setStatus(false);
                    }
                    break;
                case CASHBOX:
                    if(mainStaticElement != null)
                        this.deleteElement();
                    break;
                case PETROLSTATION:
                    if(mainStaticElement != null){
                        Grid.removePetrolRoad(i, j);
                        Grid.getListOfPetrolStations().remove(mainStaticElement);
                        this.deleteElement();
                    }
                    break;
                case FUELTANK:
                    if(mainStaticElement != null){
                        Grid.getListOfFuelTanks().remove(mainStaticElement);
                        this.deleteElement();
                    }
                    break;
            }
        });
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
                Exit.setStatus(true);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation(4, 4, 50, 50, 6);
                this.frameAnimation.getImageView().setRotate(rotate);
                this.getChildren().add(this.frameAnimation.getImageView());
                break;
            case ENTRY:
                this.mainStaticElement = new Entry(i, j, true);
                this.mainStaticElement.setElementType(ENTRY);
                Entry.setStatus(true);
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
                //mainStaticElement.setElementType(ROAD);
                this.setOccupied(true);
                this.frameAnimation = new FrameAnimation
                        ((int) (rand.nextDouble() * 3),
                                4, 50, 50, 6);
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
        System.out.println(mainStaticElement);
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
