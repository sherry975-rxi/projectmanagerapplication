package project.restControllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.model.Profile;
import project.model.User;
import project.restcontroller.RestUserController;
import project.ui.console.loadfiles.filestorage.StorageService;
import project.services.UserService;
import project.ui.console.loadfiles.loaduser.UserReader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RunWith(MockitoJUnitRunner.class)
public class RestUserControllerTest {

    @Mock
    UserService userService;

    RestUserController controller;

    @Mock
    StorageService storageService;

    @Mock
    UserReader userReader;


    private User mike, justin, joao, daniel, ana;
    private Link mikeLink, justinLink,joaoLink, danielLink, anaLink;
    private ResponseEntity<List<User>> expectedResponse;
    private List<User> expected;
    private List<User> allUsers;

    private List<User> collaborators = new ArrayList<>();
    private List<User> directors = new ArrayList<>();
    private List<User> unassigned = new ArrayList<>();



    @Before
    public void setUp() {
        initMocks(this);

        // creates three users and adds a self ref link for each
        mike = new User("Mike", "mike@gmail.com", "876", "function", "987");
        mikeLink = linkTo(RestUserController.class).slash(mike.getUserID()).withSelfRel();
        mike.add(mikeLink);

        justin = new User("Justin", "justin@gmail.com", "878", "function", "9877");
        justinLink = linkTo(RestUserController.class).slash(justin.getUserID()).withSelfRel();
        justin.add(justinLink);

        joao = new User ("Joao", "joao@gmail.com", "001", "tester", "919191919");
        joaoLink = linkTo(RestUserController.class).slash(joao.getUserID()).withSelfRel();
        joao.add(joaoLink);

        daniel = new User("Daniel", "daniel@hotmail.com", "002", "tester", "919191919");
        danielLink= linkTo(RestUserController.class).slash(daniel.getUserID()).withSelfRel();
        daniel.add(danielLink);

        ana = new User ("Ana", "ana@gmail.com", "003", "tester", "919191919");
        anaLink=linkTo(RestUserController.class).slash(ana.getUserID()).withSelfRel();
        ana.add(anaLink);

        // creates a mock list containing all users
        allUsers = new ArrayList<>();
        allUsers.add(joao);
        allUsers.add(daniel);
        allUsers.add(ana);

        /* Adds the created users to the user repository */
        collaborators.add(ana);
        directors.add(justin);
        unassigned.add(joao);

        // and finally an empty test list to be filled and compared for each assertion
        expected = new ArrayList<>();

        // then instantiates the controller using the mocked service
        controller=new RestUserController(userService, storageService, userReader);

    }
    @After
    public void tearDown(){
        mike = null;
        mikeLink = null;
        justin = null;
        justinLink = null;
        joao=null;
        joaoLink=null;
        daniel=null;
        danielLink=null;
        ana=null;
        anaLink=null;
        allUsers.clear();
        expected.clear();
        expected = null;
        collaborators.clear();
        directors.clear();
        unassigned.clear();

    }

    /**
     * This test verifies the correct initialization of the REST use controller
     */

    @Test
    public void controllerInitializedCorrectly() {
        assertNotNull(controller);
    }


    /**
     * This test verifies the rest user controller can find all users registered in the system.
     */

    @Test
    public void getAllUsers(){

        //GIVEN an empty user list in a mocked database

        when(userService.getAllUsersFromUserContainer()).thenReturn(expected);

        //WHEN 2 users are added to the user list
        expected.add(mike);
        expected.add(justin);

        //THEN the method should return a list with the two users and the status OK

        expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        assertEquals(expectedResponse, controller.getAllUsers());
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
        when(userService.getAllUsersFromUserContainer()).thenReturn(allUsers);

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

    @Test
    public void searchUsersByProfileWhenExistsTest() {


        //GIVEN 1 user in the the mock database with profile set as collaborator

        when(userService.getAllUsersFromUserContainer()).thenReturn(allUsers);

        //WHEN searching for users with the profile set as COLLABORATOR

        expected = new ArrayList<>();
        expected.add(ana);

        when(userService.searchUsersByProfileName("COLLABORATOR")).thenReturn(expected);

        expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        // THEN the response entity must contain a list with the corresponding profile and status OK

        assertEquals(expectedResponse, controller.searchUsersByProfile("COLLABORATOR"));

        //AND WHEN searching for users with the profile set as DIRECTOR

        expected = new ArrayList<>();
        expected.add(justin);

        when(userService.searchUsersByProfileName("DIRECTOR")).thenReturn(expected);

        expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        //THEN the response entity must contain a list with the corresponding profile and status OK

        assertEquals(expectedResponse, controller.searchUsersByProfile("DIRECTOR"));

        //AND WHEN searching for users with the profile set as UNASSIGNED

        expected = new ArrayList<>();
        expected.add(joao);

        when(userService.searchUsersByProfileName("UNASSIGNED")).thenReturn(expected);

        expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        //THEN the response entity must contain a list with the corresponding profile and status OK

        assertEquals(expectedResponse, controller.searchUsersByProfile("UNASSIGNED"));
    }


    /**
     * This test verifies if the controller can change the profile of a given user that is registered in the company
     * and if it returns a NOT_FOUND when the given user is not present in the database.
     */
    @Test
    public void testChangeUserProfile() {
        // GIVEN a users it is registered in the company.
        when(userService.getUserByEmail("mike@gmail.com")).thenReturn(mike);
        User user = userService.getUserByEmail("mike@gmail.com");
        assertEquals(user,mike);
        assertEquals(Profile.UNASSIGNED,mike.getUserProfile());

        // WHEN a Administrator decides to change his profile with a new profile
        User userDTO = new User();
        userDTO.setEmail("mike@gmail.com");
        userDTO.setUserProfile(Profile.COLLABORATOR);

        // THEN the response entity must contain the user updated and status OK
        ResponseEntity<?>expectedHTTPResponseOK = new ResponseEntity<>(mike, HttpStatus.OK);

        doNothing().when(userService).updateUser(any(User.class));
        assertEquals(expectedHTTPResponseOK,controller.changeUserProfile(userDTO));
        assertEquals(userDTO.getUserProfile(),mike.getUserProfile());

    }

    @Test
    public void testSeeUserDetails() {
        // GIVEN a users it is registered in the company.
        when(userService.getUserByID(876)).thenReturn(mike);
        // WHEN the decides to see his information
        controller.seeUserDetails(876);
        // THEN the response entity must contain the user updated and status OK
        ResponseEntity<?>expectedHTTPResponseOK = new ResponseEntity<>(mike, HttpStatus.OK);
        assertEquals(expectedHTTPResponseOK,controller.seeUserDetails(876));


    }

}