package Stages.MainStage;

import logger.*;
import Stages.*;
import database.DBHandle;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.io.IOException;

public class loggerScene {

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
        TextField text = new TextField();
        Button logTest = new Button("logTest");
        AnchorPane.setTopAnchor(text, 0.0);
        AnchorPane.setTopAnchor(text, 50.0);
        anchor.getChildren().addAll(text,logTest);

       /* logTest.setOnAction(e -> {
            try {
                log.test();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });*/
    }
}
