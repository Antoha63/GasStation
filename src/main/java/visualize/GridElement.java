package visualize;

import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class GridElement extends Pane {
    private boolean isOccupied;
    private static int width;
    private static int height;

    public GridElement() {
        this.setPrefWidth(50);
        this.setPrefHeight(50);
        width = (int)getPrefWidth();
        height = (int)getPrefHeight();
    }

    public GridElement(int x, int y, boolean isOccupied){
        this.isOccupied = isOccupied;
        this.setPrefWidth(50);
        this.setPrefHeight(50);
        this.setTranslateX(x);
        this.setTranslateY(y);
        width = (int)getPrefWidth();
        height = (int)getPrefHeight();
    }
    public static int getElementWidth(){
        return width;
    }
    public static int getElementHeight(){
        return height;
    }

}
