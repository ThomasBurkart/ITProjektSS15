package de.hdm.groupfive.itproject.shared;

import java.util.Vector;
import de.hdm.groupfive.itproject.shared.bo.*;

public interface IAdministrationEditorViewer {
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

}
