package de.hdm.groupfive.itproject.shared.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdm.groupfive.itproject.server.db.ElementMapper;

public class Partlist extends BusinessObject {

	/**
	 * 
	 * @param element
	 * @return
	 */

	private Date creationDate;
	private String name;
	private int id;
	private ArrayList<PartlistEntry> list;
	
	public Partlist() {
		this.list = new ArrayList<PartlistEntry>();
	}

	public void add(Element element, int amount) {
		if (element != null && amount > 0) {
			list.add(new PartlistEntry(element, amount));
		}
	}

	public void edit(Element element) {
		for(PartlistEntry entry : list) {
			if (entry.getElement().getId() == element.getId()) {
				entry.setElement(element);
			}
		}
		
	}

	public void delete(Element element) {

	}

	public Element deleteById(Element element) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public Element getElementById(Element element) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public int getId() {

		throw new UnsupportedOperationException("Not yet implemented");

	}

	public Date getCreationDate() {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public int getAmountByElement(Element element) {
		throw new UnsupportedOperationException("Not yet implemented");
		
	}
	
	public String getName() {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public void setName(String name) {
		throw new UnsupportedOperationException("Not yet implemented");

	}

	

}

class PartlistEntry {
	
	private Element element;
	private int amount;

	public PartlistEntry (Element element, int amount){
		this.element = element;
		this.amount = amount;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
