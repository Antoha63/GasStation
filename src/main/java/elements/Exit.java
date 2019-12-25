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

    int x;
    int y;

    public Exit (int x, int y){
        this.x = x;
        this.y = y;
    }
}

