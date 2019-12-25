package elements;

import vizualize.Grid;

public class CollectorFuel {

    private int x;
    private int y;
    private long id;

    public void go(Grid grid, FuelTank ft){

        this.x = grid.getWidth() - 1;
        this.y = grid.getHeight() -1;
        while (this.y > 0){
            this.y--;
        }
        while (this.x > grid.getWidth() - 3){
            this.x--;
        }
        while (this.y != grid.getHeight() -1){
            this.y++;
        }
        ft.setCurrentVolume(ft.getVolume);
        ft.setStatus(true);
        while (this.x > 0){
            this.x--;
        }


    }


}
