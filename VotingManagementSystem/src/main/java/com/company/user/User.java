package com.company.user;

import com.company.country.Country;
import com.company.country.County;
import java.time.LocalDate;
import java.time.Period;

public class User {
    
    private String name;
    private char gender; //M or F
    private String CNP;
    private int age;
    private LocalDate dateOfBirth;
    private County county;
    private boolean hasVoted;
    private String mail;
    private String password;

    public User() {
    }

    public User(String name, County county) {
        this.name = name;
        this.county = county;
    }

    public User(String mail, String password, String name, char gender, String CNP, LocalDate dateOfBirth) {
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.CNP = CNP;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
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

    /**
     * @param Romania - used to assign the county of the user if the introduced CNP is valid
     *
     * @return true if user's CNP meets all requirements
     * depending on the CNP, you are assigned to a county of Romania from which you can vote
     */
    public boolean verifyCNP(Country Romania) {
        String constant = "279146358279";
        if (!this.CNP.matches("[0-9]+")) {
            System.err.println("You must enter only numbers for the CNP");
            return false;
        }
        if (this.CNP.length() != 13) {
            System.err.println("The CNP must have 13 digits.");
            return false;
        }
        int sum = 0;
        for (int i = 0; i < constant.length(); i++) {
            sum = sum + this.CNP.charAt(i) * constant.charAt(i);
        }
        int rest = sum % 11;
        int controlDigit = Character.getNumericValue(this.CNP.charAt(12));
        if ((rest < 10 && rest == controlDigit) || (rest == 10 && controlDigit == 1)) {
            if ((this.gender == 'F' && (this.CNP.charAt(0) == '6' || this.CNP.charAt(0) == '2')) ||
                    (this.gender == 'M' && (this.CNP.charAt(0) == '5' || this.CNP.charAt(0) == '1'))) {
                this.county = assignUserToCounty(Romania);
                return true;
            } else {
                System.err.println("The first digit of your CNP does not match your gender.");
                return false;
            }
        } else {
            System.err.println("The CNP you entered is not valid.");
            return false;
        }
    }

    /**
     *
     * @param Romania - used to get the counties of Romania
     *                still need to swap a few elements to have them in sorted order and to remove Giurgiu and Calarasi and Bucuresti from the arraylist
     */
    public County assignUserToCounty(Country Romania) {
        Romania.sortCountiesAlphabetically(Romania);
        Romania.getCounties().remove(19); //removing "Giurgiu" county from arraylist
        Romania.getCounties().remove(12); //removing "Călărași" county from arraylist
        Romania.getCounties().remove(10); //removing "București" county from arraylist
        int countyDigits = (this.CNP.charAt(7) - '0')*10 + (this.CNP.charAt(8) - '0');
        for (int i = 1; i < 39; i++) {
            System.out.println(Romania.getCounties().get(i).getCountyName());
            if (countyDigits == i) {
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
     * @return true if user's gender is masculine/feminine and is expressed through a capital letter
     */
    public boolean verifyGender() {
        return (this.gender == 'M' || this.gender == 'F');
    }

    /**
     *
     * @return true if user's name is formed only of letters
     */
    public boolean verifyName() {
        return (this.name.matches("[a-zA-Z]+"));
    }
}
