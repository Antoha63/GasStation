package elements;

import elements.mainElement.MainStaticElement;
import entities.Fuel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import topologyObjects.CollectorFuel;
import visualize.Grid;

@Setter
@Getter
@ToString
public class FuelTank extends MainStaticElement {

    private int x;
    private int y;
    private long id;
    private String fuel = "98";
    private static int volume;  //between 5000 and 20000
    private int currentVolume = 5000;
    private static int criticalLevel; //between 10% and 90%
    private boolean status = true;

    public FuelTank(int x, int y) {
        this.x = x;
        this.y = y;
        Grid.setListOfFuelTanks(this);
    }

    public static void setVolume(int vol){
        volume = vol;
    }

    public static void setCriticalLevel(int vol){
        criticalLevel = vol;
    }

    public void setStatus(boolean b) {
        status = b;
    }

    public static int getVolume() {
        return volume;
    }

    public void setCurrentVolume(int volume) {
        currentVolume = volume;
    }

    public boolean checkFuelTank() {
        boolean answer = false;
        if (currentVolume <= criticalLevel / 100 * volume && status) {
            answer = true;
            this.status = false;
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
