package Stages.LogStage;

import java.nio.Buffer;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.*;
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
    // Scene (not created until first instance)
    private static Scene scene = null;

    // Opens this scene
    public static void open() {
        // If scene not created yet, create it
        if (scene == null) {create();}
        Interconnector.changeScene(scene);
    }

    private static void create() {
        /*
        From Allen : Sorry, removed the exception throws from your functions, not sure why you had that
        Idk if there was a reason for it, but I think it's better if your exceptions were handled
        within your java class rather than handled from the calling class, so for now I just placed
        a try/catch block around your code. I took out the exception throw from your other class too.
        Also I didn't place a button on your scene, if you want to be able to go back to test scene,
        just make a button and have it call testScene.open()
        */
        AnchorPane anchor = new AnchorPane();
        scene = new Scene(anchor);

        try {
            TextArea text = new TextArea();
            text.setEditable(false);
            text.setPrefSize(Interconnector.getStageWidth(), Interconnector.getStageHeight());


            //textarea reads bapLog.txt
            FileReader in = new FileReader("bapLog.txt");
            BufferedReader br = new BufferedReader(in);

            String s;
            while ((s = br.readLine()) != null) {
                text.appendText(s + "\n");
            }
            br.close();

            Button logTest = new Button("logTest");
            AnchorPane.setTopAnchor(text, 0.0);
            //AnchorPane.setTopAnchor(text, 50.0);
            //anchor.getChildren().addAll(text,logTest);
            anchor.getChildren().addAll(text);
       /* logTest.setOnAction(e -> {
            try {
                log.test();
            } catch (IOException e1a) {
                e1.printStackTrace();
            }
        });*/

        } catch (IOException e) {e.printStackTrace();}
    }
}
