package cnrs.lattice.srcmf.beans;

import java.util.HashMap;

public class MaltFormat {

	private int id = 0;
	private int form = 1;
	private int lemma = 2;
	private int cpostag = 3;
	private int postag = 4;
	private int feats = 5;
	private int head = 6;
	private int deprel = 7;
	private int phead = 8;
	private int pdeprel = 9;
	public static HashMap<String,Integer> map = new HashMap<String,Integer>();
	
	public MaltFormat(){
		int id = 0;
		int form = 1;
		int lemma = 2;
		int cpostag = 3;
		int postag = 4;
		int feats = 5;
		int head = 6;
		int deprel = 7;
		int phead = 8;
		int pdeprel = 9;
		HashMap<String,Integer> map;
	}
	
	/*
	 * Setters
	 */
	public void setId (int id){
		this.id = id;
	}
	
	public void setForm (int form){
		this.form = form;
	}
	
	public void setLemma (int id){
		this.lemma = id;
	}
	public void setCPostag (int id){
		this.cpostag = id;
	}
	public void setPostag (int id){
		this.postag = id;
	}
	public void setFeats (int id){
		this.feats = id;
	}
	public void setHead (int id){
		this.head = id;
	}
	public void setDeprel (int id){
		this.deprel = id;
	}
	public void setPHead (int id){
		this.phead = id;
	}
	public void setPDeprel (int id){
		this.pdeprel = id;
	}
	
	/*
	 * Getters
	 */
	public int getId(){
		return this.id;
	}
	public int getForm(){
		return this.form;
	}
	public int getLemma(){
		return this.lemma;
	}
	public int getCPostag(){
		return this.cpostag;
	}
	public int getPostag(){
		return this.postag;
	}
	public int getFeats(){
		return this.feats;
	}
	public int getHead(){
		return this.head;
	}
	public int getDeprel(){
		return this.deprel;
	}
	public int getPHead(){
		return this.phead;
	}
	public int getPDeprel(){
		return this.pdeprel;
	}
}
