package elements;

import elements.mainElement.MainStaticElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import visualize.Grid;

@Setter
@Getter
@ToString
public class FuelTank extends MainStaticElement {

    private int x;
    private int y;
    private long id;
    private String fuel;
    private static int volume;  //between 5000 and 20000
    private int currentVolume;
    private static double criticalLevel; //between 10% and 90%
    private boolean status = true;

    public FuelTank(int x, int y) {
        this.x = x;
        this.y = y;
        Grid.addFuelTank(this);
    }

    public static void setVolume(int vol){
        volume = vol;
    }

    public static void setCriticalLevel(int vol){
        criticalLevel = (double) vol;
    }

    public void setStatus(boolean b) {
        status = b;
    }

    public boolean getStatus (){
        return status;
    }

    public static int getVolume() {
        return volume;
    }

    public void setCurrentVolume(int volume) {
        currentVolume = volume;
    }

    public boolean checkFuelTank() {
        boolean answer = true;
        if (currentVolume <= (int)(criticalLevel / 100 * volume) && status) {
            this.status = false;
        }
        else{
            answer = false;
        }
        return answer;
    }

    public void minusVolume(int volume) {
        if (currentVolume - volume >= 0)
            this.currentVolume -= volume;
        else this.currentVolume -= currentVolume;
    }

    public String getFuel() {
        return fuel;
    }
}
