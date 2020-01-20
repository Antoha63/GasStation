package Log;

import controller.ImitationController;

public class LogStatistic {
    private static ImitationController imitationController;

    public LogStatistic(int profit, int countCars, int countLitres) {
        imitationController.statisticRefresh(profit, countCars, countLitres);
    }

    public static void setImitationController(ImitationController ic) {
        imitationController = ic;
    }
}
