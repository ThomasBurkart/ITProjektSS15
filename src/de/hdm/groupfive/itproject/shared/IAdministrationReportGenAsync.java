package de.hdm.groupfive.itproject.shared;

import java.util.Vector;

import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.IAdministrationReportGen;

/**
 * Das asynchrone Gegenstück des Interface {@link IAdministrationReportGen}. Es
 * wird semiautomatisch durch das Google Plugin erstellt und gepflegt. Daher
 * erfolgt hier keine weitere Dokumentation. Für weitere Informationen siehe das
 * synchrone Interface {@link IAdministrationReportGen}.
 * 
 * @author thies
 * @author fesseler (update)
 */

public interface IAdministrationReportGenAsync {
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
