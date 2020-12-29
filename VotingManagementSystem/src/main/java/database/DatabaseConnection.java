package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that creates ONLY one connection to the Microsoft SQL Server database (with Singleton Design Pattern)
 */

public class DatabaseConnection {

    private static Connection conn = null;

    private static void createConnection() {
        String DATABASE_URL = "jdbc:sqlserver://localhost:1433;databaseName=VotingManagementSystem;";
        String DATABASE_USERNAME = "denisafilip";
        String DATABASE_PASSWORD = "PasswordDB";
        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            createConnection();
        }
        return conn;
    }

}
