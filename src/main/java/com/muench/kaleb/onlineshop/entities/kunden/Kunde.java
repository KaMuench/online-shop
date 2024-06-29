package com.muench.kaleb.onlineshop.entities.kunden;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.muench.kaleb.onlineshop.ui.design.*;
import com.muench.kaleb.onlineshop.io.OnlineShopIO;
import com.muench.kaleb.onlineshop.entities.zahlungsmethoden.*;
import com.muench.kaleb.onlineshop.entities.bestellung.Bestellungen;

public class Kunde implements AusgabenLayout, OnlineShopIO {

	/*
	 * ### Variablen ###
	 */
	private String passwort;
	private ArrayList<Bestellungen> bestellVerlauf;
	private Abo abo;
	private Zahlungsmethode[] zahlungsmethode;
	private static int zaehler = 1;
	private final static int KENNNUMMERBEREICH = 1000;
	private String name;
	private String vorname;
	private String geburtstag;
	private Adresse adresse;
	private int kennNummer;
	private String benutzerName;

	//Konstruktor
	public Kunde() {

	}

	public Kunde(String name, String vorname, String geburtstag, Adresse adresse, String passwort,
			String benutzerName) {
		super();
		this.bestellVerlauf = new ArrayList<Bestellungen>();
		this.passwort = passwort;
		this.name = name;
		this.vorname = vorname;
		this.geburtstag = geburtstag;
		this.adresse = adresse;
		this.benutzerName = benutzerName;
		this.zahlungsmethode = new Zahlungsmethode[5];
		setKennNummer(KENNNUMMERBEREICH + zaehler);
		zaehler++;
	}

	// Gibt den Kunden in einem geeignetem Layout aus
	public byte zeigeKunde() {
		Design window = new Design();
		// Splitet den String R�ckgabewert des objekts und f�llt ein Array mit den
		// einzelnen Strings.
		String[] array = this.toDesignFormat().split(";");
		// H�lt den aktuellen Zeilenstand des aktuellen Fensters fest.

		window.ausgabeZeileL(array[0] + ":", array[1]);
		window.ausgabeZeileL(array[2] + ":", array[3]);
		window.ausgabeZeile();
		window.ausgabeZeileL(array[4] + ":", array[5]);
		window.ausgabeZeileL(array[6] + ":", array[7]);
		window.ausgabeZeile();
		window.ausgabeZeileL(array[8] + ":", array[9]);
		window.ausgabeZeileL("", array[10]);
		window.ausgabeZeileL("", array[11]);
		window.ausgabeZeile();
		window.ausgabeZeileL(array[12] + ":", getGeburtsdatumFormat(array[13]));
		window.ausgabeZeile();
		window.ausgabeZeileL(array[14] + ":");
		// Da ein Kunde maximal 5 Zahlungsmethoden haben kann, und f�r jede
		// Zahlungsmethode
		// deren Name und Zahlungsadresse ausgegeben wird, verbrauchen sie 5*2 Pl�tze im
		// Array.
		for (int i = 0; i < 10; i += 2) {
			window.ausgabeZeileL(array[i + 15], array[i + 16]);
		}
		window.ausgabeZeile();
		window.ausgabeZeileL(array[array.length - 2], array[array.length - 1]);

		// Gibt die Anzahl der Zeilen zur�ck die genutzt wurden um das Objekt auf der
		// Konsole auszugeben.
		return (byte) window.getZeilenAnzahl();
	}

	// Innere Klassen mit Comparatoren
	public static class MitVorname implements Comparator<Kunde> {
		@Override
		public int compare(Kunde k1, Kunde k2) {
			return k1.getVorname().compareTo(k2.getVorname());
		}
	}
	public static class MitNachname implements Comparator<Kunde> {
		@Override
		public int compare(Kunde k1, Kunde k2) {
			return k1.getName().compareTo(k2.getName());
		}
	}
	public static class MitKennNummer implements Comparator<Kunde> {
		@Override
		public int compare(Kunde k1, Kunde k2) {
			return Integer.valueOf(k1.getKennNummer()).compareTo(Integer.valueOf(k2.getKennNummer()));
		}
	}
	public static class MitBenutzername implements Comparator<Kunde> {
		@Override
		public int compare(Kunde k1, Kunde k2) {
			return k1.getBenutzerName().compareTo(k2.getBenutzerName());
		}
	}
	public static class MitGeburtstag implements Comparator<Kunde> {
		@Override
		public int compare(Kunde k1, Kunde k2) {
			if(k1.getGeburtstag().substring(4).compareTo(k2.getGeburtstag().substring(4)) < 0) {
				return -1;
			}else if(k1.getGeburtstag().substring(4).compareTo(k2.getGeburtstag().substring(4)) > 0){
				return 1;
			}else {
				if(k1.getGeburtstag().substring(2,4).compareTo(k2.getGeburtstag().substring(2,4)) < 0) {
					return -1;
				}else if(k1.getGeburtstag().substring(2,4).compareTo(k2.getGeburtstag().substring(2,4)) > 0){
					return 1;
				}else {
					if(k1.getGeburtstag().substring(0,2).compareTo(k2.getGeburtstag().substring(0,2)) < 0) {
						return -1;
					}else if(k1.getGeburtstag().substring(0,2).compareTo(k2.getGeburtstag().substring(0,2)) > 0){
						return 1;
					}else {
						return 0;
					}
				}
			}
		}
	}

	// Methode um das Geburtstdatum mit Punkten getrennt anzuzeigen.
	public static String getGeburtsdatumFormat(String string) {
		if (string.length() == 0) {
			return string;
		} else {
			StringBuilder stringB = new StringBuilder(string);
			stringB.insert(2, '.');
			stringB.insert(5, '.');
			return stringB.toString();
		}

	}

	// ### toDesignFormat ###
	@Override
	public String toDesignFormat() {
		String aboString;
		if (abo == null) {
			aboString = "Zurzeit nichts abboniert!";
		} else if (abo.isDeabboniert()) {
			aboString = "Zurzeit nichts abboniert!";
		} else {
			aboString = abo.getName();
		}
		return "Name;" + name + ";Vorname;" + vorname + ";Benutzername;" + benutzerName + ";Kundennummer;" + kennNummer
				+ ";Adresse;" + adresse.getStrasse() + ";" + adresse.getPlz() + ";" + adresse.getOrt() + ";Geburtstag;"
				+ geburtstag + ";Zahlungsmethode;" + getZahlungsmethodeString() + ";Abo;" + aboString;
	}

	/*
	 * ### getZahlungsmethodenString ###
	 * 
	 * Gibt einen String zur�ck der dann in toDesignFormat genutzt wird.
	 */
	public String getZahlungsmethodeString() {

		StringBuilder retString = new StringBuilder();

		for (Zahlungsmethode z : zahlungsmethode) {
			if (z instanceof Paypal) {
				Paypal p = (Paypal) z;
				retString.append(p.getName() + ";" + p.getZahlungsAdresse() + ";");
			} else if (z instanceof Ueberweisung) {
				Ueberweisung u = (Ueberweisung) z;
				retString.append(u.getName() + ";" + u.getZahlungsAdresse() + ";");
			} else {
				retString.append("Frei;Sie k�nnen hier noche eine Zahlungsmethode hinzuf�gen. ;");
			}

		}
		return retString.toString();
	}

	// ### equals ###
	@Override
	public boolean equals(Object other) {
		if (other instanceof Kunde) {
			if (((Kunde) other).getKennNummer() == this.getKennNummer()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	// ### equalsKunde ###
	// Speziell f�r die loggin() Methode der Kundenverwaltung
	public boolean equalsKunde(Kunde k) {
		if (k.getPasswort().equals(this.getPasswort()) && k.getBenutzerName().equals(this.getBenutzerName())) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * ### toString ###
	 */
	@Override
	public String toString() {
		return "Kunde [passwort=" + passwort + ", bestellVerlauf=" + bestellVerlauf + ", abo=" + abo
				+ ", zahlungsmethode=" + Arrays.toString(zahlungsmethode) + ", name=" + name + ", vorname=" + vorname
				+ ", geburtstag=" + geburtstag + ", adresse=" + adresse + ", kennNummer=" + kennNummer
				+ ", benutzerName=" + benutzerName + "]";
	}

	// sortiert Zahlungsmethoden Array
	// Wenn Zahlungsmethoden im Array sind, sollen sie immer vorne sein.
	public void sortierenZahlungsM() {
		Zahlungsmethode[] retArray = zahlungsmethode;
		zahlungsmethode = new Zahlungsmethode[5];

		for (int i = 0, e = 0; i < retArray.length; i++) {
			if (retArray[i] == null) {
			} else {
				zahlungsmethode[e] = retArray[i];
				e++;
			}
		}
	}

	@Override
	public String speichernInTxt() {
		if (abo == null) {
			return name + ";" + vorname + ";" + geburtstag + ";" + adresse.getStrasse() + ";" + adresse.getPlz() + ";"
					+ adresse.getOrt() + ";" + passwort + ";" + benutzerName + ";null;";
		} else {
			return name + ";" + vorname + ";" + geburtstag + ";" + adresse.getStrasse() + ";" + adresse.getPlz() + ";"
					+ adresse.getOrt() + ";" + passwort + ";" + benutzerName + ";" + abo.isDeabboniert() + ";";
		}

	}

	/*
	 * ### Getter und Setter ###
	 */
	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public ArrayList<Bestellungen> getBestellVerlauf() {
		return bestellVerlauf;
	}

	public void setBestellVerlauf(ArrayList<Bestellungen> bestellVerlauf) {
		this.bestellVerlauf = bestellVerlauf;
	}

	public Abo getAbo() {
		return abo;
	}

	public void setAbo(Abo abo) {
		this.abo = abo;
	}

	public Zahlungsmethode[] getZahlungsmethode() {
		return zahlungsmethode;
	}

	public void setZahlungsmethode(Zahlungsmethode zahlungsmethode, byte index) {
		this.zahlungsmethode[index] = zahlungsmethode;
	}

	public void setZahlungsmethoden(Zahlungsmethode[] zahlungsmethode) {
		this.zahlungsmethode = zahlungsmethode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getGeburtstag() {
		return geburtstag;
	}

	public void setGeburtstag(String geburtstag) {
		this.geburtstag = geburtstag;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public int getKennNummer() {
		return kennNummer;
	}

	public void setKennNummer(int kennNummer) {
		this.kennNummer = kennNummer;
	}

	public String getBenutzerName() {
		return benutzerName;
	}

	public void setBenutzerName(String benutzerName) {
		this.benutzerName = benutzerName;
	}

	public static int getKennnummerbereich() {
		return KENNNUMMERBEREICH;
	}

}
