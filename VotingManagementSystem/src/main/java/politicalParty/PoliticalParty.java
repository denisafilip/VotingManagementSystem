package politicalParty;

public class PoliticalParty {
    private String name;
    private String abbreviation;
    //private List<Candidate> candidates;
    private politicalPosition politicalPosition; //position of the political party in the political spectrum

    public PoliticalParty(String name, String abbreviation, politicalPosition politicalPosition) {
        this.name = name;
        this.abbreviation = abbreviation;
        //this.candidates = candidates;
        this.politicalPosition = politicalPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public politicalPosition getPoliticalPosition() {
        return politicalPosition;
    }

    public void setPoliticalPosition(politicalPosition politicalPosition) {
        this.politicalPosition = politicalPosition;
    }
}
