package gui;

import java.io.IOException;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.Vertaler;

public class SelecteerSpelbordController extends GridPane{
	private Vertaler vertaler;
    private DomeinController domeinController;

    @FXML
    private GridPane gridSelecteerSpelbord;

    @FXML
    private Text titelSelecteerSpelbord;
    
    @FXML
    private ListView<String> lvSpelborden;
    
    @FXML
    private Button btnWijzigen;

    @FXML
    private Button btnTerug;
    
    @FXML
    private Text txtInfo;
    
    @FXML
    private Button btnVerwijderen;
    
    private ObservableList<String> spelbordenLijst;
    private SelecteerSpelController vorigScherm;

    public SelecteerSpelbordController(DomeinController domeinController, Vertaler vertaler, SelecteerSpelController vorigScherm) {
        super();
        this.domeinController = domeinController;
        this.vertaler = vertaler;
        this.vorigScherm = vorigScherm;
        this.spelbordenLijst = FXCollections.observableArrayList(domeinController.geefSpelbordNamenVoorSpel());
                 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SelecteerSpelbord.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        int aantalSpelbordenSpel = domeinController.geefAantalSpelbordenVoorSpel();
        update(aantalSpelbordenSpel);
        
        stelStringInVolgensTaal();
        
        lvSpelborden.setItems(FXCollections.unmodifiableObservableList(this.spelbordenLijst));
    }
    
    private void stelStringInVolgensTaal() {
        ResourceBundle bundle = vertaler.getBundle();
        titelSelecteerSpelbord.setText(domeinController.geefNaamHuidigSpel());
        txtInfo.setText(bundle.getString("kiesSpelbordInfo1")+ " " + domeinController.geefAantalSpelbordenVoorSpel() + " " + bundle.getString("kiesSpelbordInfo2"));
        btnWijzigen.setText(bundle.getString("wijzigen"));
        btnTerug.setText("< " + bundle.getString("terug")); 
        btnVerwijderen.setText(bundle.getString("verwijderen"));
    }

    @FXML
    public void btnTerugOnAction(ActionEvent event) {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(vorigScherm.getScene());
            stage.show();
    }
    
    @FXML
    public void btnWijzigenOnAction(ActionEvent event)   {
    	ResourceBundle bundle = vertaler.getBundle();

    	int indexGeselecteerdSpelbord = lvSpelborden.getSelectionModel().getSelectedIndex();

        try {
            domeinController.stelHuidigSpelbordIn(indexGeselecteerdSpelbord);
            WijzigSpelbordController wijzigSpelbordController = new WijzigSpelbordController(domeinController, vertaler, this);
            Scene scene = new Scene(wijzigSpelbordController);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setTitle(bundle.getString("wijzigSpelbord"));
            stage.setResizable(false);
            stage.setScene(scene);

            stage.show();
        } catch (NullPointerException | IndexOutOfBoundsException ignore) {}
    }
    
    @FXML
    public void btnVerwijderenOnAction() {
    	ResourceBundle bundle = vertaler.getBundle();
    	
    	int indexGeselecteerdSpelbord = lvSpelborden.getSelectionModel().getSelectedIndex();
    	
    	try {
            domeinController.stelHuidigSpelbordIn(indexGeselecteerdSpelbord);

            try {
                verwijderSpelbord(indexGeselecteerdSpelbord);
                domeinController.verwijderSpelbord();
                lvSpelborden.setItems(this.spelbordenLijst);
                int aantalSpelbordenSpel = domeinController.geefAantalSpelbordenVoorSpel();
                update(aantalSpelbordenSpel);
            }catch(NullPointerException e) {
                Alert popup = new Alert(Alert.AlertType.INFORMATION);
                popup.setTitle("Alert");
                popup.setHeaderText(bundle.getString("foutmelding"));
                popup.setContentText("");
                popup.showAndWait();
            }

            Alert popup = new Alert(Alert.AlertType.INFORMATION);
            popup.setTitle("Alert");
            popup.setHeaderText(bundle.getString("spelbordVerwijderd"));
            popup.setContentText("");
            popup.showAndWait();

        } catch (IndexOutOfBoundsException | NullPointerException ignore) {}

    }
    
    private void verwijderSpelbord(int positie) {
    	this.spelbordenLijst.remove(positie);
    }

    private void update(int aantal) {
    	ResourceBundle bundle = vertaler.getBundle();
    	if(aantal < 2) {
    		btnVerwijderen.setDisable(true);
    	}
        txtInfo.setText(bundle.getString("kiesSpelbordInfo1")+ " " + aantal + " "+ bundle.getString("kiesSpelbordInfo2"));
    }

    public SelecteerSpelController getVorigScherm() {
        return vorigScherm;
    }
}
