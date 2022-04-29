package hr.java.vjezbe.iznimke;

public class BazaPodatakaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3764446744595660790L;

	public BazaPodatakaException() {
	}

	public BazaPodatakaException(String message) {
		super(message);
	}

	public BazaPodatakaException(Throwable cause) {
		super(cause);
	}

	public BazaPodatakaException(String message, Throwable cause) {
		super(message, cause);
	}

	public BazaPodatakaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
