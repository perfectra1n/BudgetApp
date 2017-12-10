package Pages.TablePage;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

class tableDataObj {
    private SimpleObjectProperty<Object> Name;
    private HashMap<String, SimpleObjectProperty<Object>> objectProperties = new HashMap<>();

    void setProperty(String name, Object value) {
        objectProperties.put(name, new SimpleObjectProperty<>(value));
    }

    SimpleObjectProperty<Object> getProperty(String name) {
        return objectProperties.get(name);
    }

    void setName(Object name) {
        Name = new SimpleObjectProperty<>(name);
    }

    SimpleObjectProperty<Object> getName() {
        return Name;
    }

    List<String> getPropertyNames() {
        List<String> list = new ArrayList<>();
        objectProperties.forEach((n,v) -> list.add(n));
        return list;
    }
}
