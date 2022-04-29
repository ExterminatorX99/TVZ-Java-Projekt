package application;

import java.math.BigDecimal;
import java.util.List;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Stan;
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

public class StanController {

	@FXML
	private TextField naslovTextField;
	@FXML
	private TextField opisTextField;
	@FXML
	private TextField kvadraturaTextField;
	@FXML
	private TextField cijenaTextField;
	@FXML
	private Button searchButton;
	@FXML
	private TableView<Stan> table;
	@FXML
	private TableColumn<Stan, String> colNaslov;
	@FXML
	private TableColumn<Stan, String> colOpis;
	@FXML
	private TableColumn<Stan, Integer> colKvadratura;
	@FXML
	private TableColumn<Stan, BigDecimal> colCijena;
	@FXML
	private TableColumn<Stan, String> colStanje;
	@FXML
	private CheckBox toggleCheckBox;

	public void initialize() {
		setFactories();
		List<Stan> stanovi;
		try {
			stanovi = BazaPodataka.dohvatiStanovePremaKriterijima(null);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}
		ObservableList<Stan> observableList = FXCollections.observableList(stanovi);
		table.setItems(observableList);
	}

	public void search(Event event) {
		if (!toggleCheckBox.isSelected() && event instanceof KeyEvent) {
			return;
		}

		String naslov = naslovTextField.getText();
		String opis = opisTextField.getText();
		int kvadratura = 0;
		BigDecimal cijena = null;
		if (Funkcije.isNumber(cijenaTextField.getText())) {
			cijena = new BigDecimal(cijenaTextField.getText());
		}
		if (Funkcije.isNumber(kvadraturaTextField.getText())) {
			kvadratura = Integer.valueOf(kvadraturaTextField.getText());
		}

		Stan stan = new Stan(null, naslov, opis, kvadratura, cijena, null);
		List<Stan> stanovi;

		try {
			stanovi = BazaPodataka.dohvatiStanovePremaKriterijima(stan);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		ObservableList<Stan> observableList = FXCollections.observableList(stanovi);
		table.setItems(observableList);
	}

	private void setFactories() {
		colNaslov.setCellValueFactory(new PropertyValueFactory<>("naslov"));
		colOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
		colKvadratura.setCellValueFactory(new PropertyValueFactory<>("kvadratura"));
		colCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
		colStanje.setCellValueFactory(new PropertyValueFactory<>("stanje"));
	}

}
