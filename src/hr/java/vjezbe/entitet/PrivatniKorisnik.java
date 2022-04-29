package hr.java.vjezbe.entitet;

/**
 * Entitet klase PrivatniKorisnik koji predstavlja fizicku osobu
 * 
 * @author Kristijan
 *
 */
public class PrivatniKorisnik extends Korisnik {
	private static final long serialVersionUID = 610165501757130613L;

	private String ime;
	private String prezime;

	/**
	 * Stvara objekt klase PrivatniKorisnik kojem su inicijalizirani ime, prezime,
	 * email i telefon
	 * 
	 * @param id      Podatak o id-u
	 * @param ime     Podatak o imenu
	 * @param prezime Podatak o prezimenu
	 * @param email   Podatak o email-u
	 * @param telefon Podatak o broju telefona
	 */
	public PrivatniKorisnik(Long id, String ime, String prezime, String email, String telefon) {
		super(id, email, telefon);
		this.ime = ime;
		this.prezime = prezime;
	}

	@Override
	public String dohvatiKontakt() {
		return String.format("Osobni podaci prodavatelja: %s %s, mail: %s, tel: %s", ime, prezime, email, telefon);
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	@Override
	public String toString() {
		return ime + ", " + prezime + ", email:" + email + ", telefon:" + telefon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((ime == null) ? 0 : ime.hashCode());
		result = prime * result + ((prezime == null) ? 0 : prezime.hashCode());
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
		PrivatniKorisnik other = (PrivatniKorisnik) obj;
		if (ime == null) {
			if (other.ime != null)
				return false;
		} else if (!ime.equals(other.ime))
			return false;
		if (prezime == null) {
			if (other.prezime != null)
				return false;
		} else if (!prezime.equals(other.prezime))
			return false;
		return true;
	}

}
