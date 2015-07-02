package de.hdm.groupfive.itproject.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.User;

public class History extends Showcase {

	private String headlineText;
	private String headlineTextStyle;
	
	private Showcase currentShowcase;

	public History() {
		this.headlineText = "Herzlich Willkommen "+ClientsideSettings.getCurrentUser().getUserName()+" :) - Historie";
		this.headlineTextStyle = "formTitle";
		currentShowcase = this;
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
		AdministrationCommonAsync administration = ClientsideSettings
				.getAdministration();
		
//		administration.getLastUpdatesForHistory(new HistoryCallback());		
	}

//	class HistoryCallback implements AsyncCallback<ArrayList<String[]>> {
//
//		/** Showcase in dem die Antwort des Callbacks eingefügt wird. */
//
//		/**
//		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
//		 * das übergebene Showcase fest.
//		 * 
//		 * @param c
//		 *            Showcase an das die Rückmeldung ausgegeben wird.
//		 */
//		public HistoryCallback() {
//		}
//
//		/**
//		 * Wenn der asynchrone Aufruf fehlschlug oder das Element nicht gelöscht
//		 * werden konnte wird die onFailure Methode aufgerufen und der Fehler
//		 * als ErrorMsg dem Showcase eingefügt, sowie im Client-Logger
//		 * verzeichnet.
//		 */
//		@Override
//		public void onFailure(Throwable caught) {
//
//			ClientsideSettings.getLogger().severe(
//					"Error: " + caught.getMessage());
//		}
//
//		/**
//		 * Wenn der asynchrone Aufruf zum löschen des Elements erfolgreich war,
//		 * wird eine SuccessMsg im Showcase eingefügt.
//		 */
//		@Override
//		public void onSuccess(ArrayList<String[]> result) {
//			
//			
//			final Grid historyGrid = new Grid(14, 4);
//			historyGrid.setStylePrimaryName("table table-striped");
//			historyGrid.setWidget(0, 0, new HTML("<b>Nutzer</b>"));
//			historyGrid.setWidget(0, 1, new HTML("<b>Element</b>"));
//			historyGrid.setWidget(0, 2, new HTML("<b>Aktion</b>"));
//			historyGrid.setWidget(0, 3, new HTML("<b>Änderungsdatum</b>"));
//			int i = 0;
//			for (String[] resultSet : result) {
//				i++;
//				historyGrid
//						.setWidget(i, 0, new HTML(resultSet[0]));
////				Button elementLink = new Button("Element " + Random.nextInt());
////				elementLink.addClickHandler(new ClickHandler() {
////
////					@Override
////					public void onClick(ClickEvent event) {
////						Module m = new Module();
////						m.setId(123);
////						m.setName(event.getRelativeElement().getInnerText());
////						m.setDescription("blablabla");
////						m.setMaterialDescription("Metall");
////						RootPanel.get("main").clear();
////						RootPanel.get("main").add(new ElementForm(m,1,null));
////					}
////				});
////				elementLink.setStylePrimaryName("elementLink");
//				historyGrid.setWidget(i, 1, new HTML(resultSet[3]));
//				historyGrid.setWidget(i, 2, new HTML(resultSet[1]));
//				historyGrid.setWidget(i, 3, new HTML(resultSet[2]));
//			}
//			currentShowcase.add(historyGrid);
//		}
//	}
}
