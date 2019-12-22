package topologyObjects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class TransportVehicle extends Pane {
    private int x;
    private int y;
    private double probabilityOfArrival;

    TransportVehicle(int x, int y, double probabilityOfArrival){
        this.x = x;
        this.y = y;
        this.probabilityOfArrival = probabilityOfArrival;
    }

    public void moveX(int x){
        boolean right = x > 0;
        for(int i = 0; i < Math.abs(x); i++) {
            if (right) this.setX(this.getX() + 1);
            else this.setX(this.getX() - 1);
        }
    }
    public void moveY(int y){
        boolean down = y > 0;
        for(int i = 0; i < Math.abs(y); i++) {
            if (down) this.setTranslateY(this.getTranslateY() + 1);
            else this.setTranslateY(this.getTranslateY() - 1);
        }
    }

    public void go(ImageView imageView) throws InterruptedException {
    }
}
