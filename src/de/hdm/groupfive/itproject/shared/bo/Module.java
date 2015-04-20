package de.hdm.groupfive.itproject.shared.bo;

public class Module extends Element {
	
	private Partlist partlist;
	
	public Module() {
		this.partlist = new Partlist();
		this.partlist.put(new Element(), 5);
	}
}
