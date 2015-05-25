package de.hdm.groupfive.itproject.shared.bo;

import java.sql.Date;

public class Element extends BusinessObject{
	
	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse benötigt.
	 */
	private static final long serialVersionUID = 1L;

	private String name = "";
	private String description = "";
	private String materialDescription = "";
	private Date creationDate;
	private Date lastUpdate;
	private User lastUser;

	
	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getMaterialdescription() {
		return this.materialDescription;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public User getUser() {
		return this.lastUser;
	}
	
	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public User getLastUser() {
		return lastUser;
	}

	public void setLastUser(User lastUser) {
		this.lastUser = lastUser;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * Erzeugen einer einfachen textuellen Repräsentation der jeweiligen
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
	 * beschränkt.
	 * </p>
	 * <p>
	 * <b>ACHTUNG:</b> Die inhaltliche Gleichheit nicht mit dem Vergleich der
	 * <em>Identität</em> eines Objekts mit einem anderen verwechseln!!! Dies
	 * würde durch den Operator <code>==</code> bestimmt. Bei Unklarheit hierzu
	 * können Sie nocheinmal in die Definition des Sprachkerns von Java schauen.
	 * Die Methode <code>equals(...)</code> ist für jeden Referenzdatentyp
	 * definiert, da sie bereits in der Klasse <code>Object</code> in
	 * einfachster Form realisiert ist. Dort ist sie allerdings auf die simple
	 * Bestimmung der Gleicheit der Java-internen Objekt-ID der verglichenen
	 * Objekte beschränkt. In unseren eigenen Klassen können wir diese Methode
	 * überschreiben und ihr mehr Intelligenz verleihen.
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
