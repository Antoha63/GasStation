package visualize;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Grid {
    private static int x0;
    private static int y0;
    private static int x;
    private static int y;
    private static int width;
    private static int height;

    private static GridElement gridElement;
    private static GridPane gridPane = new GridPane();

    public static void setGrid(int topologyX0, int topologyY0,
                               int topologyWidth, int topologyHeight,
                                AnchorPane root){
        x0 = topologyX0;
        y0 = topologyY0;
        width = topologyWidth;
        height = topologyHeight;

        gridPane.setGridLinesVisible(true);
        gridPane.setTranslateX(topologyX0);
        gridPane.setTranslateY(topologyY0);
        gridElement = new GridElement();
        gridPane.setPrefWidth(topologyWidth * gridElement.getWidth());
        gridPane.setPrefWidth(topologyHeight * gridElement.getHeight());
        for(int i = 0; i < topologyWidth; i++)
            gridPane.getColumnConstraints().add(new ColumnConstraints(40)); // column 0 is 40 wide
        for(int i = 0; i < topologyHeight + 1; i++)
            gridPane.getRowConstraints().add(new RowConstraints(40)); // column 0 is 40 wide
        gridPane.setOnMouseDragEntered(event -> {
            x = (int)event.getScreenX();
            y = (int)event.getScreenY();
        });
        gridPane.setOnDragOver(event -> {
            if(event.getDragboard().hasImage()){
                event.acceptTransferModes(TransferMode.COPY);
            }
        });
        gridPane.setOnDragDropped(event -> {
            Image image = event.getDragboard().getImage();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(40);
            imageView.setFitWidth(40);

            gridPane.add(imageView, x, y);
        });

        root.getChildren().add(gridPane);
    }

    public static GridPane getGridPane(){
        return gridPane;
    }
    public static int getX0(){
        return x0;
    }
    public static int getY0(){
        return y0;
    }
    public static int getWidth(){
        return width;
    }
    public static int getHeight(){
        return height;
    }
}
