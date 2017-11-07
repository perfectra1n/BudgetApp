// homeScene.java
// This is a test class
// May be reused for Home Screen
//

package Stages.MainStage;

import database.DBHandle;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.*;
import java.io.File;

public class homeScene {

    public static Scene create(Stage MainWindow) {

        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);

        // grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        // Request File
        Label label1 = new Label("Select an Excel File");
        label1.setFont(Font.font(14));
        grid.add(label1, 0, 0);

        // File Selection
        TextField FileSel = new TextField();
        FileSel.setFont(Font.font(12));
        grid.add(FileSel, 0, 1, 40, 1);

        // Select File Button
        Button SelFileBut = new Button("...");
        SelFileBut.setFont(Font.font(12));
        grid.add(SelFileBut, 40, 1);

        // Upload Selected File Button to Database
        Button UploadFileBut = new Button("Upload");
        UploadFileBut.setFont(Font.font(12));
        grid.add(UploadFileBut, 41, 1);

        // Successful upload?
        Text successfulUpload = new Text("Uploaded Successfully!");
        successfulUpload.setFill(Color.GREEN);
        successfulUpload.setVisible(false);
        grid.add(successfulUpload, 0, 2);

        // Failed upload?
        Text failedUpload = new Text("");
        failedUpload.setFill(Color.RED);
        failedUpload.setVisible(false);
        grid.add(failedUpload, 0, 2);

        // Close Window
        Button ExitBut = new Button("Close Window");
        grid.add(ExitBut, 43, 46);

        //---------------------------------- EVENTS ----------------------------------

        // Select File Button
        SelFileBut.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Open Excel File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter(
                            "Excel Files \"*.xlsx or\", \"*.xls\"",
                            "*.xlsx", "*.xls"));
            File chosenFile = fileChooser.showOpenDialog(null);
            if  (chosenFile != null) {
                FileSel.setText(chosenFile.getPath());
            }
        });

        // Upload File Button
        UploadFileBut.setOnAction(e -> {
            String path = FileSel.getText();
            if (!path.isEmpty()) { // if file selected
                DBHandle.loadExcelToDB(path);
            }
        });

        // Exit Button
        ExitBut.setOnAction(e -> {
            DBHandle.closeConnectionToDB(); // Prevent memory leaks
            MainWindow.close();
        });

        //----------------------------------------------------------------------------

        // return scene to be displayed
        return scene;
    }
}
