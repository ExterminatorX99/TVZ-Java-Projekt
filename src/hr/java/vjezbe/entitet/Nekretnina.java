package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

/**
 * Predstavlja sucelje koristeno za interakciju s nekretninama
 * 
 * @author Kristijan
 *
 */
public interface Nekretnina {
	/**
	 * Izracunava porez na temelju cijene
	 * 
	 * @param cijena Podatak o cijeni nekretnine
	 * @return Vraca podatak o porezu
	 * @throws CijenaJePreniskaException ako je cijena nekretnine manja od 10000
	 */
	default BigDecimal izracunajPorez(BigDecimal cijena) {
		if (cijena.compareTo(new BigDecimal(10000)) < 0) {
			throw new CijenaJePreniskaException();
		}
		return cijena.multiply(new BigDecimal(0.03));
	}
}
