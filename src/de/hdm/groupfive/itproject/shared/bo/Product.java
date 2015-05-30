package de.hdm.groupfive.itproject.shared.bo;

public class Product extends Module {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse benötigt.
	 */
	private static final long serialVersionUID = 1L;
	
	private String salesName;

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	
	

}
