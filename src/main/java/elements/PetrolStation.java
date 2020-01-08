package elements;

import elements.mainElement.MainStaticElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import visualize.Grid;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PetrolStation extends MainStaticElement {

    private int x;
    private int y;
    private long id;
    private static int speed = 70; //between 50 and 100
    private boolean status = true;

    public static int getSpeed(){
        return speed;
    }

    public boolean getStatus(){
        return status;
    }

    public PetrolStation (int x, int y){
        this.x = x;
        this.y = y;
        Grid.setListOfPetrolStations(this);
    }

    public static void setSpeed(int sp){
        speed = sp;
    }
}