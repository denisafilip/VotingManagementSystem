module VotingManagementSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires java.sql;

    opens sample to javafx.fxml;
    exports sample;
    exports user;
    exports politicalParty;
    exports webScraping;
    exports election;
    exports country;
}