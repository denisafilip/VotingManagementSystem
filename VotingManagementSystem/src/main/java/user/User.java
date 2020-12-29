package user;

import country.Country;
import country.County;
import javafx.scene.control.Label;
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
    private boolean hasVotedPresidential;
    private boolean hasVotedEuro;
    private boolean hasVotedParliament;
    private boolean hasVotedLocal;
    private boolean hasVotedReferendum;
    private boolean hasVotedReferendum2;
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

    public User(String mail, String password, String firstName, String lastName, String gender, String CNP, LocalDate dateOfBirth,
                boolean hasVotedPresidential, boolean hasVotedEuro, boolean hasVotedParliament, boolean hasVotedLocal, boolean hasVotedReferendum,
                boolean hasVotedReferendum2) {
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.CNP = CNP;
        this.dateOfBirth = dateOfBirth;
        this.age = computeUserAge();
        if (verifyCNP(null)) this.county = assignUserToCounty();
        this.hasVotedEuro = hasVotedEuro;
        this.hasVotedLocal = hasVotedLocal;
        this.hasVotedPresidential = hasVotedPresidential;
        this.hasVotedParliament = hasVotedParliament;
        this.hasVotedReferendum = hasVotedReferendum;
        this.hasVotedReferendum2 = hasVotedReferendum2;
        this.isAdmin = this.mail.equals(ADMIN_EMAIL);
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

    public boolean hasVotedPresidential() {
        return hasVotedPresidential;
    }

    public void setHasVotedPresidential(boolean hasVotedPresidential) {
        this.hasVotedPresidential = hasVotedPresidential;
    }

    public boolean hasVotedEuro() {
        return hasVotedEuro;
    }

    public void setHasVotedEuro(boolean hasVotedEuro) {
        this.hasVotedEuro = hasVotedEuro;
    }

    public boolean hasVotedParliament() {
        return hasVotedParliament;
    }

    public void setHasVotedParliament(boolean hasVotedParliament) {
        this.hasVotedParliament = hasVotedParliament;
    }

    public boolean hasVotedLocal() {
        return hasVotedLocal;
    }

    public void setHasVotedLocal(boolean hasVotedLocal) {
        this.hasVotedLocal = hasVotedLocal;
    }

    public boolean hasVotedReferendum() {
        return hasVotedReferendum;
    }

    public void setHasVotedReferendum(boolean hasVotedReferendum) {
        this.hasVotedReferendum = hasVotedReferendum;
    }

    public Boolean hasVotedReferendum2() {
        return hasVotedReferendum2;
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
        return (this.lastName.matches("[a-zA-Z- ]+"));
    }

    /**
     *
     * @return true if user's first name is formed only of letters, spaces or hyphens
     */
    public boolean verifyFirstName() {
        return (this.firstName.matches("[a-zA-Z- ]+"));
    }




}
