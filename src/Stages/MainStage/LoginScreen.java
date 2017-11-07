package Stages.MainStage;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class LoginScreen {

    public static Scene create(Stage MainWindow) {

        GridPane grid = new GridPane();
        Scene scene = new Scene(grid, 800, 600);

        // grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        // TEMPORARY CHANGE SCENE BUTTON
        Button tempBut = new Button("Click Me!");
        grid.add(tempBut, 43, 40);

        // Close Window
        Button ExitBut = new Button("Close Window");
        grid.add(ExitBut, 43, 43);

        //---------------------------------- EVENTS ----------------------------------

        //----------------------------------------------------------------------------

        // return scene to be displayed
        return scene;
    }

    //public static Scene

}