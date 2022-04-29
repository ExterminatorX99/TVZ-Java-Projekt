package application;

import java.math.BigDecimal;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Stan;
import hr.java.vjezbe.entitet.Stanje;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Funkcije;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class StanUnosController {

	@FXML
	TextField naslovTextField;
	@FXML
	TextField opisTextField;
	@FXML
	TextField kvadraturaTextField;
	@FXML
	TextField cijenaTextField;
	@FXML
	ChoiceBox<Stanje> stanjeChoiceBox;

	public void initialize() {
		stanjeChoiceBox.getItems().addAll(Stanje.values());

		stanjeChoiceBox.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
					event.consume();
				}
			}
		});
	}

	public void unesi() {
		String content = new String();
		if (naslovTextField.getText().trim().isEmpty()) {
			content += "Naslov je obavezan podatak!\n";
		}
		if (opisTextField.getText().trim().isEmpty()) {
			content += "Opis je obavezan podatak!\n";
		}
		if (kvadraturaTextField.getText().trim().isEmpty()) {
			content += "Snaga je obavezan podatak!\n";
		} else if (!Funkcije.isNumber(kvadraturaTextField.getText())) {
			content += "Neispravna snaga!\n";
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
			return;
		}
		
		String naslov = naslovTextField.getText();
		String opis = opisTextField.getText();
		int kvadratura = Integer.valueOf(kvadraturaTextField.getText());
		BigDecimal cijena = new BigDecimal(cijenaTextField.getText());
		Stanje stanje = stanjeChoiceBox.getValue() == null ? Stanje.novo : stanjeChoiceBox.getValue();

		Stan stan = new Stan(null, naslov, opis, kvadratura, cijena, stanje);

		try {
			BazaPodataka.pohraniStan(stan);
		} catch (BazaPodatakaException e) {
			Funkcije.handleBazaPodatakaException(e);
			return;
		}

		Alert alert = new Alert(AlertType.INFORMATION, "Podaci uspješno uneseni!");
		alert.setHeaderText(null);
		alert.show();
	}

}
