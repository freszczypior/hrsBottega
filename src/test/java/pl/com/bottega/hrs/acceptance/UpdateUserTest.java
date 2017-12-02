package pl.com.bottega.hrs.acceptance;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.CommandGateway;
import pl.com.bottega.hrs.application.UserFinder;
import pl.com.bottega.hrs.application.dtos.DetailedUserDto;
import pl.com.bottega.hrs.application.handlers.RegisterUserHandler;
import pl.com.bottega.hrs.application.handlers.UpdateUserHandler;
import pl.com.bottega.hrs.application.users.Role;
import pl.com.bottega.hrs.model.commands.CommandInvalidException;
import pl.com.bottega.hrs.model.commands.RegisterUserCommand;
import pl.com.bottega.hrs.model.commands.UpdateUserCommand;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by freszczypior on 2017-11-30.
 */
@SpringBootTest
@RunWith (SpringRunner.class)
public class UpdateUserTest extends AcceptanceTest {

    @Autowired
    private RegisterUserHandler registerUserHandler;

    @Autowired
    private UpdateUserHandler updateUserHandler;

    @Autowired
    private UserFinder userFinder;

    @Autowired
    private CommandGateway gateway;

    @Before
    public void setUser(){
        RegisterUserCommand registerCommand = new RegisterUserCommand();
        registerCommand.setLogin("sutLogin");
        registerCommand.setPassword("sutPassword");
        registerCommand.setRepeatedPassword("sutPassword");
        registerUserHandler.handle(registerCommand);
    }

    @Test
    public void shouldUpdateUser(){
        //when
        UpdateUserCommand updateCommand = new UpdateUserCommand();
        updateCommand.setId(1);
        updateCommand.setLogin("newLogin");
        updateCommand.setNewPassword("newPassword");
        updateCommand.setRepeatedNewPassword("newPassword");
        gateway.execute(updateCommand);

        //then
        DetailedUserDto detailedUserDto = userFinder.getUserDetails(1);
        assertEquals("newLogin", detailedUserDto.getLogin());
        assertEquals("newPassword", detailedUserDto.getPassword());
    }
    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToChangeLoginForTheSameItWas(){
        //when
        UpdateUserCommand updateCommand = new UpdateUserCommand();
        updateCommand.setId(1);
        updateCommand.setLogin("sutLogin");
        gateway.execute(updateCommand);
    }
    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToUpdateUserWhenGotDifferentPasswords(){
        //when
        UpdateUserCommand updateCommand = new UpdateUserCommand();
        updateCommand.setId(1);
        updateCommand.setNewPassword("newPassword");
        updateCommand.setRepeatedNewPassword("XXnewPassword");
        gateway.execute(updateCommand);
    }
    @Test(expected = CommandInvalidException.class)
    public void shouldNotAllowToUpdateUserWhenRolesAreEmpty(){
        //when
        Set<Role> newRoles = new HashSet<>();
        UpdateUserCommand updateCommand = new UpdateUserCommand();
        updateCommand.setId(1);
        updateCommand.setRoles(newRoles);
        gateway.execute(updateCommand);
    }
}