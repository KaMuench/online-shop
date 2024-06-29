/**
 * In der Klasse Kundenverwaltung sind viele Methoden die den Kunden betreffen.
 * Es gibt Methoden um den Account zu bearbeiten, Bestellungen einzusehen, sich einzuloggen
 * oder zu regestrieren.
 * 
 * @author Kaleb Muench
 */

package com.muench.kaleb.onlineshop.shop.kundenverwaltung;

import java.util.ArrayList;
import java.util.TreeMap;

import com.muench.kaleb.onlineshop.entities.bestellung.Bestellungen;
import com.muench.kaleb.onlineshop.entities.zahlungsmethoden.*;
import com.muench.kaleb.onlineshop.entities.kunden.*;
import com.muench.kaleb.onlineshop.ui.design.Design;
import com.muench.kaleb.onlineshop.io.OnlineShopDatenbank;
import com.muench.kaleb.onlineshop.shop.Shop;


public class Kundenverwaltung {

	//Die TreeMap in der aller Kunden gespeichert sind.
	//Der Schl�ssel sind die Kundennummern
	private TreeMap<Integer, Kunde> kundenListe = new TreeMap<>();

	/*
	 * Der curKunde ist eine Referenz zu dem Kunden Objekt welches gerade im
	 * Shop eingeloggt ist.
	 * Wenn niemand eingeloggt ist, ist curKunde ein (leerer) Kunde.
	 */
	private Kunde curKunde = new Kunde();

	private BenutzerEingabe nutzerEingabeK = new BenutzerEingabe();

	/*
	 * Boolean der anzeigt ob man schon eingeloggt ist oder nicht. Wenn man nicht
	 * eingeloggt ist, kann man die Funktionen Kasse unde Accounteinstellungen nicht
	 * nutzen.
	 */
	private boolean eingeloggt = false;

	/*
	 * ### OPTIONSLEISTEN_KUNDENVERWALTUNG ###
	 * 
	 * In dieser String Matrix werden die Optionsleisten, des Auswahlbereichs, der
	 * Kundenverwaltung gespeichert.
	 */
	private static final String[][] OPTIONSLEISTEN_KUNDENVERWALTUNG = {
			{ "0. Startmen�:", "Um das Startmen� anzeigen zu lassen,geben Sie die 0 ein.", "1. Einloggen:",
					"Um sich einzuloggen, geben Sie die 1 ein.", "2. Registrieren:",
					"Um sich zu registrieren, geben Sie die 2 ein." },
			{ "Benutzername:", "Geben Sie zuerst ihren Benutzernamen ein.", "Passwort:",
					"Geben Sie danach ihr Passewort ein.", "9. Zur�ck:",
					"Um zum zur�ck zukehren, geben Sie die 9 ein." },
			{ "0. Startmen�:", "Um das Startmen� anzeigen zu lassen,geben Sie die 0 ein.", "1. Account bearbeiten:",
					"Um Ihr Profil zu bearbeiten, geben Sie die 1 ein.", "2. Abbo bearbeiten:",
					"Um Ihr Abbonement zu bearbeiten, geben Sie die 2 ein.", "3. Zahlungsmethoden bearbeiten:",
					"Um Ihre Zahlungsmethoden zu bearbeiten, geben Sie die 3. ein.", "4. Bestellverlauf einsehen:",
					"Um Ihren Bestellverlauf einzusehen, geben Sie die 4 ein.", "5. Rechnungen einsehen:",
					"Um Ihre Rechnungen einzusehen, geben Sie die 5 ein.", "6. Abmelden:",
					"Um sich abzumelden, geben Sie bitte die 6 ein.", "9. Zur�ck:",
					"Um zum zur�ck zukehren, geben Sie die 9 ein." },
			{ "�ffne Bestellung:",
					"Um eine Bestellung zu �ffnen, geben Sie dessen Nummer aus der obigen Liste mit - ein.",
					"2. Zur�ck:", "Um zu den Accounteinstellungen zur�ck zukehren, geben Sie die 2 ein.",
					"8 N�chste Seite:", "Um zur n�chsten Seite zu bl�ttern, geben Sie die 8 ein.", "9 Vorherige Seite:",
					"Um zur vorherigen Seite zu bl�ttern, geben Sie die 9 ein." },
			{ "Zahlungsmethode hinzuf�gen:",
					"Um eine Zahlungsmethode hinzuzuf�gen, geben Sie die Nummer eines freien Platzes ein.",
					"Zahlungsmethode entfernen", "Um eine Zahlungsmethode zu entfernen, geben Sie deren Nummer ein.",
					"9. Zur�ck:", "Um zu den Accounteinstellungen zur�ck zukehren, geben Sie die 9 ein." },
			{ "1. Abbonieren:", "Um das Jahresabbonement zu abbonieren, geben Sie die 1 ein.", "2. Deabbonieren:",
					"Um das Jahresabbonement zu deabbonieren, geben Sie die 2 ein.", "9. Zur�ckkehren:",
					"Um zur�ckzukehren, geben Sie die 9 ein." },
			{ "1. Zahlungsmethode:", "Um die Zahlungsmethode 1 auszuw�hlen, geben Sie 1 ein", "2. Zahlungsmethode:",
					"Um die Zahlungsmethode 2 auszuw�hlen, geben Sie 2 ein.", "3. Zahlungsmethode:",
					"Um die Zahlungsmethode 3 auszuw�hlen, geben Sie 3 ein.", "4. Zahlungsmethode:",
					"Um die Zahlungsmethode 4 auszuw�hlen, geben Sie 4 ein.", "5. Zahlungsmethode:",
					"Um die Zahlungsmethode 5 auszuw�hlen, geben Sie 5 ein.", "9. Abrechen",
					"Um den Kauf abzubrechen, geben Sie 9 ein." } };

	//Hier werden die Benachrichtigungen der Kundenverwaltung gespeichert.
	//Diese k�nnen in der Design().infoLeiste ausgegeben werden.
	private static final String[] BENACHRICHTIGUNG_KUNDENVERWALTUNG = { "Sie sind bereits eingeloggt!",
			"Es existiert kein Benutzer mit diesen Benutzernamen! Bitte versuchen Sie es erneut!",
			"Das Passwort ist falsch! Bitte versuchen Sie es erneut.", "Erfolgreich eingeloggt.",
			" ist ein ung�ltiges Zeichen!", "Erfolgreich regestriert!", "Keine Bestellung f�r " };

	//Konstruktor
	public Kundenverwaltung() {

	}

	/**
	 * ### abfragenLogReg ###
	 * Das Fenster was sich �ffnet wenn man auf die Kasse oder die Accounteinstellungen zugreifen m�chte,
	 * aber noch nicht eingeloggt ist.
	 * Aufforderung des Benutzers sich einzuloggen oder zu regestrieren.
	 * 
	 * @param ort Welches Fenster nach dem Einloggen angezeigt wird. Damit der
	 *            angezeigte Satz grammatikalisch korrekt ist, muss der String zur
	 *            folgender Vorlage passen: "Um zu " + ort + " zu gelangen ..."
	 */
	public int abfrageLogReg(String ort) {
		Design abfrageLogReg = new Design();
		int usrInputLR = 0;
		String benachrichtigungLR = Shop.getBenachrichtigung(0);

		while (true) {

			abfrageLogReg.ueberschrift("Einloggen - Registrieren");
			abfrageLogReg.ausgabeZeile();
			abfrageLogReg.ausgabeZeileL("Um zu " + ort + " zu gelangen, m�ssen Sie sich erst einloggen.");
			abfrageLogReg.ausgabeZeileL(
					"Wenn sie bereits einen Account besitzen, k�nnen Sie gleich weiter zum Loggin-Fenster.");
			abfrageLogReg.ausgabeZeileL("Falls nicht m�ssen Sie sich erst registrieren.");
			abfrageLogReg.ausgabeBlock(25);
			abfrageLogReg.ausgabeZeile();
			abfrageLogReg.trennStrich();
			abfrageLogReg.ausgabeZeile("Auswahlm�glichkeiten");
			abfrageLogReg.trennStrich();
			for (int indexLR1 = 0,
					indexLR2 = 0; indexLR2 < OPTIONSLEISTEN_KUNDENVERWALTUNG[indexLR1].length; indexLR2 += 2) {
				abfrageLogReg.ausgabeZeileL(OPTIONSLEISTEN_KUNDENVERWALTUNG[indexLR1][indexLR2],
						OPTIONSLEISTEN_KUNDENVERWALTUNG[indexLR1][indexLR2 + 1]);
			}
			abfrageLogReg.trennStrich();
			abfrageLogReg.infoLeiste(Shop.getWiederholung(), benachrichtigungLR, isEingeloggt());
			abfrageLogReg.eingabeZeile();
			usrInputLR = nutzerEingabeK.getUsrInput(0);
			abfrageLogReg.setzeNull();

			// Zur�ck zum Startmen�
			if (usrInputLR == 0) {
				return usrInputLR;
			}
			// Zum Loggin-Fenster
			if (usrInputLR == 1) {
				loggin();
				if (isEingeloggt()) {
					// Es wird einfach irgendein Wert zur�cggegeben.
					return Integer.MIN_VALUE;
				} else {
					continue;
				}
			}
			// Zum Regestrierungs-Fenster
			else if (usrInputLR == 2) {
				if (AccountEditor.bearbeiteAccount("Registrieung")) {
					if(Shop.isLadeSaves()) {
						OnlineShopDatenbank.speichernKundenliste(this);	
					}

					Shop.setWiederholung(true);
					loggin();
					if (isEingeloggt()) {
						// Es wird einfach irgendein Wert zur�cggegeben.
						return Integer.MIN_VALUE;
					} else {
						continue;
					}
				} else {
					continue;
				}
			}
			// Wiederholung des aktuellen Fensters mit Fehlermeldung.
			else {
				Shop.setWiederholung(true);
			}
		}

	}

	/**
	 * ### loggin ###
	 * 
	 * Diese Methode ist zum Einloggen da. Der Benutzer muss zuerst seinen
	 * Benutzernamen und dann sein Passwort eingeben. Gibt es einen Kunden mit
	 * diesen Benutzernamen und Passwort, wird der Benutzer als dieser Kunde
	 * eingeloggt. Also curKunde ist dann der Account des aktuellen Users.
	 */
	public void loggin() {
		Design loggin = new Design();

		int indexL = Kunde.getKennnummerbereich() + 1;
		byte standL = 0;
		String benachrichtigungL = BENACHRICHTIGUNG_KUNDENVERWALTUNG[5];
		int usrInputL = 0;

		while (true) {
			loggin.ueberschrift("Loggin");
			loggin.ausgabeZeile();
			loggin.ausgabeZeile("Bitte geben Sie Ihre Loggin-Daten ein.");
			loggin.ausgabeZeile();
			loggin.ausgabeZeile();
			// Gibt den eingegebenen Benutzernamen und Passwort aus.
			if (standL == 0) {
				loggin.ausgabeZeileL("Benutzername:      ");
				loggin.ausgabeZeileL("Passwort:          ");
			} else if (standL == 1) {
				loggin.ausgabeZeileL("Benutzername:      " + curKunde.getBenutzerName());
				loggin.ausgabeZeileL("Passwort:          ");
			} else if (standL == 2) {
				loggin.ausgabeZeileL("Benutzername:      " + curKunde.getBenutzerName());
				loggin.ausgabeZeileL("Passwort:          " + curKunde.getPasswort());
				// Da beim Threat.sleep die Ausgabe auf der Konsole nicht ganz akkurat ist,
				// werden hier weniger Zeilen ausgegeben als normalerweise
				loggin.ausgabeBlock(23);

				loggin.ausgabeZeile();
				loggin.trennStrich();
				loggin.ausgabeZeile("Auswahlm�glichkeiten");
				loggin.trennStrich();
				loggin.ausgabeZeile();
				loggin.trennStrich();
				loggin.infoLeiste(Shop.getWiederholung(), benachrichtigungL, eingeloggt);
				loggin.leerZeile();
				Shop.setWiederholung(false);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {

				}
				break;
			}
			loggin.ausgabeBlock(17);
			loggin.ausgabeZeile();
			loggin.ausgabeZeileL("                           ACHTUNG!!");
			loggin.ausgabeZeileL("Nicht alle Eingaben sind zul�ssig als Passwort oder Benutzernamen.");
			loggin.ausgabeZeileL("Nicht erlaubt sind:   ");
			loggin.ausgabeZeileL("Einzelne Zahlen von 0 bis 9. Diese sind nur zur Navigation im Shop bestimmt.");
			loggin.ausgabeZeileL(
					"Auch Sonderzeichen []\"!?\\/#'*+.,~{}()%&$�|<>�`'=^�@�;:_ und Leerzeichen sind nicht erlaubt.");
			loggin.ausgabeZeile();
			loggin.trennStrich();
			loggin.ausgabeZeile("Auswahlm�glichkeiten");
			loggin.trennStrich();
			// Gibt die Auswahlm�glichkeiten auf dem Fenster aus.
			for (int indexL1 = 1,
					indexL2 = 0; indexL2 < OPTIONSLEISTEN_KUNDENVERWALTUNG[indexL1].length; indexL2 += 2) {
				loggin.ausgabeZeileL(OPTIONSLEISTEN_KUNDENVERWALTUNG[indexL1][indexL2],
						OPTIONSLEISTEN_KUNDENVERWALTUNG[indexL1][indexL2 + 1]);
			}
			loggin.trennStrich();
			loggin.infoLeiste(Shop.getWiederholung(), benachrichtigungL, eingeloggt);
			loggin.eingabeZeile();
			usrInputL = nutzerEingabeK.getUsrInput(2);
			loggin.setzeNull();

			// Zur�ck zum Loggin/Regestrierungsmen�
			if (usrInputL == 9) {
				curKunde = new Kunde();
				break;
			}
			// Wenn getUsrInput Integer.MIN_VALUE zur�ckgibt, werden im ersten Durchlauf
			// standL == 0
			// alle Benutzernamen der Kunden in der kundenListe mit messageString
			// verglichen.
			// Bei Erfolg werden im zweiten Durchlauf standL = 1 die Passw�rter verglichen.
			else if (usrInputL == Integer.MIN_VALUE) {

				// Der Benutzername wird �berpr�ft
				if (standL == 0) {
					boolean matchL = false;
					// Die kundenListe wird durchsucht, ob sie einen Kunden mit gleichen
					// Benutzernamen,
					// wie der String der Benutzereingabe beinhaltet.
					while (kundenListe.containsKey(indexL)) {
						if (kundenListe.get(indexL).getBenutzerName().equals(nutzerEingabeK.getMessageString())) {
							curKunde.setBenutzerName(nutzerEingabeK.getMessageString());
							standL++;
							matchL = true;
							break;
						} else {
							indexL++;
						}
					}

					// Wurde keine �bereinstimmung bei dem Benutzernamen gefunden, wird eine
					// Fehlermeldung zur�ckgegeben
					// und
					// das selbe Fenster wird erneut ausgegeben.
					if (!matchL) {
						benachrichtigungL = BENACHRICHTIGUNG_KUNDENVERWALTUNG[1];
						Shop.setWiederholung(true);
						indexL = 1001;

					}

				}
				// Das Passwort wird �berpr�ft
				else if (standL == 1) {
					if (kundenListe.get(indexL).getPasswort().equals(nutzerEingabeK.getMessageString())) {
						curKunde = kundenListe.get(indexL);
						setEingeloggt(true);
						standL++;
						benachrichtigungL = BENACHRICHTIGUNG_KUNDENVERWALTUNG[3];
						Shop.setWiederholung(true);
						continue;
					} else {
						benachrichtigungL = BENACHRICHTIGUNG_KUNDENVERWALTUNG[2];
						Shop.setWiederholung(true);
					}
				}

			}
			// Wenn die Benutzereingabe irgendetwas anderes ist, wird eine Fehlermeldung
			// zur�ckgegeben
			// und die Schleife erneut durchlaufen
			else if (usrInputL == Integer.MAX_VALUE) {
				Shop.setWiederholung(true);
				benachrichtigungL = nutzerEingabeK.getMessageString() + BENACHRICHTIGUNG_KUNDENVERWALTUNG[4];

			} else {
				Shop.setWiederholung(true);
				benachrichtigungL = usrInputL + BENACHRICHTIGUNG_KUNDENVERWALTUNG[4];
			}

		}
	}

	/**
	 * ### oeffne Account Einstellungen ##
	 * 
	 * Diese Methode fragt ab ob der Benutzer sich einloggen oder regestrieren
	 * m�chte.
	 * 
	 * @return String die Benachrichtigung in der Infoleiste, die im Shopmen�
	 *         ausgegeben werden soll.
	 */
	public String oeffAccEinst() {
		Design oeffAccEinst = new Design();

		int usrInputAE = 0;
		String benachrichtigungAE = Shop.getBenachrichtigung(0);

		while (true) {
			oeffAccEinst.ueberschrift("Accounteinstellungen");
			oeffAccEinst.ausgabeZeile();
			oeffAccEinst.ausgabeZeile("Angemeldet als " + curKunde.getBenutzerName());
			oeffAccEinst.ausgabeZeile();
			oeffAccEinst.ausgabeZeile();
			byte verbrauchteZeilen = curKunde.zeigeKunde();
			oeffAccEinst.ausgabeBlock(20 - verbrauchteZeilen);
			oeffAccEinst.ausgabeZeile();
			oeffAccEinst.trennStrich();
			oeffAccEinst.ausgabeZeile("Auswahlm�glichkeiten");
			oeffAccEinst.trennStrich();
			// Gibt die Auswahlm�glichkeiten auf dem Fenster aus.
			for (int indexL1 = 2,
					indexL2 = 0; indexL2 < OPTIONSLEISTEN_KUNDENVERWALTUNG[indexL1].length; indexL2 += 2) {
				oeffAccEinst.ausgabeZeileL(OPTIONSLEISTEN_KUNDENVERWALTUNG[indexL1][indexL2],
						OPTIONSLEISTEN_KUNDENVERWALTUNG[indexL1][indexL2 + 1]);
			}
			oeffAccEinst.trennStrich();
			oeffAccEinst.infoLeiste(Shop.getWiederholung(), benachrichtigungAE, eingeloggt);
			oeffAccEinst.eingabeZeile();
			usrInputAE = nutzerEingabeK.getUsrInput(0);
			oeffAccEinst.setzeNull();

			if (usrInputAE == 0 || usrInputAE == 9) {
				return "";
			}
			// Account bearbeiten
			else if (usrInputAE == 1) {
				AccountEditor.bearbeiteAccount("Account bearbeiten");
				if(Shop.isLadeSaves()) {
					OnlineShopDatenbank.speichernKundenliste(this);
				}
				continue;
			}
			// Abbonement verwalten
			else if (usrInputAE == 2) {
				oeffAbo();
				continue;
			}
			// Zahlungsmethoden-Men�
			else if (usrInputAE == 3) {
				oeffZahlungsM();
				continue;
			}

			// Bestellungenmen�
			else if (usrInputAE == 4) {
				oeffBestellMenue();
				continue;
			}

			else if (usrInputAE == 6) {
				curKunde = new Kunde();
				Shop.setWiederholung(true);
				setEingeloggt(false);
				return "Erfolgreich ausgeloggt";

			}
		}
	}

	// ### bearbZahlungsM ###
	public void oeffZahlungsM() {
		Design oeffZahlungsM = new Design();

		int usrInputOZ = 0;
		String benachrichtigungOF = "";
		byte entfernen = 0;

		Shop.setWiederholung(true);
		while (true) {
			oeffZahlungsM.ueberschrift("Zahlungsmethoden");
			oeffZahlungsM.ausgabeZeile();
			oeffZahlungsM.ausgabeZeileL("Hier k�nnen Sie Zahlungsmethoden hinzuf�gen oder entfernen.");
			oeffZahlungsM.ausgabeZeileL(
					"Um einen Kauf zu t�tigen, m�ssen Sie mind. eine Zahlungsmethode in Ihrem Account gespeichert haben.");
			oeffZahlungsM.ausgabeZeile();
			oeffZahlungsM.ausgabeZeile();
			oeffZahlungsM.ausgabeZeileL("Ihre Zahlungsmethoden:");
			oeffZahlungsM.ausgabeZeile();
			String[] arrayZahlungsOF = curKunde.toDesignFormat().split(";");
			for (int i = 0, e = 1; i < 10; i += 2, e++) {
				oeffZahlungsM.ausgabeZeileL(e + ". Zahlungsmethode: " + arrayZahlungsOF[i + 15],
						arrayZahlungsOF[i + 16]);
			}
			oeffZahlungsM.ausgabeBlock(17);
			oeffZahlungsM.ausgabeZeile();
			oeffZahlungsM.trennStrich();
			oeffZahlungsM.ausgabeZeile("Auswahlm�glichkeiten");
			oeffZahlungsM.trennStrich();
			// Gibt die Auswahlm�glichkeiten auf dem Fenster aus.
			for (int indexL1 = 4,
					indexL2 = 0; indexL2 < OPTIONSLEISTEN_KUNDENVERWALTUNG[indexL1].length; indexL2 += 2) {
				oeffZahlungsM.ausgabeZeileL(OPTIONSLEISTEN_KUNDENVERWALTUNG[indexL1][indexL2],
						OPTIONSLEISTEN_KUNDENVERWALTUNG[indexL1][indexL2 + 1]);
			}

			oeffZahlungsM.trennStrich();
			oeffZahlungsM.infoLeiste(Shop.getWiederholung(), benachrichtigungOF, true);
			oeffZahlungsM.eingabeZeile();
			usrInputOZ = nutzerEingabeK.getUsrInput(2);
			oeffZahlungsM.setzeNull();

			// Navigation
			// Speicher oder nicht
			if (entfernen != 0) {
				if (nutzerEingabeK.getMessageString().equals("Y") && usrInputOZ == Integer.MIN_VALUE) {
					curKunde.setZahlungsmethode(null, (byte) (entfernen - 1));
					curKunde.sortierenZahlungsM();

					// Speichern der Zahlungsmethoden des curKunde als Textdatei
					if(Shop.isLadeSaves()) {
						OnlineShopDatenbank.speichernZahlungsmethoden(curKunde);					
					}


					benachrichtigungOF = "Zahlungsmethode " + entfernen + " "
							+ curKunde.getZahlungsmethode()[entfernen - 1] + " erfolgreich entfernt!";
					entfernen = 0;
				} else if (nutzerEingabeK.getMessageString().equals("N") && usrInputOZ == Integer.MIN_VALUE) {
					benachrichtigungOF = "";
					entfernen = 0;
				} else {
					benachrichtigungOF = "Ung�ltige Antwort! Bitte antworten Sie mit Y oder N!";
				}
				Shop.setWiederholung(true);
				continue;
			}

			// Zahlungsmethode bearbeiten
			if (usrInputOZ > 0 && usrInputOZ < 6) {
				if (curKunde.getZahlungsmethode()[usrInputOZ - 1] == null) {
					curKunde.setZahlungsmethode(Zahlungsmethode.bearbZahlungsM((byte) usrInputOZ),
							(byte) (usrInputOZ - 1));
					if (curKunde.getZahlungsmethode()[usrInputOZ - 1] == null) {
						benachrichtigungOF = "Bearbeitung abgebrochen!";
					} else {
						benachrichtigungOF = "Zahlungsmethode hinzugef�gt!";
					}
					Shop.setWiederholung(true);
					curKunde.sortierenZahlungsM();

					// Speichern der Zahlungsmethoden des curKunde als Textdatei
					OnlineShopDatenbank.speichernZahlungsmethoden(curKunde);

				} else {
					Shop.setWiederholung(true);
					benachrichtigungOF = "Wollen Sie wirklich Zahlungsmethode " + usrInputOZ + " "
							+ curKunde.getZahlungsmethode()[usrInputOZ - 1] + " entfernen? Y|N";
					entfernen = (byte) usrInputOZ;
				}
			}
			// Zur�ckkehren
			else if (usrInputOZ == 9) {
				break;
			}
			// Falscher Input
			else {
				Shop.setWiederholung(true);
				benachrichtigungOF = Shop.getBenachrichtigung(0);
			}

		}

	}

	/**
	 * ### oeffBestellMenue() ###
	 * 
	 * Die Methode die das Bestellungen-Men� ausgibt. So kann den Kunde seine
	 * gemachten Bestellungen durchsehen.
	 */
	public void oeffBestellMenue() {
		Design oeffBestellMenue = new Design();
		BenutzerEingabe nutzerEingabeBM = new BenutzerEingabe();

		boolean zurueckNichtMoeglichBM = false;
		byte indexBM = 0;
		int usrInputBM = 0;
		int ausgegebenBM = 0;
		int ausgegebenDavorBM = 0;
		String benachrichtigungBM = Shop.getBenachrichtigung(0);
		ArrayList<Bestellungen> arrayListBM = curKunde.getBestellVerlauf();

		while (true) {
			oeffBestellMenue.ueberschrift("Bestellungen-Men�");
			oeffBestellMenue.ausgabeZeile();

			if (!arrayListBM.isEmpty()) {
				for (; ausgegebenBM <= 20; ++ausgegebenBM, indexBM++) {
					if (indexBM < arrayListBM.size()) {
						oeffBestellMenue.ausgabeZeileL((indexBM + 1) + ". Bestellung vom " + arrayListBM.get(indexBM));
					} else {
						break;
					}
				}
				zurueckNichtMoeglichBM = false;
			} else {
				oeffBestellMenue.ausgabeZeile("Diese Seite ist leer!");
				zurueckNichtMoeglichBM = true;
			}

			oeffBestellMenue.ausgabeBlock(26 - ausgegebenBM);
			oeffBestellMenue.ausgabeZeile();
			oeffBestellMenue.trennStrich();
			oeffBestellMenue.ausgabeZeile("Auswahlm�glichkeiten");
			oeffBestellMenue.trennStrich();
			for (int index = 0; index < OPTIONSLEISTEN_KUNDENVERWALTUNG[3].length; index += 2) {
				oeffBestellMenue.ausgabeZeileL(OPTIONSLEISTEN_KUNDENVERWALTUNG[3][index],
						OPTIONSLEISTEN_KUNDENVERWALTUNG[3][index + 1]);
			}
			oeffBestellMenue.trennStrich();
			oeffBestellMenue.infoLeiste(Shop.getWiederholung(), benachrichtigungBM, true);
			oeffBestellMenue.eingabeZeile();
			usrInputBM = nutzerEingabeBM.getUsrInput(1);
			oeffBestellMenue.setzeNull();
			if (usrInputBM < 0) {
				try {
					arrayListBM.get((usrInputBM * -1) - 1).oeffneBestellung();

				} catch (IndexOutOfBoundsException e) {
					Shop.setWiederholung(true);
					benachrichtigungBM = BENACHRICHTIGUNG_KUNDENVERWALTUNG[6] + usrInputBM * 1;
				}
				indexBM -= ausgegebenBM;
				ausgegebenDavorBM = ausgegebenBM;
			} else if (usrInputBM == 2) {
				break;
			}
			// Vorw�rts bl�ttern
			else if (usrInputBM == 8) {

				try {
					arrayListBM.get(indexBM);
					ausgegebenDavorBM = ausgegebenBM;
				} catch (IndexOutOfBoundsException e) {
					Shop.setWiederholung(true);
					benachrichtigungBM = Shop.getBenachrichtigung(1);
					indexBM -= ausgegebenBM;
				}
			}
			// Zur�ck bl�ttern
			else if (usrInputBM == 9) {
				if (zurueckNichtMoeglichBM || indexBM - ausgegebenBM * 2 < 0) {
					indexBM -= ausgegebenBM;
					// Benachrichtigung des Arrays Design.BENACHRICHTIGUNG.
					// Index 2 ist die "Anfang der Seite erreicht" Benachrichtigung.
					benachrichtigungBM = Shop.getBenachrichtigung(2);
					Shop.setWiederholung(true);
					// Bei der n�chsten Wiederholung werden die Selben Produkte angezeigt wie vor
					// dem durchlauf.
				} else if ((indexBM - ausgegebenBM * 2 >= 0) && (ausgegebenBM == ausgegebenDavorBM)) {
					indexBM -= ausgegebenBM * 2;
				} else if (((indexBM - ausgegebenBM * 2) >= 0) && (ausgegebenBM < ausgegebenDavorBM)) {
					indexBM -= (ausgegebenDavorBM + ausgegebenBM);
				}
			} else {
				Shop.setWiederholung(true);
				benachrichtigungBM = Shop.getBenachrichtigung(0);
				indexBM -= ausgegebenBM;
				ausgegebenDavorBM = ausgegebenBM;
			}
			ausgegebenBM = 0;
		}

	}

	// Die Kundenliste wird mit vorgefertigten Kunden zur Simulation gef�llt.
	public void fuelleKundenListe() {

		Kunde k1 = new Kunde("Bauer", "Alex", "01022001", new Adresse("Ahornstrasse 9", "71234", "Nuertingen"),
				"12345678", "kleinAlex");
		k1.setAbo(new Abo());
		k1.setZahlungsmethode(new Paypal("baka@skkss.de"), (byte) 0);
		k1.setZahlungsmethode(new Ueberweisung("DE12345123451234566827"), (byte)1);
		
		Kunde k2 = new Kunde("Stein", "Michael", "11031999", new Adresse("Hauptstrasse 5", "82234", "Frankfurt"),
				"87654321", "gro�Alex");
		
		Kunde k3 = new Kunde("M�ller", "Levin", "22022002", new Adresse("Esslingerstrasse 32", "71534", "Esslingen"),
				"1234562", "Peternor");
		
		Kunde k4 = new Kunde("Parker", "Peter", "20111990", new Adresse("Grove-Street 123", "23233", "Flohloch"),
				"spiderman", "spidy1990");
		k4.setAbo(new Abo());
		k4.setZahlungsmethode(new Paypal("peter.paker@yahoo.de"), (byte) 0);
		
		Kunde k5 = new Kunde("Lee", "Tom", "06051976", new Adresse("Topfgasse 1", "87865", "M�nzhausen"),
				"12345678", "XuVoLee");
		k5.setZahlungsmethode(new Ueberweisung("DE19191909888234756489"), (byte) 0);
		
		Kunde k6 = new Kunde("Leinsen", "Marta", "21101990", new Adresse("Tanzschule 12", "71234", "Dendorf"),
				"12345678", "marta2020");
		k6.setZahlungsmethode(new Paypal("marta@le.net"), (byte) 0);
		k6.setZahlungsmethode(new Paypal("m.l@1029.net"), (byte) 1);
		k6.setZahlungsmethode(new Ueberweisung("DE12300099982277364567"), (byte) 2);
		k6.setAbo(new Abo());
		
		kundeHinzufuegen(k1);
		kundeHinzufuegen(k2);
		kundeHinzufuegen(k3);
		kundeHinzufuegen(k4);
		kundeHinzufuegen(k5);
		kundeHinzufuegen(k6);
		
	}

	// ### oeffAbo ###
	public void oeffAbo() {
		Design oeffAbo = new Design();

		String benachrichtigungAbo = Shop.getBenachrichtigung(0);
		int usrInputAbo = 0;
		boolean speichern = false;

		while (true) {
			oeffAbo.ueberschrift("Abbonement");
			oeffAbo.ausgabeZeile();
			oeffAbo.ausgabeZeile();
			oeffAbo.ausgabeZeileL("Hier k�nnen Sie ihr Abbonement bearbeiten. ");
			oeffAbo.ausgabeZeile();
			oeffAbo.ausgabeZeileL("Name: Jahresabonement", "Preis: 59.99 � im Jahr.");
			oeffAbo.ausgabeZeile();
			oeffAbo.ausgabeZeileL("Mit dem Jahresabbonement haben Sie 10% Rabatt auf alle ihrer Eink�ufe.");
			oeffAbo.ausgabeZeile();
			if (curKunde.getAbo() == null) {
				oeffAbo.ausgabeZeileL("Sie haben das Jaresabbonement zurzeit nicht abboniert.");
			} else if (curKunde.getAbo().isDeabboniert()) {
				oeffAbo.ausgabeZeileL("Sie haben das Jaresabbonement zurzeit nicht abboniert.");
			} else {
				oeffAbo.ausgabeZeileL("Sie haben das Jahresabbonement zurzeit abboniert.");
			}

			oeffAbo.ausgabeBlock(20);
			oeffAbo.ausgabeZeile();
			oeffAbo.trennStrich();
			oeffAbo.ausgabeZeile("Auswahlm�glichkeiten");
			oeffAbo.trennStrich();
			for (int index = 0; index < OPTIONSLEISTEN_KUNDENVERWALTUNG[5].length; index += 2) {
				oeffAbo.ausgabeZeileL(OPTIONSLEISTEN_KUNDENVERWALTUNG[5][index],
						OPTIONSLEISTEN_KUNDENVERWALTUNG[5][index + 1]);
			}
			oeffAbo.trennStrich();
			oeffAbo.infoLeiste(Shop.getWiederholung(), benachrichtigungAbo, true);
			oeffAbo.eingabeZeile();
			usrInputAbo = nutzerEingabeK.getUsrInput(2);
			oeffAbo.setzeNull();

			if (speichern) {
				if (nutzerEingabeK.getMessageString().equals("Y")) {
					benachrichtigungAbo = "Sie haben das Jahresabbonement erfolgreich deabboniert. Ihr Abo l�uft zum ende des Jahres aus.";
					curKunde.getAbo().setDeabboniert(true);
					speichern = false;
					Shop.setWiederholung(true);
					// Speichern der �nderung de Abbonements
					OnlineShopDatenbank.speichernKundenliste(this);
					continue;
				} else if (nutzerEingabeK.getMessageString().equals("N")) {
					speichern = false;
					Shop.setWiederholung(false);
					continue;
				} else {
					benachrichtigungAbo = "Bitte geben Sie Y f�r ja oder N  f�r nein ein.";
					Shop.setWiederholung(true);
					continue;
				}

			}

			if (usrInputAbo == 1) {
				if (curKunde.getAbo() != null && !curKunde.getAbo().isDeabboniert()) {
					benachrichtigungAbo = "Sie haben das Jahresabbonement bereits abboniert!";
				} else if (curKunde.getZahlungsmethode()[0] == null) {
					benachrichtigungAbo = "Sie haben noch keine Zahlungsmethode in Ihrem Account gespeichert. Bitte f�gen Sie erst eine Zahlungsmethode hinzu!";
				} else if (curKunde.getAbo() == null || curKunde.getAbo().isDeabboniert()) {
					if (abbonieren()) {
						benachrichtigungAbo = "Sie haben das Jahresabbonement erfolgreich abboniert!";

						// Speichern der �nderung de Abbonements
						OnlineShopDatenbank.speichernKundenliste(this);
					} else {
						benachrichtigungAbo = "Kauf abgebrochen";
					}
				}
				Shop.setWiederholung(true);
			} else if (usrInputAbo == 2) {
				if (curKunde.getAbo() == null || curKunde.getAbo().isDeabboniert()) {
					benachrichtigungAbo = "Sie haben das Jahresabbonement nicht abboniert.";
				} else {
					speichern = true;
					benachrichtigungAbo = "Wollen Sie das Jahresabbonement wirklich deabbonieren? Y|N";
				}
				Shop.setWiederholung(true);
			} else if (usrInputAbo == 9) {
				break;
			} else {
				benachrichtigungAbo = Shop.getBenachrichtigung(0);
				Shop.setWiederholung(true);
			}
		}
	}

	// Kunde hinzuf�gen
	public void kundeHinzufuegen(Kunde k) {
		kundenListe.put(k.getKennNummer(), k);
	}

	// Kunde l�schen
	public void kundeLoeschen(int kennNummer) {
		kundenListe.remove(kennNummer);
	}

	// Abbo bearbeiten
	public boolean abbonieren() {
		Design abbonieren = new Design();
		BenutzerEingabe nutzerEingabeA = new BenutzerEingabe();
		String benachrichtigungA = Shop.getBenachrichtigung(0);
		int userInputA = 0;
		boolean beendenA = false;

		while (true) {
			abbonieren.ueberschrift("Bezahlen");
			abbonieren.ausgabeZeile();
			if (beendenA) {
				abbonieren.ausgabeZeile("Sie haben den Kauf erfolgreich abgeschlossen!");
				abbonieren.ausgabeZeile();
				abbonieren.ausgabeZeile("Sie haben bezahlt mit: " + curKunde.getZahlungsmethode()[userInputA - 1]);
				abbonieren.ausgabeZeile();
				abbonieren.ausgabeZeile("Wir hoffen Sie sind zufrieden mit Ihrem Kauf");
				// Da beim Threat.sleep die Ausgabe auf der Konsole nicht ganz akkurat ist,
				// werden hier weniger Zeilen ausgegeben als normalerweise
				abbonieren.ausgabeBlock(23);
				abbonieren.trennStrich();
				abbonieren.ausgabeZeile("Auswahlm�glichkeiten");
				abbonieren.trennStrich();
				abbonieren.ausgabeZeile();
				abbonieren.trennStrich();
				abbonieren.leerZeile();
				abbonieren.leerZeile();

				curKunde.setAbo(new Abo());
				if(Shop.isLadeSaves()) {
					OnlineShopDatenbank.speichernKundenliste(this);
				}

				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
				return true;

			}
			abbonieren.ausgabeZeile("Bitte w�hlen Sie Ihre Zahlungsmethode:");
			abbonieren.ausgabeZeile();
			for (Zahlungsmethode z : curKunde.getZahlungsmethode()) {
				if (z == null) {
					abbonieren.ausgabeZeileL("Frei");
				} else {
					abbonieren.ausgabeZuweisung(z.getName(), z.getZahlungsAdresse(), "", "");
				}
			}
			abbonieren.ausgabeBlock(18);
			abbonieren.ausgabeZeile();
			abbonieren.trennStrich();
			abbonieren.ausgabeZeile("Auswahlm�glichkeiten");
			abbonieren.trennStrich();
			for (int indexKa = 0; indexKa < OPTIONSLEISTEN_KUNDENVERWALTUNG[6].length; indexKa += 2) {
				abbonieren.ausgabeZeileL(OPTIONSLEISTEN_KUNDENVERWALTUNG[6][indexKa],
						OPTIONSLEISTEN_KUNDENVERWALTUNG[6][indexKa + 1]);
			}
			abbonieren.trennStrich();
			abbonieren.infoLeiste(Shop.getWiederholung(), benachrichtigungA, true);
			abbonieren.eingabeZeile();
			userInputA = nutzerEingabeA.getUsrInput(0);
			abbonieren.setzeNull();

			// Navigation
			if (userInputA > 0 && userInputA < 6) {
				if (curKunde.getZahlungsmethode()[userInputA - 1] == null) {
					benachrichtigungA = "Keine Zahlungsmethode hinterlegt! Bitte w�hlen Sie eine andere Zahlungsmethode!";
					Shop.setWiederholung(true);
					continue;
				} else {
					beendenA = true;
					continue;
				}
			} else if (userInputA == 9) {
				return false;
			} else {
				Shop.setWiederholung(true);
				benachrichtigungA = Shop.getBenachrichtigung(0);
			}
		}

	}

	/*
	 * ### Getter und Setter ###
	 */
	public TreeMap<Integer, Kunde> getKundenliste() {
		return kundenListe;
	}

	public void setKundenliste(TreeMap<Integer, Kunde> kundenliste) {
		this.kundenListe = kundenliste;
	}

	public Kunde getCurKunde() {
		return this.curKunde;
	}

	public void setCurKunde(Kunde curKunde) {
		this.curKunde = curKunde;
	}

	public static String[][] getOptionsleistenKundenverwaltung() {
		return OPTIONSLEISTEN_KUNDENVERWALTUNG;
	}

	public static String[] getBenachrichtigungKundenverwaltung() {
		return BENACHRICHTIGUNG_KUNDENVERWALTUNG;
	}

	public boolean isEingeloggt() {
		return this.eingeloggt;
	}

	public void setEingeloggt(boolean eingeloggt) {
		this.eingeloggt = eingeloggt;
	}

}
