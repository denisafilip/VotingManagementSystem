package sample;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import user.User;

public class Controller {

    @FXML
    public Button btnLogin;
    @FXML
    private DatePicker birthday;
    @FXML
    private final ComboBox<String> gender = new ComboBox<>();
    @FXML
    public String female;
    @FXML
    public String male;
    @FXML
    private TextField CNP;
    @FXML
    private PasswordField password;
    @FXML
    private TextField email;
    @FXML
    private TextField name;

    public void parseData() {
        User u = new User();
        u.setName(name.getText());

        u.setMail(email.getText());
        u.setPassword(password.getText());
        u.setDateOfBirth(birthday.getValue());
        u.setGender(gender.getValue());
        u.setCNP(CNP.getText());
    }


}
