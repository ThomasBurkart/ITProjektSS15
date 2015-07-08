package de.hdm.groupfive.itproject.shared.bo;

import java.io.Serializable;

/**
 * Hilfsklasse für {@link Partlist}) um Objekt mit 
 * unterschieldichen Datentypen einfügen zu können 
 * (Element/ Module/Product und int).
 * @author Timo Fesseler
 *
 */
public class PartlistEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * Attribut element als Bauteil der Stückliste 
	 */
	private Element element;

	/**
	 * Attribut superModule als übergeordnetes Modul des Elements
	 */
	private Module superModule;
	
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
	 * Konstruktor der Klasse PartlistEntry . Initialisiert eine neues Bauteil.
	 *  @param element 
	 *            Bauteil <code>element</code> dessen Anzahl initialisiert werden soll.
	 *  @param amount 
	 *  		  Anzahl <code>amount</code> der Bauteile die in dieser St�ckliste vorhanden sind
	 *  
	 */
	public PartlistEntry(Element element, int amount, Module superModule) {
		this(element, amount);
		this.superModule = superModule;
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
	 * @return Anzahl
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	

	/**
	 * Liefert das übergeordnete Modul bzw. Produkt des Elements
	 * @return Modul bzw. Produkt
	 */
	public Module getSuperModule() {
		return superModule;
	}

	/**
	 * Setzt das übergeordnete Modul bzw Produkt des Elements.
	 * @param superModule Übergeordnetes Modul
	 */
	public void setSuperModule(Module superModule) {
		this.superModule = superModule;
	}
}