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

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;
import de.hdm.groupfive.itproject.shared.bo.Product;

/**
 * AssignPanel bietet ein Showcase um Zuordnungen von Baugruppen, Bauteilen und 
 * Endprodukten zu erm�glichen
 * @author Thomas Burkart
 * @version 1.0
 * @since 07.07.2015
 */
public class AssignPanel extends Showcase {

	/**
	 * Überschrift des Showcase (graue Überschrift)
	 */
	private String headlineText;
	
	/**
	 * StyleSheet Klasse für die Überschrift des Showcase
	 */
	private String headlineTextStyle;
	
	/**
	 * Das Element, das im Formular geladen wird
	 */
	private PartlistEntry entry;
	
	/**
	 * Aktueller Showcase
	 */
	private Showcase currentShowcase;

	/**
	 * Konstruktor der Klasse AssignPanel
	 * erzeugt eine Zuordnungsfläche für die Anzeige der Stücklisten der Baugruppen
	 * 
	 * @param e
	 *            Stücklisten Eintrag
	 */
	public AssignPanel(PartlistEntry e) {
		this.entry = e;
		
		this.headlineText = "Bitte suchen oder wählen sie eine Baugruppe zum zuordnen.";

		this.headlineTextStyle = "formTitle";
		
		currentShowcase = this;
	}

	/**
	   * Jeder Showcase besitzt eine einleitende Überschrift, die durch diese
	   * Methode zu erstellen ist.
	   * 
	   * @see Showcase#getHeadlineText()
	   */
	protected String getHeadlineText() {
		return this.headlineText;
	}

	/**
	   * Jeder Showcase besitzt eine einleitende Überschrift und dazugehörenden StyleSheet, 
	   * der durch diese Methode zu erstellen ist.
	   */
	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}

	/**
	 * Jeder Showcase muss die <code>run()</code>-Methode implementieren. Sie ist
	 * eine "Einschubmethode", die von einer Methode der Basisklasse
	 * <code>ShowCase</code> aufgerufen wird, wenn der Showcase aktiviert wird.
	 *
	 * Aufbau des Assign-Panels (Zuordnungsfläche)
	 */
	protected void run() {
		final SearchPanel sp = new SearchPanel();
		this.add(sp.getAssignPanel(entry));

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

		final Button assignBtn = new Button("zuordnen");
		assignBtn.setStylePrimaryName("btn btn-success createBtn");
		assignBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				for(PartlistEntry pe : sp.getSearchResult().getSelectionModel().getSelectedSet()) {
					AdministrationCommonAsync administration = ClientsideSettings
							.getAdministration();
					if (entry.getElement() instanceof Product) {
						administration.assignElement(entry.getElement(), pe.getElement(), Integer.parseInt(amountTb.getValue().trim()), new ElementAssignCallback());
					} else {
						administration.assignElement(pe.getElement(), entry.getElement(), Integer.parseInt(amountTb.getValue().trim()), new ElementAssignCallback());
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

		ClientsideSettings.getLogger().info("AssignPanel aufgebaut");

	}
	
	/**
	 * Diese Klasse wird zur asynchronen Ausführung der Zuordnung gebraucht.
	 * 
	 * @author Timo Fesseler
	 *
	 */
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
			ClientsideSettings.getLogger().info("Zuordnung erfolgreich gespeichert!");
			RootPanel.get("main").clear();
			RootPanel.get("main").add(new ElementForm(entry));
			RootPanel.get("main").insert(new SuccessMsg("Zuordnung erfolgreich gespeichert!"),
					0);
		}
	}
	

}
