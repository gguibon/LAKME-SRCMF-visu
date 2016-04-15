package cnrs.lattice.srcmf.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a tag.
 *
 * @author Gaël Guibon
 */
public class Tag {

    private final StringProperty name;
    private final DoubleProperty score;
    

//    /**
//     * Default constructor.
//     */
//    public Score() {
//        this();
//    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Tag() {
        
        
        // Some initial dummy data, just for convenient testing.
        this.name = new SimpleStringProperty("a tag");
        this.score = new SimpleDoubleProperty(0.0);       
        
    }

    public String getName() {
        return name.get();
    }

    public void setName(String firstName) {
        this.name.set(firstName);
    }

    public StringProperty nameProperty() {
        return name;
    }

    

    public double getScore() {
        return score.get();
    }

    public void setScore(double score) {
        this.score.set(score);
    }

    public DoubleProperty scoreProperty() {
        return score;
    }
    
   
}