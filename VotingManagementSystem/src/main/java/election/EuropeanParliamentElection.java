package election;

import election.enums.durationOfMandate;
import election.enums.typeOfElection;
import user.Candidate;

import java.time.Year;
import java.util.ArrayList;

public class EuropeanParliamentElection extends Election {
    private ArrayList<Candidate> candidates;

    public EuropeanParliamentElection(Year yearOfElection, double electoralThreshold) {
        super(typeOfElection.EUROPEAN_PARLIAMENT, yearOfElection, durationOfMandate.FIVE_YEARS, electoralThreshold);
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }
}
