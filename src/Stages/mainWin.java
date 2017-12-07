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
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
        // Set global variable main, set window properties
        main = window;
        main.setTitle("Budget Application Program");

        /*          Set the icon of the program to the Sac State logo               */
        main.getIcons().add(new Image("images/icon.png"));
        main.setOnCloseRequest(e -> DBHandle.closeConnectionToDB());
                /*          Set the stylesheet mainWin.css to this scene        */
        String mainWinCss = mainWin.class.getResource("/css/mainWin.css").toExternalForm();
        border.getStylesheets().clear();
        border.getStylesheets().add(mainWinCss);
        createMainWindowLayout();
        homePage.open();
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

        /*          Set the ID for the pane, for usage within the CSS sheet         */
        border.setId("border");

        main.setScene(new Scene(border));



        main.setHeight(700); main.setWidth(1000);


                    /*                      Declare new menu bar                     */
        MenuBar menuBar = new MenuBar();

                    /*----------                      The File button on the menu bar                ----------*/
        Menu menuFile = new Menu("File");

                    /*                      The buttons on the drop down menu of file and the action they perform                    */
        MenuItem itemExit = new MenuItem("Exit");
        itemExit.setOnAction(e -> closeWindow());

                    /*                      Add all of the elements to the goto sub menu                 */
        menuFile.getItems().addAll(itemExit);


                    /*----------                      The Edit button on the menu bar               ----------*/
        Menu menuEdit = new Menu("Edit");

                    /*                      The buttons on the drop down menu of edit                    */
        MenuItem itemEditData = new MenuItem("Data");

                    /*                      Add all of the elements to the goto sub menu                 */
        menuEdit.getItems().addAll(itemEditData);


                    /*----------                      The About button on the menu bar            ----------*/
        Menu menuAbout = new Menu("About");

                    /*----------            Add all the menus (file, edit, about) to the menu bar    ---------*/
        menuBar.getMenus().addAll(menuFile, menuEdit, menuAbout );


        /*        Initialize the toolbar, which will be located underneath the menu bar          */
        HBox toolBar = new HBox(40);

        /*        Create the button, set the max size for each, and then set the action that will be performed upon click       */
        Button itemHome = new Button("Home");
        itemHome.setMaxSize(70,40);
        itemHome.setOnAction(e -> homePage.open());

        Button itemImport = new Button("Import");
        itemImport.setMaxSize(70,40);
        itemImport.setOnAction(e -> importPage.open());


        Button itemLog = new Button("Data Log");
        //itemLog.setMaxSize(70,40);
        itemLog.setId("itemLog");
        itemLog.setOnAction(e -> loggerPage.open());

        Button itemBarGraph = new Button("Graphs");
        itemBarGraph.setMaxSize(70,40);
        itemBarGraph.setOnAction(e -> graphPage.open());


        //toolBar.setStyle("-fx-padding: 0 0 0 0");


        /*        Add all of the menus for the toolbar, to the toolbar                          */
        toolBar.getChildren().addAll(itemImport,itemBarGraph,itemLog,itemHome);


        /*          Created a new VBox named topContainer to hold the elements that
                    will be located at the top of the page, so that we can simply
                    set it at the top of the root at the end                         */
        VBox topContainer = new VBox();
        topContainer.setId("topContainer");
        topContainer.getChildren().add(menuBar);
        topContainer.getChildren().add(toolBar);

        border.setTop(topContainer);

        /*        Set the styling for what the menus will look like             */
        /*        TODO make this look better                                    */












    }
}
