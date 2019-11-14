package animationModule.framePackage;

import lombok.Getter;

@Getter
public class FrameElements {
    private Frame transportVehicle;
    private int offsetX;
    private int offsetY;
    private int frameNumber;

    public FrameElements(Frame transportVehicle, int offsetX, int offsetY, int frameNumber) {
        this.transportVehicle = transportVehicle;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.frameNumber = frameNumber;
    }
}
