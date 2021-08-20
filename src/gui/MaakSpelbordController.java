package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.Vertaler;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

public class MaakSpelbordController extends GridPane {
    private final DomeinController domeinController;
    private final Vertaler vertaler;
    private final WilUNieuwSpelbordMakenController vorigScherm;
    private String[][] speelVeldStringArray;
    private Map<String, Integer> afbeeldingNaarVeld = Map.of(
            "file:src/pictures/speler.png", 3,
            "file:src/pictures/muur.png", 1,
            "file:src/pictures/veld.png",5,
            "file:src/pictures/doel.png",2,
            "file:src/pictures/kist.png",4);
    private Map<String, String> veldNaarAfbeelding = Map.of(
            "M", "file:src/pictures/speler.png",
            "#", "file:src/pictures/muur.png",
            " ", "file:src/pictures/veld.png",
            ".", "file:src/pictures/doel.png",
            "x", "file:src/pictures/kist.png",
            "D", "file:src/pictures/speler.png",
            "S", "file:src/pictures/kist.png"
    );

    @FXML
    private GridPane mainGridPane;

    @FXML
    private Button btnTerug;

    @FXML
    private Button btnNext;

    @FXML
    private GridPane spelbord;

    @FXML
    private ImageView imvVeld;

    @FXML
    private ImageView imvMuur;

    @FXML
    private ImageView imvDoel;

    @FXML
    private ImageView imvKist;

    @FXML
    private ImageView imvMan;

    @FXML
    private Text txtDragDrop;

    @FXML
    private Text txtMan;

    @FXML
    private Text txtVeld;

    @FXML
    private Text txtDoel;

    @FXML
    private Text txtKist;

    @FXML
    private Text txtMuur;

    public MaakSpelbordController(DomeinController domeinController, Vertaler vertaler, WilUNieuwSpelbordMakenController vorigScherm) {
        this.domeinController = domeinController;
        this.vertaler = vertaler;
        this.vorigScherm = vorigScherm;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MaakSpelbord.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
            stelStringInVolgensTaal();
            updateSpeelveld();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnTerugOnAction(ActionEvent event) {
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setScene(vorigScherm.getScene());
        stage.show();
    }

    @FXML
    void imvOnDrag(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putImage(imageView.getImage());
        content.putString(imageView.getImage().getUrl());
        db.setContent(content);
        event.consume();
    }

    @FXML
    void btnNextOnAction(ActionEvent event) {
        try {
            int index = 0;
            for (Node node : spelbord.getChildren()) {
                ImageView imageView = (ImageView) node;
                domeinController.voerActieUitOpPositie(index++, afbeeldingNaarVeld.get(imageView.getImage().getUrl()));
            }
            domeinController.voegNieuwSpelbordToe();

            Stage stage = (Stage) this.getScene().getWindow();
            stage.setScene(vorigScherm.getScene());
            stage.show();
        } catch (IllegalArgumentException iae) {
            loadAlert(vertaler.getBundle().getString("foutiefSpelbord"), vertaler.getBundle().getString(iae.getMessage()));
        }
    }


    private void stelStringInVolgensTaal(){
        ResourceBundle bundle = vertaler.getBundle();
        btnTerug.setText("< "+bundle.getString("terug"));
        txtDragDrop.setText(bundle.getString("txtDragDrop"));
        btnNext.setText(bundle.getString("klaar"));
        txtKist.setText(bundle.getString("kist"));
        txtDoel.setText(bundle.getString("doel"));
        txtMan.setText(bundle.getString("mannetje"));
        txtMuur.setText(bundle.getString("muur"));
        txtVeld.setText(bundle.getString("veldLeeg"));
    }

    private void krijgSpelbordString() {
        this.speelVeldStringArray = domeinController.geefSpelBordAlsStringArray();
    }

    private void vulGridPaneOp() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ImageView imageView = maakAfbeelding(speelVeldStringArray[i][j]);
                imageView.setFitHeight(60);
                imageView.setFitWidth(60);
                imageView.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        if (dragEvent.getGestureSource() != imageView &&
                                dragEvent.getDragboard().hasImage()) {
                            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        }
                        dragEvent.consume();
                    }
                });
                imageView.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent dragEvent) {
                        Dragboard db = dragEvent.getDragboard();
                        ImageView abc = (ImageView)dragEvent.getGestureSource();

                        if (db.hasImage()) {
                            String[] arr = abc.getImage().getUrl().split("/");
                            imageView.setImage(new Image("file:src/pictures/"+arr[arr.length-1]));
                        }
                        dragEvent.consume();
                    }
                });
                spelbord.add(imageView, j, i);
            }
        }
    }

    private void updateSpeelveld(){
        krijgSpelbordString();
        vulGridPaneOp();
    }

    private ImageView maakAfbeelding(String veld){
        Image image = new Image(veldNaarAfbeelding.get(veld));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(60);
        imageView.setFitHeight(60);
        return imageView;
    }

    private void loadAlert(String header, String context) {
        Alert popup = new Alert(Alert.AlertType.WARNING);
        popup.setTitle("Alert");
        popup.setHeaderText(header);
        popup.setContentText(context);
        popup.showAndWait();
    }

}
