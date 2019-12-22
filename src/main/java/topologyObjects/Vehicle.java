package topologyObjects;

import elements.Cashbox;
import elements.FuelTank;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import visualize.Grid;

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
    private double probabilityOfArrival;
    private int payment;

    public Vehicle(int x, int y, double probabilityOfArrival/*CarRepository carRepository, FuelRepository fuelRepository,*/ ) {
        super(x, y, probabilityOfArrival);
        /*List<Car> carList = carRepository.findAll();
        Random rand = new Random();
        Car car = carList.get(rand.nextInt(carList.size()));
        this.model = car.getModel();
        this.tankVolume = car.getTankVolume();
        this.fuelType = car.getFuelType();*/
/*        actualFuelVolume = tankVolume/100*rand.nextInt(99);
        List<Fuel> fuelList = fuelRepository.findAll();
        for (Fuel f : fuelList){
            if (f.getName() == this.fuelType)
                this.payment = actualFuelVolume *f.getPrice();
        }*/

    }

    public void pay(Cashbox cashbox, int payment) {
        cashbox.setBalance(payment);
    }

    public void fill(List<FuelTank> listOfFuelTanks) {
        for (FuelTank ft : listOfFuelTanks) {
            if (ft.getFuel() == this.fuelType)
                ft.setCurrentVolume(this.actualFuelVolume);
        }
    }

    @Override
    public void go(/*Cashbox cashbox, Entry entry, Exit exit, List<FuelTank> listOfFuelTanks, ArrayList<PetrolStation> listOfPetrolStations,*/ImageView imageView) throws InterruptedException {
        Random rand = new Random();

        if (rand.nextDouble() > this.probabilityOfArrival) {
            this.moveX(-8);
            imageView.setX(this.getX());
            imageView.setY(this.getY());
        }
//        else {
//            while (this.x != entry.getX()) {
//                this.x--;
//            }
//
//            PetrolStation pt = new PetrolStation();
//            int tmp = 0;
//            Iterator<PetrolStation> iter = listOfPetrolStations.iterator();
//            while(iter.hasNext()){
//                if(listOfPetrolStations.get(tmp).getStatus() == true) {
//                    pt = listOfPetrolStations.get(tmp);
//                }
//                tmp++;
//            }
//            pt.setStatus(false);
//
////кратчайший путь до свободной ТРК (pt) goTo(pt.getX(),pt.getY())
////Sleep время заправки pt.getSpeed()
//            this.fill(listOfFuelTanks);
//            this.pay(cashbox, this.payment);
//
////кратчайший путь до выезда
//            if (this.x == exit.getX() && this.y == exit.getY())
//                this.y++;
//            while (this.x > 0) {
//                this.x--;
//            }
//        }
    }

}