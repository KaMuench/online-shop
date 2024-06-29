/**
 * 
 * @author B�nyamin Berber
 */

package com.muench.kaleb.onlineshop.entities.produkt;



public abstract class Zubehoer extends Produkt{


	/*
	 * ### Konstruktor ###
	 */
	public Zubehoer(String prodName, String marke, double preis, int anzahl) {
		super(prodName, marke, preis, anzahl);

	}
	public Zubehoer(int artNum, String prodName, String marke, double preis, int anzahl) {
		super(artNum, prodName, marke, preis, anzahl);
	}
	
	
	/**
	 * 
	 * ### macheKopie ###
	 *
	 * Diese Methode soll eine Kopie der Produktinstanz oder ihrer Unterklassen erstellen.
	 * 
	 * @return	Produkt			gibt die Kopie der Produktinstanz zur�ck
	 * 
	 * @param	anzahlEins		falls die Anzahl der Kopie der Produktinstanz auf 1 gesetzt werden soll
	 */
	@Override
	public Produkt macheKopie(boolean anzahlEins) {		
		Produkt retProdukt = null;
		return retProdukt;
	}
}
