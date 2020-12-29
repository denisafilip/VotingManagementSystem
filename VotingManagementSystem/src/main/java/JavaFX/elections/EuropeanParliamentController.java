package JavaFX.elections;

import JavaFX.database.ParentController;
import election.Election;
import election.EuropeanParliamentElection;
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

public class EuropeanParliamentController extends ParentController implements Initializable {
    @FXML private ImageView imgVotedUSR;
    @FXML private ImageView imgVotedPNL;
    @FXML private ImageView imgVotedPSD;
    @FXML private ImageView imgVotedAUR;
    @FXML private ImageView imgVotedPMP;
    @FXML private ImageView imgVotedPRO;
    @FXML private ImageView imgVotedUDMR;
    @FXML private ImageView imgVotedPOL;
    @FXML private ImageView imgVotedPRU;
    @FXML private Button btnBack;
    @FXML private Label lblElection;
    @FXML private Button btnVoteUSR;
    @FXML private Button btnVotePNL;
    @FXML private Button btnVotePSD;
    @FXML private Button btnVoteUDMR;
    @FXML private Button btnVotePMP;
    @FXML private Button btnVoteAUR;
    @FXML private Button btnVotePRO;
    @FXML private Button btnVotePOL;
    @FXML private Button btnVotePRU;

    private final Election europeanParliamentElection = new EuropeanParliamentElection(Year.of(2019), 5);

    public void goBackToElection(ActionEvent event) throws IOException {
        changeScene(event, "/electionType.fxml", "Alegeri");
    }

    public void voteUSR() {
        placeVote(imgVotedUSR, btnVoteUSR, europeanParliamentElection, "noOfEuroVotes", "hasVotedEuro", "USR PLUS", CNP);
    }

    public void votePNL() {
        placeVote(imgVotedPNL, btnVotePNL, europeanParliamentElection, "noOfEuroVotes", "hasVotedEuro", "PNL", CNP);
    }

    public void votePSD() {
        placeVote(imgVotedPSD, btnVotePSD, europeanParliamentElection, "noOfEuroVotes", "hasVotedEuro", "PSD", CNP);
    }

    public void voteUDMR() {
        placeVote(imgVotedUDMR, btnVoteUDMR, europeanParliamentElection, "noOfEuroVotes", "hasVotedEuro", "UDMR", CNP);
    }

    public void votePMP() {
        placeVote(imgVotedPMP, btnVotePMP, europeanParliamentElection, "noOfEuroVotes", "hasVotedEuro", "PMP", CNP);
    }

    public void voteAUR() {
        placeVote(imgVotedAUR, btnVoteAUR, europeanParliamentElection, "noOfEuroVotes", "hasVotedEuro", "AUR", CNP);
    }

    public void votePRO() {
        placeVote(imgVotedPRO, btnVotePRO, europeanParliamentElection, "noOfEuroVotes", "hasVotedEuro", "PRO România", CNP);
    }

    public void votePOL() {
        placeVote(imgVotedPOL, btnVotePOL, europeanParliamentElection, "noOfEuroVotes", "hasVotedEuro", "POL", CNP);
    }

    public void votePRU() {
        placeVote(imgVotedPRU, btnVotePRU, europeanParliamentElection, "noOfEuroVotes", "hasVotedEuro", "PRU", CNP);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblElection.setText("ALEGERI EUROPARLAMENTARE - " + europeanParliamentElection.getYearOfElection());
    }
}
