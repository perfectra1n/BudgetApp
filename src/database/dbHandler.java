// DBHandle.java
// Application specific methods for contacting
// and retrieving information from the database
//

package database;

import Pages.TablePage.tableDataObj;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.util.Pair;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sqlite.date.DateFormatUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static database.dbOperations.*;
import static java.lang.String.format;

public class dbHandler {
    // Default database URL
    private static String dbURL = "jdbc:sqlite:src/database/BAPDB.db";

    // Connect to specified URL, creates file if non-existent
    public static void connectToDB() {
        connect(dbURL);
    }

    // Change dbURL
    public static void changeDBURL(String path) {
        // Not implemented
    }

    // Close connection to database
    public static void closeConnectionToDB() {
        close();
    }

    // Create a new table in database
    private static void createNewTable(String tableName, List<Pair<String, String>> columnData) {
        // Initialize StringBuilder to create the query
        StringBuilder query = new StringBuilder(format("CREATE TABLE IF NOT EXISTS '%s' (", tableName));
        // Add column names and data types
        columnData.forEach(pair ->
                query.append(format("'%s' %s,\n", pair.getKey(), pair.getValue())));
        // Remove final comma and place ');' to end the query
        query.replace(query.lastIndexOf(","),query.length(),");");
        // Send query to database
        sendQuery(query.toString());
    }

    // Create a query as String for inserting to database table, returns as String
    private static String insertData(String tableName, List<String> values) {
        // Format list of values
        StringBuilder query = new StringBuilder(format("INSERT INTO '%s' VALUES (", tableName));
        // Add values given
        values.forEach(value -> query.append(format("%s, ", value)));
        // Remove final comma and place ');' to end the query
        query.replace(query.lastIndexOf(","),query.length(),");");

        String logMSG = query.toString();
        logMSG = logMSG.replace("INSERT INTO", "Data inserted into table");
        logMSG = logMSG.replace("VALUES", "Data added");
        logMSG = logMSG.replace(";", ";\n");

        // Record to log
        logger.log.insert(logMSG);

        return query.toString();
    }

    // Get column names from a specified table, returns a List of Strings
    public static List<String> getColumnNames(String tableName) {
        List<String> list = new ArrayList<>();
        ResultSet r = queryReturnResult(format("PRAGMA table_info('%s');", tableName));
        try {
            while (r.next()) { list.add(r.getString("name")); }
        } catch (SQLException e) {e.printStackTrace();}
        return list;
    }

    // Delete all tables from a given list
    private static void dropAllTables(List<String> tables) {
        for (String tbl : tables) { dropTable(tbl); }
    }

    // Get list of all imported database tables, returns a List of Strings
    public static List<String> getImportedTablesList() {
        List<String> list = new ArrayList<>();
        ResultSet r = queryReturnResult("SELECT \"TBL_NAME\" FROM 'importedTables';");
        try {
            while (r.next()) { list.add(r.getString(1)); }
        } catch (SQLException e) {e.printStackTrace();}
        return list;
    }

    // delete all imported tables
    private static void deleteAllImportedTables() {
        dropAllTables(getImportedTablesList());
        sendQuery("DELETE FROM importedTables");
    }

    // Create set of Table Data Objects
    public static ObservableList<tableDataObj> getTableDataObjects(String tableName) {
        ObservableList<tableDataObj> dataObjects = FXCollections.observableArrayList();
        ResultSet r = queryReturnResult(format("SELECT * FROM '%s';", tableName));
        List<String> columnNames = getColumnNames(tableName);
        try {
            while (r.next()) {
                tableDataObj newObj = new tableDataObj(tableName);
                for (String col : columnNames) {
                    newObj.setProperty(col, r.getObject(col));
                }
                dataObjects.add(newObj);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return dataObjects;
    }

    // Create set of Graph Data Objects - NEED TO CREATE CLASS IN GRAPHPAGE PACKAGE
    /*public static ObservableList<graphDataObject> getGraphDataObjects(String tableName) {
        ObservableList<graphDataObj> dataObjects = FXCollections.observableArrayList();
        ResultSet r = queryReturnResult(format("SELECT * FROM '%s';", tableName));
        List<String> columnNames = getColumnNames(tableName);
        try {
            while (r.next()) {
                graphDataObj newObj = new graphDataObj(tableName);
                for (String col : columnNames) {
                    newObj.setProperty(col, r.getObject(col));
                }
                dataObjects.add(newObj);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return dataObjects;
    }*/

    // Import Excel contents to database
    // This function will delete all tables then create new ones
    // Use of this function is intended for a directly mirrored import
    public static Task loadExcelToDB(String path) {
        return new Task() {
            @Override
            protected Object call() throws Exception {

                // First delete previously imported tables (if any)
                sendQuery("CREATE TABLE IF NOT EXISTS importedTables ('TBL_NAME' STRING);");
                boolean deleteLastImport = true;
                // TEMPORARILY ALWAYS TRUE
                if (deleteLastImport) { deleteAllImportedTables(); }

                // Next, load an excel file
                try {
                    FileInputStream excFile = new FileInputStream(new File(path));  // Open selected file
                    Workbook wb = new XSSFWorkbook(excFile);                        // Load file
                    /*------------------------------ Used for task progress ------------------------------*/
                    int numberOfCells = 0, n = 0;
                    for (int a = 0; a < wb.getNumberOfSheets(); a++) {
                        if (wb.getSheetAt(a).getPhysicalNumberOfRows() == 1) continue;
                        for (int b = 1; b < wb.getSheetAt(a).getPhysicalNumberOfRows(); b++) {
                            for (int c = 0; c < wb.getSheetAt(a).getRow(b).getLastCellNum(); c++) {
                                numberOfCells++;
                            }}}
                    /*------------------------------------------------------------------------------------*/

                    // Process data into database
                    for (int a = 0; a < wb.getNumberOfSheets(); a++) {
                        Sheet sheet = wb.getSheetAt(a);

                        // CREATE TABLE
                        List<Pair<String, String>> columns = new ArrayList<>();
                        Row topRow = sheet.getRow(0);
                        for (int i = 0; i < topRow.getLastCellNum(); i++) {
                            String colName;
                            String colType = "STRING";
                            colName = topRow.getCell(i).getStringCellValue();
                            if (colName.isEmpty()) continue;
                            if (sheet.getRow(1) != null) {
                                colType = sheet.getRow(1).getCell(i).getCellTypeEnum().toString();
                            }
                            columns.add(new Pair<>(colName, colType));
                        }
                        createNewTable(sheet.getSheetName(), columns);
                        // Add table name to importedTables
                        sendQuery(format("INSERT INTO importedTables VALUES ('%s')", sheet.getSheetName()));
                        //sheet.removeRow(sheet.getRow(0));

                        // INSERT DATA
                        StringBuilder values = new StringBuilder(
                                format("INSERT INTO '%s' \nVALUES (", sheet.getSheetName()));
                        if (sheet.getPhysicalNumberOfRows() == 1) continue;
                        for (int b = 1; b < sheet.getPhysicalNumberOfRows(); b++) {
                            Row row = sheet.getRow(b);
                            boolean rowIsEmpty = true;

                            //List<String> values = new ArrayList<>();
                            for (int c = 0; c < row.getLastCellNum(); c++) {
                                Cell cell = row.getCell(c);
                                n++; updateProgress(n, numberOfCells);

                                switch (cell.getCellTypeEnum()) {
                                    case STRING:
                                        // if string contains a ' char, replace all instances with ''
                                        values.append(format("'%s',",
                                                cell.getStringCellValue().replaceAll("'", "''")));
                                        rowIsEmpty = false;
                                        break;
                                    case NUMERIC:
                                        if (DateUtil.isCellDateFormatted(cell)) {
                                            values.append(format("'%s',",
                                                    DateFormatUtils.format(
                                                            cell.getDateCellValue(), "MM/dd/yyyy")));
                                        } else { values.append(format("%f,", cell.getNumericCellValue())); }
                                        rowIsEmpty = false;
                                        break;
                                    case BOOLEAN:
                                        values.append(format("%b,", cell.getBooleanCellValue()));
                                        rowIsEmpty = false;
                                        break;
                                    case ERROR:
                                        values.append(format("%s,", cell.getErrorCellValue()));
                                        rowIsEmpty = false;
                                        break;
                                    case BLANK:
                                        values.append("'',");
                                        break;
                                    case _NONE:
                                        values.append("'',");
                                        break;
                                }
                            }//); // each cell
                            for (int i = row.getLastCellNum(); i < columns.size(); i++) {
                                values.append("'',");
                            }
                            if (rowIsEmpty) {
                                values.replace(values.lastIndexOf("("), values.length(), "");
                            } else {
                                values.replace(values.lastIndexOf(","), values.length(), "),\n(");
                            }
                        }//); // each row
                        values.replace(values.lastIndexOf(","), values.length(), ";");
                        sendQuery(values.toString());
                    }//); // each sheet
                } catch (IOException e) { e.printStackTrace(); }
                return true;
            }
        };
    }
}