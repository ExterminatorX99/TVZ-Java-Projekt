package application;

import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.PrivatniKorisnik;
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

public class PrivatniKorisnikController {

	@FXML
	private TextField imeTextField;
	@FXML
	private TextField prezimeTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField telefonTextField;
	@FXML
	private Button searchButton;
	@FXML
	private TableView<PrivatniKorisnik> table;
	@FXML
	private TableColumn<PrivatniKorisnik, String> colIme;
	@FXML
	private TableColumn<PrivatniKorisnik, String> colPrezime;
	@FXML
	private TableColumn<PrivatniKorisnik, String> colEmail;
	@FXML
	private TableColumn<PrivatniKorisnik, String> colTelefon;
	@FXML
	private CheckBox toggleCheckBox;

	public void initialize() {
		setFactories();
		List<PrivatniKorisnik> korisnici;

		try {
			korisnici = BazaPodataka.dohvatiPrivatnogKorisnikaPremaKriterijima(null).stream()
					.filter(p -> p instanceof PrivatniKorisnik).collect(Collectors.toList());
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		ObservableList<PrivatniKorisnik> observableList = FXCollections.observableList(korisnici);
		table.setItems(observableList);
	}

	public void search(Event event) {
		if (!toggleCheckBox.isSelected() && event instanceof KeyEvent) {
			return;
		}

		PrivatniKorisnik korisnik = new PrivatniKorisnik(null, imeTextField.getText(), prezimeTextField.getText(),
				emailTextField.getText(), telefonTextField.getText());
		List<PrivatniKorisnik> korisnici;

		try {
			korisnici = BazaPodataka.dohvatiPrivatnogKorisnikaPremaKriterijima(korisnik);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		ObservableList<PrivatniKorisnik> observableList = FXCollections.observableList(korisnici);
		table.setItems(observableList);
	}

	private void setFactories() {
		colIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
		colPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
	}

}
