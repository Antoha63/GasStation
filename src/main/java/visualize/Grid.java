package visualize;


import lombok.Getter;
import lombok.Setter;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Grid {
    private static int x0 = 0;
    private static int y0 = 0;
    private static int width;
    private static int height;
    private static GridElement gridElement;
    private static GridElement[][] grid;

    public static void setGrid(int topologyX0,int topologyY0, int topologyHeigth, int topologyWidth){
        setX0(topologyX0);
        setY0(topologyY0);
        setWidth(topologyWidth);
        setHeight(topologyHeigth);
        grid = new GridElement[width + 1][height + 2];
    }

    static void setX0(int topologyX0){
        x0 = topologyX0;
    }

    static void setY0(int topologyY0){
        y0 = topologyY0;
    }

    public static int getWidth(){
        return width;
    }

    static void setWidth(int topologyWidth){
         width = topologyWidth;
    }

    public static int getHeight(){
        return height;
    }

    static void setHeight(int topologyHeigth){
        height = topologyHeigth;
    }

    public static GridElement[][] getGrid() {
        return grid;
    }

    static void initGrid() {
        gridElement = new GridElement();
        for (int i = 0; i < width + 1; i++) {
            for (int j = 0; j < height + 2; j++) {
                grid[i][j] = new GridElement(x0 + i * gridElement.getWidth(), y0 + j * gridElement.getHeight(), false);
            }
        }
    }


    public static List<Line> getLineList() {
        initGrid();
        List<Line> lineList = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height + 1; j++) {
                Line lineHorizontal = new Line(grid[i][j].getX(), grid[i][j].getY(), grid[i + 1][j].getX(), grid[i + 1][j].getY());
                lineList.add(lineHorizontal);
                Line lineVertical = new Line(grid[i][j].getX(), grid[i][j].getY(), grid[i][j + 1].getX(), grid[i][j + 1].getY());
                lineList.add(lineVertical);
            }
        }
        Line lastLineHorizontal = new Line(grid[0][height + 1].getX(), grid[0][height + 1].getY(), grid[width][height + 1].getX(), grid[width][height + 1].getY());
        lineList.add(lastLineHorizontal);
        Line lastLineVertical = new Line(grid[width][0].getX(), grid[width][0].getY(), grid[width][height + 1].getX(), grid[width][height + 1].getY());
        lineList.add(lastLineVertical);
        return lineList;
    }
}
