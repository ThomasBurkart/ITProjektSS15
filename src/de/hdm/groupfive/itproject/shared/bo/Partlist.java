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
		Element.
	}
	
	public void edit (Element element){
		
	}
	
	public void delete(Element element){
		
	}
	
	public Element deleteById(Element element){
		
	}
	
	public Element getElementById(Element element){
		
	}
	
	public static int getId(){
		
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
