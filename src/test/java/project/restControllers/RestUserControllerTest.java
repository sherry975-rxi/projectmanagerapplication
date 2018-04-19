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
import project.model.Profile;
import project.model.User;
import project.repository.UserRepository;
import project.restcontroller.RestUserController;
import project.services.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RunWith(MockitoJUnitRunner.class)
public class RestUserControllerTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private User mike, justin, joao, daniel, ana;
    private Link mikeLink, justinLink,joaoLink, danielLink, anaLink;
    private ResponseEntity<List<User>> expectedResponse;
    private List<User> expected;
    RestUserController controller;
    private List<User> allUsers;

    private List<User> collaborators = new ArrayList<>();
    private List<User> directors = new ArrayList<>();
    private List<User> unassigned = new ArrayList<>();



    @Before
    public void setUp() throws Exception {
        initMocks(this);

        // creates three users and adds a self ref link for each
        mike = userService.createUser("Mike", "mike@gmail.com", "876", "function", "987", "here", "there", "porto", "porto", "portugal");
        mikeLink = linkTo(RestUserController.class).slash(mike.getUserID()).withSelfRel();
        mike.add(mikeLink);

        justin = userService.createUser("Justin", "justin@gmail.com", "878", "function", "9877", "here", "there", "porto", "porto", "portugal");
        justinLink = linkTo(RestUserController.class).slash(justin.getUserID()).withSelfRel();
        justin.add(justinLink);

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

        /* Adds the created users to the user repository */
        collaborators.add(ana);
        directors.add(justin);
        unassigned.add(joao);

        // and finally an empty test list to be filled and compared for each assertion
        expected = new ArrayList<>();

        // initiates the controller using the mocked Database/Service
        controller = new RestUserController(userService);
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

        when(userRepository.findAll()).thenReturn(expected);

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

    @Test
    public void searchUsersByProfileWhenExistsTest() throws Exception {


        //GIVEN 1 user in the the mock database with profile set as collaborator

        when(userRepository.findAll()).thenReturn(allUsers);

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
        when(userRepository.findByEmail("mike@gmail.com")).thenReturn(Optional.of(mike));
        User user = userService.getUserByEmail("mike@gmail.com");
        assertEquals(user,mike);
        assertEquals(Profile.UNASSIGNED,mike.getUserProfile());

        // WHEN a Administrator decides to change his profile with a new profile
        User userDTO = new User();
        userDTO.setEmail("mike@gmail.com");
        userDTO.setUserProfile(Profile.COLLABORATOR);

        // THEN the response entity must contain the user updated and status OK
        ResponseEntity<?>expectedHTTPResponseOK = new ResponseEntity<>(mike, HttpStatus.OK);
        assertEquals(expectedHTTPResponseOK,controller.changeUserProfile(userDTO));
        assertEquals(userDTO.getUserProfile(),mike.getUserProfile());

        // AND WHEN searching for a provided user that is not on the database
        when(userRepository.findByEmail("lolada@gmail.com")).thenReturn(Optional.empty());
        User user2 = userService.getUserByEmail("lolada@gmail.com");
        User userDTO2 = new User();
        userDTO2.setEmail("lolada@gmail.com");
        userDTO2.setUserProfile(Profile.COLLABORATOR);

        assertEquals(user2,null);
        // THEN the response entity must return status NOT_FOUND
        ResponseEntity<?>expectedHTTPResponseNOT = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        assertEquals(expectedHTTPResponseNOT,controller.changeUserProfile(userDTO2));
    }

}