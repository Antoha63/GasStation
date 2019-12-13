package TopologyObjects;

import animation.framePackage.FrameAnimation;
import animation.framePackage.FrameElements;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import visualize.GridElement;

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
            if (right) this.setTranslateX(this.getTranslateX() + 1);
            else this.setTranslateX(this.getTranslateX() - 1);
        }
    }
    public void moveY(int y){
        boolean down = y > 0;
        for(int i = 0; i < Math.abs(y); i++) {
            if (down) this.setTranslateY(this.getTranslateY() + 1);
            else this.setTranslateY(this.getTranslateY() - 1);
        }
    }
}
