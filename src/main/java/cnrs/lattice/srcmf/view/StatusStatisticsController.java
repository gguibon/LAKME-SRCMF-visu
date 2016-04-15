package cnrs.lattice.srcmf.view;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import cnrs.lattice.srcmf.model.Tag;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * The controller for the birthday statistics view.
 * 
 * @author Marco Jakob
 */
public class StatusStatisticsController {

	// Reference to the main application.
    private StartApp mainApp;
	
    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;

    private ObservableList<String> tagNames = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {        
    }

    /**
     * Sets the persons to show the statistics for.
     * 
     * @param tag
     */
    public void setTagData(List<Tag> tag) {
    	
    	HashMap<String, Double> mapCount = new HashMap<String, Double>();
    	for (Tag p : tag){
    		String name = p.getName();
    		
    		if(!tagNames.contains(name)){
    			tagNames.add(name);
    		}
    		 
    		mapCount.put(p.getName(), p.getScore());
//    		mapCount.putIfAbsent(name, (Integer)0);
//	    	mapCount.computeIfPresent(name, (k,v) -> v+1);

    	}
    	
    	// Assign the team names as categories for the horizontal axis.
        xAxis.setCategories(tagNames);
        yAxis.setUpperBound(100.00);
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        
        for (Entry<String, Double> entry : mapCount.entrySet()) {
        	series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().add(series);
    }
}