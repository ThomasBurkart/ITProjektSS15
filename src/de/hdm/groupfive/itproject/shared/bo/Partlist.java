package de.hdm.groupfive.itproject.shared.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Partlist extends BusinessObject {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse
	 * benï¿½tigt.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Erstellungsdatum der Stï¿½ckliste
	 */
	private Date creationDate;

	/**
	 * Name der Stï¿½ckliste
	 */
	private String name;

	/**
	 * Der Primï¿½rschlï¿½ssel der Stï¿½ckliste
	 */
	private int id;

	/**
	 * Attribut list vom Typ PartlistEntry als Liste um spï¿½ter die Anzahl der
	 * Bauteile speichern zu kï¿½nnen
	 */
	private ArrayList<PartlistEntry> list;

	/**
	 * Dieser Konstruktor ermï¿½glicht es, bereits bei Instantiierung von
	 * <code>Partlist</code>-Objekten eine ArrayList mit dem Datentyp von
	 * <code>PartlistEntry</code> anzulegen.
	 * 
	 * @see #Partlist()
	 */
	public Partlist() {
		this.list = new ArrayList<PartlistEntry>();
	}

	/**
	 * Hinzufï¿½gen von Bauteilen zu der Stï¿½ckliste
	 * 
	 * @param element
	 *            Bauteil das hinmzugefï¿½gt werden soll
	 * @param amount
	 *            Anzahl der Bauteile die hinzugefï¿½gt werden sollen
	 */
	public void add(Element element, int amount) {
		if (element != null && amount > 0) {
			list.add(new PartlistEntry(element, amount));
		}
	}

	/**
	 * Gibt zurÃ¼ck ob die Partlist leer ist.
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
	 * Berbeitet Bauteil aus der Stï¿½ckliste
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
	 * Lï¿½scht Bauteil aus der Stï¿½ckliste
	 * 
	 * @param element
	 *            Bauteil das gelï¿½scht werden soll.
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
	 * Lï¿½scht Bauteil anhand der Element Id aus der Stï¿½ckliste
	 * 
	 * @param elementId
	 *            Id des Bauteils das gelï¿½scht werden soll.
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
	 * Auslesen des Bauteils anhand der Element ID aus der Stï¿½ckliste.
	 * 
	 * @param elementId
	 *            ID des Bauteils das ausgelesen werden soll.
	 * @return Das Bauteil <code>Element</code> wird zurï¿½ckgegeben
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
	 * Stï¿½ckliste.
	 * 
	 * @param element
	 *            Bauteil <code>element</code> dessen Anzahl ausgelesen werden
	 *            soll.
	 * @return Die Anzahl <code>amount</code> wird zurï¿½ckgegeben
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
	 * Auslesen des Namens der Stückliste
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setzen des Namens der Stückliste
	 */
	public void setName(String name) {
		this.name = name;

	}

	/**
	 * Hizufügen von Bauteilen in betracht auf die Anzahl
	 * @param p die übergebene Stückliste
	 */
	public void add(Partlist p) {
		for (PartlistEntry pe : p.getAllEntries()) {

			// Prüfen ob das Element bereits in der totalAmount Stückliste
			// vorhanden ist.
			if (this.contains(pe.getElement())) {
				// Element bereits in totalAmount vorhanden, deswegen nur noch
				// die Anzahl addieren.
				PartlistEntry entry = this.getPartlistEntryByIndex(this
						.indexOfElement(pe.getElement()));
				entry.setAmount(entry.getAmount() + pe.getAmount());
			} else {
				// Neues Element das noch nicht in totalAmount vorhanden ist,
				// zum ersten Mal hinzufügen.
				this.add(pe.getElement(), pe.getAmount());
			}
		}

	}

	/**
	 * Auslesen aller Einträge einer Stückliste
	 * @return
	 */
	public ArrayList<PartlistEntry> getAllEntries() {
		return this.list;
	}

	/**
	 * Auslesen ob ein Bauteil schon Bestandteil der Stückliste ist
	 * @param element übergebenes Bauteil
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
	 * Auslesen des Stücklisten-Eintrags anhand des Indexes
	 * @param i übergebener Index
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
	 * @param e übergebenes Bauteil
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
