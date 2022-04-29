package hr.java.vjezbe.entitet;

import java.time.LocalDate;

/**
 * Entitet klase Prodaja koji predstavlja artikl na prodaji
 * 
 * @author Kristijan
 *
 */
public class Prodaja extends Entitet {
	private static final long serialVersionUID = 8104436595815366922L;

	private Artikl artikl;
	private Korisnik korisnik;
	private LocalDate datumObjave;

	/**
	 * Stvara objekt klase Prodaja kojem su inicijalizirani artikl, koristik i datum
	 * objave
	 * 
	 * @param artikl      Podatak o artiklu
	 * @param korisnik    Podatak o korisniku
	 * @param datumObjave Podatak o datumu objave
	 */
	public Prodaja(Artikl artikl, Korisnik korisnik, LocalDate datumObjave) {
		super(null);
		this.artikl = artikl;
		this.korisnik = korisnik;
		this.datumObjave = datumObjave;
	}

	public Artikl getArtikl() {
		return artikl;
	}

	public void setArtikl(Artikl artikl) {
		this.artikl = artikl;
	}

	public Korisnik getKorisnik() {
		return korisnik;
	}

	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}

	public LocalDate getDatumObjave() {
		return datumObjave;
	}

	public void setDatumObjave(LocalDate datumObjave) {
		this.datumObjave = datumObjave;
	}

	@Override
	public String toString() {
		return "Oglas: " + artikl + "\nProdavatelj: " + korisnik + "\nDatum objave: " + datumObjave;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((artikl == null) ? 0 : artikl.hashCode());
		result = prime * result + ((datumObjave == null) ? 0 : datumObjave.hashCode());
		result = prime * result + ((korisnik == null) ? 0 : korisnik.hashCode());
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
		Prodaja other = (Prodaja) obj;
		if (artikl == null) {
			if (other.artikl != null)
				return false;
		} else if (!artikl.equals(other.artikl))
			return false;
		if (datumObjave == null) {
			if (other.datumObjave != null)
				return false;
		} else if (!datumObjave.equals(other.datumObjave))
			return false;
		if (korisnik == null) {
			if (other.korisnik != null)
				return false;
		} else if (!korisnik.equals(other.korisnik))
			return false;
		return true;
	}
}
