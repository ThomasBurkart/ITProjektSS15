package de.hdm.groupfive.itproject.shared.bo;

public class Product extends Module {

	/**
	 * Eindeutige SerialVersion ID. Wird zum Serialisieren der Klasse benötigt.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Verkaufsname des Endproduktes
	 */
	private String salesName;
	
	/**
	 * Produkt ID des Endproduktes
	 */
	private int productId;
	
	/**
	 * Auslesen der ID des Endproduktes
	 * @return productID ID des Endproduktes
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * Setzen der Endprodukt ID
	 * @param productId übergebene Endprodukt ID
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * Auslesen des Verkaufsnamen des Endproduktes
	 * @return salesName Verkaufsname
	 */
	public String getSalesName() {
		return salesName;
	}

	/**
	 * Setzen des Verkaufsnamen des Endproduktess
	 * @param salesName Verkaufname
	 */
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	
	

}
