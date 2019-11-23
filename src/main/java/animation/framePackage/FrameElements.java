package animation.framePackage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
/*Class describes column of the spritesheet*/
public class FrameElements {
    private Frame frame;
    private int offsetX;
    private int offsetY;
    private int frameNumber;

    public FrameElements(int offsetX, int offsetY, int frameNumber) {
        frame = new Frame(100, 50);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.frameNumber = frameNumber;
    }
}
