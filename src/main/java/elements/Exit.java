package elements;

import elements.mainElement.MainStaticElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Exit extends MainStaticElement {

    private static int x;
    private static int y;

    public Exit(int x, int y, boolean f) {
        Exit.x = x;
        Exit.y = y;
    }


    public static void setX(int x) {
        Exit.x = x;
    }
    public static int getX() {
        return x;
    }
    public static void setY(int y) {
        Exit.y = y;
    }
    public static int getY() {
        return y;
    }
}

