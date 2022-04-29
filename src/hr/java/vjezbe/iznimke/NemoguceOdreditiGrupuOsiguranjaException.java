package hr.java.vjezbe.iznimke;

/**
 * Entitet klase NemoguceOdreditiGrupuOsiguranjaException
 * 
 * @author Kristijan
 *
 */
public class NemoguceOdreditiGrupuOsiguranjaException extends Exception {

	private static final long serialVersionUID = 3468972437907945309L;

	/**
	 * Stvara novu iznimku sa predefiniranom porukom
	 */
	public NemoguceOdreditiGrupuOsiguranjaException() {
		super("Previše kw, ne mogu odrediti grupu osiguranja.");
	}

	/**
	 * Stvara novu iznimku sa detaljnom porukom
	 * 
	 * @param message Podatak o detaljnoj poruci o iznimci
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String message) {
		super(message);
	}

	/**
	 * Stvara novu iznimku sa specificnim uzrokom
	 * 
	 * @param cause Podatak o uzroku iznimke
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(Throwable cause) {
		super(cause);
	}

	/**
	 * Stvara novu iznimku sa detaljnom porukom i specificnim uzrokom
	 * 
	 * @param message Podatak o detaljnoj poruci o iznimci
	 * @param cause   Podatak o uzroku iznimke
	 */
	public NemoguceOdreditiGrupuOsiguranjaException(String message, Throwable cause) {
		super(message, cause);
	}

}
