package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Entitet klase Usluga koji predstavlja uslugu
 * 
 * @author Kristijan
 *
 */
public class Usluga extends Artikl {
	private static final long serialVersionUID = -4268975312557480638L;

	/**
	 * Stvara objekt klase Usluga kojem su inicijalizirani naslov, opis i cijena
	 * 
	 * @param id     Podatak o id-u
	 * @param naslov Podatak o naslovu
	 * @param opis   Podatak o opisu
	 * @param cijena Podatak o cijeni
	 * @param stanje Podatak o stanju
	 */
	public Usluga(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
	}

	@Override
	public String tekstOglasa() {
		return String.format("Naslov usluge: %s\n" + "Opis usluge: %s\n" + "Cijena usluge: %.0f", getNaslov(),
				getOpis(), getCijena().doubleValue());
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}

	@Override
	public String toString() {
		return naslov + ", " + opis + ", " + cijena + "kn";
	}

}
