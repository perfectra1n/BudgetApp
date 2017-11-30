package Stages.MainStage;

import database.DBHandle;
import Stages.mainWin;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.sql.ResultSet;
import java.sql.SQLException;

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

        Graph.setOnAction(e -> {
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
            bc.setTitle("Total Purchase Cost");
            xAxis.setLabel("Department");
            yAxis.setLabel("Purchase Cost");
            XYChart.Series series = new XYChart.Series<>();
            try {
            ResultSet res = DBHandle.queryReturnResult("SELECT \"Purchase Cost\" FROM '8-152';");
            ResultSet dep = DBHandle.queryReturnResult("SELECT \"Dept ID - Dept Description\" FROM \"College of E&CS\"");
                double sum = 0.0;
              //  dep.next();
               // res.next();
                while (dep.next()) {
                    series.getData().add(new XYChart.Data<>(dep.getString(1), getTotalCost(res.getString(1))));
                    //res.next();
                   // dep.next();
                }
                bc.getData().add(series);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            AnchorPane.setTopAnchor(bc, 150.0);
            AnchorPane.setRightAnchor(bc, 300.0);
            anchor.getChildren().addAll(bc);
        });
    }

    private static double getTotalCost(String query) {
        double sum = 0;
        try {

            ResultSet res = DBHandle.queryReturnResult("SELECT \"Purchase Cost\" FROM '8-152';");
            sum = 0.0;
            res.next();
            while (!res.isClosed()) {
                sum += res.getDouble(1);
                res.next();
            }
            System.out.println(sum);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return sum;
    }
}

