package vizualize;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class Grid {

    private final int x0 = 0;

    private final int y0 = 0;

    private int width;

    private int height;

    private GridElement gridElement = new GridElement();

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private GridElement[][] grid = new GridElement[width][height];

    public void initGrid(){
        GridElement gridElement = new GridElement();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                grid[i][j] = new GridElement(x0 + i * gridElement.getWidth(), y0 + j * gridElement.getHeight(), false);
            }
        }
    }
}
