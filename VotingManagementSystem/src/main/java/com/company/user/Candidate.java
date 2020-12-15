package com.company.user;

import com.company.country.County;

public class Candidate extends User {
    private String politicalPartyAbbr;

    public Candidate() {
    }

    public Candidate(String name, County county, String politicalParty) {
        super(name, county);
        this.politicalPartyAbbr = politicalParty;
    }

    public String getPoliticalPartyAbbr() {
        return politicalPartyAbbr;
    }

    public void setPoliticalPartyAbbr(String politicalPartyAbbr) {
        this.politicalPartyAbbr = politicalPartyAbbr;
    }



}
