/**
 * ###   class: Design   ###
 * Diese Klasse beinhaltet Methoden f�r das Layout der Konsolenausgabe.
 * 
 * @author Kaleb Muench
 */
package com.muench.kaleb.onlineshop.ui.design;

import java.util.Random;

public class Design {

	/*
	 * Wird bei jedem Methodenaufruf einer Methode, die eine Zeile auf der Konsole
	 * ausgibt, hochgez�hlt. So kann ermittelt werden wie viele Zeilen f�r das
	 * Layoutfenster noch zu verf�gung sind.
	 */
	private byte zeilenAnzahl = 0;

	// Jede Design Instanz hat eine Kennummer, damit man schauen kann, welches
	// Objekt eine DesignException ausl�st.
	private int kennNummer;

	// Die Breit f�r das Layout des Fensters
	private static final int LAYOUTBREITE = 160;

	// Die H�he f�r das Layout des Fensters
	private static final byte LAYOUTHOEHE = 40;

	// ### Konstruktor ###
	public Design() {

		// Jedem Design Objekt wird eine einzigartige Kennnummer gegeben.
		this.kennNummer = new Random().nextInt(10000);
	}

	// Setzt die zeilenAnzahl wieder auf 0
	public void setzeNull() {
		this.zeilenAnzahl = 0;
	}

	/*
	 * ### checkLines ###
	 * 
	 * Pr�ft die Zeilen des aktuellen Fensters Falls die Maximale Zeilenanzahl
	 * erreicht ist, wird eine DesignException geworfen.
	 */
	public void checkLines() throws DesignException {
		if (zeilenAnzahl == LAYOUTHOEHE) {
			setzeNull();
			throw new DesignException("Layouth�he erreicht, Design: " + kennNummer);

		}
	}

	// Gibt einen Trennstrich mit #-Zeichen aus.
	public void trennStrich() {

		// Z�lt mit wie oft eine Methode der Klasse Design aufgerufen wurde.
		// Wenn zeilenAnzahl = 40 ist das Layoutfenster voll und es muss ein neues
		// ausgegeben werden.
		try {
			checkLines();
		} catch (DesignException e) {
			e.printStackTrace();
		}
		// Erh�ht die Zeilenanzahl des Fensters um eins.
		zeilenAnzahl++;

		// #### Beginn des eigentlichen Codes ####
		// Der Trennstrich ist 160 Zeichen breit
		System.out.println("########################################" + "########################################"
				+ "########################################" + "########################################");

	}

	/*
	 * ### ueberschrift: Object ###
	 * 
	 * Diese Funktion soll einen Text in # Linien einbetten. Dabei ist die L�nge des
	 * Strings immer gleich. Egal wie viele Buchstaben der Text hat.
	 */
	public void ueberschrift(Object input) {

		// Z�lt mit wie oft eine Methode der Klasse Design aufgerufen wurde.
		// Wenn zeilenAnzahl = 40 ist das Layoutfenster voll und es muss ein neues
		// ausgegeben werden.
		try {
			checkLines();
		} catch (DesignException e) {
			e.printStackTrace();
		}
		// Erh�ht die Zeilenanzahl des Fensters um eins.
		zeilenAnzahl++;

		// #### Beginn des eigentlichen Codes ####
		String text = input.toString();

		byte zahl = LAYOUTBREITE / 2;
		byte zahl2 = (byte) text.length();

		// �berpr�ft ob der Text eine gerade Anzahl an Buchstaben hat oder nicht.
		// Teilt die Zeile in der H�lfte.
		if (zahl2 % 2 == 1) {
			zahl -= ((zahl2 + 1) / 2) + 5;
		} else {
			zahl -= (zahl2 / 2) + 5;
		}

		// Gibt den Text in der Mitte der Zeile aus mit jeweils #-Zeichen davor und
		// dahinter
		for (int i = 1; i <= zahl; i++) {
			System.out.print("#");
		}
		System.out.print("     " + text + "     ");
		if (zahl2 % 2 == 1) {
			for (int i = 1; i <= zahl; i++) {
				System.out.print("#");
			}
			System.out.print("#");
		} else {
			for (int i = 1; i <= zahl; i++) {
				System.out.print("#");
			}
		}
		System.out.println();
	}

	// Bettet Text zwischen zwei # Zeichen ein
	public void ausgabeZeile(Object input) {

		// Z�lt mit wie oft eine Methode der Klasse Design aufgerufen wurde.
		// Wenn zeilenAnzahl = 40 ist das Layoutfenster voll und es muss ein neues
		// ausgegeben werden.
		try {
			checkLines();
		} catch (DesignException e) {
			e.printStackTrace();
		}
		// Erh�ht die Zeilenanzahl des Fensters um eins.
		zeilenAnzahl++;

		// #### Beginn des eigentlichen Codes ####
		String text = input.toString();

		byte zahl = LAYOUTBREITE / 2;
		byte zahl2 = (byte) text.length();

		// �berpr�ft ob der Text eine gerade Anzahl an Buchstaben hat oder nicht.
		// Teilt die Zeile in der H�lfte.
		if (zahl2 % 2 == 1) {
			zahl -= ((zahl2 + 1) / 2);
		} else {
			zahl -= (zahl2 / 2);
		}

		// Gibt den Text in der Mitte der Zeile aus mit jeweils einem #-Zeichen am
		// Amfang und Ende der Zeile
		System.out.print("# ");
		for (int i = 1; i <= zahl - 2; i++) {
			System.out.print(" ");
		}
		System.out.print(text);
		if (zahl2 % 2 == 1) {
			for (int i = 1; i <= zahl - 2; i++) {
				System.out.print(" ");
			}
			System.out.print(" ");
		} else {
			for (int i = 1; i <= zahl - 2; i++) {
				System.out.print(" ");
			}
		}
		System.out.println(" #");
	}

	// Eine Leerzeile mit jeweils einem # am Anfang und einem # am Ende
	public void ausgabeZeile() {

		// Z�lt mit wie oft eine Methode der Klasse Design aufgerufen wurde.
		// Wenn zeilenAnzahl = 40 ist das Layoutfenster voll und es muss ein neues
		// ausgegeben werden.
		try {
			checkLines();
		} catch (DesignException e) {
			e.printStackTrace();
		}
		// Erh�ht die Zeilenanzahl des Fensters um eins.
		zeilenAnzahl++;

		// #### Beginn des eigentlichen Codes ####
		System.out.println("#                                        " + "                                        "
				+ "                                        " + "                                      #");

	}

	// Gibt den Text Linksb�ndig aus umgeben von 2 #-Zeichen
	public void ausgabeZeileL(Object input) {

		// Z�lt mit wie oft eine Methode der Klasse Design aufgerufen wurde.
		// Wenn zeilenAnzahl = 40 ist das Layoutfenster voll und es muss ein neues
		// ausgegeben werden.
		try {
			checkLines();
		} catch (DesignException e) {
			e.printStackTrace();
		}
		// Erh�ht die Zeilenanzahl des Fensters um eins.
		zeilenAnzahl++;

		// #### Beginn des eigentlichen Codes ####
		String text = input.toString();

		System.out.print("#  " + text);
		// Von der Layoutbreite wird die L�nge des Textes abgezogen zzgl. der zwei# und
		// der 2 Leerzeichen die in der Zeile mit ausgegeben werden.
		// Das Ergebnis ist die Anzahl der Leerzeichen die Links von text in die Zeile
		// eingef�gt werden.
		for (int i = 0; i < LAYOUTBREITE - text.length() - 5; i++) {
			System.out.print(" ");
		}
		System.out.println(" #");

	}

	// Gibt den Text Linksb�ndig aus mit einem Tab und dann den zweiten text.
	public void ausgabeZeileL(Object input, Object input2) {

		// Z�lt mit wie oft eine Methode der Klasse Design aufgerufen wurde.
		// Wenn zeilenAnzahl = 40 ist das Layoutfenster voll und es muss ein neues
		// ausgegeben werden.
		try {
			checkLines();
		} catch (DesignException e) {
			e.printStackTrace();
		}
		// Erh�ht die Zeilenanzahl des Fensters um eins.
		zeilenAnzahl++;

		// #### Beginn des eigentlichen Codes ####
		String text = input.toString();
		String text2 = input2.toString();

		// Teilt die Breite der ausgabe in 2 Spalten
		// Spalte 1 ist 1/3
		// Spalte 2 2/3
		byte third = LAYOUTBREITE / 3;
		// Wenn text nicht gr��er als Spalte 1 ist:
		// Dann wird text linksb�ndig in der ersten Spalte ausgegeben und text2 in der
		// Mittleren Spalte
		if (text.length() <= third - 8) {
			System.out.print("#  " + text);
			for (int i = 0; i < third - text.length() - 3; i++) {
				System.out.print(" ");
			}
			System.out.print(text2);
			for (int i = 0; i < (2 * third - text2.length() - 1); i++) {
				System.out.print(" ");
			}
			System.out.println(" #");
			// Wenn text gr��er als die Spalte 1 ist wird er geteilt und ein Teil in Spalte
			// 1 der ersten Zeile ausgegeben
			// Der andere Teil wird in der n�chsten Zeile ausgegeben
			// Ist der andere teil auch zu lang, wird dieser wiederum geteilt und so
			// weiter...
		} else {
			String teilString = text.substring(0, 30) + "-";
			String teilString2 = text.substring(31);
			ausgabeZeileL(teilString, text2);
			ausgabeZeileL(teilString2, "");
		}

	}

	/*
	 * ### ausgabeZuweisung ###
	 * 
	 * Gibt eine Zeile in folgenden Layout aus:# # l1: l2 r1: r2 #
	 */
	public void ausgabeZuweisung(Object l1, Object l2, Object r1, Object r2) {

		// Z�lt mit wie oft eine Methode der Klasse Design aufgerufen wurde.
		// Wenn zeilenAnzahl = 40 ist das Layoutfenster voll und es muss ein neues
		// ausgegeben werden.
		try {
			checkLines();
		} catch (DesignException e) {
			e.printStackTrace();
		}
		// Erh�ht die Zeilenanzahl des Fensters um eins.
		zeilenAnzahl++;

		// #### Beginn des eigentlichen Codes ####
		// Teilen des Layouts in 4 Bereiche
		// Zwei gro�e und zwei kleine
		byte grosseSpalte = LAYOUTBREITE / 4 + LAYOUTBREITE / 8;
		byte kleineSpalte = LAYOUTBREITE / 4 - LAYOUTBREITE / 8;

		// Ausgeben von # l1
		System.out.print("#  " + l1);
		// Auff�llen mit Leerzeichen bis ein viertel der Zeile des Fensters voll ist.
		for (int i = 0; i < kleineSpalte - l1.toString().length() - 4; i++) {
			System.out.print(" ");
		}
		// Ausgeben von l2 und wieder Auff�llen, des zweiten Viertels, der Fensterzeile,
		// mit Leerzeichen.
		System.out.print(" " + l2);
		for (int i = 0; i < grosseSpalte - l2.toString().length() - 1; i++) {
			System.out.print(" ");
		}
		// Das selbe f�r die anderen beiden Viertel
		System.out.print(" " + r1);
		for (int i = 0; i < kleineSpalte - r1.toString().length() - 1; i++) {
			System.out.print(" ");
		}
		System.out.print(" " + r2);
		for (int i = 0; i < grosseSpalte - r2.toString().length() - 1; i++) {
			System.out.print(" ");
		}
		System.out.println("#");

	}

	/**
	 * ### ausgabeBlock: int ###
	 * 
	 * Gibt einen Block mit Leerzeilen aus.
	 * 
	 * @param zeilen: wie viele Leerzeilen ausgegeben werden sollen
	 */
	public void ausgabeBlock(int zeilen) {
		for (int i = 0; i < zeilen; i++) {
			ausgabeZeile();
		}
	}

	/**
	 * ### ausgabeBlock: Object, int ###
	 * 
	 * Gibt einen Block mit Text aus.
	 * 
	 * @param zeilen: wie viele Zeilen mit text ausgegeben werden sollen
	 * @param input:  der Text die jede Zeile ausgegeben werden sollen
	 */
	public void ausgabeBlock(Object input, int zeilen) {
		String text = input.toString();
		for (int i = 0; i < zeilen; i++) {
			ausgabeZeile(text);
		}
	}

	/**
	 * ### infoLeiste: String, boolean ###
	 * 
	 * Diese Methode gibt eine Informationsleiste.
	 * 
	 * Wenn "wiederholung" true ist, wird die Information von nachricht ausgegeben.
	 * 
	 * @param nachricht:    Die Nachricht als String, die Ausgegeben werden soll.
	 *                      Alle Klassen, die die diese Methode benutzen, haben
	 *                      BENACHRICHTIGUNGEN String[] Arrays in denen die
	 *                      Nachrichten gespeichert werden.
	 * @param wiederholung: Wird im Hauptprogramm deklariert welches diese Klasse
	 *                      einbindet. Ist wiederholung true, wird die Nachricht
	 *                      ausgegeben.
	 * @param eingeloggt:   Wenn true, dann steht in der Infoleiste links: LOGGED-IN
	 *                      ansonsten LOGGED-OUT. Wird von der Klasse
	 *                      Kundenverwaltung �bernommen.
	 */
	public void infoLeiste(boolean wiederholung, String nachricht, boolean eingeloggt) {

		// Z�lt mit wie oft eine Methode der Klasse Design aufgerufen wurde.
		// Wenn zeilenAnzahl = 40 ist das Layoutfenster voll und es muss ein neues
		// ausgegeben werden.
		try {
			checkLines();
		} catch (DesignException e) {
			e.printStackTrace();
		}
		// Erh�ht die Zeilenanzahl des Fensters um eins.
		zeilenAnzahl++;

		// #### Beginn des eigentlichen Codes ####;
		StringBuilder retString = new StringBuilder();

		if (wiederholung) {
			retString.append("   " + nachricht);
			// Von der Layoutbreite wird die L�nge des Textes abgezogen zzgl. der zwei# und
			// der 2 Leerzeichen die in der Zeile mit ausgegeben werden.
			// Das Ergebnis ist die Anzahl der Leerzeichen die Links von text in die Zeile
			// eingef�gt werden.
			// LOGGED-IN //LOGGED-OUT
			for (int i = 0; i < LAYOUTBREITE - nachricht.length() - 15; i++) {
				retString.append(" ");
			}
		} else {
			retString.append("                                        " + "                                        "
					+ "                                        " + "                            ");
		}
		if (eingeloggt) {
			retString.append("LOGGED-IN");
		} else {
			retString.append("LOGGED-OUT");
		}

		System.out.println(retString);
	}

	// Gibt die Zeile aus in der die Benutzereingabe eingegeben wird.
	public void eingabeZeile() {
		// Z�lt mit wie oft eine Methode der Klasse Design aufgerufen wurde.
		// Wenn zeilenAnzahl = 40 ist das Layoutfenster voll und es muss ein neues
		// ausgegeben werden.
		try {
			checkLines();
		} catch (DesignException e) {
			e.printStackTrace();
		}
		// Erh�ht die Zeilenanzahl des Fensters um eins.
		zeilenAnzahl++;

		// ### eigentlicher Code ###
		System.out.print("   Eingabe:    ");

	}

	// F�gt einfach nur eine leere Zeile ein, bei der die anzahlZeilen trotzdem
	// hochgez�hlt wird.
	public void leerZeile() {
		// Z�lt mit wie oft eine Methode der Klasse Design aufgerufen wurde.
		// Wenn zeilenAnzahl = 40 ist das Layoutfenster voll und es muss ein neues
		// ausgegeben werden.
		try {
			checkLines();
		} catch (DesignException e) {
			e.printStackTrace();
		}
		// Erh�ht die Zeilenanzahl des Fensters um eins.
		zeilenAnzahl++;

		// ### eigentlicher Code ###
		System.out.println();
	}

	// ### Getter und Setter ###
	public byte getZeilenAnzahl() {
		return zeilenAnzahl;
	}

	public void setZeilenAnzahl(byte zeilenAnzahl) {
		this.zeilenAnzahl = zeilenAnzahl;
	}

	public static int getLayoutbreite() {
		return LAYOUTBREITE;
	}

	public static byte getLayouthoehe() {
		return LAYOUTHOEHE;
	}

	public int getKennNummer() {
		return kennNummer;
	}

}
