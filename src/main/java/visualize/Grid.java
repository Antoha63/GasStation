package visualize;

import controller.ConstructorController;
import elements.Entry;
import elements.Exit;
import elements.FuelTank;
import elements.PetrolStation;
import javafx.fxml.FXMLLoader;
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
    private static int width;
    private static int height;

    private static GridElement[][] grid;
    private static String[] dragboardStrings;

    private static GridElement gridElement;
    private static GridPane gridPane = new GridPane();
    private static List<PetrolStation> listOfPetrolStations = new ArrayList<>();
    private static List<FuelTank> listOfFuelTanks = new ArrayList<>();
    private static ConstructorController constructorController;

    public static void setConstructorController(ConstructorController constructorController) {
        Grid.constructorController = constructorController;
    }

    public static List<PetrolStation> getListOfPetrolStations() {
        return listOfPetrolStations;
    }

    public static List<FuelTank> getListOfFuelTanks() {
        return listOfFuelTanks;
    }

    public static void setListOfPetrolStations(PetrolStation petrolStation) {
        listOfPetrolStations.add(petrolStation);
    }

    public static void setListOfFuelTanks(FuelTank fuelTank) {
        listOfFuelTanks.add(fuelTank);
    }

    private static Random rand = new Random();

    public static void initGrid(int topologyX0, int topologyY0,
                                int topologyWidth, int topologyHeight) {
        x0 = topologyX0;
        y0 = topologyY0;
        width = topologyWidth;
        height = topologyHeight;
        grid = new GridElement[width][height + 1];

        /*Инициализация*/
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height + 1; j++) {
                grid[i][j] = new GridElement(i, j, false);
            }
        }
        /*Область трк и кассы*/
        for (int i = 0; i < width - 3; i++)
            for (int j = 0; j < height; j++) {
                int finalI = i;
                int finalJ = j;

                grid[i][j].setOnDragOver(event -> {
                    if (event.getDragboard().hasString() && !grid[finalI][finalJ].getIsOccupied()) {
                        switch (event.getDragboard().getString()) {
                            case "cashBox":
                            case "petrolStation":
                                event.acceptTransferModes(TransferMode.COPY);
                                break;
                        }
                    }
                });
                grid[i][j].setOnDragDropped(event -> {
                    switch (event.getDragboard().getString()) {
                        case "cashBox":
                            grid[finalI][finalJ].createElement(CASHBOX, 0);
                            break;
                        case "petrolStation":
                            grid[finalI][finalJ].createElement(PETROLSTATION, 0);
                            setPetrolRoad(finalI, finalJ);
                            break;
                    }
                });
                grid[i][j].setOnMouseClicked(event -> {
                    if (grid[finalI][finalJ].getMainStaticElement() != null) {
                        if (grid[finalI][finalJ].getMainStaticElement().getElementType().equals(PETROLSTATION))
                            removePetrolRoad(finalI, finalJ);
                        listOfPetrolStations.remove(grid[finalI][finalJ].getMainStaticElement());
                        grid[finalI][finalJ].deleteElement();
                    }
                });
            }
        /*Область топливных баков*/
        for (int j = 1; j < height; j++) {
            int finalJ = j;
            grid[width - 2][j].setOnDragOver(event -> {
                if (event.getDragboard().hasString()) {
                    switch (event.getDragboard().getString()) {
                        case "fuelTank":
                            event.acceptTransferModes(TransferMode.COPY);
                            break;
                    }
                }
            });
            grid[width - 2][j].setOnDragDropped(event -> {
                switch (event.getDragboard().getString()) {
                    case "fuelTank":
                        grid[width - 2][finalJ].createElement(FUELTANK, 0);
                        break;
                }
            });
            grid[width - 2][j].setOnMouseClicked(event -> {
                if (grid[width - 2][finalJ].getMainStaticElement() != null) {
                    listOfFuelTanks.remove(grid[width - 2][finalJ].getMainStaticElement());
                    grid[width - 2][finalJ].deleteElement();
                }
            });
        }
        /*Область въезда и выезда*/
        for (int i = 1; i < width - 3; i++) {
            int finalI = i;

            grid[i][Grid.height].setOnDragOver(event -> {
                if (event.getDragboard().hasString()) {
                    switch (event.getDragboard().getString()) {
                        case "entry":
                        case "exit":
                            event.acceptTransferModes(TransferMode.COPY);
                            break;
                    }
                }
            });
            grid[i][Grid.height].setOnDragDropped(event -> {//TODO: fix readd round road when deleting the Entry
                switch (event.getDragboard().getString()) {
                    case "exit":
                        if (finalI <= Entry.getX() && Entry.getStatus())
                            grid[finalI][Grid.height].createElement(EXIT, 180);
                        break;
                    case "entry":
                        grid[finalI][Grid.height].createElement(ENTRY, 180);
                        break;
                }
                if (Entry.getStatus() && Exit.getStatus() && Entry.getX() > Exit.getX()) {
                    constructorController.disableElements(false);
                    setRoundRoad();
                }
            });
            grid[i][Grid.height].setOnMouseClicked(event -> {
                if (grid[finalI][Grid.height].getIsOccupied()) {
                    if (Entry.getX() == 0 ^ Exit.getX() == 0 && (finalI == Exit.getX() || finalI == Entry.getX())) {
                        grid[finalI][Grid.height].deleteElement();
                    }
                    if (Entry.getX() != 0 && Exit.getX() != 0 && (finalI == Exit.getX() || finalI == Entry.getX())) {
                        if (grid[finalI][Grid.height].getMainStaticElement().getElementType() == ENTRY) {
                            removeRoundRoad();
                            grid[finalI][Grid.height].deleteElement();
                            Entry.setX(0);
                            Entry.setY(0);
                            Entry.setStatus(false);
                        } else {
                            removeRoundRoad();
                            grid[finalI][Grid.height].deleteElement();
                            Exit.setX(0);
                            Exit.setY(0);
                            Exit.setStatus(false);
                        }
                    }
                }
            });
        }
        setRoad();
        setStationRoad();
    }

    private static void setPetrolRoad(int PetrolStationX, int PetrolStationY) {
        if (Exit.getStatus() && Entry.getStatus()) {
            if (grid[PetrolStationX][PetrolStationY - 1].getIsOccupied() &&
                    !grid[PetrolStationX][PetrolStationY + 1].getIsOccupied()) {
                for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                    grid[i][PetrolStationY + 1].createElement(ROAD, 0);
                }
                grid[Exit.getX()][PetrolStationY + 1].createElement(CROSSROAD, 270);
                grid[Entry.getX()][PetrolStationY + 1].createElement(CROSSROAD, 90);
            } else if (!grid[PetrolStationX][PetrolStationY - 1].getIsOccupied() &&
                    grid[PetrolStationX][PetrolStationY + 1].getIsOccupied()) {
                for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                    grid[i][PetrolStationY - 1].createElement(ROAD, 0);
                }
                grid[Exit.getX()][PetrolStationY - 1].createElement(CROSSROAD, 270);
                grid[Entry.getX()][PetrolStationY - 1].createElement(CROSSROAD, 90);
            } else if (grid[PetrolStationX][PetrolStationY - 1].getIsOccupied() &&
                    grid[PetrolStationX][PetrolStationY + 1].getIsOccupied()) {

            } else {
                for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
                    grid[i][PetrolStationY + 1].createElement(ROAD, 0);
                    grid[i][PetrolStationY - 1].createElement(ROAD, 0);
                }
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
                for (int i = Exit.getX() + 1; i <= Entry.getX() - 1; i++) {
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

    public static List<Line> getLineList() {
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

    private static void setRoad() {
        for (int i = 0; i < width; i++) {
            grid[i][height].createElement(ROAD, 0);
        }
        grid[width - 1][height].createElement(CROSSROAD, 180);
        grid[width - 3][height].createElement(CROSSROAD, 180);

    }

    public static void setRoundRoad() {
        grid[Entry.getX()][0].createElement(TURNROAD, 90);
        grid[Exit.getX()][0].createElement(TURNROAD, 0);
        for (int j = Exit.getX() + 1; j < Entry.getX(); j++) {
            grid[j][0].createElement(ROAD, 0);
        }


        for (int i = 1; i < height; i++) {
            grid[Entry.getX()][i].createElement(ROAD, 90);
            grid[Exit.getX()][i].createElement(ROAD, 90);
        }
    }

    private static void removeRoundRoad() {
        if (Exit.getX() != 0 && Entry.getX() != 0) {
            grid[Entry.getX()][0].deleteElement();
            grid[Exit.getX()][0].deleteElement();
            for (int j = Exit.getX() + 1; j < Entry.getX(); j++) {
                grid[j][0].deleteElement();
            }


            for (int i = 1; i < height; i++) {
                grid[Entry.getX()][i].deleteElement();
                grid[Exit.getX()][i].deleteElement();
            }
        }
    }


    private static void setStationRoad() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
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



    private void algoritmOfDeicstra(int sourceX, int sourceY, int destX, int destY){
        int size = (width - 3) * height;
        int[][] matrixOfDistance = new int[size][size];
        int[] minimalDistance = new int[size];
        boolean[] isVisited = new boolean[size];

        /*initDistances*/
        for(int i = 0; i < size; i ++){
            matrixOfDistance[i][i] = 0;
            for(int j = i + 1; j < size; j++){
                matrixOfDistance[i][j] = 1;
                matrixOfDistance[j][i] = 1;
            }
        }

        /*initVisitedAndMinDistances*/
        for(int i = 0; i < size; i++){
            isVisited[i] = false;
            minimalDistance[i] = 99999;
        }

        //TODO:
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