package topologyObjects;

import elements.FuelTank;
import visualize.Grid;

public class CollectorFuel{

    private int x;
    private int y;
    private long id;

    public void go(FuelTank ft){

        this.x = Grid.getWidth() - 1;
        this.y = Grid.getHeight() -1;
        while (this.y > 0){
            this.y--;
        }
        while (this.x > Grid.getWidth() - 3){
            this.x--;
        }
        while (this.y != Grid.getHeight() -1){
            this.y++;
        }
        ft.setCurrentVolume(ft.getVolume());
        ft.setStatus(true);
        while (this.x > 0){
            this.x--;
        }


    }


}
