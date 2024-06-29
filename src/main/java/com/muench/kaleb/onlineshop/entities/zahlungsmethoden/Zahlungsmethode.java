/**
 * 
 * @author B�nyamin Berber
 */

package com.muench.kaleb.onlineshop.entities.zahlungsmethoden;

import com.muench.kaleb.onlineshop.ui.design.Design;
import com.muench.kaleb.onlineshop.shop.kundenverwaltung.BenutzerEingabe;
import com.muench.kaleb.onlineshop.io.OnlineShopIO;
import com.muench.kaleb.onlineshop.shop.Shop;

public abstract class Zahlungsmethode implements OnlineShopIO{

	/*
	 * ### Variablen ###
	 */
	protected String Name;
	protected String zahlungsAdresse;

	private static final String[] BENACHRICHTIGUNG_ZAHLUNGSM = { "W�hlen Sie eine Zahlungsmethode!",
			"Geben Sie die Zahlungsadresse ein!", "Ung�ltige Antwort! Bitte antworten Sie mit Y oder N!",
			"Sie k�nnen die Bearbeitung jetzt beenden!" };

	/*
	 * ### Konstruktor ###
	 */
	public Zahlungsmethode(String zahlungsAdresse) {
		this.zahlungsAdresse = zahlungsAdresse;
	}

	/*
	 * ### toString ###
	 */
	@Override
	public String toString() {
		return String.format("%-14s%s", Name, zahlungsAdresse);
	}
	
	/*
	 * ### Methoden ###
	 */
	public static Zahlungsmethode bearbZahlungsM(byte nummer) {
		Design bearbZahlungsM = new Design();
		BenutzerEingabe nutzerEingabeBZ = new BenutzerEingabe();

		int usrInputBZ = 0;
		String benachrichtigungBZ = BENACHRICHTIGUNG_ZAHLUNGSM[0];
		String artBZ = "";
		String adresseBZ = "";
		byte beendenBZ1 = 0;
		boolean meldung = true;

		Shop.setWiederholung(true);

		while (true) {
			bearbZahlungsM.ueberschrift("Bearbeite Zahlungsmethode " + nummer);
			bearbZahlungsM.ausgabeZeile();
			bearbZahlungsM.ausgabeZeile();
			bearbZahlungsM.ausgabeZeileL(
					"Bitte w�hlen Sie die Art der Zahlungsmethode aus und geben Sie die dazugeh�rige Zahlungsadresse mit an.");
			bearbZahlungsM.ausgabeZeileL("Bei Paypal w�re das die dazu hinterlegte Email.");
			bearbZahlungsM.ausgabeZeileL("Wenn Sie ein Konto hinterlegen wollen, geben Sie die IBAN ein.");
			bearbZahlungsM.ausgabeZeile();
			bearbZahlungsM.ausgabeZeileL("Zahlungsmethode");
			bearbZahlungsM.ausgabeZeileL("Art der Zahlungsmethode: " + artBZ, "Zahlungsadresse: " + adresseBZ);
			bearbZahlungsM.ausgabeBlock(22);
			bearbZahlungsM.trennStrich();
			bearbZahlungsM.ausgabeZeile("Auswahlm�glichkeiten");
			bearbZahlungsM.trennStrich();
			bearbZahlungsM.ausgabeZeileL("1. Paypal hinzuf�gen: ",
					"Um ein Paypal Konto zu hinterlegen, geben Sie die 1 ein.");
			bearbZahlungsM.ausgabeZeileL("2. Bankonto hinzuf�gen:",
					"Um ein Bankkonto zu hinterlegen, geben Sie die 2 ein.");
			bearbZahlungsM.ausgabeZeileL("9. Bearbeitung beenden:",
					"Um die Bearbeitung der Zahlungsmethode zu beenden, geben Sie die 9 ein.");
			bearbZahlungsM.trennStrich();
			bearbZahlungsM.infoLeiste(Shop.getWiederholung(), benachrichtigungBZ, true);
			bearbZahlungsM.eingabeZeile();
			usrInputBZ = nutzerEingabeBZ.getUsrInput(3);
			bearbZahlungsM.setzeNull();

			if (artBZ.equals("Paypal") && usrInputBZ == Integer.MIN_VALUE && beendenBZ1 == 0) {
				char[] arrayBZ = { '@', '.' };
				int e = 0;
				int f = 0;
				for (int i = 0; i < nutzerEingabeBZ.getMessageString().length(); i++) {
					if (nutzerEingabeBZ.getMessageString().charAt(i) == arrayBZ[0]) {
						e++;
					} else if (nutzerEingabeBZ.getMessageString().charAt(i) == arrayBZ[1]) {
						f++;
					}
				}
				if (e < 1 || e > 1 || f < 1) {
					benachrichtigungBZ = "Ung�ltige Email-Adresse!";
					meldung = false;

				} else {
					adresseBZ = nutzerEingabeBZ.getMessageString();
					meldung = true;
				}

			} else if (artBZ.equals("Konto") && usrInputBZ == Integer.MIN_VALUE && beendenBZ1 == 0) {
				if (nutzerEingabeBZ.getMessageString().startsWith("DE")
						&& nutzerEingabeBZ.getMessageString().length() == 22) {
					boolean wrong = false;
					char[] charArrayBM = nutzerEingabeBZ.getMessageString().substring(2).toCharArray();
					for (char c : charArrayBM) {
						if (c > 47 && c < 58) {
						} else {
							wrong = true;
							break;
						}
					}
						if (wrong) {
							benachrichtigungBZ = "Ung�ltige IBAN!";
							meldung = false;
						} else {
							adresseBZ = nutzerEingabeBZ.getMessageString();
							meldung = true;
						}
					
				} else {
					benachrichtigungBZ = "Ung�ltige IBAN!";
					meldung = false;
				}
			}

			// Abbrechen
			else if (beendenBZ1 == 1) {
				if (nutzerEingabeBZ.getMessageString().equals("Y") && usrInputBZ == Integer.MIN_VALUE) {
					return null;
				} else if (nutzerEingabeBZ.getMessageString().equals("N") && usrInputBZ == Integer.MIN_VALUE) {
					beendenBZ1 = 0;
					meldung = true;
				} else {
					benachrichtigungBZ = BENACHRICHTIGUNG_ZAHLUNGSM[2];
					meldung = false;

				}
			}
			// Beenden
			else if (beendenBZ1 == 2) {
				if (nutzerEingabeBZ.getMessageString().equals("Y") && usrInputBZ == Integer.MIN_VALUE) {
					if (artBZ.equals("Paypal")) {
						return new Paypal(adresseBZ);
					} else {
						return new Ueberweisung(adresseBZ);
					}
				} else if (nutzerEingabeBZ.getMessageString().equals("N") && usrInputBZ == Integer.MIN_VALUE) {
					beendenBZ1 = 0;
					meldung = true;
				} else {
					benachrichtigungBZ = BENACHRICHTIGUNG_ZAHLUNGSM[2];
					meldung = false;
				}
			} else if (usrInputBZ == 1) {
				artBZ = "Paypal";
				adresseBZ = "";
			} else if (usrInputBZ == 2) {
				artBZ = "Konto";
				adresseBZ = "";
			} else if (usrInputBZ == 9) {
				if (artBZ.equals("") || adresseBZ.equals("")) {
					benachrichtigungBZ = "Wollen Sie die bearbeitung wirklich abbrechen. Die �nderungen gehen verloren! Y|N";
					meldung = false;
					beendenBZ1 = 1;
				} else {
					benachrichtigungBZ = "Soll diese Zahlungsmethode hinzugef�gt werden? Y|N ";
					meldung = false;
					beendenBZ1 = 2;
				}
			}

			else {
				benachrichtigungBZ = "";
			}

			if (benachrichtigungBZ.equals("")) {
				benachrichtigungBZ = Shop.getBenachrichtigung(0);
			} else if (artBZ.equals("") && meldung) {
				benachrichtigungBZ = BENACHRICHTIGUNG_ZAHLUNGSM[0];
			} else if (adresseBZ.equals("") && meldung) {
				benachrichtigungBZ = BENACHRICHTIGUNG_ZAHLUNGSM[1];
			} else if (beendenBZ1 == 0 && meldung) {
				benachrichtigungBZ = BENACHRICHTIGUNG_ZAHLUNGSM[3];
			}
			Shop.setWiederholung(true);
		}
	}
	
	@Override
	public abstract String speichernInTxt();
	
	/*
	 * ### Getter und Setter
	 */
	public String getZahlungsAdresse() {
		return zahlungsAdresse;
	}

	public void setZahlungsAdresse(String zahlungsAdresse) {
		this.zahlungsAdresse = zahlungsAdresse;
	}

	public String getName() {
		return Name;
	}
}
