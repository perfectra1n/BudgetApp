package Pages.TablePage;

import javafx.beans.property.SimpleObjectProperty;

import java.util.HashMap;

public class tableDataObj {
    private String sourceTable;
    private HashMap<String, SimpleObjectProperty> objectProperties = new HashMap<>();

    public tableDataObj(String table) {
        this.sourceTable = table;
    }

    String getSourceTable() {
        return sourceTable;
    }

    public void setProperty(String name, Object value) {
        objectProperties.put(name, new SimpleObjectProperty<>(value));
    }

    SimpleObjectProperty getProperty(String name) {
        return objectProperties.get(name);
    }
}
