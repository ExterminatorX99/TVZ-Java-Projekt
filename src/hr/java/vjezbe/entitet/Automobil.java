package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.NemoguceOdreditiGrupuOsiguranjaException;

/**
 * Entitet klase Automobil koji predstvlja automobil
 * 
 * @author Kristijan
 *
 */
public class Automobil extends Artikl implements Vozilo {
	private static final long serialVersionUID = -8464187053191773712L;

	private static final Logger log = LoggerFactory.getLogger(Automobil.class);

	private BigDecimal snagaKs;

	/**
	 * Stvara objekt klase Automobil s inicijaliziranim naslovom, opisom, snagom i
	 * cijenom
	 * 
	 * @param id      Podatak o id-u auta
	 * @param naslov  Podatak o naslovu auta
	 * @param opis    Podatak o opisu auto
	 * @param snagaKs Podatak o snazi auta, u konjskim snagama
	 * @param cijena  Podatak o cijeni auta
	 * @param stanje  Podatak o stanju artikla
	 */
	public Automobil(Long id, String naslov, String opis, BigDecimal snagaKs, BigDecimal cijena, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
		this.snagaKs = snagaKs;
	}

	@Override
	public BigDecimal izracunajGrupuOsiguranja() throws NemoguceOdreditiGrupuOsiguranjaException {
		BigDecimal grupaOsiguranja;
		double kw = izracunajKw(snagaKs).doubleValue();
		if (kw < 70) {
			grupaOsiguranja = new BigDecimal(1);
		} else if (kw < 140) {
			grupaOsiguranja = new BigDecimal(2);
		} else if (kw < 210) {
			grupaOsiguranja = new BigDecimal(3);
		} else if (kw < 280) {
			grupaOsiguranja = new BigDecimal(4);
		} else if (kw < 350) {
			grupaOsiguranja = new BigDecimal(5);
		} else {
			throw new NemoguceOdreditiGrupuOsiguranjaException();
		}
		return grupaOsiguranja;
	}

	@Override
	public String tekstOglasa() {
		String cijenaOsiguranja;
		try {
			cijenaOsiguranja = String.format("%.0f", izracunajCijenuOsiguranja().doubleValue());
		} catch (NemoguceOdreditiGrupuOsiguranjaException e) {
			log.error("Pogre�ka prilikom odre�ivanja cijene osiguranja!", e);
			cijenaOsiguranja = "Previ�e kw, ne mogu odrediti grupu osiguranja.";
		}
		return String.format(
				"Naslov automobila: %s\n" + "Opis automobila: %s\n" + "Snaga automobila: %.0f\n"
						+ "Stanje automobila: %s\n" + "Izra�un osiguranja automobila: %s\n" + "Cijena automobila: %.0f",
				naslov, opis, snagaKs.doubleValue(), stanje, cijenaOsiguranja, cijena.doubleValue());
	}

	public BigDecimal getSnagaKs() {
		return snagaKs;
	}

	public void setSnagaKs(BigDecimal snagaKs) {
		this.snagaKs = snagaKs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((snagaKs == null) ? 0 : snagaKs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Automobil other = (Automobil) obj;
		if (snagaKs == null) {
			if (other.snagaKs != null)
				return false;
		} else if (!snagaKs.equals(other.snagaKs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return naslov + ", " + opis + ", snaga:" + snagaKs + ", cijena:" + cijena + "kn, stanje: " + stanje;
	}

}
