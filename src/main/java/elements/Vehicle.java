package elements;

import repositories.CarRepository;
import entities.Car;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Vehicle {

    private int x;
    private int y;
    private String model;
    private int tankVolume;
    private int actualFuelValume;
    private String fuelType;

    public Vehicle(CarRepository carRepository){
        List<Car> carList = new ArrayList<Car>();
        carList = carRepository.findAll();
        Random rand = new Random();
        Car car = new Car();
        car = carList.get(rand.nextInt(carList.size()));
        this.model = car.getModel();
        this.tankVolume = car.getTankVolume();
        this.fuelType = car.getFuelType();
        actualFuelValume = tankVolume/100*rand.nextInt(99);
    }

    public void pay(Cashbox cashbox, int payment){
        cashbox.setBalance(payment);
    }

    public void fill(List<FuelTank> listOfFuelTanks) {
        for (FuelTank ft : listOfFuelTanks) {
            if (ft.getFuel() == this.fuelType)
                ft.setCurrentVolume(actualFuelValume);
        }
    }

}