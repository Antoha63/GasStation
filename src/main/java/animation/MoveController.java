package animation;

import animation.framePackage.FrameAnimation;
import animation.framePackage.Frame;
import animation.framePackage.FrameAnimation;
import animation.framePackage.FrameElements;
import elements.Vehicle;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.CarRepository;
import repositories.FuelRepository;
import visualize.GridElement;

@Getter
/*Class describes all animation module*/
public class MoveController {
    //private ImageView imageView;
    private Rectangle2D[] viewports;
    private Image image;

    private FrameElements frameElements;
    private FrameAnimation frameAnimation;
    private double poao = 0.1;
    private Vehicle vehicle;
    //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    //CarRepository carRepository = context.getBean(CarRepository.class);
    //FuelRepository fuelRepository = context.getBean(FuelRepository.class);

    public MoveController() {

    }

    public ImageView initImageView(){
        this.image = new Image(getClass().getClassLoader().getResource("pics/sprites.png").toString());
        frameElements = new FrameElements(0, 0, 3);
        frameAnimation = new FrameAnimation(frameElements.getFrame(), frameElements);
        viewports = frameAnimation.getViewports();

        ImageView imageView = new ImageView();
        imageView.setFitHeight(new GridElement().getWidth());
        imageView.setFitWidth(new GridElement().getWidth() * 2);
        imageView.setImage(image);
        imageView.setViewport(viewports[1]);
        return imageView;
    }

    public void initVehicle(){
        vehicle = new Vehicle(poao);
        //vehicle.go();
    }
}
