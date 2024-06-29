/**
 * ### interface: AusgabeLayout   ###
 * 
 * Dieses Interface ist daf�r da, dass die Objekte die es implementieren, 
 * in einem bestimmtem Layout Format , als Strings, ausgegeben werden k�nnen.
 * Dieses Layout-Format wird von der Klasse Design vorgegeben.
 * 
 * @author Kaleb Muench
 * 
 */
package com.muench.kaleb.onlineshop.ui.design;

public interface AusgabenLayout {

	
	public String toDesignFormat();
	/* 
	 * Die Implementation muss so erfolgen, dass der R�ckgabe String folgenderma�en aussieht:
	 * 
	 * F�r	Geraet:
	 * Name;prodName;Marke;marke;Artikelnummer;artNum;RAM;ram;Fesplattenspeicher;festplSpeicher;CPU;cpu;GPU;gpu;Laufwerk;laufwerk;Kamera;kamera;Displaygr��e;displaygrosse;Preis;preis;Auf Lager;anzahl
	 * 
	 * F�r Zubehoer:
	 * 		Tastatur:	Name;prodName;Marke;marke;Artikelnummer;artNum;Anschluss;signal;Layout;belegung;Preis;preis;Auf Lager;anzahl
	 * 		Maus:		Name;prodName;Marke;marke;Artikelnummer;artNum;Anschluss;signal;Aufl�sung;aufloesung;Preis;preis;Auf Lager;anzahl
	 * 		Display:	Name;prodName;Marke;marke;Artikelnummer;artNum;Gr��e;groesse;Reaktionszeit;reaktionszeit;Preis;preis;Auf Lager;anzahl
	 * 	
	 * F�r Kunden:
	 * 		Kunde: 			Name;name;Vorname;vorname;Benutzernummer;benutzernummer;Kundennummer;kennNummer;Strasse;strasse;Plz;plz;Ort;ort;Geburtstag;geburtstag,Zahlungsmethode;zahlungsmethode;Abo;abo;
	 * 		
	 */
}
