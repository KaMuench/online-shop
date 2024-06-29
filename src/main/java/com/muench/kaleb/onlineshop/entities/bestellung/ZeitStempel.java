/**
 * 
 * @author B�nyamin Berber
 */

package com.muench.kaleb.onlineshop.entities.bestellung;

import java.util.Calendar;

import com.muench.kaleb.onlineshop.io.OnlineShopIO;

public class ZeitStempel implements OnlineShopIO {


	/*
	 * ### Variablen ###
	 */
	private final int MINUTE;
	private final int STUNDE;
	private final int JAHR;
	private final int MONAT;
	private final int WOCHENTAG;
	private final int TAG;
	private static final String[] WOCHENTAGE = { "Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"
			};
	private static final String[] MONATE = { "Januar", "Februar", "M�rz", "April", "Mai", "Juni", "Juli", "August",
			"September", "Oktober", "November", "Dezember" };

	/*
	 * ### Konstruktor ###
	 */
	// wird aufgerufen sobald eine Kauf abgeschlossen ist
	public ZeitStempel(int minute, int stunde, int wochentag, int monat, int jahr, int tag) {
		this.MINUTE = minute;
		this.STUNDE = stunde;
		this.WOCHENTAG = wochentag;
		this.MONAT = monat;
		this.JAHR = jahr;
		this.TAG = tag;
	}
	public ZeitStempel() {
		Calendar KALENDER = Calendar.getInstance();
		this.MINUTE = KALENDER.get(Calendar.MINUTE);
		this.STUNDE = KALENDER.get(Calendar.HOUR_OF_DAY);
		this.WOCHENTAG = KALENDER.get(Calendar.DAY_OF_WEEK);
		this.MONAT = KALENDER.get(Calendar.MONTH);
		this.JAHR = KALENDER.get(Calendar.YEAR);
		this.TAG = KALENDER.get(Calendar.DAY_OF_MONTH);
	}
		@Override
		public String speichernInTxt() {
			return MINUTE + ";" + STUNDE + ";" + WOCHENTAG + ";" + MONAT + ";" + JAHR + ";" + TAG + ";";
		}

	public String getWochentage(int tag) {
		return WOCHENTAGE[tag - 1];
	}

	public String getMonate(int monat) {
		// Da Monate im Calender Modul bei 0 beginnen, wird hier kein -1 f�r den Index
		// abgezogen.
		return MONATE[monat];
	}

	/*
	 * ### toString ###
	 */
	@Override
	public String toString() {
		if (MINUTE < 10) {
			return String.format("%-11sder %-3d%-10s%d um%3d:0%d Uhr", getWochentage(WOCHENTAG), TAG,
					getMonate(MONAT), JAHR, STUNDE, MINUTE);
		} else {
			return String.format("%-11sder %-3d%-10s%d um%3d:%d Uhr", getWochentage(WOCHENTAG), TAG, getMonate(MONAT),
					JAHR, STUNDE, MINUTE);
		}

	}

	/*
	 *  ### Getter und Setter ###
	 */

	public static String[] getWochentage() {
		return WOCHENTAGE;
	}

	public static String[] getMonate() {
		return MONATE;
	}


}
