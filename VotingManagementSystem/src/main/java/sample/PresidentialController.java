package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.stage.Stage;

import java.io.IOException;

public class PresidentialController extends ParentController {
    @FXML
    private Button btnBack;
    @FXML
    private ScrollBar scrollBar;
    @FXML
    private Button btnVot;

    public void goBackToElection(ActionEvent event) throws IOException {
        goBackToScene(event, "/electionType.fxml");
    }
}
