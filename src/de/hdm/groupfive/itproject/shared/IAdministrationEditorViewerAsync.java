package de.hdm.groupfive.itproject.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;
import de.hdm.groupfive.itproject.shared.IAdministrationEditorViewer;

/**
 * Das asynchrone Gegenstück des Interface {@link IAdministrationEditorViewer}.
 * Es wird semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher
 * erfolgt hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link IAdministrationEditorViewer}.
 * 
 * @author thies
 * @author fesseler (update)
 */

public interface IAdministrationEditorViewerAsync {
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
}
