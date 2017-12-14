package Pages.TablePage;

import database.dbHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

import java.util.List;

class tableClass {
    private final TableView<tableDataObj> table;
    private final ObservableList<TableColumn<tableDataObj, Object>> tableColumns;
    private final ObservableList<tableDataObj> masterTableData;
    private FilteredList<tableDataObj> activeData;
    private String keyColumn;

    tableClass(List<String> inColumns, List<String> inDataTables) {
        this.table = new TableView<>();
        if (!inColumns.isEmpty()) keyColumn = inColumns.get(0);
        this.tableColumns = createTblColumns(inColumns);
        this.masterTableData = createData(inDataTables);
        activeData = new FilteredList<>(masterTableData, p -> true);
        table.getColumns().addAll(tableColumns);
        table.setItems(activeData);
        setProperties();
    }

    TableView<tableDataObj> getTable() { return table; }
    ObservableList<TableColumn<tableDataObj, Object>> getTableColumns() { return tableColumns; }
    ObservableList<tableDataObj> getMasterTableData() { return masterTableData; }
    String getKeyColumn() { return keyColumn; }
    void setKeyColumn(String key) { keyColumn = key; }
    FilteredList<tableDataObj> getActiveData() { return activeData; }

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

    private void setProperties() {
        table.setOnMouseClicked(e -> {
            int clicks = e.getClickCount();
            if (clicks == 2) {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    tableDataObj obj = table.getSelectionModel().getSelectedItem();
                    tablePageTab tab = new tablePageTab(
                            obj, obj.getProperty(keyColumn).get().toString());
                    tablePage.openNewTab(tab.getItemTab());
                }
            }
        });
    }
}