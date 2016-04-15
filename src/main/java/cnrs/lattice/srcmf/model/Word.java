package cnrs.lattice.srcmf.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for a word.
 *
 * @author Gaël Guibon
 */
public class Word {

    private final StringProperty id;
    private final StringProperty form;
    private final StringProperty lemma;
    private final StringProperty cpostag;
    private final StringProperty postag;
    private final StringProperty feats;
    private final StringProperty head;
    private final StringProperty deprel;
    private final StringProperty phead;
    private final StringProperty pdeprel;
    

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
    public Word() {
        
        
        // Some initial dummy data, just for convenient testing.
        this.id = new SimpleStringProperty("none");
        this.form = new SimpleStringProperty("none");
        this.lemma = new SimpleStringProperty("none");
        this.cpostag = new SimpleStringProperty("none");
        this.postag = new SimpleStringProperty("none");
        this.feats = new SimpleStringProperty("none");
        this.head = new SimpleStringProperty("none");
        this.phead = new SimpleStringProperty("none");
        this.deprel = new SimpleStringProperty("none");
        this.pdeprel = new SimpleStringProperty("none");
              
        
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }    
    
    public String getForm() {
        return form.get();
    }

    public void setForm(String form) {
        this.form.set(form);
    }

    public StringProperty formProperty() {
        return form;
    }
    
    public String getLemma() {
        return lemma.get();
    }

    public void setLemma(String lemma) {
        this.lemma.set(lemma);
    }

    public StringProperty lemmaProperty() {
        return lemma;
    }
    
    public String getCPosTag() {
        return cpostag.get();
    }

    public void setCPosTag(String cpostag) {
        this.cpostag.set(cpostag);
    }

    public StringProperty cpostagProperty() {
        return cpostag;
    }
   
    public String getPosTag() {
        return postag.get();
    }

    public void setPosTag(String postag) {
        this.postag.set(postag);
    }

    public StringProperty postagProperty() {
        return postag;
    }
    
    public String getFeats() {
        return feats.get();
    }

    public void setFeats(String feats) {
        this.feats.set(feats);
    }

    public StringProperty featsProperty() {
        return feats;
    }
    
    public String getHead() {
        return head.get();
    }

    public void setHead(String head) {
        this.head.set(head);
    }

    public StringProperty headProperty() {
        return head;
    }
    
    public String getDeprel() {
        return deprel.get();
    }

    public void setDeprel(String deprel) {
        this.deprel.set(deprel);
    }

    public StringProperty deprelProperty() {
        return deprel;
    }
    
    public String getPHead() {
        return phead.get();
    }

    public void setPHead(String phead) {
        this.phead.set(phead);
    }

    public StringProperty pheadProperty() {
        return phead;
    }
    
    public String getPDeprel() {
        return pdeprel.get();
    }

    public void setPDeprel(String pdeprel) {
        this.pdeprel.set(pdeprel);
    }

    public StringProperty pdeprelProperty() {
        return pdeprel;
    }
}