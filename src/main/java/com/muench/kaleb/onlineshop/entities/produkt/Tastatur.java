/**
 * 
 * @author B�nyamin Berber
 */

package com.muench.kaleb.onlineshop.entities.produkt;

public class Tastatur extends Zubehoer {

	/*
	 * ### Variablen ###
	 */
	private String signal;
	private String belegung;
	private static int zaehler = 1;
	private static  final int ARTNUMBEREICH = 5000;

	/*
	 * ### Konstruktor ###
	 */
	//Ohne Artikelnummer
	public Tastatur(String prodName, String marke, double preis, int anzahl, String signal, String belegung) {
		super(prodName, marke, preis, anzahl);
		this.signal = signal;
		this.belegung = belegung;
		super.setArtikelnummer(ARTNUMBEREICH + zaehler);
		zaehler++;
	}
	
	//Mit Artikelnummer
	public Tastatur(int artNum, String prodName, String marke, double preis, int anzahl, String signal, String belegung) {
		super(artNum, prodName, marke, preis, anzahl);
		this.signal = signal;
		this.belegung = belegung;
	}
	
	@Override
	public String speichernInTxt() {
		return "Tastatur;" + getProduktname() + ";"  +  getMarke() + ";" +  getPreis() + ";" +  getAnzahl() + ";" + getSignal() + ";" +  getBelegung() + ";";
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
		if(anzahlEins) {
			 retProdukt = new Tastatur(this.getArtikelnummer(), this.getProduktname(),this.getMarke(), this.getPreis(), 1,this.getSignal(), this.getBelegung());
		}else {
			 retProdukt = new Tastatur(this.getArtikelnummer(), this.getProduktname(),this.getMarke(), this.getPreis(), this.getAnzahl(),this.getSignal(), this.getBelegung());
		}
		
		return retProdukt;
	}
	
	

	/*
	 * ### toString ###
	 */
	@Override
	public String toString() {
		return "Tastatur [signal=" + signal + ", belegung=" + belegung + ",toString() = " + super.toString() + "]";
	}

	/*
	 * ###   toDesignFormat   ###
	 */
	@Override
	public String toDesignFormat() {
		return "Name;" + "Tastatur" + getProduktname() + ";Marke;" + getMarke() + ";Artikelnummer;" + getArtikelnummer() + ";Anschluss;" + getSignal() + ";Layout;" + getBelegung() + ";Preis;" + getPreis() + ";Auf Lager;" + getAnzahl();
	}
	
	/*
	 * ### Getter und Setter ###
	 */
	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}

	public String getBelegung() {
		return belegung;
	}

	public void setBelegung(String belegung) {
		this.belegung = belegung;
	}

	public static int getArtnumbereich() {
		return ARTNUMBEREICH;
	}
	
	

}
