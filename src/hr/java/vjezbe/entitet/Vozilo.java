package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.math.RoundingMode;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Predstavlja sucelje koristeno za interakciju s vozilima
 * 
 * @author Kristijan
 *
 */
public interface Vozilo {
	/**
	 * Pretvara snagu auto iz konjskih snaga u kilovate
	 * 
	 * @param konjskaSnaga Podatak o snazi auto u konjskim snagama
	 * @return Vraca podatak o snazi auto u kilovatima
	 */
	default BigDecimal izracunajKw(BigDecimal konjskaSnaga) {
		return konjskaSnaga.divide(new BigDecimal(1.36), 3, RoundingMode.HALF_UP);
	}

	/**
	 * Izracunava cijenu osiguranja auta s obzirom na grupu vozila
	 * 
	 * @return Vraca cijenu osiguranja vozila
	 * @throws NemoguceOdreditiGrupuOsiguranjaException ako je nemoguce odrediti
	 *                                                  grupu osiguranja vozila
	 */
	default BigDecimal izracunajCijenuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException {
		BigDecimal grupaOsiguranja = izracunajGrupuOsiguranja();
		BigDecimal cijenaOsiguranja;
		switch (grupaOsiguranja.intValue()) {
		case 1:
			cijenaOsiguranja = new BigDecimal(1000);
			break;

		case 2:
			cijenaOsiguranja = new BigDecimal(1500);
			break;

		case 3:
			cijenaOsiguranja = new BigDecimal(2000);
			break;

		case 4:
			cijenaOsiguranja = new BigDecimal(2500);
			break;

		case 5:
			cijenaOsiguranja = new BigDecimal(3000);
			break;

		default:
			cijenaOsiguranja = new BigDecimal(0);
			break;
		}
		return cijenaOsiguranja;
	}

	/**
	 * Izracunava grupu osiguranja auto na temelju snage vozila u kilovatima
	 * 
	 * @return Vraca grupu osiguranja vozila
	 * @throws NemoguceOdreditiGrupuOsiguranjaException ako vozilo ima previse
	 *                                                  kilovata
	 */
	BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException;
}
