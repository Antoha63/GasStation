package animationModule.framePackage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class FrameAnimation{
    private ImageView sprite ;
    private Frame frameVehicle;
    private FrameElements transportVehicle;
    private Rectangle2D[] viewPorts;
    private Timeline timeLine;
    private KeyFrame[] keyFrames;
    private int duration;
    private int tickTime;

    public FrameAnimation(String url,int offsetX, int offsetY, int frameWidth, int frameHeight, int frameNumber) {
        sprite = new ImageView();
        sprite.setImage(new Image(url));
        frameVehicle = new Frame(frameWidth, frameHeight);
        transportVehicle = new FrameElements(frameVehicle,offsetX, offsetY, frameNumber);
        viewPorts = new Rectangle2D[frameNumber];
        for(int i = 0; i < frameNumber; i++){
            viewPorts[i] = new Rectangle2D(i * frameWidth,
                                            offsetY * frameHeight,
                                            frameWidth,
                                            frameHeight);
        }
    }

//    public void play(){
//
//        for (int i = 0; i < transportVehicle.getFrameNumber(); i++){
//            keyFrames[i] = new KeyFrame(duration, )
//        }
//        timeLine = new Timeline(int repeatCounts = Timeline.INDEFINITE, keyFrames);
//    }
}
