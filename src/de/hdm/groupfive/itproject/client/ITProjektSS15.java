package de.hdm.groupfive.itproject.client;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;

import de.hdm.groupfive.itproject.shared.bo.BusinessObject;
import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Product;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ITProjektSS15 implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// lädt den Login Dialog
		LoginLogout login = new LoginLogout();
		login.loadDialog();

		final HTML content = new HTML("Herzlich Willkommen :)");
		content.setStylePrimaryName("formTitle");
		RootPanel.get("main").clear();
		RootPanel.get("clientTitle").clear();

		RootPanel.get("main").add(content);
		RootPanel.get("main").add(createHistory());

		Button clientTitle = new Button("&nbsp;Editor & Viewer",
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						RootPanel.get("main").clear();
						RootPanel.get("main").add(content);
						RootPanel.get("main").add(createHistory());
					}
				});
		clientTitle.setStylePrimaryName("btn btn-link navbar-brand");
		RootPanel.get("clientTitle").add(clientTitle);
		// Such-Bereich einfügen
		RootPanel.get("navigator").add(createSearchPanel());

		// Anlegen-Button einfügen
		RootPanel.get("navbar").add(createCreateButton());

	}

	private Grid createHistory() {
		final Grid historyGrid = new Grid(16, 4);
		historyGrid.setStylePrimaryName("table table-striped");
		historyGrid.setWidget(0, 0, new HTML("<b>Nutzer</b>"));
		historyGrid.setWidget(0, 1, new HTML("<b>Element</b>"));
		historyGrid.setWidget(0, 2, new HTML("<b>Aktion</b>"));
		historyGrid.setWidget(0, 3, new HTML("<b>Änderungsdatum</b>"));
		for (int i = 1; i <= 15; i++) {
			historyGrid
					.setWidget(i, 0, new HTML("Olaf19" + Random.nextInt(99)));
			Button elementLink = new Button("Element " + Random.nextInt(),
					new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							Module m = new Module();
							m.setId(123);
							m.setName(event.getRelativeElement().getInnerText());
							m.setDescription("blablabla");
							m.setMaterialDescription("Metall");
							createForm(m);
						}
					});
			elementLink.setStylePrimaryName("elementLink");
			historyGrid.setWidget(i, 1, elementLink);
			historyGrid.setWidget(i, 2, new HTML("geändert"));
			historyGrid.setWidget(i, 3,
					new HTML("29.05.2015 15:" + Random.nextInt(59)));
		}
		return historyGrid;
	}

	public void createForm(BusinessObject bo) {
		Grid grid = new Grid(5, 2);
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

		// Noch keine Id vorhanden, deswegen handelt es sich um ein neues
		// Element
		if (bo.getId() <= 0) {
			String type;
			if (bo instanceof Product) {
				type = "Neues Enderzeugnis anlegen";
			} else if (bo instanceof Module) {
				type = "Neue Baugruppe anlegen";
			} else {
				type = "Neues Bauteil anlegen";
			}

			RootPanel.get("main").clear();

			final HTML resultInfo = new HTML(type);
			resultInfo.setStylePrimaryName("formTitle");
			RootPanel.get("main").add(resultInfo);

		} else {
			String type;
			if (bo instanceof Product) {
				Product p = (Product) bo;
				type = "Enderzeugnis '" + p.getName() + "' editieren";
				idTb.setValue(p.getId() > 0 ? "" + p.getId()
						: "keine Id vorhanden");
				nameTb.setValue(p.getName() != null ? p.getName() : "");
				descTb.setValue(p.getDescription() != null ? p.getDescription()
						: "");
				matTb.setValue(p.getMaterialDescription() != null ? p
						.getMaterialDescription() : "");
			} else if (bo instanceof Module) {
				Module m = (Module) bo;
				type = "Baugruppe '" + m.getName() + "' editieren";
				idTb.setValue(m.getId() > 0 ? "" + m.getId()
						: "keine Id vorhanden");
				nameTb.setValue(m.getName() != null ? m.getName() : "");
				descTb.setValue(m.getDescription() != null ? m.getDescription()
						: "");
				matTb.setValue(m.getMaterialDescription() != null ? m
						.getMaterialDescription() : "");
			} else {
				if (bo instanceof Element) {
					Element e = (Element) bo;
					type = "Bauteil '" + e.getName() + "' editieren";
					idTb.setValue(e.getId() > 0 ? "" + e.getId()
							: "keine Id vorhanden");
					nameTb.setValue(e.getName() != null ? e.getName() : "");
					descTb.setValue(e.getDescription() != null ? e
							.getDescription() : "");
					matTb.setValue(e.getMaterialDescription() != null ? e
							.getMaterialDescription() : "");
				} else {
					type = "Da ist etwas schief gegangen";
				}
			}

			RootPanel.get("main").clear();

			final HTML resultInfo = new HTML(type);
			resultInfo.setStylePrimaryName("formTitle");
			RootPanel.get("main").add(resultInfo);
		}

		RootPanel.get("main").add(grid);

		FlowPanel panel = new FlowPanel();

		Button cancelBtn = new Button("abbrechen", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Inhalt zu Element neu laden
			}
		});
		Button deleteBtn = new Button("löschen", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Element löschen
			}
		});
		Button saveBtn = new Button("speichern", new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Element abspeichern/neu anlegen

			}
		});
		Button saveAndAssignBtn = new Button("zuordnen & speichern",
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						// TODO Element abspeichern/neu anlegen und Element von
						// Baum zuordnen

					}
				});
		cancelBtn.setStylePrimaryName("btn btn-warning createBtn");
		deleteBtn.setStylePrimaryName("btn btn-danger createBtn");
		saveBtn.setStylePrimaryName("btn btn-success createBtn");
		saveAndAssignBtn.setStylePrimaryName("btn btn-success createBtn");
		panel.setStylePrimaryName("actionBox");
		panel.add(cancelBtn);
		panel.add(deleteBtn);
		panel.add(saveBtn);
		panel.add(saveAndAssignBtn);

		RootPanel.get("main").add(panel);
	}

	private Widget createCreateButton() {
		Button createBtn = new Button("anlegen", new ClickHandler() {
			public void onClick(ClickEvent event) {
				FlowPanel panel = new FlowPanel();

				Button createElementBtn = new Button("Bauteil anlegen",
						new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								createForm(new Element());
							}
						});
				Button createModuleBtn = new Button("Baugruppe anlegen",
						new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								createForm(new Module());

							}
						});
				Button createProductBtn = new Button("Enderzeugnis anlegen",
						new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								createForm(new Product());

							}
						});
				createElementBtn
						.setStylePrimaryName("btn btn-default createBtn");
				createModuleBtn
						.setStylePrimaryName("btn btn-default createBtn");
				createProductBtn
						.setStylePrimaryName("btn btn-default createBtn");
				panel.add(createElementBtn);
				panel.add(createModuleBtn);
				panel.add(createProductBtn);
				RootPanel.get("main").clear();

				final HTML resultInfo = new HTML(
						"Bitte wählen Sie aus, was Sie anlegen möchten!");
				resultInfo.setStylePrimaryName("formTitle");
				RootPanel.get("main").add(resultInfo);

				RootPanel.get("main").add(panel);
			}
		});
		createBtn.setStylePrimaryName("btn btn-success");
		return createBtn;
	}

	private Widget createSearchPanel() {
		// SUCHE ANFANG
		VerticalPanel searchPanel = new VerticalPanel();
		searchPanel.setStylePrimaryName("searchPanel");
		FlowPanel searchInputPanel = new FlowPanel();
		final TextBox searchBox = new TextBox();
		searchBox.setStylePrimaryName("searchBox col-md-9 col-sm-9 col-xs-9");
		searchBox.setFocus(true);
		searchBox
				.setTitle("Suche nach Bauteil, Baugruppe oder Enderzeugnis ...");
		searchBox.getElement().setPropertyString("placeholder",
				"Suche nach Bauteil, Baugruppe oder Enderzeugnis ...");

		final HTML resultInfo = new HTML("Bitte starten Sie eine Suche!");
		resultInfo.setStylePrimaryName("resultInfo");

		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
		simplePopup
				.setStylePrimaryName("searchResultBox col-md-9 col-sm-9 col-xs-9");
		final VerticalPanel resultsPanel = new VerticalPanel();
		simplePopup.setWidget(resultsPanel);
		simplePopup.setVisible(false);
		simplePopup.show();

		searchBox.addKeyUpHandler(new KeyUpHandler() {
			public void onKeyUp(KeyUpEvent event) {
				if (searchBox.getValue().trim().length() > 2) {

					resultInfo.setHTML("Suchergebnisse für '"
							+ searchBox.getValue().trim() + "'");

					simplePopup.setVisible(true);
					resultsPanel.clear();
					for (int i = 0; i < 5; i++) {
						Hyperlink link = new Hyperlink();
						link.setText(searchBox.getValue());

						resultsPanel.add(link);
					}
				} else {
					simplePopup.setVisible(false);
					resultsPanel.clear();
				}
				if (searchBox.getValue().trim().length() <= 2) {
					resultInfo.setHTML("Bitte starten Sie eine Suche!");
				}
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					// TODO: Suche ausführen weil ENTER gedrückt wurde
					simplePopup.setVisible(false);
					resultsPanel.clear();
				}
			}
		});

		Button searchBtn = new Button("Suchen", new ClickHandler() {
			public void onClick(ClickEvent event) {

				simplePopup.setVisible(false);
				resultsPanel.clear();

			}
		});
		searchBtn
				.setStylePrimaryName("btn btn-default col-md-2 col-sm-2 col-xs-2");

		searchInputPanel.add(searchBox);
		searchInputPanel.add(searchBtn);
		searchPanel.add(searchInputPanel);
		searchPanel.add(simplePopup);

		// SUCHE ENDE
		searchPanel.add(resultInfo);
		// BAUMSTRUKTUR (ERGEBNISSE) ANFANG
		final CellTree dynamicTree = createDynamicTree();

		ScrollPanel dynamicTreeWrapper = new ScrollPanel(dynamicTree);
		dynamicTreeWrapper.setStylePrimaryName("tree col-md-11 col-sm-11 col-xs-11");
		searchPanel.add(dynamicTreeWrapper);
		// BAUMSTRUKTUR ENDE

		return searchPanel;
	}

	private CellTree createDynamicTree() {

		ArrayList<Element> result = new ArrayList<Element>();
		Module m1 = new Module();
		m1.setName("Module 1");
		m1.setDescription("Baugruppe 1 - Irgendeine Beschreibung");
		m1.setId(12);
		m1.setCreationDate(new Date(2015, 6, 7));
		m1.setMaterialDescription("Unterschiedlich");
		result.add(m1);

		Module e1 = new Module();
		e1.setName("Module 2");
		e1.setDescription("Baugruppe 2 - Noch Irgendeine Beschreibung");
		e1.setId(232);
		e1.setCreationDate(new Date(2015, 6, 3));
		e1.setMaterialDescription("Metall");
		m1.getPartlist().add(e1, 1);

		Element e2 = new Element();
		e2.setName("Element 1");
		e2.setDescription("Bauteil 1 - Beschreibung meines ersten Bauteils :D");
		e2.setId(132);
		e2.setCreationDate(new Date(2015, 6, 3));
		e2.setMaterialDescription("Eisen");
		e1.getPartlist().add(e2, 1);

		Element e3 = new Element();
		e3.setName("Element 2");
		e3.setDescription("Bauteil 2 - Beschreibung meines zweiten Bauteils ;D");
		e3.setId(132);
		e3.setCreationDate(new Date(2015, 6, 3));
		e3.setMaterialDescription("Holz");
		e1.getPartlist().add(e3, 1);

		Element e4 = new Element();
		e4.setName("Element 4");
		m1.getPartlist().add(e4, 1);
		// Create a model for the tree.

		Module module = new Module();
		module.setName("Module 3");
		result.add(module);
		
		Element e5 = new Element();
		e5.setName("Element 5");
		e5.setDescription("Bauteil 5 - Beschreibung meines 5. Bauteils ;D");
		e5.setId(132);
		e5.setCreationDate(new Date(2015, 6, 3));
		e5.setMaterialDescription("Holz");
		result.add(e5);
		module.getPartlist().add(e1, 1);
		module.getPartlist().add(e2, 1);
		module.getPartlist().add(e3, 1);
		module.getPartlist().add(e4, 1);

		// Get CellTree style using its BasicResources
		// CellTree.Resources res = GWT.create(CellTree.BasicResources.class);
		/*
		 * Create the tree using the model. We use <code>null</code> as the
		 * default value of the root node. The default value will be passed to
		 * CustomTreeModel#getNodeInfo();
		 */

		final MultiSelectionModel<Element> selectionModel = new MultiSelectionModel<Element>();
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						
						List<Element> selected = new ArrayList<Element>(
								selectionModel.getSelectedSet());
//
//						RootPanel.get("main").clear();
//						RootPanel.get("main").add(new HTML(selected.get(0).getName()));
						createForm(selected.get(0));
					}
				});

		SearchTreeModel model = new SearchTreeModel(result, selectionModel);

		CellTree cellTree = new CellTree(model, null);
		cellTree.setAnimationEnabled(true);

		// CellTree tree = new CellTree(model, null);

		// tree.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Open the first playlist by default.
		// TreeNode rootNode = tree.getRootTreeNode();
		// TreeNode firstPlaylist = rootNode.setChildOpen(0, true);
		// firstPlaylist.setChildOpen(0, true);

		return cellTree;
		// Return the tree
		// return dynamicTree;
	}

}
