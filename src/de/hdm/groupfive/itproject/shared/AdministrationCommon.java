package de.hdm.groupfive.itproject.shared;

import java.util.Vector;

import de.hdm.groupfive.itproject.server.db.*;
import de.hdm.groupfive.itproject.shared.bo.*;

public interface AdministrationCommon {
	public User registerUser(String email, String password);

	public User loginUser(String email, String password);

	public void logoutUser();

	public ElementMapper getElementMapper();

	public ModuleMapper getModuleMapper();

	public ProductMapper getProductMapper();

	public PartlistMapper getPartlistMapper();

	public UserMapper getUserMapper();

	public void setUser(User user);

	public User getUser();

	public Element createElement();

	public Element editElement(Element element);

	public void deleteElement(Element element);

	public Module assignElement(Module module, Element element);

	public Module createModule();

	public Module editModule(Module module);

	public void deleteModule(Module module);

	public Module assignModule(Module module, Module subModule);

	public Product createProduct();

	public Product editProduct(Product product);

	public void deleteProduct(Product product);

	public Element findElementById(int id);

	public Vector<Element> findElementsByCreator(User creator);

	public Vector<Element> findElementsByName(String name);

	public Partlist findPartlistByModuleName(String name);

	public Partlist findPartlistByModuleId(int id);

	public Partlist findPartlistById(int id);

	public Partlist findPartlistByModule(Module module);

	public Vector<Product> getAllProducts();

	public Object calculateMaterial(Partlist partlist);

}
