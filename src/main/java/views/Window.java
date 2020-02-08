package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

import static views.WindowState.*;

@Setter
public class Window implements IWindow {
    private String resource;
    private String title;
    private int width;
    private int height;
    private Stage stage = new Stage();
    private boolean isInitialized = false;
    @Getter
    private WindowState state = CLOSED;

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
        if(!state.equals(SHOWED)) {
            if (!isInitialized) {
                init();
                isInitialized = true;
            }
            stage.show();
            state = SHOWED;
        }
    }

    @Override
    public void hide() {
        if(!state.equals(HIDED)) {
            stage.hide();
            state = HIDED;
        }
    }

    @Override
    public void close() {
        if(!state.equals(CLOSED)) {
            stage.hide();
            isInitialized = false;
            state = CLOSED;
        }
    }

    private void init() throws IOException {
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
