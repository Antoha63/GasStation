package controller;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
public class ControllersRepository {
    private static Map<ControllerType, Controller> controllers = new HashMap<>();;

    public static void addController(ControllerType controllerType, Controller controller){
        if(controllers.containsKey(controllerType)){
            removeController(controllerType);
        }
        controllers.put(controllerType, controller);
    }

    public static Controller getController(ControllerType controllerType){
        return controllers.getOrDefault(controllerType, null);
    }

    public static void removeController(ControllerType controllerType){
            controllers.remove(controllerType);
    }
}
