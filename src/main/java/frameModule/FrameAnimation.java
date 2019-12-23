package frameModule;

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


    public FrameAnimation(int offsetX, int offsetY, int frameWidth, int frameHeight, int frameNumber) {
        frameElements = new FrameElements(offsetX, offsetY,frameWidth,frameHeight, frameNumber);
        initViewPorts();
        initImageView(offsetX);
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
    public void initImageView(int row){
        image = new Image(getClass().getClassLoader().getResource("pics/sprites.png").toString());
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setViewport(viewports[row]);
    }


}
