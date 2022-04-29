package application;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.Funkcije;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DefaultController {
	@FXML
	Text uslugaText;

	Thread thread;

	public void initialize() {
		prepThreads();
		zadnjaUsluga();
	}

	private void prepThreads() {
		thread = new Thread(new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Usluga usluga;
				try {
					usluga = BazaPodataka.dohvatiZadnjuUslugu();
				} catch (BazaPodatakaException e) {
					Funkcije.handleBazaPodatakaException(e);
					return null;
				}

				uslugaText.setText(usluga.toString());

				return null;
			}
		});
	}

	private void zadnjaUsluga() {
		Timeline prikazSlavljenika = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				thread.start();
			}
		}));
		prikazSlavljenika.setCycleCount(Timeline.INDEFINITE);
		prikazSlavljenika.play();
	}

}
