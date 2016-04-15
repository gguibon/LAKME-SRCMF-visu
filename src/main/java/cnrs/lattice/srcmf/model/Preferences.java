package cnrs.lattice.srcmf.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for preferences.
 *
 * @author Gaël Guibon
 */
public class Preferences {

    private final StringProperty pathFileMalt1;
    private final StringProperty pathFileMalt2;
    private final StringProperty errorFile1;
    private final IntegerProperty ID;
    private final IntegerProperty FORM;
    private final IntegerProperty LEMMA;
    private final IntegerProperty CPOSTAG;
    private final IntegerProperty POSTAG;
    private final IntegerProperty FEATS;
    private final IntegerProperty HEAD;
    private final IntegerProperty DEPREL;
    private final IntegerProperty PHEAD;
    private final IntegerProperty PDEPREL;
    

//    /**
//     * Default constructor.
//     */
//    public Preferences() {
//        this();
//    }

    /**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Preferences() {
        
        
        // Some initial dummy data, just for convenient testing.
        this.pathFileMalt1 = new SimpleStringProperty("a file");
        this.pathFileMalt2 = new SimpleStringProperty("a file");
        this.errorFile1 = new SimpleStringProperty("a file");
        this.ID = new SimpleIntegerProperty(0);
        this.FORM = new SimpleIntegerProperty(1);
        this.LEMMA = new SimpleIntegerProperty(2);
        this.CPOSTAG = new SimpleIntegerProperty(3);
        this.POSTAG = new SimpleIntegerProperty(4);
        this.FEATS = new SimpleIntegerProperty(5);
        this.HEAD = new SimpleIntegerProperty(6);
        this.DEPREL = new SimpleIntegerProperty(7);
        this.PHEAD = new SimpleIntegerProperty(8);
        this.PDEPREL = new SimpleIntegerProperty(9);
        
        
    }

    public String getFirstName() {
        return pathFileMalt1.get();
    }

    public void setFirstName(String firstName) {
        this.pathFileMalt1.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return pathFileMalt1;
    }

    public String getLastName() {
        return pathFileMalt2.get();
    }

    public void setLastName(String lastName) {
        this.pathFileMalt2.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return pathFileMalt2;
    }

    public String getStreet() {
        return errorFile1.get();
    }

    public void setStreet(String street) {
        this.errorFile1.set(street);
    }

    public StringProperty streetProperty() {
        return errorFile1;
    }

    public int getId() {
        return ID.get();
    }

    public void setId(int id) {
        this.ID.set(id);
    }

    public IntegerProperty idProperty() {
        return ID;
    }
    
    public int getForm() {
        return FORM.get();
    }

    public void setForm(int form) {
        this.FORM.set(form);
    }

    public IntegerProperty formProperty() {
        return FORM;
    }

    public int getLemma() {
        return LEMMA.get();
    }

    public void setLemma(int lemma) {
        this.LEMMA.set(lemma);
    }

    public IntegerProperty lemmaProperty() {
        return LEMMA;
    }
    
    public int getCPosTag() {
        return CPOSTAG.get();
    }

    public void setCPosTag(int cpostag) {
        this.CPOSTAG.set(cpostag);
    }

    public IntegerProperty cPosTagProperty() {
        return CPOSTAG;
    }
    
    public int getPosTag() {
        return POSTAG.get();
    }

    public void setPosTag(int postag) {
        this.POSTAG.set(postag);
    }

    public IntegerProperty posTagProperty() {
        return POSTAG;
    }
    
    public int getFeats() {
        return FEATS.get();
    }

    public void setFeats(int feats) {
        this.FEATS.set(feats);
    }

    public IntegerProperty featsProperty() {
        return FEATS;
    }
    
    public int getHead() {
        return HEAD.get();
    }

    public void setHead(int head) {
        this.HEAD.set(head);
    }

    public IntegerProperty headProperty() {
        return HEAD;
    }
    
    public int getDeprel() {
        return DEPREL.get();
    }

    public void setDeprel(int deprel) {
        this.DEPREL.set(deprel);
    }

    public IntegerProperty deprelProperty() {
        return DEPREL;
    }
    
    public int getPHead() {
        return PHEAD.get();
    }

    public void setPHead(int phead) {
        this.PHEAD.set(phead);
    }

    public IntegerProperty pHeadProperty() {
        return PHEAD;
    }
    
    public int getPDeprel() {
        return PDEPREL.get();
    }

    public void setPDeprel(int pdeprel) {
        this.PDEPREL.set(pdeprel);
    }

    public IntegerProperty pDeprelProperty() {
        return PDEPREL;
    }
}