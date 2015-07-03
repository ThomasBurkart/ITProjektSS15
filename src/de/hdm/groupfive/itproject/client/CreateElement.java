package de.hdm.groupfive.itproject.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.groupfive.itproject.shared.bo.Element;
import de.hdm.groupfive.itproject.shared.bo.Module;
import de.hdm.groupfive.itproject.shared.bo.Product;

public class CreateElement extends Showcase {
	private String headlineText;
	private String headlineTextStyle;
	
	public CreateElement() {
		this.headlineText = "Bitte wählen Sie aus, was Sie anlegen möchten!";
		this.headlineTextStyle = "formTitle";
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
		Button createElementBtn = new Button("<img src=\"img/element.png\" style=\"width: 100%\" />");
		createElementBtn.setStylePrimaryName("btn btn-default createBtn btn32");
		createElementBtn.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RootPanel.get("main").clear();
						RootPanel.get("main").add(new ElementForm(new Element(),1,null));
					}
				});
		Button createModuleBtn = new Button("<img src=\"img/module.png\" style=\"width: 100%\" />");
		createModuleBtn.setStylePrimaryName("btn btn-default createBtn btn32");
		createModuleBtn.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RootPanel.get("main").clear();
						RootPanel.get("main").add(new ElementForm(new Module(),1,null));

					}
				});
		Button createProductBtn = new Button("<img src=\"img/product.png\" style=\"width: 100%\" />");
		createProductBtn.setStylePrimaryName("btn btn-default createBtn btn32");
		createProductBtn.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						RootPanel.get("main").clear();
						RootPanel.get("main").add(new ElementForm(new Product(),1,null));
					}
				});
		
		
		this.add(createElementBtn);
		this.add(createModuleBtn);
		this.add(createProductBtn);
	}

}
