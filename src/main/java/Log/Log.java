package Log;

//import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import controller.ControllerType;
import controller.ControllersRepository;
import controller.ImitationController;

public class Log {
    private static ImitationController imitationController = (ImitationController)
            ControllersRepository.getController(ControllerType.IMITATIONCONTROLLER);

    public static void sendMessage(String logMessage) {
        imitationController.addMessageLog(logMessage);
    }

    public static void sendMessage(int profit, int countCars, int countLitres) {
        imitationController.statisticRefresh(profit, countCars, countLitres);
    }
}
