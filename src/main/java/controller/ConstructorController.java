package controller;

import elements.CashBox;
import elements.ElementType;
import elements.Entry;
import elements.Exit;
import entities.FuelTank;
import entities.PetrolStation;
import entities.Topology;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.FuelTankRepository;
import repositories.PetrolStationRepository;
import repositories.TopologyRepository;
import views.Window;
import views.WindowRepository;
import views.WindowState;
import views.WindowType;
import visualize.Grid;
import visualize.GridElement;

import java.io.IOException;
import java.util.List;

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
    @Getter
    @FXML
    private ImageView entry;
    @Getter
    @FXML
    private ImageView exit;
    @FXML
    private ImageView cashBox;
    @FXML
    private ImageView petrolStation;
    @FXML
    private ImageView fuelTank;

    public void initialize() {
        ControllersRepository.addController(ControllerType.CONSTRUCTORCONTROLLER, this);
        disableExit(true);
        disablePetrolStation(true);
        disableCashBox(true);
        disableFuelTank(true);
        closeButton.setOnAction(event -> {
            WindowRepository.getWindow(WindowType.CONSTRUCTORWINDOW).close();
        });
        setBackButtonEvent();

        Window window = (Window) WindowRepository.getWindow(WindowType.BOUNDSWINDOW);
        int width;
        int height;
        if (window != null && window.getState().equals(WindowState.HIDED)) {
            BoundsController boundsController = (BoundsController) ControllersRepository.
                    getController(ControllerType.BOUNDSCONTROLLER);
            width = boundsController.getTopologyWidth().getValue();
            height = boundsController.getTopologyHeight().getValue();
            Grid.drawGrid(width, height, anchorPane);
        } else {
            width = 3;
            height = 7;
        }
        window = (Window) WindowRepository.getWindow(WindowType.DOWNLOADTOPOLOGYWINDOW);
        if (window != null && window.getState() == WindowState.HIDED) {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
            TopologyRepository topologyRepository = context.getBean(TopologyRepository.class);
            PetrolStationRepository petrolStationRepository = context.getBean(PetrolStationRepository.class);
            FuelTankRepository fuelTankRepository = context.getBean(FuelTankRepository.class);

            Topology topology = topologyRepository.findByName(DownloadTopologyController.getTopologyName());
            width = topology.getWidth();
            height = topology.getHeight();


            Grid.initGrid(width, height);


            Grid.getGrid()[topology.getEntranceX()][topology.getEntranceY()].
                    createElement(ElementType.ENTRY, 180);
            Grid.getGrid()[topology.getExitX()][topology.getExitY()].
                    createElement(ElementType.EXIT, 180);
            disableEntry(true);
            disableExit(true);
            if (entry.isDisable() && exit.isDisable() && Entry.getX() > Exit.getX())
                Grid.setRoundRoad();




            Grid.setCashBoxEvents();
            disableCashBox(true);
            Grid.getGrid()[topology.getCashBoxX()][topology.getCashBoxY()].
                    createElement(ElementType.CASHBOX, 0);
            CashBox.setSetted(true); //TODO: касса не удаляется при закрузке из БД


            Grid.setPetrolStationsEvents();
            List<PetrolStation> petrolStationList = petrolStationRepository.findAll();
            if(petrolStationList.size() > 3) disablePetrolStation(true);
            else disablePetrolStation(false);
            for (PetrolStation petrolStation : petrolStationList) {   //TODO: сделать не костыльную выборку топологии
                if (petrolStation.getTopology().getId() == topology.getId()) {
                    Grid.getGrid()[petrolStation.getCoordinateX()][petrolStation.getCoordinateY()].
                            createElement(ElementType.PETROLSTATION, 0);
                    Grid.setPetrolRoad(
                            petrolStation.getCoordinateY());
                }
            }


            Grid.setFuelTanksEvents();
            List<FuelTank> fuelTankList = fuelTankRepository.findAll();
            if(fuelTankList.size() > 4) disableFuelTank(true);
            else disableFuelTank(false);
            for (FuelTank fuelTank : fuelTankList)
                if (fuelTank.getTopology().getId() == topology.getId())   //TODO: сделать не костыльную выборку топологии
                    Grid.getGrid()[fuelTank.getCoordinateX()][fuelTank.getCoordinateY()].createElement(ElementType.FUELTANK, 0);

            Grid.drawGrid(width, height, anchorPane);
        }


        scrollPaneElements.setLayoutX(Grid.getGrid()[width - 1][0].getTranslateX()
                + GridElement.getElementWidth() + 10);
        scrollPaneElements.setLayoutY(buttons.getLayoutY());
    }

    public void disableEntry(boolean status) {
        entry.setDisable(status);
        if(status) entry.setOpacity(0.5);
        else entry.setOpacity(1);
    }

    public void disableExit(boolean status) {
        exit.setDisable(status);
        if(status) exit.setOpacity(0.5);
        else exit.setOpacity(1);
    }

    public void disablePetrolStation(boolean status) {
        petrolStation.setDisable(status);
        if(status) petrolStation.setOpacity(0.5);
        else petrolStation.setOpacity(1);
    }

    public void disableCashBox(boolean status) {
        cashBox.setDisable(status);
        if(status) cashBox.setOpacity(0.5);
        else cashBox.setOpacity(1);
    }

    public void disableFuelTank(boolean status) {
        fuelTank.setDisable(status);
        if(status) fuelTank.setOpacity(0.5);
        else fuelTank.setOpacity(1);
    }

    private void setBackButtonEvent() {
        back_button.setOnAction(event -> {
            ControllersRepository.removeController(ControllerType.BOUNDSCONTROLLER);
            ControllersRepository.removeController(ControllerType.DOWNLOADTOPOLOGYCONTROLLER);
            Grid.removeGrid(anchorPane);
            WindowRepository.getWindow(WindowType.CONSTRUCTORWINDOW).hide();
            try {
                WindowRepository.getWindow(WindowType.BOUNDSWINDOW).close();
                WindowRepository.getWindow(WindowType.DOWNLOADTOPOLOGYWINDOW).close();
            } catch (NullPointerException ignored) {
            }

            try {
                WindowRepository.getWindow(WindowType.MAINWINDOW).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void drawGrid(int width, int height) {
        Grid.drawGrid(width, height, anchorPane);
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
        if (checkCorrect()) {
            WindowRepository.getWindow(WindowType.MODELLERWINDOW).show();
            WindowRepository.getWindow(WindowType.CONSTRUCTORWINDOW).hide();
        }
    }

    public void saveTopology() throws IOException {
        if (checkCorrect()) {
            WindowRepository.getWindow(WindowType.SAVETOPOLOGYWINDOW).show();
        }
    }

    @FXML
    public void checkTopology() {
        if (checkCorrect()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибок нет");
            alert.setHeaderText(null);
            alert.setContentText("Ошибок нет!");
            alert.showAndWait();
        }
    }

    @FXML
    private boolean checkCorrect() {
        boolean isCorrect;
        if (!entry.isDisable()) {
            isCorrect = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Въезд не установлен!");
            alert.showAndWait();
        } else if (!exit.isDisable()) {
            isCorrect = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Выезд не установлен!");
            alert.showAndWait();
        } else if (Grid.getListOfFuelTanks().size() < 1) {
            isCorrect = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Количество ТБ должно быть от 1 до 5!");
            alert.showAndWait();
        } else if (Grid.getListOfPetrolStations().size() < 1 |
                Grid.getListOfPetrolStations().size() > 4) {
            isCorrect = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Количество ТРК должно быть от 1 до 4!");
            alert.showAndWait();
        } else if (!CashBox.getSetted()) {
            isCorrect = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText(null);
            alert.setContentText("Отсутствует касса!");
            alert.showAndWait();
        } else {
            isCorrect = true;
        }
        return isCorrect;
    }
}
