package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.iznimke.CijenaJePreniskaException;

/**
 * Entitet klase Stan koji predstavlja stan
 * 
 * @author Kristijan
 *
 */
public class Stan extends Artikl implements Nekretnina {
	private static final long serialVersionUID = 3077635458932633682L;

	private static final Logger log = LoggerFactory.getLogger(Stan.class);

	int kvadratura;

	/**
	 * Stvara objekt klase Stan kojem su inicijalizirani naslov, opis, kvadratura i
	 * cijena
	 * 
	 * @param id         Podatak o id-u
	 * @param naslov     Podatak o naslovu
	 * @param opis       Podatak o opisu
	 * @param kvadratura Podatak o kvadraturi
	 * @param cijena     Podatak o cijeni
	 * @param stanje     Podatak o stanju artikla
	 */
	public Stan(Long id, String naslov, String opis, int kvadratura, BigDecimal cijena, Stanje stanje) {
		super(id, naslov, opis, cijena, stanje);
		this.kvadratura = kvadratura;
	}

	@Override
	public String tekstOglasa() {
		String porez;
		try {
			porez = String.format("%.2f", izracunajPorez(cijena).doubleValue());
		} catch (CijenaJePreniskaException e) {
			log.error("Pogre�ka prilikom odre�ivanja iznosa poreza!", e);
			porez = "Cijena ne smije biti manja od 10000kn";
		}

		return String.format(
				"Naslov nekretnine: %s\n" + "Opis nekretnine: %s\n" + "Kvadratura nekretnine: %d\n"
						+ "Stanje nekretnine: %s\n" + "Porez na nekretnine: %s\n" + "Cijena nekretnine: %.0f",
				naslov, opis, kvadratura, stanje, porez, cijena.doubleValue());
	}

	public int getKvadratura() {
		return kvadratura;
	}

	public void setKvadratura(int kvadratura) {
		this.kvadratura = kvadratura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + kvadratura;
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
		Stan other = (Stan) obj;
		if (kvadratura != other.kvadratura)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return naslov + ", " + opis + ", " + kvadratura + "m2, " + cijena + "kn, stanje: " + stanje;
	}

}
