package frameModule;

import lombok.Getter;

@Getter
/*Class describes column of the spritesheet*/
public class FrameElements {
    private Frame frame;
    private int offsetX;
    private int offsetY;
    private int frameNumber;

    public FrameElements(int offsetX, int offsetY, int frameWidth, int frameHeight, int frameNumber) {
        frame = new Frame(frameWidth, frameHeight);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.frameNumber = frameNumber;
    }
}
