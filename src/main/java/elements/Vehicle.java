package elements;

import entities.Fuel;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import repositories.CarRepository;
import entities.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import repositories.FuelRepository;
import visualize.Grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Vehicle extends Pane {

    private int x;
    private int y;
    private String model;
    private int tankVolume;
    private int actualFuelVolume;
    private String fuelType;
    private double probabilityOfArrival;
    private int payment;

    public Vehicle(/*CarRepository carRepository, FuelRepository fuelRepository,*/ double poa) {
        /*List<Car> carList = carRepository.findAll();
        Random rand = new Random();
        Car car = carList.get(rand.nextInt(carList.size()));
        this.model = car.getModel();
        this.tankVolume = car.getTankVolume();
        this.fuelType = car.getFuelType();*/
        this.probabilityOfArrival = poa;
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

    public ImageView go(/*Cashbox cashbox, Entry entry, Exit exit, List<FuelTank> listOfFuelTanks, ArrayList<PetrolStation> listOfPetrolStations,*/ImageView imageView) throws InterruptedException {
        Random rand = new Random();

        //this.x = Grid.getWidth() - 1;
        //this.y = Grid.getHeight() - 1;

        imageView.setX(this.x++);
        imageView.setY(this.y++);
        imageView.setTranslateX(this.x++);
        imageView.setTranslateY(this.y++);

        //if (rand.nextDouble() > this.probabilityOfArrival) {
            //while (this.x > 0)
                //this.x--;
        //}
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
        return imageView;
    }

}