package visualize;


import lombok.Getter;
import lombok.Setter;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Grid {
    private int x0 = 0;
    private int y0 = 0;
    private int width;
    private int height;
    GridElement gridElement;

    public Grid(int x0, int y0, int width, int height) {
        this.x0 = x0;
        this.y0 = y0;
        this.width = width;
        this.height = height;
    }

    private GridElement[][] grid;

    public void initGrid() {
        grid =  new GridElement[width][height];
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
