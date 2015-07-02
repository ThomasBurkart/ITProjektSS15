package de.hdm.groupfive.itproject.shared.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Partlist extends BusinessObject {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse
	 * ben�tigt.
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
	 * Attribut list vom Typ PartlistEntry als Liste um sp�ter die Anzahl der
	 * Bauteile speichern zu k�nnen
	 */
	private ArrayList<PartlistEntry> list;

	/**
	 * Dieser Konstruktor erm�glicht es, bereits bei Instantiierung von
	 * <code>Partlist</code>-Objekten eine ArrayList mit dem Datentyp von
	 * <code>PartlistEntry</code> anzulegen.
	 * 
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
	 * Hinzuf�gen von Bauteilen zu der St�ckliste
	 * 
	 * @param element
	 *            Bauteil das hinmzugef�gt werden soll
	 * @param amount
	 *            Anzahl der Bauteile die hinzugef�gt werden sollen
	 */
	public void add(Element element, int amount, Module superModule) {
		if (element != null && amount > 0) {
			list.add(new PartlistEntry(element, amount, superModule));
		}
	}

	/**
	 * Gibt zurück ob die Partlist leer ist.
	 * 
	 * @return true/false ob Partlist leer ist
	 */
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	/**
	 * Liefert alle Bauteile in Form eines Arrays
	 * 
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
	 * 
	 * @return
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 * Auslesen der Anzahl anhand des Bauteils <code>element</code> aus der
	 * St�ckliste.
	 * 
	 * @param element
	 *            Bauteil <code>element</code> dessen Anzahl ausgelesen werden
	 *            soll.
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
	 * Auslesen des Namens der St�ckliste
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setzen des Namens der St�ckliste
	 */
	public void setName(String name) {
		this.name = name;

	}

	/**
	 * Hizuf�gen von Bauteilen in betracht auf die Anzahl
	 * @param p die �bergebene St�ckliste
	 */
	public void add(Partlist p) {
		for (PartlistEntry pe : p.getAllEntries()) {

			// Pr�fen ob das Element bereits in der totalAmount St�ckliste
			// vorhanden ist.
			if (this.contains(pe.getElement())) {
				// Element bereits in totalAmount vorhanden, deswegen nur noch
				// die Anzahl addieren.
				PartlistEntry entry = this.getPartlistEntryByIndex(this
						.indexOfElement(pe.getElement()));
				entry.setAmount(entry.getAmount() + pe.getAmount());
			} else {
				// Neues Element das noch nicht in totalAmount vorhanden ist,
				// zum ersten Mal hinzuf�gen.
				this.add(pe.getElement(), pe.getAmount());
			}
		}

	}

	/**
	 * Auslesen aller Eintr�ge einer St�ckliste
	 * @return
	 */
	public ArrayList<PartlistEntry> getAllEntries() {
		return this.list;
	}

	/**
	 * Auslesen ob ein Bauteil schon Bestandteil der St�ckliste ist
	 * @param element �bergebenes Bauteil
	 * @return
	 */
	public boolean contains(Element element) {
		for (PartlistEntry pe : this.list) {
			if (pe.getElement().equals(element)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Auslesen des St�cklisten-Eintrags anhand des Indexes
	 * @param i �bergebener Index
	 * @return
	 */
	public PartlistEntry getPartlistEntryByIndex(int i) {
		if (i < this.list.size()) {
			return this.list.get(i);
		}
		return null;
	}

	/**
	 * Auslesen, welchen Index ein Bauteil hat.
	 * @param e �bergebenes Bauteil
	 * @return
	 */
	public int indexOfElement(Element e) {
		for (int i = 0; i < this.list.size(); i++) {
			if (this.list.get(i).getElement().equals(e)) {
				return i;
			}
		}
		return -1;
	}
}
