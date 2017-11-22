// homeScene.java
// This is a test class
// May be reused for Home Screen
//

package Stages.MainStage;

import Stages.*;
import database.DBHandle;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import javax.management.timer.TimerMBean;
import java.io.File;
import java.time.LocalTime;

public class testScene {

    private static GridPane grid = null; // Chosen javafx layout for this scene
    private static Scene scene = null;   // Scene (not created until first instance)

    public static Scene open() { // Returns scene
        if (scene == null) {     // If scene not created yet
            grid = new GridPane();
            scene = new Scene(grid, 800, 600);
            create();
        }
        return scene;
    }

    private static void create() {

        // grid.setAlignment(Pos.CENTER);
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        // Request File
        Label label1 = new Label("Select an Excel File");
        label1.setFont(Font.font(14));
        grid.add(label1, 0, 0, 10, 1);

        // File Selection
        TextField FileSel = new TextField();
        FileSel.setFont(Font.font(12));
        grid.add(FileSel, 0, 1, 30, 1);

        // Select File Button
        Button SelFileBut = new Button("...");
        SelFileBut.setFont(Font.font(12));
        grid.add(SelFileBut, 31, 1);

        // Upload Selected File Button to Database
        Button UploadFileBut = new Button("Upload");
        UploadFileBut.setFont(Font.font(12));
        grid.add(UploadFileBut, 35, 1, 6, 1);

        // Close Window
        Button ExitBut = new Button("Close Window");
        grid.add(ExitBut, 43, 46);

        // TEMPORARY CHANGE SCENE BUTTON
        Button tempBut = new Button("Click Me to go to a new scene!");
        grid.add(tempBut, 5, 20);

        //---------------------------------- EVENTS ----------------------------------
        // Select File Button
        SelFileBut.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser(); // Create new File Chooser
            fileChooser.setTitle("Open Excel File");     // Title of File Chooser Window
            fileChooser.getExtensionFilters().addAll(    // Accepted file formats
                    new FileChooser.ExtensionFilter(
                            "Excel Files \"*.xlsx or\", \"*.xls\"",
                            "*.xlsx", "*.xls"));
            File chosenFile = fileChooser.showOpenDialog(null);
            if  (chosenFile != null) { FileSel.setText(chosenFile.getPath()); }
        });

        // Upload File Button
        UploadFileBut.setOnAction(e -> {
            String path = FileSel.getText();
            if (!path.isEmpty()) { DBHandle.loadExcelToDB(path); } // If file chosen
        });

        // Temp change scene button
        tempBut.setOnAction(e -> {
            Interconnector.changeScene(LoginScreen.open());
            //DBHandle.test(null);
        });

        // Exit Button
        ExitBut.setOnAction(e -> Interconnector.closeWindow());
        //----------------------------------------------------------------------------
    }
}
