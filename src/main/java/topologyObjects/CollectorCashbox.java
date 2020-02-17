package topologyObjects;

import Log.Log;
import controller.ControllerType;
import controller.ControllersRepository;
import controller.ImitationController;
import elements.CashBox;
import elements.Entry;
import elements.Exit;
import javafx.scene.image.ImageView;
import visualize.Grid;
import lombok.NoArgsConstructor;
import visualize.GridElement;

import static topologyObjects.TransportVehicleDirection.*;

@NoArgsConstructor
public class CollectorCashbox extends TransportVehicle {
    private int timeToStop = 0;
    private ImitationController imitationController = (ImitationController)
            ControllersRepository.getController(ControllerType.IMITATIONCONTROLLER);

    public CollectorCashbox (int x, int y){
        super(x,y);
    }

    @Override
    public void go(ImageView imageView) throws InterruptedException {
        int sliderMode = (int)imitationController.getSliderMode().getValue();

        /*CashBox.setStatus(false);
        this.setX(Grid.getWidth() - 1);
        this.setY(Grid.getHeight() - 1);
        while (this.getX() != Entry.getX()) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
        }
        while (this.getY() > 0) {
            this.moveY(-1);
            imageView.setTranslateY(this.getY());
        }
        while (this.getX() != Exit.getX()) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
        }
        while (this.getY() != Grid.getHeight() - 1) {
            this.moveY(1);
            imageView.setTranslateY(this.getY());
        }
        CashBox.setBalance(0);
        CashBox.setStatus(true);
        while (this.getX() > 0) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
        }*/


        if (this.getX() > Entry.getX() * GridElement.getElementWidth() + Grid.getX0()) {
            this.moveX(-1 * sliderMode);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            this.setDirection(LEFT);
            if (this.getX() < Entry.getX() * GridElement.getElementWidth() + Grid.getX0()) {
                this.setX(Entry.getX() * GridElement.getElementWidth() + Grid.getX0());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
            //System.out.println("Едем до въезда");
        }
//заезжаем на АЗС
        else if (this.getX() == Entry.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == Entry.getY() * GridElement.getElementHeight() + Grid.getY0()) {
            this.setDirection(TOP);
            this.moveY(-1 * sliderMode);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
        //едем вверх
        else if (this.getX() == Entry.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() < Entry.getY() * GridElement.getElementHeight() + Grid.getY0() &&
                this.getY() > Grid.getY0()) {
            this.moveY(-1 * sliderMode);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            if (this.getY() < Grid.getY0()){
                this.setY(Grid.getY0());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
            //System.out.println("Едем вверх от въезда");
        }
        //заправились, транулись, уезжаем дальше
        else if (this.getY() == Grid.getY0() && this.getX() > Exit.getX() * GridElement.getElementWidth() + Grid.getX0()) {
            this.setDirection(LEFT);
            this.moveX(-1 * sliderMode);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            if (this.getX() < Exit.getX() * GridElement.getElementWidth() + Grid.getX0()){
                this.setX(Exit.getX() * GridElement.getElementWidth() + Grid.getX0());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
            //System.out.println("Едем влево по АЗС");
        }

//доехали до дороги вниз

        else if (this.getX() == Exit.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == CashBox.getY() * GridElement.getElementHeight() + Grid.getY0() + 1 &&
                sliderMode >= 2) {
            if (timeToStop <= 62 / sliderMode){
                timeToStop++;
            }
            else{
                Log.sendMessage("Инкассатор забрал " + CashBox.getBalance() + " р");
                this.setDirection(BOTTOM);
                this.moveY(+2 * sliderMode);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                CashBox.setBalance(0);
                CashBox.setStatus(true);
            }
        }
        else if (this.getX() == Exit.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == CashBox.getY() * GridElement.getElementHeight() + Grid.getY0() + 2 &&
                sliderMode >= 2) {
            if (timeToStop <= 62 / sliderMode){
                timeToStop++;
            }
            else{
                Log.sendMessage("Инкассатор забрал " + CashBox.getBalance() + " р");
                this.setDirection(BOTTOM);
                this.moveY(+2 * sliderMode);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                CashBox.setBalance(0);
                CashBox.setStatus(true);
            }
        }
        //притормозим у кассы, заберем бабло
        else if (this.getX() == Exit.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == CashBox.getY() * GridElement.getElementHeight() + Grid.getY0()) {
            if (timeToStop <= 62 / sliderMode){
                timeToStop++;
            }
            else{
                Log.sendMessage("Инкассатор забрал " + CashBox.getBalance() + " р");
                this.setDirection(BOTTOM);
                this.moveY(+1 * sliderMode);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                CashBox.setBalance(0);
                CashBox.setStatus(true);
            }
        }

        else if (this.getX() == Exit.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() != Exit.getY() * GridElement.getElementHeight() + Grid.getY0() &&
                this.getY() != CashBox.getY() * GridElement.getElementHeight() + Grid.getY0()) {
            this.setDirection(BOTTOM);
            this.moveY(+1 * sliderMode);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            if (this.getY() > Exit.getY() * GridElement.getElementHeight() + Grid.getY0()){
                this.setY(Exit.getY() * GridElement.getElementHeight() + Grid.getY0());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
            //System.out.println("Едем на выезд");
        }




        //на выезде, обнуляем кассу и уезжаем
        else if (this.getX() == Exit.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == Exit.getY() * GridElement.getElementHeight() + Grid.getY0()) {
            this.setDirection(LEFT);
            this.moveX(-1 * sliderMode);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            //System.out.println("Уезжает в закат");
        }
//выехали на дорогу, уезжаем
        else if (this.getX() < Exit.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == Exit.getY() * GridElement.getElementHeight() + Grid.getY0()) {
            this.moveX(-1 * sliderMode);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            if (this.getX() < Grid.getX0()){
                this.setX(Grid.getX0());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
            //System.out.println("Уезжает в закат");
        }

    }
}


