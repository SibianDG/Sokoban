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
import java.util.ResourceBundle;

public class KeuzeStartController extends GridPane {
    private Vertaler vertaler;
    private DomeinController domeinController;

    @FXML
    private GridPane gridLanguages;

    @FXML
    private Button btnAanmelden;

    @FXML
    private Button btnRegistreren;

    @FXML
    private Text titelTaal;

    public KeuzeStartController(DomeinController domeinController, Vertaler vertaler) {
        super();
        this.domeinController = domeinController;
        this.vertaler = vertaler;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("KeuzeStart.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stelStringInVolgensTaal();
    }

    @FXML
    void btnAanmeldenOnAction(ActionEvent event) {
        LoginController loginController = new LoginController(domeinController, vertaler, this);
        Scene scene = new Scene(loginController);
        Stage stage = (Stage) this.getScene().getWindow();
        stage.setTitle(vertaler.getBundle().getString("aanmelden"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnRegistrerenOnAction(ActionEvent event) {
    	RegisterController registerController = new RegisterController(domeinController, vertaler, this);
    	Scene scene = new Scene(registerController);
    	Stage stage = (Stage) this.getScene().getWindow();
    	stage.setTitle(vertaler.getBundle().getString("registreren"));
    	stage.setResizable(false);
    	stage.setScene(scene);
    	stage.show();
    }

    private void stelStringInVolgensTaal(){
        ResourceBundle bundle = vertaler.getBundle();
        titelTaal.setText(bundle.getString("welkomZin").toUpperCase());
        btnAanmelden.setText(bundle.getString("aanmelden"));
        btnRegistreren.setText(bundle.getString("registreren"));
    }

}
