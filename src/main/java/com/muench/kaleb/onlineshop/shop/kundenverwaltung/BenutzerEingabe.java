/**
 * Diese Klasse ist f�r die Benutzereingabe da.
 * Wenn die Nutzereingabe f�r Ziffern genutzt wird,
 * wird der R�ckgabewert der getUsrInput Methode geutzt.
 * Falls auch Strings vom Benutzer verarbeitet werden m�ssen,
 * wird der String messageString genutzt.
 * 
 * @author Kaleb Muench
 * 
 */

package com.muench.kaleb.onlineshop.shop.kundenverwaltung;


import java.util.NoSuchElementException;
import java.util.Scanner;

import com.muench.kaleb.onlineshop.shop.Shop;

public class BenutzerEingabe {

	private String messageString;
	
	//Die chars die nicht in der Benutzereingabe vorkommen d�rfen.
	private static final char[] verbZeichen1 = { ' ', '.', ',', ';', ':', '(', ')', '!', '?', '"', '+', '*', '=', '&', '{', '[',
			']', '}', '<', '>', '/', '#', '\'', '\\', '@', '�', '_', '|', '~', '`', '^', '�', '$', '%',
			'�' };
	private static final char[] verbZeichen2 = { ' ',',', ';', ':', '(', ')', '!', '?', '"', '+', '*', '=', '&', '{', '[',
			']', '}', '<', '>', '/', '#', '\'', '\\', '�', '|', '~', '`', '^', '�', '$', '%',
			'�' };	
	
	/**
	 * ### getUsrInput ###
	 * 
	 * Nimmt den User-Input.
	 * 
	 * Wenn der Input ung�ltig ist wird Integer.MAX_VALUE zur�ckgegeben und
	 * "wiederholung" auf true gesetzt. Wenn nicht: Wird der UserInput zur�ckgegeben
	 * und "wiederholung" auf false gesetzt.
	 * 
	 * Je nachdem wo die getUsrInput Methode genutzt wird, wird eine andere
	 * If-Anweisung durchgef�hrt.
	 * 
	 * 0: Der R�ckgabewert ist zwischen [0,9], ung�ltige Benutzereingabe gibt
	 * Integer.MAX_VALUE zur�ck.
	 * 
	 * 1: Der R�ckgabewert ist zwischen [Integer.MIN_VALUE,
	 * Integer.MAX_VALUE], ung�ltige Benutzereingabe gibt Integer.MAX_VALUE zur�ck. 
	 * 
	 * 2: Der R�ckgabewert ist entweder [0,9] oder
	 * Integer.MIN_VALUE f�r g�ltiger String, oder Integer.MAX_VALUE f�r ung�ltige
	 * Benutzereingabe.
	 * G�ltige Benutzereingabe die nicht 0 bis 9 ist gibt Integer.MIN_VALUE zur�ck
	 * und der Variable messageString wird die Benutzereingabe �bergeben.
	 * 
	 * 3: Wie Login/Registrierung, au�er das ein anderes char[]
	 * zur �berpr�fung auf ung�ltige chars genutzt wird.
	 * 
	 * @param modus Unterschiedliche Modi
	 * 
	 * @return int
	 */
	public int getUsrInput(int modus) {
		Scanner sc = new Scanner(System.in);

		// Im Startmen�
		if (modus == 0) {
			int userInput;
			try {
				userInput = sc.nextInt();
				System.out.println();
				if (userInput > 9 || userInput < 0) {
					Shop.setWiederholung(true);
					return Integer.MAX_VALUE;
				} else {
					Shop.setWiederholung(false);
					return userInput;
				}
			} catch (NoSuchElementException e) {
				System.out.println();
				Shop.setWiederholung(true);
				return Integer.MAX_VALUE;
			}

		}
		// Im Warenkorb
		else if (modus == 1) {
			int userInput;
			try {
				userInput = sc.nextInt();
				System.out.println();
				Shop.setWiederholung(false);
				return userInput;
			} catch (NoSuchElementException e) {
				System.out.println();
				Shop.setWiederholung(true);
				return Integer.MAX_VALUE;
			}

		}
		// Beim Einloggen oder Registrieren
		// Oder beim erstellen der Zahlungsmethode
		else if (modus == 2 || modus == 3) {
			StringBuilder userInputString = new StringBuilder();
			
			userInputString.append(sc.nextLine());
			
			try {
				/*
				 * Da beim eingeben des Datums auch mal eine f�hrende 0 vorkommen kann,
				 * wird hier erst die Benutzereingabe in einem String gespeichert und dann
				 * wird �berpr�ft ob es sich um eine Zahl handelt oder nicht.
				 * Wenn es eine Zahl ist die keine f�hrende Null hat und kleiner 10 und gr��er -1 ist,
				 * wird die erste if Anweisung benutzt. Ansonsten wird die Zahl messageString �bergeben und 
				 * Integer.MIN_VALUE zur�ckgegeben.
				 */
				int userInput = Integer.valueOf(userInputString.toString());
				if (userInput < 10 && userInput > -1 && userInputString.charAt(0) != '0') {
					System.out.println();
					Shop.setWiederholung(false);
					messageString = "";
					return userInput;
				} else {
					messageString = userInputString.toString();
					Shop.setWiederholung(false);
					return Integer.MIN_VALUE;
				}
			} catch (NumberFormatException e) {
				char[] verbZeichen;
				
				// Je nachdem wo die Methode getUsrInput genutzt wird, werden die Benutzereingaben
				// mit unterschiedlichen chars verglichen.
				if(modus == 2) {
					verbZeichen = verbZeichen1;				
				}else {
					verbZeichen = verbZeichen2;					
				}

				System.out.println();
				for (char c : verbZeichen) {
					for (int i = 0; i < userInputString.length(); i++) {
						if (userInputString.charAt(i) == c) {
							//Enth�lt die Benutzereingabe einen char welcher ung�ltig ist
							//Wird dieser char in messageString zur�ck gegeben und 
							//der return ist Integer.MAX_VALUE f�r ung�ltige Benutzereingabe
							messageString = String.valueOf(c);
							Shop.setWiederholung(true);
							return Integer.MAX_VALUE;
						}
					}
				}
				messageString = userInputString.toString();
				Shop.setWiederholung(false);
				return Integer.MIN_VALUE;

			}
		}
		return Integer.MAX_VALUE;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	
}
