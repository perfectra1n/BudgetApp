package Pages.TablePage;

import javafx.scene.layout.BorderPane;

class advancedSearch {
    private static BorderPane advSearch = null;

    static BorderPane open() {
        if (advSearch == null) {
            advSearch = new BorderPane();
            String tablePageCss = tablePage.class.getResource(
                    "/resources/css/advancedSearch.css").toExternalForm();
            advSearch.getStylesheets().clear();
            advSearch.getStylesheets().add(tablePageCss);
            create();
            return advSearch;
        }
        else { return advSearch; }
    }

    static void create() {

    }
    
}
