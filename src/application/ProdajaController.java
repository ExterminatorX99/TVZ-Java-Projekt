package application;

import java.time.LocalDate;
import java.util.List;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Korisnik;
import hr.java.vjezbe.entitet.Prodaja;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Funkcije;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdajaController {

	@FXML
	private ComboBox<Artikl> artikliComboBox;
	@FXML
	private ComboBox<Korisnik> korisniciComboBox;
	@FXML
	private DatePicker datumDatePicker;
	@FXML
	private Button searchButton;
	@FXML
	private TableView<Prodaja> table;
	@FXML
	private TableColumn<Artikl, String> colArtikl;
	@FXML
	private TableColumn<Korisnik, String> colKorisnik;
	@FXML
	private TableColumn<LocalDate, LocalDate> colDatum;
	@FXML
	private CheckBox toggleCheckBox;

	public void initialize() {
		setFactories();

		List<Artikl> artikli;
		List<Korisnik> korisnici;
		List<Prodaja> prodaje;

		try {
			artikli = BazaPodataka.dohvatiArtikle();
			korisnici = BazaPodataka.dohvatiKorisnike();
			prodaje = BazaPodataka.dohvatiProdajuPremaKriterijima(null);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		artikli.add(0, null);
		korisnici.add(0, null);

		ObservableList<Artikl> observableArtikli = FXCollections.observableList(artikli);
		ObservableList<Korisnik> observableKorisnici = FXCollections.observableList(korisnici);
		ObservableList<Prodaja> observableProdaje = FXCollections.observableList(prodaje);

		artikliComboBox.setItems(observableArtikli);
		korisniciComboBox.setItems(observableKorisnici);

		table.setItems(observableProdaje);
	}

	public void search(Event event) {
		if (!toggleCheckBox.isSelected() && event.getSource() instanceof ComboBox) {
			return;
		}

		Prodaja prodaja = new Prodaja(artikliComboBox.getValue(), korisniciComboBox.getValue(),
				datumDatePicker.getValue());
		List<Prodaja> prodaje;

		try {
			prodaje = BazaPodataka.dohvatiProdajuPremaKriterijima(prodaja);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		ObservableList<Prodaja> observableProdaje = FXCollections.observableList(prodaje);
		table.setItems(observableProdaje);
	}

	private void setFactories() {
		colArtikl.setCellValueFactory(new PropertyValueFactory<>("artikl"));
		colKorisnik.setCellValueFactory(new PropertyValueFactory<>("korisnik"));
		colDatum.setCellValueFactory(new PropertyValueFactory<>("datumObjave"));
	}

}
