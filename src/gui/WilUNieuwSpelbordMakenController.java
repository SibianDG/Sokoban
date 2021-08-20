package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.Vertaler;

import java.io.IOException;
import java.util.ResourceBundle;

public class WilUNieuwSpelbordMakenController extends GridPane{
	private Vertaler vertaler;
    private DomeinController domeinController;
    private HoofdschermController hoofdschermController;
    private boolean alEensGevraagdOmTeVerwijderen = false;

    @FXML
    private GridPane gridLanguages;

    @FXML
    private Button btnJa;

    @FXML
    private Button btnNee;

    @FXML
    private Text titelTaal;

    public WilUNieuwSpelbordMakenController(Vertaler vertaler, DomeinController domeinController, HoofdschermController hoofdschermController) {
        this.vertaler = vertaler;
        this.domeinController = domeinController;
        this.hoofdschermController = hoofdschermController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WilUNieuwSpelbordMaken.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stelStringInVolgensTaal();

    }

    @FXML
    void btnJaOnAction(ActionEvent event) {
    	alEensGevraagdOmTeVerwijderen = false;
        domeinController.maakLeegSpelbordAan();
        loadScene("Sokoban", new MaakSpelbordController(domeinController, vertaler, this)); 
    }

    @FXML
    void btnNeeOnAction(ActionEvent event) {     
        if(domeinController.geefTotaalAantalSpelbordenNieuwSpel() >= 1) {
            loadAlert(domeinController.geefNaamHuidigSpel() + " " + vertaler.getBundle().getString("aangemaakt"),
                    domeinController.geefTotaalAantalSpelbordenNieuwSpel()+ " "
                            + (domeinController.geefTotaalAantalSpelbordenNieuwSpel() == 1 ? vertaler.getBundle().getString("spelbord1"):vertaler.getBundle().getString("spelborden")),
                    Alert.AlertType.INFORMATION);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(hoofdschermController.getScene());
            stage.show();
        } else {
        	if(alEensGevraagdOmTeVerwijderen) {
        		domeinController.verwijderSpel();
                loadAlert(vertaler.getBundle().getString("spelVerwijderd2"), vertaler.getBundle().getString("meldingSpelbord"), Alert.AlertType.WARNING);
                Stage stage = (Stage) this.getScene().getWindow();
                stage.setScene(hoofdschermController.getScene());
                stage.show();
        	} else {
        		alEensGevraagdOmTeVerwijderen = true;
        		loadAlert(vertaler.getBundle().getString("spelAlertVerwijderen"), vertaler.getBundle().getString("meldingSpelbord"), Alert.AlertType.INFORMATION);
        	}
        }
    }


    private void stelStringInVolgensTaal() {
        ResourceBundle bundle = vertaler.getBundle();
        titelTaal.setText(bundle.getString("spelbordToevoegen") + "?");
        btnJa.setText(bundle.getString("ja"));
        btnNee.setText(bundle.getString("nee"));
    }

    private void loadScene(String titel, Object controller) {
        Scene scene = new Scene((Parent) controller);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setTitle(titel);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setX(300);
        stage.setY(100);
        stage.show();
    }

    private void loadAlert(String header, String context, Alert.AlertType alertType) {
        Alert popup = new Alert(alertType);
        popup.setTitle("Alert");
        popup.setHeaderText(header);
        popup.setContentText(context);
        popup.showAndWait();
    }
}
