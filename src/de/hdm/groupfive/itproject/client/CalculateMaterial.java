package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Partlist;
import de.hdm.groupfive.itproject.shared.bo.PartlistEntry;

/**
 * CalculateMaterial bietet ein Showcase, um die Anzahl der Bauteile 
 * in einer Baugruppe bestimmen zu können. 
 * 
 * @author Thomas Burkart
 * @version 1.0
 * @since 07.07.2015
 */
public class CalculateMaterial extends Showcase {

	/**
	 * Übersschrift des Showcase (graue Überschrift)
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
	 * Konstruktor der Klasse CalculateMaterial
	 * erzeugt eine Zuordnungsfläche für die Anzeige der Stücklisten der Baugruppen
	 * @param entry
	 */
	public CalculateMaterial(PartlistEntry entry) {
		this.entry = entry;
		this.headlineText = "Materialbedarf zu '"
				+ entry.getElement().getName() + "' kalkulieren";
		this.headlineTextStyle = "formTitle";
		this.currentShowcase = this;
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
	   * Aufbau des Panels zur Berechnung der Anzahl von Bauteilen in einer Bauurgruppe. 
	   */
	protected void run() {
		Grid grid = new Grid(1, 4);

		HTML amountText = new HTML("Anzahl");
		amountText.setStylePrimaryName("col-md-2 col-sm-2 col-xs-2 amountText");
		grid.setWidget(0, 0, amountText);

		final TextBox amountTb = new TextBox();
		amountTb.setValue(this.entry.getAmount() > 1 ? ""
				+ this.entry.getAmount() : "1");
		amountTb.setStylePrimaryName("col-md-2 col-sm-2 col-xs-2 textBox");

		amountTb.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					AdministrationCommonAsync administration = ClientsideSettings
							.getAdministration();
					if (entry.getElement() instanceof Module) {
						Module m = (Module) entry.getElement();
						administration.calculateMaterial(m.getPartlist(),
								Integer.parseInt(amountTb.getValue().trim()),
								new CalcCallback());
					} else {
						Grid grid = new Grid(2, 3);
						if (RootPanel.get("calcGrid") != null) {
							RootPanel.get("calcGrid").getElement()
									.removeFromParent();
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
			}
		});

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
					Module m = (Module) entry.getElement();
					administration.calculateMaterial(m.getPartlist(),
							Integer.parseInt(amountTb.getValue().trim()),
							new CalcCallback());
				} else {
					Grid grid = new Grid(2, 3);
					if (RootPanel.get("calcGrid") != null) {
						RootPanel.get("calcGrid").getElement()
								.removeFromParent();
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
		
		final Button createPartlistBtn = new Button("Stückliste ausgeben");
		createPartlistBtn.setStylePrimaryName("btn btn-success createBtn");
		createPartlistBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				
					if (RootPanel.get("calcGrid") != null) {
						RootPanel.get("calcGrid").getElement()
								.removeFromParent();
					}
					HTML result = new HTML();
					result.getElement().setId("calcGrid");
					if (entry.getElement() instanceof Module) {
						Module m = (Module) entry.getElement();
						
						String treeHtml = generateHtmlTree(entry, 0);
						
						result.setHTML(treeHtml);
								
					} else {
						result.setHTML(entry.getElement().getName());
					}
					

					currentShowcase.add(result);
				
			}
		});

		panel.add(amountText);
		panel.add(amountTb);
		panel.add(calcBtn);
		panel.add(createPartlistBtn);

		this.add(panel);
	}
	
	private String generateHtmlTree(PartlistEntry pe, int level) {
		String result = "";
		for (int i = 0; i < level; i++) {
			result += "   ";
		}
		
		if (pe.getElement() instanceof Module) {
			result += pe.getElement().getName();
			result += level > 0 ? " [" + pe.getAmount() + "]<br />" : "<br />";
			for(PartlistEntry entry : ((Module)pe.getElement()).getPartlist().getAllEntries()) {
				if (entry.getElement() instanceof Module) {
					result += generateHtmlTree(entry, level+1);
				} else {
					result += pe.getElement().getName() + " [" + pe.getAmount() + "]<br />";
				}
			}
		} else {
			result += pe.getElement().getName() + " [" + pe.getAmount() + "]<br />";
		}
		return result;
	}

	/**
	 * Showcase in dem die Antwort des Callbacks eingefügt wird.
	 * @author Timo Fesseler
	 *
	 */
	class CalcCallback implements AsyncCallback<Partlist> {

		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 * 
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
