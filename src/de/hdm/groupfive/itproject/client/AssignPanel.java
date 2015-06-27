package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AssignPanel extends Showcase {

	private String headlineText;
	private String headlineTextStyle;

	public AssignPanel() {
		this.headlineText = "Bitte suchen oder wählen sie eine Baugruppe zum zuordnen.";

		this.headlineTextStyle = "formTitle";
	}

	@Override
	protected String getHeadlineText() {
		// TODO Auto-generated method stub
		return this.headlineText;
	}

	@Override
	protected String getHeadlineTextStyle() {
		// TODO Auto-generated method stub
		return this.headlineTextStyle;
	}

	@Override
	protected void run() {
		this.add(SearchPanel.getSearchPanel());

		// ACTION BUTTONS für mögliche Aktionen ANFANG
		FlowPanel panel = new FlowPanel();
		panel.setStylePrimaryName("actionBox");

		Button assignBtn = new Button("zuordnen");
		assignBtn.setStylePrimaryName("btn btn-success createBtn");
		assignBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// onSuccess/OnFailure Handling?!
				
				// TODO: Zuordnen
			}
		});
		assignBtn.setWidth("100px");
		Button cancelBtn = new Button("abbrechen");
		cancelBtn.setStylePrimaryName("btn btn-warning createBtn");

		cancelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
			}
		});
		cancelBtn.setWidth("100px");
		HorizontalPanel actionPanel = new HorizontalPanel();
		// TODO Reg Button evtl. entfernen
		// actionPanel.add(regButton);
		panel.add(cancelBtn);
		panel.add(assignBtn);
		this.add(panel);
			

	}

}
