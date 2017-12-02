package pl.com.bottega.hrs.application.users;

import lombok.Getter;
import pl.com.bottega.hrs.model.commands.UpdateUserCommand;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
public class User {

    @Getter
    @Id
    @GeneratedValue
    private Integer id;

    @Getter
    private String login;

    @Getter
    private String password;

    @ElementCollection
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Getter
    @Column(name = "role")
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