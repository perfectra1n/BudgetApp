// Interconnector.java
// Functions/Methods shared through all scenes
//

/*--------------------------------------------------------------------------*/
/* To preserve the original intended method of interfacing. Each created    */
/* scene class should contain these global variables:                       */
/*    private static ? ? = null;                                            */
/*    public static Scene scene = null;                                     */
/* Question mark designates layout chosen by the class designer/programmer. */
/* Each scene class should also contain the functions/methods:              */
/*    public static Scene open() { (null scene check create) return scene } */
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

public class Interconnector {
    private static Stage MainWindow;

    public static void receiveMain(Stage MainStage) { MainWindow = MainStage; } // Main stage passed from Main class

    public static void changeScene(Scene scene) { // called by Scene classes
        MainWindow.setScene(scene);

    }

    public static void closeWindow() {
        DBHandle.closeConnectionToDB(); // Prevent memory leaks
        MainWindow.close();
    }
}
