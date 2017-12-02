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
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class mainWin {
    private static Stage main;
    private static BorderPane border;

    // Main stage passed from Main class
    public static void passMain(Stage window) {
        // Set global variable main, set window properties
        main = window; main.setTitle("Budget Application Program");
        //main.getIcons().add(new Image("images/icon.png"));
        main.setOnCloseRequest(e -> DBHandle.closeConnectionToDB());
        createMainWindowLayout();
        importPage.open();
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



                    /*                      Declare new menu bar                     */
        MenuBar menuBar = new MenuBar();

                    /*----------                      The File button on the menu bar                ----------*/
        Menu menuFile = new Menu("File");

                    /*                      The buttons on the drop down menu of file                    */
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



                    /*----------                      The Goto button on the menu bar               ----------*/
        Menu menuGoto = new Menu("Goto");

                    /*                      The buttons on the drop down menu of goto                    */
        MenuItem itemHome = new MenuItem("Home Page");
        itemHome.setOnAction(e -> homePage.open());

        MenuItem itemImport = new MenuItem("Import...");
        itemImport.setOnAction(e -> importPage.open());

        MenuItem itemLog = new MenuItem("Data Log");
        itemLog.setOnAction(e -> loggerPage.open());

        MenuItem itemBarGraph = new MenuItem("Graph view");
        itemBarGraph.setOnAction(e -> graphPage.open());



                    /*                      Add all of the elements to the goto sub menu                 */
        menuGoto.getItems().addAll(itemImport, itemBarGraph, itemLog, itemHome);



                    /*----------                      The About button on the menu bar            ----------*/
        Menu menuAbout = new Menu("About");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuGoto, menuAbout );

                    /*                      Add all of the elements to the  menu                         */
        border.setTop(menuBar);
        border.getTop().setStyle("-fx-background-color: lightgrey");












    }
}
