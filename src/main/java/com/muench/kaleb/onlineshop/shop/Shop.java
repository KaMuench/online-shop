/**
 * ###   "OnlineShop" class: shop   ###
 * Das ist die Hauptklasse des OnlineShops, welche die Main Methode besitzt.
 * Von hier aus k�nnen alle Untermen�s des Shops aufgerufen werden.
 * Also der Warenkorb, die Accountverwaltung, die Kasse, der Katalog, ...
 * 
 * @author Kaleb Muench
 */
package com.muench.kaleb.onlineshop.shop;

import java.util.ArrayList;
import java.util.Collections;


import com.muench.kaleb.onlineshop.ui.design.Design;
import com.muench.kaleb.onlineshop.entities.kunden.Kunde;
import com.muench.kaleb.onlineshop.shop.kundenverwaltung.BenutzerEingabe;
import com.muench.kaleb.onlineshop.shop.kundenverwaltung.Kundenverwaltung;
import com.muench.kaleb.onlineshop.io.OnlineShopDatenbank;
import com.muench.kaleb.onlineshop.entities.produkt.*;

public class Shop {
	/*
	 * 
	 * Katalog des Shops
	 */
	private static Katalog shopKatalog = new Katalog();
	/*
	 * ### Warenkorb des Shops ###
	 */
	private static Warenkorb shopWarenkorb = new Warenkorb();
	// Die Kudenverwaltung des Shops
	private static Kundenverwaltung shopKundenverwaltung = new Kundenverwaltung();
	private static Kasse shopKasse;
	private static BenutzerEingabe nutzerEingabeS = new BenutzerEingabe();
	// Array mit Infoleisten Benachrichtigungen
	private static final String[] BENACHRICHTIGUNGEN_SHOP = { "Ung�ltige Eingabe! Bitte geben Sie erneut ein!",
			"Letzte Seite erreicht!", "Erste Seite erreicht!" };

	private static String benachrichtigungSE = BENACHRICHTIGUNGEN_SHOP[0];

	private static final String[][] OPTIONSLEISTEN_SHOP = {
			{ "0. Startmen�:", "Um das Startmen� anzeigen zu lassen, geben Sie die 0 ein.", "1. Katalog:",
					"Um den Katalog anzuschauen,  geben Sie die 1 ein.", "2. Accounteinstellungen:",
					"Um Ihren Account zu verwalten, geben Sie die 2 ein.", "3. Kasse:",
					"Um zur Kasse zu gelangen, geben Sie die 3 ein.", "9. Beenden:",
					"Um das Programm zu beenden, geben Sie die 9 ein." },
			{ "0. Startmen�", "Um das Startmen� anzeigen zu lassen, geben Sie die 0 ein.", "1. Katalog Men�:",
					"Um zum Katalog Men� zu kommen, geben Sie die 1 ein.", "2. Computer:",
					"Um die Computer anzuschauen, geben Sie die 2 ein.", "3. Notebooks:",
					"Um die Notebooks anzuschauen, geben Sie die 3 ein.", "4. Smartphones:",
					"Um die Smartphones anzuschauen, geben Sie die 4 ein.", "5. Computer-M�use:",
					"Um Computer-M�use anzuschauen, geben Sie die 5 ein.", "6. Computer-Tastaturen:",
					"Um Computer-Tastaturen anzuschauen, geben Sie die 6 ein.", "7. Bildschirme:",
					"Um Computer-Bildschirme anzuschauen, geben Sie die 7 ein.", "9. Zur�ck:",
					"Um zur Startseite zur�ckzukehren, geben Sie die 9 ein." },
			{ "0. Beenden:", "Um das Programm zu beenden, geben Sie die 0 ein.", "1. Vorname:",
					"Um die Kunden nach ihren Vorname zu sortieren, geben Sie die 1 ein.", "2. Nachname:",
					"Um die Kunden nach ihren Vorname zu sortieren, geben Sie die 2 ein.", "3. Kundennummer:",
					"Um die Kunden nach ihren Vorname zu sortieren, geben Sie die 3 ein.", "4. Benutzername:",
					"Um die Kunden nach ihren Vorname zu sortieren, geben Sie die 4 ein.", "5. Geburtstag:",
					"Um die Kunden nach ihren Vorname zu sortieren, geben Sie die 5 ein.", "8. N�chste Seite:",
					"Um zur n�chsten Seite zu bl�ttern, geben Sie die 8 ein.", "9 Zur�ck bl�ttern:",
					"Um zur�ck zu bl�ttern, geben Sie die 9 ein. " } };

	/**
	 * ### fenster ### Beinhaltet die Zahl des akutell ge�ffnetten Fensters
	 */
	private static int fenster = 0;
	private static int fenster2 = 0;
	/**
	 * ### wiederholung ### Falls das Fenster durch eine falsche Benutzereingabe
	 * erneut ausgef�hrt wird, wird "wiederholung" auf true gesetzt. Dies erm�glicht
	 * eine "Design.infoLeiste" Fehlermeldung die eine neue Benutzereingabe
	 * auffordert, bei der das �brige Fenster aber gleich aussieht.
	 */
	private static boolean wiederholung = false;
	private static boolean ladeSaves = false;

	/**
	 * ### anzeigenStartseitEbene ### Diese Methode soll die einzelnen Seiten des
	 * Shops, der ersten Ebene ausgeben.
	 * 
	 * Startseiten Ebene: Katalog Accounteinstellungen Warenkorb Wunschliste Beenden
	 * 
	 * @param page Jeder Integer von 0 bis 9 zeigt eine andere Seite an.
	 *
	 */
	public static void anzeigenStartseitenEbene(int page) {
		Design window = new Design();
		switch (page) {
		case 1: {
			anzeigenKatalogEbene();
			fenster = 0;
			break;
		}
		// Ausgabe der Account einstellungen oder einloggen.
		case 2: {

			while (true) {
				// Accounteinstellungen
				if (shopKundenverwaltung.isEingeloggt()) {
					benachrichtigungSE = shopKundenverwaltung.oeffAccEinst();
					fenster = 0;
					break;
				}
				// Einloggen oder Registrieren.
				else {
					fenster2 = shopKundenverwaltung.abfrageLogReg("den Accounteinstellungen");
				}
				if (fenster2 == 0) {
					fenster = 0;
					break;
				}
			}
			break;
		}
		// Ausgabe Kasse
		case 3: {
			while (true) {
				// Kasse
				if (shopKundenverwaltung.isEingeloggt()) {
					/*
					 * Erst wenn jemand eingeloggt ist, kann die Kasse ge�ffnet werden. Damit die
					 * Referenz der kasseKunde auf das Objekt curKunde der Kundenverwaltung zeigt,
					 * muss jedes mal wenn die Kasse ge�ffnet wird ein neues Objekt Kasse
					 * instanziiert werden.
					 * 
					 */
					shopKasse = new Kasse();
					benachrichtigungSE = shopKasse.oeffneKasse();
					fenster = 0;
					break;
				}
				// Einloggen oder Registrieren
				else {
					fenster2 = shopKundenverwaltung.abfrageLogReg("Kasse");
				}
				if (fenster2 == 0) {
					fenster = 0;
					break;
				}
			}
			break;
		}
		// Programm beenden
		case 9: {
			window.ueberschrift("Das Programm wird beendet.");
			window.ausgabeBlock(2);
			window.ausgabeZeile("Danke, dass Sie unseren Service genutzt haben!!");
			window.ausgabeZeile();
			window.ausgabeZeile("Wir hoffen, dass Sie mit ihren Einkauf zufrieden sind.");
			window.ausgabeBlock(2);
			window.ausgabeZeile("Beehren Sie uns bald wieder!");
			window.ausgabeBlock(30);
			window.trennStrich();

			if (ladeSaves) {
				OnlineShopDatenbank.speichernKatalog(shopKatalog);
				OnlineShopDatenbank.speichernKundenliste(shopKundenverwaltung);
			}

			break;
		}

		// Startseite
		case 0: {
			window.ueberschrift("Willkommen im Elektro-Onlineshop BBKM");
			window.ausgabeZeile();
			window.ausgabeBlock("Werbetext", 26);
			window.ausgabeZeile();
			window.trennStrich();
			window.ausgabeZeile("Auswahlm�glichkeiten:");
			window.trennStrich();
			// Gibt die Auswahlm�glichkeiten auf dem Fenster aus.
			for (int index1 = 0, index2 = 0; index2 < OPTIONSLEISTEN_SHOP[index1].length; index2 += 2) {
				window.ausgabeZeileL(OPTIONSLEISTEN_SHOP[index1][index2], OPTIONSLEISTEN_SHOP[index1][index2 + 1]);
			}
			window.trennStrich();
			window.infoLeiste(wiederholung, benachrichtigungSE, shopKundenverwaltung.isEingeloggt());
			window.eingabeZeile();
			fenster = nutzerEingabeS.getUsrInput(0);
			break;
		}
		default: {
			fenster = 0;
			benachrichtigungSE = BENACHRICHTIGUNGEN_SHOP[0];
		}
		}

	}

	/*
	 * ### anzeigenKatalogEbene ###
	 * 
	 * Diese Methode navigiert zwischen den Shop Fenstern die zum Katalog geh�hren.
	 * 
	 * Katalog Ebene: Katalog Men� Computer Notebooks Smartphones Zubeh�hr Zur�ck
	 * 
	 * @param page Jeder Integer von 0 bis 9 zeigt eine andere Seite an.
	 */
	public static void anzeigenKatalogEbene() {
		Design window = new Design();
		// Fenster, Katalog Ebene: Katalog
		int usrInputKE = 1;
		boolean weiterKE = true;

		while (weiterKE) {
			switch (usrInputKE) {
			case 1: {
				window.ueberschrift("Katalog");
				window.ausgabeZeile();
				window.ausgabeBlock("Werbetext", 22);
				window.ausgabeZeile();
				window.trennStrich();
				window.ausgabeZeile("Auswahlm�glichkeiten:");
				window.trennStrich();
				// Gibt die Auswahlm�glichkeiten auf dem Fenster aus.
				for (int index1 = 1, index2 = 0; index2 < OPTIONSLEISTEN_SHOP[index1].length; index2 += 2) {
					window.ausgabeZeileL(OPTIONSLEISTEN_SHOP[index1][index2], OPTIONSLEISTEN_SHOP[index1][index2 + 1]);
				}
				window.trennStrich();
				window.infoLeiste(wiederholung, getBenachrichtigung(0), shopKundenverwaltung.isEingeloggt());
				window.eingabeZeile();
				usrInputKE = nutzerEingabeS.getUsrInput(0);
				window.setzeNull();
				break;
			}
			// Fenster, Katalog Ebene: Computer
			case 2: {
				shopKatalog.blaetternKatalog(Computer.getArtNumBereich(), "Computer");
				usrInputKE = 1;
				break;
			}
			// Fenster, Katalog Ebene: Notebooks
			case 3: {
				shopKatalog.blaetternKatalog(Notebook.getARTNUMBEREICH(), "Notebooks");
				usrInputKE = 1;
				break;
			}
			// Fenster, Katalog Ebene: Smartphones
			case 4: {
				shopKatalog.blaetternKatalog(Smartphone.getArtnumbereich(), "Smartphones");
				usrInputKE = 1;
				break;
			}
			// Fenster, Katalog Ebene: Computer-M�use
			case 5: {
				shopKatalog.blaetternKatalog(Maus.getArtnumbereich(), "Computer-M�use");
				usrInputKE = 1;
				break;
			}
			// Fenster, Katalog Ebene: Tastaturen
			case 6: {
				shopKatalog.blaetternKatalog(Tastatur.getArtnumbereich(), "Computer-Tastaturen");
				usrInputKE = 1;
				break;
			}
			// Fenster, Katalog Ebene: Displays
			case 7: {
				shopKatalog.blaetternKatalog(Display.getArtnumbereich(), "Computer-Bildschirme");
				usrInputKE = 1;
				break;
			}
			// Kehrt zum Startmen� zur�ck.
			case 9: {
				weiterKE = false;
				break;
			}
			// Kehrt zum Startmen� zur�ck.
			case 0: {
				weiterKE = false;
				break;
			}
			default:
				continue;
			}
		}

	}

	/**
	 * ### setupShop ###
	 * 
	 * Initialisiert eine Shop Situation. Erstellt Produkte. F�llt den Katalog mit
	 * Produkten. Erstellt Kunden f�llt die Kundenliste. Alternativ k�nnen auch die
	 * saves geladen werden.
	 * 
	 * @param OnlineShopDatab gibt an ob das Programm mit saves oder ohne gestartet
	 *                        werden soll.
	 * 
	 */
	public static void setupShop(boolean OnlineShopDatab) {
		if (OnlineShopDatab) {
			shopKatalog = OnlineShopDatenbank.ladeKatalog();
			shopKundenverwaltung.setKundenliste(OnlineShopDatenbank.ladeKundenliste());
		} else {
			shopKatalog.fuelleKatalog();
			shopKundenverwaltung.fuelleKundenListe();
		}

	}

	/*
	 * Methode die das Programm startet. Zuerst kann man ausw�hlen, ob man als
	 * Mitarbeiter oder als Kunde starten m�chte. Danach kann man ausw�hlen ob man
	 * die saves laden m�chte.
	 */
	public static void starteProgramm() {
		int nutzerEingabeShop = 0;
		while (true) {
			System.out.println("W�hlen Sie aus, ob Sie das Program als Mitarbeiter oder Kunde starten wollen: ");
			System.out.println();
			System.out.println("Mitarbeiter: 1");
			System.out.println("Kunde:       2");
			System.out.println("Beenden:     3");
			nutzerEingabeShop = nutzerEingabeS.getUsrInput(0);

			if (nutzerEingabeShop == 1) {
				while (true) {
					System.out.println("\n\nSie starten als Mitarbeiter!");
					System.out.println("Soll das Programm die saves laden?");
					System.out.println("1. Ja");
					System.out.println("2. Nein\n\n");
					nutzerEingabeShop = nutzerEingabeS.getUsrInput(0);
					if (nutzerEingabeShop == 1) {
						ladeSaves = true;
						setupShop(ladeSaves);
					} else if (nutzerEingabeShop == 2) {
						ladeSaves = false;
						setupShop(ladeSaves);
					} else {
						System.out.println("Bitte geben Sie 1, 2 ein!");
					}
					oeffMitarbeiterAnsicht();
					break;
				}
			} else if (nutzerEingabeShop == 2) {
				while (true) {
					System.out.println("\n\nSie starten als Kunde!");
					System.out.println("Soll das Programm die saves laden?");
					System.out.println("1. Ja");
					System.out.println("2. Nein\n\n");
					nutzerEingabeShop = nutzerEingabeS.getUsrInput(0);
					if (nutzerEingabeShop == 1) {
						ladeSaves = true;
						setupShop(ladeSaves);
						break;
					} else if (nutzerEingabeShop == 2) {
						ladeSaves = false;
						setupShop(ladeSaves);
						break;
					} else {
						System.out.println("Bitte geben Sie 1, 2 ein!");
					}
				}

				// Das Hauptprogramm wird so oft wiederholt bis "fenster" = 9 ist.
				// Wenn "fenster" = 9 ist, wird das Programm beendet.
				while (true) {
					anzeigenStartseitenEbene(fenster);
					if (fenster == 9) {
						anzeigenStartseitenEbene(fenster);
						break;
					}
				}
			} else if (nutzerEingabeShop == 3) {
				System.out.println("\n\n\nProgramm beendet!");
				break;
			} else {
				System.out.println("Bitte geben Sie 1, 2 oder 3 ein!");
			}
		}

	}

	public static void oeffMitarbeiterAnsicht() {
		Design oeffMitarbeiterMA = new Design();
		BenutzerEingabe nutzerEingabeMA = new BenutzerEingabe();

		// Die ArrayList in der die shopKundenliste �bergeben wird.
		// Dies erm�glicht dann die Kunden zu sortieren.
		ArrayList<Kunde> kundenListeMA = new ArrayList<>();
		int keyKundenlisteMA = Kunde.getKennnummerbereich() + 1;
		while (shopKundenverwaltung.getKundenliste().containsKey(keyKundenlisteMA)) {
			kundenListeMA.add(shopKundenverwaltung.getKundenliste().get(keyKundenlisteMA));
			keyKundenlisteMA++;
		}

		/*
		 * Die Anzahl der Kunden die in der aktuelle Runde ausgegeben wurde Je nachdem
		 * wie viele Zeilen es braucht um ein Kunde auszugeben, k�nnen mehrere PKunden
		 * pro Schleifen-Durchlauf ausgegeben werden. Wurden z.B. 3 Kunden pro Seite
		 * ausgegeben ist durchlauf = 3.
		 */
		byte indexMA = 0;

		byte durchlaufMA = 0;
		// Die Anzahl der Kunden die letzte Runde ausgegeben wurde.
		byte durchlaufDavorMA = 0;

		// Input des Users, zur Navigation in der Kundenliste
		int usrInputMA = 0;

		// Benachrichtigung des Arrays Shop.BENACHRICHTIGUNG_SHOP.
		// Index 0 ist die falscher Input Benachrichtigung
		String benachrichtigungMA = Shop.getBenachrichtigung(0);

		// Die Zeilen die pro Kundenausgabe genutzt wurden
		byte benutzteZeilenMA = 0;

		// Die Zeilen die noch pro Fenster f�r weitere Ausgaben zu verf�gung stehen.
		// In diesem Layout sind es 26 Zeilen die f�r die Ausgabe der Kunden �brig
		// sind.
		byte zeilenZurVerfuegungMA = 23;

		boolean zurueckNichtMoeglichMA = false;

		while (true) {
			oeffMitarbeiterMA.ueberschrift("Liste mit Kunden-Accounts");
			oeffMitarbeiterMA.ausgabeZeile();

			if (kundenListeMA.isEmpty()) {
				oeffMitarbeiterMA.ausgabeZeile("Die Kundenliste ist leer!");
				oeffMitarbeiterMA.ausgabeBlock(zeilenZurVerfuegungMA);
				zurueckNichtMoeglichMA = true;

			} else {
				while (benutzteZeilenMA <= zeilenZurVerfuegungMA) {
					try {
						benutzteZeilenMA = kundenListeMA.get(indexMA).zeigeKunde();
						indexMA++;
						durchlaufMA++;
						zeilenZurVerfuegungMA -= benutzteZeilenMA;
					} catch (IndexOutOfBoundsException e) {
						break;
					}
				}

				// Die restlichen Zeilen in die keine Kunden mehr passen, werden mit
				// Leerzeilen gef�llt
				oeffMitarbeiterMA.ausgabeBlock(zeilenZurVerfuegungMA);
				zeilenZurVerfuegungMA = 23;
				oeffMitarbeiterMA.ausgabeZeile();
				zurueckNichtMoeglichMA = false;
			}
			oeffMitarbeiterMA.trennStrich();
			oeffMitarbeiterMA.ausgabeZeile("Auswahlm�glichkeiten");
			oeffMitarbeiterMA.trennStrich();
			// Gibt die Auswahlm�glichkeiten auf dem Fenster aus.
			for (int indexW = 0; indexW < OPTIONSLEISTEN_SHOP[2].length; indexW += 2) {
				oeffMitarbeiterMA.ausgabeZeileL(OPTIONSLEISTEN_SHOP[2][indexW], OPTIONSLEISTEN_SHOP[2][indexW + 1]);
			}
			oeffMitarbeiterMA.trennStrich();
			oeffMitarbeiterMA.infoLeiste(Shop.getWiederholung(), benachrichtigungMA,
					Shop.getShopKundenverwaltung().isEingeloggt());
			oeffMitarbeiterMA.eingabeZeile();
			usrInputMA = nutzerEingabeMA.getUsrInput(0);
			oeffMitarbeiterMA.setzeNull();

			// Navigation
			// Sortieren nach Vorname
			if (usrInputMA == 1) {
				Collections.sort(kundenListeMA, new Kunde.MitVorname());
				indexMA = 0;
				durchlaufDavorMA = 0;
			}
			// Sortieren nach Nachname
			else if (usrInputMA == 2) {
				Collections.sort(kundenListeMA, new Kunde.MitNachname());
				indexMA = 0;
				durchlaufDavorMA = 0;
			}
			// Sortieren nach Kennnummer
			else if (usrInputMA == 3) {
				Collections.sort(kundenListeMA, new Kunde.MitKennNummer());
				indexMA = 0;
				durchlaufDavorMA = 0;
			}
			// Sortieren nach Benutzername
			else if (usrInputMA == 4) {
				Collections.sort(kundenListeMA, new Kunde.MitBenutzername());
				indexMA = 0;
				durchlaufDavorMA = 0;
			}
			// Sortieren nach Geburtstag
			else if (usrInputMA == 5) {
				Collections.sort(kundenListeMA, new Kunde.MitGeburtstag());
				indexMA = 0;
				durchlaufDavorMA = 0;
			}

			// Vorw�rts blaettern
			else if (usrInputMA == 8) {
				try {
					kundenListeMA.get(indexMA);
					durchlaufDavorMA = durchlaufMA;

				} catch (IndexOutOfBoundsException e) {
					// Benachrichtigung des Arrays Shop.BENACHRICHTIGUNG_SHOP.
					// Index 1 ist die "Letzte Seite erreicht" Benachrichtigung.
					benachrichtigungMA = Shop.getBenachrichtigung(1);
					Shop.setWiederholung(true);
					indexMA -= durchlaufMA;
				}
			} // Zur�ck blaettern
			else if (usrInputMA == 9) {
				/*
				 * Es werden die ersten Kunden nochmal ausgegeben. Hier muss leider mit einem
				 * extra Boolean gearbeitet werden, welcher anzeigt, ob noch weitere Elemente
				 * vor dem aktuellen Index angezeigt werden k�nnen. Ist die Kundenliste leer ist
				 * das nie der Fall. Deshalb wird dann dieser Boolean auf true gesetzt. Da hier
				 * speziell mit dem Index 0 verglichen wird, anstatt mit curArtNum also den
				 * Artikelnummern der Produkte, funktoniert die Version von blaetternKatalog
				 * hier nicht und der Boolean muss aushelfen.
				 */
				if (zurueckNichtMoeglichMA || indexMA - durchlaufMA * 2 < 0) {
					indexMA -= durchlaufMA;
					// Benachrichtigung des Arrays Shop.BENACHRICHTIGUNGEN_SHOP.
					// Index 2 ist die "Anfang der Seite erreicht" Benachrichtigung.
					benachrichtigungMA = Shop.getBenachrichtigung(2);
					Shop.setWiederholung(true);
					// Bei der n�chsten Wiederholung werden die Selben Kunden angezeigt wie vor
					// dem durchlauf.
				} else if ((indexMA - durchlaufMA * 2 >= 0) && (durchlaufMA == durchlaufDavorMA)) {
					indexMA -= durchlaufMA * 2;
				} else if (((indexMA - durchlaufMA * 2) >= 0) && (durchlaufMA < durchlaufDavorMA)) {
					indexMA -= (durchlaufDavorMA + durchlaufMA);
				}

			} else if (usrInputMA == 0) {
				break;
			}

			// falscher Input
			else {
				// Falls usrInput eine andere Zahl als m�glich oder ung�ltig ist,
				// gibt getUsrInput(...) Integer.MAX_VALUE zur�ck (siehe
				// BenutzerEingabe.getUsrInput). Ist
				// dies der Fall,
				// wird der else Zweig durchlaufen.
				benachrichtigungMA = Shop.getBenachrichtigung(0);
				Shop.setWiederholung(true);
				indexMA -= durchlaufMA;
				durchlaufDavorMA = durchlaufMA;
			}
			durchlaufMA = 0;
		}

	}

	// ### Getter und Setter ####
	public static boolean getWiederholung() {
		return wiederholung;
	}

	public static void setWiederholung(boolean wiederholung) {
		Shop.wiederholung = wiederholung;
	}

	public static String getBenachrichtigung(int index) {
		return BENACHRICHTIGUNGEN_SHOP[index];
	}

	public static Katalog getShopKatalog() {
		return shopKatalog;
	}

	public static void setShopKatalog(Katalog shopKatalog) {
		Shop.shopKatalog = shopKatalog;
	}

	public static String[][] getOptionsleistenShop() {
		return OPTIONSLEISTEN_SHOP;
	}

	public static Warenkorb getShopWarenkorb() {
		return shopWarenkorb;
	}

	public static void setShopWarenkorb(Warenkorb shopWarenkorb) {
		Shop.shopWarenkorb = shopWarenkorb;
	}

	public static Kundenverwaltung getShopKundenverwaltung() {
		return shopKundenverwaltung;
	}

	public static void setShopKundenverwaltung(Kundenverwaltung shopKundenverwaltung) {
		Shop.shopKundenverwaltung = shopKundenverwaltung;
	}

	public static boolean isLadeSaves() {
		return ladeSaves;
	}

}
