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
	 * Variable moduleId zum speichern der Baugruppen ID
	 */
	private int moduleId;

	/**
	 * Aulesen der Baugruppen ID
	 * @return moduleId
	 */
	public int getModuleId() {
		return moduleId;
	}

	/**
	 * Setzen der Baugruppen ID
	 * @param moduleId Baugruppen ID
	 */
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * Konstruktor der Klasse Module (Baugruppe). Initialisiert eine neue Stï¿½ckliste.
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
	
	/**
	 * Setzen der Stückliste
	 * @param p übergebene Stückliste
	 */
	public void setPartlist(Partlist p) {
		this.partlist = p;
	}
}
