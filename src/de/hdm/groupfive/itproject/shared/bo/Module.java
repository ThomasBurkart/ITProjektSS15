package de.hdm.groupfive.itproject.shared.bo;

public class Module extends Element {
	
	private Partlist partlist;
	
	public Module() {
		this.partlist = new Partlist();
		this.partlist.add(new Element(), 5); // ??? Warum 5? --> BSP?
	}
}
