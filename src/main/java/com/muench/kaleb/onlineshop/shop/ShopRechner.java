/**
 * Diese Klasse dient der Berechnung des Gesamtpreises f�r alle Produkte eines Produktarrays.
 * 
 * @author Kaleb Muench
 */
package com.muench.kaleb.onlineshop.shop;

import com.muench.kaleb.onlineshop.entities.produkt.Produkt;

public class ShopRechner {
	// Gibt den Gesamtwert der Produkte in des Arrays als double zur�ck.
	public static double getGesamtpreisDouble(Produkt[] produktArray) {
		double retPreis = 0;
		for (Produkt p : produktArray) {
			retPreis += p.getAnzahl() * p.getPreis();
		}
		retPreis = rundePreis(retPreis);
		return retPreis;
	}

	// Gibt den Gesamtwert der Produkte im Array als String zur�ck.
	public static String getGesamtpreisString(Produkt[] produktArray) {
		StringBuilder retString = new StringBuilder();
		double retPreis = 0.0;

		for (Produkt p : produktArray) {
			retPreis += p.getAnzahl() * p.getPreis();
		}
		retPreis = rundePreis(retPreis);
		retString.append(retPreis);
		if (retPreis % 1 == 0) {
			retString.append('0');
		} else {
			retString.deleteCharAt(retString.length() - 3);
			retString.insert(retString.length() - 2, ',');
		}
		retString.deleteCharAt(retString.length() - 3);
		retString.insert(retString.length() - 2, ',');
		for (int i = retString.length() - 6; i > 0; i -= 3) {
			retString.insert(i, '.');
		}

		retString.append(" �");
		return retString.toString();
	}

	// Gibt den Gesamtwert der Produkte im Array alss String zur�ck, abz�glich des
	// Rabbats
	public static String getGesamtpreisString(double rabatt, Produkt[] produktArray) {
		StringBuilder retString = new StringBuilder();
		double retPreis = 0.0;

		for (Produkt p : produktArray) {
			retPreis += p.getAnzahl() * p.getPreis();
		}
		retPreis -= retPreis * rabatt;
		retPreis = rundePreis(retPreis);
		retString.append(retPreis);
		if (retPreis % 1 == 0) {
			retString.append('0');
		} else {
			retString.deleteCharAt(retString.length() - 3);
			retString.insert(retString.length() - 2, ',');
		}
		retString.deleteCharAt(retString.length() - 3);
		retString.insert(retString.length() - 2, ',');
		for (int i = retString.length() - 6; i > 0; i -= 3) {
			retString.insert(i, '.');
		}

		retString.append(" �");
		return retString.toString();
	}

	// Rundet einen Double auf 2 Nachkommastellen
	public static double rundePreis(double preis) {
		double d = Math.pow(10, 2);
		return (Math.round(preis * d) / d);
	}

}
