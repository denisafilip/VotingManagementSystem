package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ElectionTypeController extends ParentController {
    @FXML
    private Button btnReferendum;
    @FXML
    private Button btnParliament;
    @FXML
    private Button btnEuro;
    @FXML
    private Button btnLocal;
    @FXML
    private Button btnPresidential;
    @FXML
    private Button btnBack;

    public void goBackToRegistration(ActionEvent event) throws IOException {
        goBackToScene(event, "/registration.fxml");
    }


    public void goToPresidential(ActionEvent event) throws IOException {
      goForwardToScene(event, "/presidential.fxml", "Alegeri prezidențiale");
    }
}
