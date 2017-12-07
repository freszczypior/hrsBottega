package pl.com.bottega.hrs.model.commands;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * Created by freszczypior on 2017-11-28.
 */
@Getter
@Setter
@Component
public class RegisterUserCommand implements Command{

    private String login;
    private String password;
    private String repeatedPassword;

    public void validate(ValidationErrors errors){
        validatePresence(errors, "login", login);
        validatePresence(errors, "password", password);
        validatePresence(errors, "password", repeatedPassword);

        if (!password.equals(repeatedPassword)){
            errors.add("repeatedPassword", "both passwords must be the same");
        }
        if (!Pattern.matches("[a-zA-Z0-9]*", login)){
            errors.add("login", "must contain only a-z, A-Z, 0-9");
        }
        if (password.trim().length() < 6){
            errors.add("password", "password must contain more then six characters");
        }
    }


}
