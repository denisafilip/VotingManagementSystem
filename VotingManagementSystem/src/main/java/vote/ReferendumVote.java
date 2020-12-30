package vote;

import election.enums.typeOfElection;
import election.referendum.ReferendumPosition;

public class ReferendumVote extends Vote {
    /** Position of user regarding the question of the referendum. Can be pro or against.*/
    private ReferendumPosition position;
    private int noOfVotes;

    public ReferendumVote(typeOfElection type, ReferendumPosition position) {
        super(type);
        this.position = position;
    }

    public ReferendumPosition getPosition() {
        return position;
    }

    public void setPosition(ReferendumPosition position) {
        this.position = position;
    }

    public int getNoOfVotes() {
        return noOfVotes;
    }

    public void setNoOfVotes(int noOfVotes) {
        this.noOfVotes = noOfVotes;
    }

    public void increaseNoOfVotes(int noOfVotes) {
        this.noOfVotes += noOfVotes;
    }
}
