/**
 * Da die Klasse Kundenverwaltung sehr gro� geworden ist, wurden einige ihrer Methoden
 * in diese Klasse gepackt. Um trotzdem auf einige Attribute von Kundenverwaltung zuzugreifen,
 * wurde in AccountEditor eine Referenzvariable angelegt die auch auf das selbe Objekt der
 * Kundenverwaltung zeigt wie die Variable des Shops.
 * 
 * @author Kaleb Muench
 */
package com.muench.kaleb.onlineshop.shop.kundenverwaltung;

import java.time.LocalDateTime;

import com.muench.kaleb.onlineshop.ui.design.Design;
import com.muench.kaleb.onlineshop.entities.kunden.*;
import com.muench.kaleb.onlineshop.shop.Shop;

public class AccountEditor {

	private static final String[] OPTIONSLEISTEN_ACCOUNTEDITOR = { "1. Benutzername",
			"Um den Benutzernamen zu bearbeiten, geben Sie die 1 ein.", "2. Passwort",
			"Um das Passwort zu bearbeiten, geben Sie die 2 ein.", "3. Vorname",
			"Um den Vornamen zu bearbeiten, geben Sie die 3 ein.", "4. Nachname",
			"Um den Nachnamen zu bearbeiten, geben Sie die 4 ein.", "5. Geburtstag",
			"Um das Geburtsdatum zu bearbeiten, geben Sie die 5 ein.", "6. Adresse ",
			"Um die Adresse zu bearbeiten, geben Sie die 6 ein.", "9. Bearbeitung abschlie�en",
			"Um die Bearbeitung abzuschlie�en, geben Sie die 9 ein." };

	private static final String[][] BENACHRICHTIGUNG_ACCOUNTEDITOR = { {
			"Kehre zur�ck ... Sollen die �nderungen gespeichert werden?   Y | N",
			"Ung�ltige Antwort! Bitte antworten Sie mit Y oder N!", " ist ein unzul�ssiges Zeichen!!",
			"Es gibt noch leere Felder. Wenn jetzt zur�ckgekehrt wird gehen alle Angaben verloren. Zur�ckkehren:   Y|N" },
			{ "", "�ndere Benutzername:   ", "�ndere Passwort:   ", "�ndere Vorname:   ", "�ndere Nachname:   ",
					"�ndere Geburtsdatum:   ", "�ndere Adresse:   " } };

	private static Kundenverwaltung kundV = Shop.getShopKundenverwaltung();

	/**
	 * ### bearbeiteAccount ###
	 * 
	 * Mit dieser Methode ist es m�glich ein schon bestehenden Kunden zu bearbeiten
	 * oder einen Kunden neu anzulegen.
	 * 
	 * @param ueberschriftA Gibt an von wo der accountEditor ge�ffnet wird.
	 * @return boolean Ist ein Benutzer erstellt wurden, wird true zur�ckgegeben
	 */
	public static boolean bearbeiteAccount(String ueberschriftA) {
		BenutzerEingabe nutzerEingabe = new BenutzerEingabe();
		Design bearbeiteAccount = new Design();

		// Da hier die Benachrichtigungen in der infoLeiste zusammengestzt werden
		// sollen,
		// gibt es hier 2 Strings mit Benachrichtigungen
		String benachrichtigungA1 = Shop.getBenachrichtigung(0);
		String benachrichtigungA2 = "";

		// Auswahl das bearbeitet werden soll.
		int usrInputA1 = 0;
		// Auswahl was bearbeitet werden soll.
		byte usrInputA2 = 0;
		boolean zurueckA = false;

		// Z�hler, da die Adresse Schrittweise bearbeitet wird.
		byte adresseTeilA = 0;

		// Array mit allen Benutzerdaten, die bearbeitet werden k�nnen.
		// Am Ende der Bearbeitung, werden die Daten des curKunde(wenn isEingeloggt ==
		// true) mit
		// denen dieses Arrays ersetzt.
		// Ansonsten werden sie dem Kunden Konstruktor �bergeben und ein neuer Kunde
		// wird erstellt.
		StringBuilder[] benutzerDatenA = { new StringBuilder(), new StringBuilder(), new StringBuilder(),
				new StringBuilder(), new StringBuilder(), new StringBuilder(), new StringBuilder(),
				new StringBuilder(), };

		// Ist der Nutzer eingeloggt und will seinen Account bearbeiten, werden die
		// Daten
		// von curKunde in das benutzerDatenA[] Array geladen.
		if (kundV.isEingeloggt()) {
			benutzerDatenA[0].append(kundV.getCurKunde().getBenutzerName());
			benutzerDatenA[1].append(kundV.getCurKunde().getPasswort());
			benutzerDatenA[2].append(kundV.getCurKunde().getVorname());
			benutzerDatenA[3].append(kundV.getCurKunde().getName());
			benutzerDatenA[4].append(kundV.getCurKunde().getGeburtstag());
			benutzerDatenA[5].append(kundV.getCurKunde().getAdresse().getStrasse());
			benutzerDatenA[6].append(kundV.getCurKunde().getAdresse().getPlz());
			benutzerDatenA[7].append(kundV.getCurKunde().getAdresse().getOrt());

		}

		while (true) {
			bearbeiteAccount.ueberschrift(ueberschriftA);
			bearbeiteAccount.ausgabeZeile();
			bearbeiteAccount
					.ausgabeZeile("Bitte geben sie eine Nummer ein f�r die Angaben die Sie bearbeiten wollen. ");
			bearbeiteAccount.ausgabeZeile();
			bearbeiteAccount.ausgabeZeileL("1. Benutzername:", benutzerDatenA[0]);
			bearbeiteAccount.ausgabeZeileL("2. Passwort:", benutzerDatenA[1]);
			bearbeiteAccount.ausgabeZeileL("3. Vorname:", benutzerDatenA[2]);
			bearbeiteAccount.ausgabeZeileL("4. Nachname:", benutzerDatenA[3]);
			bearbeiteAccount.ausgabeZeileL("5. Geburtstag:", Kunde.getGeburtsdatumFormat(benutzerDatenA[4].toString()));
			bearbeiteAccount.ausgabeZeileL("6. Adresse:", benutzerDatenA[5]);
			bearbeiteAccount.ausgabeZeileL("", benutzerDatenA[6]);
			bearbeiteAccount.ausgabeZeileL("", benutzerDatenA[7]);

			bearbeiteAccount.ausgabeBlock(8);
			bearbeiteAccount.ausgabeZeile();
			bearbeiteAccount.ausgabeZeileL("                           ACHTUNG!!");
			bearbeiteAccount.ausgabeZeileL("Nicht alle Eingaben sind zul�ssig als Passwort oder Benutzernamen.");
			bearbeiteAccount.ausgabeZeileL("Nicht erlaubt sind:   ");
			bearbeiteAccount
					.ausgabeZeileL("Einzelne Zahlen von 0 bis 9. Diese sind nur zur Navigation im Shop bestimmt.");
			bearbeiteAccount.ausgabeZeileL(
					"Auch Sonderzeichen []\"!?\\/#'*+.,~{}()%&$�|<>�`'=^�@�;:_ und Leerzeichen sind nicht erlaubt.");
			bearbeiteAccount.ausgabeZeile();
			bearbeiteAccount.trennStrich();
			bearbeiteAccount.ausgabeZeile("Auswahlm�glichkeiten");
			bearbeiteAccount.trennStrich();
			for (int indexA1 = 0; indexA1 < OPTIONSLEISTEN_ACCOUNTEDITOR.length; indexA1 += 2) {
				bearbeiteAccount.ausgabeZeileL(OPTIONSLEISTEN_ACCOUNTEDITOR[indexA1],
						OPTIONSLEISTEN_ACCOUNTEDITOR[indexA1 + 1]);
			}
			bearbeiteAccount.trennStrich();
			bearbeiteAccount.infoLeiste(Shop.getWiederholung(), benachrichtigungA1, kundV.isEingeloggt());
			bearbeiteAccount.eingabeZeile();
			usrInputA1 = nutzerEingabe.getUsrInput(2);
			bearbeiteAccount.setzeNull();

			/*
			 * ### Zur�ckkehren ### Wenn zur�ckgekehrt werden soll, w�hrend man eingeloggt
			 * ist oder noch Felder frei sind, wird vorher abgefragt ob �nderungen
			 * gespeichert werden sollen. Wenn eingeloggt: Y: Daten von benutzerDatenA Array
			 * in curKunde �bergeben, return fals N: return false Sonst: Erneute
			 * Aufforderung f�r Eingabe von Y | N
			 * 
			 * Wenn nicht eingeloggt: Y: return false N: Benutzer kann weiter bearbeiten
			 * Sonst: Erneute Aufforderung f�r Eingabe von Y | N
			 */

			if (zurueckA && kundV.isEingeloggt()) {
				// Speichern der �nderungen
				if (nutzerEingabe.getMessageString().equals("Y") && usrInputA1 == Integer.MIN_VALUE) {
					kundV.getCurKunde().setBenutzerName(benutzerDatenA[0].toString());
					kundV.getCurKunde().setPasswort(benutzerDatenA[1].toString());
					kundV.getCurKunde().setVorname(benutzerDatenA[2].toString());
					kundV.getCurKunde().setName(benutzerDatenA[3].toString());
					kundV.getCurKunde().setGeburtstag(benutzerDatenA[4].toString());
					kundV.getCurKunde().setAdresse(new Adresse(benutzerDatenA[5].toString(),
							benutzerDatenA[6].toString(), benutzerDatenA[7].toString()));

					return false;
				} else if (nutzerEingabe.getMessageString().equals("N") && usrInputA1 == Integer.MIN_VALUE) {
					return false;
				} else {
					benachrichtigungA1 = BENACHRICHTIGUNG_ACCOUNTEDITOR[0][1];
					Shop.setWiederholung(true);
					continue;
				}
			} else if (zurueckA) {
				if (nutzerEingabe.getMessageString().equals("Y")) {
					Shop.setWiederholung(false);
					return false;
				} else if (nutzerEingabe.getMessageString().equals("N")) {
					Shop.setWiederholung(false);
					zurueckA = false;
					usrInputA1 = 0;
					usrInputA2 = 0;
					continue;
				} else {
					benachrichtigungA1 = "Verlassen: " + BENACHRICHTIGUNG_ACCOUNTEDITOR[0][1];
					Shop.setWiederholung(true);
					continue;
				}
			}
			// ### Navigation ###
			/*
			 * ### Zur�ck zum Loggin und Registrierungsmen� bzw. Konto erstellen ###
			 * 
			 * Wenn die Benutzereingabe 9 ist und die Adresse gerade nicht bearbeitet wird,
			 * soll zur�ck gekehrt werden.
			 * 
			 * 
			 * Wenn zur�ckgekehrt werden soll ohne das man eingeloggt ist: �berpr�fen ob
			 * noch leere Felder vorhanden sind. Wenn nicht: Ein neuer Kunde mit den Daten
			 * von benutzerDatenA Array wird erstellt und der Kundenliste der
			 * Kundenverwaltung hinzugef�gt. Wenn ja: Schleife l�uft weiter und gelangt zur
			 * if(zurueck) Abfrage f�r abbrechen oder speichern
			 * 
			 * Wenn zur�ckgekehrt werden soll, w�hrend man eingeloggt ist: Schleife l�uft
			 * weiter und gelangt zur if(zurueck) Abfrage f�r abbrechen oder speichern
			 */
			if (usrInputA1 == 9 && adresseTeilA == 0) {
				zurueckA = true;
				if (!kundV.isEingeloggt()) {
					int i = 0;
					for (; i < benutzerDatenA.length; i++) {
						if (benutzerDatenA[i].length() == 0) {
							benachrichtigungA1 = BENACHRICHTIGUNG_ACCOUNTEDITOR[0][3];
							Shop.setWiederholung(true);
							break;
						}
					}
					if (i == benutzerDatenA.length) {

						kundV.kundeHinzufuegen(new Kunde(benutzerDatenA[3].toString(), benutzerDatenA[2].toString(),
								benutzerDatenA[4].toString(),
								new Adresse(benutzerDatenA[5].toString(), benutzerDatenA[6].toString(),
										benutzerDatenA[7].toString()),
								benutzerDatenA[1].toString(), benutzerDatenA[0].toString()));

						return true;
					}
					continue;
				} else {
					benachrichtigungA1 = BENACHRICHTIGUNG_ACCOUNTEDITOR[0][0];
					Shop.setWiederholung(true);
					continue;
				}

			}
			// ### Bearbeiten der einzelnen Benutzerdaten ###
			// Wenn die zweite Eingabe, nachdem ausgw�hlt wurde was bearbeitet werden soll,
			// g�ltig ist (usrInput == Integer.MIN_VALUE), wird die zu 
			// bearbeitende Benutzerinformation durch die zweite
			// Benutzeingabe ersetzt.
			// Dabei wird die Variable BenutzerEigabe.messageString genutzt um die
			// Benutzereingabe entgegenzunehmen.
			if (usrInputA1 == Integer.MIN_VALUE || adresseTeilA != 0) {
				// ### Bearbeiten des Benutzernamens ###
				// Wenn der Benutzername schon existiert, wird eine erneute Eingabe gefordert.
				if (usrInputA2 == 1) {
					int key = 1001;
					boolean bleibt = false;
					while (kundV.getKundenliste().containsKey(key)) {
						if (kundV.getKundenliste().get(key).getBenutzerName()
								.equals(nutzerEingabe.getMessageString())) {
							Shop.setWiederholung(true);
							benachrichtigungA1 = benachrichtigungA2 + "Benutzername existiert schon!";
							bleibt = true;

							break;
						}
						key++;
					}

					if (!bleibt) {
						benutzerDatenA[0].delete(0, benutzerDatenA[0].length());
						benutzerDatenA[0].append(nutzerEingabe.getMessageString());
						benachrichtigungA1 = "Benutzername erfolgreich ge�ndert!";
						Shop.setWiederholung(true);
						usrInputA2 = 0;
					}
				}
				// ### Bearbeiten des Passworts ###
				else if (usrInputA2 == 2) {
					benutzerDatenA[1].delete(0, benutzerDatenA[1].length());
					benutzerDatenA[1].append(nutzerEingabe.getMessageString());
					benachrichtigungA1 = "Passwort erfolgreich ge�ndert!";
					Shop.setWiederholung(true);
					usrInputA2 = 0;
				}
				// ### Bearbeiten des Vornamens ###
				else if (usrInputA2 == 3) {
					benutzerDatenA[2].delete(0, benutzerDatenA[2].length());
					benutzerDatenA[2].append(nutzerEingabe.getMessageString());
					benachrichtigungA1 = "Vorname erfolgreich ge�ndert!";
					Shop.setWiederholung(true);
					usrInputA2 = 0;
				}
				// ### Bearbeiten des Nachnamens ###
				else if (usrInputA2 == 4) {
					benutzerDatenA[3].delete(0, benutzerDatenA[3].length());
					benutzerDatenA[3].append(nutzerEingabe.getMessageString());
					benachrichtigungA1 = "Nachname erfolgreich ge�ndert!";
					Shop.setWiederholung(true);
					usrInputA2 = 0;
				}
				/*
				 *  ### Bearbeiten des Geburtsdatums ###
				 *
				 * Da das Geburtsdatum als zusammenh�ngende Zahl entgegengenommen wird,
				 * M�ssen die Indizes St�ck f�r St�ck gepr�ft werden.
				 * Ist die ganze Zahl l�nger oder k�rzer als 8 Ziffern, folgt eine Fehlermeldung.
				 * Ist die Zahl der ersten beiden Indizes > 31 | < 0, folgt eine Fehlermeldung.
				 * Ist die Zahl der n�chsten beiden Indizes > 12 | < 0, folgt eine Fehlermeldung.
				 * Ist die Zahl der letzten vier Indizes < 1900 | > 2004, folgt eine Fehlermeldung.
				 * 
				 */
				else if (usrInputA2 == 5) {
					String geburtsStringE = nutzerEingabe.getMessageString();
					if (nutzerEingabe.getMessageString().length() == 8) {
						try {
							Integer.parseInt(nutzerEingabe.getMessageString());
							if (Integer.parseInt(geburtsStringE.substring(0, 2)) < 32) {
								if (Integer.parseInt(geburtsStringE.substring(2, 4)) < 13) {
									int iE = Integer.parseInt(geburtsStringE.substring(4));
									// Wenn der Kunde unter 18 ist oder vor 1900 geboren wurde, kann er ebenfalls
									// keinen Account erstellen. Die berechnung ist ungenau, sollte aber hier
									// ausreichen.
									if (iE <= (LocalDateTime.now().getYear() - 18) && iE >= 1900) {
										benutzerDatenA[4].delete(0, benutzerDatenA[4].length());
										benutzerDatenA[4].append(geburtsStringE);
										Shop.setWiederholung(true);
										benachrichtigungA1 = "Geburtsdatum erfolgreich ge�ndert!";
										usrInputA2 = 0;
										continue;

									} else {
										// Wenn das Jahr nicht stimmt wird eine Fehlermeldung ausgegeben.
										Shop.setWiederholung(true);
										benachrichtigungA1 = benachrichtigungA2
												+ "Formatfehler! Das Jahr muss zwischen 1900 und "
												+ (LocalDateTime.now().getYear() - 18) + " liegen!";
										continue;
									}
								}
							}
						} catch (NumberFormatException e) {

						}
					}
					Shop.setWiederholung(true);
					benachrichtigungA1 = benachrichtigungA2 + "Formatfehler! Format ist: TTMMJJJJ, ohne Punkte!";
				}
				/*
				 * ### Bearbeiten der Adresse ###
				 * 
				 * Die Adresse wird St�ck f�r St�ck bearbeitet.
				 * Zuerst die Stra�e, dann die Hausnummer, PLZ und dan der Ort.
				 * Daf�r wird adresseTeilA Variable immer um eins erh�ht.
				 * F�r jeden neuen Wert von adresseTeilA wird der n�chste Teil bearbeitet.
				 * BenutzerEingabe, filtert schon alle Sonderzeichen raus. 
				 * Die restlichen unerw�nschten Benutzereingaben m�ssen nun ebenfalls
				 * abgefangen werden.
				 * 
				 * Stra�e: Nur chars > 47 & < 58 sind g�ltig. Sprich nur Buchstaben.
				 * Hausnummer: Nur Ziffern
				 * PLZ: Nur 5 Ziffern
				 * Ort: Genauso wie Stra�e
				 */
				
				else if (usrInputA2 == 6) {
					//### Stra�e ###
					if (adresseTeilA == 1) {
						if (usrInputA1 == Integer.MIN_VALUE) {
							StringBuilder adressStringE = new StringBuilder(nutzerEingabe.getMessageString());
							int i = 0;
							for (; i < adressStringE.length(); i++) {
								if (adressStringE.charAt(i) > 47 && adressStringE.charAt(i) < 58) {
									break;
								}

							}
							if (i == adressStringE.length()) {
								benutzerDatenA[5].delete(0, benutzerDatenA[5].length());
								benutzerDatenA[5].append(nutzerEingabe.getMessageString());
								benachrichtigungA1 = benachrichtigungA2 + " Hausnummer: ";
								Shop.setWiederholung(true);
								adresseTeilA++;
								continue;
							}
						}
						benachrichtigungA1 = benachrichtigungA2
								+ " Stra�e: Kein g�ltiger Stra�enname! Nur Buchstaben sind erlaubt!";
						Shop.setWiederholung(true);
					}
					//### Hausnummer ###
					else if (adresseTeilA == 2) {
						if (usrInputA1 <= 9 && usrInputA1 >= 0) {
							benutzerDatenA[5].append(" " + usrInputA1);
							adresseTeilA++;
						} else {
							try {
								if (Short.parseShort(nutzerEingabe.getMessageString()) > 0) {
									benutzerDatenA[5].append(" " + nutzerEingabe.getMessageString());
									adresseTeilA++;
								} else {
									benachrichtigungA1 = benachrichtigungA2 + " Hausnummer: "
											+ Shop.getBenachrichtigung(0);
									Shop.setWiederholung(true);
									continue;
								}

							} catch (NumberFormatException e) {
								benachrichtigungA1 = benachrichtigungA2 + " Hausnummer: " + Shop.getBenachrichtigung(0);
								Shop.setWiederholung(true);
								continue;
							}

						}
						benachrichtigungA1 = benachrichtigungA2 + " Postleitzahl: ";
						Shop.setWiederholung(true);
					} 
					//### PLZ ###
					else if (adresseTeilA == 3) {
						if (usrInputA1 == Integer.MIN_VALUE) {
							if (nutzerEingabe.getMessageString().length() == 5) {
								try {
									if (Integer.parseInt(nutzerEingabe.getMessageString()) > 0) {
										benutzerDatenA[6].delete(0, benutzerDatenA[6].length());
										benutzerDatenA[6].append(nutzerEingabe.getMessageString());
										benachrichtigungA1 = benachrichtigungA2 + " Ort: ";
										Shop.setWiederholung(true);
										adresseTeilA++;
										continue;
									}
								} catch (NumberFormatException e) {
								}
							}
						}
						benachrichtigungA1 = benachrichtigungA2 + " Postleitzahl: ung�ltiges Format";
						Shop.setWiederholung(true);
					} 
					//### Ort ###
					else if (adresseTeilA == 4) {
						if (usrInputA1 == Integer.MIN_VALUE) {
							StringBuilder adressStringE = new StringBuilder(nutzerEingabe.getMessageString());
							int i = 0;
							for (; i < adressStringE.length(); i++) {
								if (adressStringE.charAt(i) > 47 && adressStringE.charAt(i) < 58) {
									break;
								}

							}
							if (i == adressStringE.length()) {
								benutzerDatenA[7].delete(0, benutzerDatenA[7].length());
								benutzerDatenA[7].append(nutzerEingabe.getMessageString());
								benachrichtigungA1 = "Adresse erfolgreich ge�ndert!";
								Shop.setWiederholung(true);
								adresseTeilA = 0;
								usrInputA2 = 0;
								continue;
							}
						}
						benachrichtigungA1 = benachrichtigungA2
								+ " Ort: Kein g�ltiger Ortsname! Nur Buchstaben sind erlaubt!";
						Shop.setWiederholung(true);
					}
				}
				// Wenn noch kein Bereich zum bearbeiten ausgew�hlt wurde.
				else {
					Shop.setWiederholung(true);
					benachrichtigungA1 = Shop.getBenachrichtigung(0);
					continue;
				}

			}
			// Wenn die zweite Eingabe ein ung�ltiges Zeichen enth�lt, wird eine
			// Fehlermeldung ausgegeben.
			else if (usrInputA1 == Integer.MAX_VALUE && usrInputA2 != 0) {
				benachrichtigungA1 = benachrichtigungA2 + nutzerEingabe.getMessageString()
						+ BENACHRICHTIGUNG_ACCOUNTEDITOR[0][2];
			}
			// Wenn die zweite Eingabe eine ung�ltige Zahl von 0 bis 9 enth�lt, wird eine
			// Fehlermeldung ausgegeben. 
			else if (usrInputA2 != 0) {
				benachrichtigungA1 = benachrichtigungA2 + usrInputA1 + BENACHRICHTIGUNG_ACCOUNTEDITOR[0][2];
				Shop.setWiederholung(true);
			}
			// F�r die erste eingabe zum Ausw�hlen was bearbeitet werden soll.
			else {
				if (usrInputA1 == 1) {
					Shop.setWiederholung(true);
					benachrichtigungA1 = BENACHRICHTIGUNG_ACCOUNTEDITOR[1][1];
					benachrichtigungA2 = benachrichtigungA1;
					usrInputA2 = 1;
				} else if (usrInputA1 == 2) {
					Shop.setWiederholung(true);
					benachrichtigungA1 = BENACHRICHTIGUNG_ACCOUNTEDITOR[1][2];
					benachrichtigungA2 = benachrichtigungA1;
					usrInputA2 = 2;
				} else if (usrInputA1 == 3) {
					Shop.setWiederholung(true);
					benachrichtigungA1 = BENACHRICHTIGUNG_ACCOUNTEDITOR[1][3];
					benachrichtigungA2 = benachrichtigungA1;
					usrInputA2 = 3;
				} else if (usrInputA1 == 4) {
					Shop.setWiederholung(true);
					benachrichtigungA1 = BENACHRICHTIGUNG_ACCOUNTEDITOR[1][4];
					benachrichtigungA2 = benachrichtigungA1;
					usrInputA2 = 4;
				} else if (usrInputA1 == 5) {
					Shop.setWiederholung(true);
					benachrichtigungA2 = BENACHRICHTIGUNG_ACCOUNTEDITOR[1][5];
					benachrichtigungA1 = benachrichtigungA2
							+ " Bitte gib das Geburtsdatum im Format TTMMJJJJ  ohne Punkte ein. ";
					usrInputA2 = 5;
				} else if (usrInputA1 == 6) {
					Shop.setWiederholung(true);
					benachrichtigungA2 = BENACHRICHTIGUNG_ACCOUNTEDITOR[1][6];
					benachrichtigungA1 = benachrichtigungA2 + "Stra�e: ";
					usrInputA2 = 6;
					adresseTeilA = 1;
				} else {
					Shop.setWiederholung(true);
					benachrichtigungA1 = Shop.getBenachrichtigung(0);
				}
			}
		}

	}
}
