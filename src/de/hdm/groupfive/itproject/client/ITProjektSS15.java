package de.hdm.groupfive.itproject.client;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.BusinessObject;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Product;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS15 implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		AdministrationCommonAsync administration = ClientsideSettings
				.getAdministration();

		// lädt den Login Dialog
		LoginLogout login = new LoginLogout();
		login.loadDialog();

		final HTML content = new HTML("Herzlich Willkommen :)");
		content.setStylePrimaryName("formTitle");
		RootPanel.get("main").clear();
		RootPanel.get("clientTitle").clear();

		RootPanel.get("main").add(content);
		RootPanel.get("main").add(createHistory());

		Button clientTitle = new Button("&nbsp;Editor & Viewer",
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						RootPanel.get("main").clear();
						RootPanel.get("main").add(content);
						RootPanel.get("main").add(createHistory());
					}
				});
		clientTitle.setStylePrimaryName("btn btn-link navbar-brand");
		RootPanel.get("clientTitle").add(clientTitle);
		// Such-Bereich einfügen

		// Anlegen-Button einfügen
		RootPanel.get("navbar").add(createCreateButton());

	}

	private Grid createHistory() {
		final Grid historyGrid = new Grid(16, 4);
		historyGrid.setStylePrimaryName("table table-striped");
		historyGrid.setWidget(0, 0, new HTML("<b>Nutzer</b>"));
		historyGrid.setWidget(0, 1, new HTML("<b>Element</b>"));
		historyGrid.setWidget(0, 2, new HTML("<b>Aktion</b>"));
		historyGrid.setWidget(0, 3, new HTML("<b>Änderungsdatum</b>"));
		for (int i = 1; i <= 15; i++) {
			historyGrid
					.setWidget(i, 0, new HTML("Olaf19" + Random.nextInt(99)));
			Button elementLink = new Button("Element " + Random.nextInt(),
					new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							Module m = new Module();
							m.setId(123);
							m.setName(event.getRelativeElement().getInnerText());
							m.setDescription("blablabla");
							m.setMaterialDescription("Metall");
							RootPanel.get("main").clear();
							RootPanel.get("main").add(new ElementForm(m));
						}
					});
			elementLink.setStylePrimaryName("elementLink");
			historyGrid.setWidget(i, 1, elementLink);
			historyGrid.setWidget(i, 2, new HTML("geändert"));
			historyGrid.setWidget(i, 3,
					new HTML("29.05.2015 15:" + Random.nextInt(59)));
		}
		return historyGrid;
	}



	private Widget createCreateButton() {
		Button createBtn = new Button("anlegen", new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("main").clear();
				RootPanel.get("main").add(new CreateElement());
			}
		});
		createBtn.setStylePrimaryName("btn btn-success");
		return createBtn;
	}

	public static Widget createSearchPanel() {
		// SUCHE BOX ANFANG
		final VerticalPanel searchPanel = new VerticalPanel();
		searchPanel.setStylePrimaryName("searchPanel");
		final FlowPanel searchInputPanel = new FlowPanel();
		final TextBox searchBox = new TextBox();
		searchBox.setStylePrimaryName("searchBox col-md-9 col-sm-9 col-xs-9");
		searchBox.setFocus(true);
		searchBox
				.setTitle("Suche nach Bauteil, Baugruppe oder Enderzeugnis ...");
		searchBox.getElement().setPropertyString("placeholder",
				"Suche nach Bauteil, Baugruppe oder Enderzeugnis ...");
		
		
		// LIVE-SUCH BOX ANFANG
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


					simplePopup.setVisible(true);
					resultsPanel.clear();
					for (int i = 0; i < 5; i++) {
						Hyperlink link = new Hyperlink();
						link.setText(searchBox.getValue());
						link.addClickHandler(new ClickHandler() {
							// TODO: Überarbeiten weil veraltet.
							@Override
							public void onClick(ClickEvent event) {

								simplePopup.setVisible(false);
								searchPanel.clear();
								searchPanel.add(searchInputPanel);
								searchPanel.add(simplePopup);
								searchPanel.add(new SearchResult(searchBox.getValue()
										.trim(), true));
							}
						});
						resultsPanel.add(link);
					}
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

		Button searchBtn = new Button("Suchen", new ClickHandler() {
			public void onClick(ClickEvent event) {

				simplePopup.setVisible(false);
				searchPanel.clear();
				searchPanel.add(searchInputPanel);
				searchPanel.add(simplePopup);
				searchPanel.add(new SearchResult(searchBox.getValue()
						.trim(), true));

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

	public Vector<String> getLevenshtein1(String word) {
		Vector<String> words = new Vector<String>();
		for (int i = 0; i < word.length(); i++) {
			// insertions
			words.add(word.substring(0, i) + '_' + word.substring(i));
			// deletions
			words.add(word.substring(0, i) + word.substring(i + 1));
			// substitutions
			words.add(word.substring(0, i) + '_' + word.substring(i + 1));
		}
		// last insertion
		words.add(word + '_');
		return words;
	}

}
