package controller;

import elements.ElementType;
import elements.Entry;
import elements.Exit;
import entities.FuelTank;
import entities.PetrolStation;
import entities.Topology;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import repositories.FuelTankRepository;
import repositories.PetrolStationRepository;
import repositories.TopologyRepository;
import visualize.Grid;
import visualize.GridElement;

import java.io.Console;
import java.io.IOException;
import java.util.List;

public class ConstructorController {
    private double xOffset;
    private double yOffset;
    private static int topologyWidth;
    private static int topologyHeight;


    public void setBounds(int width, int height) {
        topologyWidth = width;
        topologyHeight = height;
    }

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ScrollPane scrollPaneElements;
    @FXML
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

    public void initialize() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });

        buttons.setLayoutX(10);
        buttons.setLayoutY(40);
        int x0 = (int) (buttons.getLayoutX() * 2 + buttons.getPrefWidth());
        int y0 = (int) buttons.getLayoutY();

        Grid.initGrid(x0, y0, topologyWidth, topologyHeight);
        for (int i = 0; i < topologyWidth; i++) {
            for (int j = 0; j < topologyHeight + 1; j++) {
                anchorPane.getChildren().add(Grid.getGrid()[i][j]);
            }
        }
        for (Line line : Grid.getLineList()) {
            anchorPane.getChildren().add(line);
        }
        scrollPaneElements.setLayoutX(Grid.getGrid()[topologyWidth - 1][0].getTranslateX() + GridElement.getElementWidth() + 10);
        scrollPaneElements.setLayoutY(buttons.getLayoutY());

        if (BoundsController.getPrimaryStage() != null) {
            setAdaptiveDesign(BoundsController.getPrimaryStage());
            setBackButtonEvent("/views/topologySize.fxml");
        } else if (DownloadTopologyController.getPrimaryStage() != null) {
            setAdaptiveDesign(DownloadTopologyController.getPrimaryStage());
            setBackButtonEvent("/views/downloadTopology.fxml");

            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-data-context.xml");
            TopologyRepository topologyRepository = context.getBean(TopologyRepository.class);
            PetrolStationRepository petrolStationRepository = context.getBean(PetrolStationRepository.class);
            FuelTankRepository fuelTankRepository = context.getBean(FuelTankRepository.class);
            Topology topology = topologyRepository.findByName(DownloadTopologyController.getTopologyName());
            Grid.getGrid()[topology.getCashBoxX()][topology.getCashBoxY()].createElement(ElementType.CASHBOX, 0);
            Grid.getGrid()[topology.getEntranceX()][topology.getEntranceY()].createElement(ElementType.ENTRY, 180);
            Grid.getGrid()[topology.getExitX()][topology.getExitY()].createElement(ElementType.EXIT, 180);

            List<PetrolStation> petrolStationList = petrolStationRepository.findAll();
            for (PetrolStation petrolStation : petrolStationList)
                Grid.getGrid()[petrolStation.getCoordinateX()][petrolStation.getCoordinateY()].createElement(ElementType.PETROLSTATION, 0);

            List<FuelTank> fuelTankList = fuelTankRepository.findAll();
            for (FuelTank fuelTank : fuelTankList)
                Grid.getGrid()[fuelTank.getCoordinateX()][fuelTank.getCoordinateY()].createElement(ElementType.FUELTANK, 0);

            if (Entry.getStatus() && Exit.getStatus() && Entry.getX() > Exit.getX())
                Grid.setRoundRoad();
        }
    }

    private void setBackButtonEvent(String address) {
        back_button.setOnAction(event -> {
            Stage primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource(address));
                root.setOnMousePressed(mouseEvent -> {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                });
                root.setOnMouseDragged(mouseEvent -> {
                    primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                    primaryStage.setY(mouseEvent.getScreenY() - yOffset);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            primaryStage.setTitle("");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void setAdaptiveDesign(Stage stage) {
        stage.setWidth(scrollPaneElements.getLayoutX() + scrollPaneElements.getPrefWidth() + 10);
        stage.setHeight(Grid.getGrid()[0][topologyHeight].getTranslateY() + GridElement.getElementHeight() + 10);
        scrollPaneElements.setPrefHeight(stage.getHeight() - scrollPaneElements.getLayoutY() - 10);
        dragableArea.setPrefWidth(stage.getWidth() - 2);
    }

    @FXML
    void startModelling(ActionEvent event) throws IOException {
        MoveController moveController = new MoveController();
        moveController.go(anchorPane);
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
    void entryOnMouseClicked(MouseEvent event) {
        entry.setRotate(entry.getRotate() + 90);
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
    void exitOnMouseClicked(MouseEvent event) {
        exit.setRotate(exit.getRotate() + 90);
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
    public void createModeller() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/modeller.fxml"));
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
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

    public void saveTopology() throws IOException {
        Stage primaryStage = new Stage();
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource("/views/saveTopology.fxml"));
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
