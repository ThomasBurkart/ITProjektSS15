package de.hdm.groupfive.itproject.shared.bo;

import java.io.Serializable;

public class PartlistEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Attribut element als Bauteil der St�ckliste 
	 */
	private Element element;
	
	/**
	 * Anzahl der Bauteile
	 */
	private int amount;
	
	/**
	 * Standard Konstruktor der Klasse PartlistEntry. Zum erstellen eines
	 * Stücklisten-Eintrags bitte den PartlistEntry-Kontruktor mit den
	 * Parameter-Variablen verwenden.
	 */
	public PartlistEntry() {
		// Standard Konstruktor wird benötigt für Serialisierung. Nicht entfernen!
	}
	
	/**
	 * Konstruktor der Klasse PartlistEntry . Initialisiert eine neues Bauteil.
	 *  @param element 
	 *            Bauteil <code>element</code> dessen Anzahl initialisiert werden soll.
	 *  @param amount 
	 *  		  Anzahl <code>amount</code> der Bauteile die in dieser St�ckliste vorhanden sind
	 *  
	 */
	public PartlistEntry(Element element, int amount) {
		this.element = element;
		this.amount = amount;
	}

	/**
	 * Auslesen des Bauteils
	 * @return
	 */
	public Element getElement() {
		return element;
	}

	/**
	 * Setzten des Bauteils (Elements)
	 * 
	 * @param element
	 * 		Das zu setzende Bauteil<code>element</code> 
	 */
	public void setElement(Element element) {
		this.element = element;
	}
	
	/**
	 * Auslesen der Anzahl der Bauteile
	 * @return
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * Setzen der Anzahl der Bauteile
	 * 
	 * @param amount
	 * 		Anzahl<code>amount</code> des Bauteils �ndern bzw. festlegen
	 * @return 
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
}