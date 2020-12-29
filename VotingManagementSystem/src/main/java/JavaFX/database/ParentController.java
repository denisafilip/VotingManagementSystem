package JavaFX.database;

import election.Election;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ParentController extends database.DatabaseOperations {


    public static String buttonPressed;
    /**
     * Function that changes from a scene to another, specified through the fxmlFilePath parameter
     */
    public void changeScene(ActionEvent event, String fxmlFilePath, String windowTitle) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlFilePath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle(windowTitle);
        window.setScene(scene);
        window.show();
    }

    /**
     * pop-up alert, to inform user after different actions
     *
     * @param alertType - the type of alert (ERROR, INFORMATION, CONFIRMATION, WARNING(
     * @param owner - the window the alert belongs to
     * @param title - title of the alert
     * @param message - message of the alert
     */
    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    /**
     * Function that handles all of the necessary changes after a vote is placed by the user - increasing the number of votes for the specific political party at the specific
     * election, increasing the number of votes in the county in which the user lives; setting the voting status of the user to true     */
    public void placeVote(ImageView img, Button button, Election election, String typeOfVote, String hasUserVoted, String politicalPartyAbbr, String CNP) {
        Window owner = button.getScene().getWindow();
        if (!hasUserVoted(ParentController.CNP, hasUserVoted)) {
            incrementVotesForParty(typeOfVote, election.getPoliticalParties().get(politicalPartyAbbr));
            incrementVotesForCounty(typeOfVote, ParentController.CNP);
            updateUserVotingStatus(hasUserVoted, ParentController.CNP);
            img.setVisible(true);
        } else {
            showAlert(Alert.AlertType.ERROR, owner,"Vot nereușit!", "Puteți să votați o singură dată în cadrul unor alegeri.");
        }
    }
}
