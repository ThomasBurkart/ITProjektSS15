package de.hdm.groupfive.itproject.shared.bo;

public class Product extends Module {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse ben�tigt.
	 */
	private static final long serialVersionUID = 1L;
	
	private String salesName;
	
	private int productId;
	

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	
	

}
