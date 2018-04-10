package project.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.Profile;
import project.model.User;
import project.services.UserService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({"project.services", "project.model", "project.controllers"})
public class US105CreatePasswordAndAuthenticationMechanismControllerTest {

    @Autowired
    UserService userService;
    @Autowired
    US105CreatePasswordAndAuthenticationMechanismController controller;


    User user1;

    @Before
    public void setUp() {

        // create user
        user1 = userService.createUser("Daniel", "daniel@gmail.com", "001", "collaborator", "910000000", "Rua",
                "2401-00", "Test", "Testo", "Testistan");

        // set user as collaborator
        user1.setUserProfile(Profile.COLLABORATOR);

        userService.updateUser(user1);

    }

    @After
    public void clear() {

        user1 = null;
    }


    @Test
    public void setUserPasswordTest() {
        String newPassword = "testPassword";

        assertFalse(user1.hasPassword());

        controller.setUserPassword(user1, newPassword);

        assertTrue(user1.hasPassword());

    }
}
