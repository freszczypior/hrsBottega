package pl.com.bottega.hrs.ui.rest;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.model.commands.RegisterUserCommand;

/**
 * Created by freszczypior on 2017-11-28.
 */

@RestController
public class UsersController {

    private CommandGateway gateway;

    public UsersController(CommandGateway gateway) {
        this.gateway = gateway;
    }

    @PutMapping("/users")
    public void register(@RequestBody RegisterUserCommand command){
        gateway.execute(command);
    }

}
