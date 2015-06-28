package de.hdm.groupfive.itproject.shared;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.HTML;

import de.hdm.groupfive.itproject.server.db.*;
import de.hdm.groupfive.itproject.shared.bo.*;

@RemoteServiceRelativePath("administration")
public interface AdministrationCommon extends RemoteService {

	/**
	 * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von
	 * GWT RPC zusätzlich zum No Argument Constructor der implementierenden
	 * Klasse {@link BankVerwaltungImpl} notwendig. Bitte diese Methode direkt
	 * nach der Instantiierung aufrufen.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	public User registerUser(String email, String password) throws IllegalArgumentException;

	public User loginUser(String email, String password) throws IllegalArgumentException;

	public void logoutUser() throws IllegalArgumentException;

	public void setUser(User user) throws IllegalArgumentException;

	public User getUser() throws IllegalArgumentException;

	public Element createElement(Element element) throws IllegalArgumentException;

	public Element editElement(Element element) throws IllegalArgumentException;

	public void deleteElement(Element element) throws IllegalArgumentException;

	public void assignElement(Module module, Element element, int amount) throws IllegalArgumentException;

	public Element createModule(Module module);

	public Module editModule(Module module) throws IllegalArgumentException;

	public void deleteModule(Module module) throws IllegalArgumentException;

	public void assignModule(Module module, Module subModule, int amount) throws IllegalArgumentException;

	public Element createProduct(Product module) throws IllegalArgumentException;

	public Product editProduct(Product product) throws IllegalArgumentException;

	public void deleteProduct(Product product) throws IllegalArgumentException;

	public Partlist findElementById(int id) throws IllegalArgumentException;

	public Partlist findElementsByCreator(User creator) throws IllegalArgumentException;

	public Partlist findElementsByName(String searchWord) throws IllegalArgumentException;
	
	public Partlist findElementsByName(String searchWord, int maxResults) throws IllegalArgumentException;

	public Partlist findModulesByName(String searchWord) throws IllegalArgumentException;
	
	public Partlist findModulesByName(String searchWord, int maxResults) throws IllegalArgumentException;

//	public Partlist findPartlistByModuleName(String name) throws IllegalArgumentException;

	public Partlist findPartlistByModuleId(int id) throws IllegalArgumentException;

	public Partlist findPartlistById(int id) throws IllegalArgumentException;

//	public Partlist findPartlistByModule(Module module) throws IllegalArgumentException;

	public Vector<Product> getAllProducts() throws IllegalArgumentException;

	public Partlist calculateMaterial(Partlist partlist) throws IllegalArgumentException;

}
