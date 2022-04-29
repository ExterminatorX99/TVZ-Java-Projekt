package hr.java.vjezbe.iznimke;

/**
 * Entitet klase CijenaJePreniskaException
 * 
 * @author Kristijan
 *
 */
public class CijenaJePreniskaException extends RuntimeException {

	private static final long serialVersionUID = -7966744782829041743L;

	/**
	 * Stvara novu iznimku sa predefiniranom porukom
	 */
	public CijenaJePreniskaException() {
		super("Cijena ne smije biti manja od 10000kn");
	}

	/**
	 * Stvara novu iznimku s detaljnom porukom
	 * 
	 * @param message Podatak o detaljnoj poruci o iznimci
	 */
	public CijenaJePreniskaException(String message) {
		super(message);
	}

	/**
	 * Stvara novu iznimku sa specificnim uzrokom
	 * 
	 * @param cause Podatak o uzroku iznimke
	 */
	public CijenaJePreniskaException(Throwable cause) {
		super(cause);
	}

	/**
	 * Stvara novu iznimku sa detaljnom porukom i specificnim uzrokom
	 * 
	 * @param message Podatak o detaljnoj poruci o iznimci
	 * @param cause   Podatak o uzroku iznimke
	 */
	public CijenaJePreniskaException(String message, Throwable cause) {
		super(message, cause);
	}

}
