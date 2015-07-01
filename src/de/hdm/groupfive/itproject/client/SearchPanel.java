package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;

public class SearchPanel {
		
	private VerticalPanel resultsPanel;
	private DecoratedPopupPanel simplePopup;
	private VerticalPanel searchPanel;
	private FlowPanel searchInputPanel;
	private SearchResult searchResult;
	private PartlistEntry entry;
	public static SearchPanel currentSearchPanel;
	
	public SearchPanel() {
		
	}
	
	public void load() {
		RootPanel.get("navigator").clear();
		RootPanel.get("navigator").add(createSearchPanel());
	}
	
	public VerticalPanel getSearchPanel() {
		return createSearchPanel();
	}
	
	public VerticalPanel getAssignPanel(PartlistEntry entry) {
		return createAssignPanel(entry);
	}

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

//		// LIVE-SUCH BOX ANFANG
//		simplePopup = new DecoratedPopupPanel(true);
//		simplePopup
//				.setStylePrimaryName("searchResultBox col-md-9 col-sm-9 col-xs-9");
		resultsPanel = new VerticalPanel();
//		simplePopup.setWidget(resultsPanel);
//		simplePopup.setVisible(false);
//		simplePopup.show();
//
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
//				
//
//				// LIVE-SUCH BOX ENDE
//
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					searchPanel.clear();
					searchPanel.add(searchInputPanel);
					//searchPanel.add(simplePopup);
					searchResult = new SearchResult(searchBox.getValue()
							.trim(), false);
					searchPanel.add(searchResult);
				}
//					simplePopup.setVisible(false);
//				} else {
//					if (searchBox.getValue().trim().length() > 3) {
//
//						
//						AdministrationCommonAsync administration = ClientsideSettings
//								.getAdministration();
//						
//						administration.findElementsByName(searchBox.getValue().trim(), 5, new InstantSearchCallback());
//						
//					
//					} else {
//						simplePopup.setVisible(false);
//						resultsPanel.clear();
//					}
//				}
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
//		searchPanel.add(simplePopup);

		// SUCH BOX ENDE
		
		searchResult = new SearchResult();
		searchPanel.add(searchResult);
		currentSearchPanel = this;
		return searchPanel;
	}
	
	public SearchResult getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

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

//		// LIVE-SUCH BOX ANFANG
//		simplePopup = new DecoratedPopupPanel(true);
//		simplePopup
//				.setStylePrimaryName("searchResultBox col-md-9 col-sm-9 col-xs-9");
		resultsPanel = new VerticalPanel();
//		simplePopup.setWidget(resultsPanel);
//		simplePopup.setVisible(false);
//		simplePopup.show();
//
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
//				
//
//				// LIVE-SUCH BOX ENDE
//
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					searchPanel.clear();
					searchPanel.add(searchInputPanel);
					searchResult = new SearchResult(searchBox.getValue().trim(),
							true);

					searchResult.disableLoadElementForm();
					searchPanel.add(searchResult);
				}
//
//					simplePopup.setVisible(false);
//				} else {
//					if (searchBox.getValue().trim().length() > 3) {
//
//						
//						AdministrationCommonAsync administration = ClientsideSettings
//								.getAdministration();
//						
//						administration.findModulesByName(searchBox.getValue().trim(), 5, new InstantSearchCallback());
//						
//					
//					} else {
//						simplePopup.setVisible(false);
//						resultsPanel.clear();
//					}
//				}
			}
		});

		Button searchBtn = new Button("Suchen");
		searchBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
//				simplePopup.setVisible(false);
				searchPanel.clear();
				searchPanel.add(searchInputPanel);
				searchResult = new SearchResult(searchBox.getValue().trim(),
						true);

				searchResult.disableLoadElementForm();
				searchPanel.add(searchResult);
//				searchPanel.add(simplePopup);

			}
		});
		searchBtn
				.setStylePrimaryName("btn btn-default col-md-2 col-sm-2 col-xs-2");

		searchInputPanel.add(searchBox);
		searchInputPanel.add(searchBtn);
		searchPanel.add(searchInputPanel);
//		searchPanel.add(simplePopup);

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
	
	
//	class InstantSearchCallback implements AsyncCallback<Partlist> {
//		
//		public InstantSearchCallback() {
//		}
//
//		@Override
//		public void onFailure(Throwable caught) {
//			//showcase.add(new ErrorMsg("<b>Error:</b> " + caught.getMessage()));
//			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
//		}
//
//		@Override
//		public void onSuccess(Partlist result) {
//			if (result != null) {
//				if (!result.isEmpty()) {
//					simplePopup.setVisible(true);
//					resultsPanel.clear();
//					for (final PartlistEntry pe : result.getAllEntries()) {
//						
//						Button link = new Button();
//						link.setStylePrimaryName("btn btn-link liveSearchBtn");
//						link.setText(pe.getElement().getName());
//						link.addClickHandler(new ClickHandler() {
//
//							@Override
//							public void onClick(ClickEvent event) {
//								// TODO Auto-generated method stub
//								simplePopup.setVisible(false);
//								searchPanel.clear();
//								searchPanel.add(searchInputPanel);
//								searchPanel.add(simplePopup);
//								
////								if (pe.getElement() instanceof Product) {
////									Product p = (Product)pe.getElement();
////									searchResult = new SearchResult(p.getSalesName(), p.getProductId(), );	
////								} else if (pe.getElement() instanceof Module) {
////									searchResult = new SearchResult(pe.getElement().getName(), pe.getElement().getId());
////								} else {
//									searchResult = new SearchResult(pe.getElement().getName(), pe.getElement().getId());
////								}
//								searchPanel.add(searchResult);
//							}
//						});
//
//						resultsPanel.add(link);
//					}
//				}
//			} 
//		}
//
//	}

}
