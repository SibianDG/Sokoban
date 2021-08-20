package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.Vertaler;

import java.io.IOException;
import java.util.ResourceBundle;

public class SpelVoltooidController extends GridPane {

    private Vertaler vertaler;
    private DomeinController domeinController;
    private KiesSpelWijzigenController vorigScherm;
    @FXML
    private Button btnVolgende;
    
    @FXML
    private Text txtGewonnen;
    
    @FXML
    private Text txtAantalSpelborden;
    
    @FXML
    private Text txtVoltooideSpelborden;

    public SpelVoltooidController(DomeinController domeinController, Vertaler vertaler, KiesSpelWijzigenController vorigScherm) {
        super();
        this.vertaler = vertaler;
        this.domeinController = domeinController;
        this.vorigScherm = vorigScherm;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SpelVoltooid.fxml"));
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
        btnVolgende.setText(bundle.getString("volgende"));
        txtGewonnen.setText(bundle.getString("gewonnenTxt"));
        txtAantalSpelborden.setText(bundle.getString("aantalSpelborden1") + domeinController.geefAantalSpelbordenVoorSpel());
        txtVoltooideSpelborden.setText(bundle.getString("voltooideSpelborden1") + domeinController.geefAantalVoltooideSpelbordenVoorSpel());
    }
    
    @FXML
    void btnVolgendeOnAction(ActionEvent event) {
    	Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(vorigScherm.getScene());
        stage.show();
    }

}
