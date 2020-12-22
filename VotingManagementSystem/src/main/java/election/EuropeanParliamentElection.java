package election;

import user.Candidate;

import java.time.Year;
import java.util.ArrayList;

public class EuropeanParliamentElection extends Election {
    private final int numberOfMEPS = 32; //number of members of european parliament
    private ArrayList<Candidate> candidates;

    public EuropeanParliamentElection(Year yearOfElection, double electoralThreshold) {
        super(typeOfElection.PE, yearOfElection, durationOfMandate.FIVE_YEARS, electoralThreshold);
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }
}
