package project.restControllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import project.model.Profile;
import project.model.User;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//Configurar H2
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class US136FindUserByProfileTest {

    /**
     * Controller SearchUsersController
     *
     * This controllers allows the Administrator to search for users with a certain
     * profile or by email
     *
     */
    @Autowired
    UserService userService;

    private User newUser1;
    private User newUser2;
    private User newUser3;

    @Autowired
    US136FindUserByProfile searchController;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception{

        // create user
        newUser1 = userService.createUser("Ana", "ana@gmail.com", "01", "collaborator", "221238442", "Rua Porto",
                "4480", "Porto", "Porto", "Portugal");
        // create user2
        newUser2 = userService.createUser("Joao", "joao@gmail.com", "01", "collaborator", "221238442", "Rua Porto",
                "4480", "Porto", "Porto", "Portugal");
        // create user3
        newUser3 = userService.createUser("Rui", "rui@gmail.com", "01", "collaborator", "221238442", "Rua Porto",
                "4480", "Porto", "Porto", "Portugal");

        /* Adds the created users to the user repository */
        userService.addUserToUserRepositoryX(newUser1);
        userService.addUserToUserRepositoryX(newUser2);
        userService.addUserToUserRepositoryX(newUser3);

        userService.getAllUsersFromUserContainer();

    }

    @After
    public void clear() throws Exception{
        newUser1 = null;
        newUser2 = null;
        newUser3 = null;

    }

    @Test
    public void test()
            throws Exception {

        newUser1.setUserProfile(Profile.COLLABORATOR);
        newUser3.setUserProfile(Profile.COLLABORATOR);

        List<User> userListResult = new ArrayList<>();

        userListResult.add(newUser1);
        userListResult.add(newUser3);


        this.mockMvc.perform(get("/users/COLLABORATOR")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Ana")));
    }
}
