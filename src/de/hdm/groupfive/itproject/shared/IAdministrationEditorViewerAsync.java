package de.hdm.groupfive.itproject.shared;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

public interface IAdministrationEditorViewerAsync {
	public void createElement();
	public void editElement(Element element);
	public void deleteElement(Element element);
	public void assignElement(Module module, Element element);
	
	public void createModule();
	public void editModule(Module module);
	public void deleteModule(Module module);
	public void assignModule(Module module, Module subModule);
	
	public void createProduct();
	public void editProduct(Product product);
	public void deleteProduct(Product product);
	
	public void findElementById(int id);
	public void findElementsByCreator(User creator);
	public void findElementsByName(String name);
}
