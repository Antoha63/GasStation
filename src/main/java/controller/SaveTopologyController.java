package controller;

import elements.CashBox;
import elements.Entry;
import elements.Exit;
import entities.FuelTank;
import entities.PetrolStation;
import entities.Topology;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.FuelTankRepository;
import repositories.PetrolStationRepository;
import repositories.TopologyRepository;
import visualize.Grid;
import visualize.GridElement;

import java.io.IOException;
import java.util.List;

public class SaveTopologyController {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
    TopologyRepository topologyRepository = context.getBean(TopologyRepository.class);
    PetrolStationRepository petrolStationRepository = context.getBean(PetrolStationRepository.class);
    FuelTankRepository fuelTankRepository = context.getBean(FuelTankRepository.class);

    @FXML
    private Button button;

    @FXML
    private TextField textField;

//    public void initialize() {
//        button.setOnAction(event -> {
//            Stage stage = (Stage) button.getScene().getWindow();
//            stage.close();
//        });
//    }

    public void save() {
        Topology topology = new Topology();
        topology.setName(textField.getText());
        topology.setHeight(Grid.getHeight());
        topology.setWidth(Grid.getWidth());

        if (CashBox.getStatus()) {
            topology.setCashBoxX(CashBox.getX());
            topology.setCashBoxY(CashBox.getY());
        }

        if (Entry.getStatus()) {
            topology.setEntranceX(Entry.getX());
            topology.setEntranceY(Entry.getY());
        }

        if (Exit.getStatus()) {
            topology.setExitX(Exit.getX());
            topology.setExitY(Exit.getY());
        }
        topologyRepository.save(topology);

        List<elements.PetrolStation> listOfPetrolStations= Grid.getListOfPetrolStations();
        for (elements.PetrolStation petrolStationValue: listOfPetrolStations) {
            PetrolStation petrolStation = new PetrolStation();
            petrolStation.setCoordinateX(petrolStationValue.getX());
            petrolStation.setCoordinateY(petrolStationValue.getY());
            petrolStation.setTopology(topology);
            petrolStationRepository.save(petrolStation);
        }

        List<elements.FuelTank> listOfFuelTanks= Grid.getListOfFuelTanks();
        for (elements.FuelTank fuelTankValue: listOfFuelTanks) {
            FuelTank fuelTank = new FuelTank();
            fuelTank.setCoordinateX(fuelTankValue.getX());
            fuelTank.setCoordinateY(fuelTankValue.getY());
            fuelTank.setTopology(topology);
            fuelTankRepository.save(fuelTank);
        }

        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
