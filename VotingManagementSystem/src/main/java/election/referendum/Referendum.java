package election.referendum;

import election.Election;
import election.enums.Question;
import election.enums.typeOfElection;
import vote.ReferendumPairVote;
import vote.ReferendumVote;

import java.time.Year;

public class Referendum extends Election {
    private Question question;
    private ReferendumPairVote referendumVotes;

    public Referendum(typeOfElection type, Question question, Year yearOfReferendum) {
        super(type, yearOfReferendum);
        this.question = question;
        this.referendumVotes = new ReferendumPairVote(new ReferendumVote(getType(), ReferendumPosition.PRO), new ReferendumVote(getType(), ReferendumPosition.AGAINST));
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public ReferendumPairVote getReferendumVotes() {
        return referendumVotes;
    }

    public void setReferendumVotes(ReferendumPairVote referendumVotes) {
        this.referendumVotes = referendumVotes;
    }
}
