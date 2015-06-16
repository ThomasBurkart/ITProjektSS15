package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

import de.hdm.groupfive.itproject.shared.AdministrationCommonAsync;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Product;

/**
 * ElementForm bietet ein Showcase, in dem ein Formular mit den Daten des
 * bei der Instanziierung übergebenen Bauteil/Baugruppe/Enderzeugnis gefüllt
 * wird. 
 * @author Thomas Burkart
 * @version 1.0
 * @since 12.06.2015
 */
public class ElementForm extends Showcase {

	/** Überschrift des Showcase */
	private String headlineText;
	
	/** StyleSheet Klasse für die Überschrift des Showcase */
	private String headlineTextStyle;
	
	/** Das Element, das im Formular geladen wird */
	private Element element;
	
	/** Wird verwendet um zu prüfen ob es sich um ein Element
	 * handelt, dass neu angelegt wird.
	 */
	private boolean newElement;
	
	/** Zuletzt verwendetes/gesetztes ElementForm Showcase, dient
	 * dazu in den onClick Funktionen entsprechende Rückmeldungen
	 * in das Showcase setzen zu können.
	 */
	public static Showcase currentShowcase;

	/**
	 * Kontruktor der Klasse ElementForm, erzeugt entsprechend dem übergebenen Element
	 * ein neues Formular und füllt die Formularfelder mit den Werten des Elements.
	 * Dabei wird zwischen Bauteilen, Baugruppen und Enderzeugnissen unterschieden.
	 * @param element Bauteil/Baugruppe/Enderzeugnis mit dessen Daten das Formular gefüllt wird.
	 */
	public ElementForm(Element element) {
		// Übergebenes Element
		this.element = element;
		
		// Das es sich um ein neues Element handelt wird vorerst auf "false" gesetzt, kann sich
		// aber noch ändern
		this.newElement = false;
		
		// Überprüfung dass das übergebene Element existiert, um keine Null-Pointer Exceptions
		// zu erzeugen
		if (this.element == null) {
			// Sollte das Element NULL sein, wird ein neues erzeugt.	
			this.element = new Element();
			
			// Da es sich nun um ein neues Element handelt, muss das Attribut newElement auf true
			// gesetzt werden
			this.newElement = true;
			
		// Überprüfung ob die Id des Elements kleiner oder gleich 0 ist, dies würde bedeuten,
		// dass das Element neu ist.
		} else if(this.element.getId() <= 0){
			
			// Da es sich nun um ein neues Element handelt, muss das Attribut newElement auf true
			// gesetzt werden
			this.newElement = true;
		}
		
		// Um später wieder darauf zugreifen zu können, wird die HTML-Id des Showcases auf elementForm
		// gesetzt
		this.getElement().setId("elementForm");
		
		// Style-Klasse für Titel in Main-Hälfte
		this.headlineTextStyle = "formTitle";
		
		// Um zu späterem Zeitpunkt, z.B. in den onClick Funktionen auf das Showcase zugreifen zu können,
		// muss dieses in einer statischen Variable gesetzt werden.
		currentShowcase = this;
	}

	/**
	 * Überschreibt die abstrakte Methode der Showcase Klasse. Die Methode dient dem setzen der Überschrift
	 * innerhalb des Showcase.
	 */
	@Override
	protected String getHeadlineText() {
		// Zuerst wird der Überschrift ein Leertext zugewiesen.
		this.headlineText = "";
		
		// Dann wird geprüft ob es sich um ein neues Element handelt
		// Das Attribut newElement wurde entsprechend im Konstruktor
		// gesetzt.
		if (this.newElement) {
			
			// Je nachdem, von welcher Klasse unser Element element erzeugt wurde
			// wird eine andere Überschrift gewählt.
			if (this.element instanceof Product) {
				
				// Überschrift für neue Enderzeugnisse
				this.headlineText = "Neues Enderzeugnis anlegen";
			} else if (this.element instanceof Module) {
				
				// Überschrift für neue Baugruppen
				this.headlineText = "Neue Baugruppe anlegen";
			} else {
				
				// Überschrift für neue Bauteile
				this.headlineText = "Neues Bauteil anlegen";
			}
		} else {
			// Überschriften für bereits vorhandene Bauteile/Baugruppen/Enderzeugnisse
			if (this.element instanceof Product) {
				
				// Überschrift für vorhandenes Enderzeugnis mit Verkaufsnamen.
				this.headlineText = "Enderzeugnis '" + ((Product) this.element).getSalesName()
						+ "' editieren";
			} else if (this.element instanceof Module) {
				
				// Überschrift für vorhandene Baugruppe mit Name der Baugruppe
				this.headlineText = "Baugruppe '" + ((Module) this.element).getName()
						+ "' editieren";
			} else {
				
				// Überschrift für vorhandenes Bauteil mit Name des Bauteils
				this.headlineText = "Bauteil '" + this.element.getName() + "' editieren";
			}
		}
		
		// Überschrift wird zurück gegeben.
		return this.headlineText;
	}

	/**
	 * Überschreibt die abstrakte Methode der Showcase Klasse und setzt damit die
	 * StyleSheet Klasse für die Überschrift.
	 */
	@Override
	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}
	
	/**
	 * Diese Methode wird ausgeführt nachdem die Überschrift im Showcase gesetzt wurde
	 * und der Inhalt geladen werden kann.
	 */
	@Override
	protected void run() {
		// Das Formular wird in Tabellenform aufgebaut, dazu wird ein Grid verwendet
		// Im der ersten Spalte des Grid werden die Bezeichnungen der Textboxen
		// gesetzt. In Spalte werden die Textboxen bzw. Textarea gesetzt.
		Grid grid;
		
		// Handelt sich bei dem Element um ein Enderzeugnis wird eine Zeile mehr
		// benötigt um den Verkaufsnamen ausgeben zu können.
		if (this.element instanceof Product) {
			
			// Tabelle für Enderzeugnisse (6 Zeilen und 2 Spalten)
			grid = new Grid(6, 2);
		} else {
			
			// Tabelle für Bauteile und Baugruppen (5 Zeilen und 2 Spalten)
			grid = new Grid(5, 2);
		}
		// Tabelle soll eine Breite von 100% erhalten.
		grid.setWidth("100%");
		
		
		HTML idText = new HTML("Teilnummer");
		idText.setStylePrimaryName("col-md-2 col-sm-2 col-xs-2");
		grid.setWidget(0, 0, idText);

		HTML nameText = new HTML("Bezeichnung");
		nameText.setStylePrimaryName("col-md-2 col-sm-2 col-xs-2");
		grid.setWidget(1, 0, nameText);

		HTML descText = new HTML("Beschreibung");
		descText.setStylePrimaryName("col-md-2 col-sm-2 col-xs-2");
		grid.setWidget(2, 0, descText);

		HTML matText = new HTML("Materialbezeichnung");
		matText.setStylePrimaryName("col-md-2 col-sm-2 col-xs-2");
		grid.setWidget(3, 0, matText);

		TextBox idTb = new TextBox();
		idTb.setName("textbox-id");
		idTb.setValue("wird automatisch gefüllt");
		idTb.setReadOnly(true);
		idTb.setStylePrimaryName("col-md-10 col-sm-10 col-xs-10 textBox");
		grid.setWidget(0, 1, idTb);

		TextBox nameTb = new TextBox();
		nameTb.setName("textbox-name");
		nameTb.setStylePrimaryName("col-md-10 col-sm-10 col-xs-10 textBox");
		grid.setWidget(1, 1, nameTb);

		TextArea descTb = new TextArea();
		descTb.setName("textarea-desc");
		descTb.setHeight("80px");
		descTb.setStylePrimaryName("col-md-10 col-sm-10 col-xs-10 textBox");
		grid.setWidget(2, 1, descTb);

		TextBox matTb = new TextBox();
		matTb.setName("textbox-mat");
		matTb.setStylePrimaryName("col-md-10 col-sm-10 col-xs-10 textBox");
		grid.setWidget(3, 1, matTb);

		// ÜBERSCHRIFT ANFANG
		// Noch keine Id vorhanden, deswegen handelt es sich um ein neues
		// Element. Entsprechend wird der Titel gesetzt.
		if (this.element.getId() > 0) {
			String type;
			if (this.element instanceof Product) {
				Product p = (Product) this.element;
				idTb.setValue(p.getId() > 0 ? "" + p.getId()
						: "keine Id vorhanden");
				nameTb.setValue(p.getName() != null ? p.getName() : "");
				descTb.setValue(p.getDescription() != null ? p.getDescription()
						: "");
				matTb.setValue(p.getMaterialDescription() != null ? p
						.getMaterialDescription() : "");
			} else if (this.element instanceof Module) {
				Module m = (Module) this.element;
				idTb.setValue(m.getId() > 0 ? "" + m.getId()
						: "keine Id vorhanden");
				nameTb.setValue(m.getName() != null ? m.getName() : "");
				descTb.setValue(m.getDescription() != null ? m.getDescription()
						: "");
				matTb.setValue(m.getMaterialDescription() != null ? m
						.getMaterialDescription() : "");
			} else {
				Element e = this.element;
				idTb.setValue(e.getId() > 0 ? "" + e.getId()
						: "keine Id vorhanden");
				nameTb.setValue(e.getName() != null ? e.getName() : "");
				descTb.setValue(e.getDescription() != null ? e.getDescription()
						: "");
				matTb.setValue(e.getMaterialDescription() != null ? e
						.getMaterialDescription() : "");
			}

		}

		// ÜBERSCHRIFT ENDE

		// BREADCRUMB ANFANG
		
		
		
		String test = "<ol class=\"breadcrumb\">";
		test += "<li><a href=\"#\">Endprodukt abc</a></li>";
		test += "<li><a href=\"#\">Baugruppe aha</a></li>";
		test += "<li class=\"active\">"+SearchResult.getSelectionModel().getSelectedSet().size()+" Element mauaha</li>";
		test += "</ol>";
		HTML breadcrumb = new HTML(test);
		this.add(breadcrumb);
		// BREADCRUMB ENDE

		this.add(grid);

		// ACTION BUTTONS für mögliche Aktionen ANFANG
		FlowPanel panel = new FlowPanel();
		panel.setStylePrimaryName("actionBox");
		
		
		Button cancelBtn = new Button("abbrechen");
		cancelBtn.setStylePrimaryName("btn btn-warning createBtn");
		cancelBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get("main").clear();
				RootPanel.get("main").add(new ElementForm(element));
				SearchResult.enableLoadElementForm();
			}
		});
		panel.add(cancelBtn);
		
		if (!this.newElement) {
			Button deleteBtn = new Button("löschen");
			deleteBtn.setStylePrimaryName("btn btn-danger createBtn");
			deleteBtn.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					AdministrationCommonAsync administration = ClientsideSettings.getAdministration();
					administration.deleteElement(element, new ElementDeleteCallback(currentShowcase));
				}
			});
			panel.add(deleteBtn);
		}
		Button saveBtn = new Button("speichern");
		saveBtn.setStylePrimaryName("btn btn-success createBtn");
		saveBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				
				AdministrationCommonAsync administration = ClientsideSettings.getAdministration();
				administration.deleteElement(element, new ElementDeleteCallback(currentShowcase));
				
			}
		});
		panel.add(saveBtn);
		
		Button saveAndAssignBtn = new Button("zuordnen & speichern");
		saveAndAssignBtn.setStylePrimaryName("btn btn-success createBtn");
		saveAndAssignBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO Element abspeichern/neu anlegen und Element von
				// Baum zuordnen
				SearchResult.disableLoadElementForm();
				currentShowcase.insert(new InfoMsg("Sie können nun ein [Klick] oder mehrere [Strg+Klick] Baugruppen im Suchbaum markieren, um das Element diesen zuzuordnen!"), 1);
				
			}
		});
		panel.add(saveAndAssignBtn);

		this.add(panel);
		// ACTION BUTTONS ENDE

	}
	
	/**
	 * Callback Klasse die asynchron aufgerufen wird, wenn ein Element gelöscht wird.
	 * @author Thomas Burkart
	 */
	class ElementDeleteCallback implements AsyncCallback<Void> {
		
		/** Showcase in dem die Antwort des Callbacks eingefügt wird. */
		private Showcase showcase = null;
		
		/**
		 * Konstruktor der Callback Klasse, diese legt bei der Instanziierung
		 * das übergebene Showcase fest.
		 * @param c Showcase an das die Rückmeldung ausgegeben wird.
		 */
		public ElementDeleteCallback(Showcase c) {
			this.showcase = c;
		}

		/**
		 * Wenn der asynchrone Aufruf fehlschlug oder das Element nicht gelöscht
		 * werden konnte wird die onFailure Methode aufgerufen und der Fehler
		 * als ErrorMsg dem Showcase eingefügt, sowie im Client-Logger verzeichnet.
		 */
		@Override
		public void onFailure(Throwable caught) {
			showcase.insert(new ErrorMsg("<b>Error:</b> " + caught.getMessage()), 1);
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}

		/**
		 * Wenn der asynchrone Aufruf zum löschen des Elements erfolgreich war,
		 * wird eine SuccessMsg im Showcase eingefügt.
		 */
		@Override
		public void onSuccess(Void result) {
			showcase.insert(new SuccessMsg("Löschvorgang erfolgreich!"), 1);
		}
	}
}
