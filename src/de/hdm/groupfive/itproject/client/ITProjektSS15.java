package de.hdm.groupfive.itproject.client;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Random;
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

		// SUCHE ANFANG
		VerticalPanel searchPanel = new VerticalPanel();
		searchPanel.setStylePrimaryName("searchPanel");
		FlowPanel searchInputPanel = new FlowPanel();
		final TextBox searchBox = new TextBox();
		searchBox.setStylePrimaryName("searchBox col-md-9 col-sm-9 col-xs-9");
		searchBox.setFocus(true);
		searchBox
				.setTitle("Suche nach Bauteil, Baugruppe oder Enderzeugnis ...");
		searchBox.getElement().setPropertyString("placeholder",
				"Suche nach Bauteil, Baugruppe oder Enderzeugnis ...");
		

		final HTML resultInfo = new HTML("Bitte starten Sie eine Suche!");
		resultInfo.setStylePrimaryName("resultInfo");

		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
		simplePopup
				.setStylePrimaryName("searchResultBox col-md-9 col-sm-9 col-xs-9");
		final VerticalPanel resultsPanel = new VerticalPanel();
		simplePopup.setWidget(resultsPanel);
		simplePopup.setVisible(false);
		simplePopup.show();

		searchBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (searchBox.getValue().trim().length() > 2) {
					
					resultInfo.setHTML("Suchergebnisse für '" + searchBox.getValue().trim() + "'");
					
					
					simplePopup.setVisible(true);
					resultsPanel.clear();
					for (int i = 0; i < 5; i++) {
						Hyperlink link = new Hyperlink();
						link.setText(searchBox.getValue());

						resultsPanel.add(link);
					}
				} else {
					simplePopup.setVisible(false);
					resultsPanel.clear();
				}
				if (searchBox.getValue().trim().length() <= 2) {
					resultInfo.setHTML("Bitte starten Sie eine Suche!");
				}
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					// TODO: Suche ausführen weil ENTER gedrückt wurde
					simplePopup.setVisible(false);
					resultsPanel.clear();
		        }
			}
		});

		Button searchBtn = new Button("Suchen", new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				simplePopup.setVisible(false);
				resultsPanel.clear();

			}
		});
		searchBtn
				.setStylePrimaryName("btn btn-default col-md-2 col-sm-2 col-xs-2");
		
		
		
		searchInputPanel.add(searchBox);
		searchInputPanel.add(searchBtn);
		searchPanel.add(searchInputPanel);
		searchPanel.add(simplePopup);

		// SUCHE ENDE
		searchPanel.add(resultInfo);
		// BAUMSTRUKTUR (ERGEBNISSE) ANFANG
		final Tree dynamicTree = createDynamicTree();
	    ScrollPanel dynamicTreeWrapper = new ScrollPanel(dynamicTree);
	    dynamicTreeWrapper.setStylePrimaryName("tree");
	    searchPanel.add(dynamicTreeWrapper);
		// BAUMSTRUKTUR ENDE

		RootPanel.get("navigator").add(searchPanel);
		
	    
	    
	    
		Button createBtn = new Button("anlegen", new ClickHandler() {
			public void onClick(ClickEvent event) {
				FlowPanel panel = new FlowPanel();
				
				Button createElementBtn = new Button("Bauteil anlagen", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						
					}
				});
				Button createModuleBtn = new Button("Baugruppe anlagen", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						
					}
				});
				Button createProductBtn = new Button("Produkt anlagen", new ClickHandler() {
					
					@Override
					public void onClick(ClickEvent event) {
						// TODO Auto-generated method stub
						
					}
				});
				createElementBtn.setStylePrimaryName("btn btn-default createBtn");
				createModuleBtn.setStylePrimaryName("btn btn-default createBtn");
				createProductBtn.setStylePrimaryName("btn btn-default createBtn");
				panel.add(createElementBtn);
				panel.add(createModuleBtn);
				panel.add(createProductBtn);
				RootPanel.get("main").clear();

				final HTML resultInfo = new HTML("Bitte wählen Sie aus, was Sie anlegen möchten!");
				resultInfo.setStylePrimaryName("formTitle");
				RootPanel.get("main").add(resultInfo);
				
				
				RootPanel.get("main").add(panel);
			}
		});
		createBtn.setStylePrimaryName("btn btn-success");
		
		RootPanel.get("navbar").add(createBtn);
	}

	private Tree createDynamicTree() {
		// Create a new tree
		Tree dynamicTree = new Tree();
		dynamicTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				// TODO Auto-generated method stub
			}
		});

		// Add some default tree items
		for (int i = 0; i < 5; i++) {
			TreeItem item = dynamicTree.addTextItem("Item " + i);

			// Temporarily add an item so we can expand this node
			item.addTextItem("");
		}

		// Add a handler that automatically generates some children
		dynamicTree.addOpenHandler(new OpenHandler<TreeItem>() {
			public void onOpen(OpenEvent<TreeItem> event) {
				TreeItem item = event.getTarget();
				if (item.getChildCount() == 1) {
					// Close the item immediately
					item.setState(false, false);

					// Add a random number of children to the item
					String itemText = item.getText();
					int numChildren = Random.nextInt(5) + 2;
					for (int i = 0; i < numChildren; i++) {
						TreeItem child = item.addTextItem(itemText + "." + i);
						child.addTextItem("");
					}

					// Remove the temporary item when we finish loading
					item.getChild(0).remove();

					// Reopen the item
					item.setState(true, false);
				}
			}
		});

		// Return the tree
		return dynamicTree;
	}
}
