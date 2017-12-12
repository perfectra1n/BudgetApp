// dbOperations.java
// Contains java methods for basic interaction with a database
// Not application specific
//

package database;

import java.sql.*;
import static java.lang.String.format;

public class dbOperations {
    private static Connection c = null; // Initialize connection

    // Connect to database
    static void connect(String url) {
        try { c = DriverManager.getConnection(url); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    // Close connection to database
    static void close() {
        try { c.close(); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    // Create and return new Statement
    static Statement getNewStatement() {
        Statement statement = null;
        try { statement = c.createStatement(); }
        catch (SQLException e) { e.printStackTrace(); }
        return  statement;
    }

    // Send a query to database
    static void sendQuery(String query) {
        try { c.createStatement().execute(query); }
        catch (SQLException e) { e.printStackTrace(); }
    }

    // Send a query and return a ResultSet
    public static ResultSet queryReturnResult(String query) {
        ResultSet result = null;
        try { result = c.createStatement().executeQuery(query); }
        catch (SQLException e) { e.printStackTrace(); }
        return result;
    }

    // Delete an existing table from database
    static void dropTable(String tableName) {
        sendQuery(format("DROP TABLE IF EXISTS '%s';", tableName));
    }

}
