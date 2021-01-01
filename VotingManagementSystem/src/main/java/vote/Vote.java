package vote;

import election.enums.typeOfElection;

public class Vote {
    private typeOfElection type;

    public Vote(typeOfElection type) {
        this.type = type;
    }

    public typeOfElection getType() {
        return type;
    }

    public void setType(typeOfElection type) {
        this.type = type;
    }
}
