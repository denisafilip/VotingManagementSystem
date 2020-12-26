package country;

public class County {

    private String countyName;
    private int noOfVotes;

    public County(String name) {
        this.countyName = name;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getNoOfVotes() {
        return noOfVotes;
    }

    public void setNoOfVotes(int noOfVotes) {
        this.noOfVotes = noOfVotes;
    }

    public void incrementNoOfVotes() {
        this.noOfVotes++;
    }
}
