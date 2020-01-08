package topologyObjects;

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
    private int actualFuelVolume;
    private String fuelType;
    private int payment;
    private Random rand = new Random();
    private double randValue;
    private PetrolStation pt;
    private static double probabilityOfArrival;

    public static void setProbabilityOfArrival(double poa) {
        probabilityOfArrival = poa;
    }

    public Vehicle(int x, int y, double poa/*CarRepository carRepository, FuelRepository fuelRepository,*/) {
        super(x, y);
        probabilityOfArrival = poa;
        randValue = rand.nextDouble();
        fuelType = "98";
        tankVolume = 100;
        payment = actualFuelVolume * 30;



/*List<Car> carList = carRepository.findAll();
Random rand = new Random();
Car car = carList.get(rand.nextInt(carList.size()));
this.model = car.getModel();
this.tankVolume = car.getTankVolume();
this.fuelType = car.getFuelType();*/
        actualFuelVolume = tankVolume / 100 * rand.nextInt(99);
/*List<Fuel> fuelList = fuelRepository.findAll();
for (Fuel f : fuelList){
if (f.getName() == this.fuelType)
this.payment = actualFuelVolume *f.getPrice();
}*/

    }

    public void pay(CashBox cashbox, int payment) {
        cashbox.setPayment(payment);
    }

    public void fill(List<FuelTank> listOfFuelTanks) {
        for (FuelTank ft : listOfFuelTanks) {
            if (ft.getFuel().equals(this.fuelType))
                ft.minusVolume(this.actualFuelVolume);
        }
    }

    @Override
    public void go(/*List<FuelTank> listOfFuelTanks,*/ ImageView imageView) throws InterruptedException, NullPointerException {

        if (randValue > probabilityOfArrival) {
            this.moveX(-1);
            this.setDirection(LEFT);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        } else {
//движение до въезда
            if (this.getX() > Entry.getX() * GridElement.getElementWidth() + Grid.getX0()) {
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                this.setDirection(LEFT);
                //System.out.println("Едем до въезда");
            }
//поиск свободной ТРК, если нашел - 1 пиксель вверх, нет - 1 пиксель влево
            else if (this.getX() == Entry.getX() * GridElement.getElementWidth() + Grid.getX0() && this.getY() == Entry.getY() * GridElement.getElementHeight() + Grid.getY0()) {
                PetrolStation pt_tmp;
                int tmp = 0;
                List<PetrolStation> lOPS;
                lOPS = Grid.getListOfPetrolStations();
                Iterator<PetrolStation> iter = Grid.getListOfPetrolStations().iterator();
                while (iter.hasNext() && tmp < Grid.getListOfPetrolStations().size()) {
                    if (tmp <= Grid.getListOfPetrolStations().size() - 1 && Grid.getListOfPetrolStations().get(tmp).getStatus()) {
                        pt_tmp = Grid.getListOfPetrolStations().get(tmp);
                        if (tmp == 0)
                            pt = pt_tmp;
                        else if (pt_tmp != null && pt != null) {
                            if (pt_tmp.getX() + pt_tmp.getY() > pt.getX() + pt.getY()) {

                                pt = pt_tmp;
                            }
                        }
                    }
                    tmp++;
                }

                /*Iterator<PetrolStation> iter = lOPS.iterator();
                while (iter.hasNext() && tmp < lOPS.size()) {
                    if (tmp <= lOPS.size() - 1 && lOPS.get(tmp).getStatus()) {
                        pt_tmp = lOPS.get(tmp);
                        if (tmp == 0)
                            pt = pt_tmp;
                        else if (pt_tmp != null && pt != null){
                            if (pt_tmp.getX() + pt_tmp.getY() > pt.getX() + pt.getY()){

                            pt = pt_tmp;
                            }
                        }
                    }
                    tmp++;
                }*//*
                for (int i = 0; i < lOPS.size(); i++){
                    if (lOPS.get(i).getStatus()){
                        if (i == 0)
                            pt = lOPS.get(i);
                        else if (pt.getX() + pt.getY() < lOPS.get(i).getY() + lOPS.get(i).getX())
                            pt = lOPS.get(i);
                    }
                }*/
                if (pt != null)
                    pt.setStatus(false);
                if (pt != null && !pt.getStatus()) {
                    //System.out.println("КООРДИНАТЫ ТРК " + pt.getX() + "  " + pt.getY());
                    this.moveY(-1);
                    this.setDirection(TOP);
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                    //System.out.println("НАШЕЛ. Едем вверх");
                } else if (pt == null) {
                    this.setDirection(LEFT);
                    this.moveX(-1);
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                    //System.out.println("НЕ НАШЕЛ СВОБОДНОЙ ПТ");
                }
            }
//не нашли трк, едем до окнца дороги
            else if (this.getX() < Entry.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                    this.getY() == Entry.getY() * GridElement.getElementHeight() + Grid.getY0() &&
                    this.getX() > Exit.getX() * GridElement.getElementWidth() + Grid.getX0()) {
                this.setDirection(LEFT);
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                //System.out.println("НЕ ДОЛЖЕН БВЛ ЗАЙТИ");
            }
            //нашли трк, едем вверх до уровня над ней
            else if (this.getX() == Entry.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                    this.getY() < Entry.getY() * GridElement.getElementHeight() + Grid.getY0() &&
                    this.getY() > pt.getY() * GridElement.getElementHeight() - GridElement.getElementHeight() + Grid.getY0()) {
                this.setDirection(TOP);
                this.moveY(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                //System.out.println("Едем вверх от въезда");
            }
//едем влево до ТРК
            else if (this.getX() > pt.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                    this.getY() == pt.getY() * GridElement.getElementHeight() - GridElement.getElementHeight() + Grid.getY0() &&
                    !pt.getStatus()) {
                this.setDirection(LEFT);
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
//заправляемся и уезжаем
            else if (this.getX() == pt.getX() * GridElement.getElementWidth() + Grid.getX0()
                    && this.getY() == pt.getY() * GridElement.getElementHeight() - GridElement.getElementHeight() + Grid.getY0() && pt.getStatus() == false) {
//Sleep время заправки pt.getSpeed()
//this.fill(listOfFuelTanks);
//this.pay(cashbox, this.payment);
                pt.setStatus(true);
                this.setDirection(LEFT);
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
//заправились, тронулись, уезжаем дальше
            else if (this.getX() < pt.getX() * GridElement.getElementWidth() + Grid.getX0() && this.getY() == pt.getY() * GridElement.getElementHeight() - GridElement.getElementHeight() + Grid.getY0()
                    && pt.getStatus() == true && this.getX() > Exit.getX() * GridElement.getElementWidth() + Grid.getX0()) {
                this.setDirection(LEFT);
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                //System.out.println("Едем влево после типо ТРК");
            }
//доехали до дороги вниз
            else if (pt.getStatus() && this.getX() == Exit.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                    this.getY() != Exit.getY() * GridElement.getElementHeight() + Grid.getY0()) {
                this.setDirection(BOTTOM);
                this.moveY(+1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                //System.out.println("Едем на выезд");
            }
//выехали на дорогу, уезжаем
            else if (pt.getStatus() && this.getX() <= Exit.getX() * GridElement.getElementWidth() + Grid.getX0() &&
                    this.getY() == Exit.getY() * GridElement.getElementWidth() + Grid.getY0()) {
                this.setDirection(LEFT);
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                //System.out.println("Уезжает в зака");
            }


/*while (this.getX() != 4 /*Entry.getX()) {
this.moveX(-1);
imageView.setTranslateX(this.getX());
imageView.setTranslateY(this.getY());
}

PetrolStation pt_tmp = new PetrolStation();
PetrolStation pt = new PetrolStation();
int tmp = 0;
Iterator<PetrolStation> iter = listOfPetrolStations.iterator();
while(iter.hasNext()){
if(listOfPetrolStations.get(tmp).getStatus() == true) {
pt_tmp = listOfPetrolStations.get(tmp);
if (pt.getX() == 0 && pt.getY() == 0)
pt = pt_tmp;
if (pt_tmp.getX() + pt_tmp.getY() > pt.getX() + pt.getY())
pt = pt_tmp;
}
tmp++;
}
pt.setStatus(false);

while (this.y != pt.getY()+1) {
this.moveY(-1);
imageView.setTranslateX(this.getX());
imageView.setTranslateY(this.getY());
}
while (this.x != pt.getX()) {
this.moveX(-1);
imageView.setTranslateX(this.getX());
imageView.setTranslateY(this.getY());
}

//кратчайший путь до свободной ТРК (pt) goTo(pt.getX(),pt.getY())
//Sleep время заправки pt.getSpeed()
//this.fill(listOfFuelTanks);
//this.pay(cashbox, this.payment);

//кратчайший путь до выезда
while (this.getX() != 1 Exit.getX()){
this.moveX(-1);
imageView.setTranslateX(this.getX());
imageView.setTranslateY(this.getY());
}
while (this.getY() != Grid.getHeight() -1){
this.moveY(+1);
imageView.setTranslateX(this.getX());
imageView.setTranslateY(this.getY());
}
while (this.getX() > 0){
this.moveX(-1);
imageView.setTranslateX(this.getX());
imageView.setTranslateY(this.getY());
}*/
        }
    }
}
