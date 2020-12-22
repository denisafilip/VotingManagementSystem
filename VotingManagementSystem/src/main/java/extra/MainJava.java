package extra;

import user.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class MainJava {

    public static void main(String[] args) {
	// write your code here
        //read from a file
        Scraping scraper = new Scraping();
        scraper.webScraping();
        try {
            File file = new File("C:\\Users\\Denisa\\IdeaProjects\\VotingManagementSystem-F\\VotingManagementSystem\\src\\main\\java\\database.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String[] words = scanner.nextLine().split(" ");
                String mail = words[0];
                String password = words[1];
                String name = words[2] + words[3];
                String gender = words[4];
                String CNP = words[5];
                String date = words[6];

                LocalDate dateOfBirth = LocalDate.parse(date);

                User u1 = new User(mail, password, name, gender, CNP, dateOfBirth);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //write into a file
        try {
            FileWriter myWriter = new FileWriter("database.txt", true);
            myWriter.write("test\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
