package topologyObjects;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static topologyObjects.TransportVehicleDirection.*;

@Getter
@Setter
@NoArgsConstructor
public abstract class TransportVehicle extends Pane {
    private int x;
    private int y;
    private TransportVehicleDirection direction;

    TransportVehicle(int x, int y){
        this.x = x;
        this.y = y;
        direction = LEFT;
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
            if (down) this.setY(this.getY() + 1);
            else this.setY(this.getY() - 1);
        }
    }

    public abstract void go(ImageView imageView) throws InterruptedException;
}
