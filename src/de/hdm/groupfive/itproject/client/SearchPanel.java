package de.hdm.groupfive.itproject.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;

public class SearchPanel {
		
	private static VerticalPanel resultsPanel;
	private static DecoratedPopupPanel simplePopup;
	private static VerticalPanel searchPanel;
	private static FlowPanel searchInputPanel;
	
	public static void load() {
		RootPanel.get("navigator").clear();
		RootPanel.get("navigator").add(create());
	}
	
	public static VerticalPanel getSearchPanel() {
		return create();
	}

	private static VerticalPanel create() {
		// SUCHE BOX ANFANG
		searchPanel = new VerticalPanel();
		searchPanel.setStylePrimaryName("searchPanel");
		searchInputPanel = new FlowPanel();
		final TextBox searchBox = new TextBox();
		searchBox.setStylePrimaryName("searchBox col-md-9 col-sm-9 col-xs-9");
		searchBox.setFocus(true);
		searchBox
				.setTitle("Suche nach Bauteil, Baugruppe oder Enderzeugnis ...");
		searchBox.getElement().setPropertyString("placeholder",
				"Suche nach Bauteil, Baugruppe oder Enderzeugnis ...");

		// LIVE-SUCH BOX ANFANG
		simplePopup = new DecoratedPopupPanel(true);
		simplePopup
				.setStylePrimaryName("searchResultBox col-md-9 col-sm-9 col-xs-9");
		resultsPanel = new VerticalPanel();
		simplePopup.setWidget(resultsPanel);
		simplePopup.setVisible(false);
		simplePopup.show();

		searchBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (searchBox.getValue().trim().length() > 3) {

					
					AdministrationCommonAsync administration = ClientsideSettings
							.getAdministration();
					
					administration.findElementsByName(searchBox.getValue().trim(), 5, new InstantSearchCallback());
					
				
				} else {
					simplePopup.setVisible(false);
					resultsPanel.clear();
				}

				// LIVE-SUCH BOX ENDE

				if (searchBox.getValue().trim().length() <= 2) {
					// resultInfo.setHTML("Bitte starten Sie eine Suche!");
				}
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					// TODO: Suche ausführen weil ENTER gedrückt wurde

					simplePopup.setVisible(false);
					searchPanel.clear();
					searchPanel.add(searchInputPanel);
					searchPanel.add(simplePopup);
					searchPanel.add(new SearchResult(searchBox.getValue()
							.trim(), true));
				}
			}
		});

		Button searchBtn = new Button("Suchen");
		searchBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				simplePopup.setVisible(false);
				searchPanel.clear();
				searchPanel.add(searchInputPanel);
				searchPanel.add(simplePopup);
				searchPanel.add(new SearchResult(searchBox.getValue().trim(),
						true));

			}
		});
		searchBtn
				.setStylePrimaryName("btn btn-default col-md-2 col-sm-2 col-xs-2");

		searchInputPanel.add(searchBox);
		searchInputPanel.add(searchBtn);
		searchPanel.add(searchInputPanel);
		searchPanel.add(simplePopup);

		// SUCH BOX ENDE

		searchPanel.add(new SearchResult());
		
		return searchPanel;
	}
	
	static class InstantSearchCallback implements AsyncCallback<Partlist> {
		
		public InstantSearchCallback() {
		}

		@Override
		public void onFailure(Throwable caught) {
			//showcase.add(new ErrorMsg("<b>Error:</b> " + caught.getMessage()));
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Partlist result) {
			if (result != null) {
				if (!result.isEmpty()) {
					simplePopup.setVisible(true);
					resultsPanel.clear();
					for (final PartlistEntry pe : result.getAllEntries()) {
						
						Button link = new Button();
						link.setStylePrimaryName("btn btn-link liveSearchBtn");
						link.setText(pe.getElement().getName());
						link.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								// TODO Auto-generated method stub
								simplePopup.setVisible(false);
								searchPanel.clear();
								searchPanel.add(searchInputPanel);
								searchPanel.add(simplePopup);
								searchPanel.add(new SearchResult(pe.getElement().getName(), pe.getElement().getId()));
							}
						});

						resultsPanel.add(link);
					}
				}
			} 
		}

	}

}
