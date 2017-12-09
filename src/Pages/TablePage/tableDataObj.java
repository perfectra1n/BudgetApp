package Pages.TablePage;

import javafx.beans.property.SimpleObjectProperty;
import java.util.HashMap;

class tableDataObj {
    private HashMap<String, SimpleObjectProperty> objectProperties = new HashMap<>();

    void setProperties(String name, Object value) {
        objectProperties.put(name, new SimpleObjectProperty<>(value));
    }

    SimpleObjectProperty getProperties(String name) {
        return objectProperties.get(name);
    }
}
