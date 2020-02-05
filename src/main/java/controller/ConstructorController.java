package controller;

import elements.CashBox;
import elements.ElementType;
import elements.Entry;
import elements.Exit;
import entities.FuelTank;
import entities.PetrolStation;
import entities.Topology;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import lombok.Getter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.FuelTankRepository;
import repositories.PetrolStationRepository;
import repositories.TopologyRepository;
import views.Window;
import views.WindowRepository;
import views.WindowType;
import visualize.Grid;
import visualize.GridElement;

import java.io.IOException;
import java.util.List;

import static visualize.Grid.drawGrid;

public class ConstructorController extends Controller {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ScrollPane scrollPaneElements;
    @FXML
    @Getter
    private AnchorPane buttons;
    @FXML
    private Button closeButton;
    @FXML
    private Button back_button;
    @FXML
    private AnchorPane dragableArea;
    @FXML
    private ImageView entry;
    @FXML
    private ImageView exit;
    @FXML
    private ImageView cashBox;
    @FXML
    private ImageView petrolStation;
    @FXML
    private ImageView fuelTank;

    private int x0;
    private int y0;

    public void initialize() {
        ControllersRepository.addController(ControllerType.CONSTRUCTORCONTROLLER, this);
        disableElements(true);
        closeButton.setOnAction(event -> {
            WindowRepository.getWindow(WindowType.CONSTRUCTORWINDOW).close();
        });
        setBackButtonEvent();

        buttons.setLayoutX(10);
        buttons.setLayoutY(40);



        Window window = (Window) WindowRepository.getWindow(WindowType.BOUNDSWINDOW);
        int width;
        int height;
        if (window != null && window.isInitialized()) {
            BoundsController boundsController = (BoundsController) ControllersRepository.
                    getController(ControllerType.BOUNDSCONTROLLER);
            width = boundsController.getTopologyWidth().getValue();
            height = boundsController.getTopologyHeight().getValue();
            Grid.drawGrid(width, height, anchorPane);
        }
        else{
            width = 3;
            height = 7;
        }
        window = (Window) WindowRepository.getWindow(WindowType.DOWNLOADTOPOLOGYWINDOW);
        if (window != null && window.isInitialized()) {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
            TopologyRepository topologyRepository = context.getBean(TopologyRepository.class);
            PetrolStationRepository petrolStationRepository = context.getBean(PetrolStationRepository.class);
            FuelTankRepository fuelTankRepository = context.getBean(FuelTankRepository.class);

            Topology topology = topologyRepository.findByName(DownloadTopologyController.getTopologyName());
            width = topology.getWidth();
            height = topology.getHeight();
            Grid.drawGrid(width, height, anchorPane);
            Grid.getGrid()[topology.getCashBoxX()][topology.getCashBoxY()].
                    createElement(ElementType.CASHBOX, 0);
            Grid.getGrid()[topology.getEntranceX()][topology.getEntranceY()].
                    createElement(ElementType.ENTRY, 180);
            Grid.getGrid()[topology.getExitX()] [topology.getExitY()].
                    createElement(ElementType.EXIT, 180);

            List<PetrolStation> petrolStationList = petrolStationRepository.findAll();
            for (PetrolStation petrolStation : petrolStationList)
                Grid.getGrid()[petrolStation.getCoordinateX()][petrolStation.getCoordinateY()].
                        createElement(ElementType.PETROLSTATION, 0);

            List<FuelTank> fuelTankList = fuelTankRepository.findAll();
            for (FuelTank fuelTank : fuelTankList)
                Grid.getGrid()[fuelTank.getCoordinateX()][fuelTank.getCoordinateY()].createElement(ElementType.FUELTANK, 0);

            if (Entry.getStatus() && Exit.getStatus() && Entry.getX() > Exit.getX())
                Grid.setRoundRoad();
        }

        scrollPaneElements.setLayoutX(Grid.getGrid()[width - 1][0].getTranslateX()
                + GridElement.getElementWidth() + 10);
        scrollPaneElements.setLayoutY(buttons.getLayoutY());
    }


    public void disableElements(boolean status) {
        petrolStation.setDisable(status);
        cashBox.setDisable(status);
        fuelTank.setDisable(status);
    }

    private void setBackButtonEvent() {
        back_button.setOnAction(event -> {
            ControllersRepository.removeController(ControllerType.BOUNDSCONTROLLER);
            ControllersRepository.removeController(ControllerType.DOWNLOADTOPOLOGYCONTROLLER);
            Grid.removeGrid(anchorPane);
            WindowRepository.getWindow(WindowType.CONSTRUCTORWINDOW).hide();

            try {
                WindowRepository.getWindow(WindowType.MAINWINDOW).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
});
    }

    @FXML
    void entryOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }

    @FXML
    void entryOnDragDetectedEvent(MouseEvent event) {
        Dragboard dragboard = entry.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(entry.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }

    @FXML
    void exitOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }

    @FXML
    void exitOnDragDetectedEvent(MouseEvent event) {
        Dragboard dragboard = exit.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(exit.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }

    @FXML
    void cashBoxOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }

    @FXML
    void cashBoxOnDragDetectedEvent(MouseEvent event) {
        Dragboard dragboard = cashBox.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(cashBox.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }

    @FXML
    void petrolStationOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }

    @FXML
    void petrolStationOnDragDetectedEvent(MouseEvent event) {
        Dragboard dragboard = petrolStation.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(petrolStation.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }

    @FXML
    void fuelTankOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }

    @FXML
    void fuelTankOnDragDetectedEvent(MouseEvent event) {
        Dragboard dragboard = fuelTank.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(fuelTank.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }

    @FXML
    public void createModeller() throws IOException {
        WindowRepository.getWindow(WindowType.MODELLERWINDOW).show();
        WindowRepository.getWindow(WindowType.CONSTRUCTORWINDOW).hide();
    }

    public void saveTopology() throws IOException {
        WindowRepository.getWindow(WindowType.SAVETOPOLOGYWINDOW).show();
    }
}
