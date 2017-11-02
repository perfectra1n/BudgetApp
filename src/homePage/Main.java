package homePage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Why Parent? why not "stage"
        Stage stage = FXMLLoader.load(getClass().getResource("homePage.fxml"));
        // primaryStage.setTitle("Budget Application");
        // primaryStage.setScene(new Scene(stage, 300, 275));
        stage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }
}
