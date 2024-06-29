/**
 * class: DesignException erbt von Exceptions
 * 
 * Falls fehler in der Design Klasse auftreten wird eine DesignException geworfen.
 * 
 * @author Kaleb Muench
 */

package com.muench.kaleb.onlineshop.ui.design;

public class DesignException extends Exception {

	public DesignException() {
		super("DesignException");
	}
	
	public DesignException(String art) {
		super(art);
	}
}
