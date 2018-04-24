package project.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.*;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import project.model.User;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestControllerNewIntergrationTests {

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

    User owner, mike;

    @Before
    public void setUp() {

        owner = userService.createUser("Owner boi", "hue@hue.com", "001", "Owns projects", "0000000", "here", "there", "where", "dunno", "mars");
        mike = userService.createUser("Mike", "mike@mike.com", "002", "Tests tasks", "1111111", "here", "there", "where", "dunno", "mars");



    }

    @Test
    public void basicUserTest() throws Exception {

        assertEquals(2, userService.getAllUsersFromUserContainer().size());
        assertEquals(mike, userService.getUserByID(mike.getUserID()));

        ResponseEntity<User> actual = this.restTemplate.getForEntity("http://localhost:" + port + "/users/" + mike.getUserID(),
                User.class);


        ResponseEntity<User> expected = new ResponseEntity<>(mike, HttpStatus.OK);

        assertEquals(expected.getBody().getName(), actual.getBody().getName());

    }

}
