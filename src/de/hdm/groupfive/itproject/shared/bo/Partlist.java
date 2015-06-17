package de.hdm.groupfive.itproject.shared.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Partlist extends BusinessObject {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse ben�tigt.
	 */	
	private static final long serialVersionUID = 1L;

	/**
	 * Erstellungsdatum der St�ckliste
	 */	
	private Date creationDate;
	
	/**
	 * Name der St�ckliste
	 */	
	private String name;
	
	/**
	 * Der Prim�rschl�ssel der St�ckliste
	 */
	private int id;
	
	/**
	 *  Attribut list vom Typ PartlistEntry als Liste um sp�ter die Anzahl der Bauteile speichern zu k�nnen
	 */
	private ArrayList<PartlistEntry> list;

	/**
	 * Dieser Konstruktor erm�glicht es, bereits bei Instantiierung von <code>Partlist</code>-Objekten eine ArrayList mit dem Datentyp von <code>PartlistEntry</code> anzulegen.
	 * @see #Partlist()
	 */
	public Partlist() {
		this.list = new ArrayList<PartlistEntry>();
	}

	/**
	 * Hinzuf�gen von Bauteilen zu der St�ckliste
	 * 
	 * @param element
	 *            Bauteil das hinmzugef�gt werden soll
	 * @param amount
	 *            Anzahl der Bauteile die hinzugef�gt werden sollen
	 */
	public void add(Element element, int amount) {
		if (element != null && amount > 0) {
			list.add(new PartlistEntry(element, amount));
		}
	}
	
	/**
	 * Liefert alle Bauteile in Form eines Arrays
	 * @return Array mit allen Bauteilen
	 */
	public List<Element> getAllElements() {
		List<Element> result = new ArrayList<Element>();
		for (PartlistEntry pe : this.list) {
			result.add(pe.getElement());
		}
		return result;
	}

	/**
	 * Berbeitet Bauteil aus der St�ckliste
	 * 
	 * @param element
	 *            Bauteil das bearbeitet werden soll
	 */
	public void edit(Element element) {
		for (PartlistEntry entry : list) {
			if (entry.getElement().getId() == element.getId()) {
				entry.setElement(element);
				break;
			}
		}

	}

	/**
	 * L�scht Bauteil aus der St�ckliste
	 * 
	 * @param element
	 *            Bauteil das gel�scht werden soll.
	 */
	public void delete(Element element) {
		for (PartlistEntry entry : list) {
			if (entry.getElement().getId() == element.getId()) {
				list.remove(entry);
				break;
			}
		}
	}

	/**
	 * L�scht Bauteil anhand der Element Id aus der St�ckliste
	 * 
	 * @param elementId
	 *            Id des Bauteils das gel�scht werden soll.
	 */
	public void deleteById(int elementId) {
		for (PartlistEntry entry : list) {
			if (entry.getElement().getId() == elementId) {
				list.remove(entry);
				break;
			}
		}

	}

	/**
	 * Auslesen des Bauteils anhand der Element ID aus der St�ckliste.
	 * 
	 * @param elementId
	 *            ID des Bauteils das ausgelesen werden soll.
	 * @return Das Bauteil <code>Element</code> wird zur�ckgegeben
	 */
	// WIE GIBT MAN EIN ATTRIBUT AUS EINER ANDEREN KLASSE IN EINER BESCHREIUBUNG AN?
	public Element getElementById(int elementId) {
		Element result = null;
		for (PartlistEntry entry : list) {
			if (entry.getElement().getId() == elementId) {
				result = entry.getElement();
				break;
			}
		}
		return result;
	}

	/**
	 * Auslesen der ID
	 */
	public int getId() {
		return this.id;

	}

	/**
	 * Auslesen der Erstellungsdatums
	 * @return 
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}
	/**
	 * Auslesen der Anzahl anhand des Bauteils <code>element</code> aus der St�ckliste.
	 * 
	 * @param element
	 *            Bauteil <code>element</code> dessen Anzahl ausgelesen werden soll.
	 * @return Die Anzahl <code>amount</code> wird zur�ckgegeben
	 */
	public int getAmountByElement(Element element) {
		int result = 0;
		for (PartlistEntry entry : list) {
			if (entry.getElement() == element) {
				result = entry.getAmount();
				break;
			}
		}
		return result;

	}
	/**
	 * Auslesen des Namens
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	/**
	   * Setzen des Namens
	   */
	public void setName(String name) {
		this.name = name;

	}

}

class PartlistEntry implements Serializable {

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
