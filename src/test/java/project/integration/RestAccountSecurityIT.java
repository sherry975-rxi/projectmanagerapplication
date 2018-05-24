package project.integration;

import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.RequestMatcher;
import project.dto.CredentialsDTO;
import project.model.Profile;
import project.model.User;
import project.security.JWTUtil;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestAccountSecurityIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;


    @Autowired
    BCryptPasswordEncoder passCoder;

    @Autowired
    JWTUtil jwtUtil;

    User owner, mike, userPM, userRui;

    ResponseEntity<User> userResponse;

    HttpHeaders auth;

    String mikeToken;

    String ruiToken;

    String adminToken;


    @Before
    public void setUp() {

        // create users
        owner = userService.createUser("Owner boi", "hue@hue.com", "001", "Owns projects", "0000000", "here", "there", "where", "dunno", "mars");
        mike = userService.createUser("Mike", "michael@michael.com", "002", "Tests tasks", "1111111", "here", "there", "where", "dunno", "mars");
        userPM = userService.createUser("Ana", "ana@gmail.com", "003", "manager", "221238442", "Rua Porto", "4480", "Porto", "Porto", "Portugal");
        userRui = userService.createUser("Rui", "rui@gmail.com", "004", "collaborator", "221378449", "Rua Porto", "4480", "Porto", "Porto", "Portugal");

        owner.setPassword(passCoder.encode("1"));
        mike.setPassword(passCoder.encode("12"));
        userPM.setPassword(passCoder.encode("123"));
        userRui.setPassword(passCoder.encode("1234"));

        owner.setUserProfile(Profile.ADMIN);
        mike.setUserProfile(Profile.COLLABORATOR);
        userPM.setUserProfile(Profile.COLLABORATOR);
        userRui.setUserProfile(Profile.COLLABORATOR);

        userService.updateUser(mike);
        userService.updateUser(owner);
        userService.updateUser(userPM);

        userService.updateUser(userRui);


    }



    @After
    public void tearDown() {

        taskService.getTaskRepository().deleteAll();
        projectService.getProjectCollaboratorRepository().deleteAll();
        projectService.getProjectsRepository().deleteAll();
        userService.getUserRepository().deleteAll();


    }


    /**
     * This test attempts to access private information after validating the user via the security laywer
     *
     * 1 - Mike should be able to view his own personal data post authentication
     * 2 - Rui should be unable to view Mike's personal information due to his collaborator permissions
     * 3 - Owner should view Mike's personal information, using his admin credentials
     *
     */
    @Test
    public void basicSetUpTest() {
        // GIVEN four users in the database
        assertEquals(4, userService.getAllUsersFromUserContainer().size());


        // WHEN Mike has logged in and attempts to view his data
        mikeToken = jwtUtil.generateToken(mike.getEmail());
        auth = new HttpHeaders();
        auth.add("Authorization", "Bearer " + mikeToken);

        userResponse = restTemplate.withBasicAuth("michael@michael.com", "12")
                .exchange("http://localhost:" + port + "/users/" + mike.getUserID(), HttpMethod.GET,
                        new HttpEntity<String>(null, auth), User.class);



        // THEN the response entity must contain his personal information
        assertEquals(mike.getUserID(), userResponse.getBody().getUserID());
        assertEquals(200, userResponse.getStatusCodeValue());


        // AND WHEN Rui has logged in and attempts to view Mike's data
        ruiToken = jwtUtil.generateToken(userRui.getEmail());
        auth = new HttpHeaders();
        auth.add("Authorization", "Bearer " + ruiToken);

        userResponse = restTemplate.withBasicAuth("rui@gmail.com", "1234")
                .exchange("http://localhost:" + port + "/users/" + mike.getUserID(), HttpMethod.GET,
                        new HttpEntity<String>(null, auth), User.class);


        //THEN he cannot view Mike's information
        assertEquals(403, userResponse.getStatusCodeValue());


        // AND WHEN an Admin has logged in and attempts to view Mike's data
        adminToken = jwtUtil.generateToken(owner.getEmail());
        auth = new HttpHeaders();
        auth.add("Authorization", "Bearer " + adminToken);

        userResponse = restTemplate.withBasicAuth("hue@hue.com", "1")
                .exchange("http://localhost:" + port + "/users/" + mike.getUserID(), HttpMethod.GET,
                        new HttpEntity<String>(null, auth), User.class);



        // THEN the response entity must contain Mike's personal information
        assertEquals(mike.getUserID(), userResponse.getBody().getUserID());
        assertEquals(200, userResponse.getStatusCodeValue());

    }

}
