package de.hdm.groupfive.itproject.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

@RemoteServiceRelativePath("administration")
public interface AdministrationCommon extends RemoteService {

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #AdministrationCommonImpl()}. Diese
	 * Methode muss f�r jede Instanz von <code>AdministrationCommonImpl</code>
	 * aufgerufen werden.
	 *
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	/**
	 * Login Daten werden mit der Datenbank abgeglichen
	 */
	public User loginUser() throws IllegalArgumentException;

	/**
	 * Durch den Logout wird die SessionID in der DB gespeichert und der
	 * Benutzer wird ausgeloggt
	 */
	public String logoutUser() throws IllegalArgumentException;

	/**
	 * Setzen des Benutzers
	 * 
	 * @param user
	 *            Benutzerobjekt
	 */
	public void setUser(User user) throws IllegalArgumentException;

	/**
	 * Auslesen des Benutzerobjekts/ der Benutzerdaten
	 */
	public User getUser() throws IllegalArgumentException;

	/**
	 * Erstellen eines neuen Bauteils
	 * 
	 * @param element
	 *            Bauteil das erstellt wird
	 */
	public Element createElement(Element element)
			throws IllegalArgumentException;

	/**
	 * Bearbeiten eines Bauteils
	 * 
	 * @param element
	 *            Bauteil das ver�ndert werden soll
	 */
	public Element editElement(Element element) throws IllegalArgumentException;

	/**
	 * Löschen eines Bauteils
	 * 
	 * @param element
	 *            Bauteil das gel�scht werden soll
	 */
	public void deleteElement(Element element) throws IllegalArgumentException;

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
	public void assignElement(Module module, Element element, int amount)
			throws IllegalArgumentException;

	/**
	 * Erstellen einer neuen Baugruppe
	 * 
	 * @param m
	 *            Baugruppe die erstellt wird
	 */
	public Element createModule(Module m);

	/**
	 * Bearbeiten einer Baugruppe
	 * 
	 * @param module
	 *            Baugruppe die bearbeitet werden soll
	 */
	public Module editModule(Module module) throws IllegalArgumentException;

	/**
	 * Löschen einer Baugruppe
	 * 
	 * @param module
	 *            Baugruppe die gelöscht werden soll
	 */
	public void deleteModule(Module module) throws IllegalArgumentException;

	/**
	 * Zuweisen einer Baugruppe
	 * 
	 * @param module
	 *            Baugruppe, der eine Baugruppe zugewiesen werden soll
	 * @param subModule
	 *            UnterBaugruppe, die einer Baugruppe zugewiesen werden soll
	 * @param amount
	 *            Wie viele UnterBaugruppen der Baugruppe zugeordnet werden soll
	 */
	public void assignModule(Module module, Module subModule, int amount)
			throws IllegalArgumentException;

	/**
	 * Erstellen eines neuen Endproduktes
	 * 
	 * @param p
	 *            übergebenes Endprodukt
	 */
	public Element createProduct(Product p) throws IllegalArgumentException;

	/**
	 * Bearbeiten des Endprodukts
	 * 
	 * @param product
	 *            übergebenes Endprodukt
	 */
	public Product editProduct(Product product) throws IllegalArgumentException;

	/**
	 * Löschen des Endproduktes. Beachten Sie bitte auch die Anmerkungen zu
	 * throws IllegalArgumentException {@link #delete(User)},
	 * {@link #delete(Module)} und {@link #delete(Element)}.
	 * 
	 * @see #delete(User)
	 * @see #delete(Module)
	 * @see #delete(Element)
	 */
	public void deleteProduct(Product product) throws IllegalArgumentException;

	/**
	 * Finden eines Bauteils mittels der ID
	 * 
	 * @param id
	 *            des Bauteils
	 */
	public Partlist findElementById(int id) throws IllegalArgumentException;

	/**
	 * Finden eines Bauteils mittels des Namens
	 * 
	 * @param name
	 *            des Bauteils
	 */
	public Partlist findElementsByName(String searchWord)
			throws IllegalArgumentException;

	/**
	 * Finden eines Bauteils mittels des Namens
	 * 
	 * @param searchWord
	 *            des Bauteils
	 * 
	 * @param maxResults
	 *            maximale Anzahle der angezeigten Ergebnisse
	 */
	public Partlist findElementsByName(String searchWord, int maxResults)
			throws IllegalArgumentException;

	/**
	 * Finden einer Baugruppe mittels des Namens
	 * 
	 * @param name
	 *            der Baugruppe
	 */
	public Partlist findModulesByName(String searchWord)
			throws IllegalArgumentException;

	/**
	 * Finden einer Baugruppe mittels des Namens
	 * 
	 * @param name
	 *            der Baugruppe
	 * @param maxResults
	 *            maximale Anzahle der angezeigten Ergebnisse
	 */
	public Partlist findModulesByName(String searchWord, int maxResults)
			throws IllegalArgumentException;

	/**
	 * Finden einer Stückliste mittels der BaugruppenID
	 * 
	 * @param id
	 *            der Baugruppe
	 */
	public Partlist findPartlistByModuleId(int id)
			throws IllegalArgumentException;

	/**
	 * Finden einer Stückliste mittels der StücklistenID
	 * 
	 * @param id
	 *            der Stückliste
	 */
	public Partlist findPartlistById(int id) throws IllegalArgumentException;

	/**
	 * Auslesen sämtlicher Endprodukte
	 */
	public Partlist getAllProducts() throws IllegalArgumentException;

	/**
	 * Berechnen der benötigten Bauteile
	 * 
	 * @param partlist
	 *            ist die übergebene Stückliste, aus welcher das Material
	 *            berechnet wird
	 */
	public Partlist calculateMaterial(Partlist partlist)
			throws IllegalArgumentException;

}
