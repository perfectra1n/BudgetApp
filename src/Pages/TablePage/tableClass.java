package Pages.TablePage;

import database.dbHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.List;

class tableClass {
    private final TableView<tableDataObj> table;
    private final ObservableList<TableColumn<tableDataObj, Object>> tableColumns;
    private final ObservableList<tableDataObj> masterTableData;
    private String keyColumn;

    tableClass(List<String> inColumns, List<String> inDataTables) {
        this.table = new TableView<>();
        this.keyColumn = inColumns.get(0);
        this.tableColumns = createTblColumns(inColumns);
        this.masterTableData = createData(inDataTables);
        table.getColumns().addAll(tableColumns);
        table.setItems(masterTableData);
    }

    TableView<tableDataObj> getTable() { return table; }
    ObservableList<TableColumn<tableDataObj, Object>> getTableColumns() { return tableColumns; }
    ObservableList<tableDataObj> getMasterTableData() { return masterTableData; }
    String getKeyColumn() { return keyColumn; }

    void setKeyColumn(String key) { keyColumn = key; }

    // Creates and returns table columns
    private ObservableList<TableColumn<tableDataObj, Object>> createTblColumns(List<String> inColumns) {
        // Create columns and references, set types. Return column list.
        ObservableList<TableColumn<tableDataObj, Object>> columns = FXCollections.observableArrayList();
        for (String col : inColumns) {
            TableColumn<tableDataObj, Object> newCol = new TableColumn<>(col);
            newCol.setCellValueFactory(p -> p.getValue().getProperty(col));
            columns.add(newCol);
        }
        return columns;
    }

    // Creates an ObservableList of data for table
    // Uses database functions
    private ObservableList<tableDataObj> createData(List<String> inDataTables) {
        ObservableList<tableDataObj> data = FXCollections.observableArrayList();
        for (String tbl : inDataTables) {
            if (!Character.isDigit(tbl.charAt(0))) continue;
            data.addAll(dbHandler.getTableDataObjects(tbl));
        }
        return data;
    }
}