package application;

import hr.java.vjezbe.niti.DatumObjaveNit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	private static Stage mainStage;
	private static Scene mainScene;
	private static BorderPane mainBorderPane;

	public static void setCenterPane(BorderPane center) {
		mainBorderPane.setCenter(center);
	}

	private void prikazZadnjeProdaje() {
		Timeline prikazSlavljenika = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.runLater(new DatumObjaveNit());
			}
		}));
		prikazSlavljenika.setCycleCount(Timeline.INDEFINITE);
		prikazSlavljenika.play();
	}

	@Override
	public void start(Stage primaryStage) {
		mainStage = primaryStage;
		try {
			mainStage.setTitle("Kristijan Kos");
			mainBorderPane = (BorderPane) FXMLLoader.load(getClass().getResource("Default.fxml"));
			mainScene = new Scene(mainBorderPane, 600, 500);
			mainScene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			mainStage.setScene(mainScene);
			mainStage.show();

			prikazZadnjeProdaje();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
