package gui;

import domein.DomeinController;
import exceptions.FouteZetException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.Vertaler;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

public class SpeelSpelController extends GridPane {
    private final DomeinController domeinController;
    private final Vertaler vertaler;
    private final SpelActiesController spelActiesController;
    private Map<KeyCode, Integer> keyCodeIntegerMap = Map.of(
            KeyCode.UP, 1,
            KeyCode.RIGHT, 2,
            KeyCode.DOWN, 3,
            KeyCode.LEFT, 4
    );
    private Map<String, String> veldNaarAfbeelding = Map.of(
            "M", "file:src/pictures/speler.png",
            "#", "file:src/pictures/muur.png",
            " ", "file:src/pictures/veld.png",
            ".", "file:src/pictures/doel.png",
            "x", "file:src/pictures/kist.png",
            "D", "file:src/pictures/speler.png",
            "S", "file:src/pictures/kist_doel.png"
    );
    private String[][] speelVeldStringArray;
    private String[][] bordCopy;

    @FXML
    private GridPane mainGridPane;

    @FXML
    private Button btnTerug;

    @FXML
    private GridPane spelbord;

    @FXML
    private Text txtAantalZetten;

    @FXML
    private Text txtNumberAantalZetten;

    @FXML
    private Button btnReset;


    public SpeelSpelController(DomeinController domeinController, Vertaler vertaler, SpelActiesController spelActiesController) {
        super();
        this.domeinController = domeinController;
        this.vertaler = vertaler;
        this.spelActiesController = spelActiesController;
        bordCopy = new String[10][10];

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SpeelSpel.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
            stelStringInVolgensTaal();
            updateSpeelveld();
            mainGridPane.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    try {
                        if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN || event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT) {
                            domeinController.verplaatsSpeler(keyCodeIntegerMap.get(event.getCode()));
                            updateSpeelveld();

                            if (isSpelbordVoltooid()){
                                if (isSpelVoltooid()){
                                    gewonnenPopup();
                                    domeinController.verlaatSpel();
                                } else {
                                    kiesVolgendSpelbord();
                                }
                            }
                        }
                    } catch (FouteZetException ignored) { }
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void krijgSpelbordString() {
        if (!spelbord.getChildren().isEmpty()){
            bordCopy = speelVeldStringArray.clone();
        }
        this.speelVeldStringArray = domeinController.geefSpelBordAlsStringArray();
    }

    private void vulGridPaneOp() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (bordCopy[0][0] == null || !bordCopy[j][i].equals(speelVeldStringArray[j][i])){
                    ImageView imageView = maakAfbeelding(speelVeldStringArray[j][i]);
                    imageView.setFitHeight(60);
                    imageView.setFitWidth(60);
                    spelbord.add(imageView, i, j);
                }
            }
        }
    }

    private void updateSpeelveld() {
        int aantalStappen = domeinController.geefAantalStappenHuidigSpel();
        txtNumberAantalZetten.setText(aantalStappen == 0 ? vertaler.getBundle().getString("geen") : Integer.toString(aantalStappen));
        krijgSpelbordString();
        vulGridPaneOp();
    }

    private ImageView maakAfbeelding(String veld) {
        Image image = new Image(veldNaarAfbeelding.get(veld));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        return imageView;
    }

    @FXML
    void speelSpelTerugOnAction(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(spelActiesController.getScene());
        stage.show();
    }

    @FXML
    void btnResetOnAction(ActionEvent event) {
        domeinController.resetSpelbord();
        bordCopy = new String[10][10];
        updateSpeelveld();
    }

    private void loadScene(String titel, Object controller) {
        Scene scene = new Scene((Parent) controller);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setTitle(vertaler.getBundle().getString(titel));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void stelStringInVolgensTaal() {
        ResourceBundle bundle = vertaler.getBundle();
        btnTerug.setText("< "+ bundle.getString("terug"));
        txtAantalZetten.setText(bundle.getString("aantalStappen") + ":");
        txtNumberAantalZetten.setText(bundle.getString("geen"));
        btnReset.setText(bundle.getString("reset"));
    }

    private boolean isSpelbordVoltooid() {
        return domeinController.isHuidigSpelbordVoltooid();
    }

    private boolean isSpelVoltooid() {
        return domeinController.isSpelGedaan();
    }

    private void gewonnenPopup() {
        spelbordGewonnenAlert();
        SpelVoltooidController spelVoltooidController = new SpelVoltooidController(domeinController, vertaler, spelActiesController.getVorigScherm());
        Scene scene = new Scene(spelVoltooidController);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setTitle("Sokoban");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void kiesVolgendSpelbord() {
        spelbordGewonnenAlert();
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(spelActiesController.getScene());
        stage.show();
    }

    private void spelbordGewonnenAlert() {
    	ResourceBundle bundle = vertaler.getBundle();
        Alert popup = new Alert(Alert.AlertType.INFORMATION);
        popup.setTitle("Sokoban");
        popup.setHeaderText(bundle.getString("gewonnenTxt"));
        popup.setContentText(String.format("%s %d %s %d %s.", bundle.getString("gewonnenAlertText1"),  domeinController.geefAantalVoltooideSpelbordenVoorSpel(), bundle.getString("gewonnenAlertText2"), domeinController.geefAantalSpelbordenVoorSpel(), bundle.getString("gewonnenAlertText3")));
        popup.setX(500);
        popup.setY(300);
        popup.showAndWait();
    }
}
