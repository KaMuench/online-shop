/**
 * Der Warenkorb, in den die Produkte gelegt werden k�nnen, die man sp�ter kaufen m�chte.
 * 
 * @author Kaleb Muench
 */
package com.muench.kaleb.onlineshop.shop;

import java.util.ArrayList;
import java.util.Collections;

import com.muench.kaleb.onlineshop.ui.design.*;
import com.muench.kaleb.onlineshop.shop.kundenverwaltung.BenutzerEingabe;
import com.muench.kaleb.onlineshop.entities.produkt.Produkt;

public class Warenkorb {
	/*
	 * Der Warenkorb in den Produkte gelegt werden k�nnen, die man kaufen m�chte. Da
	 * im Warenkorb auch unterschiedliche Produkte in einem Fenster ausgegeben
	 * werden m�ssen, eignet sich hierf�r eine ArrayList eher.
	 */
	private ArrayList<Produkt> warenkorb = new ArrayList<>();

	private static final BenutzerEingabe nutzerEingabeW = new BenutzerEingabe();

	private static final String[] OPTIONSLEISTEN_WARENKORB = { "Hinzuf�gen:",
			"Um einen Artikel in Ihren Warenkorb abzulegen, geben Sie dessen Artikelnummer ein.", "Entfernen",
			"Um eine Artikel aus Ihren Warenkorb zu entfernen, geben Sie dessen Artikelnummer mit - Zeichen ein.",
			"2. Zur�ck:", "Um den Warenkorb zu verlassen, geben Sie die 2 ein.", "8. N�chste Seite:",
			"Um zur n�chsten Seite zu bl�ttern, geben Sie die 8 ein.", "9. Vorherige Seite:",
			"Um zur vorherigen Seite zubl�ttern, geben Sie 9 ein. " };
	private static final String[] BENACHRICHTIGUNGEN_WARENKORB = {
			"Welches Produkt m�chtest du gerne in den Warenkorb legen?", "Produkt existiert nicht!",
			"Produkt in Warenkorb hinzugef�gt!", "Produkt aus Warenkorb entfernt!", "Produkt nicht auf Lager!",
			"Produkt kann nicht entfernt werden. Es ist nicht im Warenkorb!" };

	/**
	 * ### oeffne ###
	 * 
	 * Diese Methode soll es den Kunden erm�glichen, Produkte in seinen Warenkorb
	 * abzulegen. Diese Produkte k�nnen dann sp�ter an der Kasse gekauft werden.
	 */
	public void oeffneWarenkorb() {
		Design oeffneWarenkorb = new Design();
		/*
		 * Die Anzahl der Produkte die in der aktuelle Runde ausgegeben wurde Je nachdem
		 * wie viele Zeilen es braucht um ein Produkt auszugeben, k�nnen, mehrere
		 * Produkte pro Schleifen Durchlauf ausgegeben werden. Wurden z.B. 3 Produkte
		 * pro Seite ausgegeben ist durchlauf = 3.
		 */
		byte indexWarenkorb = 0;

		byte durchlaufW = 0;
		// Die Anzahl der Produkte die letzte Runde ausgegeben wurde.
		byte durchlaufDavorW = 0;

		// Input des Users, zu Navigation im Warenkorb
		int usrInputW = 0;

		String benachrichtigungW = BENACHRICHTIGUNGEN_WARENKORB[0];

		// Die Zeilen die pro Produktausgabe genutzt wurden
		byte benutzteZeilenW = 0;

		// Die Zeilen die noch pro Fenster f�r weitere Ausgaben zu verf�gung stehen.
		// In diesem Layout sind es 25 Zeilen, die f�r die Ausgabe der Produkte �brig
		// sind.
		byte zeilenZurVerfuedurchlaufW = 25;

		boolean zurueckNichtMoeglichW = false;

		Shop.setWiederholung(true);

		while (true) {
			oeffneWarenkorb.ueberschrift("Warenkorb");
			oeffneWarenkorb.ausgabeZeile();

			if (warenkorb.isEmpty()) {
				oeffneWarenkorb.ausgabeZeile("Der Warenkorb ist leer.");
				oeffneWarenkorb.ausgabeBlock(zeilenZurVerfuedurchlaufW);
				zurueckNichtMoeglichW = true;

			} else {
				while (benutzteZeilenW <= zeilenZurVerfuedurchlaufW) {
					try {
						benutzteZeilenW = zeigeProdukt(warenkorb.get(indexWarenkorb), false);
						indexWarenkorb++;
						durchlaufW++;
						zeilenZurVerfuedurchlaufW -= benutzteZeilenW;
					} catch (IndexOutOfBoundsException e) {
						break;
					}
				}

				// Die restlichen Zeilen in die keine Produkte mehr passen, werden mit
				// Leerzeilen gef�llt
				oeffneWarenkorb.ausgabeBlock(zeilenZurVerfuedurchlaufW);
				zeilenZurVerfuedurchlaufW = 25;
				oeffneWarenkorb.ausgabeZeile();
				zurueckNichtMoeglichW = false;
			}
			oeffneWarenkorb.ausgabeZeileL("Gesamtpreis:  " + ShopRechner.getGesamtpreisString(getWarenkorbArray()));
			oeffneWarenkorb.trennStrich();
			oeffneWarenkorb.ausgabeZeile("Auswahlm�glichkeiten");
			oeffneWarenkorb.trennStrich();
			// Gibt die Auswahlm�glichkeiten auf dem Fenster aus.
			for (int indexW = 0; indexW < OPTIONSLEISTEN_WARENKORB.length; indexW += 2) {
				oeffneWarenkorb.ausgabeZeileL(OPTIONSLEISTEN_WARENKORB[indexW], OPTIONSLEISTEN_WARENKORB[indexW + 1]);
			}
			oeffneWarenkorb.trennStrich();
			oeffneWarenkorb.infoLeiste(Shop.getWiederholung(), benachrichtigungW,
					Shop.getShopKundenverwaltung().isEingeloggt());
			oeffneWarenkorb.eingabeZeile();
			usrInputW = nutzerEingabeW.getUsrInput(1);
			oeffneWarenkorb.setzeNull();

			// Navigation
			// Zur�ck zum Katalog
			if (usrInputW == 2) {
				break;

			} // Vorw�rts blaettern
			else if (usrInputW == 8) {
				try {
					warenkorb.get(indexWarenkorb);
					durchlaufDavorW = durchlaufW;
					// Frage, welches Produkt in den Warenkorb gelegt werden soll.
					benachrichtigungW = BENACHRICHTIGUNGEN_WARENKORB[0];

				} catch (IndexOutOfBoundsException e) {
					// Benachrichtigung des Arrays Shop.BENACHRICHTIGUNG.
					// Index 1 ist die "Letzte Seite erreicht" Benachrichtigung.
					benachrichtigungW = Shop.getBenachrichtigung(1);
					indexWarenkorb -= durchlaufW;
				}
			} // Zur�ck blaettern
			else if (usrInputW == 9) {
				/*
				 * Es werden die ersten Produkte nochmal ausgegeben. Hier muss leider mit einem
				 * extra Boolean gearbeitet werden, welcher anzeigt, ob noch weitere Elemente
				 * vor dem aktuellen Index angezeigt werden k�nnen. Ist der Warenkorb leer ist
				 * das nie der Fall. Deshalb wird dann dieser Boolean auf true gesetzt. Da hier
				 * speziell mit dem Index 0 verglichen wird, anstatt mit curArtNum also den
				 * Artikelnummern der Produkte, funktoniert die Version von blaetternKatalog
				 * hier nicht und der Boolean muss aushelfen.
				 */
				if (zurueckNichtMoeglichW || indexWarenkorb - durchlaufW * 2 < 0) {
					indexWarenkorb -= durchlaufW;
					// Benachrichtigung des Arrays Shop.BENACHRICHTIGUNG.
					// Index 2 ist die "Anfang der Seite erreicht" Benachrichtigung.
					benachrichtigungW = Shop.getBenachrichtigung(2);
					// Bei der n�chsten Wiederholung werden die Selben Produkte angezeigt wie vor
					// dem durchlauf.
				} else if ((indexWarenkorb - durchlaufW * 2 >= 0) && (durchlaufW == durchlaufDavorW)) {
					// Frage, welches Produkt in den Warenkorb gelegt werden soll.
					benachrichtigungW = BENACHRICHTIGUNGEN_WARENKORB[0];
					indexWarenkorb -= durchlaufW * 2;
				} else if (((indexWarenkorb - durchlaufW * 2) >= 0) && (durchlaufW < durchlaufDavorW)) {
					indexWarenkorb -= (durchlaufDavorW + durchlaufW);
					// Frage, welches Produkt in den Warenkorb gelegt werden soll.
					benachrichtigungW = BENACHRICHTIGUNGEN_WARENKORB[0];
				}

			}
			// Produkt zu Warenkorb hinzuf�gen
			else if (usrInputW > 9 && !(usrInputW == Integer.MAX_VALUE)) {
				if (Shop.getShopKatalog().getKatalog().containsKey(usrInputW)) {
					Produkt p = Shop.getShopKatalog().getKatalog().get(usrInputW);
					if (p.checkAnzahl()) {
						Produkt retProdukt = p.macheKopie(true);
						hinzuZuWarenkorb(retProdukt);
						benachrichtigungW = BENACHRICHTIGUNGEN_WARENKORB[2];
					} else {
						// Produkt nicht auf Lager
						benachrichtigungW = BENACHRICHTIGUNGEN_WARENKORB[4];
					}
				} else {
					benachrichtigungW = BENACHRICHTIGUNGEN_WARENKORB[1];
				}
				indexWarenkorb -= durchlaufW;
				durchlaufDavorW = durchlaufW;
			}
			// Produkt aus Warenkorb entfernen
			else if (usrInputW < 0) {
				if (entfernenAusWarenkorb(usrInputW * -1)) {
					benachrichtigungW = BENACHRICHTIGUNGEN_WARENKORB[3];
				} else {
					benachrichtigungW = BENACHRICHTIGUNGEN_WARENKORB[5];
				}

				indexWarenkorb -= durchlaufW;
				durchlaufDavorW = durchlaufW;
			}

			// falscher Input
			else {
				// Falls usrInput eine andere Zahl als m�glich oder ung�ltig ist,
				// gibt getUsrInput(...) Integer.MAX_VALUE zur�ck (siehe
				// BenutzerEingabe.getUsrInput). Ist dies der Fall,
				// wird der else Zweig durchlaufen.
				benachrichtigungW = Shop.getBenachrichtigung(0);
				indexWarenkorb -= durchlaufW;
				durchlaufDavorW = durchlaufW;
			}
			durchlaufW = 0;
			if (!(usrInputW == 2)) {
				Shop.setWiederholung(true);
			}
		}

	}

	// Produkt in Warenkorb hinzuf�gen
	public void hinzuZuWarenkorb(Produkt p) {
		boolean match = false;
		if (!warenkorb.isEmpty()) {
			for (Produkt pr : warenkorb) {
				if (pr.equals(p)) {
					int curInd = warenkorb.indexOf(pr);
					int curAnz = warenkorb.get(curInd).getAnzahl();
					warenkorb.get(curInd).setAnzahl(curAnz + 1);
					match = true;
					break;
				}
			}
			if (!match) {
				warenkorb.add(p);
			}
		} else {
			warenkorb.add(p);
		}
		//Sortieren des Warenkorbs nach Produktnummern geordnet.
		Collections.sort(warenkorb);
	}

	/**
	 * Produkt aus Warenkorb entfernen
	 * 
	 * @param artNum Das Produkt was entfernt werden soll.
	 * @return boolean Wenn kein Produkt entfernt wurde, false.
	 */
	public boolean entfernenAusWarenkorb(int artNum) {
		int warenkorbSize = warenkorb.size();
		int notEquals = 0;
		if (!warenkorb.isEmpty()) {
			for (Produkt pr : warenkorb) {
				if (pr.getArtikelnummer() == artNum) {
					int curInd = warenkorb.indexOf(pr);
					int curAnz = warenkorb.get(curInd).getAnzahl();
					if (curAnz == 1) {
						warenkorb.remove(curInd);
						break;
					} else {
						warenkorb.get(curInd).setAnzahl(curAnz - 1);
						break;
					}
				} else {
					notEquals++;
				}
			}
			Collections.sort(warenkorb);
		} else {
			return false;
		}
		if (notEquals == warenkorbSize) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ### zeigeObjekt ###
	 * 
	 * Diese Methode ist daf�r da, Objekte als String auf der Konsole auszugeben.
	 * Diese Objekte werden im Design Format ausgegeben. Die Objekte m�ssen zuvor
	 * das Interface "AusgabenLayout" implementieren und die Methode
	 * "toDesignFormat" ebenfalls ausimplementieren.
	 * 
	 * @param produkt   Produkt dass das Interface AusgabeLayout implementiert.
	 * @param imKatalog Soll das Produkt im Katalog ausgegeben werden steht bei
	 *                  Anzahl "Auf lager", ansonsten "Anzahl"
	 * 
	 * @return byte Gibt die Anzahl der Zeilen zur�ck die genutzt wurden um das
	 *         Objekt auf der Konsole auszugeben. Dieser Return Wert kann genutzt
	 *         werden, um zu �berpr�fen ob ein neues Fenster begonnen werden muss
	 *         oder nicht.
	 */
	public static byte zeigeProdukt(AusgabenLayout produkt, boolean imKatalog) {
		Design window = new Design();
		// Splitet den String R�ckgabewert des objekts und f�llt ein Array mit den
		// einzelnen Strings.
		String[] array = produkt.toDesignFormat().split(";");
		// Falls das Produkt nicht im Katalog ausgegeben wird:
		if (!imKatalog) {
			array[array.length - 2] = "Anzahl";
		}

		// Gibt das Objekt als String in Zeilenlayout "ausgabeZuweisung" zur�ck
		for (int i = 0; i < array.length; i += 4) {
			if (!(i + 4 > (array.length))) {
				window.ausgabeZuweisung(array[i] + ":", array[i + 1], array[i + 2] + ":", array[i + 3]);
			} else {
				window.ausgabeZuweisung(array[i], array[i + 1], "", "");
			}
		}
		window.ausgabeZeile();
		window.ausgabeZeile();

		// Gibt die Anzahl der Zeilen zur�ck die genutzt wurden um das Objekt auf der
		// Konsole auszugeben.
		return window.getZeilenAnzahl();
	}

	// ### Getter und Setter ###

	public ArrayList<Produkt> getWarenkorb() {
		return warenkorb;
	}

	public void setWarenkorb(ArrayList<Produkt> warenkorb) {
		this.warenkorb = warenkorb;
	}
	
	public Produkt[] getWarenkorbArray() {
		Produkt[] retWarenkorb = new Produkt[warenkorb.size()];
		int i = 0;
		for(Produkt p: warenkorb) {
			retWarenkorb[i] = p;
			i++;
		}
		return retWarenkorb;
	}
}
