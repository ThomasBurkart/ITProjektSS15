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
 * Ein Panel, dass das HTML UL-Element verwendet. Alle Kinder werden in LI-Elemente eingewickelt.
  *
  * Bei der Verwendung von UL-Listen ist ein modernes Muster, um Web-Seiten-Layout, wie es ist leicht, sie zu formatieren
  * Mit CSS. Darüber hinaus haben mehrere Vorteile gegenüber Tabellen (Ändern des Layouts müssen sie
  * Erfordert den Code ändern, Zugänglichkeit, usw.). 
 */
public class UlListPanel extends ComplexPanel implements InsertPanel {

	/**
	 * Erzeugt ein leeres "flow panel"
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
	 * Fügt ein neues Kind Widget zu dem Panel.
	 *
	 * @param W Widget, welches hinzugefügt werden soll
	 */
	@Override
	public void add(Widget w) {
		add(wrapWidget(w), getElement());
	}

	@Override
	/**
	 * löschen der Kinder aller Elemente 
	 */
	public void clear() {
		try {
			// doLogicalClear();
		} finally {
			// Löscht alle existierenden Kinder der Elemente
			Node child = getElement().getFirstChild();
			while (child != null) {
				getElement().removeChild(child);
				child = getElement().getFirstChild();
			}
		}
	}

	/**
	 * Fügt ein Widget vor den festgelegten Index
	 * 
	 * @param w 
	 * 			Widget, welches eingefügrt werden soll
	 * @param beforeIndex 
	 * 		der Platz vor des Indexes, indem das widget eingefügt werden soll
	 * @throws IndexOutOfBoundsException wenn <code>beforeIndex</code> ist auser dem gültigkeitsbereich
	 */
	public void insert(Widget w, int beforeIndex) {
		insert(wrapWidget(w), getElement(), beforeIndex, true);
	}

	/**
	 * Das LI Element für die Nutzung in {@link UlListPanel}s.
	 */
	private static class LiPanel extends ComplexPanel implements InsertPanel {

		protected LiPanel() {
			setElement(Document.get().createLIElement());
		}

		/**
		 * Fügt ein neues Kind Widget zu dem Panel
		 * 
		 * @param w , Widget welches hinzugefügt werden soll
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
				// löscht alle Kindknoten
				Node child = getElement().getFirstChild();
				while (child != null) {
					getElement().removeChild(child);
					child = getElement().getFirstChild();
				}
			}
		}

		/**

		 * Fügt ein Widget ein, vor den festgelegten Index
		 * 
		 * @param w 
		 * 			das Widget, welches eingefügt werden soll
		 * @param beforeIndex 
		 * 			der Platz vor des Indexes, indem das widget eingefügt werden soll
		 * 
		 * @throws IndexOutOfBoundsException 
		 * 			wenn<code>beforeIndex</code> ist nicht mehr in dem Gültigkeitsbereich
		 */
		public void insert(Widget w, int beforeIndex) {
			insert(w, getElement(), beforeIndex, true);
		}
	}
}