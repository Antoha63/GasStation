package Log;

//import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import controller.ImitationController;

public class LogMessage {
    private static ImitationController imitationController;

    public LogMessage(String logMessage) {
        imitationController.addMessageLog(logMessage);
    }

    public static void setImitationController(ImitationController ic) {
        imitationController = ic;
    }
}
