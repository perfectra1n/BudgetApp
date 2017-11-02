package homePage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Why Parent? why not "stage"
       // Stage stage = FXMLLoader.load(getClass().getResource("homePage.fxml"));
        AnchorPane root = FXMLLoader.load(getClass().getResource("homePage.fxml"));
        // primaryStage.setTitle("Budget Application");
        // primaryStage.setScene(new Scene(stage, 300, 275));
        //stage.show();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Budget Application");
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}