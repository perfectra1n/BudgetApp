package Pages.TablePage;

import Pages.mainWin;
import database.dbHandler;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class tablePage {
    private static TabPane tableLayout = null;
    private static tableClass searchTable;
    private static Tab searchTab;
    private static BorderPane border;

    public static void open() {
        if (tableLayout == null) {
            tableLayout = new TabPane();
            tableLayout.setId("TablePage");
            String tablePageCss = tablePage.class.getResource(
                    "/resources/css/tablePage.css").toExternalForm();
            tableLayout.getStylesheets().clear();
            tableLayout.getStylesheets().add(tablePageCss);
            /*------------------ Search Tab -----------------*/
            searchTab = new Tab("Search");
            searchTab.setClosable(false);
            tableLayout.getTabs().add(searchTab);
            /* ********************************************* */
            mainWin.changeCenter(tableLayout);
            create(searchTab);
        }
        else {
            searchTab.setContent(border);
            mainWin.changeCenter(tableLayout);
        }
    }

    private static void create(Tab searchTab) {
        /*--------------- Create Border Pane ------------*/
        border = new BorderPane(); searchTab.setContent(border);
        /* ********************************************* */
        /*---------------- Search Table -----------------*/
        // Retrieve list of imported tables
        List<String> importedTbls = dbHandler.getImportedTablesList();
        // Retrieve list of column names
        List<String> columns = allColumns(importedTbls);
        // Create and retrieve table
        searchTable = new tableClass(columns, importedTbls);
        // Place table on layout
        border.setCenter(searchTable.getTable());
        /* ********************************************* */
        /*---------------- Search Layout ----------------*/
        GridPane searchArea = new GridPane();
        //searchArea.setMinHeight();
        searchArea.setHgap(16.0);
        // Key Column Search Field
        Label keyLabel = new Label(searchTable.getKeyColumn());
        TextField keyTextField = new TextField();
        searchArea.add(keyLabel,0,0);
        searchArea.add(keyTextField,0,1);
        // Purchase Cost Search Field
        Label PurchaseCost = new Label("Purchase Cost");
        TextField minCost = new TextField();
        minCost.setPromptText("min"); minCost.setMaxWidth(60);
        TextField maxCost = new TextField();
        maxCost.setPromptText("max"); maxCost.setMaxWidth(60);
        searchArea.add(PurchaseCost,1,0);
        HBox cost = new HBox(minCost, maxCost); cost.setSpacing(5);
        searchArea.add(cost,1,1);
        // Search Button
        MenuItem advSearch = new MenuItem("Advanced Search");
        SplitMenuButton searchButton = new SplitMenuButton(advSearch);
        searchButton.setText("Search"); searchButton.setId("SearchButton");
        searchArea.add(searchButton,3,1);
        // Reset Button
        Button resetButton = new Button("Reset");
        resetButton.setId("ResetButton");
        HBox searchANDreset = new HBox(searchButton, resetButton);
        searchANDreset.setSpacing(40);
        searchArea.add(searchANDreset,3,1);
        // Import Excel Sheet Button
        Button importButton = new Button(("Import Excel Sheet"));
        searchArea.add(importButton,8,1);
        // Progress indicator
        ProgressIndicator pind = new ProgressIndicator(0);
        pind.progressProperty().addListener((observable, old, done) -> {
            if (done.doubleValue()==1) {
                updateTable();
            }
        });

        // Place search area on layout
        border.setTop(searchArea);
        /* ********************************************* */
        /*-------------------- EVENTS -------------------*/
        searchButton.setOnAction(e -> {
            FilteredList<tableDataObj> filteredDataList =
                    new FilteredList<tableDataObj>(
                            searchTable.getActiveData(), cond -> true);

            searchTable.setActiveData(filteredDataList);
        });
        
        advSearch.setOnAction(e -> searchTab.setContent(advancedSearch.open()));



        importButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser(); // Create new File Chooser
            fileChooser.setTitle("Open Excel File");     // Title of File Chooser Window
            fileChooser.getExtensionFilters().addAll(    // Accepted file formats
                    new FileChooser.ExtensionFilter(
                            "Excel Files \"*.xlsx or\", \"*.xls\"",
                            "*.xlsx", "*.xls"));
            File chosenFile = fileChooser.showOpenDialog(tableLayout.getScene().getWindow());
            if (chosenFile != null) {
                border.setCenter(pind);
                Task task = dbHandler.loadExcelToDB(chosenFile.getPath());
                pind.progressProperty().unbind();
                pind.progressProperty().bind(task.progressProperty());
                new Thread(task).start();
            }
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

    private static void updateTable() {
        List<String> importedTbls = dbHandler.getImportedTablesList();
        List<String> columns = allColumns(importedTbls);
        border.setCenter(new tableClass(columns, importedTbls).getTable());
    }

    static void openNewTab(Tab tab) {
        tableLayout.getTabs().add(tab);
        tableLayout.getSelectionModel().select(tab);
    }
}
