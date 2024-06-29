/**
 * 
 * @author B�nyamin Berber
 */

package com.muench.kaleb.onlineshop.entities.produkt;

public class Computer extends Geraete {

	private static int zaehler = 1;
	private static final int ARTNUMBEREICH = 1000;

	/*
	 * ### Konstruktor ###
	 */
	//Ohne Artikelnummer
	public Computer(String prodName, String marke, double preis, int anzahl, byte ram, short festplSpeicher, String cpu,
			String gpu, boolean laufwerk, boolean kamera, String displaygrosse) {
		super(prodName, marke, preis, anzahl, ram, festplSpeicher, cpu, gpu, laufwerk, kamera, displaygrosse);
		super.setArtikelnummer(ARTNUMBEREICH + zaehler);
		zaehler++;
	}
	
	//Mit Artikelnummer
	public Computer(Integer artNum, String prodName, String marke, double preis, int anzahl, byte ram, short festplSpeicher, String cpu,
			String gpu, boolean laufwerk, boolean kamera, String displaygrosse) {
		super(artNum, prodName, marke, preis, anzahl, ram, festplSpeicher, cpu, gpu, laufwerk, kamera, displaygrosse);
	}
	
	public Computer() {
		super();
	}

	/*
	 * ### toDesignFormat ###
	 */
	@Override
	public String toDesignFormat() {

		return "Name;" + "Computer " + getProduktname() + ";Marke;" +  ";Artikelnummer;" + getArtikelnummer() + ";RAM;" + getRam() + " GB;Fest. Speicher;"
				+ getFestplSpeicher() + " GB;CPU;" + getCpu() + ";GPU;" + getGpu() + ";Laufwerk;" + getLaufwerk()
				+ ";Kamera;" + getKamera() + ";Displaygr��e;" + getDisplaygroesse() + ";Preis;" + getPreis()
				+ " �;Auf Lager;" + getAnzahl();

	}
	
	@Override
	public String speichernInTxt() {
		
		return "Computer;" + super.speichernInTxt();
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
			 retProdukt = new Computer(this.getArtikelnummer(), this.getProduktname(),this.getMarke(), this.getPreis(), 1, this.getRam(), this.getFestplSpeicher(), this.getCpu(), this.getGpu(), this.getLaufwerk(), this.getKamera(), this.getDisplaygroesse());
		}else {
			 retProdukt = new Computer(this.getArtikelnummer(), this.getProduktname(),this.getMarke(), this.getPreis(), this.getAnzahl(), this.getRam(), this.getFestplSpeicher(), this.getCpu(), this.getGpu(), this.getLaufwerk(), this.getKamera(), this.getDisplaygroesse());	
		}
		
		return retProdukt;
	}
	
	
	//### Getter und Setter ###
	public static int getArtNumBereich() {
		return ARTNUMBEREICH;
	}
		
}
