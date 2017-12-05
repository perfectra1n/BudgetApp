
package Stages.MainStage;

import Stages.LogStage.loggerPage;
import database.DBHandle;
import Stages.mainWin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.animation.Animation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class homePage {
private static TilePane homeLayout = null;

    public static void open() {
        // Layout not created until first call
        if (homeLayout == null) {
            homeLayout = new TilePane(Orientation.VERTICAL);
        }
        mainWin.changeCenter(homeLayout);
        create();
    }
    private static void create() {
        homeLayout.setTileAlignment(Pos.CENTER);
        homeLayout.setPrefRows(2);
        homeLayout.setPrefColumns(3);
        homeLayout.setPrefTileHeight(300);
        homeLayout.setPrefTileWidth(300);
        homeLayout.setHgap(30);
        homeLayout.setVgap(30);
        //-----------------Adding place holder buttons-----------------//
        Button but1 = new Button ("");
        but1.setPrefSize(10,10);

            Image IM = new Image("images/importIcon.png");
            but1.setGraphic(new ImageView(IM));
            but1.setOnAction(e -> importPage.open());



        Button but2 = new Button ("");
        but2.setPrefSize(10,10);

            Image GU = new Image("images/graphIcon.png");
            but2.setGraphic(new ImageView(GU));
            but2.setOnAction(e -> graphPage.open());

        Button but3 = new Button ("");
        but3.setPrefSize(10,10);

            Image DL = new Image("images/logIcon.png");
            but3.setGraphic(new ImageView(DL));
            but3.setOnAction(e -> loggerPage.open());


        Button but4 = new Button ("");
        but4.setPrefSize(10,10);

            Image S = new Image("images/searchIcon.png");
            but4.setGraphic(new ImageView(S));


        Button but5 = new Button ("NULL");
        but5.setPrefSize(10,10);


        Button but6 = new Button ("NULL");
        but6.setPrefSize(10,10);



        homeLayout.getChildren().addAll(but1,but2,but3,but4,but5,but6);
       }

    }

