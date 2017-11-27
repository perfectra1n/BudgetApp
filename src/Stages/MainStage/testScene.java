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
import javafx.stage.*;
import java.io.File;

public class testScene {
    // Scene (not created until first instance)
    private static Scene scene = null;

    // Opens this scene
    public static void open() {
        // If scene not created yet, create it
        if (scene == null) {create();}
        Interconnector.changeScene(scene);
    }

    // Scene layout and events
    private static void create() {
        GridPane grid = new GridPane(); // Chosen javafx layout for this scene
        scene = new Scene(grid);

        // grid.setAlignment(Pos.CENTER);
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        // Request File
        Label label1 = new Label("Select an Excel File");
        label1.setMinSize(120.0, 15.0);
        label1.setPrefHeight(15.0);
        grid.add(label1, 0, 0);

        // File Selection
        TextField FileSel = new TextField();
        FileSel.setMinSize(700.0, 25.0);
        FileSel.setPrefSize(700.0, 25.0);
        grid.add(FileSel, 0, 1);

        // Select File Button
        Button SelFileBut = new Button("...");
        SelFileBut.setMinSize(25.0, 25.0);
        SelFileBut.setPrefHeight(25.0);
        grid.add(SelFileBut, 1, 1);

        // Upload Selected File Button to Database
        Button UploadFileBut = new Button("Upload");
        UploadFileBut.setMinSize(60.0, 25.0);
        UploadFileBut.setPrefHeight(25.0);
        grid.add(UploadFileBut, 2, 1);

        // Close Window
        Button ExitBut = new Button("Close Window");
        ExitBut.setPrefSize(100.0, 25.0);
        ExitBut.setPrefHeight(25.0);
        grid.add(ExitBut, 3, 1);

        //---------------------------------- TEST BUTTONS ----------------------------------------
        // Graph Scene
        Button But1 = new Button("Graph Scene");
        But1.setMinSize(120.0, 25.0);
        But1.setPrefHeight(25.0);
        grid.add(But1, 0, 2);
        But1.setOnAction(e -> GraphScene.open());

        // Log Scene
        Button But2 = new Button("Log Scene");
        But1.setMinSize(120.0, 25.0);
        But1.setPrefHeight(25.0);
        grid.add(But2, 0, 3);
        But2.setOnAction(e -> Stages.LogStage.loggerScene.open());

        //
        Button But3 = new Button("NULL");
        But1.setMinSize(120.0, 25.0);
        But1.setPrefHeight(25.0);
        grid.add(But3, 0, 4);

        //
        Button But4 = new Button("NULL");
        But1.setMinSize(120.0, 25.0);
        But1.setPrefHeight(25.0);
        grid.add(But4, 0, 5);

        //
        Button But5 = new Button("NULL");
        But1.setMinSize(120.0, 25.0);
        But1.setPrefHeight(25.0);
        grid.add(But5, 0, 6);

        //
        Button But6 = new Button("NULL");
        But1.setMinSize(120.0, 25.0);
        But1.setPrefHeight(25.0);
        grid.add(But6, 0, 7);

        //
        Button But7 = new Button("NULL");
        But1.setMinSize(120.0, 25.0);
        But1.setPrefHeight(25.0);
        grid.add(But7, 0, 8);
        //----------------------------------------------------------------------------------------


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

        // Exit Button
        ExitBut.setOnAction(e -> Interconnector.closeWindow());
        //----------------------------------------------------------------------------
    }
}
