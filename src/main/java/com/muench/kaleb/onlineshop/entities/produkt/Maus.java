/**
 * 
 * @author B�nyamin Berber
 */

package com.muench.kaleb.onlineshop.entities.produkt;

public class Maus extends Zubehoer {

	/*
	 * ### Variablen ###
	 */
	private String signal;
	private String aufloesung;
	private byte funktionsTasten;
	private static int zaehler = 1;
	private static final int ARTNUMBEREICH = 4000;

	/*
	 * ### Konstruktor ###
	 */
	//Ohne Artikelnummer
	public Maus(String prodName, String marke, double preis, int anzahl, String signal, String aufloesung,
			byte funktionsTasten) {
		super(prodName, marke, preis, anzahl);
		this.signal = signal;
		this.aufloesung = aufloesung;
		this.funktionsTasten = funktionsTasten;
		super.setArtikelnummer(ARTNUMBEREICH+ zaehler);
		zaehler++;
	}
	
	//Mit Artikelnummer
	public Maus(int artNum, String prodName, String marke, double preis, int anzahl, String signal, String aufloesung,
			byte funktionsTasten) {
		super(artNum, prodName, marke, preis, anzahl);
		this.signal = signal;
		this.aufloesung = aufloesung;
		this.funktionsTasten = funktionsTasten;
	}
	
	@Override
	public String speichernInTxt() {
		return "Maus;" + getProduktname() + ";"  +  getMarke() + ";" +  getPreis() + ";" +  getAnzahl() + ";" + getSignal() + ";" +  getAufloesung() + ";" +  getFunktionsTasten() + ";";
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
			 retProdukt = new Maus(this.getArtikelnummer(), this.getProduktname(),this.getMarke(), this.getPreis(), 1,this.getSignal(), this.getAufloesung(), this.getFunktionsTasten());
		}else {
			 retProdukt = new Maus(this.getArtikelnummer(), this.getProduktname(),this.getMarke(), this.getPreis(), this.getAnzahl(),this.getSignal(), this.getAufloesung(), this.getFunktionsTasten());
		}
		
		return retProdukt;
	}
	
	
	/*
	 * ### toString ###
	 */
	@Override
	public String toString() {
		return "Maus [signal=" + signal + ", aufloesung=" + aufloesung + ", funktionsTasten=" + funktionsTasten
				+ ", toString()=" + super.toString() + "]";
	}


	/*
	 * ###   toDesignFormat   ###
	 */
	@Override
	public String toDesignFormat() {
		return "Name;" + "Maus " + getProduktname() + ";Marke;" + getMarke() + ";Artikelnummer;" + getArtikelnummer() + ";Anschluss;" + getSignal() + ";Aufl�sung;" + getAufloesung() + ";Preis;" + getPreis() + "�;Auf Lager;" + getAnzahl();
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

	public String getAufloesung() {
		return aufloesung;
	}

	public void setAufloesung(String aufloesung) {
		this.aufloesung = aufloesung;
	}

	public byte getFunktionsTasten() {
		return funktionsTasten;
	}

	public void setFunktionsTasten(byte funktionsTasten) {
		this.funktionsTasten = funktionsTasten;
	}

	public static int getArtnumbereich() {
		return ARTNUMBEREICH;
	}


}
