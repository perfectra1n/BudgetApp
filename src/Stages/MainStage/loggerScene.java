package Stages.MainStage;

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

    private static AnchorPane anchor = null; // Chosen javafx layout for this scene
    private static Scene scene = null;   // Scene (not created until first instance)

    public static Scene open() throws IOException { // Returns scene
        if (scene == null) {     // If scene not created yet
            anchor = new AnchorPane();
            scene = new Scene(anchor, 800, 600);
            create();
        }
        return scene;
    }
    private static void create() throws IOException {
        TextArea text = new TextArea();
        text.setEditable(false);
        text.setPrefSize(800,600);


        //textarea reads bapLog.txt
        FileReader in = new FileReader("bapLog.txt");
        BufferedReader br = new BufferedReader(in);

        String s;
        while ((s = br.readLine()) != null){
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
    }
}
