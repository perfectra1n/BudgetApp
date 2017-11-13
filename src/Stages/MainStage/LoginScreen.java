// LoginScreen.java
// This is a test class
//

package Stages.MainStage;

import Stages.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class LoginScreen {

    private static GridPane grid = null;
    private static Scene scene = null;

    public static Scene open() {
        if (scene == null) {
            grid = new GridPane();
            scene = new Scene(grid, 800, 600);
            create();
        }
        return scene;
    }

    private static void create() {

        // grid.setAlignment(Pos.CENTER);
        grid.setHgap(10); grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        // Request File
        Label label1 = new Label("Hi! this is the LoginScreen scene.");
        label1.setFont(Font.font(20));
        grid.add(label1, 0, 0, 20, 1);

        // TEMPORARY CHANGE SCENE BUTTON
        Button tempBut = new Button("Click Me to go back!");
        grid.add(tempBut, 10, 20);

        // Close Window
        Button ExitBut = new Button("Close Window");
        grid.add(ExitBut, 43, 43);

        //---------------------------------- EVENTS ----------------------------------

        // Temp change scene button
        tempBut.setOnAction(e -> Interconnector.changeScene(testScene.open()));

        // Exit Button
        ExitBut.setOnAction(e -> Interconnector.closeWindow());
        //----------------------------------------------------------------------------
    }
}