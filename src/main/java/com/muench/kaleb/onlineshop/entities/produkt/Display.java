/**
 * 
 * @author B�nyamin Berber
 */

package com.muench.kaleb.onlineshop.entities.produkt;

public class Display extends Zubehoer {

	/*
	 * ### Variablen ###
	 */
	private String groesse;
	private String reaktionsZeit;
	private String hertz;
	private static int zaehler = 1;
	private static final int ARTNUMBEREICH = 6000;

	/*
	 * ### Konstruktor ###
	 */
	//Ohne Artikelnummer
	public Display(String prodName, String marke, double preis, int anzahl, String groesse, String reaktionsZeit,
			String hertz) {
		super(prodName, marke, preis, anzahl);
		this.groesse = groesse;
		this.reaktionsZeit = reaktionsZeit;
		this.hertz = hertz;
		super.setArtikelnummer(ARTNUMBEREICH + zaehler);
		zaehler++;
	}
	//Mit Artikelnummer
	public Display(int artNum, String prodName, String marke, double preis, int anzahl, String groesse, String reaktionsZeit,
			String hertz) {
		super(artNum, prodName, marke, preis, anzahl);
		this.groesse = groesse;
		this.reaktionsZeit = reaktionsZeit;
		this.hertz = hertz;
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
			 retProdukt = new Display(this.getArtikelnummer(), this.getProduktname(),this.getMarke(), this.getPreis(), 1,this.getGroesse(), this.getReaktionsZeit(), this.getHertz());
		}else {
			 retProdukt = new Display(this.getArtikelnummer(), this.getProduktname(),this.getMarke(), this.getPreis(), this.getAnzahl(),this.getGroesse(), this.getReaktionsZeit(), this.getHertz());	
		}
		
		return retProdukt;
	}

	/*
	 * ### toString ###
	 */
	@Override
	public String toString() {
		return "Display [groe�e=" + groesse + ", reaktionsZeit=" + reaktionsZeit + ", hertz=" + hertz + ", toString() ="
				+ super.toString() + "]";
	}
	
	/*
	 * ###   toDesignFormat   ###
	 */
	@Override
	public String toDesignFormat() {
		return "Name;" + "Display " + getProduktname() + ";Marke;" + getMarke() + ";Artikelnummer;" + getArtikelnummer() + ";Gr��e;" + getGroesse() + ";Reaktionszeit;" + getReaktionsZeit() + ";Preis;" + getPreis() + " �;Auf Lager;" + getAnzahl();
	}

	@Override
	public String speichernInTxt() {
		return "Display;" + getProduktname() + ";"  +  getMarke() + ";" +  getPreis() + ";" +  getAnzahl() + ";" + getGroesse() + ";" +  getReaktionsZeit() + ";" +  getHertz() + ";";
	}
	
	/*
	 * ### Getter und Setter ###
	 */
	public String getGroesse() {
		return groesse;
	}

	public void setGroesse(String groesse) {
		this.groesse = groesse;
	}

	public String getReaktionsZeit() {
		return reaktionsZeit;
	}

	public void setReaktionsZeit(String reaktionsZeit) {
		this.reaktionsZeit = reaktionsZeit;
	}

	public String getHertz() {
		return hertz;
	}

	public void setHertz(String hertz) {
		this.hertz = hertz;
	}

	public static int getArtnumbereich() {
		return ARTNUMBEREICH;
	}

	
}
