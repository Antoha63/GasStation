package visualize;

import frameModule.FrameAnimation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import topologyObjects.*;

@Getter
@Setter
@NoArgsConstructor
public class VisualisedTransportVehicle {
    private TransportVehicle transportVehicle;
    private FrameAnimation frameAnimation;
    private int imageOffsetX = 2;


    public VisualisedTransportVehicle(int x, int y, double probabilityOfArrival, TransportVehicleType type) {
        switch (type) {
            case AUTOMOBILE:
                this.transportVehicle = new Vehicle(x, y, probabilityOfArrival);
                this.frameAnimation = new FrameAnimation(imageOffsetX,
                        0,
                        100,
                        50,
                        3);
                this.frameAnimation.getImageView().setFitHeight(40);
                this.frameAnimation.getImageView().setFitWidth(80);
                break;
            case COLLECTORFUEL:
                this.transportVehicle = new CollectorFuel(x,y);
                this.frameAnimation = new FrameAnimation(imageOffsetX,
                        2,
                        100,
                        50,
                        3);
                this.frameAnimation.getImageView().setFitHeight(40);
                this.frameAnimation.getImageView().setFitWidth(80);
                break;
            case COLLECTORCASHBOX:
                this.transportVehicle = new CollectorCashbox(x,y);
                this.frameAnimation = new FrameAnimation(imageOffsetX,
                        1,
                        100,
                        50,
                        3);
                this.frameAnimation.getImageView().setFitHeight(40);
                this.frameAnimation.getImageView().setFitWidth(80);
                break;
        }
    }

    public void go() throws InterruptedException {
        this.transportVehicle.go(this.frameAnimation.getImageView());
    }
}