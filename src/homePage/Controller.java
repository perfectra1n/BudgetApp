package homePage;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;

import java.io.File;

public class Controller {
   @FXML
    private TextField directoryTextField;
  /*  public Button browse;

    public MenuItem openFile;
*/
    @FXML
    void openAction(ActionEvent event) {
        handleOpenClick();
    }

    public void handleOpenClick() {
        System.out.print("Fuck you... STFU");

        //openFile.setOnAction(new EventHandler<ActionEvent>());
        //Browse function
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Excel File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));

        File chosenExcelDocs = fileChooser.showOpenDialog(null);
        //check to see if chosenExcelDocs is populated or not
        if (chosenExcelDocs != null) {
            //directoryTextField.setText(fileChooser.getName());
            //mainStage.display(selectedFile);
           directoryTextField.setText(chosenExcelDocs.getPath());
        }

   /* public void onButtonClick() {
        System.out.print("Fuck you");

        //Browse function
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Excel File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));

        File chosenExcelDocs = fileChooser.showOpenDialog(null);

        //check to see if chosenExcelDocs is populated or not
        if (chosenExcelDocs != null) {
            //mainStage.display(selectedFile);
        }*/
    }
}
