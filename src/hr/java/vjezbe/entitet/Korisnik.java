package hr.java.vjezbe.entitet;

/**
 * Entitet klase Korisnik koji sadrzi podatke o korisniku
 * 
 * @author Kristijan
 *
 */
abstract public class Korisnik extends Entitet {
	private static final long serialVersionUID = 2817314347483451116L;

	protected String email;
	protected String telefon;

	/**
	 * Stvara objekt klase Korisnik kojem su inicijalizirani email-om i telefonom
	 * 
	 * @param id      Podatak o id-u korisniku
	 * @param email   Podatak o email-u korisnika
	 * @param telefon Podatak o broju telefona korisnika
	 */
	public Korisnik(Long id, String email, String telefon) {
		super(id);
		this.email = email;
		this.telefon = telefon;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	/**
	 * Vraca podatke o korisniku u formatiranom obliku
	 * 
	 * @return String koji sadrzi podatke o Korisniku
	 */
	abstract public String dohvatiKontakt();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((telefon == null) ? 0 : telefon.hashCode());
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
		Korisnik other = (Korisnik) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (telefon == null) {
			if (other.telefon != null)
				return false;
		} else if (!telefon.equals(other.telefon))
			return false;
		return true;
	}
}
