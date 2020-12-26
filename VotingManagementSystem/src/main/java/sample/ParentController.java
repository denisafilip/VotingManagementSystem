package sample;

import country.County;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ParentController {

    private static final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;databaseName=VotingManagementSystem;";
    private static final String DATABASE_USERNAME = "denisafilip";
    private static final String DATABASE_PASSWORD = "PasswordDB";
    private static final String INSERT_USER = "INSERT INTO [dbo].[User] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_FROM_COUNTY = "SELECT id FROM [dbo].[County] WHERE name = ?";
    private static final String VALIDATE_USER = "SELECT * from [dbo].[User] WHERE mail = ? and password = ? and CNP = ?";


    public void goForwardToScene(ActionEvent event, String fxmlFilePath, String windowTitle) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlFilePath));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setTitle(windowTitle);
        window.setScene(scene);
        window.show();
    }

    public void goBackToScene(ActionEvent event, String fxmlFilePath) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlFilePath));
        Scene  scene = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    /**
     *
     * @param firstName - first name of User
     * @param lastName - last name of User
     * @param email - mail of User
     * @param password - password of User
     * @param gender - gender of User
     * @param dateOfBirth - date of birth of User
     * @param age - age of User
     * @param CNP - CNP of User
     * @param county - county of User
     * @param hasVoted - false at this point, because the user only register, it did not vote yet
     *
     * inserts user's data into User table, from Microsoft SQL Server Database
     */
    public void insertIntoDatabase(String firstName, String lastName, String email, String password, String gender, LocalDate dateOfBirth, int age, String CNP, County county, Boolean hasVoted) {
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

            PreparedStatement selectFromCounty = conn.prepareStatement(SELECT_FROM_COUNTY);
            selectFromCounty.setString(1, county.getCountyName());

            ResultSet resultSet = selectFromCounty.executeQuery();
            int idCounty = 0;
            while (resultSet.next()) {
                idCounty = resultSet.getInt("id");
            }

            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, gender);
            preparedStatement.setDate(6, Date.valueOf(dateOfBirth));
            preparedStatement.setInt(7, age);
            preparedStatement.setString(8, CNP);
            preparedStatement.setInt(9, idCounty);
            preparedStatement.setBoolean(10, hasVoted);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean isUserInDatabase(String email, String password, String CNP) {
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(VALIDATE_USER);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, CNP);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
