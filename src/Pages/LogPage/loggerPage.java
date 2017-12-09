package Pages.LogPage;

import java.io.*;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.io.IOException;

public class loggerPage {
    private static Stage logWindow = null;
    private static AnchorPane anchor = null;

    // Opens this scene
    public static void open() {
        // Layout not created until first call
        if (logWindow == null) {
            logWindow = new Stage();
            logWindow.setTitle("Budget Application Project Log");
            anchor = new AnchorPane();
            logWindow.setScene(new Scene(anchor));
        }
        logWindow.show();
        create();
    }

    private static void create() {
        try {
            TextArea text = new TextArea();
            text.setEditable(false);
            text.prefHeightProperty().bind(anchor.getScene().heightProperty());
            text.prefWidthProperty().bind(anchor.getScene().widthProperty());


            //textarea reads bapLog.txt
            FileReader in = new FileReader("src/logger/bapLog.txt");
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
