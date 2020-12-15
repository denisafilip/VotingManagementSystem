package com.company.election;

import com.company.user.Candidate;

import java.time.Year;
import java.util.ArrayList;

public class PresidentialElection extends Election {
    private final numberOfRound round; //1 or 2
    private ArrayList<Candidate> candidates;

    public PresidentialElection(Year yearOfElection, double electoralThreshold, numberOfRound round) {
        super(typeOfElection.P, yearOfElection, durationOfMandate.FIVE_YEARS, electoralThreshold);
        this.round = round;
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }
}
