package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

@Setter
public class Window implements IWindow {
    private String resource;
    private String title;
    private int width;
    private int height;
    private Stage stage;
    private boolean isInitialized = false;

    public boolean isInitialized() {
        return isInitialized;
    }

    private double xOffset;
    private double yOffset;

    public Window(String resource, String title, Stage stage) {
        this.resource = resource;
        this.title = title;
    }

    public Window(String resource, String title, int width, int height, Stage stage) {
        this.resource = resource;
        this.title = title;
        this.width = width;
        this.height = height;
        this.stage = stage;
    }

    @Override
    public void show() throws IOException {
        if (!isInitialized) {
            init();
            isInitialized = true;
        }
        stage.show();
    }

    @Override
    public void hide() {
        stage.hide();
    }

    @Override
    public void close() {
        hide();
        stage = null;
        isInitialized = false;
    }

    private void init() throws IOException {
        stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        stage.setScene(new Scene(root, width, height));
        stage.setTitle(title);
    }

    @Override
    protected void finalize() {
        close();
        stage = null;
    }
}
