package pl.com.bottega.hrs.model.commands;

import lombok.Getter;
import lombok.Setter;
import pl.com.bottega.hrs.application.users.Role;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by freszczypior on 2017-11-30.
 */
@Getter
@Setter
public class UpdateUserCommand implements Command {

    private Integer id;

    private String login;

    private String newPassword;

    private String repeatedNewPassword;

    private Set<Role> roles;

    public void validate(ValidationErrors errors) {
//        validatePresence(errors, "login", login);
//        validatePresence(errors, "password", password);
//        validatePresence(errors, "password", repeatedPassword);

        if (newPassword != null && !newPassword.equals(repeatedNewPassword)) {
            errors.add("repeatedPassword", "both passwords must be the same");
        }
        if (login != null && !Pattern.matches("[a-zA-Z0-9]*", login)) {
            errors.add("login", "must contain only a-z, A-Z, 0-9");
        }
        if (newPassword != null && newPassword.trim().length() < 6) {
            errors.add("password", "password must contain more then six characters");
        }
        if (roles != null && roles.isEmpty()){
            errors.add("roles", "new roles must contain some elements");
        }
    }


}
