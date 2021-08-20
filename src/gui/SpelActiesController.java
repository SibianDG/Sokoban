package gui;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.Vertaler;

public class SpelActiesController extends GridPane {
	
	private DomeinController domeinController;
	private Vertaler vertaler;
	private KiesSpelWijzigenController vorigScherm;
	
	@FXML
	private GridPane gridSpelActies;
	
	@FXML
	private Text titelSpelActies;
	
	@FXML
	private Button btnVoltooiSpelbord;

	@FXML
	private Button btnTerug;
	
	@FXML
	private Button btnAfsluiten;
	
	private HoofdschermController hoofdscherm;
	

	public SpelActiesController(DomeinController domeinController, Vertaler vertaler, KiesSpelWijzigenController vorigScherm) {
        super();

        this.domeinController = domeinController;
        this.vertaler = vertaler;
        this.vorigScherm = vorigScherm;
        this.hoofdscherm = vorigScherm.getHoofdscherm();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SpelActies.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
			throw new RuntimeException(e);
        }
        stelStringInVolgensTaal();
    }
	
	private void stelStringInVolgensTaal() {
        ResourceBundle bundle = vertaler.getBundle();
        titelSpelActies.setText(bundle.getString("spelActies").toUpperCase());
        btnVoltooiSpelbord.setText(bundle.getString("voltooiSpelbord"));
        btnAfsluiten.setText(bundle.getString("spelVerlaten"));
        btnTerug.setText("< "+ bundle.getString("terug"));
    }


	@FXML
	void btnTerugOnAction(ActionEvent event) {
		domeinController.verlaatSpel();
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(vorigScherm.getScene());
		stage.show();
	}
	
	@FXML
	public void btnVoltooiSpelbordOnAction(ActionEvent event) {
		SpeelSpelController speelSpelController = new SpeelSpelController(domeinController, vertaler, this);
		Scene scene = new Scene(speelSpelController);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setTitle("Sokoban");
		stage.setResizable(false);
		stage.setX(400);
		stage.setY(100);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	public void btnAfsluitenOnAction(ActionEvent event) {
		domeinController.verlaatSpel();
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(hoofdscherm.getScene());
		stage.show();
	}
	
	public KiesSpelWijzigenController getVorigScherm() {
		return vorigScherm;
	}
}
