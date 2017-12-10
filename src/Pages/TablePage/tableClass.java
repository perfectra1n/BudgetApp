package Pages.TablePage;

import database.DBHandle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static java.lang.String.format;

class tableClass {

    // Create Table for viewing all data
    // This function is specifically programmed for this application
    static TableView<tableDataObj> createTable(List<String> inData, List<String> inColumns) {

        // Initialize Table
        TableView<tableDataObj> table = new TableView<>();
        // Add columns to table
        ObservableList<TableColumn<tableDataObj, Object>> columns = createTblColumns(inColumns);
        for (TableColumn<tableDataObj, Object> col : columns) {
            table.getColumns().add(col);
        }
        // Add data to table
        ObservableList<tableDataObj> data = createData(inData);
        table.setItems(data);
        return table;
    }

    // Creates and returns table columns
    private static ObservableList<TableColumn<tableDataObj, Object>> createTblColumns(List<String> inColumns) {
        // Create columns and references, set types. Return column list.
        ObservableList<TableColumn<tableDataObj, Object>> columns = FXCollections.observableArrayList();
        TableColumn<tableDataObj, Object> keyCol = new TableColumn<>("Asset Tag");
        keyCol.setCellValueFactory(p -> p.getValue().getName());
        columns.add(keyCol);
        inColumns.remove(keyCol.getText());
        for (String col : inColumns) {
            TableColumn<tableDataObj, Object> newCol = new TableColumn<>(col);
            newCol.setCellValueFactory(p -> p.getValue().getProperty(col));
            columns.add(newCol);
        }
        return columns;
    }

    // Creates an ObservableList of data for table
    // Uses database functions
    private static ObservableList<tableDataObj> createData(List<String> inData) {
        ObservableList<tableDataObj> data = FXCollections.observableArrayList();
        for (String tbl : inData) {
            if (!Character.isDigit(tbl.charAt(0))) continue; //skip unwanted tables
            ResultSet r = DBHandle.queryReturnResult(format("SELECT * FROM '%s';", tbl));
            List<String> columns = DBHandle.getColumnNames(tbl);
            try {
                for (r.next(); !r.isClosed(); r.next()) {
                    tableDataObj newData = new tableDataObj();
                    newData.setName(r.getObject("Asset Tag"));
                    for (String col : columns) {
                        if (col.equals("Asset Tag")) continue; //skip asset tag
                        newData.setProperty(col, r.getObject(col));
                    }
                    data.add(newData);
                }
            } catch (SQLException e) {e.printStackTrace();}
        }
        return data;
    }
}