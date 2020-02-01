package views;

import javafx.stage.Stage;

public class WindowFactory {
    public IWindow createWindow(WindowType windowType) {
        String resource = null;
        String title = null;
        int width = 0;
        int height = 0;
        Stage stage = new Stage();
        switch (windowType){
            case MAINWINDOW:
                resource = "/views/main.fxml";
                title = "Главная";
                width = 400;
                height = 300;
                break;
            case BOUNDSWINDOW:
                resource = "/views/topologySize.fxml";
                title = "Размеры топологии";
                width = 400;
                height = 300;
                break;
            case SAVETOPOLOGYWINDOW:
                resource = "/views/saveTopology.fxml";
                title = "Сохранить топологию";
                width = 800;
                height = 600;
                break;
            case DOWNLOADTOPOLOGYWINDOW:
                resource = "/views/downloadTopology.fxml";
                title = "Загрузить топологию";
                width = 400;
                height = 300;
                break;
            case DEVINFOWINDOW:
                resource = "/views/developerInfo.fxml";
                title = "Информация о разработчиках";
                width = 400;
                height = 300;
                break;
            case SYSINFOWINDOW:
                resource = "/views/systemInfo.fxml";
                title = "Информация о системе";
                width = 400;
                height = 300;
                break;
            case CONSTRUCTORWINDOW:
                resource = "/views/constructor.fxml";
                title = "Конструктор";
                width = 800;
                height = 600;
                break;
            case MODELLERWINDOW:
                resource = "/views/modeller.fxml";
                title = "Моделлер";
                width = 800;
                height = 600;
                break;
            case DBWORKWINDOW:
                resource = "/views/dbWorkViews/dbWork.fxml";
                title = "Работа с БД";
                width = 800;
                height = 600;
                break;
            case ADDFUELWINDOW:
                resource = "/views/dbWorkViews/fuelParametersAdd.fxml";
                title = "Добавление типа топлива";
                width = 400;
                height = 300;
                break;
            case ADDCARWINDOW:
                resource = "/views/dbWorkViews/carParametersAdd.fxml";
                title = "Добавление транспортного средства";
                width = 400;
                height = 300;
                break;
            case IMITATIONWINDOW:
                resource = "/views/imitation.fxml";
                title = "Имитация";
                width = 800;
                height = 600;
                break;
            default:{
                new Exception("Unknown window type for application");
            }
        }
        return new Window(resource, title, width, height, stage);
    }
}
