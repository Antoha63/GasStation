import entities.Car;
import entities.Fuel;
import entities.FuelTank;
import entities.PetrolStation;
import entities.Topology;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.CarRepository;
import repositories.FuelRepository;
import repositories.FuelTankRepository;
import repositories.PetrolStationRepository;
import repositories.TopologyRepository;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");

        TopologyRepository topologyRepository = context.getBean(TopologyRepository.class);
        PetrolStationRepository petrolStationRepository = context.getBean(PetrolStationRepository.class);
        FuelTankRepository fuelTankRepository = context.getBean(FuelTankRepository.class);
        CarRepository carRepository = context.getBean(CarRepository.class);
        FuelRepository fuelRepository = context.getBean(FuelRepository.class);

        Topology topology = new Topology();
        topology.setHeight(1);
        topology.setWidth(1);
        topology.setCashBoxX(1);
        topology.setCashBoxY(1);
        topology.setEntranceX(1);
        topology.setEntranceY(1);
        topology.setExitX(1);
        topology.setExitY(1);
        topologyRepository.save(topology);

        PetrolStation petrolStation = new PetrolStation();
        petrolStation.setCoordinateX(1);
        petrolStation.setCoordinateY(1);
        petrolStation.setTopology(topology);
        petrolStationRepository.save(petrolStation);

        FuelTank fuelTank = new FuelTank();
        fuelTank.setCoordinateX(1);
        fuelTank.setCoordinateY(1);
        fuelTank.setTopology(topology);
        fuelTankRepository.save(fuelTank);

        Car car = new Car();
        car.setModel("Mercedes");
        car.setTankVolume(10);
        car.setFuelType("95");
        carRepository.save(car);

        Fuel fuel = new Fuel();
        fuel.setName("95");
        fuel.setPrice(45);
        fuelRepository.save(fuel);
    }
}
