package controller;

import javafx.scene.control.Alert;
import lombok.Getter;
import views.IWindow;
import views.WindowFactory;
import views.WindowRepository;
import views.WindowType;

import java.io.IOException;

public abstract class Controller {
    private static WindowFactory windowFactory = new WindowFactory();
    @Getter
    private static WindowRepository windowRepository = new WindowRepository(windowFactory);

    protected static void openNewWindow(WindowType newWindowType)
            throws IOException {
        IWindow newWindow = windowRepository.getWindow(newWindowType);
        newWindow.show();
    }
    protected static void hideWindow(WindowType windowType) {
        windowRepository.getWindow(windowType).hide();
    }
}
