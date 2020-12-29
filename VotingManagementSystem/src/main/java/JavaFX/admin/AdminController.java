package JavaFX.admin;

import JavaFX.database.ParentController;
import country.Country;
import country.County;
import election.Election;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import politicalParty.PoliticalParty;
import webScraping.Scraping;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminController extends ParentController implements Initializable {
    @FXML private Button btnBack;

    @FXML private ImageView imgVotesUSR;
    @FXML private ImageView imgVotesPNL;
    @FXML private ImageView imgVotesPSD;
    @FXML private ImageView imgVotesAUR;
    @FXML private ImageView imgVotesPMP;
    @FXML private ImageView imgVotesPRO;
    @FXML private ImageView imgVotesUDMR;
    @FXML private ImageView imgVotesPOL;
    @FXML private ImageView imgVotesPRU;

    @FXML private Label lblElection;
    @FXML private AnchorPane anchorPane;

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    @FXML private final BarChart<String, Number> barChartCounties = new BarChart<>(xAxis, yAxis);
    @FXML private final BarChart<String, Number> barChartRef1 = new BarChart<>(xAxis, yAxis);
    @FXML private final BarChart<String, Number> barChartRef2 = new BarChart<>(xAxis, yAxis);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblElection.setText(buttonPressed);
        if (buttonPressed.equals("ADMIN - STATISTICI VOT - Referendum")) {
            displayVotesReferendum();
        } else {
            displayVotesPoliticalParties();
            displayVotesCounties();
        }
    }

    public void goBackToAdminMenu(ActionEvent actionEvent) throws IOException {
        changeScene(actionEvent, "/adminMenu.fxml", "Meniu administrator");
    }

    public void displayVotesReferendum() {
        Scraping scraper = new Scraping();
        Country Romania = scraper.webScrapingCounties();

        barChartCounties.setVisible(false);
        barChartRef1.setVisible(true);
        barChartRef2.setVisible(true);

        setUpBarChartForReferendum(Romania, barChartRef1, "Voturi pe Județe Întrebare 1 Referendum", "noOfYesReferendumVotes", "noOfNoReferendumVotes");
        setUpBarChartForReferendum(Romania, barChartRef2, "Voturi pe Județe Întrebare 2 Referendum", "noOfYesReferendum2Votes", "noOfNoReferendum2Votes");

        changeVisibilityOfImageViews();

        int noOfVotesYes1 = 0, noOfVotesYes2 = 0, noOfVotesNo1 = 0, noOfVotesNo2 = 0;
        for (County c : Romania.getCounties()) {
            noOfVotesYes1 += getNoOfVotesCounty("noOfYesReferendumVotes", c.getCountyName());
            noOfVotesYes2 += getNoOfVotesCounty("noOfYesReferendum2Votes", c.getCountyName());
            noOfVotesNo1 += getNoOfVotesCounty("noOfNoReferendumVotes", c.getCountyName());
            noOfVotesNo2 += getNoOfVotesCounty("noOfNoReferendum2Votes", c.getCountyName());
        }
        final int layoutX = 815;
        int layoutY = 228;
        int distanceBetweenLabels = 80;

        createLabelForReferendum(layoutY, layoutX, noOfVotesYes1, 1, "pro");
        createLabelForReferendum(layoutY + distanceBetweenLabels, layoutX, noOfVotesNo1, 1, "contra");
        createLabelForReferendum(layoutY + 2*distanceBetweenLabels, layoutX, noOfVotesYes2, 2, "pro");
        createLabelForReferendum(layoutY + 3*distanceBetweenLabels, layoutX, noOfVotesNo2, 2, "contra");

    }

    public void changeVisibilityOfImageViews() {
        imgVotesAUR.setVisible(false);
        imgVotesUSR.setVisible(false);
        imgVotesPNL.setVisible(false);
        imgVotesPSD.setVisible(false);
        imgVotesUDMR.setVisible(false);
        imgVotesPOL.setVisible(false);
        imgVotesPMP.setVisible(false);
        imgVotesPRU.setVisible(false);
        imgVotesPRO.setVisible(false);
    }

    public void createLabelForReferendum(int layoutY, int layoutX, int noOfVotes, int noOfQuestion, String positionToQuestion) {
        Label lbl = new Label();
        lbl.setText("Întrebare " + noOfQuestion + ": voturi " + positionToQuestion + " - " + noOfVotes);
        lbl.setLayoutX(layoutX);
        lbl.setLayoutY(layoutY);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        anchorPane.getChildren().add(lbl);
    }

    public void setUpBarChartForReferendum(Country Romania, BarChart<String, Number> barChart, String barChartTitle, String yesVotes, String noVotes) {
        barChart.setTitle(barChartTitle);
        Romania.sortCountiesAlphabetically(Romania);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Număr de voturi");
        for (County c : Romania.getCounties()) {
            int noOfVotesNo = getNoOfVotesCounty(noVotes, c.getCountyName());
            int noOfVotesYes = getNoOfVotesCounty(yesVotes, c.getCountyName());
            series.getData().add(new XYChart.Data<>(c.getCountyName(), noOfVotesYes + noOfVotesNo));
        }
        barChart.getData().add(series);
    }

    public void displayVotesCounties() {
        Scraping scraper = new Scraping();
        Country Romania = scraper.webScrapingCounties();
        barChartCounties.setVisible(true);
        barChartRef1.setVisible(false);
        barChartRef2.setVisible(false);
        barChartCounties.setTitle("Voturi pe Județe");
        Romania.sortCountiesAlphabetically(Romania);
        String typeOfVote = setTypeOfVote();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Număr de voturi");
        for (County c : Romania.getCounties()) {
            int noOfVotes = getNoOfVotesCounty(typeOfVote, c.getCountyName());
            series.getData().add(new XYChart.Data<>(c.getCountyName(), noOfVotes));
        }
        barChartCounties.getData().add(series);

    }

    public void displayVotesPoliticalParties() {
        Election election = new Election();

        final int DISTANCE_BETWEEN_LABELS = 80;
        final int LAYOUT_X = 931;
        int layoutY = 60;
        xAxis.setLabel("Județ");
        yAxis.setLabel("Voturi");
        String typeOfVote = setTypeOfVote();
        System.out.println(typeOfVote);
        for (Map.Entry<String, PoliticalParty> entry : election.getPoliticalParties().entrySet()) {
            PoliticalParty p = entry.getValue();
            int noOfVotes = getNoOfVotesPoliticalParty(typeOfVote, p.getAbbreviation());
            System.out.println(noOfVotes);
            Label lbl = new Label();
            lbl.setText("voturi: " + noOfVotes);
            lbl.setLayoutX(LAYOUT_X);
            lbl.setLayoutY(layoutY);
            lbl.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            layoutY += DISTANCE_BETWEEN_LABELS;
            anchorPane.getChildren().add(lbl);
        }
    }

    private String setTypeOfVote() {
        String typeOfVote;
        switch (buttonPressed) {
            case "ADMIN - STATISTICI VOT - Alegeri Europarlamentare":
                typeOfVote = "noOfEuroVotes";
                break;
            case "ADMIN - STATISTICI VOT - Alegeri Prezidențiale":
                typeOfVote = "noOfPresidentialVotes";
                break;
            case "ADMIN - STATISTICI VOT - Alegeri Parlamentare":
                typeOfVote = "noOfParliamentVotes";
                break;
            case "ADMIN - STATISTICI VOT - Alegeri Locale":
                typeOfVote = "noOfLocalVotes";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + buttonPressed);
        }
        return typeOfVote;
    }
}
