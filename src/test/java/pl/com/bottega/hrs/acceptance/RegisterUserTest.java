package pl.com.bottega.hrs.acceptance;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.UserFinder;
import pl.com.bottega.hrs.application.dtos.BasicUserDto;
import pl.com.bottega.hrs.application.dtos.DetailedUserDto;
import pl.com.bottega.hrs.application.handlers.RegisterUserHandler;
import pl.com.bottega.hrs.application.users.User;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.model.commands.RegisterUserCommand;
import pl.com.bottega.hrs.model.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.swing.text.html.Option;
import java.util.Optional;

/**
 * Created by freszczypior on 2017-11-28.
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegisterUserTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterUserHandler registerUserHandler;

    @Autowired
    private CommandGateway gateway;

    @Autowired
    private UserFinder userFinder;

    @Test
    public void shouldRegisterUser(){
        //given
        RegisterUserCommand command = new RegisterUserCommand();
        command.setLogin("Batman");
        command.setPassword("password");
        command.setRepeatedPassword("password");
        registerUserHandler.handle(command);

        //then
        DetailedUserDto detailedUserDto = userFinder.getUserDetails(1);
        assertEquals(Integer.valueOf(1), detailedUserDto.getId());
    }
    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToRegisterUserTwoTimes(){
        //given
        RegisterUserCommand command = new RegisterUserCommand();
        command.setLogin("Batman");
        command.setPassword("password");
        command.setRepeatedPassword("password");
        registerUserHandler.handle(command);
        registerUserHandler.handle(command);
    }
    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToRegisterUserWithWrongLogin(){
        //given
        RegisterUserCommand command = new RegisterUserCommand();
        command.setLogin("Batman%%!!");
        command.setPassword("password");
        command.setRepeatedPassword("password");
        gateway.execute(command);
    }
    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToRegisterUserWithDifferentPasswords(){
        //given
        RegisterUserCommand command = new RegisterUserCommand();
        command.setLogin("Batman%%!!");
        command.setPassword("password1");
        command.setRepeatedPassword("password2");
        gateway.execute(command);
    }



}
