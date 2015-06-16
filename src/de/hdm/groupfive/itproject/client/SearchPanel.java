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

public class SearchPanel {

	public static void load() {
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
						Button link = new Button();
						link.setStylePrimaryName("btn btn-link liveSearchBtn");
						link.setText(searchBox.getValue());
						link.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								// TODO Auto-generated method stub
								simplePopup.setVisible(false);
								searchPanel.clear();
								searchPanel.add(searchInputPanel);
								searchPanel.add(simplePopup);
								searchPanel.add(new SearchResult(searchBox
										.getValue().trim(), true));
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
		RootPanel.get("navigator").clear();
		RootPanel.get("navigator").add(searchPanel);
	}
}
