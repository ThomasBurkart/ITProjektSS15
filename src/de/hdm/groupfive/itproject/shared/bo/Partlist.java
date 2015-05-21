package de.hdm.groupfive.itproject.shared.bo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

public class Partlist extends HashMap<Element,Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	
	private Date creationDate;
	private String name;
	//habe das Attribut in static gewandelt, damit es mit der Methode verwendtbar ist.
	private static int id;
	
	public void add(Element element, int quantity){
		Element addElement = Element.
	}
	
	public void edit (Element element){
		
	}
	
	public void delete(Element element){
		
	}
	
	public Element deleteById(Element element){
		
	}
	
	public Element getElementById(Element element){
		
	}
	/*
	 * wie bindet man das Attribut id in eine statische Methode ein?
	 */
	public static int getId(){
		
		return id;
	}
	
	public Date getCreationDate(){
		
	}
	
	public int getQuantityByElement(Element element){
		
	}
	
	public String getName(){
		
	}
	
	public void setName(String name){
		
	}
	
	
	
	public int getQuantityByElement(Element element) {
		int result = -1;
		if (this.containsKey(element)) {
			result = this.get(element);
		}
		return result;
	}

}
