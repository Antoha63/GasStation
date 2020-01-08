package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import visualize.Grid;
import visualize.GridElement;

import java.io.IOException;

public class ImitationController {
    @FXML
    private AnchorPane dragableArea;
    @FXML
    private AnchorPane threadButtons;
    @FXML
    private AnchorPane statistics;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private AnchorPane backButtons;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextArea log_list;
    @FXML
    private AnchorPane log_list_anchorPane;
    @FXML
    private Label cash_value_label;
    @FXML
    private Label automobileCountLabel;
    @FXML
    private Label fuelValueLabel;
    @FXML
    private Button closeButton;

    public void initialize() {
        positionElements();
        setOnActionCloseWindow();
        drawGrid();
        setOnActionPlay();
    }

    private void positionElements() {
        int spacing = 10;
        backButtons.setLayoutX(spacing);
        backButtons.setLayoutY(threadButtons.getLayoutY() + threadButtons.getPrefHeight() + spacing);
        log_list_anchorPane.setLayoutX(Grid.getGrid()[Grid.getWidth() - 1][0].getTranslateX() +
                GridElement.getElementWidth() + spacing);
        statistics.setLayoutX(log_list_anchorPane.getLayoutX() + log_list.getPrefWidth() / 2 - statistics.getPrefWidth() / 2);
/*
        System.out.println("statistics.getLayoutX() " + statistics.getLayoutX());
        System.out.println("statistics.getLayoutY()" + statistics.getLayoutY());
        System.out.println("log_list_anchorPane.getLayoutY()" + log_list_anchorPane.getLayoutY());
        System.out.println("log_list.getPrefHeight()" + log_list.getPrefHeight());*/
        Stage stage = ModellerController.getPrimaryStage();
        stage.setWidth(log_list_anchorPane.getLayoutX() + log_list_anchorPane.getPrefWidth() + 10);
        stage.setHeight(Grid.getGrid()[0][Grid.getHeight()].getTranslateY() + GridElement.getElementHeight() + 10);

        log_list.setPrefHeight(stage.getHeight() - log_list_anchorPane.getLayoutY() - log_list.getLayoutY() -
                statistics.getPrefHeight() - 2 * spacing);
        statistics.setLayoutY(log_list_anchorPane.getLayoutY() + log_list.getPrefHeight() + 3 * spacing);
        dragableArea.setPrefWidth(stage.getWidth() - 2);
    }

    private void setOnActionCloseWindow() {
        closeButton.setOnAction(event -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });
    }

    private void drawGrid() {
        for (int i = 0; i < Grid.getWidth(); i++) {
            for (int j = 0; j < Grid.getHeight() + 1; j++) {
                anchorPane.getChildren().add(Grid.getGrid()[i][j]);
            }
        }
        for (Line line : Grid.getLineList()) {
            anchorPane.getChildren().add(line);
        }
    }

    private void setOnActionPlay() {
        playButton.setOnAction(event -> {
            MoveController moveController = new MoveController();
            try {
                moveController.go(anchorPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
