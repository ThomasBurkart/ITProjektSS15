package de.hdm.groupfive.itproject.shared.bo;

import java.sql.Date;

public class Element extends BusinessObject{
	
	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse ben�tigt.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name des Bauteils
	 */
	private String name = "";
	
	/**
	 * Beschreibung des Bauteils
	 */
	private String description = "";
	
	/**
	 * Materialbeschreibung des Bauteils
	 */
	private String materialDescription = "";
	
	/**
	 * Erstellungsdatum des Bauteils
	 */
	private Date creationDate;
	
	/**
	 * Datum des letzten Zugriffs auf das Bauteil
	 */
	private Date lastUpdate;
	
	/**
	 * letzter Benutzer der auf das Bauteil zugegriffen hat
	 */
	private User lastUser;

	/**
	 * Auslesen von Name des Bauteils
	 * 
	 *@return 
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Auslesen der Beschreibung des Bauteils
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Auslesen des Erstellungsdatums des Bauteils
	 * @return
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 * Auslesen des Datums des letzten Updates
	 * @return
	 */
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	/**
	 * Auslesen des letzten Benutzers des Bauteils
	 * @return
	 */
	public User getUser() {
		return this.lastUser;
	}
	
	/**
	 * Setzten des Namens des Bauteils z.B.: Schraube
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setzten der Beschreibung des Bauteils z.B.: Bei dieser Schraube handelt es sich um eine Kreuzschlitzschraube
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Setzten der Materialbeschreibung des Bauteils z.B.: Besteht aus Kupfer oder Metall
	 */
	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}
	
	/**
	 * Liefer die Materialbeschreibung des Bauteils z.B.: Besteht aus Kupfer oder Metall
	 */
	public String getMaterialDescription() {
		return this.materialDescription;
	}
	
	/**
	 * Setzten des Erstellungsdatums 
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * Setzten des Datums des letzten Updates
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * Setzten des letzten Benutzers des Bauteils
	 */
	public void setLastUser(User lastUser) {
		this.lastUser = lastUser;
	}


	/**
	 * Erzeugen einer einfachen textuellen Repr�sentation der jeweiligen
	 * Kontoinstanz.
	 */
	@Override
	public String toString() {
		return super.toString() + " inhaber, Kunden-ID: #" + this.lastUser;
	}

	/**
	 * <p>
	 * Feststellen der <em>inhaltlichen</em> Gleichheit zweier Account-Objekte.
	 * Die Gleichheit wird in diesem Beispiel auf eine identische Kontonummer
	 * beschr�nkt.
	 * </p>
	 * <p>
	 * <b>ACHTUNG:</b> Die inhaltliche Gleichheit nicht mit dem Vergleich der
	 * <em>Identit�t</em> eines Objekts mit einem anderen verwechseln!!! Dies
	 * w�rde durch den Operator <code>==</code> bestimmt. Bei Unklarheit hierzu
	 * k�nnen Sie nocheinmal in die Definition des Sprachkerns von Java schauen.
	 * Die Methode <code>equals(...)</code> ist f�r jeden Referenzdatentyp
	 * definiert, da sie bereits in der Klasse <code>Object</code> in
	 * einfachster Form realisiert ist. Dort ist sie allerdings auf die simple
	 * Bestimmung der Gleicheit der Java-internen Objekt-ID der verglichenen
	 * Objekte beschr�nkt. In unseren eigenen Klassen k�nnen wir diese Methode
	 * �berschreiben und ihr mehr Intelligenz verleihen.
	 * </p>
	 */
	@Override
	public boolean equals(Object o) {
		/*
		 * Abfragen, ob ein Objekt ungl. NULL ist und ob ein Objekt gecastet
		 * werden kann, sind immer wichtig!
		 */
		if (o != null && o instanceof Element) {
			Element c = (Element) o;
			try {
				return super.equals(c);
			} catch (IllegalArgumentException e) {
				return false;
			}
		}
		return false;
	}
	
}
