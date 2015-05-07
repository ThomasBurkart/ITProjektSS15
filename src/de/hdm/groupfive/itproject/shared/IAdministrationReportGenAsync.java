package de.hdm.groupfive.itproject.shared;

import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;


public interface IAdministrationReportGenAsync {
	public void findPartlistByModuleName(String name);
	public void findPartlistByModuleId(int id);
	public void findPartlistById(int id);
	public void findPartlistByModule(Module module);
	public void getAllProducts();
	public void calculateMaterial(Partlist partlist);
}
