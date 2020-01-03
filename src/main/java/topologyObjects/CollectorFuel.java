package topologyObjects;

import elements.FuelTank;
import javafx.scene.image.ImageView;
import visualize.Grid;


public class CollectorFuel extends TransportVehicle {

    private long id;

    @Override
    public void go(ImageView imageView) throws InterruptedException {

        FuelTank.setStatus(false);
        this.setX(Grid.getWidth() - 1);
        this.setY(Grid.getHeight() - 1);
        while (this.getY() > 0) {
            this.moveY(-1);
            imageView.setTranslateY(this.getY());
        }
        while (this.getX() > Grid.getWidth() - 3) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
        }
        while (this.getY() != Grid.getHeight() - 1) {
            this.moveY(+1);
            imageView.setTranslateY(this.getY());
        }
        FuelTank.setCurrentVolume(FuelTank.getVolume());
        FuelTank.setStatus(true);
        while (this.getX() > 0) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
    }
}
