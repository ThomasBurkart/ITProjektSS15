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
	private int id;
	
	public void add(Element element, int quantity){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public void edit (Element element){
		
	}
	
	public void delete(Element element){
		
	}
	
	public Element deleteById(Element element){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public Element getElementById(Element element){
		throw new UnsupportedOperationException("Not yet implemented");
	}
	/*
	 * wie bindet man das Attribut id in eine statische Methode ein?
	 */
	public static int getId(){
		
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	public Date getCreationDate(){
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public String getName(){
		throw new UnsupportedOperationException("Not yet implemented");
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
