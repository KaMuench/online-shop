/**
 * 
 * @author B�nyamin Berber
 */

package com.muench.kaleb.onlineshop.entities.zahlungsmethoden;

public class Ueberweisung extends Zahlungsmethode {

	/*
	 * ### Konstruktor ###
	 */
	public Ueberweisung(String zahlungsAdresse) {
		super(zahlungsAdresse);
		super.Name = "Konto";
	}
	
	@Override
	public String speichernInTxt() {
		return Name + ";" + zahlungsAdresse + ";";
	}
	
	/*
	 * ### toString ###
	 */
	@Override
	public String toString() {
		return String.format("%-7s%s", Name, zahlungsAdresse);
	}
	
	/*
	 * ### Getter und Setter ###
	 */
	@Override
	public String getName() {
		return Name;
	}

}
