package topologyObjects;

import elements.FuelTank;
import javafx.scene.image.ImageView;
import visualize.Grid;



public class CollectorFuel extends TransportVehicle{

    private long id;

    @Override
    public void go(ImageView imageView) throws InterruptedException {

        ft.setStatus = false;
        this.x = Grid.getWidth() - 1;
        this.y = Grid.getHeight() -1;
        while (this.y > 0){
            this.moveY(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
        while (this.x > Grid.getWidth() - 3){
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
        while (this.y != Grid.getHeight() -1){
            this.moveY(+1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
        ft.setCurrentVolume(ft.getVolume());
        ft.setStatus(true);
        while (this.x > 0){
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
    }
}
