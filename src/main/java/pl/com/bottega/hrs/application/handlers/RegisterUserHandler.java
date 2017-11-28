package pl.com.bottega.hrs.application.handlers;

import org.springframework.stereotype.Component;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.model.commands.Command;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.model.commands.RegisterUserCommand;
import pl.com.bottega.hrs.model.commands.ValidationErrors;
import pl.com.bottega.hrs.model.repositories.UserRepository;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;

/**
 * Created by freszczypior on 2017-11-28.
 */

@Component
public class RegisterUserHandler implements Handler<RegisterUserCommand> {

    private UserRepository userRepository;
    private ValidationErrors errors;

    public RegisterUserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public void handle(RegisterUserCommand command) {
        User user = new User(command.getLogin(), command.getPassword());
        try {
            userRepository.save(user);
        }catch (EntityExistsException e){
            errors.add("ligin", "such login already exists");
            throw new CommandInvalidException(errors);
        }
    }

    @Override
    public Class<? extends Command> getSupportedCommandClass() {
        return RegisterUserCommand.class;
    }
}
