package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Entitet klase Artikl
 * 
 * @author Kristijan
 *
 */
abstract public class Artikl extends Entitet {
	private static final long serialVersionUID = -3344447163456344575L;

	protected String naslov;
	protected String opis;
	protected BigDecimal cijena;
	protected Stanje stanje;

	/**
	 * Stvara objekt klase Artikl sa inicijaliziranim naslovom, opisom i cijenom
	 * 
	 * @param id     Podatak o id-u artikla
	 * @param naslov Podatak o naslovu artikla
	 * @param opis   Podatak o opisu artikla
	 * @param cijena Podatak o cijeni artikla
	 * @param stanje Podatak o stanju artikla
	 */
	public Artikl(Long id, String naslov, String opis, BigDecimal cijena, Stanje stanje) {
		super(id);
		this.naslov = naslov;
		this.opis = opis;
		this.cijena = cijena;
		this.stanje = stanje;
	}

	/**
	 * Vraca podatke o Artiklu u formatiranom obliku
	 * 
	 * @return String koji sadrzi podatke o oglasu
	 */
	abstract public String tekstOglasa();

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public BigDecimal getCijena() {
		return cijena;
	}

	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}

	public Stanje getStanje() {
		return stanje;
	}

	public void setStanje(Stanje stanje) {
		this.stanje = stanje;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cijena == null) ? 0 : cijena.hashCode());
		result = prime * result + ((naslov == null) ? 0 : naslov.hashCode());
		result = prime * result + ((opis == null) ? 0 : opis.hashCode());
		result = prime * result + ((stanje == null) ? 0 : stanje.hashCode());
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
		Artikl other = (Artikl) obj;
		if (cijena == null) {
			if (other.cijena != null)
				return false;
		} else if (!cijena.equals(other.cijena))
			return false;
		if (naslov == null) {
			if (other.naslov != null)
				return false;
		} else if (!naslov.equals(other.naslov))
			return false;
		if (opis == null) {
			if (other.opis != null)
				return false;
		} else if (!opis.equals(other.opis))
			return false;
		if (stanje != other.stanje)
			return false;
		return true;
	}

}
