package Pages.TablePage;

import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;

import java.util.List;

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
        FlowPane tabLayout = new FlowPane();
        String tablePageTabCss = tablePageTab.class.getResource(
                "/resources/css/tablePageTab.css").toExternalForm();
        tabLayout.getStylesheets().clear();
        tabLayout.getStylesheets().add(tablePageTabCss);
        itemTab.setContent(tabLayout);
        /* ******************************************************** */
    }
}
