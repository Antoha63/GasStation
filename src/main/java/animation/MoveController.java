package animation;

import animation.framePackage.FrameAnimation;
import animation.framePackage.Frame;
import animation.framePackage.FrameAnimation;
import animation.framePackage.FrameElements;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import lombok.Getter;

@Getter
/*Class describes all animation module*/
public class MoveController {
    private Rectangle2D[] viewports;
    private Image image;

    private FrameElements frameElements;
    private FrameAnimation frameAnimation;

    public MoveController(Image image) {
        this.image = image;
        frameElements = new FrameElements(0, 0, 3);
        frameAnimation = new FrameAnimation(frameElements.getFrame(), frameElements);
        viewports = frameAnimation.getViewports();
    }
}
