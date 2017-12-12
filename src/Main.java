/* *************************************************************** */
/*                           TEAM ETHEREAL                         */
/*                BUDGET APPLICATION PROGRAM PROJECT               */
/*                                                                 */
/*            JONATHON FULLER              JERIC ORTILLO           */
/*            ZACHARY SCHUETT              ALLEN AQUINO            */
/* *************************************************************** */

// Main.java
// This is the main class for the application
// Handles creation of Main GUI
//

import javafx.application.Application;
import javafx.stage.Stage;
import static Pages.mainWin.passMain;

public class Main extends Application {
//---------------------------- MAIN USER INTERFACE CREATION ----------------------------
    // Connect to database
    public static void main(String [] args) { launch(args); }
    // Initiate Main Window (Stage) and pass window
    @Override
    public void start(Stage window) throws Exception { passMain(window); }
//--------------------------------------------------------------------------------------
}