package topologyObjects;

import elements.CashBox;
import javafx.scene.image.ImageView;
import visualize.Grid;

public class CollectorCashbox extends TransportVehicle{

    private long id;

    @Override
    public void go(ImageView imageView) throws InterruptedException {

        CashBox.setStatus(false);
        this.x = Grid.getWidth() - 1;
        this.y = Grid.getHeight() -1;
        while (this.x != entry.getX()){
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
        while (this.y > 0){
            this.moveY(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
        while (this.x != exit.getX()){
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
        while (this.y != Grid.getHeight() -1){
            this.moveY(+1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
        cashbox.setBalance(0);
        cashbox.setStatus(true);
        while (this.x > 0){
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }

    }
}


