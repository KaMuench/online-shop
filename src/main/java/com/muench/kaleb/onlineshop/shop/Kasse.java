/**
 * Diese Klasse ist die Kasse des Online Shops.
 * 
 * @author Kaleb Muench
 */
package com.muench.kaleb.onlineshop.shop;

import com.muench.kaleb.onlineshop.ui.design.Design;
import com.muench.kaleb.onlineshop.entities.bestellung.Bestellungen;
import com.muench.kaleb.onlineshop.entities.kunden.Kunde;
import com.muench.kaleb.onlineshop.entities.zahlungsmethoden.Zahlungsmethode;
import com.muench.kaleb.onlineshop.shop.kundenverwaltung.BenutzerEingabe;
import com.muench.kaleb.onlineshop.io.OnlineShopDatenbank;

public class Kasse {

	/*
	 * Die Kasse greift auf die Selbe ArrayLit wie der Warenkorb zu. Es wird hier
	 * der �bersichtlichkeit wegen einfach eine neuer Referenz, auf den selben
	 * Warenkorb erstellt.
	 */
	private Warenkorb kasseWarenkorb;
	// Das selbe f�r den Kundenverwaltung().curKunde aktuell eingeloggten Kunden.
	private Kunde kasseKunde;

	private final String[][] OPTIONSLEISTEN_KASSE = {
			{ "0. Startmen�", "Um zum Startmen� zu gelangen, geben Sie die 0 ein.", "1. Kaufen",
					"Um den Kauf abzuschlie�en, geben Sie die 1 ein.", "2. Warenkorb bearbeiten",
					"Um den Warenkorb zu bearbeiten, geben Sie die 2 ein.", "8. N�chste Seite:",
					"Um zur n�chsten Seite zu bl�ttern, geben Sie die 8 ein.", "9. Vorherige Seite:",
					"Um zur vorherigen Seite zubl�ttern, geben Sie 9 ein. " },
			{ "1. Zahlungsmethode:", "Um die Zahlungsmethode 1 auszuw�hlen, geben Sie 1 ein", "2. Zahlungsmethode:",
					"Um die Zahlungsmethode 2 auszuw�hlen, geben Sie 2 ein.", "3. Zahlungsmethode:",
					"Um die Zahlungsmethode 3 auszuw�hlen, geben Sie 3 ein.", "4. Zahlungsmethode:",
					"Um die Zahlungsmethode 4 auszuw�hlen, geben Sie 4 ein.", "5. Zahlungsmethode:",
					"Um die Zahlungsmethode 5 auszuw�hlen, geben Sie 5 ein.", "9. Abrechen",
					"Um den Kauf abzubrechen, geben Sie 9 ein." } };
	private static final String[] BENACHRICHTIGUNG_KASSE = { "Ende der Seite erreicht!", "Anfang der Seite erreicht!",
			"Kauf abgeschlossen!", "Kauf abgebrochen",
			"Keine Zahlungsmethode hinterlegt! Bitte w�hlen Sie eine andere Zahlungsmethode!",
			"Der Warenkorb ist leer! Bitte legen Sie erst ein Produkt in den Warenkorb, um den Kauf abzuschlie�en!",
			"Sie haben noch keine Zahlungsmethode hinterlegt! F�gen Sie in ihren Accounteinstellungen eine hinzu!" };
	private static final BenutzerEingabe nutzerEingabeK = new BenutzerEingabe();

	// ### Konstruktor ###
	public Kasse() {
		this.kasseWarenkorb = Shop.getShopWarenkorb();
		this.kasseKunde = Shop.getShopKundenverwaltung().getCurKunde();
	}

	/**
	 * Gibt die Kasse aus.
	 * 
	 * Die Methode ist �hnlich der oeffWarenkorb().
	 * 
	 * @return String Gibt eine Benachrichtigung zur�ck an die Methode in der
	 *         oeffKasse aufgerufen wurde.
	 */
	public String oeffneKasse() {
		Design oeffneKasse = new Design();

		// Index des aktuell auszugebenden Produkts in der Warenkorb ArrayList.
		byte indexKasse = 0;
		// Anzahl der Produkte die Ausgegeben wurde.
		byte durchlaufKasse = 0;
		// Anzahl der Produkt die letzte Runde ausgegeben wurde.
		byte durchlaufDavorKasse = 0;
		int usrInputKasse = 0;
		// Zeilenanzahl die f�r die Ausgabe des Produkts gebraucht wurde.
		byte benutzteZeilenKasse = 0;
		// Zeilenanzahl die noch genutzt werden kann.
		byte zeilenZurVerfuegungKasse = 24;
		// Wenn nicht zur�ckgebl�ttert werden kann, true,
		boolean zurueckNichtMoeglichKasse = false;
		String benachrichtigungKasse = Shop.getBenachrichtigung(0);

		while (true) {
			oeffneKasse.ueberschrift("Kasse");
			oeffneKasse.ausgabeZeile();

			// Wenn der Warenkorb leer ist:
			if (kasseWarenkorb.getWarenkorb().isEmpty()) {
				oeffneKasse.ausgabeZeile("Die Kasse ist leer.");
				oeffneKasse.ausgabeBlock(zeilenZurVerfuegungKasse);
				zurueckNichtMoeglichKasse = true;
			}
			/*
			 * Wenn Produkte im Warenkorb sind, die ausgegeben werden k�nnen, wird die
			 * Methode zeigeProdukt aufgerufen, die das Produkt im passenden Format auf der
			 * Konsole ausgibt. Die Zeilen die daf�r ben�tigt werden, werden in der Variable
			 * benutzteZeilenKasse gespeichert. Nach jeder Ausgabe wird �berpr�ft ob genug
			 * Zeile f�r die Ausgabe eines weiteren Produktes zu verf�gung stehen. Ist dies
			 * der Fall, wird das n�chste Produkt ausgegeben, wenn nicht wird die Schleife
			 * gestoppt. Mit jeden Durchlauf wird durchlaufKasse hochgez�hlt.
			 */
			else {
				while (benutzteZeilenKasse <= zeilenZurVerfuegungKasse) {
					try {
						benutzteZeilenKasse = Warenkorb.zeigeProdukt(kasseWarenkorb.getWarenkorb().get(indexKasse),
								false);
						indexKasse++;
						durchlaufKasse++;
						zeilenZurVerfuegungKasse -= benutzteZeilenKasse;
					} catch (IndexOutOfBoundsException e) {
						break;
					}
				}

				// Die restlichen verf�gbaren Zeilen, werden mit Leerzeilen gef�llt.
				oeffneKasse.ausgabeBlock(zeilenZurVerfuegungKasse);
				// Die Zeilen die zu verf�gung stehen, werden wieder zur�ckgesetzt.
				zeilenZurVerfuegungKasse = 24;
				oeffneKasse.ausgabeZeile();
				// Es kann nun zur�ckgebl�ttert werden.
				zurueckNichtMoeglichKasse = false;
			}
			// Ausgabe des Gesamtpreises aller Produkte im Warenkorb
			oeffneKasse.ausgabeZeileL("Preis:", ShopRechner.getGesamtpreisString(kasseWarenkorb.getWarenkorbArray()));
			if (kasseKunde.getAbo() == null) {
				oeffneKasse.ausgabeZeileL("Mit Abbonement:", "Sie besitzen zurzeit kein Abbonement!");
			} else {
				oeffneKasse.ausgabeZeileL("Mit Abbonement:", ShopRechner
						.getGesamtpreisString(kasseKunde.getAbo().getRabatt(), kasseWarenkorb.getWarenkorbArray()));
			}

			oeffneKasse.trennStrich();
			oeffneKasse.ausgabeZeile("Auswahlm�glichkeiten");
			oeffneKasse.trennStrich();

			for (int indexK = 0; indexK < OPTIONSLEISTEN_KASSE[0].length; indexK += 2) {
				oeffneKasse.ausgabeZeileL(OPTIONSLEISTEN_KASSE[0][indexK], OPTIONSLEISTEN_KASSE[0][indexK + 1]);
			}
			oeffneKasse.trennStrich();
			oeffneKasse.infoLeiste(Shop.getWiederholung(), benachrichtigungKasse,
					Shop.getShopKundenverwaltung().isEingeloggt());
			oeffneKasse.eingabeZeile();
			usrInputKasse = nutzerEingabeK.getUsrInput(0);
			oeffneKasse.setzeNull();

			// ### Navigation ###
			// ### Zur�ck zum Katalog ###
			if (usrInputKasse == 0) {
				return "";
			}
			// ### Kaufen ###
			else if (usrInputKasse == 1) {
				while (true) {
					// Wenn der Kunde keine Zahlungsmethode hinterlegt hat, gibt es eine
					// Fehlerbenachrichtigung.
					if (kasseKunde.getZahlungsmethode()[0] == null) {
						benachrichtigungKasse = BENACHRICHTIGUNG_KASSE[6];
						indexKasse -= durchlaufKasse;
						durchlaufDavorKasse = durchlaufKasse;
						break;
					}
					// Genauso wenn der Warenkorb leer ist, also kein Produkt zum Kauf ausgew�hlt
					// wurde.
					else if (kasseWarenkorb.getWarenkorb().isEmpty()) {
						benachrichtigungKasse = BENACHRICHTIGUNG_KASSE[5];
						break;
					}
					// Ruft die Methode zum kauf abschlie�en aus.
					else {
						// Wurde der Kauf abgeschlossen return: Benachrichtigung
						if (kaufAbschliessen()) {
							Shop.setWiederholung(true);
							return benachrichtigungKasse = BENACHRICHTIGUNG_KASSE[2];
						}
						// Wurde der Kauf abgebrochen, wird die Kasse wieder ausgegeben.
						// Und eine Benachrichtigung zur�ckgegeben.
						else {
							indexKasse -= durchlaufKasse;
							durchlaufDavorKasse = durchlaufKasse;
							benachrichtigungKasse = BENACHRICHTIGUNG_KASSE[3];
							break;

						}

					}

				}
				Shop.setWiederholung(true);
			}
			// ### Warenkorb bearbeiten ###
			else if (usrInputKasse == 2) {
				kasseWarenkorb.oeffneWarenkorb();
				indexKasse -= durchlaufKasse;
				durchlaufDavorKasse = durchlaufKasse;
			}
			// ### Vorw�rts blaettern ###
			else if (usrInputKasse == 8) {
				try {
					kasseWarenkorb.getWarenkorb().get(indexKasse);
					durchlaufDavorKasse = durchlaufKasse;
				} catch (IndexOutOfBoundsException e) {
					benachrichtigungKasse = BENACHRICHTIGUNG_KASSE[0];
					Shop.setWiederholung(true);
					indexKasse -= durchlaufKasse;
				}
			}
			// ### Zur�ck blaettern ###
			else if (usrInputKasse == 9) {
				if (zurueckNichtMoeglichKasse || indexKasse - durchlaufKasse * 2 < 0) {
					indexKasse -= durchlaufKasse;
					benachrichtigungKasse = BENACHRICHTIGUNG_KASSE[1];
					Shop.setWiederholung(true);
				} else if ((indexKasse - durchlaufKasse * 2 >= 0) && (durchlaufKasse == durchlaufDavorKasse)) {
					indexKasse -= durchlaufKasse * 2;
				} else if ((indexKasse - durchlaufKasse * 2 >= 0) && (durchlaufKasse < durchlaufDavorKasse)) {
					indexKasse -= (durchlaufDavorKasse + durchlaufKasse);
				}
			}

			// ### falscher Input ###
			// Wird eine ung�ltige Benutzereingabe get�tigt
			// werden die zuletzt ausgegebenen Produkte erneut ausgegeben.
			else {
				benachrichtigungKasse = Shop.getBenachrichtigung(0);
				indexKasse -= durchlaufKasse;
				durchlaufDavorKasse = durchlaufKasse;
			}
			durchlaufKasse = 0;

		}

	}

	/**
	 * Methode in der der Kauf abgeschlossen wird. Zuerst wird muss der Benutzer
	 * eine Zahlungsmethode w�hlen. Wird der Kauf abgeshlossen, wird ein neues
	 * Objekt Bestellungen erstellt. Dieses wird dann der ArrayList: bestellVerlauf,
	 * des Kundenverwaltung().curKunden hizugef�gt.
	 * 
	 * @return Wurde der Kauf abgeschlossen return true. Wurde der Kauf abgebrochen
	 *         return false
	 */
	public boolean kaufAbschliessen() {
		Design kaufAbschliessen = new Design();
		String benachrichtigungKA = Shop.getBenachrichtigung(0);

		int userInputKA = 0;
		boolean beendenKA = false;

		while (true) {
			kaufAbschliessen.ueberschrift("Bezahlen");
			kaufAbschliessen.ausgabeZeile();
			// ### Abschluss des Kaufs ###
			if (beendenKA) {
				kaufAbschliessen.ausgabeZeile("Sie haben den Kauf erfolgreich abgeschlossen!");
				kaufAbschliessen.ausgabeZeile("Die Lieferung wird in den n�chsten Tagen erfolgen.");
				kaufAbschliessen.ausgabeZeile();
				kaufAbschliessen
						.ausgabeZeile("Sie haben bezahlt mit: " + kasseKunde.getZahlungsmethode()[userInputKA - 1]);
				kaufAbschliessen.ausgabeZeile();
				if (kasseKunde.getAbo() == null) {
					kaufAbschliessen.ausgabeZeile("Wir werden den Betrag von "
							+ ShopRechner.getGesamtpreisString(kasseWarenkorb.getWarenkorbArray())
							+ " in den n�chsten Tagen abbuchen.");
					// Bestellung anlegen
					Bestellungen bKA = new Bestellungen(kasseWarenkorb, false);
					kasseKunde.getBestellVerlauf().add(bKA);
				} else {
					kaufAbschliessen
							.ausgabeZeile(
									"Wir werden den Betrag von "
											+ ShopRechner.getGesamtpreisString(kasseKunde.getAbo().getRabatt(),
													kasseWarenkorb.getWarenkorbArray())
											+ " in den n�chsten Tagen abbuchen.");
					// Bestellung anlegen und dem Bestellverlauf des Kundenverwaltung().curKunde
					// �bergeben.
					Bestellungen bKA = new Bestellungen(kasseWarenkorb, true);
					kasseKunde.getBestellVerlauf().add(bKA);
				}
				kaufAbschliessen.ausgabeZeile();
				kaufAbschliessen.ausgabeZeile();
				kaufAbschliessen.ausgabeZeile("Wir hoffen Sie sind zufrieden mit Ihrem Kauf");
				// Da beim Threat.sleep die Ausgabe auf der Konsole nicht ganz akkurat ist,
				// werden hier weniger Zeilen ausgegeben als normalerweise
				kaufAbschliessen.ausgabeBlock(20);
				kaufAbschliessen.trennStrich();
				kaufAbschliessen.ausgabeZeile("Auswahlm�glichkeiten");
				kaufAbschliessen.trennStrich();
				kaufAbschliessen.ausgabeZeile();
				kaufAbschliessen.trennStrich();
				kaufAbschliessen.leerZeile();
				kaufAbschliessen.leerZeile();

				// Wurde beim Start des Programms "saves laden" ausgew�hlt,
				// wird hier die �nderung des Bestellverlaufs in saves gespeichert.
				if (Shop.isLadeSaves()) {
					OnlineShopDatenbank.speichernBestellungen(kasseKunde);
				}
				// Leeren des Warenkorbs
				kasseWarenkorb.getWarenkorb().clear();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				return true;

			}
			kaufAbschliessen.ausgabeZeile("Bitte w�hlen Sie Ihre Zahlungsmethode:");
			kaufAbschliessen.ausgabeZeile();
			for (Zahlungsmethode z : kasseKunde.getZahlungsmethode()) {
				if (z == null) {
					kaufAbschliessen.ausgabeZeileL("Frei");
				} else {
					kaufAbschliessen.ausgabeZuweisung(z.getName(), z.getZahlungsAdresse(), "", "");
				}
			}
			kaufAbschliessen.ausgabeBlock(18);
			kaufAbschliessen.ausgabeZeile();
			kaufAbschliessen.trennStrich();
			kaufAbschliessen.ausgabeZeile("Auswahlm�glichkeiten");
			kaufAbschliessen.trennStrich();
			for (int indexKa = 0; indexKa < OPTIONSLEISTEN_KASSE[1].length; indexKa += 2) {
				kaufAbschliessen.ausgabeZeileL(OPTIONSLEISTEN_KASSE[1][indexKa], OPTIONSLEISTEN_KASSE[1][indexKa + 1]);
			}
			kaufAbschliessen.trennStrich();
			kaufAbschliessen.infoLeiste(Shop.getWiederholung(), benachrichtigungKA, true);
			kaufAbschliessen.eingabeZeile();
			userInputKA = nutzerEingabeK.getUsrInput(0);
			kaufAbschliessen.setzeNull();

			// ### Navigation ###
			if (userInputKA > 0 && userInputKA < 6) {
				// Ist die Gew�hlte Zahlungsmethode null, wird eine Benachrichtigung ausgegeben.
				if (kasseKunde.getZahlungsmethode()[userInputKA - 1] == null) {
					benachrichtigungKA = BENACHRICHTIGUNG_KASSE[4];
					Shop.setWiederholung(true);
					continue;
				} else {
					beendenKA = true;
					continue;
				}
			}
			// ### Kauf abbrechen ###
			else if (userInputKA == 9) {
				return false;
			} else {
				Shop.setWiederholung(true);
				benachrichtigungKA = Shop.getBenachrichtigung(0);
			}
		}

	}

}
