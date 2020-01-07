package topologyObjects;

import elements.CashBox;
import elements.FuelTank;
import elements.Entry;
import elements.Exit;
import elements.PetrolStation;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import visualize.Grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
    private static ArrayList<PetrolStation> listOfPetrolStations;
    private static double probabilityOfArrival;

    public static void setProbabilityOfArrival(double poa){
        probabilityOfArrival = poa;
    }

    public Vehicle(int x, int y, double poa/*CarRepository carRepository, FuelRepository fuelRepository,*/) {
        super(x, y);
        probabilityOfArrival = poa;
        randValue = rand.nextDouble();
/*List<Car> carList = carRepository.findAll();
Random rand = new Random();
Car car = carList.get(rand.nextInt(carList.size()));
this.model = car.getModel();
this.tankVolume = car.getTankVolume();
this.fuelType = car.getFuelType();*/
/* actualFuelVolume = tankVolume/100*rand.nextInt(99);
List<Fuel> fuelList = fuelRepository.findAll();
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
    public void go(/*Cashbox cashbox, List<FuelTank> listOfFuelTanks,*/ ImageView imageView) throws InterruptedException {

        if (randValue > probabilityOfArrival) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        } else {
//движение до въезда
            if (this.getX() > Entry.getX() * 50 + Grid.getX0()) {
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                System.out.println("Едем до въезда");
            }
//поиск свободной ТРК, если нашел - 1 пиксель вверх, нет - 1 пиксель влево
            else if (this.getX() == Entry.getX() * 50 + Grid.getX0() && this.getY() == Entry.getY() * 50 + Grid.getY0()) {
                /*PetrolStation pt_tmp;
                int tmp = 0;
                Iterator<PetrolStation> iter = listOfPetrolStations.iterator();
                while (iter.hasNext()) {
                    if (listOfPetrolStations.get(tmp).getStatus()) {
                        pt_tmp = listOfPetrolStations.get(tmp);
                        if (pt.getX() == 0 && pt.getY() == 0)
                            pt = pt_tmp;
                        if (pt_tmp.getX() + pt_tmp.getY() > pt.getX() + pt.getY())
                            pt = pt_tmp;
                    }
                    tmp++;
                }
                if (pt != null)
                    pt.setStatus(false);
                if (pt != null && !pt.getStatus()) {*/
                    this.moveY(-1);
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                System.out.println("На въезде. Едем вверх");/*
                } else if (pt == null) {
                    this.moveX(-1);
                    imageView.setTranslateX(this.getX());
                    imageView.setTranslateY(this.getY());
                }*/
            }
//не нашли трк, едем до окнца дороги
            else if (this.getX() < Entry.getX() * 50 + Grid.getX0() && this.getY() == Entry.getY() * 50 + Grid.getY0()) {
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                System.out.println("НЕ ДОЛЖЕН БВЛ ЗАЙТИ");
            }
            //нашли трк, едем вверх до уровня над ней
            else if (this.getX() == Entry.getX() * 50 + Grid.getX0() && this.getY() < Entry.getY() * 50 + Grid.getY0() && this.getY() > /*pt.getY()*/ 2 * 50 - 50 + Grid.getY0()) {
                this.moveY(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                System.out.println("Едем вверх от въезда");
            }
//едем влево до ТРК
            /*else if (this.getX() > pt.getX() * 40 + Grid.getX0() && this.getY() == pt.getY() * 40 - 40 + Grid.getY0()) {
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
//заправляемся и уезжаем
            else if (this.getX() == pt.getX() * 40 + Grid.getX0() && this.getY() == pt.getY() * 40 - 40 + Grid.getY0() && pt.getStatus() == false) {
//Sleep время заправки pt.getSpeed()
//this.fill(listOfFuelTanks);
//this.pay(cashbox, this.payment);
                pt.setStatus(true);

                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }*/
//заправились, транулись, уезжаем дальше
            else if (/*this.getX() < pt.getX() * 40 + Grid.getX0() &&*/ this.getY() == /*pt.getY()*/ 2 * 50 - 50 + Grid.getY0() /*&& pt.getStatus() == true*/ && this.getX() > Exit.getX() * 50 + Grid.getX0()) {
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                System.out.println("Едем влево после типо ТРК");
            }
//доехали до дороги вниз
            else if (/*pt.getStatus() &&*/ this.getX() == Exit.getX() * 50 + Grid.getX0() && this.getY() != Exit.getY() * 50 + Grid.getY0()) {
                this.moveY(+1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                System.out.println("Едем на выезд");
            }
//выехали на дорогу, уезжаем
            else if (/*pt.getStatus() &&*/ this.getX() <= Exit.getX() * 50 + Grid.getX0() && this.getY() == Exit.getY() * 50 + Grid.getY0()) {
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
                System.out.println("Уезжает в зака");
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
