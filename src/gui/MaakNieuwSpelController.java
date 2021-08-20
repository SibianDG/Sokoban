package gui;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.Vertaler;

public class MaakNieuwSpelController extends GridPane {
	
	private DomeinController domeinController;
	private Vertaler vertaler;
	private HoofdschermController vorigScherm;
	
	@FXML
	private GridPane gridMaakNieuwSpel;
	
	@FXML
	private Text titelMaakNieuwSpel;
	
	@FXML
	private Button btnVolgende;
	
	@FXML
	private Label lblSpelnaam;
	
	@FXML
	private Text txtFout;
	
	@FXML
	private HBox hboxFout;
	
	@FXML
	private TextField txfSpelnaam;
	
	@FXML
	private Button btnVorige;


	public MaakNieuwSpelController(DomeinController domeinController, Vertaler vertaler, HoofdschermController vorigScherm) {
        super();
        this.domeinController = domeinController;
        this.vertaler = vertaler;
        this.vorigScherm = vorigScherm;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MaakNieuwSpel.fxml"));
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
        titelMaakNieuwSpel.setText(bundle.getString("maakNieuwSpel").toUpperCase());
        btnVolgende.setText(bundle.getString("volgende"));
        txfSpelnaam.setPromptText("| " + bundle.getString("spelnaam"));
        lblSpelnaam.setText(bundle.getString("spelnaam"));
        btnVorige.setText("< " + bundle.getString("terug"));
    }
	
	@FXML
	public void btnVolgendeOnAction(ActionEvent event) {	
		try {
			domeinController.voegSpelToe(txfSpelnaam.getText());
			loadScene("Sokoban", new WilUNieuwSpelbordMakenController(vertaler, domeinController, vorigScherm));
		}catch(IllegalArgumentException e) {
			ResourceBundle bundle = vertaler.getBundle();
			txtFout.setText(bundle.getString("spelnaamLeeg"));
			
			if(e.getMessage().equals("naamBestaatReeds")) {
				txtFout.setText(vertaler.getBundle().getString("naamBestaatReeds"));
	            txtFout.setVisible(true);
	            hboxFout.setVisible(true);
			} else if(e.getMessage().equals("spatiesSpelnaam")) {
				txtFout.setText(vertaler.getBundle().getString("spatiesSpelnaam"));
	            txtFout.setVisible(true);
	            hboxFout.setVisible(true);
			}
		}
		txfSpelnaam.setText("");
	}
	
	@FXML
	public void btnVorigeOnAction(ActionEvent event) {
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(vorigScherm.getScene());
		stage.show();
	}

	private void loadScene(String titel, Object controller) {
		Scene scene = new Scene((Parent) controller);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setTitle(titel);
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
	
}
