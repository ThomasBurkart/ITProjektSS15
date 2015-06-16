package de.hdm.groupfive.itproject.server;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.groupfive.itproject.server.db.*;
import de.hdm.groupfive.itproject.shared.*;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

/**
 * <p>
 * Implementierungsklasse des Interface <code>AdministationCommon</code>. Diese
 * Klasse ist <em>die</em> Klasse, die s�mtliche Applikationslogik 
 * (oder engl. Business Logic) aggregiert. Sie ist wie eine Spinne, 
 * die s�mtliche Zusammenh�nge in ihrem Netz (in unserem Fall die Daten 
 * der Applikation) �berblickt und f�r einen geordneten Ablauf und
 * dauerhafte Konsistenz der Daten und Abl�ufe sorgt.
 * </p>
 * <p>
 * Die Applikationslogik findet sich in den Methoden dieser Klasse. Jede dieser
 * Methoden kann als <em>Transaction Script</em> bezeichnet werden. Dieser Name
 * l�sst schon vermuten, dass hier analog zu Datenbanktransaktion pro
 * Transaktion gleiche mehrere Teilaktionen durchgef�hrt werden, die das System
 * von einem konsistenten Zustand in einen anderen, auch wieder konsistenten
 * Zustand �berf�hren. Wenn dies zwischenzeitig scheitern sollte, dann ist das
 * jeweilige Transaction Script daf�r verwantwortlich, eine Fehlerbehandlung
 * durchzuf�hren.
 * </p>
 * <p>
 * Diese Klasse steht mit einer Reihe weiterer Datentypen in Verbindung. Dies
 * sind:
 * <ol>
 * <li>{@link AdministrationCommon}: Dies ist das <em>lokale</em> - also
 * Server-seitige - Interface, das die im System zur Verf�gung gestellten
 * Funktionen deklariert.</li>
 * <li>{@link AdministrationCommonAsync}: <code>AdministartionCommonImpl</code> und
 * <code>AdministrationCommon</code> bilden nur die Server-seitige Sicht der
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
 * Basis f�r die Anbindung von <code>AdministrationCommonImpl</code> an die Runtime
 * des GWT RPC-Mechanismus.</li>
 * </ol>
 * </p>
 * <p>
 * <b>Wichtiger Hinweis:</b> Diese Klasse bedient sich sogenannter
 * Mapper-Klassen. Sie geh�ren der Datenbank-Schicht an und bilden die
 * objektorientierte Sicht der Applikationslogik auf die relationale
 * organisierte Datenbank ab. Zuweilen kommen "kreative" Zeitgenossen auf die
 * Idee, in diesen Mappern auch Applikationslogik zu realisieren. Siehe dazu
 * auch die Hinweise in {@link #delete(User)} Einzig nachvollziehbares
 * Argument f�r einen solchen Ansatz ist die Steigerung der Performance
 * umfangreicher Datenbankoperationen. Doch auch dieses Argument zieht nur dann,
 * wenn wirklich gro�e Datenmengen zu handhaben sind. In einem solchen Fall
 * w�rde man jedoch eine entsprechend erweiterte Architektur realisieren, die
 * wiederum s�mtliche Applikationslogik in der Applikationsschicht isolieren
 * w�rde. Also, keine Applikationslogik in die Mapper-Klassen "stecken" sondern
 * dies auf die Applikationsschicht konzentrieren!
 * </p>
 * <p>
 * Beachten Sie, dass s�mtliche Methoden, die mittels GWT RPC aufgerufen werden
 * k�nnen ein <code>throws IllegalArgumentException</code> in der
 * Methodendeklaration aufweisen. Diese Methoden d�rfen also Instanzen von
 * {@link IllegalArgumentException} auswerfen. Mit diesen Exceptions k�nnen z.B.
 * Probleme auf der Server-Seite in einfacher Weise auf die Client-Seite
 * transportiert und dort systematisch in einem Catch-Block abgearbeitet werden.
 * </p>
 */
@SuppressWarnings("serial")
public class AdministrationCommonImpl extends RemoteServiceServlet implements AdministrationCommon {

	/**
	 * Eindeutige SerialVersion Id. Wird zum Serialisieren der Klasse ben�tigt.
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
	 * Referenz auf den DatenbankMapper, der St�cklistenobjekte mit der Datenbank
     * abgleicht.
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
	   * ***************************************************************************
	   */
	/**
	 * No-Argument Konstruktor
	 */
	public AdministrationCommonImpl() throws IllegalArgumentException {
		
	}
	
	/**
	 *  Initialsierungsmethode. Siehe dazu Anmerkungen zum No-Argument-Konstruktor
	 * {@link #AdministrationCommonImpl()}. Diese Methode muss f�r jede Instanz von
	 * <code>AdministrationCommonImpl</code> aufgerufen werden.
	 */
	public void init() throws IllegalArgumentException {
	    /*
	     * Ganz wesentlich ist, dass die Administration einen vollst�ndigen Satz
	     * von Mappern besitzt, mit deren Hilfe sie dann mit der Datenbank
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
	   * ***************************************************************************
	   */
	
	  /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden f�r User-Objekte
	   * ***************************************************************************
	   */
	
	@Override
	public User registerUser(String email, String password) throws IllegalArgumentException {
		// TODO wird wahrscheinlich nicht mehr benötigt
		return null;
	}

	@Override
	public User loginUser(String email, String password) throws IllegalArgumentException {
		// TODO Google Api ansteuern
		return null;
	}

	@Override
	public void logoutUser() throws IllegalArgumentException {
		// TODO Google Api ansteuern

	}

	public UserMapper getUserMapper() throws IllegalArgumentException {
		return this.userMapper;
	}

	@Override
	public void setUser(User user) throws IllegalArgumentException {
		this.currentUser = user;
	}

	@Override
	public User getUser() throws IllegalArgumentException {
		return this.currentUser;
	}
	
	 /*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden f�r User-Objekte
	   * ***************************************************************************
	   */

	  /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden f�r Element-Objekte
	   * ***************************************************************************
	   */
	
	public ElementMapper getElementMapper() throws IllegalArgumentException {
		return this.elementMapper;
	}

	@Override
	public Element createElement(Element element) throws IllegalArgumentException {	
		try {
			return this.getElementMapper().insert(element);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public Element editElement(Element element) throws IllegalArgumentException {
		try {
			return this.getElementMapper().update(element);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public void deleteElement(Element element) throws IllegalArgumentException {
		try {
			this.getElementMapper().delete(element);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}		
	}

	@Override
	public Module assignElement(Module module, Element element) throws IllegalArgumentException {
		// module.getPartlist().add(element, amount);
		// TODO amount fehlt bei assignElement
		return null;
	}
	 


	@Override
	public Element findElementById(int id) throws IllegalArgumentException {
		try {
			return this.getElementMapper().findById(id);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public Vector<Element> findElementsByCreator(User creator) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Element> findElementsByName(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		Vector<Element> result = new Vector<Element>();
		if (name.equals("leer")) {
			// Nur für Tests
		} else {
			Module m1 = new Module();
			m1.setName(name);
			m1.setId(0);
			
			Element e1 = new Element();
			e1.setName(name + " element");
			e1.setId(1);
			e1.setDescription("blablabla");
		
			m1.getPartlist().add(e1, 1);
			
			result.add(m1);
		}
		return result;
	}
	
	
	  /*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden f�r Element-Objekte
	   * ***************************************************************************
	   */

	  /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden f�r Modul-Objekte
	   * ***************************************************************************
	   */
	
	
	public ModuleMapper getModuleMapper() throws IllegalArgumentException {
		return this.moduleMapper;
	}
	
	@Override
	public Module createModule(Module module) throws IllegalArgumentException {
		try {
			return this.getModuleMapper().insert(module);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public Module editModule(Module module) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteModule(Module module) throws IllegalArgumentException {
		if (module != null) {
			try {
				this.getModuleMapper().delete(module);
			} catch (SQLException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		} else {
			throw new IllegalArgumentException("Übergebenes Modul Objekt ist NULL");
		}
	}

	@Override
	public Module assignModule(Module module, Module subModule) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	
	  /*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden f�r Modul-Objekte
	   * ***************************************************************************
	   */

	  /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden f�r Partlist-Objekte
	   * ***************************************************************************
	   */
	
	
	public PartlistMapper getPartlistMapper() throws IllegalArgumentException {
		return this.partlistMapper;
	}

	

	public Partlist findPartlistByModuleName(String name) throws IllegalArgumentException {
		try {
			return this.getModuleMapper().findByName(name).getPartlist();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	@Override
	public Partlist findPartlistByModuleId(int id) throws IllegalArgumentException {
		try {
			return this.getModuleMapper().findById(id).getPartlist();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
	}

	@Override
	public Partlist findPartlistById(int id) throws IllegalArgumentException {
		try {
			return this.getPartlistMapper().findById(id);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}

//	@Override
//	public Partlist findPartlistByModule(Module module) throws IllegalArgumentException {
//		return this.moduleMapper.;
//	}
	
	@Override
	public String calculateMaterial(Partlist partlist)
			throws IllegalArgumentException {
		//.getAllElements(partlist)
		
		return null;
	}
	
		
	  /*
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden f�r Partlist-Objekte
	   * ***************************************************************************
	   */

	  /*
	   * ***************************************************************************
	   * ABSCHNITT, Beginn: Methoden f�r Product-Objekte
	   * ***************************************************************************
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
	 * @param name des Endproduktes
	 * @param module Baugruppe des zum Enderzeugnis wird
	 * @param price des Endproduktes
	 */
	public Product createProduct(String salesName, Module module) throws IllegalArgumentException {
		
		Product p = new Product(); 
		p = (Product) module;
		p.setSalesName(salesName); 		// Name des Endproduktes
		
		
		try {
			return this.getProductMapper().insert(p);
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	 /**
	  * Auslesen s�mtlicher Endprodukte 
	  */
	public Vector<Product> getAllProducts() throws IllegalArgumentException {
		try {
			return this.getProductMapper().findAll();
		} catch (SQLException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	

	/**
	 * 
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
	   * throws IllegalArgumentException {@link #delete(User)}, {@link #delete(Module)} und {@link #delete(Element)}.
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
	   * ***************************************************************************
	   * ABSCHNITT, Ende: Methoden f�r Product-Objekte
	   * ***************************************************************************
	   */

	
}
