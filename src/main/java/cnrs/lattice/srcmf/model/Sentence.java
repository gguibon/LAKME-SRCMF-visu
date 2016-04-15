package cnrs.lattice.srcmf.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a sentence.
 *
 * @author Gaël Guibon
 */
public class Sentence {

    private final List<Word> words ;
    private final IntegerProperty id;
    

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
    public Sentence() {
        
        
        // Some initial dummy data, just for convenient testing.
    	this.words = new ArrayList<Word>();
        this.id = new SimpleIntegerProperty();       
        
    }

    public List<Word> getWords() {
        return words;
    }
    
    /**
     * Return the sentence as a String, as if is it a plain text.
     * Only forms are extracted of course.
     * @return
     */
    public String getStringSentence() {
        List<String> forms = new ArrayList<String>();
    	for(Word w : words){
        	forms.add(w.getForm());
        }
    	
    	Joiner joiner = Joiner.on(" ");
    	return joiner.join(forms);
    }
    
    /**
     * return the String TSV corresponding to MALT format for this sentence
     * @return
     */
    public String toMalt() {
    	Joiner joiner = Joiner.on("\t");
    	Joiner joinerLines = Joiner.on("\n");
    	List<String> lines = new ArrayList<String>();
    	
    	for (Word w : words){
    		List<String> cols = new ArrayList<String>();
    		cols.add(w.getId());
    		cols.add(w.getForm());
    		cols.add(w.getLemma());
    		cols.add(w.getCPosTag());
    		cols.add(w.getPosTag());
    		cols.add(w.getFeats());
    		cols.add(w.getHead());
    		cols.add(w.getDeprel());
    		cols.add(w.getPHead());
    		cols.add(w.getPDeprel());
    		lines.add(joiner.join(cols));
    	}
    	
    	return joinerLines.join(lines);
    }

    public void addWord(Word word) {
        this.words.add(word);
    }

    
    public int getId() {
        return id.get();
    }

    public void setId(int score) {
        this.id.set(score);
    }

    public IntegerProperty idProperty() {
        return id;
    }
    
   
}