package gui;

import java.io.IOException;
import java.util.*;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.Vertaler;

public class WijzigSpelbordController extends GridPane{
		
		private boolean gewijzigd = false;
		private boolean ongedaan = false;
	
		private final DomeinController domeinController;
	    private final Vertaler vertaler;
	    private final SelecteerSpelbordController vorigScherm;
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
	            "S", "file:src/pictures/kist.png");
	    
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
	    private Button btnMaakOngedaan;

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


	public WijzigSpelbordController(DomeinController domeinController, Vertaler vertaler, SelecteerSpelbordController vorigScherm) {
		this.domeinController = domeinController;
		this.vertaler = vertaler;
		this.vorigScherm = vorigScherm;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("WijzigSpelbord.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		stelStringInVolgensTaal();
		updateSpeelveld();
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
		gewijzigd = true;
	}

	@FXML
	void btnNextOnAction(ActionEvent event) {
		List<Integer> actieLijst = new ArrayList<>();
		int actie = 0;
		for(int i = 0; i < this.speelVeldStringArray.length; i++) {
			for(int j =0; j < this.speelVeldStringArray.length; j++) {
				switch(this.speelVeldStringArray[i][j]) {
				case "#" : actie = 1; break;
				case "." : actie = 2; break;
				case "M" : actie = 3; break;
				case "x" : actie = 4; break;
				case " " : actie = 5; break;
				}
				actieLijst.add(actie);
			}
		}

		try {
			int index = 0;
			if(!(ongedaan == true && gewijzigd == false)) {
				for (Node node : spelbord.getChildren()) {

					ImageView imageView = (ImageView) node;
					if(afbeeldingNaarVeld.get(imageView.getImage().getUrl()) != actieLijst.get(index)) {
						domeinController.wijzigSpelbordOpPositie(index, afbeeldingNaarVeld.get(imageView.getImage().getUrl()));
					}
					index++;
				}

				domeinController.updateSpelbord();
				Alert popup = new Alert(Alert.AlertType.CONFIRMATION);
				popup.setTitle("Alert");
				popup.setHeaderText(vertaler.getBundle().getString("gewijzigdSpelbord"));
				popup.setContentText(vertaler.getBundle().getString("gewijzigdConfirm"));
				popup.showAndWait();
			}
			loadScene("Sokoban", new SelecteerSpelbordController(domeinController, vertaler, vorigScherm.getVorigScherm()));
		} catch (IllegalArgumentException iae) {
			domeinController.resetSpelbord();
			loadAlert(vertaler.getBundle().getString("foutiefSpelbordTitel"), vertaler.getBundle().getString(iae.getMessage()));
		}
	}

	@FXML
	void btnMaakOngedaanOnAction(ActionEvent event) {
		domeinController.resetSpelbord();
		spelbord.getChildren().removeAll(spelbord.getChildren());
		krijgSpelbordString();
		vulGridPaneOp();
		gewijzigd = false;
		ongedaan = true;
	}

	private void stelStringInVolgensTaal(){
		ResourceBundle bundle = vertaler.getBundle();
		btnTerug.setText("< "+bundle.getString("terug"));
		txtDragDrop.setText(bundle.getString("wijzigSpelDragAndDrop"));
		btnNext.setText(bundle.getString("klaar"));
		btnMaakOngedaan.setText(bundle.getString("ongedaanMakenButton"));
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
}