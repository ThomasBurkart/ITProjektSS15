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
	    
	  
	    
	    DecoratedStackPanel stackPanel = new DecoratedStackPanel();
	    stackPanel.setWidth("100%");
	    stackPanel.setStylePrimaryName("menupanel");
	    
	    String elementHeader = getHeaderString("+ Bauteil");
	    stackPanel.add(createElementLinks(), elementHeader, true);
	    String elementHeader2 = getHeaderString("+ Baugruppe");
	    stackPanel.add(createModuleLinks(), elementHeader2, true);
	    String elementHeader3 = getHeaderString("+ Enderzeugnis");
	    stackPanel.add(createElementLinks(), elementHeader3, true);

	    RootPanel.get("navigator").add(stackPanel);
	}
	
	private VerticalPanel createElementLinks() {
		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("100%");
		final Hyperlink addLink = new Hyperlink("erstellen", "addElement");
		addLink.setStylePrimaryName("menubutton"); 
		ClickHandler addClickHandler = new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	final HTML content = new HTML("Bauteil erstellen geklickt :D");
		    	RootPanel.get("main").clear();
		    	RootPanel.get("main").add(content);
		    }
		};
		addLink.addDomHandler(addClickHandler, ClickEvent.getType());
		panel.add(addLink);
		
		final Hyperlink editLink = new Hyperlink("ändern", "editElement");
		editLink.setStylePrimaryName("menubutton"); 
		ClickHandler editClickHandler = new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	final HTML content = new HTML("Bauteil ändern geklickt :P");
		    	RootPanel.get("main").clear();
		    	RootPanel.get("main").add(content);
		    }
		};
		editLink.addDomHandler(editClickHandler, ClickEvent.getType());
		panel.add(editLink);
		
		final Hyperlink deleteLink = new Hyperlink("löschen", "deleteElement");
		deleteLink.setStylePrimaryName("menubutton"); 
		ClickHandler deleteClickHandler = new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	final HTML content = new HTML("Bauteil löschen geklickt :D");
		    	RootPanel.get("main").clear();
		    	RootPanel.get("main").add(content);
		    }
		};
		deleteLink.addDomHandler(deleteClickHandler, ClickEvent.getType());
		panel.add(deleteLink);
		return panel;
	}
	
	private VerticalPanel createModuleLinks() {
		VerticalPanel panel = new VerticalPanel();
		panel.setWidth("100%");
		final Hyperlink addLink = new Hyperlink("erstellen", "addModule");
		addLink.setStylePrimaryName("menubutton"); 
		ClickHandler addClickHandler = new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	final HTML content = new HTML("Baugruppe erstellen geklickt :D");
		    	RootPanel.get("main").clear();
		    	RootPanel.get("main").add(content);
		    }
		};
		addLink.addDomHandler(addClickHandler, ClickEvent.getType());
		panel.add(addLink);
		
		final Hyperlink editLink = new Hyperlink("ändern", "editModule");
		editLink.setStylePrimaryName("menubutton"); 
		ClickHandler editClickHandler = new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	final HTML content = new HTML("Baugruppe �ndern geklickt :D");
		    	RootPanel.get("main").clear();
		    	RootPanel.get("main").add(content);
		    }
		};
		editLink.addDomHandler(editClickHandler, ClickEvent.getType());
		panel.add(editLink);
		
		final Hyperlink deleteLink = new Hyperlink("löschen", "deleteModule");
		deleteLink.setStylePrimaryName("menubutton"); 
		ClickHandler deleteClickHandler = new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	final HTML content = new HTML("Baugruppe löschen geklickt :D");
		    	RootPanel.get("main").clear();
		    	RootPanel.get("main").add(content);
		    }
		};
		deleteLink.addDomHandler(deleteClickHandler, ClickEvent.getType());
		panel.add(deleteLink);
		
		final Hyperlink defineLink = new Hyperlink("zuordnen", "defineModule");
		defineLink.setStylePrimaryName("menubutton"); 
		ClickHandler defineClickHandler = new ClickHandler() {
		    public void onClick(ClickEvent event) {
		    	final HTML content = new HTML("Baugruppe zuordnen geklickt :D");
		    	RootPanel.get("main").clear();
		    	RootPanel.get("main").add(content);
		    }
		};
		defineLink.addDomHandler(defineClickHandler, ClickEvent.getType());
		panel.add(defineLink);
		
		return panel;
	}
	
	/**
	   * Create the {@link Tree} of Mail options.
	   *
	   * @param images the {@link Images} used in the Mail options
	   * @return the {@link Tree} of mail options
	   */
	  
	private String getHeaderString(String text) {
	    // Add the image and text to a horizontal panel
	    HorizontalPanel hPanel = new HorizontalPanel();
	    hPanel.setSpacing(0);
	    hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
	    HTML headerText = new HTML(text);
	    hPanel.add(headerText);

	    // Return the HTML string for the panel
	    return hPanel.getElement().getString();
	  }
}
