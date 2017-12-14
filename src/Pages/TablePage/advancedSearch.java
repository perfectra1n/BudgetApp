package Pages.TablePage;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class advancedSearch {
    private static BorderPane advSearch = null;
    private static tableClass tableObj;

    static BorderPane open(tableClass t) {
        if (advSearch == null) {
            advSearch = new BorderPane();
            tableObj = t;
            String tablePageCss = tablePage.class.getResource(
                    "/resources/css/advancedSearch.css").toExternalForm();
            advSearch.getStylesheets().clear();
            advSearch.getStylesheets().add(tablePageCss);
            create();
            return advSearch;
        }
        else { return advSearch; }
    }

    private static void create() {
        GridPane searchArea = new GridPane();
        searchArea.setStyle("-fx-padding: 10");
        searchArea.setHgap(50); searchArea.setVgap(30);
        Button searchButton = new Button("Search");
        Button resetButton = new Button("Reset All Fields");
        searchArea.add(searchButton,0,0);
        searchArea.add(resetButton,0,1);
        advSearch.setTop(searchArea);

        FlowPane searchFields = new FlowPane();
        searchFields.setStyle("-fx-padding: 10");
        searchFields.setHgap(50); searchFields.setVgap(30);
        advSearch.setCenter(searchFields);
        List<Pair<String, TextField>> searchItems = new ArrayList<>();

        tableObj.getTableColumns().forEach(tc -> {
            Label lbl = new Label(tc.getText());
            TextField txt = new TextField();
            txt.setMinWidth(200); txt.setMinHeight(27);
            searchItems.add(new Pair<>(lbl.getText(), txt));
            searchFields.getChildren().add(new VBox(lbl, txt));
        });

        /*--------------------------- EVENTS ---------------------------*/
        searchButton.setOnAction(e -> tableObj.getActiveData().setPredicate(obj -> {

            return false;
        }));

        resetButton.setOnAction(e -> searchItems.forEach(pair -> {
            pair.getValue().clear();
        }));
        /* ************************************************************ */
    }
}
