package de.hdm.groupfive.itproject.shared.bo;

import java.util.ArrayList;
import java.util.Date;

public class Partlist extends BusinessObject {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse benötigt.
	 */	
	private static final long serialVersionUID = 1L;
<<<<<<< HEAD
	
	/**
	 * Erstellungsdatum der Stückliste
	 */	
=======

	/**
	 * Das Erstelldatum
	 */
>>>>>>> branch 'master' of https://github.com/ThomasBurkart/ITProjektSS15.git
	private Date creationDate;
	
	/**
	 * Name der Stückliste
<<<<<<< HEAD
	 */	
=======
	 */
>>>>>>> branch 'master' of https://github.com/ThomasBurkart/ITProjektSS15.git
	private String name;
	
	/**
<<<<<<< HEAD
	 * Eindeutige ID der Stückliste
=======
	 * Der Primärschlüssel der Stückliste
>>>>>>> branch 'master' of https://github.com/ThomasBurkart/ITProjektSS15.git
	 */
	private int id;
	
	/**
<<<<<<< HEAD
	 *  Attribut list vom Typ PartlistEntry als Liste um später die Anzahl der Bauteile ausgeben zu können
=======
	 * Attribut in dem die Stückliste gespeichert wird. 
>>>>>>> branch 'master' of https://github.com/ThomasBurkart/ITProjektSS15.git
	 */
	private ArrayList<PartlistEntry> list;

	/**
	 * Dieser Konstruktor ermöglicht es, bereits bei Instantiierung von <code>Partlist</code>-Objekten eine ArrayList mit dem Datentyp von <code>PartlistEntry</code> anzulegen.
	 * @see #Partlist()
	 */
	public Partlist() {
		this.list = new ArrayList<PartlistEntry>();
	}

	/**
	 * Hinzufügen von Bauteilen zu der Stückliste
	 * 
	 * @param element
	 *            Bauteil das hinmzugefügt werden soll
	 * @param amount
	 *            Anzahl der Bauteile die hinzugefügt werden sollen
	 */
	public void add(Element element, int amount) {
		if (element != null && amount > 0) {
			list.add(new PartlistEntry(element, amount));
		}
	}

	/**
	 * Berbeitet Bauteil aus der Stückliste
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
	 * Löscht Bauteil aus der Stückliste
	 * 
	 * @param element
	 *            Bauteil das gelöscht werden soll.
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
	 * Löscht Bauteil anhand der Element Id aus der Stückliste
	 * 
	 * @param elementId
	 *            Id des Bauteils das gelöscht werden soll.
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
	 * Auslesen des Bauteils anhand der Element ID aus der Stückliste.
	 * 
	 * @param elementId
	 *            ID des Bauteils das ausgelesen werden soll.
	 * @return Das Bauteil <code>Element</code> wird zurückgegeben
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
	 * Auslesen der Anzahl anhand des Bauteils <code>element</code> aus der Stückliste.
	 * 
	 * @param element
	 *            Bauteil <code>element</code> dessen Anzahl ausgelesen werden soll.
	 * @return Die Anzahl <code>amount</code> wird zurückgegeben
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

class PartlistEntry {
	
	/**
	 * Attribut element als Bauteil der Stückliste 
	 */
	private Element element;
	
	/**
	 * Anzahl der Bauteile
	 */
	private int amount;
	
	/**
	 * Konstruktor der Klasse PartlistEntry . Initialisiert eine neues Bauteil.
	 *  @param element 
	 *            Bauteil <code>element</code> dessen Anzahl initialisiert werden soll.
	 *  @param amount 
	 *  		  Anzahl <code>amount</code> der Bauteile die in dieser Stückliste vorhanden sind
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
	 * 		Anzahl<code>amount</code> des Bauteils ändern bzw. festlegen
	 * @return 
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
