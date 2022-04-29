package application;

import java.time.LocalDate;
import java.util.List;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Funkcije;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class ProdajaUnosController {

	@FXML
	private ComboBox<Artikl> artikliComboBox;
	@FXML
	private ComboBox<Korisnik> korisniciComboBox;
	@FXML
	private DatePicker datumDatePicker;

	public void initialize() {
		List<Artikl> artikli;
		List<Korisnik> korisnici;

		try {
			artikli = BazaPodataka.dohvatiArtikle();
			korisnici = BazaPodataka.dohvatiKorisnike();
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		artikliComboBox.getItems().addAll(artikli);
		korisniciComboBox.getItems().addAll(korisnici);
	}

	public void unesi() {
		String content = new String();
		if (artikliComboBox.getValue() == null) {
			content += "Artikl je obavezan podatak!\n";
		}
		if (korisniciComboBox.getValue() == null) {
			content += "Korisnik je obavezan podatak!\n";
		}
		if (datumDatePicker.getValue() == null) {
			content += "Datum je obavezan podatak!\n";
		}
		if (!content.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, content);
			alert.setHeaderText(null);
			alert.show();
			return;
		}

		Artikl artikl = artikliComboBox.getValue();
		Korisnik korisnik = korisniciComboBox.getValue();
		LocalDate datum = datumDatePicker.getValue();

		Prodaja prodaja = new Prodaja(artikl, korisnik, datum);

		try {
			BazaPodataka.pohraniProdaju(prodaja);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		Alert alert = new Alert(AlertType.INFORMATION, "Podaci uspješno uneseni!");
		alert.setHeaderText(null);
		alert.show();
	}

}
