
package Pages.HomePage;

import Pages.TablePage.*;
import Pages.GraphPage.*;
import Pages.LogPage.*;
import Pages.mainWin;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class homePage {
    private static TilePane homeLayout = null;

    public static void open() {
        // Layout not created until first call
        if (homeLayout == null) {
            homeLayout = new TilePane();
            /*-------------- Layout settings --------------*/
            homeLayout.setId("HomePage");
            homeLayout.setPrefTileWidth(150);
            homeLayout.setPrefTileHeight(100);
            homeLayout.setHgap(40); homeLayout.setVgap(35);
            homeLayout.setAlignment(Pos.CENTER);
            homeLayout.setPrefColumns(3);
            /* ******************************************* */
                            /*          Set the stylesheet homePage.css to this scene        */
            homeLayout.getStylesheets().add(homePage.class.getResource(
                    "/resources/css/homePage.css").toExternalForm());
            // Display layout
            mainWin.changeCenter(homeLayout);
            create();
        }
        else { mainWin.changeCenter(homeLayout); }
    }
    private static void create() {
        /*------------------ Add buttons ------------------*/

        Button TablePageButton = new Button ("Data");
        TablePageButton.setId("TablePageButton");
        TablePageButton.prefHeightProperty().bind(homeLayout.prefTileHeightProperty());
        TablePageButton.prefWidthProperty().bind(homeLayout.prefTileWidthProperty());
        TablePageButton.setOnAction(e -> tablePage.open());

        VBox graphButtons = new VBox();
        graphButtons.setSpacing(5);
        graphButtons.setId("VboxOfGraphButtons");
        graphButtons.prefHeightProperty().bind(homeLayout.prefTileHeightProperty());
        graphButtons.prefWidthProperty().bind(homeLayout.prefTileWidthProperty());

        Button BarGraphButton = new Button ("Bar Graph");
        BarGraphButton.setId("BarGraphButton");
        BarGraphButton.prefHeightProperty().bind(graphButtons.prefHeightProperty());
        BarGraphButton.prefWidthProperty().bind(graphButtons.prefWidthProperty());
        BarGraphButton.setOnAction(e -> graphPage.open());

        Button PieGraphButton = new Button ("Pie Graph");
        PieGraphButton.setId("PieGraphButton");
        PieGraphButton.prefHeightProperty().bind(graphButtons.prefHeightProperty());
        PieGraphButton.prefWidthProperty().bind(graphButtons.prefWidthProperty());

        Button LineGraphButton = new Button ("Line Graph");
        LineGraphButton.setId("LineGraphButton");
        LineGraphButton.prefHeightProperty().bind(graphButtons.prefHeightProperty());
        LineGraphButton.prefWidthProperty().bind(graphButtons.prefWidthProperty());

        Button TableGraphButton = new Button ("Table Graph");
        TableGraphButton.setId("TableGraphButton");
        TableGraphButton.prefHeightProperty().bind(graphButtons.prefHeightProperty());
        TableGraphButton.prefWidthProperty().bind(graphButtons.prefWidthProperty());

        Button BudgetButton = new Button ("Budget Analyzing");
        BudgetButton.setId("BudgetButton");
        BudgetButton.prefHeightProperty().bind(homeLayout.prefTileHeightProperty());
        BudgetButton.prefWidthProperty().bind(homeLayout.prefTileWidthProperty());

        Button LogButton = new Button ("View Data Log");
        LogButton.setId("LogButton");
        LogButton.prefHeightProperty().bind(homeLayout.prefTileHeightProperty());
        LogButton.prefWidthProperty().bind(homeLayout.prefTileWidthProperty());
        LogButton.setOnAction(e -> loggerPage.open());
        
        graphButtons.getChildren().addAll(BarGraphButton,PieGraphButton, LineGraphButton, TableGraphButton);

        homeLayout.getChildren().addAll(
                TablePageButton, graphButtons, BudgetButton, LogButton);
    }

}