package animationModule;

import animationModule.framePackage.FrameAnimation;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import lombok.Getter;

@Getter
public class MoveController {

    private FrameAnimation frameAnimation;
    private ImageView imageView;
    private Rectangle2D[] viewports;

    public MoveController() {
        frameAnimation = new FrameAnimation("file:../../pics/sprites.png",0,0, 100, 50, 3);
        this.imageView = frameAnimation.getSprite();
        this.viewports = frameAnimation.getViewPorts();
    }
}
