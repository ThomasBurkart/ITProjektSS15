package de.hdm.groupfive.itproject.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Node;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Widget;


/**
 * Dieses Panel verwendet das HTML UL-Tag. Alle Children werden in ein LI-Tag gepackt.
 * 
 */
public class UlListPanel extends ComplexPanel implements InsertPanel {

	/**
	 * Erzeugt ein leeres Flowpanel.
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
	 * Fügt ein neues Child Widget dem Panel hinzu.
	 * 
	 * @param w Hinzufügendes Widget
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
	 * Fügt ein Widget vor dem vegegebenen Index ein.
	 * 
	 * @param w Hinzufügendes Widget
	 * @param beforeIndex Index bevor dieser eingefügt wird.
	 * @throws IndexOutOfBoundsException wenn <code>beforeIndex</code> außerhalb des Intervalls ist
	 */
	public void insert(Widget w, int beforeIndex) {
		insert(wrapWidget(w), getElement(), beforeIndex, true);
	}

	/**
	 * Das LI-Element um {@link UlListPanel}s zu verwenden.
	 * @author Fesseler
	 */
	private static class LiPanel extends ComplexPanel implements InsertPanel {

		protected LiPanel() {
			setElement(Document.get().createLIElement());
		}

		/**
		 * Fügt ein neues Child Widget dem Panel hinzu.
		 * 
		 * @param w Hinzufügendes Widget
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
		 * Setzt ein Widget vor dem Index ein
		 * 
		 * @param w Hinzufügendes Widget
		 * @param beforeIndex Index bevor dieser eingefügt wird.
		 * @throws IndexOutOfBoundsException wenn <code>beforeIndex</code> außerhalb des Intervalls ist
		 */
		public void insert(Widget w, int beforeIndex) {
			insert(w, getElement(), beforeIndex, true);
		}
	}
}