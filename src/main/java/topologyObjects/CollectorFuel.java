package topologyObjects;

import elements.Entry;
import elements.Exit;
import elements.FuelTank;
import javafx.scene.image.ImageView;
import visualize.Grid;
import lombok.NoArgsConstructor;
import visualize.GridElement;

import static topologyObjects.TransportVehicleDirection.*;


@NoArgsConstructor
public class CollectorFuel extends TransportVehicle {

    private long id;

    public CollectorFuel (int x, int y){
        super(x,y);
    }

    @Override
    public void go(ImageView imageView) throws InterruptedException {

        /*FuelTank.setStatus(false);
        this.setX(Grid.getWidth() - 1);
        this.setY(Grid.getHeight() - 1);
        while (this.getY() > 0) {
            this.moveY(-1);
            imageView.setTranslateY(this.getY());
        }
        while (this.getX() > Grid.getWidth() - 3) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
        }
        while (this.getY() != Grid.getHeight() - 1) {
            this.moveY(+1);
            imageView.setTranslateY(this.getY());
        }
        FuelTank.setCurrentVolume(FuelTank.getVolume());
        FuelTank.setStatus(true);
        while (this.getX() > 0) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }*/

        if (this.getX() > (Grid.getWidth() - 1) * GridElement.getElementWidth() + Grid.getX0()) {
            this.setDirection(LEFT);
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            System.out.println("Едем до въезда");
        }
//заезжаем на АЗС
        else if (this.getX() == (Grid.getWidth() - 1) * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == Grid.getHeight() * GridElement.getElementHeight() + Grid.getY0()) {
            this.setDirection(TOP);
            this.moveY(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
        //едем вверх
        else if (this.getX() == (Grid.getWidth() - 1) * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() < Grid.getHeight() * GridElement.getElementHeight() + Grid.getY0() &&
                this.getY() > Grid.getY0()) {
            this.moveY(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            System.out.println("Едем вверх от въезда");
        }
        //заправились, транулись, уезжаем дальше
        else if (this.getY() == Grid.getY0() &&
                this.getX() > (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0()) {
            this.setDirection(LEFT);
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            System.out.println("Едем влево по АЗС");
        }
//доехали до дороги вниз
        else if (this.getX() == (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() != Grid.getHeight() * GridElement.getElementHeight() + Grid.getY0()) {
            this.setDirection(BOTTOM);
            this.moveY(+1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            System.out.println("Едем на выезд");
        }
        //на выезде, обнуляем кассу и уезжаем
        else if (this.getX() == (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == Grid.getHeight() * GridElement.getElementHeight() + Grid.getY0()) {
            this.setDirection(LEFT);
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            //ft.setCurrentVolume();
            //ft.setStatus(true);
            System.out.println("Уезжает в закат");
        }
//выехали на дорогу, уезжаем
        else if (this.getX() < (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == Grid.getHeight() * GridElement.getElementHeight() + Grid.getY0()) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            System.out.println("Уезжает в закат");
        }
    }
}
