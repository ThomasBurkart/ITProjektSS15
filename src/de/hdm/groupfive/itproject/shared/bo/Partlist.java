package de.hdm.groupfive.itproject.shared.bo;

import java.util.ArrayList;
import java.util.Date;

public class Partlist extends BusinessObject {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse benötigt.
	 */
	private static final long serialVersionUID = 1L;

	private Date creationDate;
	private String name;
	private int id;
	private ArrayList<PartlistEntry> list;

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

	public int getId() {

		throw new UnsupportedOperationException("Not yet implemented");

	}

	public Date getCreationDate() {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	public int getAmountByElement(Element element) {
		throw new UnsupportedOperationException("Not yet implemented");

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		throw new UnsupportedOperationException("Not yet implemented");

	}

}

class PartlistEntry {

	private Element element;
	private int amount;

	public PartlistEntry(Element element, int amount) {
		this.element = element;
		this.amount = amount;
	}

	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
