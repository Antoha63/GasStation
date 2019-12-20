package animation.framePackage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import visualize.GridElement;


@Getter
@Setter
@AllArgsConstructor
/*Class describes animation of the character*/
public class FrameAnimation{
    private Image image;
    private ImageView imageView;
    private Rectangle2D[] viewports;

    private FrameElements frameElements;


    public FrameAnimation(int offsetX, int offsetY) {
        frameElements = new FrameElements(offsetX, offsetY, 3);
        initViewPorts();
        initImageView();
    }

    public void initViewPorts(){
        viewports = new Rectangle2D[frameElements.getFrameNumber()];
        int frameWidth = frameElements.getFrame().getFrameWidth();
        int frameHeight = frameElements.getFrame().getFrameHeight();
        for(int i = 0; i < frameElements.getFrameNumber(); i++){
            viewports[i] = new Rectangle2D(i * frameWidth,
                    frameElements.getOffsetY() * frameHeight,
                    frameWidth, frameHeight);
        }
    }
    public void initImageView(){
        image = new Image(getClass().getClassLoader().getResource("pics/sprites.png").toString());
        imageView = new ImageView();
        imageView.setFitHeight(new GridElement().getWidth());
        imageView.setFitWidth(new GridElement().getWidth() * 2);
        imageView.setImage(image);
        imageView.setViewport(viewports[2]);
    }


}
