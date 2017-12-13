package Pages.GraphPage;

import Pages.mainWin;
import database.dbHandler;
import database.dbOperations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static java.lang.String.format;

public class graphPage {
    private static List<CheckBox> checkBoxList = new ArrayList<>();
    private static BorderPane graphLayout = null;

    // Opens this scene
    public static void open() {
        // Layout not created until first call
        if (graphLayout == null) {
            graphLayout = new BorderPane();
            graphLayout.setId("GraphPage");
                            /*          Set the stylesheet graphPage.css to this scene        */
            String graphPageCss = graphPage.class.getResource("/resources/css/graphPage.css").toExternalForm();
            graphLayout.getStylesheets().clear();
            graphLayout.getStylesheets().add(graphPageCss);
            mainWin.changeCenter(graphLayout);
            create();
        }
        else { mainWin.changeCenter(graphLayout); }
    }

    // Scene layout and events
    private static void create() {
        VBox leftPane = new VBox();
        leftPane.setId("leftPane");

//-----------------------------------------------------------------//
        //-------------- DROP BOX "X" -----------------------//
        ComboBox<String> comboBoxX = new ComboBox<>();
        comboBoxX.setPrefSize(150.0, 25.0); //Sets the default drop list size

        //----------------POPULATE X OPTIONS-----------------//
        comboBoxX.getItems().addAll("Bar Graph", "Pie Graph", "Line Chart"); //Populates the actual drop down menu
        comboBoxX.setValue("Bar Graph");
//-----------------------------------------------------------------//

//-----------------------------------------------------------------//
        //-------------- DROP BOX "Y" -----------------------//
        ComboBox<String> comboBoxY = new ComboBox<>();
        comboBoxY.setPrefSize(150.0, 25.0);                   //Sets the default drop list size
        //----------------POPULATE Y OPTIONS-----------------//
        comboBoxY.getItems().addAll("Vertical", "Horizontal"); // Populates the actual drop down menu
        comboBoxY.setValue("Vertical");

//-----------------------------------------------------------------//
        //----------------CHECK BOXES-----------------//
        // This is the list of the boxlist and their formatting.//

        VBox boxlist = new VBox();
        ResultSet r = dbOperations.queryReturnResult("SELECT \"Dept ID - Dept Description\" FROM 'College of E&CS';");
        //List<CheckBox> checkBoxList = new ArrayList<>();
        //CheckBox box = new CheckBox();
        try {
            r.next();
            while (!r.isClosed()) {
                String str = r.getString(1);
                str = str.substring(str.indexOf('-') + 2, str.length());
                CheckBox box = new CheckBox(str);
                // box = new CheckBox(str);
                box.setSelected(true);
                checkBoxList.add(box);
                boxlist.getChildren().add(box);
                r.next();
            }

        } catch (SQLException e) { e.printStackTrace(); }
//--------------------------test------------------------------------//
        int i = 0;
        while(i < checkBoxList.size()){
            if (checkBoxList.get(i).isSelected()){
                checkBoxList.get(i).setOnAction(e ->{
                    if (comboBoxX.getValue().equals(("Bar Graph"))) {
                            if (comboBoxY.getValue().equals("Vertical")) {
                                createVerticalGraph();
                            } else if (comboBoxY.getValue().equals("Horizontal")) {
                                createHorizontalGraph();
                            }
                    }
                    if (comboBoxX.getValue().equals(("Pie Graph"))) {
                        createPieGraph();
                    }
                    if (comboBoxX.getValue().equals(("Line Chart"))) {
                        createLineChart();
                    }
                });
                i++;
            }

       /*     else if (!checkBoxList.get(i).isSelected()){
                checkBoxList.get(i).setOnAction(e ->{
                    if (comboBoxY.getValue().equals("Vertical")) {
                        createVerticalGraph();
                    }
                    else if (comboBoxY.getValue().equals("Horizontal")){
                        createHorizontalGraph();
                    }
                });
                i++;
            }*/
        }

        //--------------------------------------------------//
        createVerticalGraph();
        leftPane.getChildren().addAll(comboBoxX, comboBoxY, boxlist);
        graphLayout.setLeft(leftPane);


        //--------------------------- EVENTS ---------------------------
        comboBoxX.setOnAction(e -> {
            if (comboBoxX.getValue().equals("Bar Graph")) {
                //leftPane.getChildren().addAll(comboBoxY,comboBoxX);
               // leftPane.getChildren().removeAll(comboBoxY,comboBoxX);
                graphLayout.setCenter(null);
                if (comboBoxY.getValue().equals("Vertical")) {
                    createVerticalGraph();
                }
                else if (comboBoxY.getValue().equals("Horizontal")) {
                    createHorizontalGraph();
                }
            }
            else if (comboBoxX.getValue().equals("Pie Graph")) {
                if (leftPane.getChildren().contains(comboBoxY)){
                    leftPane.getChildren().removeAll(comboBoxY);
                }
               // leftPane.getChildren().addAll(comboBoxY,comboBoxX);
              //  leftPane.getChildren().removeAll(comboBoxY);
                graphLayout.setCenter(null);
                createPieGraph();
            }
            else if (comboBoxX.getValue().equals("Line Chart")) {
                if (leftPane.getChildren().contains(comboBoxY)){
                    leftPane.getChildren().removeAll(comboBoxY);
                }
                //leftPane.getChildren().addAll(comboBoxY,comboBoxX);
                //leftPane.getChildren().removeAll(comboBoxY,comboBoxX);
                graphLayout.setCenter(null);
                createLineChart();
            }
        });

        comboBoxY.setOnAction(e -> {
            if (comboBoxY.getValue().equals("Vertical")) {
                createVerticalGraph();
            }
            else if (comboBoxY.getValue().equals("Horizontal")) {
                createHorizontalGraph();
            }
        });
        //--------------------------------------------------------------
    }

    // Creates a vertical bar graph
    private static void createVerticalGraph() {
        CategoryAxis xAxis = new CategoryAxis();                        // X-Axis
        NumberAxis yAxis = new NumberAxis();                            // Y-Axis
        BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);    // Create Bar Chart
        bc.setTitle("Cost by Department");                             // Title of Chart
        xAxis.setLabel("Department");                                   // Title of X-Axis
        yAxis.setLabel("Total Cost");                                   // Title of Y-Axis
        XYChart.Series<String, Number> series = new XYChart.Series<>(); // The Vertical "Bars"
        ResultSet depts = dbOperations.queryReturnResult("SELECT \"TBL_NAME\" FROM 'importedTables';");

        int i = 0;

        try {
            depts.next();
            String str = format("SELECT \"Dept ID - Dept Description\" FROM '%s';", depts.getString(1));
            ResultSet names = dbOperations.queryReturnResult(str);
            depts.next(); names.next();
            while (i < checkBoxList.size()) {
                if (checkBoxList.get(i).isSelected()) {
                    str = format("SELECT \"Purchase Cost\" FROM '%s';", depts.getString(1));
                    ResultSet costs = dbOperations.queryReturnResult(str);
                    double totalCost = getTotalCost(costs);
                    String deptName = names.getString(1);
                    deptName = deptName.substring(deptName.indexOf('-') + 2, deptName.length());
                    series.getData().add(new XYChart.Data<>(deptName, totalCost));
                    depts.next(); names.next();

                    i++;
                }
                else if (!checkBoxList.get(i).isSelected()) {
                    depts.next(); names.next();
                    i++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //---------------------------------------------------------------------------------//
        bc.getData().add(series);
        bc.setLegendVisible(false);
        graphLayout.setCenter(bc);
    }

    // Creates a horizontal bar graph
    private static void createHorizontalGraph() {
        CategoryAxis yAxis = new CategoryAxis();                        // X-Axis
        NumberAxis xAxis = new NumberAxis();                            // Y-Axis
        BarChart<Number, String> bc = new BarChart<>(xAxis, yAxis);     // Create Bar Chart
        bc.setTitle("Cost by Department");                              // Title of Chart
        xAxis.setLabel("Total Cost");                                   // Title of X-Axis
        yAxis.setLabel("Department");                                   // Title of Y-Axis
        XYChart.Series<Number, String> series = new XYChart.Series<>(); // The "Bars"

        ResultSet depts = dbOperations.queryReturnResult("SELECT \"TBL_NAME\" FROM 'importedTables';");
        int i = 0;

        try {
            depts.next();
            String str = format("SELECT \"Dept ID - Dept Description\" FROM '%s';", depts.getString(1));
            ResultSet names = dbOperations.queryReturnResult(str);
            depts.next(); names.next();
            while (i < checkBoxList.size()) {
                if (checkBoxList.get(i).isSelected()) {
                    str = format("SELECT \"Purchase Cost\" FROM '%s';", depts.getString(1));
                    ResultSet costs = dbOperations.queryReturnResult(str);
                    double totalCost = getTotalCost(costs);
                    String deptName = names.getString(1);
                    deptName = deptName.substring(deptName.indexOf('-') + 2, deptName.length());
                    series.getData().add(new XYChart.Data<>(totalCost, deptName));
                    depts.next(); names.next();

                    i++;
                }
                else if (!checkBoxList.get(i).isSelected()) {
                    depts.next(); names.next();
                    i++;
                }
            }
            bc.getData().add(series);
            bc.setLegendVisible(false);
            graphLayout.setCenter(bc);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private static void createPieGraph() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        int i = 0;
        ResultSet depts = dbOperations.queryReturnResult("SELECT \"TBL_NAME\" FROM 'importedTables';");
        try {
            depts.next();
            String str = format("SELECT \"Dept ID - Dept Description\" FROM '%s';", depts.getString(1));
            ResultSet names = dbOperations.queryReturnResult(str);
            depts.next(); names.next();
            while (i < checkBoxList.size()) {
                if (checkBoxList.get(i).isSelected()) {
                    str = format("SELECT \"Purchase Cost\" FROM '%s';", depts.getString(1));
                    ResultSet costs = dbOperations.queryReturnResult(str);
                    double totalCost = getTotalCost(costs);
                    String deptName = names.getString(1);
                    deptName = deptName.substring(deptName.indexOf('-') + 2, deptName.length());
                    pieChartData.add(new PieChart.Data(deptName, totalCost));
                    depts.next();
                    names.next();
                    i++;
                }
                else if (!checkBoxList.get(i).isSelected()) {
                    depts.next(); names.next();
                    i++;
                }
            }
            PieChart pie = new PieChart(pieChartData);
            pie.setTitle("Cost by Department");
            graphLayout.setCenter(pie);

           // System.out.println(pie.legendSideProperty());
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static void createLineChart(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Department");
        yAxis.setLabel("Total Cost");
        LineChart<String,Number> lc = new LineChart<String,Number>(xAxis,yAxis);
        lc.setTitle("Cost by Department");

        XYChart.Series<String,Number> series = new XYChart.Series<>();

        ResultSet depts = dbOperations.queryReturnResult("SELECT \"TBL_NAME\" FROM 'importedTables';");

        int i = 0;

        try {
            depts.next();
            String str = format("SELECT \"Dept ID - Dept Description\" FROM '%s';", depts.getString(1));
            ResultSet names = dbOperations.queryReturnResult(str);
            depts.next(); names.next();
            while (i < checkBoxList.size()) {
                if (checkBoxList.get(i).isSelected()) {
                    str = format("SELECT \"Purchase Cost\" FROM '%s';", depts.getString(1));
                    ResultSet costs = dbOperations.queryReturnResult(str);
                    double totalCost = getTotalCost(costs);
                    String deptName = names.getString(1);
                    deptName = deptName.substring(deptName.indexOf('-') + 2, deptName.length());
                    series.getData().add(new XYChart.Data<>(deptName, totalCost));
                    depts.next(); names.next();

                    i++;
                }
                else if (!checkBoxList.get(i).isSelected()) {
                    depts.next(); names.next();
                    i++;
                }
            }
            lc.getData().add(series);
            lc.setLegendVisible(false);
            graphLayout.setCenter(lc);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Generates total sum given a result set
    private static double getTotalCost(ResultSet costs) {
        double sum = 0.0;
        try {
            costs.next();
            while (!costs.isClosed()) {
                sum += costs.getDouble(1);
                costs.next();
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return sum;
    }
}


