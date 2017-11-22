package Stages.MainStage;

import Stages.Interconnector;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    //\\\DROP BOX FOR X Y PLANE\\\//
    private static void create() {
        ComboBox comboBoxX = new ComboBox();
        ComboBox comboBoxY = new ComboBox();
        anchor.getChildren().addAll(comboBoxX, comboBoxY);


//-----------------------------------------------------------------//
        //-------------- DROP BOX "X" -----------------------//
        comboBoxX.setPrefSize(150.0, 40.0);   //Sets the default drop list size
        AnchorPane.setTopAnchor(comboBoxX, 50.0);           //Sets the positioning of the dropbox
        AnchorPane.setLeftAnchor(comboBoxX, 50.0);          // ^


        //----------------POPULATE X OPTIONS-----------------//
        comboBoxX.setPromptText("Select X Axis");                                       //Populates the dropbox with a prompt so user know what to do
        comboBoxX.getItems().addAll("option 1","option 2","option 3");        //Populates the actual drop down menu
//-----------------------------------------------------------------//




//-----------------------------------------------------------------//
        //-------------- DROP BOX "Y" -----------------------//
        comboBoxY.setPrefSize(150.0, 40.0);  //Sets the default drop list size
        AnchorPane.setTopAnchor(comboBoxY, 50.0);          //Sets the positioning of the dropbox
        AnchorPane.setRightAnchor(comboBoxY, 50.0);        //^

        //----------------POPULATE Y OPTIONS-----------------//
        comboBoxY.setPromptText("Select Y Axis");                                      //Populates the dropbox with a prompt so user know what to do
        comboBoxY.getItems().addAll("option 1","option 2","option 3");       //Populates the actual drop down menu
//-----------------------------------------------------------------//
    }
}
