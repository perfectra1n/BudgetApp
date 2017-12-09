
package Pages.HomePage;

import Pages.TablePage.*;
import Pages.GraphPage.*;
import Pages.LogPage.*;
import Pages.mainWin;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class homePage {
    private static TilePane homeLayout = null;

    public static void open() {
        // Layout not created until first call
        if (homeLayout == null) {
            homeLayout = new TilePane(Orientation.VERTICAL);
                            /*          Set the stylesheet homePage.css to this scene        */
            String homePageCss = homePage.class.getResource("/resources/css/homePage.css").toExternalForm();
            homeLayout.getStylesheets().clear();
            homeLayout.getStylesheets().add(homePageCss);
            mainWin.changeCenter(homeLayout);
            create();
        }
        else { mainWin.changeCenter(homeLayout); }
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

        Image IM = new Image("resources/images/icons/importIcon.png");
        but1.setGraphic(new ImageView(IM));
        but1.setOnAction(e -> tablePage.open());



        Button but2 = new Button ("");
        but2.setPrefSize(10,10);

        Image GU = new Image("resources/images/icons/graphIcon.png");
        but2.setGraphic(new ImageView(GU));
        but2.setOnAction(e -> graphPage.open());

        Button but3 = new Button ("");
        but3.setPrefSize(10,10);

        Image DL = new Image("resources/images/icons/logIcon.png");
        but3.setGraphic(new ImageView(DL));
        but3.setOnAction(e -> loggerPage.open());


        Button but4 = new Button ("");
        but4.setPrefSize(10,10);

        Image S = new Image("resources/images/icons/searchIcon.png");
        but4.setGraphic(new ImageView(S));


        Button but5 = new Button ("NULL");
        but5.setPrefSize(10,10);


        Button but6 = new Button ("NULL");
        but6.setPrefSize(10,10);

        homeLayout.getChildren().addAll(but1,but2,but3,but4,but5,but6);
    }

}