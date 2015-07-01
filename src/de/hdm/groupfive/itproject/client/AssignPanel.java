package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.groupfive.itproject.client.SearchResult.SearchAllProductsCallback;
import de.hdm.groupfive.itproject.server.db.ModuleMapper;
import de.hdm.groupfive.itproject.server.db.PartlistMapper;
import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;
import de.hdm.groupfive.itproject.shared.bo.Product;

public class AssignPanel extends Showcase {

	private String headlineText;
	private String headlineTextStyle;
	private PartlistEntry entry;
	private Showcase currentShowcase;

	public AssignPanel(PartlistEntry e) {
		this.entry = e;
		
		this.headlineText = "Bitte suchen oder wählen sie eine Baugruppe zum zuordnen.";

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
		final SearchPanel sp = new SearchPanel();
		this.add(sp.getAssignPanel());
		
		Grid grid = new Grid(1,4);
		
		HTML amountText = new HTML("Anzahl");
		amountText.setStylePrimaryName("col-md-2 col-sm-2 col-xs-2 amountText");
		grid.setWidget(0, 0, amountText);
		
		final TextBox amountTb = new TextBox();
		amountTb.setValue(this.entry.getAmount() > 1 ? ""+this.entry.getAmount() : "1");
		amountTb.setStylePrimaryName("col-md-2 col-sm-2 col-xs-2 textBox");
		
		grid.setWidget(0, 1, amountTb);
		
		// ACTION BUTTONS für mögliche Aktionen ANFANG
		FlowPanel panel = new FlowPanel();
		panel.setStylePrimaryName("actionBox");

		Button assignBtn = new Button("zuordnen");
		assignBtn.setStylePrimaryName("btn btn-success createBtn");
		assignBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				for(PartlistEntry pe : sp.getSearchResult().getSelectionModel().getSelectedSet()) {
					AdministrationCommonAsync administration = ClientsideSettings
							.getAdministration();
					if (pe.getElement() instanceof Product) {
						administration.assignModule((Module)entry.getElement(), (Module)pe.getElement(), Integer.parseInt(amountTb.getValue()), new ElementAssignCallback());
						
							// Modul mit Produkt verbinden
					} else if (pe.getElement() instanceof Module) {
						// Element oder Modul mit Modul verbinden
						if (entry.getElement() instanceof Module) {
							administration.assignModule((Module)pe.getElement(), (Module)entry.getElement(), Integer.parseInt(amountTb.getValue()), new ElementAssignCallback());
						} else {
							administration.assignElement((Module)pe.getElement(), entry.getElement(), Integer.parseInt(amountTb.getValue()), new ElementAssignCallback());
						}
					}
				}
			}
		});

		Button cancelBtn = new Button("abbrechen");
		cancelBtn.setStylePrimaryName("btn btn-warning createBtn");

		cancelBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				RootPanel.get("main").clear();
				RootPanel.get("main").add(new ElementForm(entry));
			}
		});

		panel.add(amountText);
		panel.add(amountTb);
		panel.add(cancelBtn);
		panel.add(assignBtn);
		
		this.add(panel);
			

	}
	class ElementAssignCallback implements AsyncCallback<Void> {

		/** Showcase in dem die Antwort des Callbacks eingefügt wird. */

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 * 
		 * @param c
		 *            Showcase an das die Rückmeldung ausgegeben wird.
		 */
		public ElementAssignCallback() {
		}

		/**
		 * Wenn der asynchrone Aufruf fehlschlug oder das Element nicht gelöscht
		 * werden konnte wird die onFailure Methode aufgerufen und der Fehler
		 * als ErrorMsg dem Showcase eingefügt, sowie im Client-Logger
		 * verzeichnet.
		 */
		@Override
		public void onFailure(Throwable caught) {
			currentShowcase.insert(
					new ErrorMsg("<b>Error:</b> " + caught.getMessage()), 1);
			ClientsideSettings.getLogger().severe(
					"Error: " + caught.getMessage());
		}

		/**
		 * Wenn der asynchrone Aufruf zum löschen des Elements erfolgreich war,
		 * wird eine SuccessMsg im Showcase eingefügt.
		 */
		@Override
		public void onSuccess(Void result) {
			RootPanel.get("main").clear();
			RootPanel.get("main").add(new ElementForm(entry));
			currentShowcase.insert(new SuccessMsg("Zuordnung erfolgreich gespeichert!"),
					1);
		}
	}
	

}
