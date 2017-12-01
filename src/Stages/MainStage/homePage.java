
package Stages.MainStage;

import database.DBHandle;
import Stages.mainWin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.animation.Animation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class homePage {
private static TilePane homeLayout = null;

    public static void open(){
        // Layout not created until first call
        if (homeLayout == null) {
            homeLayout = new TilePane(Orientation.VERTICAL);
            homeLayout.setTileAlignment(Pos.CENTER);
            homeLayout.setPrefRows(2);
            homeLayout.setPrefColumns(3);
            homeLayout.setPrefTileHeight(300);
            homeLayout.setPrefTileWidth(300);
            homeLayout.setHgap(25);
            homeLayout.setVgap(25);
    //-----------------Adding place holder buttons-----------------//
            Button but1 = new Button ("A");
                but1.setPrefSize(175,175);
            Button but2 = new Button ("B");
                but2.setPrefSize(175,175);
            Button but3 = new Button ("C");
                but3.setPrefSize(175,175);
            Button but4 = new Button ("D");
                but4.setPrefSize(175,175);
            Button but5 = new Button ("E");
                but5.setPrefSize(175,175);
            Button but6 = new Button ("F");
                but6.setPrefSize(175,175);
            homeLayout.getChildren().addAll(but1,but2,but3,but4,but5,but6);
        }
        mainWin.changeCenter(homeLayout);



    }


}
