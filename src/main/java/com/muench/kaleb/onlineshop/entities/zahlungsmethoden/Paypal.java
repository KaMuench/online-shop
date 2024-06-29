/**
 * 
 * @author B�nyamin Berber
 */

package com.muench.kaleb.onlineshop.entities.zahlungsmethoden;

public class Paypal extends Zahlungsmethode{
	
	/*
	 * ### Konstruktor ###
	 */
	public Paypal(String zahlungsAdresse) {
		super(zahlungsAdresse);
		super.Name = "Paypal";
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
		return String.format("%-14s%s", Name, zahlungsAdresse);
	}
	
	/*
	 * ### Getter und Setter ###
	 */
	@Override
	public String getName() {
		return Name;
	}
	
	
	

}
