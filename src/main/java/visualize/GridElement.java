package visualize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GridElement {
    private int x;

    private int y;

    private static final int width = 40;

    private static final int height = 40;

    private boolean isOccupied;

    public GridElement(int x, int y, boolean isOccupied){
        this.x = x;
        this.y = y;
        this.isOccupied = isOccupied;
    }

    public static int getWidth(){
        return width;
    }
    public static int getHeight(){
        return height;
    }

}
