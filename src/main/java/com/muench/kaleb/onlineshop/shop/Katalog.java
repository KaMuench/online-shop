package com.muench.kaleb.onlineshop.shop;

import java.util.TreeMap;

import com.muench.kaleb.onlineshop.ui.design.Design;
import com.muench.kaleb.onlineshop.shop.kundenverwaltung.BenutzerEingabe;
import com.muench.kaleb.onlineshop.entities.produkt.*;


public class Katalog {

	// TreeMap welche die Produkte beinhaltet, die im Katalog zu sehen sind
	private TreeMap<Integer, Produkt> katalog = new TreeMap<>();
	private BenutzerEingabe nutzerEingabeKat = new BenutzerEingabe();
	
	/**
	 *  Array in dem die Optionen des Auswahlbereichs des Katalogs gespeichert sind.
	 *	Index 0:	Startmen�
	 *	Index 1:	Katalog Men�
	 *  Index 2:	Warenkorb 
	 *  Index 3:	Vorw�rts
	 *  Index 4:    Zur�ck
	 *  
	 *  @author Kaleb Muench
	 */
	private static final String[] OPTIONSLEISTEN_KATALOG = {"1. Katalog Menü:",
			"Um zum Katalog Menü zu kommen, geben Sie die 1 ein.", "2. In den Warenkorb legen:",
			"Um ein Produkt in den Warenkorb zu legen, geben Sie die 2 ein.", "8. Vorwärts:",
			"Um zur nächsten Seite zu blättern, geben Sie die 8 ein.", "9. Zurück:",
			"Um zurück zub blättern, geben Sie 9 ein. " };

	/**
	 * ### fuelleKatalog ###
	 * 
	 * F�llt das Katalog mit verschiedenen Produkten.
	 * 
	 * @author B�njamin Berber
	 */
	public void fuelleKatalog() {

		// ### Computer ###
		Computer c1 = new Computer("CIX 4", "Acer", 499.99, 25, (byte) 8, (short) 512, "Intel Core i5",
				"Nvidea Redeon RTX", true, false, "kein Display");
		katalog.put(c1.getArtikelnummer(), c1);
		Computer c2 = new Computer("OptiPlex 3080", "Dell", 299.99, 42, (byte) 16, (short) 1024, "Intel Core i7",
				"AMD Radeon RX Vega", true, false, "kein Display");
		katalog.put(c2.getArtikelnummer(), c2);
		Computer c3 = new Computer("ProDesk D7", "HP", 449.99, 36, (byte) 8, (short) 512, "AMD Ryzen 7 5800X",
				"GeForce RTX 3060", true, false, "kein Display");
		katalog.put(c3.getArtikelnummer(), c3);
		Computer c4 = new Computer("DTD 13", "KennyBooks", 797.99, 0, (byte) 8, (short) 512, "Intel Core i5",
				"Nvidea Redeon RTX", true, false, "kein Display");
		katalog.put(c4.getArtikelnummer(), c4);
		
		// ### Notebook ###
		Notebook n1 = new Notebook("ThinkPad X1", "Lenovo", 439.99, 20, (byte) 8, (short) 512, "Intel Core i5",
				"Nvidea Redeon RTX", false, true, "14 Zoll");
		katalog.put(n1.getArtikelnummer(), n1);
		Notebook n2 = new Notebook("Surface ", "Microsoft", 699.99, 0, (byte) 8, (short) 256, "Intel Core i7",
				"UHD Grafik", false, true, "15 Zoll");
		katalog.put(n2.getArtikelnummer(), n2);
		Notebook n3 = new Notebook("AS X12", "Asus", 419.99, 10, (byte) 4, (short) 512, "Intel Core i7",
				"Nvidea Redeon RTX", false, true, "14 Zoll");
		katalog.put(n3.getArtikelnummer(), n3);

		// ### Tastatur ###
		Tastatur t1 = new Tastatur("MK270", "Logitech", 29.99, 20, "kabellos", "QWERTZ");
		katalog.put(t1.getArtikelnummer(), t1);
		Tastatur t2 = new Tastatur("Ornata V2", "Razer", 67.99, 30, "kabelgebunden", "QWERTZ");
		katalog.put(t2.getArtikelnummer(), t2);
		Tastatur t3 = new Tastatur("MK470", "Logitech", 38.99, 40, "kabellos", "QWERTZ");
		katalog.put(t3.getArtikelnummer(), t3);

		// ### Maus ###
		Maus m1 = new Maus("M185", "Logitech", 29.99, 14, "kabellos", "1000 dpi", (byte) 3);
		katalog.put(m1.getArtikelnummer(), m1);
		Maus m2 = new Maus("Surface M", "Microsoft", 42.99, 31, "kabellos", "1000 dpi", (byte) 1);
		katalog.put(m2.getArtikelnummer(), m2);
		Maus m3 = new Maus("RZ01", "Razer", 129.99, 32, "kabellgebunden", "1600 dpi", (byte) 5);
		katalog.put(m3.getArtikelnummer(), m3);

		// ### Display ###
		Display d1 = new Display("L24", "Lenovo", 249.99, 50, "27 Zoll", "5ms Reaktionszeit", "75 Hz");
		katalog.put(d1.getArtikelnummer(), d1);
		Display d2 = new Display("Optix G32", "MSI", 269.99, 54, "31,5 Zoll", "1ms Reaktionszeit", "165 Hz");
		katalog.put(d2.getArtikelnummer(), d2);
		Display d3 = new Display("Nitro QG241", "Acer", 139.00, 0, "23,8 Zoll", "1ms Reaktionszeit", "75 Hz");
		katalog.put(d3.getArtikelnummer(), d3);

		Tastatur t4 = new Tastatur("MK210", "Logitech", 38.99, 40, "kabellos", "QWERTZ");
		katalog.put(t4.getArtikelnummer(), t4);
	}

	/**
	 * ### blaetternKatalog ###
	 * 
	 * Diese Methode soll das Navigieren im Katalog erm�glichen. Die
	 * Auswahlm�glichkeiten
	 * 
	 * @author Kaleb Muench
	 * 
	 */
	public int blaetternKatalog(int curArtNumK, String ueberschriftK) {
		Design blaetternKatalog = new Design();

		// Jedes Produkt hat seinen Artikel Nummer Bereich.
		// Computer > 1000; Notebooks>2000 ...
		// Wenn im Katalog also ein Produkt mit diesem Artikel-Nummer-Beeich enthalten
		// ist, wird die Schleife gestartet,
		// die die Produkte ausgibt.
		curArtNumK += 1;
		int artNumBereichK = curArtNumK;
		/*
		 * Die Anzahl der Produkte die in der aktuelle Runde ausgegeben wurde Je nachdem
		 * wie viele Zeilen es braucht um ein Produkt auszugeben, k�nnen, mehrere
		 * Produkte pro Schleifen Durchlauf ausgegeben werden. Wurden z.B. 3 Produkte
		 * pro Seite ausgegeben ist durchlauf = 3.
		 */
		byte durchlaufK = 0;
		// Die Anzahl der Produkte die letzte Runde ausgegeben wurde.
		byte durchlaufDavorK = 0;
		// Die Zeilen die pro Produktausgabe genutzt wurden
		int benutzteZeilenK = 0;
		// Die Zeilen die noch pro Fenster f�r weitere Ausgaben zu verf�gung stehen.
		// In diesem Layout sind es 29 Zeilen die f�r die Ausgabe der Produkte �brig
		// sind.
		byte zeilenZurVerfuegungK = 26;
		// Input des Users, zu navigation im Katalog
		int usrInputK = 0;
		// Benachrichtigung des Arrays Design.BENACHRICHTIGUNG.
		// Index 0 ist die falscher Input Benachrichtigung
		int benachrichtigungK = 0;
		

		// So lange noch ein Produkt im Katalog ist, welches die curArtNum Artikelnummer
		// hat.
		if (katalog.containsKey(curArtNumK)) {
			while (katalog.containsKey(curArtNumK)) {
				// Ausgabe des oberen Teil des Fensters
				blaetternKatalog.ueberschrift(ueberschriftK);
				blaetternKatalog.ausgabeZeile();
				// Wenn noch genug Zeilen frei sind um ein Produkt auszugeben:
				while (benutzteZeilenK < zeilenZurVerfuegungK && katalog.containsKey(curArtNumK)) {

					benutzteZeilenK = Warenkorb.zeigeProdukt(katalog.get(curArtNumK), true);
					curArtNumK++;
					durchlaufK++;
					zeilenZurVerfuegungK -= benutzteZeilenK;
				}
				// Die restlichen Zeilen in die keine Produkte mehr passen, werden mit
				// Leerzeilen gef�llt
				blaetternKatalog.ausgabeBlock(zeilenZurVerfuegungK);
				zeilenZurVerfuegungK = 26;

				blaetternKatalog.ausgabeZeile();
				blaetternKatalog.trennStrich();
				blaetternKatalog.ausgabeZeile("Auswahlmöglichkeiten:");
				blaetternKatalog.trennStrich();
				// Gibt die Auswahlm�glichkeiten auf dem Fenster aus.
				for (int indexK = 0; indexK < OPTIONSLEISTEN_KATALOG.length; indexK += 2) {
					blaetternKatalog.ausgabeZeileL(OPTIONSLEISTEN_KATALOG[indexK], OPTIONSLEISTEN_KATALOG[indexK + 1]);
				}
				blaetternKatalog.trennStrich();
				blaetternKatalog.infoLeiste(Shop.getWiederholung(), Shop.getBenachrichtigung(benachrichtigungK), Shop.getShopKundenverwaltung().isEingeloggt());
				blaetternKatalog.eingabeZeile();
				usrInputK = nutzerEingabeKat.getUsrInput(0);
				// Da ds LAYOUTHOEHE Zeilen ausgegeben hat, w�rde bei der n�chsten Wiederholung
				// der �u�eren while Schleife, eine Design.Exception ausgegeben werden.
				// Deswegen wird anzahZeilen = 0 gesetzt, da ein neues Fenster angezeigt wird.
				blaetternKatalog.setzeNull();

				// Navigation:
				if (usrInputK == 1) {
					// Es wird zum Katalogmen� zur�ckgekehrt.
					return  1;
				} else if (usrInputK == 2) {
					Shop.getShopWarenkorb().oeffneWarenkorb();
					curArtNumK -= durchlaufK;
					durchlaufK = 0;
					/*
					 * Wenn der Warenkorb aufgerufen wurde, ist usrInput == 2.
					 * Wenn dann als n�chstes ein ung�ltiger usrInput aufgenommen wird, 
					 * bleibt usrInput == 2 (siehe Shop.getUsrInput(...) und der Warenkorb wird als n�chstes angezeigt.
					 * Das soll aber vermieden werden. Denn nach falschen usrInput soll die gerade ge�fnette Seite nochmal angezeigt werden.
					 * Deswegen wir usrInput hier auf einen Wert auserhalb des betrachteten Zahlenbereichs gesetzt: -1.
					 * Das f�hrt dazu das der else Zweig folgt, der wiederum die aktuelle Seite nochmal ausgeben l�sst.
					 */
					usrInputK = -1;
				} else if (usrInputK == 9) {

					// Es werden die ersten Produkte nochmal ausgegeben.
					if ((curArtNumK - durchlaufK * 2) < artNumBereichK) {
						curArtNumK -= durchlaufK;
						// Benachrichtigung des Arrays Design.BENACHRICHTIGUNG.
						// Index 2 ist die "Anfang der Seite erreicht" Benachrichtigung.
						benachrichtigungK = 2;
						Shop.setWiederholung(true);
						// Bei der n�chsten Wiederholung werden die Selben Produkte angezeigt wie vor
						// dem durchlauf.
					} else if ((curArtNumK - durchlaufK * 2 >= artNumBereichK) && (durchlaufK == durchlaufDavorK)) {
						curArtNumK -= durchlaufK * 2;
					} else if (((curArtNumK - durchlaufK * 2) >= artNumBereichK) && (durchlaufK < durchlaufDavorK)) {
						curArtNumK -= (durchlaufDavorK + durchlaufK);
					}
					durchlaufDavorK = durchlaufK;
					durchlaufK = 0;

				} else if (usrInputK == 8) {
					if (!katalog.containsKey(curArtNumK)) {
						curArtNumK -= durchlaufK;
						// Benachrichtigung des Arrays Design.BENACHRICHTIGUNG.
						// Index 1 ist die "Ende der Seite erreicht" Benachrichtigung.
						benachrichtigungK = 1;
						Shop.setWiederholung(true);
						durchlaufK = 0;
					} else {
						durchlaufDavorK = durchlaufK;
						durchlaufK = 0;
					}
				} else {
					// Falls usr Input eine andere Zahl zwischen 1 und 8 ist oder ung�ltig, wird die
					// selbe Seite nochmal angezeigt.
					benachrichtigungK = 0;
					curArtNumK -= durchlaufK;
					Shop.setWiederholung(true);
					durchlaufDavorK = durchlaufK;
					durchlaufK = 0;
				}
			}
			// Wird nie benutzt, compiler will es aber haben.
			return  1;
		} else {
			StringBuilder warten = new StringBuilder();
			for(int i = 0; i < 4; i++) {
				// Wenn keine Computer zum anzeigen im Katalog sind.
				blaetternKatalog.ueberschrift(ueberschriftK);
				blaetternKatalog.ausgabeZeile();
				blaetternKatalog.ausgabeZeile("Leider haben wir zurzeit keine " + ueberschriftK + " im Katalog.");
				//Da beim Threat.sleep die Ausgabe auf der Konsole nicht ganz akkurat ist, 
				//werden hier weniger Zeilen ausgegeben als normalerweise
				blaetternKatalog.ausgabeBlock(27);
				blaetternKatalog.trennStrich();
				blaetternKatalog.ausgabeZeile("Auswahlm�glichkeiten:");
				blaetternKatalog.trennStrich();
				blaetternKatalog.ausgabeZeile();
				blaetternKatalog.trennStrich();
				blaetternKatalog.infoLeiste(true, "Kehrt zur�ck zum Katalog-Men� " + warten, Shop.getShopKundenverwaltung().isEingeloggt());
				blaetternKatalog.leerZeile();
				warten.append(".");
				blaetternKatalog.setzeNull();
				try{
					Thread.sleep(500);
				}catch(InterruptedException e) {
					
				}
			}
			//Einfach eine Zahl die kein Commando erzeugt.
			return Integer.MAX_VALUE;

		}
	}

	// ### toString ###
	@Override
	public String toString() {
		return "Katalog [katalog=" + katalog + "]";
	}

	// ### Getter und Setter ###
	public TreeMap<Integer, Produkt> getKatalog() {
		return katalog;
	}

	public void setKatalog(TreeMap<Integer, Produkt> katalog) {
		this.katalog = katalog;
	}

}
