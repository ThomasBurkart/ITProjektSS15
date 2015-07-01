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
	 * Methode muss für jede Instanz von <code>AdministrationCommonImpl</code>
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
	public Element createElement(Element element) throws IllegalArgumentException;

	/**
	 * Bearbeiten eines Bauteils
	 * 
	 * @param element
	 *            Bauteil das verändert werden soll
	 */
	public Element editElement(Element element) throws IllegalArgumentException;

	/**
	 * Löschen eines Bauteils
	 * 
	 * @param element
	 *            Bauteil das gelöscht werden soll
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
	public void assignElement(Module module, Element element, int amount) throws IllegalArgumentException;

	
	public Element createModule(Module module);

	public Module editModule(Module module) throws IllegalArgumentException;

	public void deleteModule(Module module) throws IllegalArgumentException;

	public void assignModule(Module module, Module subModule, int amount) throws IllegalArgumentException;

	public Element createProduct(Product module) throws IllegalArgumentException;

	public Product editProduct(Product product) throws IllegalArgumentException;

	public void deleteProduct(Product product) throws IllegalArgumentException;

	/**
	 * Finden eines Bauteils mittels der ID
	 * 
	 * @param id
	 *            des Bauteils
	 */
	public Partlist findElementById(int id) throws IllegalArgumentException;

	/**
	 * Finden eines Bauteils mittels der Erstellers
	 * 
	 * @param creator
	 *            Ersteller, der das Bauteil erstellt hat
	 */
	public Partlist findElementsByCreator(User creator) throws IllegalArgumentException;

	/**
	 * Finden eines Bauteils mittels des Namens
	 * 
	 * @param name
	 *            des Bauteils
	 */
	public Partlist findElementsByName(String searchWord) throws IllegalArgumentException;
	
	public Partlist findElementsByName(String searchWord, int maxResults) throws IllegalArgumentException;

	public Partlist findModulesByName(String searchWord) throws IllegalArgumentException;
	
	public Partlist findModulesByName(String searchWord, int maxResults) throws IllegalArgumentException;

	public Partlist findPartlistByModuleId(int id) throws IllegalArgumentException;

	public Partlist findPartlistById(int id) throws IllegalArgumentException;

	public Vector<Product> getAllProducts() throws IllegalArgumentException;

	public Partlist calculateMaterial(Partlist partlist) throws IllegalArgumentException;

}
