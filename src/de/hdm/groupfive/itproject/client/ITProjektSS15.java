package de.hdm.groupfive.itproject.client;

import com.google.gwt.core.client.EntryPoint;
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
		
		HTML content = new HTML("Herzlich Willkommen :)");
    	RootPanel.get("main").clear();
    	RootPanel.get("main").add(content);
		
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
	    
	    final Hyperlink link2 = new Hyperlink("Link 2", "test1");
	    link2.setStylePrimaryName("menubutton"); 
	    ClickHandler handler2 = new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	final HTML content = new HTML("Link 2 geklickt :D");
	        	RootPanel.get("main").clear();
	        	RootPanel.get("main").add(content);
	        }
	    };
	    link2.addDomHandler(handler2, ClickEvent.getType());
	    RootPanel.get("navigator").add(link2);
	    

	    final Hyperlink link3 = new Hyperlink("Link 3", "test2");
	    link3.setStylePrimaryName("menubutton");
	    ClickHandler handler3 = new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	final HTML content = new HTML("Link 3 geklickt :P");
	        	RootPanel.get("main").clear();
	        	RootPanel.get("main").add(content);
	        }
	    };
	    link3.addDomHandler(handler3, ClickEvent.getType());
	    RootPanel.get("navigator").add(link3);
	    

	    final Hyperlink link4 = new Hyperlink("Link 4", "test2");
	    link4.setStylePrimaryName("menubutton");
	    ClickHandler handler4 = new ClickHandler() {
	        public void onClick(ClickEvent event) {
	        	final HTML content = new HTML("Link 4 geklickt ;)");
	        	RootPanel.get("main").clear();
	        	RootPanel.get("main").add(content);
	        }
	    };
	    link4.addDomHandler(handler4, ClickEvent.getType());
	    RootPanel.get("navigator").add(link4);

	}
}
