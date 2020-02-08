package visualize;

import controller.ConstructorController;
import controller.ControllerType;
import controller.ControllersRepository;
import elements.*;
import javafx.scene.Parent;
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

    public static void addPetrolStation(PetrolStation petrolStation) {
        listOfPetrolStations.add(petrolStation);
    }

    public static void addFuelTank(FuelTank fuelTank) {
        listOfFuelTanks.add(fuelTank);
    }

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
                if (event.getDragboard().hasString()) {
                    switch (event.getDragboard().getString()) {
                        case "entry":
                        case "exit":
                            event.acceptTransferModes(TransferMode.COPY);
                            break;
                    }
                }
            });
            grid[k][Grid.height].setOnDragDropped(event -> {
                switch (event.getDragboard().getString()) {
                    case "exit":
                        if (finalK < Entry.getX() - 1 && Entry.getStatus() && !Exit.getStatus()) {
                            //TODO: заблокированный элемент слева от выезда
                            grid[finalK][Grid.height].createElement(EXIT, 180);
                            setRoundRoad();
                        }
                        break;
                    case "entry":
                        if(finalK >= 3 && !Entry.getStatus())
                            grid[finalK][Grid.height].createElement(ENTRY, 180);
                        break;
                }
                if (Entry.getStatus() && Exit.getStatus() && Entry.getX() > Exit.getX()) {
                    constructorController.disableElements(false);
                    /*Область трк*/
                    setPetrolStationsEvents();
                    /*Область кассы*/
                    setCashBoxEvents();
                    /*Область топливных баков*/
                    setFuelTanksEvents();
                }
            });
            grid[k][Grid.height].setOnMouseClicked(event -> {
                if (grid[finalK][Grid.height].getIsOccupied()
                        && listOfPetrolStations.isEmpty() &&
                        listOfFuelTanks.isEmpty() &&
                        !CashBox.getSetted()) {
                    if (grid[finalK][Grid.height].getMainStaticElement().getElementType() == ENTRY) {
                        removeRoundRoad();
                        grid[finalK][Grid.height].deleteElement();
                        Entry.setX(0);
                        Entry.setY(0);
                        Entry.setStatus(false);
                    } else {
                        removeRoundRoad();
                        grid[finalK][Grid.height].deleteElement();
                        Exit.setX(0);
                        Exit.setY(0);
                        Exit.setStatus(false);
                    }
                }
            });
        }
    }

    public static void setPetrolStationsEvents() {
        for (int i = 0; i < width - 3; i++)
            for (int j = 0; j < height; j++) {
                int finalI = i;
                int finalJ = j;

                grid[i][j].setOnDragOver(dragEvent -> {
                    if (dragEvent.getDragboard().hasString() &&
                            !grid[finalI][finalJ].getIsOccupied()
                            && Exit.getStatus() && Entry.getStatus()) {
                        if ("petrolStation".equals(dragEvent.getDragboard().getString())) {
                            if (Entry.getStatus() && Exit.getStatus()
                                    && finalI <= Entry.getX() && finalI >= Exit.getX())
                                dragEvent.acceptTransferModes(TransferMode.COPY);
                        }
                    }
                });
                grid[i][j].setOnDragDropped(dragEvent -> {
                    if(Exit.getStatus() && Entry.getStatus() &&
                            dragEvent.getDragboard().getString().equals("petrolStation")) {
                        grid[finalI][finalJ].createElement(PETROLSTATION, 0);
                        setPetrolRoad(finalI, finalJ);
                    }
                });
                grid[i][j].setOnMouseClicked(dragEvent -> {
                    if (grid[finalI][finalJ].getMainStaticElement() != null &&
                            grid[finalI][finalJ].getMainStaticElement().
                                    getElementType().equals(PETROLSTATION)) {
                        removePetrolRoad(finalI, finalJ);
                        listOfPetrolStations.remove(grid[finalI][finalJ].getMainStaticElement());
                        grid[finalI][finalJ].deleteElement();
                    }
                });
            }
    }

    public static void setCashBoxEvents() {
        for (int j = 0; j < height + 1; j++) {
            int finalJ = j;
            grid[Exit.getX() - 1][j].setOnDragOver(dragEvent -> {
                if (dragEvent.getDragboard().hasString() && !grid[0][finalJ].getIsOccupied()
                        && Exit.getStatus() && Entry.getStatus() && !CashBox.getSetted()) {
                    if ("cashBox".equals(dragEvent.getDragboard().getString())) {
                        dragEvent.acceptTransferModes(TransferMode.COPY);
                    }
                }
            });
            grid[Exit.getX() - 1][j].setOnDragDropped(dragEvent -> {
                if(Exit.getStatus() && Entry.getStatus())
                    if ("cashBox".equals(dragEvent.getDragboard().getString())) {
                        grid[Exit.getX() - 1][finalJ].createElement(CASHBOX, 0);
                        CashBox.setSetted(true);
                    }
            });
            grid[Exit.getX() - 1][j].setOnMouseClicked(dragEvent -> {
                if (grid[Exit.getX() - 1][finalJ].getMainStaticElement() != null &&
                        grid[Exit.getX() - 1][finalJ].getMainStaticElement().
                                getElementType().equals(CASHBOX)) {
                    grid[Exit.getX() - 1][finalJ].deleteElement();
                    CashBox.setSetted(false);
                    CashBox.setX(666);
                }
            });
        }
    }

    public static void setFuelTanksEvents() {
        for (int j = 1; j < height; j++) {
            int finalJ = j;
            grid[width - 2][j].setOnDragOver(dragEvent -> {
                if (dragEvent.getDragboard().hasString() &&
                        Exit.getStatus() && Entry.getStatus() &&
                        dragEvent.getDragboard().getString().equals("fuelTank")) {
                    dragEvent.acceptTransferModes(TransferMode.COPY);
                }
            });
            grid[width - 2][j].setOnDragDropped(dragEvent -> {
                if(Exit.getStatus() && Entry.getStatus())
                    if ("fuelTank".equals(dragEvent.getDragboard().getString())) {
                        grid[width - 2][finalJ].createElement(FUELTANK, 0);
                    }
            });
            grid[width - 2][j].setOnMouseClicked(dragEvent -> {
                if (grid[width - 2][finalJ].getMainStaticElement() != null) {
                    listOfFuelTanks.remove(grid[width - 2][finalJ].getMainStaticElement());
                    grid[width - 2][finalJ].deleteElement();
                }
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
            root.getChildren().add(line);
        }
    }

    public static void removeGrid(AnchorPane root) {
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
        Entry.setStatus(false);

        Exit.setX(0);
        Exit.setY(0);
        Exit.setStatus(false);

        listOfFuelTanks = new ArrayList<>();
        listOfPetrolStations = new ArrayList<>();
    }

    public static void setPetrolRoad(int PetrolStationX, int PetrolStationY) {
        if (Exit.getStatus() && Entry.getStatus()) {
            if (grid[PetrolStationX][PetrolStationY - 1].getIsOccupied() &&
                    !grid[PetrolStationX][PetrolStationY + 1].getIsOccupied()) {
                for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                    grid[i][PetrolStationY + 1].createElement(ROAD, 0);
                }
                grid[Exit.getX()][PetrolStationY + 1].deleteElement();
                grid[Entry.getX()][PetrolStationY + 1].deleteElement();
                grid[Exit.getX()][PetrolStationY + 1].createElement(CROSSROAD, 270);
                grid[Entry.getX()][PetrolStationY + 1].createElement(CROSSROAD, 90);
            } else if (!grid[PetrolStationX][PetrolStationY - 1].getIsOccupied() &&
                    grid[PetrolStationX][PetrolStationY + 1].getIsOccupied()) {
                for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                    grid[i][PetrolStationY - 1].createElement(ROAD, 0);
                }
                grid[Exit.getX()][PetrolStationY - 1].deleteElement();
                grid[Entry.getX()][PetrolStationY - 1].deleteElement();
                grid[Exit.getX()][PetrolStationY - 1].createElement(CROSSROAD, 270);
                grid[Entry.getX()][PetrolStationY - 1].createElement(CROSSROAD, 90);
            } else if (grid[PetrolStationX][PetrolStationY - 1].getIsOccupied() &&
                    grid[PetrolStationX][PetrolStationY + 1].getIsOccupied()) {

            } else {
                for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                    grid[i][PetrolStationY + 1].createElement(ROAD, 0);
                    grid[i][PetrolStationY - 1].createElement(ROAD, 0);
                }
                grid[Exit.getX()][PetrolStationY + 1].deleteElement();
                grid[Entry.getX()][PetrolStationY + 1].deleteElement();
                grid[Exit.getX()][PetrolStationY - 1].deleteElement();
                grid[Entry.getX()][PetrolStationY - 1].deleteElement();
                grid[Exit.getX()][PetrolStationY + 1].createElement(CROSSROAD, 270);
                grid[Entry.getX()][PetrolStationY + 1].createElement(CROSSROAD, 90);
                grid[Exit.getX()][PetrolStationY - 1].createElement(CROSSROAD, 270);
                grid[Entry.getX()][PetrolStationY - 1].createElement(CROSSROAD, 90);
            }
        }
    }

    private static void removePetrolRoad(int PetrolStationX, int PetrolStationY) {
        try {
            if (PetrolStationY == 1) {
                for (int i = Exit.getX() + 1; i < Entry.getX(); i++) {
                    grid[i][PetrolStationY + 1].deleteElement();
                }
                grid[Exit.getX()][PetrolStationY + 1].deleteElement();
                grid[Entry.getX()][PetrolStationY + 1].deleteElement();
                grid[Exit.getX()][PetrolStationY + 1].createElement(ROAD, 90);
                grid[Entry.getX()][PetrolStationY + 1].createElement(ROAD, 90);
            } else if (PetrolStationY == height - 1) {
                for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                    grid[i][PetrolStationY - 1].deleteElement();
                }
                grid[Exit.getX()][PetrolStationY - 1].deleteElement();
                grid[Entry.getX()][PetrolStationY - 1].deleteElement();
                grid[Exit.getX()][PetrolStationY - 1].createElement(ROAD, 90);
                grid[Entry.getX()][PetrolStationY - 1].createElement(ROAD, 90);
            } else {
                if (grid[PetrolStationX][PetrolStationY - 1].getIsOccupied() &&
                        !grid[PetrolStationX][PetrolStationY + 1].getIsOccupied()) {
                    for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                        grid[i][PetrolStationY + 1].deleteElement();
                    }
                    grid[Exit.getX()][PetrolStationY + 1].deleteElement();
                    grid[Entry.getX()][PetrolStationY + 1].deleteElement();
                    grid[Exit.getX()][PetrolStationY + 1].createElement(ROAD, 90);
                    grid[Entry.getX()][PetrolStationY + 1].createElement(ROAD, 90);
                } else if (!grid[PetrolStationX][PetrolStationY - 1].getIsOccupied() &&
                        grid[PetrolStationX][PetrolStationY + 1].getIsOccupied()) {
                    for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                        grid[i][PetrolStationY - 1].deleteElement();
                    }
                    grid[Exit.getX()][PetrolStationY - 1].deleteElement();
                    grid[Entry.getX()][PetrolStationY - 1].deleteElement();
                    grid[Exit.getX()][PetrolStationY - 1].createElement(ROAD, 90);
                    grid[Entry.getX()][PetrolStationY - 1].createElement(ROAD, 90);
                } else if (grid[PetrolStationX][PetrolStationY - 1].getIsOccupied() &&
                        grid[PetrolStationX][PetrolStationY + 1].getIsOccupied()) {
                    for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                        grid[i][PetrolStationY + 1].deleteElement();
                        grid[i][PetrolStationY - 1].deleteElement();
                    }
                    grid[Exit.getX()][PetrolStationY + 1].deleteElement();
                    grid[Entry.getX()][PetrolStationY + 1].deleteElement();
                    grid[Exit.getX()][PetrolStationY - 1].deleteElement();
                    grid[Entry.getX()][PetrolStationY - 1].deleteElement();
                    grid[Exit.getX()][PetrolStationY + 1].createElement(ROAD, 90);
                    grid[Entry.getX()][PetrolStationY + 1].createElement(ROAD, 90);
                    grid[Exit.getX()][PetrolStationY - 1].createElement(ROAD, 90);
                    grid[Entry.getX()][PetrolStationY - 1].createElement(ROAD, 90);
                }
            }
        } catch (NullPointerException ignored) {

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

    private static void removeRoundRoad() {
        if (Exit.getStatus() && Entry.getStatus()) {
            for (int i = Exit.getX() + 1; i < Entry.getX(); i++) {
                grid[i][0].deleteElement();
            }
            for (int j = 0; j < height; j++) {
                grid[Entry.getX()][j].deleteElement();
                grid[Exit.getX()][j].deleteElement();
            }
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