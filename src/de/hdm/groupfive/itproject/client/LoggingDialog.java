package de.hdm.groupfive.itproject.client;

import java.util.logging.Logger;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.logging.client.HasWidgetsLogHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;


public class LoggingDialog {
	private static DialogBox dialog;
	
	public static DialogBox getDialogBox() {
		if (dialog == null) {
			dialog = new DialogBox();
			dialog.setHTML("Log-Information<div style=\"float: right; cursor: pointer;\">x</div>");
			Button closeBtn = new Button("x");
			closeBtn.setStylePrimaryName("btn btn-link");
			closeBtn.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					dialog.hide();
				}
			});
			
			
			
			dialog.getCaption().addMouseDownHandler(new MouseDownHandler() {
				
				@Override
				public void onMouseDown(MouseDownEvent event) {
					// TODO Auto-generated method stub
					dialog.hide();
				}
			});
			
			dialog.setModal(true);
			dialog.setWidth("100%");
			VerticalPanel logPanel = new VerticalPanel();
			logPanel.getElement().setId("logDialogBox");
			
			//logPanel.add(new HTML("test"));
			ScrollPanel scrollPanel = new ScrollPanel(logPanel);
			scrollPanel.setWidth("300px");
			scrollPanel.setHeight("350px");
			
			HorizontalPanel contentPane = new HorizontalPanel();
			contentPane.add(scrollPanel);
			
			VerticalPanel infoPane = new VerticalPanel();
			infoPane.add(new HTML("<b>Impressum</b><br /><br />Diese Strukturstücklisten Management Web-Applikation wurde im Rahmen eines <a href=\"https://www.hdm-stuttgart.de/studenten/stundenplan/vorlesungsverzeichnis/vorlesung_detail?vorlid=5211691\" target=\"_blank\">IT-Projekts</a> des Studiengangs <a href=\"http://www.wi.hdm-stuttgart.de\" target=\"_blank\">Wirtschaftsinformatik und digitale Medien</a>,<br />an der <a href=\"http://www.hdm-stuttgart.de\" target=\"_blank\">Hochschule der Medien</a>, unter Leitung<br />von <a href=\"http://www.prof-thies.de\" target=\"_blank\">Herrn Prof. Dr. Thies</a>, von <b>Gruppe 6</b> erstellt.<br /><br /><i>Mitglieder:</i>"));
			Grid memberGrid = new Grid(6, 2);
			memberGrid.setStylePrimaryName("memberGrid");
			memberGrid.setHTML(0, 0, "Samire Jakupi");
			memberGrid.setHTML(0, 1, "000000");
			memberGrid.setHTML(1, 0, "Fabian Wenzel");
			memberGrid.setHTML(1, 1, "27997");
			memberGrid.setHTML(2, 0, "Michael Kennerknecht");
			memberGrid.setHTML(2, 1, "27975");
			memberGrid.setHTML(3, 0, "Joel Siffermann");
			memberGrid.setHTML(3, 1, "000000");
			memberGrid.setHTML(4, 0, "Timo Fesseler");
			memberGrid.setHTML(4, 1, "28001");
			memberGrid.setHTML(5, 0, "Thomas Burkart");
			memberGrid.setHTML(5, 1, "27961");
			infoPane.add(memberGrid);
			infoPane.add(new HTML(""));
			infoPane.setWidth("300px");
			contentPane.add(infoPane);
			dialog.add(contentPane);
			dialog.setGlassEnabled(true);
			dialog.setAnimationEnabled(true);
			dialog.center();
			dialog.hide();
			
			
			Logger logger = ClientsideSettings.getLogger();
			logger.addHandler(new HasWidgetsLogHandler(logPanel));
			
			logger.info("Logger Dialog Box initialisiert.");
		}
		return dialog;
	}
}
