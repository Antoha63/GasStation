package topologyObjects;

import Log.LogMessage;
import controller.ImitationController;
import controller.MoveController;
import elements.CashBox;
import elements.FuelTank;
import elements.Entry;
import elements.Exit;
import elements.PetrolStation;
import entities.Car;
import entities.Fuel;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import visualize.Grid;
import visualize.GridElement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static topologyObjects.TransportVehicleDirection.*;

@Getter
@Setter
@ToString
public class Vehicle extends TransportVehicle {
    private String model;
    private int tankVolume;
    private double actualFuelVolume;
    private String fuelType;
    private int payment;
    private Random rand = new Random();
    private double randValue;
    private int pt = 666;
    private int stop = 0;
    private static double probabilityOfArrival;
    private boolean comeLogMessage = true;

    public static void setProbabilityOfArrival(double poa) {
        probabilityOfArrival = poa;
    }

    public Vehicle(int x, int y, double poa/*CarRepository carRepository, FuelRepository fuelRepository,*/) {
        super(x, y);
        probabilityOfArrival = poa;
        randValue = rand.nextDouble();
        fuelType = "98";
        tankVolume = 50;
        double tv = (double) tankVolume;
        actualFuelVolume = tv/100*(rand.nextInt(99 ) + 1 );
        double pymnt = actualFuelVolume * 30;
        payment = (int) actualFuelVolume * 30;



/*List<Car> carList = carRepository.findAll();
Random rand = new Random();
Car car = carList.get(rand.nextInt(carList.size()));
this.model = car.getModel();
this.tankVolume = car.getTankVolume();
this.fuelType = car.getFuelType();*/
/*double tv = (double) tankVolume;
        actualFuelVolume =  tv/100*(rand.nextInt(99 ) + 1 );*/
/*List<Fuel> fuelList = fuelRepository.findAll();
for (Fuel f : fuelList){
if (f.getName() == this.fuelType)
this.payment = actualFuelVolume *f.getPrice();
}*/

    }

    public static double getProbabilityOfArrival() {
        return probabilityOfArrival;
    }


    public void pay(CashBox cashbox, int payment) {
        cashbox.setPayment(payment);
    }

    public void fill() {
        for (FuelTank ft : Grid.getListOfFuelTanks()) {
            if (ft.getFuel().equals(this.fuelType)){
                //System.out.println("actualFuelVolume " + actualFuelVolume);
                ft.minusVolume((int)actualFuelVolume);
                //System.out.println("ft.getCurrentVolume() " + ft.getCurrentVolume());
            }
        }
    }

    @Override
    public void go(/*List<FuelTank> listOfFuelTanks,*/ ImageView imageView) throws InterruptedException, NullPointerException {

        if (randValue > probabilityOfArrival) {
            this.moveX(-1 * MoveController.getSliderMode());
            this.setDirection(LEFT);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        } else {
//движение до въезда
            if (this.getX() > Entry.getX() * GridElement.getElementWidth() + Grid.getX0()) {
                this.moveX(-1 * MoveController.getSliderMode());
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
//поиск свободной ТРК, если нашел - 1 пиксель вверх, нет - 1 пиксель влево
            else if (this.getX() == Entry.getX() * GridElement.getElementWidth() + Grid.getX0() && this.getY() == Entry.getY() * GridElement.getElementHeight() + Grid.getY0()) {
                List<PetrolStation> lOPS = new ArrayList<>();
                for (int i = 0; i < Grid.getListOfPetrolStations().size(); i++){
                    if (Grid.getListOfPetrolStations().get(i).getStatus()){
                        lOPS.add(Grid.getListOfPetrolStations().get(i));
                    }
                }
                if (lOPS.size() != 0) {
                    PetrolStation pt_tmp = lOPS.get(0);
                    for (int i = 1; i < lOPS.size() - 1; i++) {
                        if (pt_tmp.getX() + pt_tmp.getY() < Grid.getListOfPetrolStations().get(i).getX() + Grid.getListOfPetrolStations().get(i).getY())
                            pt_tmp = Grid.getListOfPetrolStations().get(i);
                    }
                    pt = Grid.getListOfPetrolStations().indexOf(pt_tmp);
                }
                if (pt != 666)
                    Grid.getListOfPetrolStations().get(pt).setStatus(false);
                if (pt != 666 && !Grid.getListOfPetrolStations().get(pt).getStatus()) {
                    //System.out.println("КООРДИНАТЫ ТРК " + Grid.getListOfPetrolStations().get(pt).getX() + "  " + Grid.getListOfPetrolStations().get(pt).getY());
                    this.moveY(-1 * MoveController.getSliderMode());
                    this.setDirection(TOP);
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                    //System.out.println("НАШЕЛ. Едем вверх");
                } else if (pt == 666) {
                    this.setDirection(LEFT);
                    this.moveX(-1 * MoveController.getSliderMode());
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                    //System.out.println("НЕ НАШЕЛ СВОБОДНОЙ ПТ");
                }
            }
//не нашли трк, едем до окнца дороги
            else if (this.getX() < Entry.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                    this.getY() == Entry.getY() * GridElement.getElementHeight() + Grid.getY0() &&
                    this.getX() > Exit.getX() * GridElement.getElementWidth() + Grid.getX0())  {
                this.setDirection(LEFT);
                this.moveX(-1 * MoveController.getSliderMode());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                //System.out.println("НЕ ДОЛЖЕН БВЛ ЗАЙТИ");
            }
            //нашли трк, едем вверх до уровня над ней
            else if (pt!=666 && this.getX() == Entry.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                    this.getY() < Entry.getY() * GridElement.getElementHeight() + Grid.getY0() &&
                    this.getY() > Grid.getListOfPetrolStations().get(pt).getY() * GridElement.getElementHeight() - GridElement.getElementHeight() + Grid.getY0()) {
                this.setDirection(TOP);
                this.moveY(-1 * MoveController.getSliderMode());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                if (this.getY() < Grid.getListOfPetrolStations().get(pt).getY() * GridElement.getElementHeight() - GridElement.getElementHeight() + Grid.getY0()){
                    this.setY(Grid.getListOfPetrolStations().get(pt).getY() * GridElement.getElementHeight() - GridElement.getElementHeight() + Grid.getY0());
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                }
                //System.out.println("Едем вверх от въезда");
            }
//едем влево до ТРК
            else if (pt!=666 && this.getX() > Grid.getListOfPetrolStations().get(pt).getX() * GridElement.getElementWidth() + Grid.getX0() && this.getY() == Grid.getListOfPetrolStations().get(pt).getY() * GridElement.getElementHeight() - GridElement.getElementHeight() + Grid.getY0() && !Grid.getListOfPetrolStations().get(pt).getStatus()) {
                this.setDirection(LEFT);
                this.moveX(-1 * MoveController.getSliderMode());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                if (this.getX() < Grid.getListOfPetrolStations().get(pt).getX() * GridElement.getElementWidth() + Grid.getX0()){
                    this.setX(Grid.getListOfPetrolStations().get(pt).getX() * GridElement.getElementWidth() + Grid.getX0());
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                }
            }
//заправляемся и уезжаем
            else if (pt!=666 && this.getX() == Grid.getListOfPetrolStations().get(pt).getX() * GridElement.getElementWidth() + Grid.getX0()
                    && this.getY() == Grid.getListOfPetrolStations().get(pt).getY() * GridElement.getElementHeight() - GridElement.getElementHeight() + Grid.getY0() && Grid.getListOfPetrolStations().get(pt).getStatus() == false) {
                if (stop <= 62 * PetrolStation.getSpeed() / 60 * actualFuelVolume / MoveController.getSliderMode()) {
                    stop++;
                    if (comeLogMessage) {
                        new LogMessage(model + " заправляется. Топливо: " + fuelType + ", " + actualFuelVolume + " л");
                        comeLogMessage = false;
                    }
                }
                else {
                    new LogMessage(model + " заправлен. Сумма: " + payment + " р");
                    this.fill();
                    CashBox.setPayment(payment);
                    Grid.getListOfPetrolStations().get(pt).setStatus(true);
                    this.setDirection(LEFT);
                    this.moveX(-1 * MoveController.getSliderMode());
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                    //System.out.println(CashBox.getBalance());
                    //System.out.println(CashBox.getCriticalLevel() + "Critical velue");
                }
            }
//заправились, тронулись, уезжаем дальше
            else if (pt!=666 && this.getX() < Grid.getListOfPetrolStations().get(pt).getX() * GridElement.getElementWidth() + Grid.getX0() && this.getY() == Grid.getListOfPetrolStations().get(pt).getY() * GridElement.getElementHeight() - GridElement.getElementHeight() + Grid.getY0()
                    && this.getX() > Exit.getX() * GridElement.getElementWidth() + Grid.getX0()) {
                this.setDirection(LEFT);
                this.moveX(-1 * MoveController.getSliderMode());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                if (this.getX() < Exit.getX() * GridElement.getElementWidth() + Grid.getX0()){
                    this.setX(Exit.getX() * GridElement.getElementWidth() + Grid.getX0());
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                }
                //System.out.println("Едем влево после типо ТРК");
            }
//доехали до дороги вниз
            else if (pt!=666 && this.getX() == Exit.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                    this.getY() != Exit.getY() * GridElement.getElementHeight() + Grid.getY0()) {
                this.setDirection(BOTTOM);
                this.moveY(+1 * MoveController.getSliderMode());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                if (this.getY() > Exit.getY() * GridElement.getElementHeight() + Grid.getY0()){
                    this.setY(Exit.getY() * GridElement.getElementHeight() + Grid.getY0());
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                }
                //System.out.println("Едем на выезд");
            }
//выехали на дорогу, уезжаем
            else if (this.getX() <= Exit.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                    this.getY() == Exit.getY() * GridElement.getElementWidth() + Grid.getY0()) {
                this.setDirection(LEFT);
                this.moveX(-1 * MoveController.getSliderMode());
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                if (this.getX() < Grid.getX0()){
                    this.setX(Grid.getX0());
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                }
                //System.out.println("Уезжает в зака");
            }
        }
    }
}
