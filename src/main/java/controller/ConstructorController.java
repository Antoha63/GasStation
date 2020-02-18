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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
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
            List<PetrolStation> petrolStationList = petrolStationRepository.findAllByTopology_Id(topology.getId());
            if (petrolStationList.size() > 3) disablePetrolStation(true);
            else disablePetrolStation(false);

            for (PetrolStation petrolStation : petrolStationList) {
                Grid.getGrid()[petrolStation.getCoordinateX()][petrolStation.getCoordinateY()].
                        createElement(ElementType.PETROLSTATION, 0);
                Grid.setPetrolRoad(
                        petrolStation.getCoordinateY());
            }


            Grid.setFuelTanksEvents();
            List<FuelTank> fuelTankList = fuelTankRepository.findAllByTopology_Id(topology.getId());
            if (fuelTankList.size() > 4) disableFuelTank(true);
            else disableFuelTank(false);

            for (FuelTank fuelTank : fuelTankList)
                Grid.getGrid()[fuelTank.getCoordinateX()][fuelTank.getCoordinateY()].createElement(ElementType.FUELTANK, 0);

            Grid.drawGrid(width, height, anchorPane);
        }


        scrollPaneElements.setLayoutX(Grid.getGrid()[width - 1][0].getTranslateX()
                + GridElement.getElementWidth() + 10);
        scrollPaneElements.setLayoutY(buttons.getLayoutY());
    }

    public void disableEntry(boolean status) {
        entry.setDisable(status);
        if (status) entry.setOpacity(0.5);
        else entry.setOpacity(1);
    }

    public void disableExit(boolean status) {
        exit.setDisable(status);
        if (status) exit.setOpacity(0.5);
        else exit.setOpacity(1);
    }

    public void disablePetrolStation(boolean status) {
        petrolStation.setDisable(status);
        if (status) petrolStation.setOpacity(0.5);
        else petrolStation.setOpacity(1);
    }

    public void disableCashBox(boolean status) {
        cashBox.setDisable(status);
        if (status) cashBox.setOpacity(0.5);
        else cashBox.setOpacity(1);
    }

    public void disableFuelTank(boolean status) {
        fuelTank.setDisable(status);
        if (status) fuelTank.setOpacity(0.5);
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

    private Rectangle[][] hover;
    @FXML
    void entryOnDragDetectedEvent(MouseEvent event) {
        hover = new Rectangle[Grid.getWidth() - 3][1];
        for (int k = 3; k < Grid.getWidth() - 3; k++) {
            hover[k][0] = new Rectangle(GridElement.getElementWidth(), GridElement.getElementHeight(), new Color(0.29,0.85,0.25,0.5));
            Grid.getGrid()[k][Grid.getHeight()].getChildren().add(hover[k][0]);
        }
        Dragboard dragboard = entry.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(entry.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }

    @FXML
    void entryOnDragDoneEvent(DragEvent event){
        for (int k = 3; k < Grid.getWidth() - 3; k++) {
            Grid.getGrid()[k][Grid.getHeight()].getChildren().remove(hover[k][0]);
        }
    }

    @FXML
    void exitOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }

    @FXML
    void exitOnDragDetectedEvent(MouseEvent event) {
        hover = new Rectangle[Entry.getX() - 1][1];
        for (int k = 1; k < Entry.getX() - 1; k++) {
            hover[k][0] = new Rectangle(GridElement.getElementWidth(), GridElement.getElementHeight(), new Color(0.29,0.85,0.25,0.5));
            Grid.getGrid()[k][Grid.getHeight()].getChildren().add(hover[k][0]);
        }
        Dragboard dragboard = exit.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(exit.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }

    @FXML
    public void exitOnDragDoneEvent(DragEvent dragEvent) {
        for (int k = 1; k < Entry.getX() - 1; k++) {
            Grid.getGrid()[k][Grid.getHeight()].getChildren().remove(hover[k][0]);
        }
    }

    @FXML
    void cashBoxOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }

    @FXML
    void cashBoxOnDragDetectedEvent(MouseEvent event) {
        hover = new Rectangle[Grid.getHeight()][1];
        for (int k = 0; k < Grid.getHeight(); k++) {
            hover[k][0] = new Rectangle(GridElement.getElementWidth(), GridElement.getElementHeight(), new Color(0.29,0.85,0.25,0.5));
            Grid.getGrid()[Exit.getX() - 1][k].getChildren().add(hover[k][0]);
        }
        Dragboard dragboard = cashBox.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(cashBox.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }

    @FXML
    public void cashBoxOnDragDoneEvent(DragEvent dragEvent) {
        for (int k = 0; k < Grid.getHeight(); k++) {
            Grid.getGrid()[Exit.getX() - 1][k].getChildren().remove(hover[k][0]);
        }
    }

    @FXML
    void petrolStationOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }

    @FXML
    void petrolStationOnDragDetectedEvent(MouseEvent event) {
        hover = new Rectangle[Entry.getX() - Exit.getX() - 1 ][Grid.getHeight() - 1];
        for (int i = Exit.getX() + 1; i < Entry.getX(); i++) {
            for (int j = 1; j < Grid.getHeight(); j++){
                if(!Grid.getGrid()[i][j].getIsOccupied()){
                    hover[i - (Exit.getX() + 1)][j - 1] = new Rectangle(GridElement.getElementWidth(), GridElement.getElementHeight(), new Color(0.29,0.85,0.25,0.5));
                    Grid.getGrid()[i][j].getChildren().add(hover[i - (Exit.getX() + 1)][j - 1]);
                }
            }
        }
        Dragboard dragboard = petrolStation.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(petrolStation.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }

    @FXML
    public void petrolStationOnDragDoneEvent(DragEvent dragEvent) {
        for (int i = Exit.getX() + 1; i < Entry.getX(); i++) {
            for (int j = 1; j < Grid.getHeight(); j++){
                Grid.getGrid()[i][j].getChildren().remove(hover[i - (Exit.getX() + 1)][j - 1]);
            }
        }
    }

    @FXML
    void fuelTankOnDragOverEvent(DragEvent event) {
        event.acceptTransferModes(TransferMode.COPY);
    }

    @FXML
    void fuelTankOnDragDetectedEvent(MouseEvent event) {
        hover = new Rectangle[Grid.getHeight() - 1][1];
        for (int k = 1; k < Grid.getHeight(); k++) {
            if(!Grid.getGrid()[Grid.getWidth() - 2][k].getIsOccupied()) {
                hover[k - 1][0] = new Rectangle(GridElement.getElementWidth(), GridElement.getElementHeight(), new Color(0.29, 0.85, 0.25, 0.5));
                Grid.getGrid()[Grid.getWidth() - 2][k].getChildren().add(hover[k - 1][0]);
            }
        }
        Dragboard dragboard = fuelTank.startDragAndDrop(TransferMode.COPY);
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(fuelTank.getId());
        dragboard.setContent(clipboardContent);
        event.consume();
    }

    @FXML
    public void fuelTankOnDragDoneEvent(DragEvent dragEvent) {
        for (int k = 1; k < Grid.getHeight(); k++) {
            Grid.getGrid()[Grid.getWidth() - 2][k].getChildren().remove(hover[k - 1][0]);
        }
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
