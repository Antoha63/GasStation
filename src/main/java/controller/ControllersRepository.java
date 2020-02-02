package controller;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
public class ControllersRepository {
    private static Map<ControllerType, Controller> controllers = new HashMap<>();;

    public static void addController(ControllerType controllerType, Controller controller){
        controllers.put(controllerType, controller);
    }

    public static Controller getController(ControllerType controllerType){
        return controllers.get(controllerType);
    }

    public static void removeController(ControllerType controllerType){
        if(controllers.containsKey(controllerType))
            controllers.remove(controllerType);
    }
}
