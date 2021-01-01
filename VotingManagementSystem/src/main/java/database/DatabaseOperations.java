package database;

import country.County;
import election.Election;
import election.enums.typeOfElection;
import election.referendum.Referendum;
import election.referendum.ReferendumPosition;
import politicalParty.PoliticalParty;
import user.User;
import vote.ReferendumPairVote;
import vote.ReferendumVote;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseOperations {

    /** Queries for database, used in prepared statements */
    private static final String INSERT_USER = "INSERT INTO [dbo].[User] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_FROM_COUNTY = "SELECT id FROM [dbo].[County] WHERE name = ?";
    private static final String VALIDATE_USER = "SELECT * from [dbo].[User] WHERE mail = ? and password = ? or CNP = ?";
    private static final String FIND_USER_FROM_CNP = "SELECT * from [dbo].[User] WHERE CNP = ?";
    private static final String FIND_USER_COUNTY = "SELECT idCounty, name from [dbo].[User] INNER JOIN [dbo].[County] ON [User].idCounty = County.id WHERE CNP = ?";
    private static final String IS_USER_AN_ADMIN = "SELECT isAdmin from [dbo].[User] WHERE CNP = ?";
    private static final String MAKE_USER_AN_ADMIN = "UPDATE [dbo].[User] SET isAdmin = 'true' WHERE mail = ?";
    private static final String GET_USER_BY_MAIL_AND_PASSWORD = "SELECT * from [dbo].[User] WHERE mail = ? and password = ?";

    private static final double NO_OF_VOTERS_IN_ROMANIA = 18191396;

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
     * @param u - the User which we want to insert into the database
     * inserts user's data into User table, from Microsoft SQL Server Database
     */
    public void insertUserIntoDatabase(User u) {
        PreparedStatement preparedStatement = null;
        PreparedStatement selectFromCounty = null;
        ResultSet resultSet = null;
        try {
            selectFromCounty = conn.prepareStatement(SELECT_FROM_COUNTY);
            selectFromCounty.setString(1, u.getCounty().getCountyName());

            resultSet = selectFromCounty.executeQuery();
            int idCounty = 0;
            while (resultSet.next()) {
                idCounty = resultSet.getInt("id");
            }

            preparedStatement = conn.prepareStatement(INSERT_USER);
            preparedStatement.setString(1, u.getFirstName());
            preparedStatement.setString(2, u.getLastName());
            preparedStatement.setString(3, u.getMail());
            preparedStatement.setString(4, u.getPassword());
            preparedStatement.setString(5, u.getGender());
            preparedStatement.setDate(6, Date.valueOf(u.getDateOfBirth()));
            preparedStatement.setInt(7, u.getAge());
            preparedStatement.setString(8, u.getCNP());
            preparedStatement.setInt(9, idCounty);
            preparedStatement.setBoolean(10, u.getPresidential());
            preparedStatement.setBoolean(11, u.getEuropeanParliament());
            preparedStatement.setBoolean(12, u.getLocal());
            preparedStatement.setBoolean(13, u.getFirstReferendum());
            preparedStatement.setBoolean(14, u.getSecondReferendum());
            preparedStatement.setBoolean(15, u.isAdmin());
            preparedStatement.setBoolean(16, u.getSenateParliament());
            preparedStatement.setBoolean(17, u.getDeputiesParliament());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePreparedStatement(preparedStatement);
            closePreparedStatement(selectFromCounty);
            closeResultSet(resultSet);
        }
    }

    /** @return true if the user is already found in the database */
    public Boolean isUserInDatabaseWithCNP(User u) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(VALIDATE_USER);
            preparedStatement.setString(1, u.getMail());
            preparedStatement.setString(2, u.getPassword());
            preparedStatement.setString(3, u.getCNP());

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
    public Boolean isUserInDatabaseForLogIn(User u) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(GET_USER_BY_MAIL_AND_PASSWORD);
            preparedStatement.setString(1, u.getMail());
            preparedStatement.setString(2, u.getPassword());
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

    /** Getting the CNP of the user, based on his email and password combination - for log in */
    public void getUserCNP(User u) {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(GET_USER_BY_MAIL_AND_PASSWORD);
            preparedStatement.setString(1, u.getMail());
            preparedStatement.setString(2, u.getPassword());
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
     * @param election - the election for which we want to increment the number of votes (presidential, euro, local, parliament)
     * @return true if user has voted, false on the contrary
     */
    public Boolean hasUserVoted(String CNP, Election election) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(FIND_USER_FROM_CNP);
            preparedStatement.setString(1, CNP);

            resultSet = preparedStatement.executeQuery();
            boolean hasVoted = false;
            while(resultSet.next()) {
                hasVoted = resultSet.getBoolean(election.getType().getLabel());
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
     * @param election - the election for which we want to increment the number of votes (presidential, euro, local, parliament)
     * @param politicalParty - the political party for which we want to increment the number of votes
     */
    public void incrementVotesForParty(Election election, PoliticalParty politicalParty) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String query = "UPDATE [dbo].[PoliticalParty] SET " + election.getType().getLabel() + " = " + election.getType().getLabel() + " + 1 WHERE abbreviation = '" + politicalParty.getAbbreviation() + "'";
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
    public void updateUserVotingStatus(Election election, String CNP) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String query = "UPDATE [dbo].[User] SET " + election.getType().getLabel() + " = 'true' WHERE CNP = '" + CNP + "'";
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
        } finally {
            closeResultSet(resultSet);
            closePreparedStatement(preparedStatement);
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
    public void incrementVotesForCounty(Election election, String CNP) {
        Statement increaseNoOfVotesForCounty = null;
        try {
            int idCounty = getUserCountyById(CNP);

            String query = "UPDATE [dbo].[County] SET " + election.getType().getLabel() + " = " + election.getType().getLabel() + " + 1 WHERE id = " + idCounty;
            increaseNoOfVotesForCounty = conn.createStatement();
            increaseNoOfVotesForCounty.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(increaseNoOfVotesForCounty);
        }
    }

    public void incrementVotesForCountyReferendum(Election election, String CNP, String label) {
        Statement increaseNoOfVotesForCounty = null;
        try {
            String voteName = election.getType().getLabel() + label;
            int idCounty = getUserCountyById(CNP);
            String query = "UPDATE [dbo].[County] SET " + voteName + " = " + voteName + " + 1 WHERE id = " + idCounty;
            increaseNoOfVotesForCounty = conn.createStatement();
            increaseNoOfVotesForCounty.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeStatement(increaseNoOfVotesForCounty);
        }
    }

    /**
     * @param election - the election for which we want to select the number of votes
     * @param politicalPartyAbbr - abbreviation of the political party whose votes we want to see
     * @return the number of votes of the political party obtained in a specific election
     */
    public int getNoOfVotesPoliticalParty(Election election, String politicalPartyAbbr) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT " + election.getType().getLabel() + " FROM [dbo].[PoliticalParty] WHERE abbreviation = '" + politicalPartyAbbr + "'";
            statement = conn.createStatement();

            resultSet = statement.executeQuery(query);
            int noOfVotes = 0;
            while (resultSet.next()) {
                noOfVotes = resultSet.getInt(election.getType().getLabel());
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
    public int getNoOfVotesCounty(Election election, String countyName) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT " + election.getType().getLabel() + " FROM [dbo].[County] WHERE name = N'" + countyName + "'";
            statement = conn.createStatement();

            resultSet = statement.executeQuery(query);
            int noOfVotes = 0;
            while (resultSet.next()) {
                noOfVotes = resultSet.getInt(election.getType().getLabel());
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

    /** @return a pair of Referendum Votes, containing the number of pro and against votes placed in a county for a referendum question*/
    public ReferendumPairVote getNoOfVotesCountyReferendum(Referendum referendum, String countyName) {
        Statement statement = null;
        Statement statement1 = null;
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        try {
            String againstVoteName = referendum.getType().getLabel() + referendum.getReferendumVotes().getAgainstVote().getPosition().getLabel();
            String proVoteName = referendum.getType().getLabel() + referendum.getReferendumVotes().getProVote().getPosition().getLabel();

            String query2 = "SELECT " + proVoteName + " FROM [dbo].[County] WHERE name = N'" + countyName + "'";
            String query = "SELECT " + againstVoteName + " FROM [dbo].[County] WHERE name = N'" + countyName + "'";
            statement = conn.createStatement();
            statement1 = conn.createStatement();
            resultSet = statement.executeQuery(query);
            ReferendumPairVote referendumVotes = new ReferendumPairVote(new ReferendumVote(referendum.getType(), ReferendumPosition.PRO), new ReferendumVote(referendum.getType(), ReferendumPosition.AGAINST));
            while (resultSet.next()) {
                referendumVotes.getAgainstVote().setNoOfVotes(resultSet.getInt(againstVoteName));
            }
            resultSet1 = statement1.executeQuery(query2);
            while (resultSet1.next()) {
                referendumVotes.getProVote().setNoOfVotes(resultSet1.getInt(proVoteName));
            }
            return referendumVotes;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeResultSet(resultSet1);
            closeStatement(statement);
            closeStatement(statement1);
        }
        return null;
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

    public double computeVoteAttendance(Election election) {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT SUM(" + election.getType().getLabel() + ") AS SUM FROM [dbo].[County]";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            double sumOfVotes = 0;
            while (resultSet.next()) {
                sumOfVotes = resultSet.getInt("SUM");
            }
            return (sumOfVotes/NO_OF_VOTERS_IN_ROMANIA) * 100.0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResultSet(resultSet);
            closeStatement(statement);
        }
        return 0.0;
    }
}
