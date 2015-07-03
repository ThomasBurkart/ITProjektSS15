package de.hdm.groupfive.itproject.server;

import java.sql.SQLException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.groupfive.itproject.server.db.DBConnection;
import de.hdm.groupfive.itproject.server.db.ElementMapper;
import de.hdm.groupfive.itproject.server.db.ModuleMapper;
import de.hdm.groupfive.itproject.server.db.ProductMapper;
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
 * Klasse ist <em>die</em> Klasse, die sï¿½mtliche Applikationslogik (oder engl.
 * Business Logic) aggregiert. Sie ist wie eine Spinne, die sï¿½mtliche
 * Zusammenhï¿½nge in ihrem Netz (in unserem Fall die Daten der Applikation)
 * ï¿½berblickt und fï¿½r einen geordneten Ablauf und dauerhafte Konsistenz der
 * Daten und Ablï¿½ufe sorgt.
 * </p>
 * <p>
 * Die Applikationslogik findet sich in den Methoden dieser Klasse. Jede dieser
 * Methoden kann als <em>Transaction Script</em> bezeichnet werden. Dieser Name
 * lï¿½sst schon vermuten, dass hier analog zu Datenbanktransaktion pro
 * Transaktion gleiche mehrere Teilaktionen durchgefï¿½hrt werden, die das
 * System von einem konsistenten Zustand in einen anderen, auch wieder
 * konsistenten Zustand ï¿½berfï¿½hren. Wenn dies zwischenzeitig scheitern
 * sollte, dann ist das jeweilige Transaction Script dafï¿½r verwantwortlich,
 * eine Fehlerbehandlung durchzufï¿½hren.
 * </p>
 * <p>
 * Diese Klasse steht mit einer Reihe weiterer Datentypen in Verbindung. Dies
 * sind:
 * <ol>
 * <li>{@link AdministrationCommon}: Dies ist das <em>lokale</em> - also
 * Server-seitige - Interface, das die im System zur Verfï¿½gung gestellten
 * Funktionen deklariert.</li>
 * <li>{@link AdministrationCommonAsync}: <code>AdministartionCommonImpl</code>
 * und <code>AdministrationCommon</code> bilden nur die Server-seitige Sicht der
 * Applikationslogik ab. Diese basiert vollstï¿½ndig auf synchronen
 * Funktionsaufrufen. Wir mï¿½ssen jedoch in der Lage sein, Client-seitige
 * asynchrone Aufrufe zu bedienen. Dies bedingt ein weiteres Interface, das in
 * der Regel genauso benannt wird, wie das synchrone Interface, jedoch mit dem
 * zusï¿½tzlichen Suffix "Async". Es steht nur mittelbar mit dieser Klasse in
 * Verbindung. Die Erstellung und Pflege der Async Interfaces wird durch das
 * Google Plugin semiautomatisch unterstï¿½tzt. Weitere Informationen unter
 * {@link AdministrationCommonAsync}.</li>
 * <li> {@link RemoteServiceServlet}: Jede Server-seitig instantiierbare und
 * Client-seitig ï¿½ber GWT RPC nutzbare Klasse muss die Klasse
 * <code>RemoteServiceServlet</code> implementieren. Sie legt die funktionale
 * Basis fï¿½r die Anbindung von <code>AdministrationCommonImpl</code> an die
 * Runtime des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie gehï¿½ren der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Zuweilen kommen "kreative" Zeitgenossen auf die
 * Idee, in diesen Mappern auch Applikationslogik zu realisieren. Siehe dazu
 * auch die Hinweise in {@link #delete(User)} Einzig nachvollziehbares Argument
 * fï¿½r einen solchen Ansatz ist die Steigerung der Performance umfangreicher
 * Datenbankoperationen. Doch auch dieses Argument zieht nur dann, wenn wirklich
 * groï¿½e Datenmengen zu handhaben sind. In einem solchen Fall wï¿½rde man
 * jedoch eine entsprechend erweiterte Architektur realisieren, die wiederum
 * sï¿½mtliche Applikationslogik in der Applikationsschicht isolieren wï¿½rde.
 * Also, keine Applikationslogik in die Mapper-Klassen "stecken" sondern dies
 * auf die Applikationsschicht konzentrieren!
 * </p>
 * <p>
 * Beachten Sie, dass sämtliche Methoden, die mittels GWT RPC aufgerufen werden
 * können ein <code>throws IllegalArgumentException</code> in der
 * Methodendeklaration aufweisen. Diese Methoden dï¿½rfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions können z.B.
 * Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
 * transportiert und dort systematisch in einem Catch-Block abgearbeitet werden.
 * </p>
 */
@SuppressWarnings("serial")
public class AdministrationCommonImpl extends RemoteServiceServlet implements
		AdministrationCommon {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse
	 * benï¿½tigt.
	 */
	private static final long serialVersionUID = 1L;

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
	 * Methode muss fï¿½r jede Instanz von <code>AdministrationCommonImpl</code>
	 * aufgerufen werden.
	 */
	public void init() throws IllegalArgumentException {
		/*
		 * Ganz wesentlich ist, dass die Administration einen vollstï¿½ndigen
		 * Satz von Mappern besitzt, mit deren Hilfe sie dann mit der Datenbank
		 * kommunizieren kann.
		 */
		this.elementMapper = ElementMapper.getElementMapper();
		this.moduleMapper = ModuleMapper.getModuleMapper();
		this.productMapper = ProductMapper.getProductMapper();
	}

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Ende: Initialisierung
	 * *****************************************
	 * **********************************
	 */

	/*
	 * ***************************************************************************
	 * ABSCHNITT, Beginn: Methoden für User-Objekte
	 * *****************************
	 * **********************************************
	 */

	/**
	 * Login Daten werden mit der Datenbank abgeglichen
	 * 
	 */
	@Override
	public User loginUser(boolean isReportGen) throws IllegalArgumentException {
		com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
				.getUserService();

		if (userService.isUserLoggedIn()) {
			com.google.appengine.api.users.User user = userService
					.getCurrentUser();

			User u = new User();
			u.setEmail(user.getEmail());
			u.setUserName(user.getNickname());
			u.setUserId(user.getUserId());
			u.setIsLoggedIn(true);
			return u;
		} else {
			User u = new User();
			u.setIsLoggedIn(false);
			if (isReportGen) {
				u.setLoginUrl(userService
						.createLoginURL(ServerSettings.PAGE_URL_REPORT));
			} else {
				u.setLoginUrl(userService
						.createLoginURL(ServerSettings.PAGE_URL_EDITOR));
			}
			return u;
		}
	}

	/**
	 * Durch den Logout wird die SessionID in der DB gespeichert und der
	 * Benutzer wird ausgeloggt
	 */
	@Override
	public String logoutUser(boolean isReportGen)
			throws IllegalArgumentException {
		com.google.appengine.api.users.UserService userService = com.google.appengine.api.users.UserServiceFactory
				.getUserService();

		if (userService.isUserLoggedIn()) {
			if (isReportGen) {
				return userService
						.createLogoutURL(ServerSettings.PAGE_URL_REPORT);
			}
			return userService.createLogoutURL(ServerSettings.PAGE_URL_EDITOR);
		}
		return "http://www.google.de";
	}

	/*
	 * *************************************************
	 * ABSCHNITT, Ende: Methoden fï¿½r User-Objekte
	 * *************************************************
	 */

	/*
	 * *************************************************
	 * ABSCHNITT, Beginn: Methoden fï¿½r Element-Objekte
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
	 * @param element
	 *            Bauteil das erstellt wird
	 */
	@Override
	public Element createElement(Element element)
			throws IllegalArgumentException {
		if (element == null) {
			throw new IllegalArgumentException(
					"Übergebenes Objekt an createElement() ist NULL");
		}
		Element result = null;
		try {
			if (element instanceof Product) {
				result = this.getProductMapper().insert((Product) element);
			} else if (element instanceof Module) {
				result = this.getModuleMapper().insert((Module) element);
			} else {
				result = this.getElementMapper().insert(element);
			}

			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/**
	 * Bearbeiten eines Bauteils
	 * 
	 * @param element
	 *            Bauteil das verändert werden soll
	 * @return Das geänderte Bauteil wird an den Bauteil-Mapper (ElementMapper)
	 *         übergeben
	 */
	@Override
	public Element editElement(Element element) throws IllegalArgumentException {
		Element result = null;
		try {
			result = this.getElementMapper().update(element);
			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/**
	 * Löschen eines Bauteils
	 * 
	 * @param element
	 *            Bauteil das gelï¿½scht werden soll
	 * @return das zu lï¿½schende Bauteil wird an den Bauteil-Mapper
	 *         (ElementMapper) ï¿½bergeben zum lï¿½schen
	 */
	@Override
	public void deleteElement(Element element) throws IllegalArgumentException {
		try {
			this.getElementMapper().delete(element);
			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/**
	 * Finden eines Bauteils mittels der ID
	 * 
	 * @param id
	 *            des Bauteils
	 * @param onlyModules
	 *            nur Baugruppen finden
	 * @param onlyProducts
	 *            nur Enderzeugnisse finden
	 * @return Element wird durch die findById()-Methode gesucht
	 */
	@Override
	public Partlist findElementById(int id, boolean onlyModules,
			boolean onlyProducts) throws IllegalArgumentException {
		Partlist result = null;
		try {
			result = this.getElementMapper().findById(id, onlyModules,
					onlyProducts);
			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/*
	 * **********************************************************
	 */
	/**
	 * Zuweisen eines Bauteils
	 * 
	 * @param superElement
	 *            Baugruppe, der ein Bauteil zugewiesen werden soll
	 * @param subElement
	 *            Bauteil, das einer Baugruppe zugewiesen werden soll
	 * @param amount
	 *            Wie viele Bauteile zugeordnet werden sollen
	 */
	@Override
	public void assignElement(Element superElement, Element subElement,
			int amount) throws IllegalArgumentException {
		if (superElement.getId() == subElement.getId()) {
			if (superElement instanceof Product) {
				throw new IllegalArgumentException(
						"Ein Produkt kann sich nicht selbst zugewiesen werden!");
			} else if (superElement instanceof Module) {
				throw new IllegalArgumentException(
						"Eine Baugruppe kann sich nicht selbst zugewiesen werden!");
			} else if (superElement instanceof Element) {
				throw new IllegalArgumentException(
						"Eine Bauteil kann sich nicht selbst zugewiesen werden!");
			}
		}

		try {
			// Product - Product
			if (superElement instanceof Product
					&& subElement instanceof Product) {
				throw new IllegalArgumentException(
						"Ein Enderzeugnis kann keinem anderen Enderzeugnis zugewiesen werden!");
				// Product - Modul
			} else if (superElement instanceof Product
					&& subElement instanceof Module
					&& !(subElement instanceof Product)) {
				if (isPartOfModule((Module)subElement, superElement)) {
					throw new IllegalArgumentException(
							"Die übergeordnete Baugruppe ist bereits in der untergeordneten Baugruppe vorhanden, es können keine Schleifen angelegt werden!");
				}
				this.getModuleMapper().assignModule((Module) superElement,
						(Module) subElement, amount);
				// Modul - Produkt
			} else if (!(superElement instanceof Product)
					&& superElement instanceof Module
					&& subElement instanceof Product) {
				if (isPartOfModule((Module)superElement, subElement)) {
					throw new IllegalArgumentException(
							"Die übergeordnete Baugruppe ist bereits in der untergeordneten Baugruppe vorhanden, es können keine Schleifen angelegt werden!");
				}
				this.getModuleMapper().assignModule((Module) subElement,
						(Module) superElement, amount);
				// Modul - Modul
			} else if (!(superElement instanceof Product)
					&& superElement instanceof Module
					&& !(subElement instanceof Product)
					&& subElement instanceof Module) {
				if (isPartOfModule((Module)subElement, superElement)) {
					throw new IllegalArgumentException(
							"Die übergeordnete Baugruppe ist bereits in der untergeordneten Baugruppe vorhanden, es können keine Schleifen angelegt werden!");
				}
				this.getModuleMapper().assignModule((Module) superElement,
						(Module) subElement, amount);
				// Modul - Element
			} else if (superElement instanceof Module
					&& !(subElement instanceof Module)) {
				this.getModuleMapper().assignElement((Module) superElement,
						subElement, amount);
				// Element - Modul
			} else if (!(superElement instanceof Module)
					&& subElement instanceof Module) {
				this.getModuleMapper().assignElement((Module) subElement,
						superElement, amount);
				// Element - Element
			} else if (!(superElement instanceof Module)
					&& !(subElement instanceof Module)) {
				throw new IllegalArgumentException(
						"Eine Bauteil kann keinem anderen Bauteil zugewiesen werden!");
			} else {
				throw new IllegalArgumentException(
						"Ups da ist was schief gegangen! Die Zuordnung konnte nicht gespeichert werden!");
			}

			DBConnection.closeConnection();
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
	}

	/**
	 * Löscht eine Zuordnung zwischen Elementen beliebiger Art
	 * 
	 * @param pe
	 *            Partlist Eintrag mit Element und Übergeordnetem Element
	 */
	@Override
	public void deleteAssignment(PartlistEntry pe)
			throws IllegalArgumentException {
		if (pe.getSuperModule() != null && pe.getElement() != null) {
			if (pe.getElement() instanceof Module) {
				try {
					this.getModuleMapper().deleteModuleRelationshipAssign(
							pe.getSuperModule(), (Module) pe.getElement());

					DBConnection.closeConnection();
				} catch (SQLException e) {
					throw new IllegalArgumentException(e.getMessage());
				}
			} else {
				try {
					this.getModuleMapper().deleteModuleElementAssign(
							pe.getSuperModule(), pe.getElement());

					DBConnection.closeConnection();
				} catch (SQLException e) {
					throw new IllegalArgumentException(e.getMessage());
				}
			}
		}
	}

	/*
	 * **********************************************************
	 */
	/**
	 * Finden eines Bauteils mittels des Namens
	 * 
	 * @param name
	 *            des Bauteils
	 * @return Partlist mit Such-Ergebnissen
	 */
	@Override
	public Partlist findElementsByName(String searchWord)
			throws IllegalArgumentException {
		return this.findElementsByName(searchWord, 1000);
	}

	/**
	 * Finden eines Bauteils mittels des Namens
	 * 
	 * @param name
	 *            des Bauteils
	 * @param maxResults
	 *            maximale Anzahle der angezeigten Ergebnisse
	 * @return Partlist mit Such-Ergebnissen
	 */
	@Override
	public Partlist findElementsByName(String searchWord, int maxResults)
			throws IllegalArgumentException {
		Partlist result = null;
		try {
			result = this.getElementMapper().findByName(searchWord, maxResults);
			DBConnection.closeConnection();
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return result;
	}

	/**
	 * Finden einer Baugruppe mittels des Namens
	 * 
	 * @param name
	 *            der Baugruppe
	 */
	@Override
	public Partlist findModulesByName(String searchWord)
			throws IllegalArgumentException {
		return this.findModulesByName(searchWord, 1000);
	}

	/**
	 * Finden einer Baugruppe mittels des Namens
	 * 
	 * @param name
	 *            der Baugruppe
	 * @param maxResults
	 *            maximale Anzahle der angezeigten Ergebnisse
	 */
	@Override
	public Partlist findModulesByName(String searchWord, int maxResults)
			throws IllegalArgumentException {
		Partlist result = null;
		try {
			result = this.getModuleMapper().findByName(searchWord, maxResults);

			DBConnection.closeConnection();
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (SQLException ex) {
			throw new IllegalArgumentException(ex.getMessage());
		}
		return result;
	}

	/*
	 * *************************************************
	 * ABSCHNITT, Ende: Methoden fï¿½r Element-Objekte
	 * *************************************************
	 */

	/*
	 * *************************************************
	 * ABSCHNITT, Beginn: Methoden fï¿½r Modul-Objekte
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
	 * @param m
	 *            Baugruppe die erstellt wird
	 */
	@Override
	public Module createModule(Module m) throws IllegalArgumentException {
		Module result = null;
		try {
			result = this.getModuleMapper().insert(m);
			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/**
	 * Bearbeiten einer Baugruppe
	 * 
	 * @param module
	 *            Baugruppe die bearbeitet werden soll
	 * @return Die bearbeitete Baugruppe wird an den Baugruppen-Mapper
	 *         (ModuleMapper) ï¿½bergeben
	 */
	@Override
	public Module editModule(Module module) throws IllegalArgumentException {
		Module result = null;
		try {
			result = this.getModuleMapper().update(module);
			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/*
	 * **********************************************************
	 */

	/**
	 * Löschen einer Baugruppe
	 * 
	 * @param module
	 *            Baugruppe die gelöscht werden soll
	 * @return die zulöschende Baugruppe wird an den Baugruppen-Mapper
	 *         (ModuleMapper) übergeben zum lï¿½schen
	 */
	@Override
	public void deleteModule(Module module) throws IllegalArgumentException {
		if (module != null) {
			try {
				this.getModuleMapper().delete(module);
				DBConnection.closeConnection();
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
	 * ABSCHNITT, Ende: Methoden fï¿½r Modul-Objekte
	 * **************************************************
	 */

	/*
	 * ***************************************************
	 * ABSCHNITT, Beginn: Methoden fï¿½r Partlist-Objekte
	 * ***************************************************
	 */

	/**
	 * Finden einer Stückliste mittels der BaugruppenID
	 * 
	 * @param id
	 *            der Baugruppe
	 */
	@Override
	public Partlist findPartlistByModuleId(int id)
			throws IllegalArgumentException {
		Partlist result = null;
		try {
			result = this.getModuleMapper().findById(id).getPartlist();
			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/**
	 * Berechnen der benötigten Bauteile
	 * 
	 * @param partlist
	 *            ist die übergebene Stückliste, aus welcher das Material
	 *            berechnet wird
	 */
	@Override
	public Partlist calculateMaterial(Partlist partlist, int amount)
			throws IllegalArgumentException {
		if (partlist != null) {
			Partlist totalAmount = new Partlist();

			// Alle EintrÃ¤ge der Ã¼bergebenen Parlist durchiterieren.
			for (PartlistEntry pe : partlist.getAllEntries()) {

				// Wenn Element vom Typ Module ist, dann befinden sich weitere
				// Elemente in dessen StÃ¼ckliste (Partlist)
				if (pe.getElement() instanceof Module) {
					Module module = (Module) pe.getElement();
					Partlist partAmount = calculateMaterial(
							module.getPartlist(), amount);
					totalAmount.add(partAmount);

					// Ansonsten handelt es sich um ein einzelnes
					// Element/Bauteil
				} else {
					// PrÃ¼fen ob das Element bereits in der totalAmount
					// StÃ¼ckliste
					// vorhanden ist.
					if (totalAmount.contains(pe.getElement())) {
						// Element bereits in totalAmount vorhanden, deswegen
						// nur
						// noch die Anzahl addieren.
						PartlistEntry entry = totalAmount
								.getPartlistEntryByIndex(totalAmount
										.indexOfElement(pe.getElement()));
						entry.setAmount(entry.getAmount()
								+ (pe.getAmount() * amount));
					} else {
						// Neues Element das noch nicht in totalAmount vorhanden
						// ist, zum ersten Mal hinzufÃ¼gen.
						totalAmount.add(pe.getElement(), pe.getAmount()
								* amount);
					}
				}
			}
			return totalAmount;
		} else {
			throw new IllegalArgumentException(
					"Keine Stückliste zur Berechnung vorhanden!");
		}
	}

	/**
	 * Durchläuft alle Elemente eines Moduls um zu prüfen ob ein Element darin
	 * enthalten ist
	 * 
	 * @param module
	 *            ist das übergebene Modul das durchsucht werden soll
	 * @param element
	 *            ist das Element nach dem gesucht wird
	 * @return true wenn vorhanden
	 */
	public boolean isPartOfModule(Module module, Element element)
			throws IllegalArgumentException {
		boolean result = false;

		if (module.equals(element)) {
			return true;
		}

		// Alle EintrÃ¤ge der Ã¼bergebenen Parlist durchiterieren.
		for (PartlistEntry pe : module.getPartlist().getAllEntries()) {
			if (pe.getElement() instanceof Module) {
				result = isPartOfModule((Module) pe.getElement(), element);
				if (result) {
					break;
				}
			} else if (pe.getElement().equals(element)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/*
	 * *************************************************
	 * ABSCHNITT, Ende: Methoden fï¿½r Partlist-Objekte
	 * *************************************************
	 */

	/*
	 * *************************************************
	 * ABSCHNITT, Beginn: Methoden fï¿½r Product-Objekte
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
	 * @param p
	 *            übergebenes Endprodukt
	 */
	public Product createProduct(Product p) throws IllegalArgumentException {
		Product result = null;
		try {
			result = this.getProductMapper().insert(p);
			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/**
	 * Auslesen sämtlicher Endprodukte
	 */
	public Partlist getAllProducts() throws IllegalArgumentException {
		Partlist result = null;
		try {
			result = this.getProductMapper().findAll();
			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/**
	 * Bearbeiten des Endprodukts
	 * 
	 * @param product
	 *            übergebenes Endprodukt
	 */
	public Product editProduct(Product product) throws IllegalArgumentException {
		Product result = null;
		try {
			result = this.getProductMapper().update(product);
			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		return result;
	}

	/**
	 * Löschen des Endproduktes. Beachten Sie bitte auch die Anmerkungen zu
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
			DBConnection.closeConnection();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	/*
	 * *************************************************
	 * ABSCHNITT, Ende: Methoden für Product-Objekte
	 * *************************************************
	 */
}
