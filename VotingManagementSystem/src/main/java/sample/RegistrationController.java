package sample;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Window;
import user.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController extends ParentController implements Initializable {

    @FXML private Label lblFirstName;
    @FXML private Label lblLastName;
    @FXML private Label lblEmail;
    @FXML private Label lblPassword;
    @FXML private Label lblConfirmPassword;
    @FXML private Label lblBirthday;
    @FXML private Label lblGender;
    @FXML private Label lblCNP;

    @FXML
    private Button btnVote;
    @FXML
    private DatePicker dateBirthday;
    ObservableList<String> options = FXCollections.observableArrayList("F", "M");
    @FXML
    private ComboBox<String> comboGender;
    @FXML
    private TextField txtCNP;
    @FXML
    private PasswordField passPassword;
    @FXML
    private PasswordField passConfirmPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboGender.setItems(options);
    }

    public void setOnAction(ActionEvent event) throws IOException {
        Window owner = btnVote.getScene().getWindow();

        clearErrorLabels();
        if (txtLastName.getText().isEmpty()) {
            lblLastName.setText("Vă rog să vă introduceți numele.");
            return;
        }
        if (txtFirstName.getText().isEmpty()) {
            lblFirstName.setText("Vă rog să vă introduceți prenumele.");
            return;
        }
        if (txtEmail.getText().isEmpty()) {
            lblEmail.setText("Vă rog să vă introduceți email-ul.");
            return;
        }
        if (passPassword.getText().isEmpty()) {
            lblPassword.setText("Vă rog să vă introduceți parola.");
            return;
        }
        if (passPassword.getText().length() < 6) {
            lblPassword.setText("Parola trebuie să conțină cel puțin 6 caractere.");
            return;
        }
        if (passConfirmPassword.getText().isEmpty()) {
            lblConfirmPassword.setText("Vă rog să vă confirmați parola.");
            return;
        }
        if (!passPassword.getText().equals(passConfirmPassword.getText())) {
            lblConfirmPassword.setText("Parolele nu coincid.");
            return;
        }
        if (dateBirthday.getValue() == null) {
            lblBirthday.setText("Vă rog să vă introduceți ziua de naștere.");
            return;
        }
        if (comboGender.getSelectionModel().getSelectedItem() == null) {
            lblGender.setText("Vă rog să vă alegeți genul.");
            return;
        }
        if (txtCNP.getText().isEmpty()) {
            lblCNP.setText("Vă rog să vă introduceți CNP-ul.");
            return;
        }
        User u = new User(txtEmail.getText(), passPassword.getText(), txtFirstName.getText(), txtLastName.getText(), comboGender.getValue(), txtCNP.getText(), dateBirthday.getValue());
        if (!u.verifyLastName()) {
            lblLastName.setText("Numele trebuie să conțină doar litere.");
            return;
        }
        if (!u.verifyFirstName()) {
            lblFirstName.setText("Prenumele trebuie să conțină doar litere.");
            return;
        }
        if (!u.isMajor()) {
            lblBirthday.setText("Trebuie să aveți peste 18 ani pentru a putea vota.");
            return;
        }
        if (!u.verifyCNP(lblCNP)) {
            return;
        }
        if (!isUserInDatabase(u.getMail(), u.getPassword(), u.getCNP())) {
            insertIntoDatabase(u.getFirstName(), u.getLastName(), u.getMail(), u.getPassword(), u.getGender(), u.getDateOfBirth(), u.getAge(), u.getCNP(), u.getCounty(), false);
        } else {
            showAlert(Alert.AlertType.INFORMATION, owner, "Înregistrare nereușită", "Puteți să votați o singură dată.");
            return;
        }

        goForwardToScene(event, "/electionType.fxml", "Alegeri");

    }

    /**
     * clearing the text of the error labels from the sign up form
     */
    public void clearErrorLabels() {
        lblFirstName.setText("");
        lblLastName.setText("");
        lblEmail.setText("");
        lblPassword.setText("");
        lblConfirmPassword.setText("");
        lblBirthday.setText("");
        lblGender.setText("");
        lblCNP.setText("");
    }
}
