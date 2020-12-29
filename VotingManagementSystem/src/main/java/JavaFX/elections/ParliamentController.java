package JavaFX.elections;

import JavaFX.database.ParentController;
import country.Country;
import election.ParliamentElection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import user.Candidate;
import webScraping.Scraping;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ParliamentController extends ParentController implements Initializable {
    @FXML private ImageView imgVotedUSR;
    @FXML private ImageView imgVotedPNL;
    @FXML private ImageView imgVotedPSD;
    @FXML private ImageView imgVotedPMP;
    @FXML private ImageView imgVotedPRO;
    @FXML private ImageView imgVotedUDMR;

    @FXML private Label lblElection;
    @FXML private Button btnBack;
    @FXML private Button btnVoteUSR;
    @FXML private Button btnVotePNL;
    @FXML private Button btnVotePSD;
    @FXML private Button btnVoteUDMR;
    @FXML private Button btnVotePMP;
    @FXML private Button btnVotePRO;
    @FXML private ComboBox<String> comboDepUSR;
    @FXML private ComboBox<String> comboDepPNL;
    @FXML private ComboBox<String> comboDepPSD;
    @FXML private ComboBox<String> comboDepUDMR;
    @FXML private ComboBox<String> comboDepPMP;
    @FXML private ComboBox<String> comboDepPRO;
    @FXML private ComboBox<String> comboSenateUSR;
    @FXML private ComboBox<String> comboSenatePNL;
    @FXML private ComboBox<String> comboSenatePSD;
    @FXML private ComboBox<String> comboSenateUDMR;
    @FXML private ComboBox<String> comboSenatePMP;
    @FXML private ComboBox<String> comboSenatePRO;

    private final Scraping scraper = new Scraping();
    private final Country Romania = scraper.webScrapingCounties();
    private final ParliamentElection parliamentElection = scraper.webScrapingPCandidates(Romania);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String countyName = getUserCountyByName(CNP);
        lblElection.setText("ALEGERI PARLAMENTARE - " + countyName);
        addValuesToComboBox(countyName, "USR PLUS", comboDepUSR, comboSenateUSR);
        addValuesToComboBox(countyName, "PNL", comboDepPNL, comboSenatePNL);
        addValuesToComboBox(countyName, "PSD", comboDepPSD, comboSenatePSD);
        addValuesToComboBox(countyName,"Pro România", comboDepPRO, comboSenatePRO);
        addValuesToComboBox(countyName,"PMP", comboDepPMP, comboSenatePMP);
        addValuesToComboBox(countyName,"UDMR", comboDepUDMR, comboSenateUDMR);
    }

    public void goBackToElection(ActionEvent event) throws IOException {
        changeScene(event, "/electionType.fxml", "Alegeri");
    }

    public void voteUSR() {
        placeVote(imgVotedUSR, btnVoteUSR, parliamentElection, "noOfParliamentVotes", "hasVotedParliament", "USR PLUS", CNP);
    }

    public void votePNL() {
        placeVote(imgVotedPNL, btnVotePNL, parliamentElection, "noOfParliamentVotes", "hasVotedParliament", "PNL", CNP);
    }

    public void votePSD() {
        placeVote(imgVotedPSD, btnVotePSD, parliamentElection, "noOfParliamentVotes", "hasVotedParliament", "PSD", CNP);
    }

    public void voteUDMR() {
        placeVote(imgVotedUDMR, btnVoteUDMR, parliamentElection, "noOfParliamentVotes", "hasVotedParliament", "UDMR", CNP);
    }

    public void votePMP() {
        placeVote(imgVotedPMP, btnVotePMP, parliamentElection, "noOfParliamentVotes", "hasVotedParliament", "PMP", CNP);
    }

    public void votePRO() {
        placeVote(imgVotedPRO, btnVotePRO, parliamentElection, "noOfParliamentVotes", "hasVotedParliament", "PRO România", CNP);
    }



    public void addValuesToComboBox(String countyName, String politicalParty, ComboBox<String> comboBoxDep, ComboBox<String> comboBoxSenate) {
        List<String> candidatesDep = new ArrayList<>();
        for (Candidate c : parliamentElection.getChamberOfDeputies()) {
            if (c.getPoliticalPartyAbbr().equals(politicalParty) && c.getCounty().getCountyName().equals(countyName)) {
                candidatesDep.add(c.getFirstName());
            }
        }
        ObservableList<String> candidatesDepOptions = FXCollections.observableArrayList(candidatesDep);
        comboBoxDep.setItems(candidatesDepOptions);

        List<String> candidatesSenate = new ArrayList<>();
        for (Candidate c : parliamentElection.getSenate()) {
            if (c.getPoliticalPartyAbbr().equals(politicalParty) && c.getCounty().getCountyName().equals(countyName)) {
                candidatesSenate.add(c.getFirstName());
            }
        }
        ObservableList<String> candidatesSenateOptions = FXCollections.observableArrayList(candidatesSenate);
        comboBoxSenate.setItems(candidatesSenateOptions);
    }
}
