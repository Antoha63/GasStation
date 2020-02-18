package elements;


import elements.mainElement.MainStaticElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@ToString

public class Entry extends MainStaticElement {

    private static int x;
    private static int y;

    public Entry(int x, int y, boolean f) {
        Entry.x = x;
        Entry.y = y;
    }

    public static int getX() {
        return x;
    }
    public static void setX(int x) {
        Entry.x = x;
    }
    public static int getY() {
        return y;
    }
    public static void setY(int y) {
        Entry.y = y;
    }
}
