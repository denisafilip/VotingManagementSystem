package election;

import java.time.Year;

public class Referendum {
    private String question;
    private final typeOfElection type = typeOfElection.R;
    private Year yearOfReferendum;

    public Referendum(String question, Year yearOfReferendum) {
        this.question = question;
        this.yearOfReferendum = yearOfReferendum;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Year getYearOfReferendum() {
        return yearOfReferendum;
    }

    public void setYearOfReferendum(Year yearOfReferendum) {
        this.yearOfReferendum = yearOfReferendum;
    }
}
