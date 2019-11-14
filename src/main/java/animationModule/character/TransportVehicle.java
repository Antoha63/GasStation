package animationModule.character;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TransportVehicle {
    private int x;
    private int y;
    private int speed;

    public TransportVehicle(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void moveX(int x){

    }
    public void moveY(int y){

    }
}
