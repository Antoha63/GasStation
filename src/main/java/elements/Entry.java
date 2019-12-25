package elements;


import elements.mainElement.MainStaticElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Entry extends MainStaticElement {

    int x;
    int y;

    public Entry (int x, int y){
        this.x = x;
        this.y = y;
    }
}
