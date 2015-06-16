package de.hdm.groupfive.itproject.shared;

import java.util.ArrayList;
import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;

import de.hdm.groupfive.itproject.server.db.ElementMapper;
import de.hdm.groupfive.itproject.server.db.ModuleMapper;
import de.hdm.groupfive.itproject.server.db.PartlistMapper;
import de.hdm.groupfive.itproject.server.db.ProductMapper;
import de.hdm.groupfive.itproject.server.db.UserMapper;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

public interface AdministrationCommonAsync {

	public void createElement(Element element, AsyncCallback<Element> callback);

	public void editElement(Element element, AsyncCallback<Element> callback);

	public void deleteElement(Element element, AsyncCallback<Void> callback);

	public void assignElement(Module module, Element element,
			AsyncCallback<Module> callback);

	public void createModule(Module module, AsyncCallback<Module> callback);

	public void editModule(Module module, AsyncCallback<Module> callback);

	public void deleteModule(Module module, AsyncCallback<Void> callback);

	public void assignModule(Module module, Module subModule,
			AsyncCallback<Module> callback);

	void createProduct(String salesName, Module module,
			AsyncCallback<Product> callback);

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

//	public void findPartlistByModule(Module module,
//			AsyncCallback<Partlist> callback);

	public void getAllProducts(AsyncCallback<Vector<Product>> callback);

	public void calculateMaterial(Partlist partlist,
			AsyncCallback<String> callback);

	public void registerUser(String email, String password,
			AsyncCallback<User> callback);

	public void loginUser(String email, String password, AsyncCallback<User> callback);

	public void logoutUser(AsyncCallback<Void> callback);

	public void setUser(User user, AsyncCallback<Void> callback);

	public void getUser(AsyncCallback<User> callback);

	public void init(AsyncCallback<Void> callback);
}
