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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import project.dto.UserDTO;
import project.model.*;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

    ResponseEntity<User> actualUser, expectedUser;

    ResponseEntity<List<User>> actualUserList, expectedUserList;

    ResponseEntity<Project> actualRealProject;

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

        // GIVEN four users in the test Database
        assertEquals(4, userService.getAllUsersFromUserContainer().size());
        assertEquals(mike, userService.getUserByID(mike.getUserID()));

        // WHEN the response entity is fetched from the find User by ID URL
        actualUser = this.restTemplate.getForEntity("http://localhost:" + port + "/users/" + mike.getUserID(),
                User.class);


        // THEN the response entity must contain Mike
        expectedUser = new ResponseEntity<>(mike, HttpStatus.OK);
        assertEquals(expectedUser.getBody().getName(), actualUser.getBody().getName());

    }

    /**
     * This tests the various URI's that fetch lists of users
     *
     * @throws Exception
     */
    @Test
    public void userListTests() throws Exception {

        // GIVEN four users in the test Database
        assertEquals(4, userService.getAllUsersFromUserContainer().size());

        ParameterizedTypeReference<List<User>> listOfUsers = new ParameterizedTypeReference<List<User>>() {};

        // WHEN searching for all users
        actualUserList = this.restTemplate.exchange("http://localhost:" + port + "/users/allUsers", HttpMethod.GET, null, listOfUsers);

        // THEN the expectedUser response entity must contain all users in the container
        expectedUserList = new ResponseEntity<>(userService.getAllUsersFromUserContainer(), HttpStatus.OK);

        assertEquals(expectedUserList.getBody().size(), actualUserList.getBody().size());
        assertEquals("Owner boi", actualUserList.getBody().get(0).getName());
        assertEquals("Mike", actualUserList.getBody().get(1).getName());

    }

    /**
     * This tests the URI that fetches user by email
     * @throws Exception
     */
    @Test
    public void shouldReturnUserByEmail() throws Exception{
        // GIVEN four users in the test Database
            assertEquals(4, userService.getAllUsersFromUserContainer().size());

            ParameterizedTypeReference<List<User>> listOfUsers = new ParameterizedTypeReference<List<User>>() {};

        // WHEN searching for a user email "mike@mike"
        actualUserList = this.restTemplate.exchange("http://localhost:" + port + "/users/email/mike@mike", HttpMethod.GET, null, listOfUsers);

        // THEN the expectedUser response entity must contain the user
        expectedUserList = new ResponseEntity<>(userService.searchUsersByPartsOfEmail("mike@mike"), HttpStatus.OK);

        assertEquals(expectedUserList.getBody().size(), actualUserList.getBody().size());
        assertEquals(1, actualUserList.getBody().size());
        assertEquals("Mike", actualUserList.getBody().get(0).getName());
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

        actualUser = this.restTemplate.postForEntity("http://localhost:" + port + "/account/logIn", requestBody, User.class);

        // THEN the expectedUser status should be Forbidden
        expectedUser = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        assertEquals(expectedUser.getStatusCode(), actualUser.getStatusCode());

        // AND WHEN the request contains the actualUser password
        requestBody = new UserDTO("Mike", "mike@mike.com", "", "", "", "123456", "", "");

        actualUser = this.restTemplate.postForEntity("http://localhost:" + port + "/account/logIn", requestBody, User.class);

        // THEN the expectedUser response entity should contain Mike
        expectedUser = new ResponseEntity<>(mike, HttpStatus.OK);
        assertEquals(expectedUser.getBody().getName(), actualUser.getBody().getName());
        assertEquals(expectedUser.getStatusCode(), actualUser.getStatusCode());



    }

    /**
     *
     *
     *
     */
    @Test
    public void validateUserTest() {
        //GIVEN a user with no password
        assertFalse(mike.hasPassword());

        // WHEN posting a logIn request for that user with the correct email
        UserDTO requestBody = new UserDTO("Mike", "mike@mike.com", "", "", "", "wrong", "", "");

        actualUser = this.restTemplate.postForEntity("http://localhost:" + port + "/account/logIn", requestBody, User.class);


        // THEN the response entity must contain the user with 3 links, for account validation
        assertEquals(HttpStatus.OK, actualUser.getStatusCode());
        assertEquals("Mike", actualUser.getBody().getName());
        assertEquals(3, actualUser.getBody().getLinks().size());


        //AND WHEN mike is given a question + answer, and chooses the link to validate via question

        mike.setQuestion("Are You testing?");
        mike.setAnswer("Yes");
        userService.updateUser(mike);

        ResponseEntity<Link> toCheckValidation = this.restTemplate.getForEntity("http://localhost:" + port + "/account/"+mike.getUserID()+"/validate/3", Link.class);

        // THEN the response entity must contain a single link with the question

        assertEquals(HttpStatus.OK, toCheckValidation.getStatusCode());
        assertEquals("Are You testing?", toCheckValidation.getBody().getRel());

        //AND WHEN mike inputs the wrong validation answer

        String inputtedCode = "Wrong answer";

        toCheckValidation = this.restTemplate.postForEntity("http://localhost:" + port + "/account/"+mike.getUserID()+"/validate/inputCode", inputtedCode, Link.class);

        // THEN the response entity should contain Forbidden
        assertEquals(HttpStatus.FORBIDDEN, toCheckValidation.getStatusCode());

        //AND WHEN mike inputs the correct answer

        inputtedCode = "Yes";

        toCheckValidation = this.restTemplate.postForEntity("http://localhost:" + port + "/account/"+mike.getUserID()+"/validate/inputCode", inputtedCode, Link.class);

        // THEN the response entity should contain OK, and a link to his data
        assertEquals(HttpStatus.OK, toCheckValidation.getStatusCode());
        assertEquals("createPassword", toCheckValidation.getBody().getRel());



    }


    /**
     * This tests if the basic setup for project creation integration testing works correctly
     *
     * @throws Exception
     */
    @Test
    public void basicProjectTest() throws Exception {
        // GIVEN a single project in the database
        assertEquals(1, projectService.getAllProjectsfromProjectsContainer().size());
        assertEquals(projectOne.getIdCode(), projectService.getProjectById(projectOne.getIdCode()).getProjectId());

        // WHEN a new project is created via HTTP post request
        Project toCreate = new Project("Create with REST", "Create it good", mike);
        actualRealProject = this.restTemplate.postForEntity("http://localhost:" + port + "/projects/", toCreate, Project.class);

        // THEN the response entity must contain the created project, with mike as the project manager. The database must also contain 2 projects
        assertEquals("Create with REST", actualRealProject.getBody().getName());
        assertEquals(mike, actualRealProject.getBody().getProjectManager());

        assertEquals(2, projectService.getAllProjectsfromProjectsContainer().size());
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

        // Given
        assertEquals(0, taskOne.getTaskTeam().size());
        assertEquals(0, taskOne.getPendingTaskTeamRequests().size());

        // When
        UserDTO requestBodyRui = new UserDTO("JO", "rui@gmail.com", "", "", "", "wrong", "", "");
        taskRequestResponse = this.restTemplate.postForEntity("http://localhost:" + port + "/projects/" + projectOne.getProjectId() + "/tasks/" + taskOne.getTaskID() + "/requests/assignmentRequest", requestBodyRui , TaskTeamRequest.class);

        // Then
        assertEquals(HttpStatus.CREATED, taskRequestResponse.getStatusCode());

        // And When
        ResponseEntity<TaskTeamRequest> result2 = this.restTemplate.postForEntity("http://localhost:" + port + "/projects/" + projectOne.getProjectId() + "/tasks/" + taskOne.getTaskID() + "/requests/assignmentRequest", requestBodyRui , TaskTeamRequest.class);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, result2.getStatusCode());

    }

    /**
     * Given
     * Adding userRui to taskOne
     *
     * When
     * Creating removal request for userRui but already is added to taskOne
     *
     * Then
     * It is expected to be successfully created
     *
     * And When
     * Creating again removal request that already exists for userRui
     *
     * Then
     * Expects a FORBIDDEN message
     */
    @Test
    public void canCreateARemovalRequest() {


        // Given
        taskOne.addProjectCollaboratorToTask(projCollabRui);
        taskService.saveTask(taskOne);


        // When
        UserDTO requestBodyRui = new UserDTO("JO", "rui@gmail.com", "", "", "", "wrong", "", "");
        taskRequestResponse = this.restTemplate.postForEntity("http://localhost:" + port + "/projects/" + projectOne.getProjectId() + "/tasks/" + taskOne.getTaskID() + "/requests/removalRequest", requestBodyRui , TaskTeamRequest.class);

        // Then
        assertEquals(HttpStatus.CREATED, taskRequestResponse.getStatusCode());

        // And When
        ResponseEntity<TaskTeamRequest> result2 = this.restTemplate.postForEntity("http://localhost:" + port + "/projects/" + projectOne.getProjectId() + "/tasks/" + taskOne.getTaskID() + "/requests/removalRequest", requestBodyRui , TaskTeamRequest.class);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, result2.getStatusCode());

    }


}
