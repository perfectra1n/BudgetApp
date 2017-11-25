package Stages.MainStage;

import Stages.Interconnector;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class GraphScene {

    private static AnchorPane anchor = null; // Chosen javafx layout for this scene
    private static Scene scene = null;   // Scene (not created until first instance)

    public static Scene open() { // Returns scene
        if (scene == null) {     // If scene not created yet
            anchor = new AnchorPane();
            scene = new Scene(anchor, 800, 600);
            create();
        }
        return scene;
    }
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
        anchor.getChildren().addAll(Graph, Back);
        anchor.getChildren().addAll(box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12, box13, box14, box15, box16);
    }
}
