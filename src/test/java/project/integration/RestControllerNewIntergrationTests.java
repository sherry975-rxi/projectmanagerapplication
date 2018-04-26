package project.integration;

import org.junit.After;
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

import project.dto.UserDTO;
import project.model.*;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    User owner, mike, userPM, userRui ;

    Project projectOne;

    Task taskOne;

    ProjectCollaborator projCollabRui;

    ResponseEntity<User> actual, expected;
    ResponseEntity<TaskTeamRequest> taskRequestResponse;

    @Before
    public void setUp() {

        // create users
        owner = userService.createUser("Owner boi", "hue@hue.com", "001", "Owns projects", "0000000", "here", "there", "where", "dunno", "mars");
        mike = userService.createUser("Mike", "mike@mike.com", "002", "Tests tasks", "1111111", "here", "there", "where", "dunno", "mars");
        userPM = userService.createUser("Ana", "ana@gmail.com", "003", "manager", "221238442", "Rua Porto","4480", "Porto", "Porto", "Portugal");
        userRui = userService.createUser("Rui", "rui@gmail.com", "004", "collaborator", "221378449", "Rua Porto","4480", "Porto", "Porto", "Portugal");

        // create projects
        projectOne = projectService.createProject("Restful Web Service", "Implement API Rest", userPM);

        // add users to projects
        projCollabRui = projectService.createProjectCollaborator(userRui, projectOne, 20);

        // create tasks in projects
        taskOne = taskService.createTask("Create Rest Controller", projectOne);



    }

    @After
    public void tearDown() {
        actual = null;
        expected = null;
        taskOne.getPendingTaskTeamRequests().clear();
        taskService.saveTask(taskOne);
        taskService.getTaskRepository().deleteAll();
        projectService.getProjectCollaboratorRepository().deleteAll();
        projectService.getProjectsRepository().deleteAll();
        userService.getUserRepository().deleteAll();


    }

    /**
     * This tests the basic set up of the functional test using a simple "get User details by ID" method
     *
     * First, it asserts the chosen users are in the database
     * Then attempts to generate a response entity based on the given URL, asserting it contains mike after searching for his ID
     *
     * @throws Exception
     */

    @Test
    public void basicUserTest() throws Exception {
        mike = userService.getUserByEmail("mike@mike.com");

        // GIVEN two users in the test Database
        assertEquals(4, userService.getAllUsersFromUserContainer().size());
        assertEquals(mike, userService.getUserByID(mike.getUserID()));

        // WHEN the response entity is fetched from the find User by ID URL
        actual = this.restTemplate.getForEntity("http://localhost:" + port + "/users/" + mike.getUserID(),
                User.class);


        // THEN the response entity must contain Mike
        expected = new ResponseEntity<>(mike, HttpStatus.OK);
        assertEquals(expected.getBody().getName(), actual.getBody().getName());

    }

    /**
     * Integration test for log in Rest API.
     * First, it tests if an incorrect password returns Status Code FORBIDDEN
     *
     * Then, it tests if using a correct password returns the logged in user and Status OK
     *
     *
     * @throws Exception
     */
    @Test
    public void logInUserTest() throws Exception {
        // GIVEN a user with a password 123456
        mike.setPassword("123456");
        userService.updateUser(mike);

        // WHEN posting a logIn request using an incorrect password
        UserDTO requestBody = new UserDTO("Mike", "mike@mike.com", "", "", "", "wrong", "", "");

        actual = this.restTemplate.postForEntity("http://localhost:" + port + "/account/logIn", requestBody, User.class);

        // THEN the expected status should be Forbidden
        expected = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        assertEquals(expected.getStatusCode(), actual.getStatusCode());

        // AND WHEN the request contains the actual password
        requestBody = new UserDTO("Mike", "mike@mike.com", "", "", "", "123456", "", "");

        actual = this.restTemplate.postForEntity("http://localhost:" + port + "/account/logIn", requestBody, User.class);

        // THEN the expected response entity should contain Mike
        expected = new ResponseEntity<>(mike, HttpStatus.OK);
        assertEquals(expected.getBody().getName(), actual.getBody().getName());
        assertEquals(expected.getStatusCode(), actual.getStatusCode());



    }


    @Test
    public void basicProjectTest() throws Exception {
        assertEquals(1, projectService.getAllProjectsfromProjectsContainer().size());
        assertEquals(projectOne.getIdCode(), projectService.getProjectById(projectOne.getIdCode()).getProjectId());
    }


    @Test
    public void basicTaskTest() throws Exception {
        assertEquals(1, taskService.getAllTasksFromTaskRepository().size());
        assertEquals(taskOne.getTaskID(), taskService.getProjectTasks(projectOne).get(0).getTaskID());
    }

    /**
     * Given
     * TaskOne in projectOne, with no collaborators, neither requests
     *
     * When
     * Creating assignment request to taskOne from userRui
     *
     * Then
     * It is expected to be successfully created
     *
     * And When
     * Creating again assignment request that already exists for userRui
     *
     * Then
     * Expects a FORBIDDEN message
     */
    @Test
    public void canCreateAnAssignmentRequest() {

        //Given
        assertEquals(0, taskOne.getTaskTeam().size());
        assertEquals(0, taskOne.getPendingTaskTeamRequests().size());

        //When
        UserDTO requestBodyRui = new UserDTO("JO", "rui@gmail.com", "", "", "", "wrong", "", "");
        taskRequestResponse = this.restTemplate.postForEntity("http://localhost:" + port + "/projects/" + projectOne.getProjectId() + "/tasks/" + taskOne.getTaskID() + "/requests/assignmentRequest", requestBodyRui , TaskTeamRequest.class);

        //Then
        assertEquals(HttpStatus.CREATED, taskRequestResponse.getStatusCode());

        // And When
        ResponseEntity<TaskTeamRequest> result2 = this.restTemplate.postForEntity("http://localhost:" + port + "/projects/" + projectOne.getProjectId() + "/tasks/" + taskOne.getTaskID() + "/requests/assignmentRequest", requestBodyRui , TaskTeamRequest.class);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, result2.getStatusCode());

    }


}
