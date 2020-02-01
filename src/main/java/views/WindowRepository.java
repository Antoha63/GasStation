package views;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WindowRepository {
    private static Map<WindowType, IWindow> windows;
    private WindowFactory windowFactory;

    static {
        windows = new HashMap<>();
    }

    public WindowRepository(WindowFactory windowFactory) {
        this.windowFactory = windowFactory;
    }

    public boolean isExists(WindowType windowType) {
        return windows.containsKey(windowType);
    }

    public void addWindow(WindowType windowType) throws IOException {
        IWindow window = windowFactory.createWindow(windowType);
        windows.put(windowType, window);
    }

    public static IWindow getWindow(WindowType windowType) {
        return windows.get(windowType);
    }

    public void deleteWindow(WindowType windowType) {
        windows.remove(windowType);
    }
}
