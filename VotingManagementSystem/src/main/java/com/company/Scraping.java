package com.company;

import com.company.country.Country;
import com.company.election.ParliamentElection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.time.Year;

public class Scraping {

    public void webScraping() {
        try {
            //web scraping counties of Romania
            Document docCounty = Jsoup.connect("https://ro.wikipedia.org/wiki/Lista_județelor_României_după_populație").get();
            Country Romania = new Country("Romania");
            Romania.setCountiesOfRomania(Romania, docCounty);

            //parsing data of the candidates for the parliament elections
            Document doc = Jsoup.connect("https://www.g4media.ro/harta-candidatii-la-alegerile-parlamentare-2020-istoricul-prezentei-mandatele-pe-fiecare-judet-si-scorurile-din-2016.html").get();
            Elements paragraph = doc.select("p:contains(: Senat)");
            ParliamentElection legislativeElection = new ParliamentElection(Year.of(2020), 5);
            legislativeElection.setParliamentCandidates(Romania, paragraph);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }
}

