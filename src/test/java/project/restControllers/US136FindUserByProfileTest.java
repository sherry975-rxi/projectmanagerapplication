package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import project.model.Project;
import project.model.User;
import project.restcontroller.US136FindUserByProfile;
import project.services.UserService;
import project.services.exceptions.ObjectNotFoundException;

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

    @InjectMocks
    US136FindUserByProfile us136FindUserByProfileController;

    @Mock
    UserService userService;

    private MockMvc mockMvc;
    private User newUser1;
    private User newUser2;
    private User newUser3;
    private List<User> collaborators = new ArrayList<>();

    //This method will be initialized by the initFields below
    private JacksonTester<Project> projectJack;
    private JacksonTester<User> taskJack;

    @Before
    public void setUp() {

        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(us136FindUserByProfileController).build();

        // create user
        newUser1 = new User("Joao", "jl@gmail.com", "01", "Project Manager", "555555555");
        // create user2
        newUser2 = new User("Ana", "joao@gmail.com", "01", "collaborator", "221238442");
        // create user3
        newUser3 = new User("Rui", "rui@gmail.com", "01", "collaborator", "221238442");

        /* Adds the created users to the user repository */
        userService.addUserToUserRepositoryX(newUser1);
        userService.addUserToUserRepositoryX(newUser2);
        userService.addUserToUserRepositoryX(newUser3);

        collaborators.clear();

    }

    @Test
    public void controllerInitializedCorrectly() {
        assertNotNull(us136FindUserByProfileController);
    }

    @Test
    public void searchUsersByProfileWhenExistsTest() throws Exception {

        //Given
        collaborators.add(newUser1);
        given(userService.searchUsersByProfileName("COLLABORATOR")).willReturn(collaborators);

        //When
        MockHttpServletResponse response = mockMvc.perform(get("/users/COLLABORATOR").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //Then
        assertEquals(response.getContentAsString(), "[" + taskJack.write(newUser1).getJson() + "]");
    }

    //TODO
    @Test
    public void searchUsersByProfileWhenDoesNotExistTest() throws Exception {

        //Given
        given(userService.searchUsersByProfileName("aaaaaa")).willThrow(new ObjectNotFoundException("Profile not found"));

        //When
        MockHttpServletResponse response = mockMvc.perform(get("/users/DD").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertEquals(0, response.getContentLength());

    }
    
}
