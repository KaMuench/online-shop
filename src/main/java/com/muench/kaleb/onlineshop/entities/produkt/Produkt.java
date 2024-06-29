/**
 *  Die Oberklasse Produkt.
 *  Dies sind alle Produkte die man im OnlineShop kaufen kann.
 *  
 *  @author B�nyamin Berber
 */
package com.muench.kaleb.onlineshop.entities.produkt;



import com.muench.kaleb.onlineshop.ui.design.AusgabenLayout;
import com.muench.kaleb.onlineshop.io.OnlineShopIO;

public abstract class Produkt implements Comparable<Produkt>, AusgabenLayout, OnlineShopIO{

	/*
	 * ### Variablen ###
	 */
	private Integer artNum;
	private String prodName;
	private String marke;
	private double preis;
	private int anzahl;

	/*
	 * ### Konstruktor ###
	 */
	//Ohne Artikelnummer
	public Produkt(String prodName, String marke, double preis, int anzahl) {
		super();
		this.prodName = prodName;
		this.marke = marke;
		this.preis = preis;
		this.anzahl = anzahl;
	}
	
	//Mit Artikelnummer
	public Produkt(Integer artNum, String prodName, String marke, double preis, int anzahl) {
		this.artNum = artNum;
		this.prodName = prodName;
		this.marke = marke;
		this.preis = preis;
		this.anzahl = anzahl;
	}
	
	public Produkt() {
		
	}

	//### equals ###
	public boolean equals(Object other) {
		 if(other instanceof Produkt) {
			if(((Produkt) other).getArtikelnummer() == this.getArtikelnummer()) {
				return true;
			}else {
				return false;
			}
		 }else {
			 return false;
		 }
	 }
	
	/*
	 * ### CompareTo ### 
	 */
	@Override
	public int compareTo(Produkt produkt) {
		return getArtikelnummer().compareTo(produkt.getArtikelnummer());
	}
	
	/*
	 * ### toString ###
	 */
	@Override
	public String toString() {
		return "Produkt [prodName=" + prodName + ", marke=" + marke + ", preis=" + preis + ", anzahl=" + anzahl
				+ ", artNum=" + artNum + "]\n";
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
	public Produkt macheKopie(boolean anzahlEins) {		
		Produkt retProdukt = null;
		return retProdukt;
	}
	
	@Override
	public abstract String speichernInTxt();
	
	/**
	 * ### checkAnzahl ###
	 * 
	 * Diese Methode gibt false zur�ck wenn Anzahl == 0 ist ansonsten true und verringert Anzahl um 1.
	 * @return true/false	Wenn die Anzahl der Instanz 0 ist wird false zur�ck gegeben ansonsten true.
	 */
	public boolean checkAnzahl() {
		if(this.getAnzahl() == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	/*
	 * ### Getter und Setter ###
	 */
	public Integer getArtikelnummer() {
		return artNum;
	}

	public void setArtikelnummer(Integer artNum) {
		this.artNum = artNum;
	}

	public String getProduktname() {
		return prodName;
	}

	public void setProduktname(String prodName) {
		this.prodName = prodName;
	}

	public String getMarke() {
		return marke;
	}

	public void setMarke(String marke) {
		this.marke = marke;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public int getAnzahl() {
		return anzahl;
	}
	
	public void setAnzahl(int anzahl) {
		this.anzahl = anzahl;
	}
	

}
