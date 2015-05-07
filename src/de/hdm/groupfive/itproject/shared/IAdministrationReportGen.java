package de.hdm.groupfive.itproject.shared;

import java.util.Vector;
import de.hdm.groupfive.itproject.shared.bo.*;

public interface IAdministrationReportGen {
	public Partlist findPartlistByModuleName(String name);

	public Partlist findPartlistByModuleId(int id);

	public Partlist findPartlistById(int id);

	public Partlist findPartlistByModule(Module module);

	public Vector<Product> getAllProducts();

	public Object calculateMaterial(Partlist partlist);
}
