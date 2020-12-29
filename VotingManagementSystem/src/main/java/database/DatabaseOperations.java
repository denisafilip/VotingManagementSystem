package database;

import country.County;
import politicalParty.PoliticalParty;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseOperations {

    /** Queries for database, used in prepared statements */
    private static final String INSERT_USER = "INSERT INTO [dbo].[User] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_FROM_COUNTY = "SELECT id FROM [dbo].[County] WHERE name = ?";
    private static final String VALIDATE_USER = "SELECT * from [dbo].[User] WHERE mail = ? and password = ? or CNP = ?";
    private static final String FIND_USER_FROM_CNP = "SELECT * from [dbo].[User] WHERE CNP = ?";
    private static final String FIND_USER_COUNTY = "SELECT idCounty, name from [dbo].[User] INNER JOIN [dbo].[County] ON [User].idCounty = County.id WHERE CNP = ?";
    private static final String IS_USER_AN_ADMIN = "SELECT isAdmin from [dbo].[User] WHERE CNP = ?";
    private static final String MAKE_USER_AN_ADMIN = "UPDATE [dbo].[User] SET isAdmin = 'true' WHERE mail = ?";
    private static final String GET_USER_BY_MAIL_AND_PASSWORD = "SELECT * from [dbo].[User] WHERE mail = ? and password = ?";

    /** Realizes the connection to the database only once for the entire project */
    static Connection conn = DatabaseConnection.getConnection();

    public static String CNP;

    public void closePreparedStatement(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null && !preparedStatement.isClosed()) {
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void closeStatement(Statement statement) {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
     * @param hasVotedPresidential - false at this point, because the user only registered, it did not vote yet
     * @param hasVotedEuro - false at this point, because the user only registered, it did not vote yet
     * @param hasVotedLocal - false at this point, because the user only registered, it did not vote yet
     * @param hasVotedParliament - false at this point, because the user only registered, it did not vote yet
     * @param hasVotedReferendum - false at this point, because the user only registered, it did not vote yet
     * inserts user's data into User table, from Microsoft SQL Server Database
     */
    public void insertUserIntoDatabase(String firstName, String lastName, String email, String password, String gender, LocalDate dateOfBirth,
                                       int age, String CNP, County county, Boolean hasVotedPresidential, Boolean hasVotedEuro,
                                       Boolean hasVotedLocal, Boolean hasVotedParliament, Boolean hasVotedReferendum, Boolean hasVotedReferendum2, Boolean isAdmin) {
        PreparedStatement preparedStatement = null;
        PreparedStatement selectFromCounty = null;
        ResultSet resultSet = null;
        try {

            selectFromCounty = conn.prepareStatement(SELECT_FROM_COUNTY);
            selectFromCounty.setString(1, county.getCountyName());

            resultSet = selectFromCounty.executeQuery();
            int idCounty = 0;
            while (resultSet.next()) {
                idCounty = resultSet.getInt("id");
            }

            preparedStatement = conn.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, gender);
            preparedStatement.setDate(6, Date.valueOf(dateOfBirth));
            preparedStatement.setInt(7, age);
            preparedStatement.setString(8, CNP);
            preparedStatement.setInt(9, idCounty);
            preparedStatement.setBoolean(10, hasVotedPresidential);
            preparedStatement.setBoolean(11, hasVotedEuro);
            preparedStatement.setBoolean(12, hasVotedLocal);
            preparedStatement.setBoolean(13, hasVotedParliament);
            preparedStatement.setBoolean(14, hasVotedReferendum);
            preparedStatement.setBoolean(15, hasVotedReferendum2);
            preparedStatement.setBoolean(16, isAdmin);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(preparedStatement);
            closePreparedStatement(selectFromCounty);
            closeResultSet(resultSet);
        }
    }

    /**
     *
     * @param email - user's email
     * @param password - user's password
     * @param CNP - user's CNP
     * @return true if the user is already found in the database
     */
    public Boolean isUserInDatabaseWithCNP(String email, String password, String CNP) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(VALIDATE_USER);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, CNP);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return true;
    }

    /**
     * Get user from database, based on email and password combination - used for log in.
     * @return true if the user is found in the database
     */
    public Boolean isUserInDatabaseForLogIn(String email, String password) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(GET_USER_BY_MAIL_AND_PASSWORD);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return false;
    }

    /**
     * Getting the CNP of the user, based on his email and password combination - for log in
     */
    public void getUserCNP(String email, String password) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(GET_USER_BY_MAIL_AND_PASSWORD);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CNP = resultSet.getString("CNP");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
    }

    /**
     * @param CNP - CNP of user
     * @param typeOfVote - vote to be checked for (presidential, euro, local, parliament, referendum)
     * @return true if user has voted, false on the contrary
     */
    public Boolean hasUserVoted(String CNP, String typeOfVote) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(FIND_USER_FROM_CNP);
            preparedStatement.setString(1, CNP);

            resultSet = preparedStatement.executeQuery();
            boolean hasVoted = false;
            while(resultSet.next()) {
                hasVoted = resultSet.getBoolean(typeOfVote);
            }
            return hasVoted;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(preparedStatement);
            closeResultSet(resultSet);
        }
        return true;
    }

    /**
     * Function that increments the number of specific votes of a political party in the PoliticalParty table from the SQL Server database, after an user has voted for them
     * @param typeOfVote - the type of vote we want to increment (presidential, euro, local, parliament)
     * @param politicalParty - the political party for which we want to increment the number of votes
     */
    public void incrementVotesForParty(String typeOfVote, PoliticalParty politicalParty) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String query = "UPDATE [dbo].[PoliticalParty] SET " + typeOfVote + " = " + typeOfVote + " + 1 WHERE abbreviation = '" + politicalParty.getAbbreviation() + "'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    /** After an user placed his vote, change his voting status for the respective election to true - this doesn't allow an user to vot multiple
     * times in the same election.
     */
    public void updateUserVotingStatus(String typeOfVote, String CNP) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String query = "UPDATE [dbo].[User] SET " + typeOfVote + " = 'true' WHERE CNP = '" + CNP + "'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(statement);
        }
    }

    /** Gets the ID of the user's county (the one that appears on the CNP) */
    public int getUserCountyById(String CNP) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(FIND_USER_COUNTY);
            preparedStatement.setString(1, CNP);
            resultSet = preparedStatement.executeQuery();
            int idCounty = 0;
            while (resultSet.next()) {
                idCounty = resultSet.getInt("idCounty");
            }
            return idCounty;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** @return the name of the county the user (that appears on the CNP) */
    public String getUserCountyByName(String CNP) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(FIND_USER_COUNTY);
            preparedStatement.setString(1, CNP);
            resultSet = preparedStatement.executeQuery();
            String countyName = null;
            while (resultSet.next()) {
                countyName = resultSet.getString("name");
            }
            return countyName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** After an user has voted, increment the total number of votes placed in the user's county */
    public void incrementVotesForCounty(String typeOfVote, String CNP) {
        Statement increaseNoOfVotesForCounty = null;
        try {
            int idCounty = getUserCountyById(CNP);
            String query = "UPDATE [dbo].[County] SET " + typeOfVote + " = " + typeOfVote + " + 1 WHERE id = " + idCounty;
            increaseNoOfVotesForCounty = conn.createStatement();
            increaseNoOfVotesForCounty.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(increaseNoOfVotesForCounty);
        }
    }

    /**
     * @param typeOfVote - the type of vote we want to select the number of
     * @param politicalPartyAbbr - abbreviation of the political party whose votes we want to see
     * @return the number of votes of the political party obtained in a specific election
     */
    public int getNoOfVotesPoliticalParty(String typeOfVote, String politicalPartyAbbr) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT " + typeOfVote + " FROM [dbo].[PoliticalParty] WHERE abbreviation = '" + politicalPartyAbbr + "'";
            statement = conn.createStatement();

            resultSet = statement.executeQuery(query);
            int noOfVotes = 0;
            while (resultSet.next()) {
                noOfVotes = resultSet.getInt(typeOfVote);
            }
            return noOfVotes;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return 0;
    }

    /** @return the number of votes placed in a county at a specific type of election */
    public int getNoOfVotesCounty(String typeOfVote, String countyName) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT " + typeOfVote + " FROM [dbo].[County] WHERE name = '" + countyName + "'";
            statement = conn.createStatement();

            resultSet = statement.executeQuery(query);
            int noOfVotes = 0;
            while (resultSet.next()) {
                noOfVotes = resultSet.getInt(typeOfVote);
            }
            return noOfVotes;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return 0;
    }

    /** Function that checks the user's admin status, in order to grant him permission to the admin page.
     * @return true if user is an admin, who can see statistics about voting */
    public Boolean checkIfUserIsAdmin(String CNP) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(IS_USER_AN_ADMIN);
            preparedStatement.setString(1, CNP);
            resultSet = preparedStatement.executeQuery();

            boolean isAdmin = false;
            while (resultSet.next()) {
                isAdmin = resultSet.getBoolean("isAdmin");
            }
            return isAdmin;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
        }
        return false;
    }

    public void grantAdminPermissions(String email) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(MAKE_USER_AN_ADMIN);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(preparedStatement);
        }
    }

}
