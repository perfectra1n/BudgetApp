package Pages.TablePage;

import Pages.mainWin;
import database.DBHandle;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class tablePage {
    private static TabPane tableLayout = null;

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
        BorderPane border = new BorderPane();
        searchTab.setContent(border);
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
        textField[5] = new TextField("min"); // min
        textField[5].setMaxSize(60.0, 27.0);
        textField[6] = new TextField("max"); // max
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
        HBox twoButtons = new HBox(searchButton, resetButton); twoButtons.setSpacing(50.0);
        twoButtons.setAlignment(Pos.CENTER);
        grid.add(twoButtons, 2, 3, 3, 1);
        grid.add(importButton, 8,2);
        // Place search area on layout
        border.setTop(grid);
        /* ********************************************* */
        /*---------------- Search Table -----------------*/
        // Retrieve list of imported tables
        List<String> importedTbls = DBHandle.getImportedTablesList();
        // Retrieve list of column names
        List<String> columns = allColumns(importedTbls);
        // Create and retrieve table
        TableView searchTable = tableClass.createTable(importedTbls, columns);
        // Display table
        searchTable.setStyle("-fx-padding: 5 5 5 5;");
        // Place table on layout
        border.setCenter(searchTable);
        /* ********************************************* */

        /*-------------------- EVENTS -------------------*/
        importButton.setOnAction(e -> importData.open());

        resetButton.setOnAction(e -> {
            for (int i = 0; i < 5; i++) { textField[i].clear(); }
            textField[5].setText("min"); textField[6].setText("max");
        });
        /* ********************************************* */
    }

    private static List<String> allColumns(List<String> importedTables) {
        List<String> columns = new ArrayList<>();
        for (String tbl : importedTables) {
            if (!Character.isDigit(tbl.charAt(0))) continue; //skip unwanted tables
            List<String> inColumns = DBHandle.getColumnNames(tbl);
            for (String col : inColumns) {
                if (columns.contains(col) || col.contains("No Assets")) continue; //skip columns already included
                columns.add(col);
            }
        }
        return columns;
    }
}
