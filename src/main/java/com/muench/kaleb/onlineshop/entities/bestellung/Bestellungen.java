/**
 * 
 * @author Buenyamin Berber
 */
package com.muench.kaleb.onlineshop.entities.bestellung;

import com.muench.kaleb.onlineshop.ui.design.Design;
import com.muench.kaleb.onlineshop.entities.kunden.Abo;
import com.muench.kaleb.onlineshop.entities.produkt.Produkt;
import com.muench.kaleb.onlineshop.shop.kundenverwaltung.BenutzerEingabe;
import com.muench.kaleb.onlineshop.io.OnlineShopIO;
import com.muench.kaleb.onlineshop.shop.*;


public class Bestellungen implements OnlineShopIO {

	/*
	 * ### Variablen ###
	 */
	private final ZeitStempel ZEITSTEMPEL;
	private final Produkt[] INHALT;
	private final boolean MITABOBEZAHLT;
	private static final String[] OPTIONSLEISTEN_BESTELLUNGEN = { "2. Zur�ck:",
			"Um zur�ckzukehren, geben Sie die 2 ein.", "8. N�chste Seite:",
			"Um zur n�chsten Seite zu bl�ttern, geben Sie die 8 ein.", "9. Vorherige Seite:",
			"Um zur vorherigen Seite zubl�ttern, geben Sie 9 ein. " };

	/*
	 * ### Konstruktor ###
	 */
	public Bestellungen(Warenkorb w, boolean MITABOBEZAHLT) {
		this.ZEITSTEMPEL = new ZeitStempel();
		this.MITABOBEZAHLT = MITABOBEZAHLT;
		Object[] oArray = w.getWarenkorb().toArray();
		Produkt[] retArray = new Produkt[oArray.length];
		for (int i = 0; i < oArray.length; i++) {
			retArray[i] = (Produkt) oArray[i];
		}
		this.INHALT = retArray;
	}
	
	// wird aufgerufen sobald ein Kauf abegschlossen wurde
	public Bestellungen(Produkt[] p, boolean MITABOBEZAHLT, ZeitStempel z) {
		this.MITABOBEZAHLT = MITABOBEZAHLT;
		this.ZEITSTEMPEL = z;
		this.INHALT = p;
	}
	
	/*
	 *  ### toString ###
	 */
	@Override
	public String toString() {
		return ZEITSTEMPEL.toString();
	}
	
	/*
	 * ### Methoden ###
	 */
	@Override
	public String speichernInTxt() {
		return "Bestellung;" + MITABOBEZAHLT + ";" + ZEITSTEMPEL.speichernInTxt();
	
	}
	/**
	 * ### oeffneBestellung ###
	 * 
	 * @author Kaleb Muench
	 */
	public void oeffneBestellung() {
		Design oeffneBestellungOB = new Design();
		BenutzerEingabe nutzerEingabeOB = new BenutzerEingabe();
		/*
		 * Die Anzahl der Produkte die in der aktuelle Runde ausgegeben wurde Je nachdem
		 * wie viele Zeilen es braucht um ein Produkt auszugeben, k�nnen, mehrere
		 * Produkte pro Schleifen Durchlauf ausgegeben werden. Wurden z.B. 3 Produkte
		 * pro Seite ausgegeben ist durchlauf = 3.
		 */
		byte indexOB = 0;

		byte durchlaufOB = 0;
		// Die Anzahl der Produkte die letzte Runde ausgegeben wurde.
		byte durchlaufDavorOB = 0;

		// Input des Users, zu navigation im Warenkorb
		int usrInputOB = 0;

		// Benachrichtigung des Arrays Shop.BENACHRICHTIGUNG.
		// Index 0 ist die falscher Input Benachrichtigung
		String benachrichtigungOB = Shop.getBenachrichtigung(0);

		// Die Zeilen die pro Produktausgabe genutzt wurden
		int benutzteZeilenOB = 0;

		// Die Zeilen die noch pro Fenster f�r weitere Ausgaben zu verf�gung stehen.
		// In diesem Layout sind es 28 Zeilen die f�r die Ausgabe der Produkte �brig
		// sind.
		byte zeilenZurVerfuegungOB = 27;

		boolean zurueckNichtMoeglichOB = false;

		while (true) {
			oeffneBestellungOB.ueberschrift("Bestellung vom " + this.ZEITSTEMPEL);
			oeffneBestellungOB.ausgabeZeile();

			if (INHALT.length == 0) {
				oeffneBestellungOB.ausgabeZeile("Der Warenkorb ist leer.");
				oeffneBestellungOB.ausgabeBlock(zeilenZurVerfuegungOB);
				zurueckNichtMoeglichOB = true;

			} else {
				while (benutzteZeilenOB <= zeilenZurVerfuegungOB) {
					try {
						benutzteZeilenOB = Warenkorb.zeigeProdukt(INHALT[indexOB], false);
						indexOB++;
						durchlaufOB++;
						zeilenZurVerfuegungOB -= benutzteZeilenOB;
					} catch (IndexOutOfBoundsException e) {
						break;
					}
				}

				// Die restlichen Zeilen in die keine Produkte mehr passen, werden mit
				// Leerzeilen gef�llt
				oeffneBestellungOB.ausgabeBlock(zeilenZurVerfuegungOB);
				zeilenZurVerfuegungOB = 27;
				oeffneBestellungOB.ausgabeZeile();
				zurueckNichtMoeglichOB = false;
			}
			if(MITABOBEZAHLT) {
				oeffneBestellungOB.ausgabeZeileL("Gesamtpreis:  " + ShopRechner.getGesamtpreisString(new Abo().getRabatt(), INHALT));
			}else {
				oeffneBestellungOB.ausgabeZeileL("Gesamtpreis:  " + ShopRechner.getGesamtpreisString(INHALT));
			}
			
			oeffneBestellungOB.trennStrich();
			oeffneBestellungOB.ausgabeZeile("Auswahlm�glichkeiten");
			oeffneBestellungOB.trennStrich();
			// Gibt die Auswahlm�glichkeiten auf dem Fenster aus.
			for (int indexW = 0; indexW < OPTIONSLEISTEN_BESTELLUNGEN.length; indexW += 2) {
				oeffneBestellungOB.ausgabeZeileL(OPTIONSLEISTEN_BESTELLUNGEN[indexW],
						OPTIONSLEISTEN_BESTELLUNGEN[indexW + 1]);
			}
			oeffneBestellungOB.trennStrich();
			oeffneBestellungOB.infoLeiste(Shop.getWiederholung(), benachrichtigungOB,
					Shop.getShopKundenverwaltung().isEingeloggt());
			oeffneBestellungOB.eingabeZeile();
			usrInputOB = nutzerEingabeOB.getUsrInput(1);
			oeffneBestellungOB.setzeNull();

			// Navigation
			// Zur�ck zum Katalog
			if (usrInputOB == 2) {
				break;

			} // Vorw�rts blaettern
			else if (usrInputOB == 8) {
				if (indexOB < INHALT.length) {
					durchlaufDavorOB = durchlaufOB;
				} else {
					benachrichtigungOB = Shop.getBenachrichtigung(1);
					Shop.setWiederholung(true);
					indexOB -= durchlaufOB;
				}
			} // Zur�ck blaettern
			else if (usrInputOB == 9) {
				/*
				 * Es werden die ersten Produkte nochmal ausgegeben. Hier muss leider mit einem
				 * extra Boolean gearbeitet werden, welcher anzeigt, ob noch weitere Elemente
				 * vor dem aktuellen Index angezeigt werden k�nnen. Ist der Warenkorb leer ist
				 * das nie der Fall. Deshalb wird dann dieser Boolean auf true gesetzt. Da hier
				 * speziell mit dem Index 0 verglichen wird, anstatt mit curArtNum also den
				 * Artikelnummern der Produkte, funktoniert die Version von blaetternKatalog
				 * hier nicht und der Boolean muss aushelfen.
				 */
				if (zurueckNichtMoeglichOB || indexOB - durchlaufOB * 2 < 0) {
					indexOB -= durchlaufOB;
					// Benachrichtigung des Arrays Design.BENACHRICHTIGUNG.
					// Index 2 ist die "Anfang der Seite erreicht" Benachrichtigung.
					benachrichtigungOB = Shop.getBenachrichtigung(2);
					Shop.setWiederholung(true);
					// Bei der n�chsten Wiederholung werden die Selben Produkte angezeigt wie vor
					// dem durchlauf.
				} else if ((indexOB - durchlaufOB * 2 >= 0) && (durchlaufOB == durchlaufDavorOB)) {
					// Frage, welches Produkt in den Warenkorb gelegt werden soll.
					indexOB -= durchlaufOB * 2;
				} else if (((indexOB - durchlaufOB * 2) >= 0) && (durchlaufOB < durchlaufDavorOB)) {
					indexOB -= (durchlaufDavorOB + durchlaufOB);
					// Frage, welches Produkt in den Warenkorb gelegt werden soll.
				}
			}
			// falscher Input
			else {
				// Falls usrInput eine andere Zahl als m�glich oder ung�ltig ist,
				// gibt getUsrInput(...) Integer.MAX_VALUE zur�ck (siehe Shop.getUsrInput). Ist
				// dies der Fall,
				// wird der else Zweig durchlaufen.
				benachrichtigungOB = Shop.getBenachrichtigung(0);
				indexOB -= durchlaufOB;
				durchlaufDavorOB = durchlaufOB;
			}
			durchlaufOB = 0;
		}
	}
	
	public Produkt[] getINHALT() {
		return INHALT;
	}

	
}
