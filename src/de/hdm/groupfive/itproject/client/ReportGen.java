package de.hdm.groupfive.itproject.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;

public class ReportGen implements EntryPoint {

	@Override
	public void onModuleLoad() {

		// lädt den Login Dialog 
    	LoginLogout.load();
    	
	}
	
	public Grid grid() {
    	
    	
    	de.hdm.groupfive.itproject.shared.bo.Element a1 = new de.hdm.groupfive.itproject.shared.bo.Element();
    	a1.setName("Bauteil1");
    	
    	de.hdm.groupfive.itproject.shared.bo.Element a2 = new de.hdm.groupfive.itproject.shared.bo.Element();
    	a2.setName("Bauteil1");
    	
    	de.hdm.groupfive.itproject.shared.bo.Element a3 = new de.hdm.groupfive.itproject.shared.bo.Element();
    	a3.setName("Bauteil1");
    	
    	Partlist testList = new Partlist();
    	testList.add(a1, 3);
    	testList.add(a2, 1);
    	testList.add(a3, 5);
    	
    	
    	final Grid g= new Grid(10,3);
    	g.setText(0,0, new String("ElementID"));
    	g.setText(0,1, new String("Name"));
    	g.setText(0,2, new String("Anzahl"));
    	for(int r = 0; r < testList.size() ; ++r){
    		for(PartlistEntry.PartlistEntry entry: Partlist.list){
    			
    		}
    		g.setWidget(r, 0, new Widget(testList.getId()));
    		g.setText(r, 1, new String(testList.getName()));
    		g.setWidget(r, 2, new Widget(testList.getAmount()));
    	}
    	return g;
	}

}
