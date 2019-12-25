package visualize;

import frameModule.FrameAnimation;
import lombok.NoArgsConstructor;
import topologyObjects.*;

@NoArgsConstructor
public class VisualisedTransportVehicle {
    private TransportVehicle transportVehicle;
    private FrameAnimation frameAnimation;
    private int imageOffsetX = 2;

    public void setImageOffsetX(int offsetX){
        this.imageOffsetX = offsetX;
    }
    public TransportVehicle getTransportVehicle(){
        return transportVehicle;
    }

    public void createTransportVehicle(int x, int y, double probabilityOfArrival, TransportVehicleType type){
        switch (type){
            case AUTOMOBILE:
                this.transportVehicle = new Vehicle(x, y, probabilityOfArrival);
                this.frameAnimation = new FrameAnimation(imageOffsetX,
                                                    0,
                                                    100,
                                                    50,
                                                    3);
                break;
            case COLLECTORFUEL:
                this.transportVehicle = new CollectorFuel();
                this.frameAnimation = new FrameAnimation(imageOffsetX,
                                                    2,
                                                    100,
                                                    50,
                                                    3);
                break;
            case COLLECTORCASHBOX:
                this.transportVehicle = new CollectorCashbox();
                this.frameAnimation = new FrameAnimation(imageOffsetX,
                                                    1,
                                                    100,
                                                    50,
                                                    3);
                break;
        }
    }

    public void go() throws InterruptedException {
        this.transportVehicle.go(this.frameAnimation.getImageView());
    }
}
