package application;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Funkcije;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class PoslovniKorisnikUnosController {

	@FXML
	TextField nazivTextField;
	@FXML
	TextField emailTextField;
	@FXML
	TextField webTextField;
	@FXML
	TextField telefonTextField;

	public void initialize() {
		;
	}

	public void unesi() {
		String content = new String();
		if (nazivTextField.getText().trim().isEmpty()) {
			content += "Ime je obavezan podatak!\n";
		}
		if (emailTextField.getText().trim().isEmpty()) {
			content += "E-mail je obavezan podatak!\n";
		}
		if (webTextField.getText().trim().isEmpty()) {
			content += "Prezime je obavezan podatak!\n";
		}
		if (telefonTextField.getText().trim().isEmpty()) {
			content += "Telefon je obavezan podatak!\n";
		}
		if (!content.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, content);
			alert.setHeaderText(null);
			alert.show();
			return;
		}

		String naziv = nazivTextField.getText();
		String email = emailTextField.getText();
		String web = webTextField.getText();
		String telefon = telefonTextField.getText();

		PoslovniKorisnik korisnik = new PoslovniKorisnik(null, naziv, email, web, telefon);

		try {
			BazaPodataka.pohraniPoslovnogKorisnika(korisnik);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		Alert alert = new Alert(AlertType.INFORMATION, "Podaci uspješno uneseni!");
		alert.setHeaderText(null);
		alert.show();
	}

}
