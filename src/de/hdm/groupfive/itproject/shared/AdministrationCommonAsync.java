package de.hdm.groupfive.itproject.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

public interface AdministrationCommonAsync {
	public void registerUser(String email, String password);

	public void loginUser(String email, String password);

	public void logoutUser();

	public void getElementMapper();

	public void getModuleMapper();

	public void getProductMapper();

	public void getPartlistMapper();

	public void getUserMapper();

	public void setUser(User user);

	public void getUser();

	public void createElement(AsyncCallback<Element> callback);

	public void editElement(Element element, AsyncCallback<Element> callback);

	public void deleteElement(Element element, AsyncCallback<Void> callback);

	public void assignElement(Module module, Element element,
			AsyncCallback<Module> callback);

	public void createModule(AsyncCallback<Module> callback);

	public void editModule(Module module, AsyncCallback<Module> callback);

	public void deleteModule(Module module, AsyncCallback<Void> callback);

	public void assignModule(Module module, Module subModule,
			AsyncCallback<Module> callback);

	public void createProduct(AsyncCallback<Product> callback);

	public void editProduct(Product product, AsyncCallback<Product> callback);

	public void deleteProduct(Product product, AsyncCallback<Void> callback);

	public void findElementById(int id, AsyncCallback<Element> callback);

	public void findElementsByCreator(User creator,
			AsyncCallback<Vector<Element>> callback);

	public void findElementsByName(String name,
			AsyncCallback<Vector<Element>> callback);

	public void findPartlistByModuleName(String name,
			AsyncCallback<Partlist> callback);

	public void findPartlistByModuleId(int id, AsyncCallback<Partlist> callback);

	public void findPartlistById(int id, AsyncCallback<Partlist> callback);

	public void findPartlistByModule(Module module,
			AsyncCallback<Partlist> callback);

	public void getAllProducts(AsyncCallback<Vector<Product>> callback);

	public void calculateMaterial(Partlist partlist,
			AsyncCallback<Object> callback);
}
