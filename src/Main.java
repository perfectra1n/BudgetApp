//**********************************************************************
//                          TEAM ETHEREAL
//                BUDGET APPLICATION PROGRAM PROJECT
//
//            JONATHON FULLER              JERIC ORTILLO
//            ZACHARY SCHUETT              ALLEN AQUINO
//**********************************************************************

// Main.java
// This is the main TestModule class
// Handles creation of Main Window and connects stages and scenes
//

import Stages.MainStage.*;
//import Stages.LogStage.*;
import database.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String [] args) {
        DBHandle.connectToDB();
        launch(args);
    }

    @Override
    public void start(Stage MainWindow) throws Exception {
        Scene homepage = homeScene.create(MainWindow);
        MainWindow.setTitle("Budget Application Program");
        MainWindow.setScene(homepage);
        MainWindow.show();
        // Prevent memory leaks
        MainWindow.setOnCloseRequest(e -> DBHandle.closeConnectionToDB());
    }
}