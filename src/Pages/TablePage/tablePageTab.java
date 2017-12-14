package Pages.TablePage;

import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

class tablePageTab {

    private final Tab itemTab;
    private final tableDataObj tableItem;

    tablePageTab(tableDataObj inData, String title) {
        this.tableItem = inData;
        this.itemTab = new Tab(title);
        createTabLayout();
    }

    Tab getItemTab() { return itemTab; }

    private void createTabLayout() {
        /*------------------ Tab Layout Settings -------------------*/
        BorderPane tabLayout = new BorderPane();
        String tablePageTabCss = tablePageTab.class.getResource(
                "/resources/css/tablePageTab.css").toExternalForm();
        tabLayout.getStylesheets().clear();
        tabLayout.getStylesheets().add(tablePageTabCss);
        itemTab.setContent(tabLayout);
        /* ******************************************************** */
        FlowPane leftSide = new FlowPane();
        leftSide.setHgap(18);

        tableItem.getAllProperties().forEach((n,v) -> {
            leftSide.getChildren().add(new VBox(
                    new Label(n), new TextField(v.get().toString())));
        });

        Button saveButton = new Button("Save");
        saveButton.setId("SaveButton");

        BorderPane rightSide = new BorderPane();
        rightSide.setId("RightSide");
        ScrollPane rightCenter = new ScrollPane();

        rightSide.setTop(saveButton);
        rightSide.setCenter(rightCenter);

        tabLayout.setLeft(leftSide);
        tabLayout.setCenter(rightSide);
    }
}
