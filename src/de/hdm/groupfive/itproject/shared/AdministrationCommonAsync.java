package de.hdm.groupfive.itproject.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

/**
 * AdministrationCommonAsync ist das Intgerface um die Applikationslogik 
 * und die GUI mit einander zu verbinden, jedoch zuständig für die Hintergrundaktivitäten
 * um Aktionen ausführen zu können und während der Umsetzung weiter arbeiten zu können.
 * @author Timo Fesseler
 *
 */
public interface AdministrationCommonAsync {

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #AdministrationCommonImpl()}. Diese
	 * Methode muss f�r jede Instanz von <code>AdministrationCommonImpl</code>
	 * aufgerufen werden.
	 *
	 * @throws IllegalArgumentException
	 */
	public void init(AsyncCallback<Void> callback);

	/**
	 * Login Daten werden mit der Datenbank abgeglichen
	 */
	public void loginUser(boolean isReportGen, AsyncCallback<User> callback);

	/**
	 * Durch den Logout wird die SessionID in der DB gespeichert und der
	 * Benutzer wird ausgeloggt
	 */
	public void logoutUser(boolean isReportGen, AsyncCallback<String> callback);

	/**
	 * Erstellen eines neuen Bauteils
	 * 
	 * @param element
	 *            Bauteil das erstellt wird
	 */
	public void createElement(Element element, AsyncCallback<Element> callback);

	/**
	 * Bearbeiten eines Bauteils
	 * 
	 * @param element
	 *            Bauteil das ver�ndert werden soll
	 */
	public void editElement(Element element, AsyncCallback<Element> callback);

	/**
	 * Löschen eines Bauteils
	 * 
	 * @param element
	 *            Bauteil das gel�scht werden soll
	 */
	public void deleteElement(Element element, AsyncCallback<Void> callback);

	/**
	 * Zuweisen eines Bauteils
	 * 
	 * @param module
	 *            Baugruppe, der ein Bauteil zugewiesen werden soll
	 * @param element
	 *            Bauteil, das einer Baugruppe zugewiesen werden soll
	 * @param amount
	 *            Wie viele Bauteile zugeordnet werden sollen
	 */
	public void assignElement(Element module, Element element, int amount,
			AsyncCallback<Void> callback);

	/**
	 * Löschen einer Zuweisung eines Bauteils
	 * @param pe Parlist Eintrag mit Element, Modul
	 */
	public void deleteAssignment(PartlistEntry pe, AsyncCallback<Void> callback);
	
	/**
	 * Erstellen einer neuen Baugruppe
	 * 
	 * @param m
	 *            Baugruppe die erstellt wird
	 */
	public void createModule(Module m, AsyncCallback<Element> callback);

	/**
	 * Bearbeiten einer Baugruppe
	 * 
	 * @param module
	 *            Baugruppe die bearbeitet werden soll
	 */
	public void editModule(Module module, AsyncCallback<Module> callback);

	/**
	 * Löschen einer Baugruppe
	 * 
	 * @param module
	 *            Baugruppe die gelöscht werden soll
	 */
	public void deleteModule(Module module, AsyncCallback<Void> callback);

	/**
	 * Erstellen eines neuen Endproduktes
	 * 
	 * @param p
	 *            übergebenes Endprodukt
	 */
	public void createProduct(Product module, AsyncCallback<Element> callback);

	/**
	 * Bearbeiten des Endprodukts
	 * 
	 * @param product
	 *            übergebenes Endprodukt
	 */
	public void editProduct(Product product, AsyncCallback<Product> callback);

	/**
	 * Löschen des Endproduktes. Beachten Sie bitte auch die Anmerkungen zu
	 * throws IllegalArgumentException {@link #delete(User)},
	 * {@link #delete(Module)} und {@link #delete(Element)}.
	 * 
	 * @see #delete(User)
	 * @see #delete(Module)
	 * @see #delete(Element)
	 */
	public void deleteProduct(Product product, AsyncCallback<Void> callback);

	/**
	 * Finden eines Bauteils mittels der ID
	 * 
	 * @param id
	 *            des Bauteils
	 */
	public void findElementById(int id, boolean onlyModules, boolean onlyProducts, AsyncCallback<Partlist> callback);

	/**
	 * Finden eines Bauteils mittels des Namens
	 * 
	 * @param name
	 *            des Bauteils
	 */
	public void findElementsByName(String searchWord,
			AsyncCallback<Partlist> callback);

	/**
	 * Finden eines Bauteils mittels des Namens
	 * 
	 * @param searchWord
	 *            des Bauteils
	 * 
	 * @param maxResults
	 *            maximale Anzahle der angezeigten Ergebnisse
	 */
	public void findElementsByName(String searchWord, int maxResults,
			AsyncCallback<Partlist> callback);

	/**
	 * Finden einer Baugruppe mittels des Namens
	 * 
	 * @param name
	 *            der Baugruppe
	 */
	public void findModulesByName(String searchWord,
			AsyncCallback<Partlist> callback);

	/**
	 * Finden einer Baugruppe mittels des Namens
	 * 
	 * @param name
	 *            der Baugruppe
	 * @param maxResults
	 *            maximale Anzahle der angezeigten Ergebnisse
	 */
	public void findModulesByName(String searchWord, int maxResults,
			AsyncCallback<Partlist> callback);

	/**
	 * Finden einer Stückliste mittels der BaugruppenID
	 * 
	 * @param id
	 *            der Baugruppe
	 */
	public void findPartlistByModuleId(int id, AsyncCallback<Partlist> callback);

	/**
	 * Auslesen sämtlicher Endprodukte
	 */
	public void getAllProducts(AsyncCallback<Partlist> callback);

	/**
	 * Berechnen der benötigten Bauteile
	 * 
	 * @param partlist
	 *            ist die übergebene Stückliste, aus welcher das Material
	 *            berechnet wird
	 * @param amount Anzahl der Stücklisten
	 */
	public void calculateMaterial(Partlist partlist, int amount,
			AsyncCallback<Partlist> callback);

	/**
	 * Liefert Historie für Startseite
	 * @param callback
	 */
//	public void getLastUpdatesForHistory(AsyncCallback<ArrayList<String[]>> callback);
}
