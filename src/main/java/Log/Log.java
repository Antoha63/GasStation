package Log;

//import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import controller.ControllerType;
import controller.ControllersRepository;
import controller.ImitationController;

public class LogMessage extends Log{
    private static ImitationController imitationController = (ImitationController)
            ControllersRepository.getController(ControllerType.IMITATIONCONTROLLER);
    @Override
    public void sendMessage(String logMessage) {
        imitationController.addMessageLog(logMessage);
    }
}
