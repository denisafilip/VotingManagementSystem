module VotingManagementSystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires java.sql;
    requires org.apache.commons.pool2;
    requires commons.dbcp;

    opens JavaFX to javafx.fxml;
    opens JavaFX.elections to javafx.fxml;
    opens JavaFX.admin to javafx.fxml;
    opens JavaFX.registration to javafx.fxml;
    opens database to javafx.fxml;

    exports JavaFX;
    exports JavaFX.elections;
    exports JavaFX.admin;
    exports JavaFX.registration;
    exports database;
    exports user;
    exports politicalParty;
    exports webScraping;
    exports election;
    exports country;
    exports election.enums;
    exports vote;
    exports election.referendum;
}