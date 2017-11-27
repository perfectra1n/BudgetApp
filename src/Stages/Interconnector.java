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
import javafx.scene.Scene;
import javafx.stage.Stage;
import Stages.MainStage.*;

public class Interconnector {
    private static Stage MainWindow;

    // Main stage passed from Main class
    public static void receiveMain(Stage MainStage) {
        MainWindow = MainStage;
        testScene.open();
    }

    // Changes to passed scene and retains size of window
    public static void changeScene(Scene scene) {
        // before setting new scene, retrieve window size
        double width = MainWindow.getWidth(),
               height = MainWindow.getHeight();
        // set new scene
        MainWindow.setScene(scene);
        // set width and height to previous values
        MainWindow.setWidth(width);
        MainWindow.setHeight(height);
    }

    // Close the Main Window
    public static void closeWindow() {
        DBHandle.closeConnectionToDB(); // Prevent memory leaks
        MainWindow.close();
    }
}
