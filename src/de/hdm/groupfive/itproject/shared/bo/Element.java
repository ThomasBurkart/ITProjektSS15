package de.hdm.groupfive.itproject.shared.bo;

import java.sql.Timestamp;
import java.util.Vector;

import de.hdm.groupfive.itproject.server.db.ElementMapper;
import de.hdm.groupfive.itproject.server.db.ModuleMapper;
import de.hdm.groupfive.itproject.server.db.PartlistMapper;
import de.hdm.groupfive.itproject.server.db.ProductMapper;
import de.hdm.groupfive.itproject.server.db.UserMapper;
import de.hdm.groupfive.itproject.shared.AdministrationCommon;

public class Element extends BusinessObject implements AdministrationCommon{
	private static final long serialVersionUID = 1L;

	private String name = "";
	private String description = "";
	private String materialDescription = "";
	private Timestamp creationDate;
	private Timestamp lastUpdate;
	private User lastUser;

	/**
	 * Fremdschlüsselbeziehung zum Inhaber des Kontos.
	 */
	private int ownerID = 0;

	/**
	 * Auslesen des Fremdschlüssels zum Kontoinhaber.
	 */

	public int getOwnerID() {
		return this.ownerID;
	}

	/**
	 * Setzen des Fremdschlüssels zum Kontoinhaber.
	 */
	public void setOwnerID(int kundeID) {
		this.ownerID = kundeID;
	}

	/**
	 * Erzeugen einer einfachen textuellen Repräsentation der jeweiligen
	 * Kontoinstanz.
	 */
	@Override
	public String toString() {
		return super.toString() + " inhaber, Kunden-ID: #" + this.ownerID;
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

	public void setNameID(String string) {
		// TODO Auto-generated method stub
		/**
		 * Setzen des Namens des Bauteils
		 */
		this.name = string;


	}

	public void setDescriptionID(String string) {
		// TODO Auto-generated method stub
		/**
		 * Setzen der Beschreibung des Bauteils
		 */
		this.description = string;

	}

	public void setMaterialDescriptionID(String string) {
		// TODO Auto-generated method stub
		/**
		 * Setzen der Materialbeschreibung des Bauteils
		 */
		this.materialDescription = string;

	}

	public void setLastUpdateID(Timestamp timestamp) {
		// TODO Auto-generated method stub
		/**
		   * 
		   */
		this.lastUpdate = timestamp;

	}

	public void setCreationDateID(Timestamp timestamp) {
		// TODO Auto-generated method stub
		this.creationDate = timestamp;
	}

	public void setUserID(User user) {
		// TODO Auto-generated method stub
		this.lastUser = user;

	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getMaterialdescription() {
		return this.materialDescription;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public User getUser() {
		return this.lastUser;
	}

	@Override
	public User registerUser(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User loginUser(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logoutUser() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ElementMapper getElementMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModuleMapper getModuleMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductMapper getProductMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PartlistMapper getPartlistMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserMapper getUserMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Element createElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element editElement(Element element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteElement(Element element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Module assignElement(Module module, Element element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Module createModule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Module editModule(Module module) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteModule(Module module) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Module assignModule(Module module, Module subModule) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product createProduct() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product editProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProduct(Product product) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Element findElementById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Element> findElementsByCreator(User creator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Element> findElementsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partlist findPartlistByModuleName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partlist findPartlistByModuleId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partlist findPartlistById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Partlist findPartlistByModule(Module module) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object calculateMaterial(Partlist partlist) {
		// TODO Auto-generated method stub
		return null;
	}

}
