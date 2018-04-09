package project.restControllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import project.model.Profile;
import project.model.User;
import project.repository.UserRepository;
import project.restcontroller.US136FindUserByProfile;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class US136FindUserByProfileTest {

    /**
     * Controller SearchUsersController
     *
     * This controllers allows the Administrator to search for users with a certain
     * profile or by email
     *
     */

    US136FindUserByProfile us136FindUserByProfileController;

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepo;

    private User newUser1;
    private User newUser2;
    private User newUser3;
    private List<User> collaborators = new ArrayList<>();
    private List<User> directors = new ArrayList<>();
    private List<User> unassigned = new ArrayList<>();

    @Before
    public void setUp() {

        // create user
        newUser1 = new User("Joao", "jl@gmail.com", "01", "Project Manager", "555555555");
        // create user2
        newUser2 = new User("Ana", "joao@gmail.com", "01", "collaborator", "221238442");
        // create user3
        newUser3 = new User("Rui", "rui@gmail.com", "01", "collaborator", "221238442");

        /* Adds the created users to the user repository */
        collaborators.add(newUser1);
        directors.add(newUser2);
        unassigned.add(newUser3);

        us136FindUserByProfileController = new US136FindUserByProfile(userService);

    }

    @After
    public void tearDown(){
        collaborators.clear();
        directors.clear();
        unassigned.clear();
    }
    @Test
    public void controllerInitializedCorrectly() {
        assertNotNull(us136FindUserByProfileController);
    }

    @Test
    public void searchUsersByProfileWhenExistsTest() throws Exception {

        //Tests if the controller finds collaborators correctly
        //Given
        given(userService.searchUsersByProfileName("COLLABORATOR")).willReturn(collaborators);

        //When
        List<User> resultCol = us136FindUserByProfileController.searchUsersByProfileController("COLLABORATOR");

        //Then
        assertEquals(collaborators, resultCol);

        //Tests if the controller finds the directors correctly
        //Given
        given(userService.searchUsersByProfileName("DIRECTOR")).willReturn(directors);

        //When
        List<User> resultDir = us136FindUserByProfileController.searchUsersByProfileController("DIRECTOR");

        //Then
        assertEquals(directors, resultDir);

        //Tests if the controller finds the unassigned users correctly
        //Given
        given(userService.searchUsersByProfileName("UNASSIGNED")).willReturn(unassigned);

        //When
        List<User> resultUn = us136FindUserByProfileController.searchUsersByProfileController("UNASSIGNED");

        //Then
        assertEquals(unassigned, resultUn);
    }

    //TODO
    @Test
    public void searchUsersByProfileWhenDoesNotExistTest() throws Exception {
        //Given
        given(userService.searchUsersByProfileName("randomsh*t")).willReturn(new ArrayList<>());

        //When
        List<User> result = us136FindUserByProfileController.searchUsersByProfileController("randomsh*t");

        //Then
        assertEquals(new ArrayList<>(), result);

    }
    
}
