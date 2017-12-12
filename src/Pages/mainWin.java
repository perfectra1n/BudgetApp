// Interconnector.java
// Functions/Methods shared through all scenes
//

/*--------------------------------------------------------------------------*/
/* To preserve the original intended method of interfacing. Each created    */
/* scene class should contain this global variable:                         */
/*    private static Scene scene = null;                                    */
/* Each scene class should also contain the functions/methods:              */
/*    public static void open() { (null scene check create) changeScene }   */
/*    private static void create() { (design of scene + event handling) }   */
/* Of course, feel free to use whatever you prefer as long as it works,     */
/* such as xml style, cascading style sheets, etc.                          */
/* These methods were only placed to make it as simple as possible to       */
/* connect scenes between each other.                                       */
/*--------------------------------------------------------------------------*/

package Pages;

import Pages.GraphPage.graphPage;
import Pages.HomePage.homePage;
import Pages.LogPage.*;
import Pages.TablePage.*;
import database.dbHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainWin {
    private static Stage main;
    private static BorderPane border;

    // Main stage passed from Main class
    public static void passMain(Stage window) {
        if (main == null) {
            // Set global variable main, set window properties
            main = window; main.setTitle("Budget Application Program");
            // Set the icon of the program to the Sac State logo
            main.getIcons().add(new Image("resources/images/icons/icon.png"));
            // Connect to database
            dbHandler.connectToDB();
            // Close window event handler
            main.setOnCloseRequest(e -> {
                confirmClose();
                dbHandler.closeConnectionToDB();
            });

            createMainWindowLayout();
            homePage.open();
            window.show();
        }
    }

    // Changes to passed scene and retains size of window
    public static void changeCenter(Parent layout) { border.setCenter(layout); }

    // Confirm user wishes to close window
    private static void confirmClose() {}

    // Close the Main Window
    public static void closeWindow() {
        dbHandler.closeConnectionToDB(); // Prevent memory leaks
        main.close();
    }

    // Main Window layout and Menu events
    private static void createMainWindowLayout() {
        border = new BorderPane(); // Root layout of the stage

        /*          Set the ID for the pane, for usage within the CSS sheet         */
        border.setId("MainWindow");
        /*          Set the stylesheet mainWin.css to this scene        */
        String mainWinCss = mainWin.class.getResource("/resources/css/mainWin.css").toExternalForm();
        border.getStylesheets().clear();
        border.getStylesheets().add(mainWinCss);

        // Set scene to main stage
        main.setScene(new Scene(border));
        main.setHeight(700); main.setWidth(1000);
        /*
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem itemExit = new MenuItem("Exit");
        itemExit.setOnAction(e -> closeWindow());
        menuFile.getItems().addAll(itemExit);
        Menu menuEdit = new Menu("Edit");
        MenuItem itemEditData = new MenuItem("Data");
        menuEdit.getItems().addAll(itemEditData);
        Menu menuAbout = new Menu("About");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuAbout );
        */

        /*        Initialize the toolbar, which will be located underneath the menu bar          */
        HBox toolBar = new HBox(40);

        /*        Create the button, set the max size for each, and then set the action that will be performed upon click       */
        Button itemHome = new Button("Home");
        itemHome.setId("toolBarButton");
        itemHome.setOnAction(e -> homePage.open());

        Button itemImport = new Button("Table");
        itemImport.setId("toolBarButton");
        itemImport.setOnAction(e -> tablePage.open());


        Button itemLog = new Button("Data Log");
        itemLog.setId("toolBarButton");
        itemLog.setOnAction(e -> loggerPage.open());

        Button itemBarGraph = new Button("Graphs");
        itemBarGraph.setId("toolBarButton");
        itemBarGraph.setOnAction(e -> graphPage.open());


        //toolBar.setStyle("-fx-padding: 0 0 0 0");


        /*        Add all of the menus for the toolbar, to the toolbar                          */
        toolBar.getChildren().addAll(itemImport,itemBarGraph,itemLog,itemHome);


        /*          Created a new VBox named topContainer to hold the elements that
                    will be located at the top of the page, so that we can simply
                    set it at the top of the root at the end                         */
        VBox topContainer = new VBox();
        topContainer.setId("topContainer");
        topContainer.getChildren().add(toolBar);
        toolBar.setAlignment(Pos.CENTER);

        border.setTop(topContainer);

        /*        Set the styling for what the menus will look like             */
        /*        TODO make this look better                                    */
    }
}
