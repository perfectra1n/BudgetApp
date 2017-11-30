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

package Stages;

import Stages.LogStage.*;
import Stages.MainStage.*;
import database.DBHandle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class mainWin {
    private static Stage main;
    private static BorderPane border;

    // Main stage passed from Main class
    public static void passMain(Stage window) {
        // Set global variable main, set window properties
        main = window; main.setTitle("Budget Application Program");
        main.setOnCloseRequest(e -> DBHandle.closeConnectionToDB());
        createMainWindowLayout();
        testPage.open();
        window.show();
    }

    // Changes to passed scene and retains size of window
    public static void changeCenter(Parent layout) {
        border.setCenter(layout);
    }

    // Close the Main Window
    public static void closeWindow() {
        DBHandle.closeConnectionToDB(); // Prevent memory leaks
        main.close();
    }

    // Main Window layout and Menu events
    private static void createMainWindowLayout() {
        border = new BorderPane();
        main.setScene(new Scene(border));
        main.setHeight(700); main.setWidth(1000);

        // Menu creation
        MenuBar menuBar = new MenuBar();                       // Menu Bar
        Menu menuFile = new Menu("File");                  // Sub Menu "File"
        Menu menuView = new Menu("View");                  // Sub Menu "View"
        Menu menuPages = new Menu("Pages");                // Sub Menu "Pages"
        Menu menuGraph = new Menu("Graph");                // Sub Menu "Graph"
        MenuItem itemImport = new MenuItem("Import...");   // Menu Item "Import..."
        MenuItem itemSecret = new MenuItem("About");
        MenuItem itemExit = new MenuItem("Exit");          // Menu Item "Exit"
        MenuItem itemLog = new MenuItem("Data Log");       // Menu Item "Data Log"
        MenuItem itemTest = new MenuItem("Test Page");     // Menu Item "Test Page"
        MenuItem itemBarGraph = new MenuItem("Bar Graph"); // Menu Item "Graph Page"

        // Add menu items to sub-menus
        menuFile.getItems().addAll(itemImport, itemSecret, itemExit);
        menuView.getItems().addAll(menuPages, itemLog);
        menuPages.getItems().addAll(itemTest);
        menuGraph.getItems().addAll(itemBarGraph);

        // Add sub menus to menu bar
        menuBar.getMenus().addAll(menuFile, menuView, menuGraph);

        // Add menu bar to top of window
        border.setTop(menuBar);

        //--------------------------------------EVENTS--------------------------------------
        //
        Popup shh = new Popup();
        Label text = new Label("Gotcha Bitch");
        shh.getContent().add(text);
        itemSecret.setOnAction(e -> {
            if (shh.isShowing()) {shh.hide();}
            else {shh.show(main);}
        });

        // Menu Item "Test Page"
        itemTest.setOnAction(e -> testPage.open());

        // Menu Item "Graph Page"
        itemBarGraph.setOnAction(e -> graphPage.open());

        // Menu Item "Data Log"
        itemLog.setOnAction(e -> loggerPage.open());

        // Menu Item "Exit"
        itemExit.setOnAction(e -> closeWindow());
        //----------------------------------------------------------------------------------
    }
}
