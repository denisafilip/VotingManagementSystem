package election.enums;

public enum typeOfElection {
    PRESIDENTIAL("Presidential"),
    PARLIAMENT("Parliament"),
    SENATE_PARLIAMENT("SenateParliament"),
    CHAMBER_OF_DEPUTIES_PARLIAMENT("ChamberOfDeputiesParliament"),
    LOCAL("Local"),
    EUROPEAN_PARLIAMENT("EuropeanParliament"),
    REFERENDUM_QUESTION_1("ReferendumQuestion1"),
    REFERENDUM_QUESTION_2("ReferendumQuestion2");

    private final String label;

    typeOfElection(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
