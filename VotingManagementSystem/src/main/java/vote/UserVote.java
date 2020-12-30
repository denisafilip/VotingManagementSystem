package vote;

import election.enums.typeOfElection;

public class UserVote extends Vote {
    /** Status of the vote: true if it has been placed by the user, false on the contrary.*/
    private boolean status;

    public UserVote(typeOfElection type) {
        super(type);
    }

    public UserVote(typeOfElection type, boolean status) {
        super(type);
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
