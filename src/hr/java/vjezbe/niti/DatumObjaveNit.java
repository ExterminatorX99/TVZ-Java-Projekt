package hr.java.vjezbe.niti;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Funkcije;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DatumObjaveNit implements Runnable {

	@Override
	public void run() {
		try {
			Prodaja prodaja = BazaPodataka.dohvatiZadnjuProdaju();

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setContentText(prodaja.toString());
			alert.show();
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
		}
	}

}
