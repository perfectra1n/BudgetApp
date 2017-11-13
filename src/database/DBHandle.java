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
        try { // creates database file if nonexistent
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Close connection to database
    public static void closeConnectionToDB() {
        try {
            conn.close();
            System.out.println("connection closed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Send query to database
    private static void sendQuery(String query) {
        try {
            Statement sql = conn.createStatement();
            sql.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Create a new table in database
    public static void createNewTable(String tableName, String[] columns, String[] dataTypes) {
        String columnList = format("'%s' %s", columns[0], dataTypes[0]);
        for (int i = 1; i < columns.length; i++) { // more than 1 column/data type
            columnList += format(",\n'%s' %s", columns[i], dataTypes[i]);
        }
        String query = format("CREATE TABLE IF NOT EXISTS '%s' (\n%s);", tableName, columnList);
        //sendQuery(query);
    }

    // Delete an existing table from database
    public static void dropTable(String tableName) {
        String query = format("DROP TABLE IF EXISTS %s;", tableName);
        sendQuery(query);
    }

    // Insert data into table
    public static void insertData(String tableName, String[] values, String... columns) {
        // List of values
        String valueList = format("%s", values[0]);
        for (int i = 1; i < values.length; i++) {
            valueList += format(", %s", values[i]);
        }

        // List of columns (optional)
        String ColumnList = ""; // Empty by default
        if (columns.length > 0) {
            ColumnList = format("('%s'", columns[0]);
            for (int i = 1; i < columns.length; i++) {
                ColumnList += format(", '%s'", columns[i]);
            } ColumnList += ")"; // End list with closing parentheses
        }

        String query = format("INSERT INTO '%s' %s\nVALUES (%s);", tableName, ColumnList, valueList);
        //sendQuery(query);
    }

    // Import Excel contents to database
    // This function will delete all tables then create new ones
    // Use of this function is intended for a directly mirrored import
    public static boolean loadExcelToDB(String path) {
        boolean successfulUpload = false;

        try {
            FileInputStream excFile = new FileInputStream(new File(path));  // Open selected file
            Workbook wb = new XSSFWorkbook(excFile);                        // Load file
            int numberOfSheets = wb.getNumberOfSheets();                    // Number of sheets in excel file

            for (int j = 0; j < numberOfSheets; j++) {
                Sheet dataSheet = wb.getSheetAt(j);                     // Load sheet
                String sheetName = dataSheet.getSheetName();            // Load sheet name
                Iterator<Row> rowIterator = dataSheet.iterator();       // Create row iterator

                if (rowIterator.hasNext()) {
                    // Analyze only first row
                    Row currentRow = rowIterator.next();                // Next row (first row)
                    int numberOfColumns = currentRow.getLastCellNum();  // Number of columns (for table)
                    List<String> tempLst = new ArrayList<String>();     // Columns for table creation
                    String[] dataTypes = new String[numberOfColumns];   // Data types for table creation

                    // Fill arrays needed to create new table
                    for (int i = 0; i < numberOfColumns; i++) {
                        if (currentRow.getCell(i).getStringCellValue() != "") {
                            tempLst.add(currentRow.getCell(i).getStringCellValue());
                            // Check for null before assigning dataType in case of empty data
                            if (dataSheet.getRow(currentRow.getRowNum() + 1) != null) {
                                dataTypes[i] = dataSheet.getRow(currentRow.getRowNum() + 1).getCell(i).getCellTypeEnum().toString();
                            } else {
                                dataTypes[i] = "STRING";
                            }
                        }
                    }
                    String[] columns = tempLst.toArray(new String[]{});
                    createNewTable(sheetName, columns, dataTypes); // Create new table for this sheet

                    // Analyze and import rest of rows (if any)
                    while (rowIterator.hasNext()) {
                        currentRow = rowIterator.next(); // Next row
                        Iterator<Cell> cellIterator = currentRow.iterator();
                        int i = 0; // column counter
                        // Fill array with data to be inserted to table.
                        // Would prefer for loop to be able to reset all array positions within
                        // the loop, but it causes issues if a particular cell within the number
                        // of columns does not have an Enum type.
                        while (cellIterator.hasNext()) {
                            Cell currentCell = cellIterator.next();
                            // All values will be passed as strings
                            // but formatted to distinguish data type in database
                            if (currentCell.getCellTypeEnum() == CellType.STRING) {
                                columns[i] = format("'%s'", currentCell.getStringCellValue());
                            } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                                columns[i] = format("%f", currentCell.getNumericCellValue());
                            } else {
                                columns[i] = format("'%s'", currentCell.getStringCellValue());
                            } i++; //increment counter
                        }
                        // Clear array data carried over from previous row
                        while (i < numberOfColumns) {
                            columns[i] = "''";
                            i++;
                        }
                        insertData(sheetName, columns); // Insert into table
                    }
                }
            }
            successfulUpload = true; // successful upload
        } catch (FileNotFoundException e) {
            System.out.println("\nEXCEPTION THROWN: READ THE FOLLOWING");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("\nEXCEPTION THROWN: READ THE FOLLOWING");
            e.printStackTrace();
        }
        return successfulUpload;
    }
}