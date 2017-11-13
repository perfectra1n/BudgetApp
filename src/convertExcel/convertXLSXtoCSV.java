package convertExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import bapSQL.InsertApp;

public class convertXLSXtoCSV
{
    public static void xlsx(File inputFile, File outputFile)
    {
        //For storing data into CSV files
        StringBuffer data = new StringBuffer();

        try
        {
            FileOutputStream fos = new FileOutputStream(outputFile);

            //Get the workbook object for XLSX file
            XSSFWorkbook wBook = new XSSFWorkbook(new FileInputStream(inputFile.getPath()));

            //Get first sheet from the workbook
            XSSFSheet sheet = wBook.getSheetAt(0);
            Row       row;
            Cell      cell;

            //Iterator through each rows from the first sheet
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext())
            {
                row = rowIterator.next();

                //For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext())
                {
                    cell = cellIterator.next();

                    switch (cell.getCellTypeEnum())
                    {
                        case BOOLEAN:
                            data.append(cell.getBooleanCellValue() + ",");
                            break;

                        case NUMERIC:
                            data.append(cell.getNumericCellValue() + ",");
                            break;

                        case STRING:
                            data.append(cell.getStringCellValue() + ",");
                            break;

                        case BLANK:
                            data.append("" + ",");
                            break;

                        default:
                            data.append(cell + ",");


                    }
                }
            }

           fos.write(data.toString().getBytes());
           fos.close();

        } catch (Exception ioe)
        {
            ioe.printStackTrace();
        }

    }

    public static void createNewDatabase(String fileName)
    {
        //will need to eventually change this
        String url = "jdbc:sqlite:/sqlite/db/" + fileName;

        try (Connection conn = DriverManager.getConnection(url))
        {
            if (conn != null)
            {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A connection has been established with the SQLite database.");
                System.out.println("It is located at: " + url);
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }



    public static void main(String[] args)
    {
        InsertApp app = new InsertApp();
        createNewDatabase("bap.db");


    }

}