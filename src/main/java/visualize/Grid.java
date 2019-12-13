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

    public Grid(int x0, int y0, int width, int height) {
        Grid.x0 = x0;
        Grid.y0 = y0;
        Grid.width = width;
        Grid.height = height;
        Grid.grid = new GridElement[width][height];
    }

    public static int getWidth(){
        return width;
    }
    public static int getHeight(){
        return width;
    }
    public static GridElement[][] getGrid() {
        return grid;
    }
    public static void initGrid() {
        gridElement = new GridElement();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new GridElement(x0 + i * gridElement.getWidth(), y0 + j * gridElement.getHeight(), false);
            }
        }
    }


    public List<Line> getLineList() {
        initGrid();
        List<Line> lineList = new ArrayList<>();
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                Line lineHorizontal = new Line(grid[i][j].getX(), grid[i][j].getY(), grid[i + 1][j].getX(), grid[i + 1][j].getY());
                lineList.add(lineHorizontal);
                Line lineVertical = new Line(grid[i][j].getX(), grid[i][j].getY(), grid[i][j + 1].getX(), grid[i][j + 1].getY());
                lineList.add(lineVertical);
            }
        }
        Line lastLineHorizontal = new Line(grid[0][height - 1].getX(), grid[0][height - 1].getY(), grid[width - 1][height - 1].getX(), grid[width - 1][height - 1].getY());
        lineList.add(lastLineHorizontal);
        Line lastLineVertical = new Line(grid[width - 1][0].getX(), grid[width - 1][0].getY(), grid[width - 1][height - 1].getX(), grid[width - 1][height - 1].getY());
        lineList.add(lastLineVertical);
        return lineList;
    }
}
