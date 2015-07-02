package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.groupfive.itproject.client.AssignPanel.ElementAssignCallback;
import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;
import de.hdm.groupfive.itproject.shared.bo.Product;
import de.hdm.groupfive.itproject.shared.bo.User;

public class CalculateMaterial extends Showcase {

	/** Überschrift des Showcase */
	private String headlineText;

	/** StyleSheet Klasse für die Überschrift des Showcase */
	private String headlineTextStyle;

	/** Das Element, das im Formular geladen wird */
	private PartlistEntry entry;
	
	private Showcase currentShowcase;
	
	public CalculateMaterial(PartlistEntry entry) {
		this.entry = entry;
		this.headlineText = "Materialbedarf zu '"+entry.getElement().getName()+"' kalkulieren";
		this.headlineTextStyle = "formTitle";
		this.currentShowcase = this;
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

		final Button calcBtn = new Button("berechnen");
		calcBtn.setStylePrimaryName("btn btn-success createBtn");
		calcBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				AdministrationCommonAsync administration = ClientsideSettings
							.getAdministration();
				if (entry.getElement() instanceof Module) {
					Module m = (Module)entry.getElement();
					administration.calculateMaterial(m.getPartlist(), new CalcCallback());
				} else {
					Grid grid = new Grid(2, 3);
					if (RootPanel.get("calcGrid") != null) {
						RootPanel.get("calcGrid").getElement().removeFromParent();
					}
					
					grid.getElement().setId("calcGrid");
					grid.setStylePrimaryName("table table-striped");
					grid.setHTML(0, 0, "<b>Bauteil Id</b>");
					grid.setHTML(0, 1, "<b>Bauteil Name</b>");
					grid.setHTML(0, 2, "<b>Gesamt Anzahl</b>");
				
					grid.setHTML(1, 0, "" + entry.getElement().getId());
					grid.setHTML(1, 1, entry.getElement().getName());
					grid.setHTML(1, 2, "" + amountTb.getValue().trim());
					currentShowcase.add(grid);
				}
			}
		});

		

		panel.add(amountText);
		panel.add(amountTb);
		panel.add(calcBtn);
		
		this.add(panel);
	}
	class CalcCallback implements AsyncCallback<Partlist> {

		/** Showcase in dem die Antwort des Callbacks eingefügt wird. */

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 * 
		 * @param c
		 *            Showcase an das die Rückmeldung ausgegeben wird.
		 */
		public CalcCallback() {
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
		public void onSuccess(Partlist result) {
			Grid grid = new Grid(result.getAllEntries().size() + 1, 3);
			if (RootPanel.get("calcGrid") != null) {
				RootPanel.get("calcGrid").getElement().removeFromParent();
			}
			
			grid.getElement().setId("calcGrid");
			grid.setStylePrimaryName("table table-striped");
			grid.setHTML(0, 0, "<b>Bauteil Id</b>");
			grid.setHTML(0, 1, "<b>Bauteil Name</b>");
			grid.setHTML(0, 2, "<b>Gesamt Anzahl</b>");
			int i = 0;
			for (PartlistEntry pe : result.getAllEntries()) {
				i++;
				grid.setHTML(i, 0, "" + pe.getElement().getId());
				grid.setHTML(i, 1, pe.getElement().getName());
				grid.setHTML(i, 2, "" + pe.getAmount());
			}
			currentShowcase.add(grid);
				
		}
	}
}
