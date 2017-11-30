package Stages.MainStage;

import database.DBHandle;
import Stages.mainWin;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.String.format;

public class graphPage {
    private static AnchorPane anchor = null;

    // Opens this scene
    public static void open() {
        // Layout not created until first call
        if (anchor == null) { anchor = new AnchorPane(); }
        mainWin.changeCenter(anchor);
        create();
    }

    // Scene layout and events
    private static void create() {

//-----------------------------------------------------------------//
        //-------------- DROP BOX "X" -----------------------//
        ComboBox comboBoxX = new ComboBox();
        comboBoxX.setPrefSize(100.0, 40.0);   //Sets the default drop list size
        AnchorPane.setTopAnchor(comboBoxX, 50.0);           //Sets the positioning of the dropbox
        AnchorPane.setLeftAnchor(comboBoxX, 500.0);          // ^
        //----------------POPULATE X OPTIONS-----------------//
        comboBoxX.setPromptText("Select X Axis");                                       //Populates the dropbox with a prompt so user know what to do
        comboBoxX.getItems().addAll("option 1","option 2","option 3");        //Populates the actual drop down menu
//-----------------------------------------------------------------//

//-----------------------------------------------------------------//
        //-------------- DROP BOX "Y" -----------------------//
        ComboBox comboBoxY = new ComboBox();
        comboBoxY.setPrefSize(100.0, 40.0);  //Sets the default drop list size
        AnchorPane.setTopAnchor(comboBoxY, 50.0);          //Sets the positioning of the dropbox
        AnchorPane.setRightAnchor(comboBoxY, 50.0);        //^
        //----------------POPULATE Y OPTIONS-----------------//
        comboBoxY.setPromptText("Select Y Axis");                                      //Populates the dropbox with a prompt so user know what to do
        comboBoxY.getItems().addAll("option 1","option 2","option 3");       //Populates the actual drop down menu
//-----------------------------------------------------------------//
        //----------------CHECK BOXES-----------------//
        // This is the list of the checkboxes and their formatting.//

        VBox checkboxes = new VBox();
        AnchorPane.setLeftAnchor(checkboxes, 10.0);
        AnchorPane.setTopAnchor(checkboxes, 10.0);
        ResultSet r = DBHandle.queryReturnResult("SELECT \"Dept ID - Dept Description\" FROM 'College of E&CS';");
        try {
            r.next();
            while (!r.isClosed()) {
                String str = r.getString(1);
                str = str.substring(str.indexOf('-') + 2, str.length());
                CheckBox box = new CheckBox(str);
                box.setSelected(true);
                checkboxes.getChildren().add(box);
                r.next();
            }
        } catch (SQLException e) { e.printStackTrace(); }

//-----------------------------------------------------------------//

//-----------------------------------------------------------------//
        //------------------- GRAPH BUTTON ------------------//
        Button Graph = new Button("GRAPH");             //Creates Graph Button
        AnchorPane.setBottomAnchor(Graph, 75.0);       //Sets the positioning of the button
        AnchorPane.setRightAnchor(Graph, 50.0);        //^
//-----------------------------------------------------------------//

        //------------------- BACK BUTTON -------------------//
        Button Back = new Button("BACK");               //Creates Back Button
        AnchorPane.setBottomAnchor(Back, 75.0);        //Sets the positioning of the button
        AnchorPane.setLeftAnchor(Back, 585.0);          //^
//-----------------------------------------------------------------//

        anchor.getChildren().addAll(comboBoxX, comboBoxY);
        anchor.getChildren().addAll(Graph, Back, checkboxes);

        Graph.setOnAction(e -> createGraph());
    }

    private static void createGraph() {
        CategoryAxis xAxis = new CategoryAxis();                        // X-Axis
        NumberAxis yAxis = new NumberAxis();                            // Y-Axis
        BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);     // Create Bar Chart
        bc.setTitle("Total Purchase Cost");                             // Title of Chart
        xAxis.setLabel("Department");                                   // Title of X-Axis
        yAxis.setLabel("Purchase Cost");                                // Title of Y-Axis
        XYChart.Series series = new XYChart.Series<>();                 // The "Bars"

        ResultSet depts = DBHandle.queryReturnResult("SELECT \"TBL_NAME\" FROM 'importedTables';");
        try {
            depts.next();
            String str = format("SELECT \"Dept ID - Dept Description\" FROM '%s';", depts.getString(1));
            ResultSet names = DBHandle.queryReturnResult(str);
            depts.next(); names.next();
            while (!depts.isClosed()) {
                str = format("SELECT \"Purchase Cost\" FROM '%s';", depts.getString(1));
                ResultSet costs = DBHandle.queryReturnResult(str);
                double totalCost = getTotalCost(costs);
                String deptName = names.getString(1);
                deptName = deptName.substring(deptName.indexOf('-') + 2, deptName.length());
                series.getData().add(new XYChart.Data<>(deptName, totalCost));
                depts.next(); names.next();
            }
            bc.getData().add(series);

            AnchorPane.setTopAnchor(bc, 150.0);
            AnchorPane.setRightAnchor(bc, 300.0);
            anchor.getChildren().addAll(bc);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private static double getTotalCost(ResultSet costs) {
        double sum = 0.0;
        try {
            costs.next();
            while (!costs.isClosed()) {
                sum += costs.getDouble(1);
                costs.next();
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return sum;
    }
}

