package election;

import politicalParty.PoliticalParty;
import politicalParty.politicalPosition;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Election {
    private final typeOfElection type;
    private Year yearOfElection;
    private final durationOfMandate mandate;
    private List<PoliticalParty> politicalParties;
    private double electoralThreshold;

    public Election(typeOfElection type, Year yearOfElection, durationOfMandate mandate, double electoralThreshold) {
        this.type = type;
        this.yearOfElection = yearOfElection;
        this.mandate = mandate;
        this.electoralThreshold = electoralThreshold;
        addPoliticalParties();
    }

    public void addPoliticalParties() {
        List <PoliticalParty> pParties = new ArrayList<>();
        pParties.add(new PoliticalParty("Partidul Național Liberal", "PNL", politicalPosition.CENTER_RIGHT));
        pParties.add(new PoliticalParty("Partidul Social Democrat", "PSD", politicalPosition.CENTER_LEFT));
        pParties.add(new PoliticalParty("Alianța USR PLUS", "USR PLUS", politicalPosition.CENTER_RIGHT));
        pParties.add(new PoliticalParty("Alianța pentru Unirea Românilor", "AUR", politicalPosition.RIGHT));
        pParties.add(new PoliticalParty("Uniunea Democratică Maghiară din România", "UDMR", politicalPosition.CENTER_RIGHT));
        pParties.add(new PoliticalParty("Partidul Mișcarea Populară", "PMP", politicalPosition.CENTER_RIGHT));
        pParties.add(new PoliticalParty("PRO România", "PRO România", politicalPosition.CENTER));
        pParties.add(new PoliticalParty("Uniunea Națională pentru Progresul României", "UNPR", politicalPosition.CENTER_LEFT));
        pParties.add(new PoliticalParty("Partidul Național Liberal", "PNL", politicalPosition.CENTER_RIGHT));
        pParties.add(new PoliticalParty("Partidul România Unită", "PRU", politicalPosition.EXTREME_RIGHT));
        this.politicalParties = pParties;
    }

    public Year getYearOfElection() {
        return yearOfElection;
    }

    public void setYearOfElection(Year yearOfElection) {
        this.yearOfElection = yearOfElection;
    }

    public List<PoliticalParty> getPoliticalParties() {
        return politicalParties;
    }

    public void setPoliticalParties(List<PoliticalParty> politicalParties) {
        this.politicalParties = politicalParties;
    }

    public double getElectoralThreshold() {
        return electoralThreshold;
    }

    public void setElectoralThreshold(double electoralThreshold) {
        this.electoralThreshold = electoralThreshold;
    }
}
