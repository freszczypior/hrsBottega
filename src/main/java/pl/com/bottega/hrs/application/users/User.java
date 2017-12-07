package pl.com.bottega.hrs.application.users;

import lombok.Getter;
import pl.com.bottega.hrs.model.Title;
import pl.com.bottega.hrs.model.commands.UpdateUserCommand;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="users")
@Getter
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String login;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    User() {
    }

    public User(Integer id, String login, String password, Set<Role> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }
    public User(Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        roles.add(Role.STANDARD);
    }
    public User(String login, String password) {
        this.login = login;
        this.password = password;
        roles.add(Role.STANDARD);
    }

    public void updateProfile(String login, String password, Set<Role> roles) {
        if (login != null)
            this.login = login;
        if (password != null)
            this.password = password;
        if (roles != null && !roles.isEmpty())
            this.roles = roles;
    }
//
//    public void updateProfile(UpdateUserCommand command) {
//        if (command.getLogin() != null)
//            this.login = command.getLogin();
//        if (command.getNewPassword() != null)
//            this.password = command.getNewPassword();
//        if (!command.getRoles().isEmpty())
//            this.roles = command.getRoles();
//    }
}