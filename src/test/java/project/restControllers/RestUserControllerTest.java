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
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
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
     * This test verifies the rest user controller can find all users registered in the system.
     */


    @Test
    public void getAllUsers(){

        //GIVEN an empty user list

        //WHEN 1 user is added to the user list
        expected.add(mike);
        expected.add(justin);
        when(userRepository.findAll()).thenReturn(expected);

        //THEN the method should return a list with the two users and the status "200 ok"
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
    public void controllerInitializedCorrectly() {
        assertNotNull(controller);
    }

    @Test
    public void searchUsersByProfileWhenExistsTest() throws Exception {

        /*

        //GIVEN 1 user in the the mock database with profile as collaborator

        when(userRepository.findAll()).thenReturn(allUsers);

        given(userService.searchUsersByProfileName("COLLABORATOR")).willReturn(collaborators);
        expected = new ArrayList<>();
        expected.add(ana);

        when(userService.searchUsersByProfileName("@")).thenReturn(expected);

        expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        //WHEN searching for users with the profile set as collaborators

        // THEN the response entity must contain an empty list and status OK
        assertEquals(expectedResponse, controller.searchUsersByProfile("COLLABORATOR"));



        /*

        //Tests if the controller finds collaborators correctly
        //Given
        given(userService.searchUsersByProfileName("COLLABORATOR")).willReturn(collaborators);

        //When
        List<User> resultCol = controller.searchUsersByProfileController("COLLABORATOR");

        //Then
        assertEquals(collaborators, resultCol);

        //Tests if the controller finds the directors correctly
        //Given
        given(userService.searchUsersByProfileName("DIRECTOR")).willReturn(directors);

        //When
        List<User> resultDir = controller.searchUsersByProfileController("DIRECTOR");

        //Then
        assertEquals(directors, resultDir);

        //Tests if the controller finds the unassigned users correctly
        //Given
        given(userService.searchUsersByProfileName("UNASSIGNED")).willReturn(unassigned);

        //When
        List<User> resultUn = controller.searchUsersByProfileController("UNASSIGNED");

        //Then
        assertEquals(unassigned, resultUn);
    }

*/

}
}