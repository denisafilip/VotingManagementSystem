package JavaFX.elections;

import JavaFX.database.ParentController;
import election.Referendum;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

public class ReferendumController extends ParentController implements Initializable {
    @FXML private ImageView imgVotedYes1;
    @FXML private ImageView imgVotedYes2;
    @FXML private ImageView imgVotedNo1;
    @FXML private ImageView imgVotedNo2;

    @FXML private Label lblElection;
    @FXML private Label lblQuestion1;
    @FXML private Label lblQuestion2;
    @FXML private Button btnBack;
    @FXML private Button btnYes1;
    @FXML private Button btnYes2;
    @FXML private Button btnNo1;
    @FXML private Button btnNo2;

    private final Referendum referendum1 = new Referendum("Sunteți de acord cu interzicerea amnistiei și grațierii pentru infracțiuni de corupție?", Year.of(2019));
    private final Referendum referendum2 = new Referendum("Sunteți de acord cu interzicerea adoptării de către Guvern a ordonanțelor de urgență în domeniul infracțiunilor, pedepselor și al organizării judiciare și cu extinderea dreptului de a ataca ordonanțele direct la Curtea Constituțională?", Year.of(2019));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblElection.setText("REFERENDUM - " + referendum1.getYearOfReferendum());
        lblQuestion1.setText(referendum1.getQuestion());
        lblQuestion2.setText(referendum2.getQuestion());
    }

    public void goBackToElection(ActionEvent event) throws IOException {
        changeScene(event, "/electionType.fxml", "Alegeri");
    }

    public void voteYesInFirstReferendum() {
        placeVoteReferendum(imgVotedYes1, btnYes1, "hasVotedReferendum", "noOfYesReferendumVotes");
    }

    public void voteYesInSecondReferendum() {
        placeVoteReferendum(imgVotedYes2, btnYes2, "hasVotedReferendum2", "noOfYesReferendum2Votes");
    }

    public void voteNoInFirstReferendum() {
        placeVoteReferendum(imgVotedNo1, btnNo1, "hasVotedReferendum", "noOfNoReferendumVotes");
    }

    public void voteNoInSecondReferendum() {
        placeVoteReferendum(imgVotedNo2, btnNo2, "hasVotedReferendum2", "noOfNoReferendum2Votes");
    }

    public void placeVoteReferendum(ImageView img, Button button, String hasUserVoted, String typeOfVote) {
        Window owner = button.getScene().getWindow();
        if (!hasUserVoted(CNP, hasUserVoted)) {
            incrementVotesForCounty(typeOfVote, CNP);
            updateUserVotingStatus(hasUserVoted, CNP);
            img.setVisible(true);
        } else {
            showAlert(Alert.AlertType.ERROR, owner,"Vot nereușit!", "Puteți să votați o singură dată în unui referendum.");
        }
    }
}
