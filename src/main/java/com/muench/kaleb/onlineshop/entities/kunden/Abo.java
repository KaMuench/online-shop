package com.muench.kaleb.onlineshop.entities.kunden;

public class Abo {

	private final double rabatt = 0.10;
	private final String name = "Jahresabo";
	private final String preis = "59.99 � im Jahr.";
	private boolean deabboniert = false;

	// Konstruktor
	public Abo(boolean deabboniert) {
		this.deabboniert = deabboniert;
	}
	public Abo() {
		
	}

	// ### Getter und Settter ###

	public double getRabatt() {
		return rabatt;
	}

	public String getName() {
		return name;
	}

	public String getPreis() {
		return preis;
	}

	public boolean isDeabboniert() {
		return deabboniert;
	}

	public void setDeabboniert(boolean deabboniert) {
		this.deabboniert = deabboniert;
	}

}
