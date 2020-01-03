package visualize;

import elements.Entry;
import elements.Exit;
import frameModule.FrameAnimation;
import javafx.scene.image.ImageView;
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

    private static String[] dragboardStrings;

    private static GridElement gridElement;
    private static GridPane gridPane = new GridPane();

    private static GridElement[][] grid;
    private static Random rand = new Random();

    public static void initGrid(int topologyX0, int topologyY0,
                                int topologyWidth, int topologyHeight) {
        x0 = topologyX0;
        y0 = topologyY0;
        width = topologyWidth;
        height = topologyHeight;
        grid = new GridElement[width][height + 1];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height + 1; j++) {
                grid[i][j] = new GridElement(i, j, false);
                grid[i][j].setOnDragOver(event -> {
                    if (event.getDragboard().hasString()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    }
                });
                int finalJ = j;
                int finalI = i;
                grid[i][j].setOnDragDropped(event -> {
                    if (!grid[finalI][finalJ].getIsOccupied()) {
                        switch (event.getDragboard().getString()) {
                            case "petrolStation":
                                grid[finalI][finalJ].createElement(PETROLSTATION, 0);
/*                                    grid[finalI - 1][finalJ - 1].setOccupied(true);
                                    grid[finalI][finalJ - 1].setOccupied(true);
                                    grid[finalI + 1][finalJ - 1].setOccupied(true);

                                    grid[finalI - 1][finalJ + 1].setOccupied(true);
                                    grid[finalI][finalJ + 1].setOccupied(true);
                                    grid[finalI + 1][finalJ + 1].setOccupied(true);*/

                                break;
                            case "fuelTank":
                                grid[finalI][finalJ].createElement(FUELTANK, 0);
                                break;
                            case "exit":
                                grid[finalI][finalJ].createElement(EXIT, 180);
                                break;
                            case "entry":
                                grid[finalI][finalJ].createElement(ENTRY, 180);
                                break;
                            case "cashBox":
                                grid[finalI][finalJ].createElement(CASHBOX, 0);
                                break;
                        }
                        if (Entry.getFlag() && Exit.getFlag())
                            setRoundRoad();
                    } else {
                        event.setDropCompleted(true);
                    }
                });
                grid[i][j].setOnMouseClicked(event -> {
                    if (grid[finalI][finalJ].getMainStaticElement() != null) {
/*                        if(dragboardStrings[0].equals("petrolStation")) {
                            grid[finalI - 1][finalJ - 1].setOccupied(false);
                            grid[finalI][finalJ - 1].setOccupied(false);
                            grid[finalI + 1][finalJ - 1].setOccupied(false);

                            grid[finalI - 1][finalJ + 1].setOccupied(false);
                            grid[finalI][finalJ + 1].setOccupied(false);
                            grid[finalI + 1][finalJ + 1].setOccupied(false);
                        }*/
                        grid[finalI][finalJ].getChildren().remove(grid[finalI][finalJ].getFrameAnimation().getImageView());
                        grid[finalI][finalJ].deleteElement();
                    }
                });
            }

        }
        setRoad();
        setStationRoad();
    }

    public static GridElement[][] getGrid() {
        return grid;
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

    private static void setRoundRoad() {
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
}
