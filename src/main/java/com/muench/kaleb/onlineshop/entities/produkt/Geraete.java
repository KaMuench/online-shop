/**
 * 
 * @author B�nyamin Berber
 */

package com.muench.kaleb.onlineshop.entities.produkt;


public abstract class Geraete extends Produkt {

	/*
	 * ### Variablen ###
	 */
	private byte ram;
	private short festplSpeicher;
	private String cpu;
	private String gpu;
	private boolean laufwerk;
	private boolean kamera;
	private String displaygroesse;

	/*
	 * ### Konstruktor ###
	 */
	// ohne Artikelnummer
	public Geraete(String prodName, String marke, double preis, int anzahl, byte ram, short festplSpeicher, String cpu,
			String gpu, boolean laufwerk, boolean kamera, String displaygroesse) {
		super(prodName, marke, preis, anzahl);
		this.ram = ram;
		this.festplSpeicher = festplSpeicher;
		this.cpu = cpu;
		this.gpu = gpu;
		this.laufwerk = laufwerk;
		this.kamera = kamera;
		this.displaygroesse = displaygroesse;
	}
	// mit Artikelnummer
	public Geraete(Integer artNum, String prodName, String marke, double preis, int anzahl, byte ram, short festplSpeicher, String cpu,
			String gpu, boolean laufwerk, boolean kamera, String displaygroesse) {
		super(artNum, prodName, marke, preis, anzahl);
		this.ram = ram;
		this.festplSpeicher = festplSpeicher;
		this.cpu = cpu;
		this.gpu = gpu;
		this.laufwerk = laufwerk;
		this.kamera = kamera;
		this.displaygroesse = displaygroesse;
	}
	
	public Geraete() {
		
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
	
	
	/*
	 * ### toString ###
	 */
	@Override
	public String toString() {
		return "Geraete [Ram=" + ram + ", festplSpeicher=" + festplSpeicher + ", cpu=" + cpu + ", gpu=" + gpu
				+ ", laufwerk=" + laufwerk + ", kamera=" + kamera + ", displaygrosse=" + displaygroesse
				+ ", toString()=" + super.toString() + "]";
	}
	
	@Override
	public String speichernInTxt() {
		return getProduktname() + ";"  +  getMarke() + ";" +  getPreis() + ";" +  getAnzahl() + ";" + getRam() + ";" +  getFestplSpeicher() + ";" +  getCpu() + ";" +  getGpu() + ";" +  getLaufwerk() + ";" +  getKamera() + ";" +  getDisplaygroesse() + ";"  ;
	}
	
	/*
	 * ### toDesignFormat ###
	 */
	@Override
	public abstract String toDesignFormat();
	

	/*
	 * ### Getter und Setter ###
	 */
	public byte getRam() {
		return ram;
	}

	public void setRam(byte ram) {
		this.ram = ram;
	}

	public short getFestplSpeicher() {
		return festplSpeicher;
	}

	public void setFestplSpeicher(short festplSpeicher) {
		this.festplSpeicher = festplSpeicher;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getGpu() {
		return gpu;
	}

	public void setGpu(String gpu) {
		this.gpu = gpu;
	}

	public boolean getLaufwerk() {
		return laufwerk;
	}

	public void setLaufwerk(boolean laufwerk) {
		this.laufwerk = laufwerk;
	}

	public boolean getKamera() {
		return kamera;
	}

	public void setKamera(boolean kamera) {
		this.kamera = kamera;
	}

	public String getDisplaygroesse() {
		return displaygroesse;
	}

	public void setDisplaygroesse(String displaygrosse) {
		this.displaygroesse = displaygrosse;
	}

}
