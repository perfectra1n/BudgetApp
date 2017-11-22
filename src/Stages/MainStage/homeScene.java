// InitScene.java
// This is a test class
// May be reused for Home Screen
//

package Stages.MainStage;

import Stages.Interconnector;
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

    public static Scene create() {

        AnchorPane anchor = new AnchorPane();
        GridPane grid = new GridPane();
        anchor.setBottomAnchor(grid, 0.0);
        grid.prefWidthProperty().bind(anchor.widthProperty());
        //grid.prefHeightProperty().bind(anchor.heightProperty());
        // grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        // Menu Bar
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        menuBar.getMenus().addAll(menuFile);
        // Menu Items
        MenuItem itemOpen = new MenuItem("Open");
        MenuItem itemClose = new MenuItem("Close");
        MenuItem itemLog = new MenuItem("Log");
        menuFile.getItems().addAll(itemOpen, itemLog, itemClose);
        menuBar.prefWidthProperty().bind(anchor.widthProperty());
        //menuBar.prefHeightProperty().bind(anchor.heightProperty());
        anchor.setTopAnchor(menuBar,0.0);
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

        // Log Menu Item
        itemLog.setOnAction(e -> {
            Stage logStage = new Stage();
            logStage.show();
        });

        // Select File Button
        SelFileBut.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Open Excel File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter(
                            "Excel Files \"*.xlsx or\", \"*.xls\"",
                            "*.xlsx", "*.xls"));
            File chosenFile = fileChooser.showOpenDialog(null);
            if (chosenFile != null) {
                FileSel.setText(chosenFile.getPath());
            }
        });

        // Upload File Button
        UploadFileBut.setOnAction(e -> {
            String path = FileSel.getText();
            DBHandle.dropTable("table2");
            //DBHandle.createNewTable("table1", "column1", "column2", "column3", "column4", "column5");
            if (!path.isEmpty()) { // if file selected
                DBHandle.loadExcelToDB(path);
            }
        });

        // Exit Button
        ExitBut.setOnAction(e -> {
            Interconnector.closeWindow();
        });

        //----------------------------------------------------------------------------

        // return scene to be displayed
        anchor.getChildren().addAll(menuBar,grid);
        return (new Scene(anchor, 800, 600));
    }
}
