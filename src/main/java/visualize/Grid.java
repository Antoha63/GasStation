package visualize;

import controller.ConstructorController;
import controller.ControllerType;
import controller.ControllersRepository;
import elements.*;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static elements.ElementType.CASHBOX;
import static elements.ElementType.CROSSROAD;
import static elements.ElementType.EMPTYPLACE;
import static elements.ElementType.ENTRY;
import static elements.ElementType.EXIT;
import static elements.ElementType.FUELTANK;
import static elements.ElementType.PETROLSTATION;
import static elements.ElementType.ROAD;
import static elements.ElementType.TURNROAD;

@Setter
@Getter
public class Grid {
    private static int x0;
    private static int y0;
    private static int x;
    private static int y;
    @Setter
    private static int width;
    @Setter
    private static int height;

    static {
        width = 0;
        height = 0;
    }

    private static GridElement[][] grid;
    private static GridElement gridElement;

    @Getter
    @Setter
    private static List<PetrolStation> listOfPetrolStations = new ArrayList<>();
    @Getter
    @Setter
    private static List<FuelTank> listOfFuelTanks = new ArrayList<>();
    @Getter
    private static List<Line> lines;
    private static Random rand = new Random();

    public static void initGrid(int topologyWidth, int topologyHeight) {
        x0 = 220;
        y0 = 40;
        width = topologyWidth;
        height = topologyHeight;
        grid = new GridElement[width][height + 1];

        /*Инициализация*/
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height + 1; j++) {
                grid[i][j] = new GridElement(i, j, false);
            }
        }
        lines = getLineList();

        setEntryExitEvents(); /*Область въезда и выезда*/
        setStationRoad();
        setInputCrossRoad();
    }

    private static void setEntryExitEvents() {
        ConstructorController constructorController = (ConstructorController)
                ControllersRepository.getController(ControllerType.CONSTRUCTORCONTROLLER);
        for (int k = 1; k < width - 3; k++) {
            int finalK = k;

            grid[k][Grid.height].setOnDragOver(event -> {
                if (event.getDragboard().hasString() ) {
                    switch (event.getDragboard().getString()) {
                        case "entry":
                            if(finalK >= 3) event.acceptTransferModes(TransferMode.COPY);
                        case "exit":
                            if (finalK < Entry.getX() - 1) event.acceptTransferModes(TransferMode.COPY);
                            break;
                    }
                }
            });
            grid[k][Grid.height].setOnDragDropped(event -> {
                switch (event.getDragboard().getString()) {
                    case "exit":
                        grid[finalK][Grid.height].createElement(EXIT, 180);
                        constructorController.disableExit(true);
                        setRoundRoad();
                        constructorController.disablePetrolStation(false);
                        constructorController.disableCashBox(false);
                        constructorController.disableFuelTank(false);
                        break;
                    case "entry":
                        grid[finalK][Grid.height].createElement(ENTRY, 180);
                        constructorController.disableExit(false);
                        constructorController.disableEntry(true);
                        break;
                }
                if (constructorController.getEntry().isDisable() &&
                        constructorController.getExit().isDisable() && Entry.getX() > Exit.getX()) {
                    /*Область трк*/
                    setPetrolStationsEvents();
                    /*Область кассы*/
                    setCashBoxEvents();
                    /*Область топливных баков*/
                    setFuelTanksEvents();
                }
            });
            grid[k][Grid.height].setOnMouseClicked(event -> deleteEntryExit(finalK));
        }
    }

    public static void setPetrolStationsEvents() {
        ConstructorController constructorController = (ConstructorController)
                ControllersRepository.getController(ControllerType.CONSTRUCTORCONTROLLER);
        for (int i = 0; i < width - 3; i++)
            for (int j = 0; j < height; j++) {
                int finalI = i;
                int finalJ = j;

                grid[i][j].setOnDragOver(dragEvent -> {
                    if (dragEvent.getDragboard().hasString() &&
                            !grid[finalI][finalJ].getIsOccupied()) {
                        if ("petrolStation".equals(dragEvent.getDragboard().getString())) {
                            if (finalI <= Entry.getX() && finalI >= Exit.getX())
                                dragEvent.acceptTransferModes(TransferMode.COPY);
                        }
                    }
                });
                grid[i][j].setOnDragDropped(dragEvent -> {
                    if(dragEvent.getDragboard().getString().equals("petrolStation")) {
                        grid[finalI][finalJ].createElement(PETROLSTATION, 0);
                        setPetrolRoad(finalJ);
                        if(listOfPetrolStations.size() > 3) constructorController.disablePetrolStation(true);
                    }
                });
                grid[i][j].setOnMouseClicked(dragEvent -> {
                    deletePetrolStation(finalI, finalJ);
                });
            }
    }

    public static void setCashBoxEvents() {
        ConstructorController constructorController = (ConstructorController)
                ControllersRepository.getController(ControllerType.CONSTRUCTORCONTROLLER);
        for (int j = 0; j < height; j++) {
            int finalJ = j;
            grid[Exit.getX() - 1][j].setOnDragOver(dragEvent -> {
                    if ("cashBox".equals(dragEvent.getDragboard().getString())) {
                        dragEvent.acceptTransferModes(TransferMode.COPY);
                    }
            });
            grid[Exit.getX() - 1][j].setOnDragDropped(dragEvent -> {
                    if ("cashBox".equals(dragEvent.getDragboard().getString())) {
                        grid[Exit.getX() - 1][finalJ].createElement(CASHBOX, 0);
                        CashBox.setSetted(true);
                    }
                if(CashBox.getSetted()) constructorController.disableCashBox(true);
            });
            grid[Exit.getX() - 1][j].setOnMouseClicked(dragEvent -> {
                deleteCashBox(finalJ);
            });
        }
    }

    public static void setFuelTanksEvents() {
        ConstructorController constructorController = (ConstructorController)
                ControllersRepository.getController(ControllerType.CONSTRUCTORCONTROLLER);
        for (int j = 1; j < height; j++) {
            int finalJ = j;
            grid[width - 2][j].setOnDragOver(dragEvent -> {
                if (dragEvent.getDragboard().hasString() &&
                        dragEvent.getDragboard().getString().equals("fuelTank") &&
                        !grid[width - 2][finalJ].getIsOccupied()) {
                    dragEvent.acceptTransferModes(TransferMode.COPY);
                }
            });
            grid[width - 2][j].setOnDragDropped(dragEvent -> {
                    if ("fuelTank".equals(dragEvent.getDragboard().getString())) {
                        grid[width - 2][finalJ].createElement(FUELTANK, 0);
                    }
                if(listOfFuelTanks.size() > 4) constructorController.disableFuelTank(true);
            });
            grid[width - 2][j].setOnMouseClicked(dragEvent -> {
                deleteFuelTank(finalJ);
            });
        }
    }

    public static void drawGrid(int width, int height, AnchorPane root){
        if(Grid.width != width || Grid.height != height) {
            initGrid(width, height);
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height + 1; j++) {
                if(!root.getChildren().contains(grid[i][j]))
                    root.getChildren().add(grid[i][j]);
            }
        }
        for (Line line : lines) {
            if(!root.getChildren().contains(line))
                root.getChildren().add(line);
        }
    }

    public static void deleteEntryExit(int k) {
        ConstructorController constructorController = (ConstructorController)
                ControllersRepository.getController(ControllerType.CONSTRUCTORCONTROLLER);
        if (grid[k][Grid.height].getMainStaticElement() != null &&
                grid[k][Grid.height].getIsOccupied()
                && listOfPetrolStations.isEmpty() &&
                listOfFuelTanks.isEmpty() &&
                !CashBox.getSetted()) {
            if (grid[k][Grid.height].getMainStaticElement().getElementType() == ENTRY) {
                grid[k][Grid.height].deleteElement();
                Entry.setX(0);
                Entry.setY(0);
                constructorController.disableEntry(false);
                constructorController.disableExit(true);
            } else {
                removeRoundRoad();
                grid[k][Grid.height].deleteElement();
                Exit.setX(0);
                Exit.setY(0);
                constructorController.disableExit(false);
            }

            constructorController.disablePetrolStation(true);
            constructorController.disableCashBox(true);
            constructorController.disableFuelTank(true);
        }
    }

    public static void deletePetrolStation(int i, int j) {
        ConstructorController constructorController = (ConstructorController)
                ControllersRepository.getController(ControllerType.CONSTRUCTORCONTROLLER);
        if (grid[i][j].getMainStaticElement() != null &&
                grid[i][j].getMainStaticElement().
                        getElementType().equals(PETROLSTATION)) {
            removePetrolRoad(j);
            listOfPetrolStations.remove(grid[i][j].getMainStaticElement());
            grid[i][j].deleteElement();
            for(int k = 0; k < listOfPetrolStations.size(); k++){
                for (int a = Exit.getX() + 1; a <= Entry.getX() - 1; a++) {
                    grid[a][listOfPetrolStations.get(k).getY() - 1].setOccupied(true);
                    grid[a][listOfPetrolStations.get(k).getY() + 1].setOccupied(true);
                }
            }
            if(listOfPetrolStations.size() < 4) constructorController.disablePetrolStation(false);
        }
    }

    public static void deleteCashBox(int j){
        ConstructorController constructorController = (ConstructorController)
                ControllersRepository.getController(ControllerType.CONSTRUCTORCONTROLLER);
        if (grid[Exit.getX() - 1][j].getMainStaticElement() != null &&
                grid[Exit.getX() - 1][j].getMainStaticElement().
                        getElementType().equals(CASHBOX)) {
            grid[Exit.getX() - 1][j].deleteElement();
            CashBox.setSetted(false);
            constructorController.disableCashBox(false);
        }
    }

    public static void deleteFuelTank(int j){
        ConstructorController constructorController = (ConstructorController)
                ControllersRepository.getController(ControllerType.CONSTRUCTORCONTROLLER);
        if (grid[width - 2][j].getMainStaticElement() != null) {
            listOfFuelTanks.remove(grid[width - 2][j].getMainStaticElement());
            grid[width - 2][j].deleteElement();
            if(listOfFuelTanks.size() < 5) constructorController.disableFuelTank(false);
        }
    }

    public static void removeGrid(AnchorPane root) {
        ConstructorController constructorController = (ConstructorController)
                ControllersRepository.getController(ControllerType.CONSTRUCTORCONTROLLER);
        for (int i = 0; i < Grid.width; i++) {
            for (int j = 0; j < Grid.height + 1; j++) {
                root.getChildren().remove(Grid.grid[i][j]);
            }
        }
        for (Line line : lines) {
            root.getChildren().remove(line);
        }

        Grid.height = 0;
        Grid.width = 0;
        grid = null;

        Entry.setX(0);
        Entry.setY(0);
        constructorController.disableEntry(false);

        Exit.setX(0);
        Exit.setY(0);
        constructorController.disableExit(true);

        CashBox.setX(666);
        CashBox.setY(666);
        CashBox.setSetted(false);
        constructorController.disableCashBox(true);

        listOfFuelTanks = new ArrayList<>();
        constructorController.disableFuelTank(true);
        listOfPetrolStations = new ArrayList<>();
        constructorController.disablePetrolStation(true);
    }

    public static void setPetrolRoad(int PetrolStationY) {
        for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
            if (PetrolStationY > 1) grid[i][PetrolStationY - 1].createElement(ROAD, 0);
            grid[i][PetrolStationY + 1].setOccupied(true);
        }
        if (PetrolStationY > 1){
            grid[Exit.getX()][PetrolStationY - 1].deleteElement();
            grid[Entry.getX()][PetrolStationY - 1].deleteElement();
            grid[Exit.getX()][PetrolStationY - 1].createElement(CROSSROAD, 270);
            grid[Entry.getX()][PetrolStationY - 1].createElement(CROSSROAD, 90);
        }
    }

    private static void setInputCrossRoad() {
        for (int i = 0; i < width; i++) {
            grid[i][height].createElement(ROAD, 0);
        }
        grid[width - 1][height].createElement(CROSSROAD, 180);
        grid[width - 3][height].createElement(CROSSROAD, 180);

    }

    public static void setRoundRoad() {
        grid[Entry.getX()][0].createElement(TURNROAD, 90);
        grid[Exit.getX()][0].createElement(TURNROAD, 0);
        for (int i = Exit.getX() + 1; i < Entry.getX(); i++) {
            grid[i][0].createElement(ROAD, 0);
        }
        for (int j = 1; j < height; j++) {
            grid[Entry.getX()][j].createElement(ROAD, 90);
            grid[Exit.getX()][j].createElement(ROAD, 90);
        }
    }

    private static void setStationRoad() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height + 1; j++) {
                grid[i][j].createElement(EMPTYPLACE, 0);
            }
        grid[width - 3][0].createElement(TURNROAD, 0);
        grid[width - 1][0].createElement(TURNROAD, 90);
        grid[width - 2][0].createElement(ROAD, 0);

        for (int i = 1; i < height; i++) {
            grid[width - 3][i].createElement(ROAD, 90);
            grid[width - 1][i].createElement(ROAD, 90);
        }
    }

    private static void removePetrolRoad(int PetrolStationY) {
        try{
            for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                if (PetrolStationY > 1) {
                    grid[i][PetrolStationY - 1].deleteElement();
                    grid[i][PetrolStationY - 1].createElement(EMPTYPLACE, 0);
                }
                grid[i][PetrolStationY + 1].setOccupied(false);
            }
            if (PetrolStationY > 1){
                grid[Exit.getX()][PetrolStationY - 1].deleteElement();
                grid[Entry.getX()][PetrolStationY - 1].deleteElement();
                grid[Exit.getX()][PetrolStationY - 1].createElement(ROAD, 90);
                grid[Entry.getX()][PetrolStationY - 1].createElement(ROAD, 90);
            }
        }
        catch (NullPointerException ignored){}
    }

    private static void removeRoundRoad() {
            for (int i = Exit.getX() + 1; i < Entry.getX(); i++) {
                grid[i][0].deleteElement();
            }
            for (int j = 0; j < height; j++) {
                grid[Entry.getX()][j].deleteElement();
                grid[Exit.getX()][j].deleteElement();
            }
    }

    private static List<Line> getLineList() {
        List<Line> lineList = new ArrayList<>();
        for (int j = 0; j < height + 1; j++) {
            Line lineHorizontal = new Line(grid[0][j].getTranslateX(),
                    grid[0][j].getTranslateY(),
                    grid[width - 1][j].getTranslateX() + GridElement.getElementWidth(),
                    grid[width - 1][j].getTranslateY());
            lineList.add(lineHorizontal);
        }
        Line lastLineHorizontal = new Line(grid[0][0].getTranslateX(),
                grid[0][height].getTranslateY() + GridElement.getElementHeight(),
                grid[width - 1][height].getTranslateX() + GridElement.getElementWidth(),
                grid[width - 1][height].getTranslateY() + GridElement.getElementHeight());
        lineList.add(lastLineHorizontal);
        for (int j = 0; j < width; j++) {
            Line lineVertical = new Line(grid[j][0].getTranslateX(),
                    grid[j][0].getTranslateY(),
                    grid[j][height].getTranslateX(),
                    grid[j][height].getTranslateY() + GridElement.getElementHeight());
            lineList.add(lineVertical);
        }
        Line lastLineVertical = new Line(grid[width - 1][0].getTranslateX() + GridElement.getElementWidth(),
                grid[width - 1][0].getTranslateY(),
                grid[width - 1][height].getTranslateX() + GridElement.getElementWidth(),
                grid[width - 1][height].getTranslateY() + GridElement.getElementHeight());
        lineList.add(lastLineVertical);
        return lineList;
    }

    public static void addPetrolStation(PetrolStation petrolStation) {
        listOfPetrolStations.add(petrolStation);
    }

    public static void addFuelTank(FuelTank fuelTank) {
        listOfFuelTanks.add(fuelTank);
    }

    public static GridElement[][] getGrid() {
        return grid;
    }

    public static void setGrid(GridElement[][] newGrid) {
        grid = newGrid;
    }

    public static int getX0() {
        return x0;
    }

    public static int getY0() {
        return y0;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }
}