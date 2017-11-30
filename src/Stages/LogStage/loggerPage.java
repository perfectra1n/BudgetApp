package Stages.LogStage;

import java.io.*;

import Stages.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.IOException;

public class loggerPage {
    private static AnchorPane anchor = null;

    // Opens this scene
    public static void open() {
        // Layout not created until first call
        Stage logWindow = new Stage();
        logWindow.setTitle("Budget Application Project Log");

        if (anchor == null) { anchor = new AnchorPane(); }
        PageConnector.changeRoot(anchor);
        create();
    }

    private static void create() {
        try {
            TextArea text = new TextArea();
            text.setEditable(false);
            text.prefHeightProperty().bind(anchor.getScene().heightProperty());
            text.prefWidthProperty().bind(anchor.getScene().widthProperty());


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
