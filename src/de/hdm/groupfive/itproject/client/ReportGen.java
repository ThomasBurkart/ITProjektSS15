package de.hdm.groupfive.itproject.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;

public class ReportGen implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		
		final Hyperlink homeLabel = new Hyperlink("ReportGEn", "mainpage");
		   
	    RootPanel.get("homelabel").add(homeLabel);

	}

}
