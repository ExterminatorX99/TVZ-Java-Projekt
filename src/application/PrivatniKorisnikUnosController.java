package application;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Funkcije;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class PrivatniKorisnikUnosController {

	@FXML
	TextField imeTextField;
	@FXML
	TextField prezimeTextField;
	@FXML
	TextField emailTextField;
	@FXML
	TextField telefonTextField;

	public void initialize() {
		;
	}

	public void unesi() {
		String content = new String();
		if (imeTextField.getText().trim().isEmpty()) {
			content += "Ime je obavezan podatak!\n";
		}
		if (prezimeTextField.getText().trim().isEmpty()) {
			content += "Prezime je obavezan podatak!\n";
		}
		if (emailTextField.getText().trim().isEmpty()) {
			content += "E-mail je obavezan podatak!\n";
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

		String ime = imeTextField.getText();
		String prezime = prezimeTextField.getText();
		String email = emailTextField.getText();
		String telefon = telefonTextField.getText();

		PrivatniKorisnik korisnik = new PrivatniKorisnik(null, ime, prezime, email, telefon);

		try {
			BazaPodataka.pohraniPrivatnogKorisnika(korisnik);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		Alert alert = new Alert(AlertType.INFORMATION, "Podaci uspješno uneseni!");
		alert.setHeaderText(null);
		alert.show();
	}

}
