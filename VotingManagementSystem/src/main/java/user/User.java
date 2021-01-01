package user;

import country.Country;
import country.County;
import election.enums.typeOfElection;
import javafx.scene.control.Label;
import vote.UserVote;
import webScraping.Scraping;

import java.time.LocalDate;
import java.time.Period;

public class User {
    private final static String ADMIN_EMAIL = "denisafilip0207@gmail.com";

    private String firstName;
    private String lastName;
    private String gender; //M or F
    private String CNP;
    private int age;
    private LocalDate dateOfBirth;
    private County county;
    private String mail;
    private String password;
    private UserVote presidential;
    private UserVote europeanParliament;
    private UserVote local;
    private UserVote firstReferendum;
    private UserVote secondReferendum;
    private UserVote senateParliament;
    private UserVote deputiesParliament;
    private boolean isAdmin;

    public User() {
    }

    public User(String firstName, County county) {
        this.firstName = firstName;
        this.county = county;
    }

    public User(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public User(String mail, String password, String firstName, String lastName, String gender, String CNP, LocalDate dateOfBirth) {
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.CNP = CNP;
        this.dateOfBirth = dateOfBirth;
        this.age = computeUserAge();
        if (verifyCNP(null)) this.county = assignUserToCounty();
        initializeVotes(false);
        this.isAdmin = this.mail.equals(ADMIN_EMAIL);
    }

    public void setVotingStatus(boolean status) {
        this.europeanParliament.setStatus(status);
        this.local.setStatus(status);
        this.deputiesParliament.setStatus(status);
        this.senateParliament.setStatus(status);
        this.firstReferendum.setStatus(status);
        this.secondReferendum.setStatus(status);
        this.presidential.setStatus(status);
    }

    public void initializeVotes(boolean status) {
        this.europeanParliament = new UserVote(typeOfElection.EUROPEAN_PARLIAMENT, status);
        this.senateParliament = new UserVote(typeOfElection.SENATE_PARLIAMENT, status);
        this.deputiesParliament = new UserVote(typeOfElection.CHAMBER_OF_DEPUTIES_PARLIAMENT, status);
        this.local = new UserVote(typeOfElection.LOCAL, status);
        this.presidential = new UserVote(typeOfElection.PRESIDENTIAL, status);
        this.firstReferendum = new UserVote(typeOfElection.REFERENDUM_QUESTION_1, status);
        this.secondReferendum = new UserVote(typeOfElection.REFERENDUM_QUESTION_2, status);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getPresidential() {
        return presidential.getStatus();
    }

    public boolean getEuropeanParliament() {
        return europeanParliament.getStatus();
    }

    public boolean getLocal() {
        return local.getStatus();
    }

    public boolean getFirstReferendum() {
        return firstReferendum.getStatus();
    }

    public boolean getSecondReferendum() {
        return secondReferendum.getStatus();
    }

    public boolean getSenateParliament() {
        return senateParliament.getStatus();
    }

    public boolean getDeputiesParliament() {
        return deputiesParliament.getStatus();
    }

    /**
     *
     * @param lblCNP - JavaFX label, used to display errors if the CNP is incorrect
     * @return true is user's CNP meets all of the requirements
     */
    public boolean verifyCNP(Label lblCNP) {
        String constant = "279146358279";
        if (!this.CNP.matches("[0-9]+")) {
            if (lblCNP != null) lblCNP.setText("CNP-ul trebuie să conțină doar cifre.");
            return false;
        }
        if (this.CNP.length() != 13) {
            if (lblCNP != null) lblCNP.setText("CNP-ul trebuie să fie format din 13 cifre.");
            return false;
        }
        int sum = 0;
        for (int i = 0; i < constant.length(); i++) {
            sum += (this.CNP.charAt(i) - '0') * (constant.charAt(i) - '0');
        }
        int rest = sum % 11;
        int controlDigit = Character.getNumericValue(this.CNP.charAt(12));
        if ((rest < 10 && rest == controlDigit) || (rest == 10 && controlDigit == 1)) {
            if ((this.gender.equals("F") && (this.CNP.charAt(0) == '6' || this.CNP.charAt(0) == '2')) ||
                    (this.gender.equals("M") && (this.CNP.charAt(0) == '5' || this.CNP.charAt(0) == '1'))) {
                return true;
            } else {
                if (lblCNP != null) lblCNP.setText("Prima cifră a CNP-ului introdus nu se potrivește cu genul dvs.");
                return false;
            }
        } else {
            if (lblCNP != null) lblCNP.setText("CNP-ul introdus nu este valid.");
            return false;
        }
    }

    /**
     *
     * @return the County in which the user is located, computed based on his/hers CNP
     */
    public County assignUserToCounty() {
        Scraping scraper = new Scraping();
        Country Romania = scraper.webScrapingCounties();
        Romania.sortCountiesAlphabetically(Romania);

        Romania.getCounties().remove(19); //removing "Giurgiu" county from arraylist
        Romania.getCounties().remove(12); //removing "Călărași" county from arraylist
        Romania.getCounties().remove(10); //removing "București" county from arraylist
        int countyDigits = (this.CNP.charAt(7) - '0')*10 + (this.CNP.charAt(8) - '0');
        for (int i = 0; i < 39; i++) {
            if (countyDigits == i + 1) {
                return Romania.getCounties().get(i);
            }
        }
        if (countyDigits >= 40 && countyDigits <= 46) {
            return new County("București");
        } else if (countyDigits == 51) {
             return new County("Călărași");
        } else if (countyDigits == 52) {
            return new County("Giurgiu");
        }
        return null;
    }

    /**
     *
     * @return user's age, computed as a difference of the current date and the user's date of birth
     */
    public int computeUserAge() {
        LocalDate now = LocalDate.now();
        Period difference = Period.between(this.dateOfBirth, now);
        return difference.getYears();
    }

    /**
     *
     * @return true if user is over 18 and under 110 years old (eligible to vote and alive)
     */
    public boolean isMajor() {
        return (this.age >= 18 && this.age < 110);
    }

    /**
     *
     * @return true if user's last name is formed only of letters, spaces or hyphens
     */
    public boolean verifyLastName() {
        return (this.lastName.matches("[a-zA-Z- ăîâșț]+"));
    }

    /**
     *
     * @return true if user's first name is formed only of letters, spaces or hyphens
     */
    public boolean verifyFirstName() {
        return (this.firstName.matches("[a-zA-Z- ăîâșț]+"));
    }


    /**
     *
     * @return true if user's input birthday is the same as the one obtained from the CNP
     */
    public int verifyDateOfBirth() {
        String birthYear;
        if (this.CNP.charAt(0) == '6' || this.CNP.charAt(0) == '5') {
            birthYear = "20" + this.CNP.charAt(1) + this.CNP.charAt(2);
        } else {
            birthYear = "19" + this.CNP.charAt(1) + this.CNP.charAt(2);
        }
        int birthMonth = (this.CNP.charAt(3) - '0')*10 + (this.CNP.charAt(4) - '0');
        int birthDay = (this.CNP.charAt(5) - '0')*10 + (this.CNP.charAt(6) - '0');
        LocalDate userDOB = LocalDate.of(Integer.parseInt(birthYear), birthMonth, birthDay);
        return userDOB.compareTo(this.dateOfBirth);
    }


}
