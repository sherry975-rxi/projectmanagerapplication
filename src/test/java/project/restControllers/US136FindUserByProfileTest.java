package project.restControllers;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import project.HelloJpaApplication;
import project.model.Profile;
import project.model.User;
import project.services.UserService;

import java.awt.*;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloJpaApplication.class)
@WebAppConfiguration
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
    public void getUserByProfile() throws Exception {



    }

    @Test
    public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived()
            throws ClientProtocolException, IOException {

        // Given
        String name = RandomStringUtils.randomAlphabetic( 8 );
        HttpUriRequest request = new HttpGet( "https://api.github.com/users/" + name );

        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );

        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));
    }
}
