package application;

import java.math.BigDecimal;
import java.util.List;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Automobil;
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

public class AutomobilController {

	@FXML
	private TextField naslovTextField;
	@FXML
	private TextField opisTextField;
	@FXML
	private TextField snagaTextField;
	@FXML
	private TextField cijenaTextField;
	@FXML
	private Button searchButton;
	@FXML
	private TableView<Automobil> table;
	@FXML
	private TableColumn<Automobil, String> colNaslov;
	@FXML
	private TableColumn<Automobil, String> colOpis;
	@FXML
	private TableColumn<Automobil, BigDecimal> colSnaga;
	@FXML
	private TableColumn<Automobil, BigDecimal> colCijena;
	@FXML
	private TableColumn<Automobil, String> colStanje;
	@FXML
	private CheckBox toggleCheckBox;

	public void initialize() {
		setFactories();
		List<Automobil> automobili;

		try {
			automobili = BazaPodataka.dohvatiAutePremaKriterijima(null);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		ObservableList<Automobil> observableList = FXCollections.observableList(automobili);
		table.setItems(observableList);
	}

	public void search(Event event) {
		if (!toggleCheckBox.isSelected() && event instanceof KeyEvent) {
			return;
		}

		String naslov = naslovTextField.getText();
		String opis = opisTextField.getText();
		BigDecimal snaga = null;
		BigDecimal cijena = null;
		if (Funkcije.isNumber(cijenaTextField.getText())) {
			cijena = new BigDecimal(cijenaTextField.getText());
		}
		if (Funkcije.isNumber(snagaTextField.getText())) {
			snaga = new BigDecimal(snagaTextField.getText());
		}

		Automobil auto = new Automobil(null, naslov, opis, snaga, cijena, null);
		List<Automobil> automobili;

		try {
			automobili = BazaPodataka.dohvatiAutePremaKriterijima(auto);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		ObservableList<Automobil> observableList = FXCollections.observableList(automobili);
		table.setItems(observableList);
	}

	private void setFactories() {
		colNaslov.setCellValueFactory(new PropertyValueFactory<>("naslov"));
		colOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
		colSnaga.setCellValueFactory(new PropertyValueFactory<>("snagaKs"));
		colCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
		colStanje.setCellValueFactory(new PropertyValueFactory<>("stanje"));
	}

}
