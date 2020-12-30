package election;

import election.enums.durationOfMandate;
import election.enums.typeOfElection;
import politicalParty.PoliticalParty;
import politicalParty.politicalPosition;

import java.time.Year;
import java.util.LinkedHashMap;
import java.util.Map;

public class Election {
    private typeOfElection type;
    private Year yearOfElection;
    private durationOfMandate mandate;
    private Map<String, PoliticalParty> politicalParties;
    private double electoralThreshold;

    public Election() {
        addPoliticalParties();
    }

    public Election(typeOfElection type, Year yearOfElection) {
        this.type = type;
        this.yearOfElection = yearOfElection;
    }

    public Election(typeOfElection type, Year yearOfElection, durationOfMandate mandate, double electoralThreshold) {
        this.type = type;
        this.yearOfElection = yearOfElection;
        this.mandate = mandate;
        this.electoralThreshold = electoralThreshold;
        addPoliticalParties();
    }

    public void addPoliticalParties() {
        Map<String, PoliticalParty> pParties = new LinkedHashMap<>();
        pParties.put("USR PLUS", new PoliticalParty("Alianța USR PLUS", "USR PLUS", politicalPosition.CENTER_RIGHT));
        pParties.put("PNL", new PoliticalParty("Partidul Național Liberal", "PNL", politicalPosition.CENTER_RIGHT));
        pParties.put("PSD", new PoliticalParty("Partidul Social Democrat", "PSD", politicalPosition.CENTER_LEFT));
        pParties.put("UDMR", new PoliticalParty("Uniunea Democratică Maghiară din România", "UDMR", politicalPosition.CENTER_RIGHT));
        pParties.put("PRO România", new PoliticalParty("PRO România", "PRO România", politicalPosition.CENTER));
        pParties.put("PMP", new PoliticalParty("Partidul Mișcarea Populară", "PMP", politicalPosition.CENTER_RIGHT));
        pParties.put("AUR", new PoliticalParty("Alianța pentru Unirea Românilor", "AUR", politicalPosition.RIGHT));
        pParties.put("POL", new PoliticalParty("Partidul Oamenilor Liberi", "POL", politicalPosition.CENTER));
        pParties.put("PRU", new PoliticalParty("Partidul România Unită", "PRU", politicalPosition.EXTREME_RIGHT));
        this.politicalParties = pParties;
    }

    public Year getYearOfElection() {
        return yearOfElection;
    }

    public void setYearOfElection(Year yearOfElection) {
        this.yearOfElection = yearOfElection;
    }

    public double getElectoralThreshold() {
        return electoralThreshold;
    }

    public void setElectoralThreshold(double electoralThreshold) {
        this.electoralThreshold = electoralThreshold;
    }

    public Map<String, PoliticalParty> getPoliticalParties() {
        return politicalParties;
    }

    public void setPoliticalParties(Map<String, PoliticalParty> politicalParties) {
        this.politicalParties = politicalParties;
    }

    public typeOfElection getType() {
        return type;
    }

    public void setType(typeOfElection type) {
        this.type = type;
    }
}
