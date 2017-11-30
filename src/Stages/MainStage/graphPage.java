package Stages.MainStage;

import database.DBHandle;
import Stages.PageConnector;
import javafx.scene.chart.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class graphPage {
    private static AnchorPane anchor = null;

    // Opens this scene
    public static void open() {
        // Layout not created until first call
        if (anchor == null) { anchor = new AnchorPane(); }
        PageConnector.changeRoot(anchor);
        create();
    }

    // Scene layout and events
    private static void create() {
        // Test Scene
        Button But1 = new Button("Test Scene");
        But1.setMinSize(120.0, 25.0);
        But1.setPrefHeight(25.0);
        AnchorPane.setLeftAnchor(But1, 10.0);
        AnchorPane.setBottomAnchor(But1, 10.0);
        But1.setOnAction(e -> testPage.open());
        //------------------------

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
        
        CheckBox box1 = new CheckBox("CECS");
            AnchorPane.setTopAnchor(box1, 15.0);
            AnchorPane.setLeftAnchor(box1, 10.0);
        CheckBox box2 = new CheckBox("CC");
            AnchorPane.setTopAnchor(box2, 35.0);
            AnchorPane.setLeftAnchor(box2, 10.0);
        CheckBox box3 = new CheckBox("ECS Tech Shop");
            AnchorPane.setTopAnchor(box3, 55.0);
            AnchorPane.setLeftAnchor(box3, 10.0);
        CheckBox box4 = new CheckBox("ECS CAD Center");
            AnchorPane.setTopAnchor(box4, 75.0);
            AnchorPane.setLeftAnchor(box4, 10.0);
        CheckBox box5 = new CheckBox("MESA E.P.");
            AnchorPane.setTopAnchor(box5, 95.0);
            AnchorPane.setLeftAnchor(box5, 10.0);
        CheckBox box6 = new CheckBox("MESA Center");
            AnchorPane.setTopAnchor(box6, 115.0);
            AnchorPane.setLeftAnchor(box6, 10.0);
        CheckBox box7 = new CheckBox("CSc");
            AnchorPane.setTopAnchor(box7, 135.0);
            AnchorPane.setLeftAnchor(box7, 10.0);
        CheckBox box8 = new CheckBox("CE");
            AnchorPane.setTopAnchor(box8, 155.0);
            AnchorPane.setLeftAnchor(box8, 10.0);
        CheckBox box9 = new CheckBox("ECS Campaign Dev.");
            AnchorPane.setTopAnchor(box9, 175.0);
            AnchorPane.setLeftAnchor(box9, 10.0);
        CheckBox box10 = new CheckBox("EE");
            AnchorPane.setTopAnchor(box10, 195.0);
            AnchorPane.setLeftAnchor(box10, 10.0);
        CheckBox box11 = new CheckBox("ME");
            AnchorPane.setTopAnchor(box11, 215.0);
            AnchorPane.setLeftAnchor(box11, 10.0);
        CheckBox box12 = new CheckBox("CpE");
            AnchorPane.setTopAnchor(box12, 235.0);
            AnchorPane.setLeftAnchor(box12, 10.0);
        CheckBox box13 = new CheckBox("CM");
            AnchorPane.setTopAnchor(box13, 255.0);
            AnchorPane.setLeftAnchor(box13, 10.0);
        CheckBox box14 = new CheckBox("ECS Dean's Office");
            AnchorPane.setTopAnchor(box14, 275.0);
            AnchorPane.setLeftAnchor(box14, 10.0);
        CheckBox box15 = new CheckBox("S.A.C");
            AnchorPane.setTopAnchor(box15, 295.0);
            AnchorPane.setLeftAnchor(box15, 10.0);
        CheckBox box16 = new CheckBox("STORC");
            AnchorPane.setTopAnchor(box16, 315.0);
            AnchorPane.setLeftAnchor(box16, 10.0);

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
        anchor.getChildren().addAll(Graph, Back, But1);
        anchor.getChildren().addAll(box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12, box13, box14, box15, box16);

        Graph.setOnAction(e -> {
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
            bc.setTitle("Total Purchase Cost");
            xAxis.setLabel("Department");
            yAxis.setLabel("Purchase Cost");
            XYChart.Series series = new XYChart.Series<>();
            try {
            ResultSet res = database.DBHandle.queryReturnResult("SELECT \"Purchase Cost\" FROM '8-152';");
                double sum = 0.0;
                res.next();
                while (!res.isClosed()) {
                    series.getData().add(new XYChart.Data<>(res.getString(1), res.getInt(1)));
                    res.next();
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
}