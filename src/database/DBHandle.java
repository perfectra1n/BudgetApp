// DBHandle.java
// Handles control of information between program and database
//

package database;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.sql.*;
import java.util.*;
import static java.lang.String.format;

public class DBHandle {

    // Initialize connection
    private static Connection conn = null;

    // Connects to local database file
    // Holds database file in program's local files.
    // Machine independent, creates database file if not found
    public static void connectToDB() {
        String url = "jdbc:sqlite:src/database/BAPDB.db";
        // creates database file if nonexistent
        try {conn = DriverManager.getConnection(url);}
        catch (SQLException e) {e.printStackTrace();}
    }

    // Close connection to database
    public static void closeConnectionToDB() {
        try {conn.close();}
        catch (SQLException e) {e.printStackTrace();}
    }

    // Send a query to database
    private static void sendQuery(String query) {
        try {
            Statement sql = conn.createStatement();
            sql.execute(query);
        } catch (SQLException e) {e.printStackTrace();}
    }

    // Send a query and return ResultSet
    private static ResultSet queryReturnResult(String query) {
        ResultSet result = null;
        try {
            Statement sql = conn.createStatement();
            result = sql.executeQuery(query);
        } catch (SQLException e) {e.printStackTrace();}
        return result;
    }

    // Create a new table in database (returns String)
    public static String createNewTable(String tableName, String[] columns, String[] dataTypes) {
        // Format the column list
        String columnList = format("'%s' %s", columns[0], dataTypes[0]);
        for (int i = 1; i < columns.length; i++) { // more than 1 column/data type
            columnList += format(",\n'%s' %s", columns[i], dataTypes[i]);
        }
        // Format and create the query
        String query = format("CREATE TABLE IF NOT EXISTS '%s' (\n%s);", tableName, columnList);
        // return the query as a String
        return query;
    }

    // Delete an existing table from database
    public static void dropTable(String tableName) {
        String query = format("DROP TABLE IF EXISTS %s;", tableName);
        sendQuery(query);
    }

    // Delete all tables from a given list
    private static void dropAllTables(List tables) {
        for (int i = 0; i < tables.size(); i++) {
            sendQuery(format("DROP TABLE IF EXISTS '%s';", tables.get(i)));
        }
    }

    // Insert data into table
    public static String insertData(String tableName, int numberOfColumns, String[] values, String... columns) {
        // Format list of values
        int i = 0;
        String valueList = format("%s", values[i]); // Start with first value to be input
        for (i = 1; i < values.length; i++) {       // If more than 1 value received
            valueList += format(", %s", values[i]); // Append to list of values
        }
        // if amount of values received < numberOfColumns
        while (i < numberOfColumns) {valueList += ", ''"; i++;} // Place empty string in empty columns

        // Format list of columns (optional)
        String ColumnList = ""; // Empty by default
        if (columns.length > 0) {
            ColumnList = format("('%s'", columns[0]);
            for (i = 0; i < columns.length; i++) {
                ColumnList += format(", '%s'", columns[i]);
            } ColumnList += ")"; // End list with closing parentheses
        }
        // Format and create the query
        String query = format("INSERT INTO '%s' %s\nVALUES (%s);", tableName, ColumnList, valueList);
        // return the query as a String
        return query;
    }

    // Import Excel contents to database
    // This function will delete all tables then create new ones
    // Use of this function is intended for a directly mirrored import
    public static void loadExcelToDB(String path) {
        long t = System.currentTimeMillis(); // Used for displaying time taken to import

        try (Statement sql = conn.createStatement()){
            // First delete previously imported tables (if any)
            ResultSet res = queryReturnResult("SELECT TBL_NAME FROM importedTables;");
            List<String> tblNames = new ArrayList<>();
            res.next(); // start from first imported table name
            while (!res.isClosed()) {tblNames.add(res.getString("TBL_NAME")); res.next();}
            dropAllTables(tblNames);
            sendQuery("DELETE FROM importedTables");

            // Next, load new tables
            FileInputStream excFile = new FileInputStream(new File(path));  // Open selected file
            Workbook wb = new XSSFWorkbook(excFile);                        // Load file
            Iterator<Sheet> sheetIterator = wb.sheetIterator();             // Create sheet iterator

            while (sheetIterator.hasNext()) {
                Sheet currentSheet = sheetIterator.next();              // Load sheet
                String sheetName = currentSheet.getSheetName();         // Load sheet name
                Iterator<Row> rowIterator = currentSheet.iterator();    // Create row iterator

                // Analyze only first row for table creation (must have at least one column for table)
                if (rowIterator.hasNext()) {                            // If sheet not empty
                    Row currentRow = rowIterator.next();                // First row
                    int numberOfColumns = currentRow.getLastCellNum();  // Number of columns (for table)
                    List<String> lstC = new ArrayList<>();              // Columns for table creation
                    List<String> lstD = new ArrayList<>();              // Data types for table creation

                    // Fill arrays needed to create new table
                    for (int i = 0; i < numberOfColumns; i++) {
                        if ((currentRow.getCell(i).getStringCellValue() != "") || (i == 0)) {
                            lstC.add(currentRow.getCell(i).getStringCellValue());
                            Row nextRow = currentSheet.getRow(currentRow.getRowNum() + 1);
                            if (nextRow != null) { // Check for null before assigning dataType in case of empty data
                                lstD.add(nextRow.getCell(i).getCellTypeEnum().toString());
                            } else {lstD.add("STRING");} // if empty data, assign type STRING
                        }//end of if
                    }//end of for
                    String[] columns = lstC.toArray(new String[]{});             // columns to pass
                    String[] datatypes = lstD.toArray(new String[]{});           // datatypes to pass
                    sql.addBatch(createNewTable(sheetName, columns, datatypes)); // create table query
                    // Add table name to importedTables (used for deletion)
                    sendQuery(format("INSERT INTO importedTables VALUES ('%s')", sheetName));
                }//end of if

                // Analyze the rest of the rows after the first row (if any)
                while (rowIterator.hasNext()) {
                    Row currentRow = rowIterator.next();
                    Iterator<Cell> cellIterator = currentRow.iterator();
                    List<String> data = new ArrayList<>();
                    // Process each cell value in current Row
                    while (cellIterator.hasNext()) {
                        Cell currentCell = cellIterator.next();
                        switch (currentCell.getCellTypeEnum()) { // different cell types
                            case STRING:
                                // if string contains a ' char, replace all instances with ''
                                String str = currentCell.getStringCellValue();
                                if (str.contains("'")) {str = str.replaceAll("'", "''");}
                                data.add(format("'%s'", str));
                                break;
                            case NUMERIC:
                                data.add(format("%f", currentCell.getNumericCellValue()));
                                break;
                            case BOOLEAN:
                                data.add(format("%b", currentCell.getBooleanCellValue()));
                                break;
                            case ERROR:
                                data.add(format("%s", currentCell.getErrorCellValue()));
                                break;
                            case BLANK:
                                data.add("''");
                                break;
                            case _NONE:
                                data.add("''");
                                break;
                        } // end of switch
                    }//end of cell while
                    String[] columnData = data.toArray(new String[]{}); // Values to be passed
                    // Insert data query
                    sql.addBatch(insertData(sheetName, currentSheet.getRow(0).getLastCellNum(), columnData));
                }//end of row while
            }//end of while
            sql.executeBatch(); // Execute all queries
        }//end of try
        catch (IOException | SQLException e) {e.printStackTrace();}
        //display time taken for import
        double q = (System.currentTimeMillis() - t) / 1000.0;
        System.out.println(q + " seconds");
    }
}