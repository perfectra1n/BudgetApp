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

import database.DBHandle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Stages.MainStage.*;

public class PageConnector {
    private static Stage main;

    // Main stage passed from Main class
    public static void passMain(Stage window) {
        main = window;
        main.setTitle("Budget Application Program");
        main.setOnCloseRequest(e -> DBHandle.closeConnectionToDB());
        testPage.open();
        window.show();
    }

    // Changes to passed scene and retains size of window
    public static void changeRoot(Parent root) {
        // Only null on startup
        if (main.getScene() == null) { main.setScene(new Scene(root)); }
        // For every other call, set new root of scene
        else { main.getScene().setRoot(root); }
    }

    // Close the Main Window
    public static void closeWindow() {
        DBHandle.closeConnectionToDB(); // Prevent memory leaks
        main.close();
    }
}
