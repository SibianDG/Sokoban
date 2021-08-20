package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.Vertaler;

import java.io.IOException;
import java.util.ResourceBundle;

public class RegisterController extends GridPane {

    private DomeinController domeinController;
    private Vertaler vertaler;
    private KeuzeStartController vorigScherm;

    @FXML
    private GridPane gridRegister;

    @FXML
    private Text titelTaal;

    @FXML
    private TextField txfLastName;
    
    @FXML
    private TextField txfFirstName;
    
    @FXML
    private TextField txfUsername;    
    
    @FXML
    private PasswordField pwfPassword;

    @FXML
    private Button btnRegister;

    @FXML
    private Text txtInfo;
    
    @FXML
    private Text txtInfoW;
    
    @FXML
    private Button btnTerug;

    @FXML
    private HBox hbGebruikersnaam;

    @FXML
    private HBox hbWachtwoord;

    @FXML
    private Text txtUitroep;
    
    public RegisterController(DomeinController domeinController, Vertaler vertaler, KeuzeStartController vorigScherm){
        super();
        this.domeinController = domeinController;
        this.vertaler = vertaler;
        this.vorigScherm = vorigScherm;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Registreer.fxml"));
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
        titelTaal.setText(bundle.getString("registreren").toUpperCase());
        txfFirstName.setPromptText("| " + bundle.getString( "voornaam"));
        txfLastName.setPromptText("| " + bundle.getString("familienaam"));
        txfUsername.setPromptText("| " + bundle.getString("gebruikersnaam"));
        pwfPassword.setPromptText("| " + bundle.getString("wachtwoord"));
        txtInfo.setText(bundle.getString("gebruikersnaamInfo"));
        txtInfoW.setText(bundle.getString("wachtwoordInfo"));
        btnTerug.setText("< " + bundle.getString("terug"));
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {
    	ResourceBundle bundle = vertaler.getBundle();
    	String voornaam, familienaam, gebruikersnaam, wachtwoord;
    	String foutmelding = "";
    		
    	try {
    		voornaam = txfFirstName.getText();
    		familienaam = txfLastName.getText();
    		gebruikersnaam = txfUsername.getText();
    		wachtwoord = pwfPassword.getText();

    		if(gebruikersnaam.isEmpty()) {
                foutmelding += String.format("%s%n", bundle.getString("gebruikersnaamVerplicht"));
                txtInfo.setText(foutmelding);
                hbGebruikersnaam.getStyleClass().clear();
                hbGebruikersnaam.getStyleClass().add("foutmeldingHBox");
                txtUitroep.setVisible(true);
            }
    		if (wachtwoord.isEmpty()){
    			foutmelding += String.format("%s%n", bundle.getString("wachtwoordVerplicht"));
                hbWachtwoord.getStyleClass().clear();
                hbWachtwoord.getStyleClass().add("foutmeldingHBox");
                txtUitroep.setVisible(true);
            }

    		
    		domeinController.registreer(voornaam, familienaam, gebruikersnaam, wachtwoord);

            HoofdschermController hoofdschermController = new HoofdschermController(domeinController, vertaler);
            Scene scene = new Scene(hoofdschermController);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setTitle("Sokoban");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
    		
    	} catch(IllegalArgumentException e) {
    		foutmelding += String.format("%n%s", bundle.getString(e.getMessage()));
            hbGebruikersnaam.getStyleClass().clear();
            hbGebruikersnaam.getStyleClass().add("foutmeldingHBox");
            txtInfo.setText(foutmelding);
            hbWachtwoord.setVisible(false);
            txtUitroep.setVisible(true);

        }  catch(Exception e) {
            foutmelding += String.format("%s%n", bundle.getString(e.getMessage()));
            hbGebruikersnaam.getStyleClass().clear();
            hbGebruikersnaam.getStyleClass().add("foutmeldingHBox");
            txtInfo.setText(foutmelding);
            hbWachtwoord.setVisible(false);
            txtUitroep.setVisible(true);

        }
    }
    
    @FXML
    public void btnTerugOnAction(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(vorigScherm.getScene());
        stage.show();
    }
}

