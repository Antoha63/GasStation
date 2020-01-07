package elements;


import elements.mainElement.MainStaticElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Entry extends MainStaticElement {

    private static int x;
    private static int y;
    private static boolean flag; //isSetted

    public Entry(int x, int y, boolean f) {
        Entry.x = x;
        Entry.y = y;
        flag = f;
    }

    public static void setX(int x) {
        Entry.x = x;
    }

    public static void setY(int y) {
        Entry.y = y;
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

    public static boolean getFlag() {
        return flag;
    }

    public static void setFlag(boolean f) {
        flag = f;
    }

}
