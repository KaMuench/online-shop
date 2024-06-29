package com.muench.kaleb.onlineshop.entities.kunden;

public class Adresse {
	/*
	 * ### Variablen ###
	 */
	private String strasse;
	private String plz;
	private String ort;

	/*
	 * ### Konstruktor ###
	 */
	public Adresse(String strasse, String plz, String ort) {
		super();
		this.strasse = strasse;
		this.plz = plz;
		this.ort = ort;
	}

	/*
	 * ### toString ###
	 */
	@Override
	public String toString() {
		return "Adresse [strasse=" + strasse + ", plz=" + plz + ", ort=" + ort + "]";
	}
	
	
	/*
	 * ### Getter und Setter
	 */
	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

}
