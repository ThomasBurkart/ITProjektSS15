package de.hdm.groupfive.itproject.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * A panel that uses the HTML UL element. All children will be wrapped into LI elements.
 * 
 * Using UL lists is a modern pattern to layout web pages, as it is easy to style them
 * with CSS. Moreover, they have several advantages over tables (changing the layout
 * requires changing the code, accessibility, etc).
 * 
 */
public class UlListPanel extends ComplexPanel implements InsertPanel {

	/**
	 * Creates an empty flow panel.
	 */
	public UlListPanel() {
		setElement(Document.get().createULElement());
	}

	private LiPanel wrapWidget(Widget w) {
		LiPanel li = new LiPanel();
		li.add(w);
		return li;
	}

	/**
	 * Adds a new child widget to the panel.
	 * 
	 * @param w the widget to be added
	 */
	@Override
	public void add(Widget w) {
		add(wrapWidget(w), getElement());
	}

	@Override
	public void clear() {
		try {
			// doLogicalClear();
		} finally {
			// Remove all existing child nodes.
			Node child = getElement().getFirstChild();
			while (child != null) {
				getElement().removeChild(child);
				child = getElement().getFirstChild();
			}
		}
	}

	/**
	 * Inserts a widget before the specified index.
	 * 
	 * @param w the widget to be inserted
	 * @param beforeIndex the index before which it will be inserted
	 * @throws IndexOutOfBoundsException if <code>beforeIndex</code> is out of range
	 */
	public void insert(Widget w, int beforeIndex) {
		insert(wrapWidget(w), getElement(), beforeIndex, true);
	}

	/**
	 * The LI element for use in {@link UlListPanel}s.
	 * @author Markus
	 */
	private static class LiPanel extends ComplexPanel implements InsertPanel {

		protected LiPanel() {
			setElement(Document.get().createLIElement());
		}

		/**
		 * Adds a new child widget to the panel.
		 * 
		 * @param w the widget to be added
		 */
		@Override
		public void add(Widget w) {
			add(w, getElement());
		}

		@Override
		public void clear() {
			try {
				// doLogicalClear();
			} finally {
				// Remove all existing child nodes.
				Node child = getElement().getFirstChild();
				while (child != null) {
					getElement().removeChild(child);
					child = getElement().getFirstChild();
				}
			}
		}

		/**
		 * Inserts a widget before the specified index.
		 * 
		 * @param w the widget to be inserted
		 * @param beforeIndex the index before which it will be inserted
		 * @throws IndexOutOfBoundsException if <code>beforeIndex</code> is out of range
		 */
		public void insert(Widget w, int beforeIndex) {
			insert(w, getElement(), beforeIndex, true);
		}
	}
}