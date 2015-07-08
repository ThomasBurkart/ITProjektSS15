package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;

/**
 * Die Klasse beinhaltet ein Showcase, in dem der User eine Suche durchführen kann,
 * dabei kann der User nach Bauteilen, Baugruppen oder Enderzeugnissen suchen
 * 
 * @author Thomas Burkart
 * @version 1.0
 * @since 07.07.2015
 */
public class SearchPanel {
	
	/**
	 * Feld für die Ergebnisse
	 */
	private VerticalPanel resultsPanel;
	/**
	 * Feld(Panel) für die Suche
	 */
	private VerticalPanel searchPanel;
	/**
	 * Eingabesuchfeld 
	 */
	private FlowPanel searchInputPanel;
	/**
	 * Ergebnissuche 
	 */
	private SearchResult searchResult;
	/**
	 * Das Element, das im Formular geladen wird
	 */
	private PartlistEntry entry;
	/**
	 * Aktueller Suchpanel 
	 */
	public static SearchPanel currentSearchPanel;
	
	/**
	 * Konstruktor der Klasse SearchPanel um eine Suche zu ermöglichen
	 */
	public SearchPanel() {
		resultsPanel = null;
	}
	
	/**
	 * Methode um den Navigator zu laden.
	 */
	public void load() {
		RootPanel.get("navigator").clear();
		RootPanel.get("navigator").add(createSearchPanel());
	}
	
	/**
	 * Erstellt eine Suche für den Report Generator
	 */
	public void loadForReportGen() {
		RootPanel.get("navigator").clear();
		RootPanel.get("navigator").add(createSearchPanelForReportGen());
	}
	
	/**
	 * gibt der nächsten Methode den Auftrag ein Suchfeld zu erzeugen
	 */
	public VerticalPanel getSearchPanel() {
		return createSearchPanel();
	}
	
	/**
	 * Eintrag wird übergeben
	 */
	public VerticalPanel getAssignPanel(PartlistEntry entry) {
		return createAssignPanel(entry);
	}

	/**
	 * In dieser Methode wird das Suchfelderstellt
	 * @return
	 * 	Suchfeld
	 */
	private VerticalPanel createSearchPanel() {
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

		resultsPanel = new VerticalPanel();
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					searchPanel.clear();
					searchPanel.add(searchInputPanel);
					searchResult = new SearchResult(searchBox.getValue()
							.trim(), false);
					searchPanel.add(searchResult);
				}
			}
		});

		Button searchBtn = new Button("Suchen");
		searchBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				searchPanel.clear();
				searchPanel.add(searchInputPanel);
				searchResult = new SearchResult(searchBox.getValue().trim(),
						false);
				searchPanel.add(searchResult);
			}
		});
		searchBtn
				.setStylePrimaryName("btn btn-default col-md-2 col-sm-2 col-xs-2");

		searchInputPanel.add(searchBox);
		searchInputPanel.add(searchBtn);
		searchPanel.add(searchInputPanel);

		// SUCH BOX ENDE
		
		searchResult = new SearchResult();
		searchPanel.add(searchResult);
		currentSearchPanel = this;
		return searchPanel;
	}
	
	/**
	 * Suchfeld für den Report Generator wird erzeugt
	 * @return
	 * 	Suchfeld für Report Generator
	 */
	private VerticalPanel createSearchPanelForReportGen() {
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

		resultsPanel = new VerticalPanel();
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					searchPanel.clear();
					searchPanel.add(searchInputPanel);
					searchResult = new SearchResult(searchBox.getValue()
							.trim(), false);
					searchResult.enableReportGen();
					searchPanel.add(searchResult);
				}
			}
		});

		Button searchBtn = new Button("Suchen");
		searchBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				searchPanel.clear();
				searchPanel.add(searchInputPanel);
				searchResult = new SearchResult(searchBox.getValue().trim(),
						false);

				searchResult.enableReportGen();
				searchPanel.add(searchResult);
			}
		});
		searchBtn
				.setStylePrimaryName("btn btn-default col-md-2 col-sm-2 col-xs-2");

		searchInputPanel.add(searchBox);
		searchInputPanel.add(searchBtn);
		searchPanel.add(searchInputPanel);
		searchResult = new SearchResult();
		searchResult.enableReportGen();
		searchPanel.add(searchResult);
		currentSearchPanel = this;
		return searchPanel;
	}
	
	/**
	 * Gibt das Suchergebnis zurück
	 * @return
	 * 		Suchergebnis
	 */
	public SearchResult getSearchResult() {
		return searchResult;
	}

	/**
	 * Setzten des Suchergebnisses
	 * @param searchResult
	 * 		zu übergebendes Suchergebniss
	 */
	public void setSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

	/**
	 * Erzeugt ein Zuordnungsfeld
	 * @param entry
	 * 		Eintrag/Element der zugeordnet werden soll
	 * @return
	 * 	Suchfeld
	 */
	private VerticalPanel createAssignPanel(PartlistEntry entry) {
		// SUCHE BOX ANFANG
		searchPanel = new VerticalPanel();
		searchPanel.setStylePrimaryName("searchPanel");
		searchInputPanel = new FlowPanel();
		final TextBox searchBox = new TextBox();
		searchBox.setStylePrimaryName("searchBox col-md-9 col-sm-9 col-xs-9");
		searchBox.setFocus(true);
		searchBox
				.setTitle("Suche nach Baugruppe oder Enderzeugnis ...");
		searchBox.getElement().setPropertyString("placeholder",
				"Suche nach Baugruppe oder Enderzeugnis ...");

		resultsPanel = new VerticalPanel();
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					searchPanel.clear();
					searchPanel.add(searchInputPanel);
					searchResult = new SearchResult(searchBox.getValue().trim(),
							true);

					searchResult.disableLoadElementForm();
					searchPanel.add(searchResult);
				}
			}
		});

		Button searchBtn = new Button("Suchen");
		searchBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				searchPanel.clear();
				searchPanel.add(searchInputPanel);
				searchResult = new SearchResult(searchBox.getValue().trim(),
						true);
				searchResult.disableLoadElementForm();
				searchPanel.add(searchResult);
			}
		});
		searchBtn
				.setStylePrimaryName("btn btn-default col-md-2 col-sm-2 col-xs-2");

		searchInputPanel.add(searchBox);
		searchInputPanel.add(searchBtn);
		searchPanel.add(searchInputPanel);

		// SUCH BOX ENDE
		
		if (entry.getSuperModule() != null) {
			searchResult = new SearchResult(entry.getSuperModule().getName(), entry.getSuperModule().getId());
		} else {
			searchResult = new SearchResult();
		}
		searchResult.disableLoadElementForm();
		searchPanel.add(searchResult);

		return searchPanel;
	}
}
