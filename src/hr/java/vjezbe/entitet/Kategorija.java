package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * Entitet klase Kategorija koja predstavlja kategoriju
 * 
 * @author Kristijan
 *
 */
public class Kategorija<T extends Artikl> extends Entitet {
	private static final long serialVersionUID = 4660770726891072541L;

	private String naziv;
	private List<T> artikli;

	/**
	 * Stvara objekt klase Kategorija kojem su inicijalizirani naziv i popis artikla
	 * 
	 * @param id    Podatak o id-u kategorije
	 * @param naziv Podatak o nazivu kategorije
	 */
	public Kategorija(Long id, String naziv) {
		super(id);
		this.naziv = naziv;
		this.artikli = new ArrayList<T>();
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public List<T> getArtikli() {
		return artikli;
	}

	public void setArtikli(List<T> artikli) {
		this.artikli = artikli;
	}

	public void dodajArtikl(T artikl) {
		artikli.add(artikl);
	}

	public T dohvatiArtikl(int index) {
		return artikli.get(index);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Kategorija<?> other = (Kategorija<?>) obj;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		return true;
	}
}
