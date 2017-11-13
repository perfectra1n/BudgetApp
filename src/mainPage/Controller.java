package mainPage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import convertExcel.convertXLSXtoCSV;
import static convertExcel.convertXLSXtoCSV.createNewDatabase;


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
    private void handleOpenClick()
    {

        //Browse function
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Excel File");
        //Here is where we define what extensions we accept
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.x*"));

        //New object chosenExcelDocs is defined
        File chosenExcelDocs = fileChooser.showOpenDialog(null);
        String filePath = chosenExcelDocs.getPath();

        //check to see if chosenExcelDocs is populated or not
        if (chosenExcelDocs != null)
        {
            File outputFile = new File("C:/output/output.csv");
           File inputFile = new File(filePath);

           directoryTextField.setText(chosenExcelDocs.getPath());
           createNewDatabase("bap.db");
           convertXLSXtoCSV.xlsx(inputFile, outputFile);
        }


    }






}
