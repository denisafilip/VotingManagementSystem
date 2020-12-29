package JavaFX.elections;

import JavaFX.database.ParentController;
import election.Election;
import election.LocalElection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

public class LocalController extends ParentController implements Initializable {
    @FXML private ImageView imgVotedUSR;
    @FXML private ImageView imgVotedPNL;
    @FXML private ImageView imgVotedPSD;
    @FXML private ImageView imgVotedPMP;
    @FXML private ImageView imgVotedPRO;
    @FXML private ImageView imgVotedUDMR;
    @FXML
    private Label lblElection;
    @FXML private Button btnBack;
    @FXML private Button btnVoteUSR;
    @FXML private Button btnVotePNL;
    @FXML private Button btnVotePSD;
    @FXML private Button btnVoteUDMR;
    @FXML private Button btnVotePMP;
    @FXML private Button btnVotePRO;

    private final Election localElection = new LocalElection(Year.of(2020), 5);

    public void goBackToElection(ActionEvent event) throws IOException {
        changeScene(event, "/electionType.fxml", "Alegeri");
    }

    public void voteUSR() {
        placeVote(imgVotedUSR, btnVoteUSR, localElection, "noOfLocalVotes", "hasVotedLocal", "USR PLUS", CNP);
    }

    public void votePNL() {
        placeVote(imgVotedPNL, btnVotePNL, localElection, "noOfLocalVotes", "hasVotedLocal", "PNL", CNP);
    }

    public void votePSD() {
        placeVote(imgVotedPSD, btnVotePSD, localElection, "noOfLocalVotes", "hasVotedLocal", "PSD", CNP);
    }

    public void voteUDMR() {
        placeVote(imgVotedUDMR, btnVoteUDMR, localElection, "noOfLocalVotes", "hasVotedLocal", "UDMR", CNP);
    }

    public void votePMP() {
        placeVote(imgVotedPMP, btnVotePMP, localElection, "noOfLocalVotes", "hasVotedLocal", "PMP", CNP);
    }

    public void votePRO() {
        placeVote(imgVotedPRO, btnVotePRO, localElection, "noOfLocalVotes", "hasVotedLocal", "PRO România", CNP);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String countyName = getUserCountyByName(CNP);
        lblElection.setText("ALEGERI LOCALE - " + countyName + " - " + localElection.getYearOfElection());
    }
}
