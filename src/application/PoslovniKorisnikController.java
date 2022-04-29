package application;

import java.util.List;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PoslovniKorisnik;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Funkcije;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class PoslovniKorisnikController {

	@FXML
	private TextField nazivTextField;
	@FXML
	private TextField webTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField telefonTextField;
	@FXML
	private Button searchButton;
	@FXML
	private TableView<PoslovniKorisnik> table;
	@FXML
	private TableColumn<PoslovniKorisnik, String> colNaziv;
	@FXML
	private TableColumn<PoslovniKorisnik, String> colWeb;
	@FXML
	private TableColumn<PoslovniKorisnik, String> colEmail;
	@FXML
	private TableColumn<PoslovniKorisnik, String> colTelefon;
	@FXML
	private CheckBox toggleCheckBox;

	public void initialize() {
		setFactories();
		List<PoslovniKorisnik> korisnici;
		try {
			korisnici = BazaPodataka.dohvatiPoslovnogKorisnikaPremaKriterijima(null);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		ObservableList<PoslovniKorisnik> observableList = FXCollections.observableList(korisnici);
		table.setItems(observableList);
	}

	public void search(Event event) {
		if (!toggleCheckBox.isSelected() && event instanceof KeyEvent) {
			return;
		}

		PoslovniKorisnik korisnik = new PoslovniKorisnik(null, nazivTextField.getText(), emailTextField.getText(),
				webTextField.getText(), telefonTextField.getText());
		List<PoslovniKorisnik> korisnici;

		try {
			korisnici = BazaPodataka.dohvatiPoslovnogKorisnikaPremaKriterijima(korisnik);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		ObservableList<PoslovniKorisnik> observableList = FXCollections.observableList(korisnici);
		table.setItems(observableList);
	}

	private void setFactories() {
		colNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
		colWeb.setCellValueFactory(new PropertyValueFactory<>("web"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
	}

}
