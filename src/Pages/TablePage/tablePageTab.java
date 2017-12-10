package Pages.TablePage;

import javafx.scene.control.Tab;

import java.util.List;

public class tablePageTab {
    private final Tab itemTab;
    private final tableDataObj tableItem;

    tablePageTab(tableDataObj inData) {
        this.tableItem = inData;
        this.itemTab = new Tab(inData.getName().get().toString());
        createTabLayout();
    }

    public Tab getItemTab() { return itemTab; }

    private void createTabLayout() {
        List<String> properties = tableItem.getPropertyNames();
        System.out.println(properties);
    }
}
