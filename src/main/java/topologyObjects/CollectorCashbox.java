package topologyObjects;

import elements.CashBox;
import elements.Entry;
import elements.Exit;
import javafx.scene.image.ImageView;
import visualize.Grid;

public class CollectorCashbox extends TransportVehicle {

    private long id;

    @Override
    public void go(ImageView imageView) throws InterruptedException {

        CashBox.setStatus(false);
        this.setX(Grid.getWidth() - 1);
        this.setY(Grid.getHeight() - 1);
        while (this.getX() != Entry.getX()) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
        }
        while (this.getY() > 0) {
            this.moveY(-1);
            imageView.setTranslateY(this.getY());
        }
        while (this.getX() != Exit.getX()) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
        }
        while (this.getY() != Grid.getHeight() - 1) {
            this.moveY(1);
            imageView.setTranslateY(this.getY());
        }
        CashBox.setBalance(0);
        CashBox.setStatus(true);
        while (this.getX() > 0) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
        }

    }
}


