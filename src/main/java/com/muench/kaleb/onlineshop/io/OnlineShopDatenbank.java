/**
 * Diese Klasse dient zum Speichern und Laden des Onlineshops.
 * 
 * @author Kaleb Muench
 */
package com.muench.kaleb.onlineshop.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import com.muench.kaleb.onlineshop.entities.zahlungsmethoden.*;
import com.muench.kaleb.onlineshop.entities.kunden.*;
import com.muench.kaleb.onlineshop.entities.produkt.*;
import com.muench.kaleb.onlineshop.entities.bestellung.*;

import com.muench.kaleb.onlineshop.shop.kundenverwaltung.Kundenverwaltung;
import com.muench.kaleb.onlineshop.shop.Katalog;

public class OnlineShopDatenbank {

	public static Katalog ladeKatalog() {
		Katalog retKatalog = new Katalog();

		try (BufferedReader dateiLeser = new BufferedReader(new FileReader("saves/produktListe.txt"))) {
			String zeile;
			while ((zeile = dateiLeser.readLine()) != null) {
				String[] datenProdukt = zeile.split(";");
				if (datenProdukt[0].equals("Computer")) {
					Computer k = new Computer(datenProdukt[1], datenProdukt[2], Double.parseDouble(datenProdukt[3]),
							Integer.parseInt(datenProdukt[4]), Byte.parseByte(datenProdukt[5]),
							Short.parseShort(datenProdukt[6]), datenProdukt[7], datenProdukt[8],
							Boolean.parseBoolean(datenProdukt[9]), Boolean.parseBoolean(datenProdukt[10]),
							datenProdukt[11]);
					retKatalog.getKatalog().put(k.getArtikelnummer(), k);
				} else if (datenProdukt[0].equals("Notebook")) {
					Notebook k = new Notebook(datenProdukt[1], datenProdukt[2], Double.parseDouble(datenProdukt[3]),
							Integer.parseInt(datenProdukt[4]), Byte.parseByte(datenProdukt[5]),
							Short.parseShort(datenProdukt[6]), datenProdukt[7], datenProdukt[8],
							Boolean.parseBoolean(datenProdukt[9]), Boolean.parseBoolean(datenProdukt[10]),
							datenProdukt[11]);
					retKatalog.getKatalog().put(k.getArtikelnummer(), k);
				} else if (datenProdukt[0].equals("Smartphone")) {
					Smartphone k = new Smartphone(datenProdukt[1], datenProdukt[2], Double.parseDouble(datenProdukt[3]),
							Integer.parseInt(datenProdukt[4]), Byte.parseByte(datenProdukt[5]),
							Short.parseShort(datenProdukt[6]), datenProdukt[7], datenProdukt[8],
							Boolean.parseBoolean(datenProdukt[9]), Boolean.parseBoolean(datenProdukt[10]),
							datenProdukt[11]);
					retKatalog.getKatalog().put(k.getArtikelnummer(), k);
				} else if (datenProdukt[0].equals("Maus")) {
					Maus k = new Maus(datenProdukt[1], datenProdukt[2], Double.parseDouble(datenProdukt[3]),
							Integer.parseInt(datenProdukt[4]), datenProdukt[5], datenProdukt[6],
							Byte.parseByte(datenProdukt[7]));
					retKatalog.getKatalog().put(k.getArtikelnummer(), k);
				} else if (datenProdukt[0].equals("Tastatur")) {
					Tastatur k = new Tastatur(datenProdukt[1], datenProdukt[2], Double.parseDouble(datenProdukt[3]),
							Integer.parseInt(datenProdukt[4]), datenProdukt[5], datenProdukt[6]);
					retKatalog.getKatalog().put(k.getArtikelnummer(), k);
				} else if (datenProdukt[0].equals("Display")) {
					Display k = new Display(datenProdukt[1], datenProdukt[2], Double.parseDouble(datenProdukt[3]),
							Integer.parseInt(datenProdukt[4]), datenProdukt[5], datenProdukt[6], datenProdukt[7]);
					retKatalog.getKatalog().put(k.getArtikelnummer(), k);
				}
			}
			return retKatalog;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static ArrayList<Bestellungen> ladeBestellungen(Kunde kunde) {
		ArrayList<Bestellungen> retArrayList = new ArrayList<>();

		try (BufferedReader dateiLeser = new BufferedReader(
				new FileReader(new File("saves/BestellungenKunden/bestellungen" + kunde.getKennNummer() + ".txt")))) {
			String zeile;
			ArrayList<Produkt> retProdukte = new ArrayList<>();
			while ((zeile = dateiLeser.readLine()) != null) {
				String[] datenBestellung = zeile.split(";");
				if (datenBestellung[0].equals("Bestellung")) {
					Produkt[] pArray = new Produkt[retProdukte.size()];
					for (int i = 0; i < retProdukte.size(); i++) {
						pArray[i] = retProdukte.get(i);
					}
					Bestellungen retBestellungen = new Bestellungen(pArray, Boolean.parseBoolean(datenBestellung[1]),
							new ZeitStempel(Integer.parseInt(datenBestellung[2]), Integer.parseInt(datenBestellung[3]),
									Integer.parseInt(datenBestellung[4]), Integer.parseInt(datenBestellung[5]),
									Integer.parseInt(datenBestellung[6]), Integer.parseInt(datenBestellung[7])));
					retArrayList.add(retBestellungen);
				} else if (datenBestellung[0].equals("Computer")) {
					Computer k = new Computer(datenBestellung[1], datenBestellung[2],
							Double.parseDouble(datenBestellung[3]), Integer.parseInt(datenBestellung[4]),
							Byte.parseByte(datenBestellung[5]), Short.parseShort(datenBestellung[6]),
							datenBestellung[7], datenBestellung[8], Boolean.parseBoolean(datenBestellung[9]),
							Boolean.parseBoolean(datenBestellung[10]), datenBestellung[11]);
					retProdukte.add(k);
				} else if (datenBestellung[0].equals("Notebook")) {
					Notebook k = new Notebook(datenBestellung[1], datenBestellung[2],
							Double.parseDouble(datenBestellung[3]), Integer.parseInt(datenBestellung[4]),
							Byte.parseByte(datenBestellung[5]), Short.parseShort(datenBestellung[6]),
							datenBestellung[7], datenBestellung[8], Boolean.parseBoolean(datenBestellung[9]),
							Boolean.parseBoolean(datenBestellung[10]), datenBestellung[11]);
					retProdukte.add(k);
				} else if (datenBestellung[0].equals("Smartphone")) {
					Smartphone k = new Smartphone(datenBestellung[1], datenBestellung[2],
							Double.parseDouble(datenBestellung[3]), Integer.parseInt(datenBestellung[4]),
							Byte.parseByte(datenBestellung[5]), Short.parseShort(datenBestellung[6]),
							datenBestellung[7], datenBestellung[8], Boolean.parseBoolean(datenBestellung[9]),
							Boolean.parseBoolean(datenBestellung[10]), datenBestellung[11]);
					retProdukte.add(k);
				} else if (datenBestellung[0].equals("Maus")) {
					Maus k = new Maus(datenBestellung[1], datenBestellung[2], Double.parseDouble(datenBestellung[3]),
							Integer.parseInt(datenBestellung[4]), datenBestellung[5], datenBestellung[6],
							Byte.parseByte(datenBestellung[7]));
					retProdukte.add(k);
				} else if (datenBestellung[0].equals("Tastatur")) {
					Tastatur k = new Tastatur(datenBestellung[1], datenBestellung[2],
							Double.parseDouble(datenBestellung[3]), Integer.parseInt(datenBestellung[4]),
							datenBestellung[5], datenBestellung[6]);
					retProdukte.add(k);
				} else if (datenBestellung[0].equals("Display")) {
					Display k = new Display(datenBestellung[1], datenBestellung[2],
							Double.parseDouble(datenBestellung[3]), Integer.parseInt(datenBestellung[4]),
							datenBestellung[5], datenBestellung[6], datenBestellung[7]);
					retProdukte.add(k);
				}
			}
			return retArrayList;
		} catch (IOException e) {
			e.printStackTrace();
			return retArrayList;
		}

	}

	public static TreeMap<Integer, Kunde> ladeKundenliste() {
		TreeMap<Integer, Kunde> retTreeMap = new TreeMap<>();

		try (BufferedReader dateiLeser = new BufferedReader(new FileReader(new File("saves/kundenListe.txt")))) {
			String zeile;
			while ((zeile = dateiLeser.readLine()) != null) {
				String[] datenKunde = zeile.split(";");
				// Kunde erstellen
				Kunde kunde = new Kunde(datenKunde[0], datenKunde[1], datenKunde[2],
						new Adresse(datenKunde[3], datenKunde[4], datenKunde[5]), datenKunde[6], datenKunde[7]);

				// Abo hinzuf�gen
				if (datenKunde[8].equals("false")) {
					kunde.setAbo(new Abo());
				} else if (datenKunde[8].equals("true")) {
					kunde.setAbo(new Abo(true));
				}
				// Kunde Bestellverlauf laden
				kunde.setBestellVerlauf(ladeBestellungen(kunde));
				kunde.setZahlungsmethoden(ladeZahlungsmethoden(kunde));
				// Kunde Kundenliste hinzuf�gen
				retTreeMap.put(kunde.getKennNummer(), kunde);
			}

			return retTreeMap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static Zahlungsmethode[] ladeZahlungsmethoden(Kunde kunde) {
		Zahlungsmethode[] retZahlungsmethode = new Zahlungsmethode[5];

		try (BufferedReader dateiLeser = new BufferedReader(new FileReader(
				new File("saves/ZahlungsmethodenKunden/zahlungsmethoden" + kunde.getKennNummer() + ".txt")))) {
			String zeile;
			byte index = 0;
			while ((zeile = dateiLeser.readLine()) != null) {
				String[] datenZahlungsM = zeile.split(";");
				if (datenZahlungsM[0].equals("Paypal")) {
					retZahlungsmethode[index] = new Paypal(datenZahlungsM[1]);
				} else if (datenZahlungsM[0].equals("Konto")) {
					retZahlungsmethode[index] = new Ueberweisung(datenZahlungsM[1]);
				} else {
					retZahlungsmethode[index] = null;
				}
				index++;
			}
			return retZahlungsmethode;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void speichernKatalog(Katalog katalog) {

		try (BufferedWriter dateiSchreiber = new BufferedWriter(
				new FileWriter(new File("saves/produktListe.txt"), false))) {

			int keyComputer = Computer.getArtNumBereich() + 1;
			int keyNotebooks = Notebook.getARTNUMBEREICH() + 1;
			int keySmartphone = Smartphone.getArtnumbereich() + 1;
			int keyMaus = Maus.getArtnumbereich() + 1;
			int keyTastatur = Tastatur.getArtnumbereich() + 1;
			int keyDisplay = Display.getArtnumbereich() + 1;

			while (katalog.getKatalog().containsKey(keyComputer)) {
				dateiSchreiber.write(katalog.getKatalog().get(keyComputer).speichernInTxt() + "\n");
				keyComputer++;
			}
			while (katalog.getKatalog().containsKey(keyNotebooks)) {
				dateiSchreiber.write(katalog.getKatalog().get(keyNotebooks).speichernInTxt() + "\n");
				keyNotebooks++;
			}
			while (katalog.getKatalog().containsKey(keySmartphone)) {
				dateiSchreiber.write(katalog.getKatalog().get(keySmartphone).speichernInTxt() + "\n");
				keySmartphone++;
			}
			while (katalog.getKatalog().containsKey(keyMaus)) {
				dateiSchreiber.write(katalog.getKatalog().get(keyMaus).speichernInTxt() + "\n");
				keyMaus++;
			}
			while (katalog.getKatalog().containsKey(keyTastatur)) {
				dateiSchreiber.write(katalog.getKatalog().get(keyTastatur).speichernInTxt() + "\n");
				keyTastatur++;
			}
			while (katalog.getKatalog().containsKey(keyDisplay)) {
				dateiSchreiber.write(katalog.getKatalog().get(keyDisplay).speichernInTxt() + "\n");
				keyDisplay++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void speichernBestellungen(Kunde kunde) {
		try (BufferedWriter dateiSchreiber = new BufferedWriter(new FileWriter(
				new File("saves/BestellungenKunden/bestellungen" + kunde.getKennNummer() + ".txt"), false))) {
			for (Bestellungen b : kunde.getBestellVerlauf()) {
				for (Produkt p : b.getINHALT()) {
					dateiSchreiber.write(p.speichernInTxt() + "\n");
				}
				dateiSchreiber.write(b.speichernInTxt() + "\n");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void speichernKundenliste(Kundenverwaltung kundenverwaltung) {
		try (BufferedWriter dateiSchreiber = new BufferedWriter(
				new FileWriter(new File("saves/kundenListe.txt"), false))) {

			int keyKennNummerbereich = Kunde.getKennnummerbereich() + 1;

			while (kundenverwaltung.getKundenliste().containsKey(keyKennNummerbereich)) {
				speichernBestellungen(kundenverwaltung.getKundenliste().get(keyKennNummerbereich));
				speichernZahlungsmethoden(kundenverwaltung.getKundenliste().get(keyKennNummerbereich));
				dateiSchreiber
						.write(kundenverwaltung.getKundenliste().get(keyKennNummerbereich).speichernInTxt() + "\n");
				keyKennNummerbereich++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void speichernZahlungsmethoden(Kunde kunde) {
		try (BufferedWriter dateiSchreiber = new BufferedWriter(new FileWriter(
				new File("saves/ZahlungsmethodenKunden/zahlungsmethoden" + kunde.getKennNummer() + ".txt"), false))) {
			for (Zahlungsmethode z : kunde.getZahlungsmethode()) {
				if (z == null) {
					dateiSchreiber.write("null;\n");
				} else {
					dateiSchreiber.write(z.speichernInTxt() + "\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
