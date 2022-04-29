package application;

import java.math.BigDecimal;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Funkcije;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UslugaUnosController {

	@FXML
	TextField naslovTextField;
	@FXML
	TextField opisTextField;
	@FXML
	TextField cijenaTextField;
	@FXML
	ChoiceBox<Stanje> stanjeChoiceBox;

	Thread initThread;
	Thread unesiThread;

	public void initialize() {
		prepThreads();
		initThread.start();
	}

	public void unesi() {
		unesiThread.start();
	}

	private void prepThreads() {
		initThread = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				stanjeChoiceBox.getItems().addAll(Stanje.values());

				stanjeChoiceBox.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent event) {
						if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
							event.consume();
						}
					}
				});
				return null;
			}
		});

		unesiThread = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				String content = new String();
				if (naslovTextField.getText().trim().isEmpty()) {
					content += "Naslov je obavezan podatak!\n";
				}
				if (opisTextField.getText().trim().isEmpty()) {
					content += "Opis je obavezan podatak!\n";
				}
				if (cijenaTextField.getText().trim().isEmpty()) {
					content += "Cijena je obavezan podatak!\n";
				} else if (!Funkcije.isNumber(cijenaTextField.getText())) {
					content += "Neispravna cijena!\n";
				}
				if (!content.isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR, content);
					alert.setHeaderText(null);
					alert.show();
					return null;
				}

				String naslov = naslovTextField.getText();
				String opis = opisTextField.getText();
				BigDecimal cijena = new BigDecimal(cijenaTextField.getText());
				Stanje stanje = stanjeChoiceBox.getValue() == null ? Stanje.novo : stanjeChoiceBox.getValue();

				Usluga usluga = new Usluga(null, naslov, opis, cijena, stanje);

				try {
					BazaPodataka.pohraniUslugu(usluga);
				} catch (BazaPodatakaException e) {
					Funkcije.handleBazaPodatakaException(e);
					return null;
				}

				Alert alert = new Alert(AlertType.INFORMATION, "Podaci uspješno uneseni!");
				alert.setHeaderText(null);
				alert.show();
				return null;
			}
		});

	}

}
