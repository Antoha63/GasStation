package topologyObjects;

import Log.Log;
import controller.ControllerType;
import controller.ControllersRepository;
import controller.ImitationController;
import elements.Exit;
import elements.FuelTank;
import javafx.scene.image.ImageView;
import visualize.Grid;
import lombok.NoArgsConstructor;
import visualize.GridElement;

import static topologyObjects.TransportVehicleDirection.*;


@NoArgsConstructor
public class CollectorFuel extends TransportVehicle {
    private String fuelType;
    private int numOfFuelTank = 666;
    private int timeToStop = 0;
    private ImitationController imitationController = (ImitationController)
            ControllersRepository.getController(ControllerType.IMITATIONCONTROLLER);

    public CollectorFuel (int x, int y){
        super(x,y);
    }

    public CollectorFuel (int x, int y, String fuelType){
        super(x,y);
        this.fuelType = fuelType;
    }

    public void setFuelType (String s){
        fuelType = s;
    }
    public String getFuelType (){
        return fuelType;
    }

    @Override
    public void go(ImageView imageView) throws InterruptedException {
        int sliderMode = (int)imitationController.getSliderMode().getValue();

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
            this.moveX(-1 * sliderMode);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            if (this.getX() < (Grid.getWidth() - 1) * GridElement.getElementWidth() + Grid.getX0()) {
                this.setX((Grid.getWidth() - 1) * GridElement.getElementWidth() + Grid.getX0());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
            //System.out.println("Едем до въезда");
        }
//заезжаем на АЗС
        else if (this.getX() == (Grid.getWidth() - 1) * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == Grid.getHeight() * GridElement.getElementHeight() + Grid.getY0()) {
            this.setDirection(TOP);
            this.moveY(-1 * sliderMode);
            //поиск того ТБ, к которому он вызван
            for (int i = 0; i < Grid.getListOfFuelTanks().size(); i++){
                if (Grid.getListOfFuelTanks().get(i).getFuel().equals(fuelType) && !Grid.getListOfFuelTanks().get(i).getStatus())
                    numOfFuelTank = i;
            }
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
        //едем вверх
        else if (this.getX() == (Grid.getWidth() - 1) * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() < Grid.getHeight() * GridElement.getElementHeight() + Grid.getY0() &&
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
        else if (this.getY() == Grid.getY0() &&
                this.getX() > (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0()) {
            this.setDirection(LEFT);
            this.moveX(-1 * sliderMode);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            if (this.getX() < (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0()){
                this.setX((Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
            //System.out.println("Едем влево по АЗС");
        }
//доехали до дороги вниз

        else if (this.getX() == (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0() && numOfFuelTank != 666 &&
                this.getY() == Grid.getListOfFuelTanks().get(numOfFuelTank).getY() * GridElement.getElementHeight() + Grid.getY0() + 1 &&
                sliderMode >= 2) {
            if (timeToStop <= 62 / sliderMode){
                timeToStop++;
            }
            else{
                Log.sendMessage("Дозаправщик разгрузился. Топливо: " + fuelType + ", " + (FuelTank.getVolume() - Grid.getListOfFuelTanks().get(numOfFuelTank).getCurrentVolume()) + "л");
                this.setDirection(BOTTOM);
                this.moveY(+2 * sliderMode);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                Grid.getListOfFuelTanks().get(numOfFuelTank).setCurrentVolume(FuelTank.getVolume());
                Grid.getListOfFuelTanks().get(numOfFuelTank).setStatus(true);
            }
        }
        else if (this.getX() == (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0() && numOfFuelTank != 666 &&
                this.getY() == Grid.getListOfFuelTanks().get(numOfFuelTank).getY() * GridElement.getElementHeight() + Grid.getY0() + 2 &&
                sliderMode >= 2) {
            if (timeToStop <= 62 / sliderMode){
                timeToStop++;
            }
            else{
                Log.sendMessage("Дозаправщик разгрузился. Топливо: " + fuelType + ", " + (FuelTank.getVolume() - Grid.getListOfFuelTanks().get(numOfFuelTank).getCurrentVolume()) + "л");
                this.setDirection(BOTTOM);
                this.moveY(+2 * sliderMode);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                Grid.getListOfFuelTanks().get(numOfFuelTank).setCurrentVolume(FuelTank.getVolume());
                Grid.getListOfFuelTanks().get(numOfFuelTank).setStatus(true);
            }
        }
        //притормозим у кассы, заберем бабло
        else if (this.getX() == (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0() && numOfFuelTank != 666 &&
                this.getY() == Grid.getListOfFuelTanks().get(numOfFuelTank).getY() * GridElement.getElementHeight() + Grid.getY0()) {
            if (timeToStop <= 62 / sliderMode){
                timeToStop++;
            }
            else{
                Log.sendMessage("Дозаправщик разгрузился. Топливо: " + fuelType + ", " + (FuelTank.getVolume() - Grid.getListOfFuelTanks().get(numOfFuelTank).getCurrentVolume()) + "л");
                this.setDirection(BOTTOM);
                this.moveY(+1 * sliderMode);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                Grid.getListOfFuelTanks().get(numOfFuelTank).setCurrentVolume(FuelTank.getVolume());
                Grid.getListOfFuelTanks().get(numOfFuelTank).setStatus(true);
            }
        }

        else if (this.getX() == (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() != Grid.getHeight() * GridElement.getElementHeight() + Grid.getY0()) {
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
        else if (this.getX() == (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == Grid.getHeight() * GridElement.getElementHeight() + Grid.getY0()) {
            this.setDirection(LEFT);
            this.moveX(-1 * sliderMode);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
            //ft.setCurrentVolume();
            //ft.setStatus(true);
            //System.out.println("Уезжает в закат");
        }
//выехали на дорогу, уезжаем
        else if (this.getX() < (Grid.getWidth() - 3) * GridElement.getElementWidth() + Grid.getX0() &&
                this.getY() == Grid.getHeight() * GridElement.getElementHeight() + Grid.getY0()) {
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
