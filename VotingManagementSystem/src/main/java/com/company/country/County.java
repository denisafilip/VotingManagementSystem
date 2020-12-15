package com.company.country;

public class County {

    private String countyName;
    //private int population;
    private int totalVotes;

    public County(String name) {
        this.countyName = name;
        //this.population = population;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
}
