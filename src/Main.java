//**********************************************************************
//                          TEAM ETHEREAL
//                BUDGET APPLICATION PROGRAM PROJECT
//
//            JONATHON FULLER              JERIC ORTILLO
//            ZACHARY SCHUETT              ALLEN AQUINO
//**********************************************************************

// Main.java
// This is the main class for the application.
// Handles creation of Main Window.
//

import database.DBHandle;
import Stages.Interconnector;
import Stages.MainStage.testScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
//---------------------------- MAIN USER INTERFACE CREATION ----------------------------
    // Connect to database
    public static void main(String [] args) { DBHandle.connectToDB(); launch(args); }
    // Initiate Main Window (Stage) and pass window
    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Budget Application Program");
        window.setOnCloseRequest(e -> DBHandle.closeConnectionToDB());
        window.setScene(testScene.open()); //currently initiates with test scene. Must be changed later.
        window.show(); Interconnector.receiveMain(window); }
//--------------------------------------------------------------------------------------
}