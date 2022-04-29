package hr.java.vjezbe.entitet;

/**
 * Entitet klase PoslovniKorisnik koji predstavlja tvrtku
 * 
 * @author Kristijan
 *
 */
public class PoslovniKorisnik extends Korisnik {
	private static final long serialVersionUID = -3213650871775617772L;

	private String naziv;
	private String web;

	/**
	 * Stvara objekt klase PoslovniKorisnik kojem su inicijalizirani naziv, email,
	 * web i telefon
	 * 
	 * @param id      Podatak o id-u
	 * @param naziv   Podatak o nazivu
	 * @param email   Podatak o email-u
	 * @param web     Podatak o web stranici
	 * @param telefon Podatak o broju telefona
	 */
	public PoslovniKorisnik(Long id, String naziv, String email, String web, String telefon) {
		super(id, email, telefon);
		this.naziv = naziv;
		this.web = web;
	}

	@Override
	public String dohvatiKontakt() {
		return String.format("Naziv tvrtke: %s, mail: %s, tel: %s, web: %s", naziv, email, telefon, web);
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	@Override
	public String toString() {
		return naziv +  ", email:" + email + ", web:" + web +", telefon:" + telefon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((naziv == null) ? 0 : naziv.hashCode());
		result = prime * result + ((web == null) ? 0 : web.hashCode());
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
		PoslovniKorisnik other = (PoslovniKorisnik) obj;
		if (naziv == null) {
			if (other.naziv != null)
				return false;
		} else if (!naziv.equals(other.naziv))
			return false;
		if (web == null) {
			if (other.web != null)
				return false;
		} else if (!web.equals(other.web))
			return false;
		return true;
	}

}
