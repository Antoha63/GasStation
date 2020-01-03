package topologyObjects;

import elements.CashBox;
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

    public void pay(CashBox cashbox, int payment) {
        cashbox.setPayment(payment);
    }

    public void fill(List<FuelTank> listOfFuelTanks) {
        for (FuelTank ft : listOfFuelTanks) {
            if (ft.getFuel() == this.fuelType)
                ft.minusVolume(this.actualFuelVolume);
        }
    }

    @Override
    public void go(/*Cashbox cashbox, List<FuelTank> listOfFuelTanks, ArrayList<PetrolStation> listOfPetrolStations,*/ImageView imageView) throws InterruptedException {
        Random rand = new Random();

        if (rand.nextDouble() > this.getProbabilityOfArrival()) {
            this.moveX(-1);
            imageView.setTranslateX(this.getX());
            imageView.setTranslateY(this.getY());
        }
/*
        else {
            while (this.x != entry.getX()) {
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
            this.fill(listOfFuelTanks);
            this.pay(cashbox, this.payment);

//кратчайший путь до выезда
            while (this.x != exit.getX()){
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
            while (this.y != Grid.getHeight() -1){
                this.moveY(+1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
            while (this.x > 0){
                this.moveX(-1);
                imageView.setTranslateX(this.getX());
                imageView.setTranslateY(this.getY());
            }
        }
    }
*/
}

}