package gui;

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

import java.io.IOException;
import java.util.Map;

public class LanguageController extends GridPane {
    private String ingesteldeTaal = "English";
    private DomeinController domeinController;

    @FXML
    private Text titelTaal;

    @FXML
    private GridPane gridLanguages;

    @FXML
    private Button dutchButton;

    @FXML
    private Button englishButton;

    @FXML
    private Button frenchButton;

    @FXML
    private Button next1;

    public LanguageController(DomeinController domeinController) {
        super();
        this.domeinController = domeinController;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Languages.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnLanguageNext(ActionEvent event) {
        Map<String, String> locals = Map.of(
                "Dutch", "nl",
                "English", "en",
                "French", "fr"
        );
        KeuzeStartController keuzeStartController = new KeuzeStartController(domeinController, new Vertaler(locals.get(ingesteldeTaal)));
        Scene scene = new Scene(keuzeStartController);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setTitle("Sokoban");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnLanguageOnAction(ActionEvent event) {
    	
        Map<String, String> buttonText = Map.of(
                "Dutch", "Volgende",
                "English", "Next",
                "French", "Suivante"
        );
        Map<String, String> titelTaalMap = Map.of(
                "Dutch", "Taal",
                "English", "Language",
                "French", "Langue"
        );
        Button geklikteButton = ((Button)event.getSource());
        ingesteldeTaal = geklikteButton.getText();

        verwijderActiveButtonClassButton();
        geklikteButton.getStyleClass().add("activeButton");

        next1.setText(buttonText.get(ingesteldeTaal));
        titelTaal.setText(titelTaalMap.get(ingesteldeTaal).toUpperCase());
    }

    private void verwijderActiveButtonClassButton(){
        dutchButton.getStyleClass().removeIf(style -> style.equals("activeButton"));
        englishButton.getStyleClass().removeIf(style -> style.equals("activeButton"));
        frenchButton.getStyleClass().removeIf(style -> style.equals("activeButton"));
    }

}
