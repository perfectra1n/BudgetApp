package homePage;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;

public class Controller
{
    public Button browse;

    public void onButtonClick()
    {
        System.out.print("Fuck you");

        //Browse function
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Excel File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.xls"));

        File chosenExcelDocs = fileChooser.showOpenDialog(null);

        //check to see if chosenExcelDocs is populated or not
        if(chosenExcelDocs != null)
        {
            //mainStage.display(selectedFile);
        }

    }
}
