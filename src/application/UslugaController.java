package application;

import java.math.BigDecimal;
import java.util.List;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Funkcije;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

public class UslugaController {

	@FXML
	private TextField naslovTextField;
	@FXML
	private TextField opisTextField;
	@FXML
	private TextField cijenaTextField;
	@FXML
	private Button searchButton;
	@FXML
	private TableView<Usluga> table;
	@FXML
	private TableColumn<Usluga, String> colNaslov;
	@FXML
	private TableColumn<Usluga, String> colOpis;
	@FXML
	private TableColumn<Usluga, BigDecimal> colCijena;
	@FXML
	private TableColumn<Usluga, Stanje> colStanje;
	@FXML
	private CheckBox toggleCheckBox;

	Thread initThread;
	Thread searchThread;
	Event event;

	public void initialize() {
		prepThreads();
		initThread.start();
	}

	public void search(Event event) {
		this.event = event;
		searchThread.start();
	}

	private void prepThreads() {
		initThread = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				setFactories();
				List<Usluga> usluge;

				try {
					usluge = BazaPodataka.dohvatiUslugePremaKriterijima(null);
				} catch (BazaPodatakaException e) {
					Funkcije.handleBazaPodatakaException(e);
					return null;
				}

				ObservableList<Usluga> observableList = FXCollections.observableList(usluge);
				table.setItems(observableList);
				return null;
			}
		});

		searchThread = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				if (!toggleCheckBox.isSelected() && event instanceof KeyEvent) {
					return null;
				}

				String naslov = naslovTextField.getText();
				String opis = opisTextField.getText();
				BigDecimal cijena = null;
				if (Funkcije.isNumber(cijenaTextField.getText())) {
					cijena = new BigDecimal(cijenaTextField.getText());
				}

				Usluga usluga = new Usluga(null, naslov, opis, cijena, null);
				List<Usluga> usluge;

				try {
					usluge = BazaPodataka.dohvatiUslugePremaKriterijima(usluga);
				} catch (BazaPodatakaException e) {
					Funkcije.handleBazaPodatakaException(e);
					return null;
				}

				ObservableList<Usluga> observableList = FXCollections.observableList(usluge);
				table.setItems(observableList);
				return null;
			}
		});

	}

	private void setFactories() {
		colNaslov.setCellValueFactory(new PropertyValueFactory<>("naslov"));
		colOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
		colCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
		colStanje.setCellValueFactory(new PropertyValueFactory<>("stanje"));
	}

}
