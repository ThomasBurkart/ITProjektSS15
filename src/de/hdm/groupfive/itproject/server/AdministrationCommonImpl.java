package de.hdm.groupfive.itproject.server;

import java.sql.SQLException;
import java.util.Vector;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.groupfive.itproject.server.db.ElementMapper;
import de.hdm.groupfive.itproject.server.db.ModuleMapper;
import de.hdm.groupfive.itproject.server.db.PartlistMapper;
import de.hdm.groupfive.itproject.server.db.ProductMapper;
import de.hdm.groupfive.itproject.server.db.UserMapper;
import de.hdm.groupfive.itproject.shared.AdministrationCommon;
import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

/**
 * <p>
 * Implementierungsklasse des Interface <code>AdministationCommon</code>. Diese
 * Klasse ist <em>die</em> Klasse, die s�mtliche Applikationslogik (oder engl.
 * Business Logic) aggregiert. Sie ist wie eine Spinne, die s�mtliche
 * Zusammenh�nge in ihrem Netz (in unserem Fall die Daten der Applikation)
 * �berblickt und f�r einen geordneten Ablauf und dauerhafte Konsistenz der
 * Daten und Abl�ufe sorgt.
 * </p>
 * <p>
 * Die Applikationslogik findet sich in den Methoden dieser Klasse. Jede dieser
 * Methoden kann als <em>Transaction Script</em> bezeichnet werden. Dieser Name
 * l�sst schon vermuten, dass hier analog zu Datenbanktransaktion pro
 * Transaktion gleiche mehrere Teilaktionen durchgef�hrt werden, die das
 * System von einem konsistenten Zustand in einen anderen, auch wieder
 * konsistenten Zustand �berf�hren. Wenn dies zwischenzeitig scheitern
 * sollte, dann ist das jeweilige Transaction Script daf�r verwantwortlich,
 * eine Fehlerbehandlung durchzuf�hren.
 * </p>
 * <p>
 * Diese Klasse steht mit einer Reihe weiterer Datentypen in Verbindung. Dies
 * sind:
 * <ol>
 * <li>{@link AdministrationCommon}: Dies ist das <em>lokale</em> - also
 * Server-seitige - Interface, das die im System zur Verf�gung gestellten
 * Funktionen deklariert.</li>
 * <li>{@link AdministrationCommonAsync}: <code>AdministartionCommonImpl</code>
 * und <code>AdministrationCommon</code> bilden nur die Server-seitige Sicht der
 * Applikationslogik ab. Diese basiert vollst�ndig auf synchronen
 * Funktionsaufrufen. Wir m�ssen jedoch in der Lage sein, Client-seitige
 * asynchrone Aufrufe zu bedienen. Dies bedingt ein weiteres Interface, das in
 * der Regel genauso benannt wird, wie das synchrone Interface, jedoch mit dem
 * zus�tzlichen Suffix "Async". Es steht nur mittelbar mit dieser Klasse in
 * Verbindung. Die Erstellung und Pflege der Async Interfaces wird durch das
 * Google Plugin semiautomatisch unterst�tzt. Weitere Informationen unter
 * {@link AdministrationCommonAsync}.</li>
 * <li> {@link RemoteServiceServlet}: Jede Server-seitig instantiierbare und
 * Client-seitig �ber GWT RPC nutzbare Klasse muss die Klasse
 * <code>RemoteServiceServlet</code> implementieren. Sie legt die funktionale
 * Basis f�r die Anbindung von <code>AdministrationCommonImpl</code> an die
 * Runtime des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie geh�ren der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Zuweilen kommen "kreative" Zeitgenossen auf die
 * Idee, in diesen Mappern auch Applikationslogik zu realisieren. Siehe dazu
 * auch die Hinweise in {@link #delete(User)} Einzig nachvollziehbares Argument
 * f�r einen solchen Ansatz ist die Steigerung der Performance umfangreicher
 * Datenbankoperationen. Doch auch dieses Argument zieht nur dann, wenn wirklich
 * gro�e Datenmengen zu handhaben sind. In einem solchen Fall w�rde man
 * jedoch eine entsprechend erweiterte Architektur realisieren, die wiederum
 * s�mtliche Applikationslogik in der Applikationsschicht isolieren w�rde.
 * Also, keine Applikationslogik in die Mapper-Klassen "stecken" sondern dies
 * auf die Applikationsschicht konzentrieren!
 * </p>
 * <p>
 * Beachten Sie, dass s�mtliche Methoden, die mittels GWT RPC aufgerufen
 * werden k�nnen ein <code>throws IllegalArgumentException</code> in der
 * Methodendeklaration aufweisen. Diese Methoden d�rfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions k�nnen
 * z.B. Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
 * transportiert und dort systematisch in einem Catch-Block abgearbeitet werden.
 * </p>
 */
@SuppressWarnings("serial")
public class AdministrationCommonImpl extends RemoteServiceServlet implements
		AdministrationCommon {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse
	 * ben�tigt.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Aktuell angemeldeter Benutzer
	 */
	private User currentUser = null;

	/**
	 * Referenz auf den DatenbankMapper, der Benutzerobjekte mit der Datenbank
	 * abgleicht.
	 */
	private UserMapper userMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Bauteilobjekte mit der Datenbank
	 * abgleicht.
	 */
	private ElementMapper elementMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Baugruppenobjekte mit der Datenbank
	 * abgleicht.
	 */
	private ModuleMapper moduleMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der St�cklistenobjekte mit der
	 * Datenbank abgleicht.
	 */
	private PartlistMapper partlistMapper = null;

	/**
	 * Referenz auf den DatenbankMapper, der Endproduktobjekte mit der Datenbank
	 * abgleicht.
	 */
	private ProductMapper productMapper = null;

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Initialisierung
	 * ***************************************
	 * ************************************
	 */
	/**
	 * No-Argument Konstruktor
	 */
	public AdministrationCommonImpl() throws IllegalArgumentException {

	}

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor {@link #AdministrationCommonImpl()}. Diese
	 * Methode muss f�r jede Instanz von <code>AdministrationCommonImpl</code>
	 * aufgerufen werden.
	 */
	public void init() throws IllegalArgumentException {
		/*
		 * Ganz wesentlich ist, dass die Administration einen vollst�ndigen
		 * Satz von Mappern besitzt, mit deren Hilfe sie dann mit der Datenbank
		 * kommunizieren kann.
		 */
		this.userMapper = UserMapper.getUserMapper();
		this.elementMapper = ElementMapper.getElementMapper();
		this.moduleMapper = ModuleMapper.getModuleMapper();
		this.productMapper = ProductMapper.getProductMapper();
		this.partlistMapper = PartlistMapper.getPartlistMapper();
	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Initialisierung
	 * *****************************************
	 * **********************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden f�r User-Objekte
	 * *****************************
	 * **********************************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Initialisierung
	 * *****************************************
	 * **********************************
	 */
	
	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden f�r User-Objekte
	 * *****************************
	 * **********************************************
	 */
	
	
	/**
	 * �bergibt die Registrierungsdaten an die Datenbank weiter
	 * 
	 * @param email
	 *            des Benutzers
	 * @param password
	 *            des Benutzerkontos
	 */
	@Override
	public User registerUser(String email, String password)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Login Daten werden mit der Datenbank abgeglichen
	 * 
	 * @param email
	 *            des Benutzers
	 * @param password
	 *            des Benutzerkontos
	 */
	@Override
	public User loginUser()
			throws IllegalArgumentException {
		com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory.getUserService();
		
		if (userService.isUserLoggedIn()) {
			com.google.appengine.api.users.User user = userService.getCurrentUser();
			
			User u = new User();
			u.setEmail(user.getEmail());
			u.setUserName(user.getNickname());
			u.setUserId(user.getUserId());
			u.setFederatedIdentity(user.getFederatedIdentity());
			u.setIsLoggedIn(true);
			this.currentUser = u;
			return u;
		} else {
			User u = new User();
			u.setIsLoggedIn(false);
			u.setLoginUrl(userService.createLoginURL(ServerSettings.PAGE_URL));
			this.currentUser = null;
			return u;
		}
	}

	/**
	 * Durch den Logout wird die SessionID in der DB gespeichert und der
	 * Benutzer wird ausgeloggt
	 */
	@Override
	public String logoutUser() throws IllegalArgumentException {
		com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory.getUserService();
		
		if (userService.isUserLoggedIn()) {
			com.google.appengine.api.users.User user = userService.getCurrentUser();
			this.currentUser = null;
			return userService.createLogoutURL(ServerSettings.PAGE_URL);
		}
		return "http://www.google.de";
	}

	/**
	 * Auslesen des Benutzer-Mappers (UserMappers)
	 */
	public UserMapper getUserMapper() throws IllegalArgumentException {
		return this.userMapper;
	}

	/**
	 * Setzen des Benutzers
	 * 
	 * @param user
	 *            Benutzerobjekt
	 */
	@Override
	public void setUser(User user) throws IllegalArgumentException {
		this.currentUser = user;
	}

	/**
	 * Auslesen des Benutzobjekts/ der Benutzerdaten
	 */
	@Override
	public User getUser() throws IllegalArgumentException {
		return this.currentUser;
	}

	/*
	 * *************************************************
	 * ABSCHNITT, Ende: Methoden f�r User-Objekte
	 * *************************************************
	 */

	/*
	 * *************************************************
	 * ABSCHNITT, Beginn: Methoden f�r Element-Objekte
	 * *************************************************
	 */

	/**
	 * Auslesen des Bauteil-Mapper (ElementMappers)
	 */
	public ElementMapper getElementMapper() throws IllegalArgumentException {
		return this.elementMapper;
	}

	/**
	 * Erstellen eines neuen Bauteils
	 * 
	 * @param name
	 *            des Bauteils
	 * @param element
	 *            Bauteil das erstellt wird
	 */
	@Override
	public Element createElement(Element element)
			throws IllegalArgumentException {
		if (element == null) {
			throw new IllegalArgumentException("Übergebenes Objekt an createElement() ist NULL");
		}
		
		try {
			if (element instanceof Product) {
				return this.getProductMapper().insert((Product)element);
			} else if (element instanceof Module) {
				return this.getModuleMapper().insert((Module)element);
			} else {
				return this.getElementMapper().insert(element);
			}
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Bearbeiten eines Bauteils
	 * 
	 * @param element
	 *            Bauteil das ver�ndert werden soll
	 * @return Das ge�nderte Bauteil wird an den Bauteil-Mapper (ElementMapper)
	 *         �bergeben
	 */
	@Override
	public Element editElement(Element element) throws IllegalArgumentException {
		try {
			return this.getElementMapper().update(element);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * L�schen eines Bauteils
	 * 
	 * @param element
	 *            Bauteil das gel�scht werden soll
	 * @return das zu l�schende Bauteil wird an den Bauteil-Mapper
	 *         (ElementMapper) �bergeben zum l�schen
	 */
	@Override
	public void deleteElement(Element element) throws IllegalArgumentException {
		try {
			this.getElementMapper().delete(element);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Finden eines Bauteils mittels der ID
	 * 
	 * @param id
	 *            des Bauteils
	 * @return Element wird durch die findById()-Methode gesucht
	 */
	@Override
	public Partlist findElementById(int id) throws IllegalArgumentException {
		try {
			return this.getElementMapper().findById(id);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/*
	 * **********************************************************
	 */
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
	@Override
	public void assignElement(Module module, Element element, int amount)
			throws IllegalArgumentException {

		if (module.getPartlist().contains(element)) {
			for (PartlistEntry pe : module.getPartlist().getAllEntries()) {
				if (pe.getElement().equals(element)) {
					pe.setAmount(pe.getAmount() + amount);

					break;
				}
			}

		} else {
			module.getPartlist().add(element, amount);
		}
		// TODO: speichern
	}

	/**
	 * Finden eines Bauteils mittels der Erstellers
	 * 
	 * @param creator
	 *            Ersteller, der das Bauteil erstellt hat
	 * @return
	 */
	@Override
	public Partlist findElementsByCreator(User creator)
			throws IllegalArgumentException {
		
//		Vector<User> result = new Vector<User>();
//		if (creator.equals("leer")) {
//			// Nur für Tests
//		} else {
//			Module m1 = new Module();
//			m1.setName(name);
//			m1.setId(0);
//
//			Element e1 = new Element();
//			e1.setName(name + " element");
//			e1.setId(1);
//			e1.setDescription("blablabla");
//
//			m1.getPartlist().add(e1, 1);
//
//			result.add(m1);
//		}
//		return result;
		return null;
	}

	/*
	 * **********************************************************
	 */
	/**
	 * Finden eines Bauteils mittels des Namens
	 * 
	 * @param name
	 *            des Bauteils
	 * @return result
	 */
	@Override
	public Partlist findElementsByName(String searchWord)
			throws IllegalArgumentException {
		return this.findElementsByName(searchWord, 1000);
	}
	
	@Override
	public Partlist findElementsByName(String searchWord, int maxResults)
			throws IllegalArgumentException {
		try {
			return this.elementMapper.findByName(searchWord, maxResults);
		} catch(IllegalArgumentException ex) {
			throw ex;
		} catch(SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}
	
	@Override
	public Partlist findModulesByName(String searchWord)
			throws IllegalArgumentException {
		return this.findModulesByName(searchWord, 1000);
	}
	
	@Override
	public Partlist findModulesByName(String searchWord, int maxResults)
			throws IllegalArgumentException {
		try {
			return this.moduleMapper.findByName(searchWord, maxResults);
		} catch(IllegalArgumentException ex) {
			throw ex;
		} catch(SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	/*
	 * *************************************************
	 * ABSCHNITT, Ende: Methoden f�r Element-Objekte
	 * *************************************************
	 */

	/*
	 * *************************************************
	 * ABSCHNITT, Beginn: Methoden f�r Modul-Objekte
	 * *************************************************
	 */

	/**
	 * Auslesen des Baugruppen-Mappers (moduleMapper)
	 */
	public ModuleMapper getModuleMapper() throws IllegalArgumentException {
		return this.moduleMapper;
	}

	/**
	 * Erstellen einer neuen Baugruppe
	 * 
	 * @param name
	 *            dert Baugruppe
	 * @param module
	 *            Baugruppe die erstellt wird
	 */
	@Override
	public Module createModule(Module m)
			throws IllegalArgumentException {
		try {
			return this.getModuleMapper().insert(m);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Bearbeiten einer Baugruppe
	 * 
	 * @param module
	 *            Baugruppe die bearbeitet werden soll
	 * @return Die bearbeitete Baugruppe wird an den Baugruppen-Mapper
	 *         (ModuleMapper) �bergeben
	 */
	@Override
	public Module editModule(Module module) throws IllegalArgumentException {
		try {
			return this.getModuleMapper().update(module);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/*
	 * **********************************************************
	 */
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
	@Override
	public void assignModule(Module module, Module subModule, int amount)
			throws IllegalArgumentException {
		if (module.getPartlist().contains(subModule)) {
			for (PartlistEntry pe : module.getPartlist().getAllEntries()) {
				if (pe.getElement().equals(subModule)) {
					pe.setAmount(pe.getAmount() + amount);

					break;
				}
			}

		} else {
			module.getPartlist().add(subModule, amount);
		}

	}

	/**
	 * L�schen einer Baugruppe
	 * 
	 * @param module
	 *            Baugruppe die gel�scht werden soll
	 * @return die zul�schende Baugruppe wird an den Baugruppen-Mapper
	 *         (ModuleMapper) �bergeben zum l�schen
	 */
	@Override
	public void deleteModule(Module module) throws IllegalArgumentException {
		if (module != null) {
			try {
				this.getModuleMapper().delete(module);
			} catch (SQLException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		} else {
			throw new IllegalArgumentException(
					"Übergebenes Modul Objekt ist NULL");
		}
	}

	/*
	 * **************************************************
	 * ABSCHNITT, Ende: Methoden f�r Modul-Objekte
	 * **************************************************
	 */

	/*
	 * ***************************************************
	 * ABSCHNITT, Beginn: Methoden f�r Partlist-Objekte
	 * ***************************************************
	 */

	/**
	 * Auslesen des St�cklisten-Mappers (PartlistMapper)
	 */
	public PartlistMapper getPartlistMapper() throws IllegalArgumentException {
		return this.partlistMapper;
	}

	// public Partlist findPartlistByModuleName(String name) throws
	// IllegalArgumentException {
	// try {
	// return this.getModuleMapper().findByName(name).getPartlist();
	// } catch (SQLException e) {
	// throw new IllegalArgumentException(e.getMessage());
	// }
	// }

	/**
	 * Finden einer St�ckliste mittels der BaugruppenID
	 * 
	 * @param id
	 *            der Baugruppe
	 */
	@Override
	public Partlist findPartlistByModuleId(int id)
			throws IllegalArgumentException {
		try {
			return this.getModuleMapper().findById(id).getPartlist();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

	}

	/**
	 * Finden einer St�ckliste mittels der St�cklistenID
	 * 
	 * @param id
	 *            der St�ckliste
	 */
	@Override
	public Partlist findPartlistById(int id) throws IllegalArgumentException {
//		try {
//			return null;
//		} catch (SQLException e) {
//			throw new IllegalArgumentException(e.getMessage());
//		}
		return null;
	}

	// @Override
	// public Partlist findPartlistByModule(Module module) throws
	// IllegalArgumentException {
	// return this.moduleMapper.;
	// }

	/**
	 * Finden einer St�ckliste mittels der Baugruppe
	 * 
	 * @param module
	 *            Baurgruppe der St�ckliste
	 */
	public Partlist findPartlistByModule(Module module)
			throws IllegalArgumentException {
		return this.findPartlistById(module.getId());
	}

	/**
	 * Berechnen der ben�tigten Bauteile
	 * 
	 * @param partlist
	 *            ist die �bergebene St�ckliste, aus welcher das Material
	 *            berechnet wird
	 */
	@Override
	public Partlist calculateMaterial(Partlist partlist)
			throws IllegalArgumentException {
		Partlist totalAmount = new Partlist();

		// Alle Einträge der übergebenen Parlist durchiterieren.
		for (PartlistEntry pe : partlist.getAllEntries()) {

			// Wenn Element vom Typ Module ist, dann befinden sich weitere
			// Elemente in dessen Stückliste (Partlist)
			if (pe.getElement() instanceof Module) {
				Module module = (Module) pe.getElement();
				Partlist partAmount = calculateMaterial(module.getPartlist());
				totalAmount.add(partAmount);

				// Ansonsten handelt es sich um ein einzelnes Element/Bauteil
			} else {
				// Prüfen ob das Element bereits in der totalAmount Stückliste
				// vorhanden ist.
				if (totalAmount.contains(pe.getElement())) {
					// Element bereits in totalAmount vorhanden, deswegen nur
					// noch die Anzahl addieren.
					PartlistEntry entry = totalAmount
							.getPartlistEntryByIndex(totalAmount
									.indexOfElement(pe.getElement()));
					entry.setAmount(entry.getAmount() + pe.getAmount());
				} else {
					// Neues Element das noch nicht in totalAmount vorhanden
					// ist, zum ersten Mal hinzufügen.
					totalAmount.add(pe.getElement(), pe.getAmount());
				}
			}
		}
		return totalAmount;
	}

	/*
	 * *************************************************
	 * ABSCHNITT, Ende: Methoden f�r Partlist-Objekte
	 * *************************************************
	 */

	/*
	 * *************************************************
	 * ABSCHNITT, Beginn: Methoden f�r Product-Objekte
	 * *************************************************
	 */

	/**
	 * Auslesen des Datenbank Eintrags vom Endprodukt
	 */
	public ProductMapper getProductMapper() throws IllegalArgumentException {
		return this.productMapper;
	}

	/**
	 * Erstellen eines neuen Endproduktes
	 * 
	 * @param salesName
	 *            des Endproduktes
	 * @param module
	 *            Baugruppe das zum Enderzeugnis wird
	 */
	public Product createProduct(Product p)
			throws IllegalArgumentException {

		try {
			return this.getProductMapper().insert(p);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Auslesen s�mtlicher Endprodukte
	 */
	public Partlist getAllProducts() throws IllegalArgumentException {
		try {
			return this.getProductMapper().findAll();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Bearbeiten des Endprodukts
	 */
	public Product editProduct(Product product) throws IllegalArgumentException {
		try {
			return this.getProductMapper().update(product);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * L�schen des Endproduktes. Beachten Sie bitte auch die Anmerkungen zu
	 * throws IllegalArgumentException {@link #delete(User)},
	 * {@link #delete(Module)} und {@link #delete(Element)}.
	 * 
	 * @see #delete(User)
	 * @see #delete(Module)
	 * @see #delete(Element)
	 */
	@Override
	public void deleteProduct(Product product) throws IllegalArgumentException {
		try {
			this.getProductMapper().delete(product);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/*
	 * *************************************************
	 * ABSCHNITT, Ende: Methoden f�r Product-Objekte
	 * *************************************************
	 */

}
