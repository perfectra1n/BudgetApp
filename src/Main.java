/* *************************************************************** */
/*                           TEAM ETHEREAL                         */
/*                BUDGET APPLICATION PROGRAM PROJECT               */
/*                                                                 */
/*            JONATHON FULLER              JERIC ORTILLO           */
/*            ZACHARY SCHUETT              ALLEN AQUINO            */
/* *************************************************************** */

// Main.java
// This is the main class for the application.
// Handles creation of Main Window.
//

import Pages.mainWin;
import database.DBHandle;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
//---------------------------- MAIN USER INTERFACE CREATION ----------------------------
    // Connect to database
    public static void main(String [] args) { DBHandle.connectToDB(); launch(args); }
    // Initiate Main Window (Stage) and pass window
    @Override
    public void start(Stage window) throws Exception { mainWin.passMain(window); }
//--------------------------------------------------------------------------------------
}