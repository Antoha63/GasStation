package elements;

import elements.mainElement.MainStaticElement;
import entities.Fuel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FuelTank extends MainStaticElement {

    private int x;
    private int y;
    private long id;
    private String fuel;
    private int volume;  //between 5000 and 20000
    private int currentVolume;
    private int criticalLevel; //between 10% and 90%
    private boolean status = true;

    public FuelTank (int x, int y){
        this.x = x;
        this.y = y;
    }

    private void checkFuelTank(){
        if (currentVolume <= criticalLevel/100*volume && this.status == true){
            this.status = false;
            //callCollectorFuel()
            this.status = true;
            this.currentVolume = volume;
        }
    }

    public void setCurrentVolume(int volume){
        if (currentVolume - volume >= 0)
            this.currentVolume -= volume;
        else this.currentVolume -= currentVolume;
    }

}
