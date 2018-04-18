package project.restControllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.model.User;
import project.repository.UserRepository;
import project.restcontroller.RestUserController;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RunWith(MockitoJUnitRunner.class)
public class RestUserControllerTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    RestUserController controller;

    List<User> expected;

    List<User> allUsers;

    ResponseEntity<List<User>> expectedResponse;

    User joao, daniel, ana;
    Link joaoLink, danielLink, anaLink;



    @Before
    public void setUp() {
        // initiates the controller using the mocked Database/Service
        controller = new RestUserController(userService);

        // creates three users and adds a self ref link for each
        joao = userService.createUser("Joao", "joao@gmail.com", "001", "tester", "919191919", "test", "test", "test", "test", "test");
        joaoLink = linkTo(RestUserController.class).slash(joao.getUserID()).withSelfRel();
        joao.add(joaoLink);

        daniel = userService.createUser("Daniel", "daniel@hotmail.com", "002", "tester", "919191919", "test", "test", "test", "test", "test");
        danielLink= linkTo(RestUserController.class).slash(daniel.getUserID()).withSelfRel();
        daniel.add(danielLink);

        ana = userService.createUser("Ana", "ana@gmail.com", "003", "tester", "919191919", "test", "test", "test", "test", "test");
        anaLink=linkTo(RestUserController.class).slash(ana.getUserID()).withSelfRel();
        ana.add(anaLink);

        // creates a mock list containing all users
        allUsers = new ArrayList<>();
        allUsers.add(joao);
        allUsers.add(daniel);
        allUsers.add(ana);

        // and finally an empty test list to be filled and compared for each assertion
        expected = new ArrayList<>();

    }

    @After
    public void tearDown() {
        joao=null;
        joaoLink=null;
        daniel=null;
        danielLink=null;
        ana=null;
        anaLink=null;
        allUsers.clear();
        expected.clear();
    }

    /**
     *
     * This test verifies the rest controller can find all users according by email according to the string provided.
     * It uses a mock user Repository containing all test users.
     *
     * When searching for a non used email provider, it must return 0 users
     * When searching for the generic "@" symbol used in all emails, it must return all users
     * When searching for a used email provider, it must return only those whose email contains that string
     *
     */
    @Test
    public void testSearchUserByEmail() {
        // GIVEN 3 users in the mock database with email accounts from hotmail and gmail

        when(userRepository.findAll()).thenReturn(allUsers);

        // WHEN searching for nonExistent email provider "sapo.pt"

        expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        // THEN the response entity must contain an empty list and status OK
        assertEquals(expectedResponse, controller.searchUsersByEmail("sapo.pt"));


        // AND WHEN searching for "@" provider used by two users
        expected = new ArrayList<>();
        expected.add(joao);
        expected.add(daniel);
        expected.add(ana);

        when(userService.searchUsersByPartsOfEmail("@")).thenReturn(expected);

        expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        // THEN the response entity must contain all Users
        assertEquals(expectedResponse, controller.searchUsersByEmail("@"));


        // AND WHEN searching for "gmail.com" provider used by two users
        expected.remove(daniel);

        when(userService.searchUsersByPartsOfEmail("gmail.com")).thenReturn(expected);

        expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        // THEN the response entity must contain those two Users
        assertEquals(expectedResponse, controller.searchUsersByEmail("gmail.com"));




    }
}
