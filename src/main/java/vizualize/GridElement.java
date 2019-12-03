package vizualize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GridElement {
    private int x;

    private int y;

    private final int width = 10;

    private final int height = 10;

    private boolean isOccupied;

    public GridElement(int x, int y, boolean isOccupied){
        this.x = x;
        this.y = y;
        this.isOccupied = isOccupied;
    }
}
