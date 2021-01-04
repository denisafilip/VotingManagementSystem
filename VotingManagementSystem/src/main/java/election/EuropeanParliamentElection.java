package election;

import election.enums.MandateDuration;
import election.enums.ElectionType;
import user.Candidate;

import java.time.Year;
import java.util.ArrayList;

public class EuropeanParliamentElection extends Election {
    private ArrayList<Candidate> candidates;

    public EuropeanParliamentElection(Year yearOfElection, double electoralThreshold) {
        super(ElectionType.EUROPEAN_PARLIAMENT, yearOfElection, MandateDuration.FIVE_YEARS, electoralThreshold);
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }
}
