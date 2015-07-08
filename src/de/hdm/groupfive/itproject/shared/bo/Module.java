package de.hdm.groupfive.itproject.shared.bo;

/**
 * Module bietet ein BusinessObject in dem eine Grundstruktur 
 * durch (vgl. Klasse {@link Element}) für Modules als auch Products erzeugt wird.
 * @author Timo Fesseler
 *
 */
public class Module extends Element {
	
	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse ben�tigt.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Attribut partlist als St�ckliste der Baugruppe 
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
	 * Konstruktor der Klasse Module (Baugruppe). Initialisiert eine neue St�ckliste.
	 */
	public Module() {
		this.partlist = new Partlist();
	}
	
	/**
	 * Liefert St�ckliste der Baugruppe
	 * @return St�ckliste der Baugruppe
	 */
	public Partlist getPartlist() {
		return this.partlist;
	}
	
	/**
	 * Setzen der St�ckliste
	 * @param p �bergebene St�ckliste
	 */
	public void setPartlist(Partlist p) {
		this.partlist = p;
	}
}
