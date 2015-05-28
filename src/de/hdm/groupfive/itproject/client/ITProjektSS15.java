package de.hdm.groupfive.itproject.client;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS15 implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
    	// lädt den Login Dialog 
    	LoginLogout login = new LoginLogout();
		login.loadDialog();
		
		HTML content = new HTML("Herzlich Willkommen :)");
    	RootPanel.get("main").clear();
    	RootPanel.get("main").add(content);
    	
		VerticalPanel searchPanel = new VerticalPanel();
		searchPanel.setStylePrimaryName("searchPanel");
		FlowPanel searchInputPanel = new FlowPanel();
		TextBox searchBox = new TextBox();
		searchBox.setStylePrimaryName("searchBox col-md-9 col-sm-9 col-xs-9");	
		//searchBox.setWidth("100%");
		searchBox.setFocus(true);
		searchBox.setTitle("Suche nach Bauteil, Baugruppe oder Enderzeugnis ...");
		searchBox.getElement().setPropertyString("placeholder", "Suche nach Bauteil, Baugruppe oder Enderzeugnis ...");
		
		Button searchBtn = new Button();
		
		searchBtn.setStylePrimaryName("btn btn-default col-md-2 col-sm-2 col-xs-2");
		searchBtn.setText("Suchen");
		searchInputPanel.add(searchBox);
		searchInputPanel.add(searchBtn);
		searchPanel.add(searchInputPanel);
		RootPanel.get("navigator").add(searchPanel);
		
		// erzeugt das Menü links auf der Seite
    	//createMenu();
	}
	
	
	private void createMenu() {
		 /*
	     * Ab hier bauen wir sukzessive den Navigator mit seinen Buttons aus.
	     */

	    /*
	     * Neues Button Widget erzeugen und eine Beschriftung festlegen.
	     */
	    final Hyperlink homeLink = new Hyperlink("Startseite", "mainpage");
	    homeLink.setStylePrimaryName("menubutton");
	    ClickHandler handler = new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	final HTML content = new HTML("Startseite geklickt :D");
	        	RootPanel.get("main").clear();
	        	RootPanel.get("main").add(content);
	        }
	    };
	    homeLink.addDomHandler(handler, ClickEvent.getType());
	    RootPanel.get("navigator").add(homeLink);
	    
	  
	    
	}
}
