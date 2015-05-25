package de.hdm.groupfive.itproject.shared.bo;

public class Module extends Element {
	
	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse benötigt.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attribut partlist als Stückliste der Baugruppe 
	 */
	private Partlist partlist;

	/**
	 * Konstruktor der Klasse Module (Baugruppe). Initialisiert eine neue Stückliste.
	 */
	public Module() {
		this.partlist = new Partlist();
	}
	
	/**
	 * Liefert Stückliste der Baugruppe
	 * @return Stückliste der Baugruppe
	 */
	public Partlist getPartlist() {
		return this.partlist;
	}
}
