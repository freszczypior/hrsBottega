package pl.com.bottega.hrs.ui.rest;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.UserFinder;
import pl.com.bottega.hrs.application.dtos.DetailedUserDto;
import pl.com.bottega.hrs.model.commands.RegisterUserCommand;
import pl.com.bottega.hrs.model.commands.UpdateUserCommand;

/**
 * Created by freszczypior on 2017-11-28.
 */

@RestController
public class UsersController {

    private UserFinder userFinder;

    private CommandGateway gateway;

    public UsersController(UserFinder userFinder, CommandGateway gateway) {
        this.userFinder = userFinder;
        this.gateway = gateway;
    }

    @PostMapping("/users")
    public void register(@RequestBody RegisterUserCommand command){
        gateway.execute(command);
    }

    @PutMapping("/users/{id}")
    public DetailedUserDto update(@PathVariable Integer id, @RequestBody UpdateUserCommand command){
        command.setId(id);
        gateway.execute(command);
        return userFinder.getUserDetails(id);
    }
}
