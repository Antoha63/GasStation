package animation.framePackage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
/*Class describes animation of the character*/
public class FrameAnimation{
    private Rectangle2D[] viewports;
//    private Timeline timeLine;
//    private KeyFrame[] keyFrames;
    private int duration;
    private int tickTime;



    public FrameAnimation(Frame frame, FrameElements frameElements) {
        this.viewports = new Rectangle2D[frameElements.getFrameNumber()];
        for(int i = 0; i < frameElements.getFrameNumber(); i++){
            viewports[i] = new Rectangle2D(i * frame.getFrameWidth(),
                    frameElements.getOffsetY() * frame.getFrameHeight(),
                    frame.getFrameWidth(),
                    frame.getFrameHeight());
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
