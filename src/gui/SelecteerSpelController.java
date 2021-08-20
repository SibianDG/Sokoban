package gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle;

import domein.DomeinController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.Vertaler;

public class SelecteerSpelController extends GridPane{
	private Vertaler vertaler;
    private DomeinController domeinController;

    @FXML
    private GridPane gridKiesSpel;

    @FXML
    private Text titelKiesSpel;
    
    @FXML
    private ListView<String> lvSpelLijst;
    
    @FXML
    private Button btnVolgende;

    @FXML
    private Button btnTerug;
    
    @FXML
    private Text txtInfo;
    
    private ObservableList<String> spelLijst;
    private String[][] spellen;
    private HoofdschermController vorigScherm;

    public SelecteerSpelController(DomeinController domeinController, Vertaler vertaler, HoofdschermController vorigScherm) {
        super();
        this.domeinController = domeinController;
        this.vertaler = vertaler;
        this.vorigScherm = vorigScherm;
        this.spellen = domeinController.geefAlleSpellen();
                 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SelecteerSpel.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        stelStringInVolgensTaal();
        String[] spelNamen = new String[spellen.length];
        
        for(int i = 0; i < spellen.length; i++) {
        	spelNamen[i] = spellen[i][0];
        }
        ObservableList<String> items = FXCollections.observableArrayList(Arrays.asList(spelNamen));
        lvSpelLijst.setItems(FXCollections.unmodifiableObservableList(items));

    }
    
    private void stelStringInVolgensTaal() {
        ResourceBundle bundle = vertaler.getBundle();
        titelKiesSpel.setText(bundle.getString("kiesSpelTitel").toUpperCase());
        txtInfo.setText(bundle.getString("wijzigenSpelbord"));
        btnVolgende.setText(bundle.getString("volgende"));
        btnTerug.setText("< " + bundle.getString("terug"));
    }

    @FXML
    void btnTerugOnAction(ActionEvent event) {
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(vorigScherm.getScene());
            stage.show();
    }
    
    @FXML
    public void btnVolgendeOnAction(ActionEvent event) {
        try {
            String geselecteerdSpel = lvSpelLijst.getSelectionModel().getSelectedItem();

            int spelID = 0;
            for (String[] strings : spellen) {
                if (strings[0].equals(geselecteerdSpel)) {
                    spelID = Integer.parseInt(strings[1]);
                }
            }
            domeinController.stelHuidigSpelIn(spelID);
            SelecteerSpelbordController selecteerSpelbordController = new SelecteerSpelbordController(domeinController, vertaler, this);
            Scene scene = new Scene(selecteerSpelbordController);
            Stage stage = (Stage) this.getScene().getWindow();
            stage.setTitle(vertaler.getBundle().getString("selecteerSpelbord"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IndexOutOfBoundsException | NullPointerException ignore) {}

    }

}
