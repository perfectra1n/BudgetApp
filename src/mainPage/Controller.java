package mainPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;



public class Controller {
   @FXML
    private TextField directoryTextField;

    @FXML
    void openAction(ActionEvent event) {
        handleOpenClick();
    }
    @FXML
    void closeAction(ActionEvent event) {
        handleClose();
    }

    private void handleClose() {
        System.exit(0);
    }


    @FXML
    private void handleOpenClick() {

        //Browse function
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Excel File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files \"*.xlsx or\", \"*.xls\"", "*.xlsx", "*.xls"));

        File chosenExcelDocs = fileChooser.showOpenDialog(null);

        //check to see if chosenExcelDocs is populated or not
        if (chosenExcelDocs != null) {
           directoryTextField.setText(chosenExcelDocs.getPath());
        }
    }



}
