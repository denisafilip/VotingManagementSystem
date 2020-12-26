package sample;

import country.Country;
import election.ParliamentElection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import webScraping.Scraping;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {



    Stage window;
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/registration.fxml"));
        Parent root = myLoader.load();
        window.setTitle("Log in");
        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }


    public static void main(String[] args) {

        Scraping scraper = new Scraping();
        Country Romania = scraper.webScrapingCounties();
        ParliamentElection parliamentElection = scraper.webScrapingPCandidates(Romania);

        Connection conn = null;
        ResultSet resultSet = null;

        try {
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=VotingManagementSystem;";
            String user = "denisafilip";
            String password = "PasswordDB";
            conn = DriverManager.getConnection(dbURL, user, password);


            /*adding Romania's counties into County table in SQL Server Database

            Romania.sortCountiesAlphabetically(Romania);
            for (County c : Romania.getCounties()) {
                String query = "INSERT INTO [dbo].[County]" + "VALUES(?) ";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, c.getCountyName());
                preparedStatement.executeUpdate();
            }*/

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        launch(args);
    }
}
