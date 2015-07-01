package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.User;

public class History extends Showcase {

	private String headlineText;
	private String headlineTextStyle;

	public History() {
		this.headlineText = "Herzlich Willkommen "+ClientsideSettings.getCurrentUser().getUserName()+" :) - Historie";
		this.headlineTextStyle = "formTitle";
	}

	@Override
	protected String getHeadlineText() {
		return this.headlineText;
	}

	@Override
	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}

	@Override
	protected void run() {
		final Grid historyGrid = new Grid(14, 4);
		historyGrid.setStylePrimaryName("table table-striped");
		historyGrid.setWidget(0, 0, new HTML("<b>Nutzer</b>"));
		historyGrid.setWidget(0, 1, new HTML("<b>Element</b>"));
		historyGrid.setWidget(0, 2, new HTML("<b>Aktion</b>"));
		historyGrid.setWidget(0, 3, new HTML("<b>Änderungsdatum</b>"));
		for (int i = 1; i <= 12; i++) {
			historyGrid
					.setWidget(i, 0, new HTML("Olaf19" + Random.nextInt(99)));
			Button elementLink = new Button("Element " + Random.nextInt());
			elementLink.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					Module m = new Module();
					m.setId(123);
					m.setName(event.getRelativeElement().getInnerText());
					m.setDescription("blablabla");
					m.setMaterialDescription("Metall");
					RootPanel.get("main").clear();
					RootPanel.get("main").add(new ElementForm(m,1,null));
				}
			});
			elementLink.setStylePrimaryName("elementLink");
			historyGrid.setWidget(i, 1, elementLink);
			historyGrid.setWidget(i, 2, new HTML("geändert"));
			historyGrid.setWidget(i, 3,
					new HTML("29.05.2015 15:" + Random.nextInt(59)));
		}
		this.add(historyGrid);
	}

}
