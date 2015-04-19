package de.hdm.groupfive.itproject.shared.bo;

import java.io.Serializable;
import java.util.HashMap;

public class Partlist extends HashMap<Element,Integer> implements Serializable {
	
	/**
	 * 
	 * @param element
	 * @return
	 */
	public int getQuantityByElement(Element element) {
		int result = -1;
		if (this.containsKey(element)) {
			result = this.get(element);
		}
		return result;
	}

}
