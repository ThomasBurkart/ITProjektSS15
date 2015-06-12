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

public class ElementForm extends Showcase {

	private String headlineText;
	private String headlineTextStyle;
	private Element element;
	private boolean newElement;
	
	public static Showcase currentShowcase;

	public ElementForm(Element element) {
		this.element = element;
		this.newElement = false;
		if (this.element == null) {
			this.element = new Element();
			this.newElement = true;
		} else if(this.element.getId() <= 0){
			this.newElement = true;
		}
		this.getElement().setId("elementForm");
		// Style-Klasse für Titel in Main-Hälfte
		this.headlineTextStyle = "formTitle";
		currentShowcase = this;
	}

	@Override
	protected String getHeadlineText() {
		this.headlineText = "";
		if (this.newElement) {
			// Titel für neue Elemente/Module/Produkte
			if (this.element instanceof Product) {
				this.headlineText = "Neues Enderzeugnis anlegen";
			} else if (this.element instanceof Module) {
				this.headlineText = "Neue Baugruppe anlegen";
			} else {
				this.headlineText = "Neues Bauteil anlegen";
			}
		} else {
			// Titel für bereits vorhandene Elemente/Module/Produkte
			if (this.element instanceof Product) {
				this.headlineText = "Enderzeugnis '" + ((Product) this.element).getSalesName()
						+ "' editieren";
			} else if (this.element instanceof Module) {
				this.headlineText = "Baugruppe '" + ((Module) this.element).getName()
						+ "' editieren";
			} else {
				this.headlineText = "Bauteil '" + this.element.getName() + "' editieren";
			}
		}
		return this.headlineText;
	}

	@Override
	protected String getHeadlineTextStyle() {
		return this.headlineTextStyle;
	}

	@Override
	protected void run() {
		// FORMULAR ANFANG
		Grid grid;
		if (this.element instanceof Product) {
			grid = new Grid(6, 2);
		} else {
			grid = new Grid(5, 2);
		}
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

		// FORMULAR ENDE

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
	class ElementDeleteCallback implements AsyncCallback<Void> {
		private Showcase showcase = null;

		public ElementDeleteCallback(Showcase c) {
			this.showcase = c;
		}

		@Override
		public void onFailure(Throwable caught) {
			showcase.insert(new ErrorMsg("<b>Error:</b> " + caught.getMessage()), 1);
			ClientsideSettings.getLogger().severe("Error: " + caught.getMessage());
		}


		@Override
		public void onSuccess(Void result) {
			showcase.insert(new SuccessMsg("Löschvorgang erfolgreich!"), 1);
		}

	}
}
