package Pages.TablePage;

import Pages.mainWin;
import database.dbHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static Pages.TablePage.tableClass.createTable;

public class tablePage {
    private static TabPane tableLayout = null;
    private static TableView searchTable;
    private static BorderPane border;

    public static void open() {
        if (tableLayout == null) {
            tableLayout = new TabPane();
            mainWin.changeCenter(tableLayout);
            create();
        }
        else { mainWin.changeCenter(tableLayout); }
    }

    private static void create() {
        /*------------------ Search Tab -----------------*/
        Tab searchTab = new Tab("Search");
        searchTab.setClosable(false);
        tableLayout.getTabs().add(searchTab);
        /* ********************************************* */
        /*--------------- Create Border Pane ------------*/
        border = new BorderPane();
        searchTab.setContent(border);
        /* ********************************************* */
        /*---------------- Search Table -----------------*/
        // Retrieve list of imported tables
        List<String> importedTbls = dbHandler.getImportedTablesList();
        // Retrieve list of column names
        List<String> columns = allColumns(importedTbls);
        // Create and retrieve table
        searchTable = createTable(importedTbls, columns);
        // Display table
        searchTable.setStyle("-fx-padding: 5 5 5 5;");
        // Place table on layout
        border.setCenter(searchTable);
        /* ********************************************* */
        /*---------------- Search Layout ----------------*/
        GridPane grid = new GridPane();
        grid.setStyle("-fx-padding: 10 10 10 10;"
                + "-fx-background-color: lightgrey;");
        grid.setHgap(20.0);
        // Search Labels
        Label[] label = new Label[6];
        label[0] = new Label("Asset Tag"); label[1] = new Label("Asset Type");
        label[2] = new Label("Site"); label[3] = new Label("Dept Code");
        label[4] = new Label("Purchase Order"); label[5] = new Label("Purchase Cost");
        // Text Fields
        TextField[] textField = new TextField[7];
        textField[0] = new TextField(); // Asset Tag
        textField[0].setMinSize(140.0,27.0);
        textField[1] = new TextField(); // Asset Type
        textField[1].setMinSize(140.0,27.0);
        textField[2] = new TextField(); // Site
        textField[2].setMinSize(140.0,27.0);
        textField[3] = new TextField(); // Dept Code
        textField[3].setMinSize(40.0,27.0);
        textField[4] = new TextField(); // Purchase Order
        textField[4].setMinSize(120.0,27.0);
        textField[5] = new TextField(); // min
        textField[5].setPromptText("min");
        textField[5].setMaxSize(60.0, 27.0);
        textField[6] = new TextField(); // max
        textField[6].setPromptText("max");
        textField[6].setMaxSize(60.0,27.0);
        // Add to grid
        grid.add(label[0], 0, 0);       // Asset Tag
        grid.add(textField[0], 0, 1);
        grid.add(label[1], 0, 2);       // Asset Type
        grid.add(textField[1], 0, 3);
        grid.add(label[2], 1, 0);       // Site
        grid.add(textField[2], 1, 1);
        grid.add(label[3], 1, 2);       // Dept Code
        grid.add(textField[3], 1, 3);
        grid.add(label[4], 2, 0);       // Purchase Order
        grid.add(textField[4], 2, 1);
        grid.add(label[5], 3, 0);       // Purchase Cost
        HBox MaxMin = new HBox(textField[5], textField[6]); MaxMin.setSpacing(15.0);
        grid.add(MaxMin, 3, 1);
        // Buttons
        Button searchButton = new Button("Search"); searchButton.setPrefSize(70.0, 30.0);
        Button resetButton = new Button("Reset"); resetButton.setPrefSize(70.0, 30.0);
        Button importButton = new Button(("Import...")); importButton.setPrefSize(100.0,25.0);
        ProgressIndicator pind = new ProgressIndicator(0);
        pind.setMaxSize(100,100);
        pind.progressProperty().addListener((observable, old, done) -> {
            if (done.doubleValue()==1) {
                updateTable();
            }
        });
        HBox twoButtons = new HBox(searchButton, resetButton); twoButtons.setSpacing(50.0);
        twoButtons.setAlignment(Pos.CENTER);
        grid.add(twoButtons, 2, 3, 3, 1);
        grid.add(importButton, 8,1);
        // Place search area on layout
        border.setTop(grid);
        /* ********************************************* */

        /*-------------------- EVENTS -------------------*/
        searchButton.setOnAction(e -> {

        });

        importButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser(); // Create new File Chooser
            fileChooser.setTitle("Open Excel File");     // Title of File Chooser Window
            fileChooser.getExtensionFilters().addAll(    // Accepted file formats
                    new FileChooser.ExtensionFilter(
                            "Excel Files \"*.xlsx or\", \"*.xls\"",
                            "*.xlsx", "*.xls"));
            File chosenFile = fileChooser.showOpenDialog(null);
            if (!chosenFile.getPath().isEmpty()) {
                border.setCenter(pind);
                Task task = dbHandler.loadExcelToDB(chosenFile.getPath());
                pind.progressProperty().unbind();
                pind.progressProperty().bind(task.progressProperty());
                new Thread(task).start();
            }
        });

        searchTable.setOnMouseClicked(e -> {
            int clicks = e.getClickCount();
            if (clicks == 2) {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    tableDataObj obj = (tableDataObj) searchTable.getSelectionModel().getSelectedItem();
                    tablePageTab tab = new tablePageTab(obj);
                    tableLayout.getTabs().add(tab.getItemTab());
                }
            }
        });

        resetButton.setOnAction(e -> {
            for (int i = 0; i < 5; i++) { textField[i].clear(); }
            textField[5].setPromptText("min"); textField[6].setPromptText("max");
        });
        /* ********************************************* */
    }

    private static List<String> allColumns(List<String> importedTables) {
        List<String> columns = new ArrayList<>();
        for (String tbl : importedTables) {
            if (!Character.isDigit(tbl.charAt(0))) continue; //skip unwanted tables
            List<String> inColumns = dbHandler.getColumnNames(tbl);
            for (String col : inColumns) {
                if (columns.contains(col) || col.contains("No Assets")) continue; //skip columns already included
                columns.add(col);
            }
        }
        return columns;
    }

    public static void updateTable() {
        List<String> importedTbls = dbHandler.getImportedTablesList();
        List<String> columns = allColumns(importedTbls);
        searchTable = createTable(importedTbls, columns);
        border.setCenter(searchTable);
    }
}
