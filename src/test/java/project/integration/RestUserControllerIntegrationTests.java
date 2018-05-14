package project.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestUserControllerIntegrationTests {

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
        mike = userService.createUser("Mike", "michael@michael.com", "002", "Tests tasks", "1111111", "here", "there", "where", "dunno", "mars");
        userPM = userService.createUser("Ana", "ana@gmail.com", "003", "manager", "221238442", "Rua Porto","4480", "Porto", "Porto", "Portugal");
        userRui = userService.createUser("Rui", "rui@gmail.com", "004", "collaborator", "221378449", "Rua Porto","4480", "Porto", "Porto", "Portugal");

        mike.setUserProfile(Profile.COLLABORATOR);
        owner.setUserProfile(Profile.DIRECTOR);
        userPM.setUserProfile(Profile.COLLABORATOR);
        userRui.setUserProfile(Profile.UNASSIGNED);

        userService.updateUser(mike);
        userService.updateUser(owner);
        userService.updateUser(userPM);

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
     * Then attempts to generate a response entity based on the given URL, asserting it contains michael after searching for his ID
     *
     */

    @Test
    public void basicUserTest() {

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
    public void shouldReturnUserList() throws Exception {

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

        // WHEN searching for a user email "michael@michael"
        actualUserList = this.restTemplate.exchange("http://localhost:" + port + "/users/email/michael@michael", HttpMethod.GET, null, listOfUsers);

        // THEN the expectedUser response entity must contain the user
        expectedUserList = new ResponseEntity<>(userService.searchUsersByPartsOfEmail("michael@michael"), HttpStatus.OK);

        assertEquals(expectedUserList.getBody().size(), actualUserList.getBody().size());
        assertEquals(1, actualUserList.getBody().size());
        assertEquals("Mike", actualUserList.getBody().get(0).getName());
    }

    /**
     * This test the URI that fetches users by profile set as Director
     * @throws Exception
     */

    @Test
    public void shouldReturnDirectorWhenSearchByProfile() throws Exception{

        //GIVEN four users in the test database
        assertEquals(4, userService.getAllUsersFromUserContainer().size());

        //WHEN searching for Profile set as DIRECTOR

        ParameterizedTypeReference<List<User>> listOfDirectors = new ParameterizedTypeReference<List<User>>() {};

        actualUserList = this.restTemplate.exchange("http://localhost:" + port + "/users/profiles/DIRECTOR", HttpMethod.GET, null, listOfDirectors);

        //THEN the expectedUserList must contain the users

        expectedUserList = new ResponseEntity<>(userService.searchUsersByProfileName("DIRECTOR"), HttpStatus.OK);

        assertEquals(expectedUserList.getBody().size(), actualUserList.getBody().size());
        assertEquals(1, actualUserList.getBody().size());
        assertEquals("Owner boi", expectedUserList.getBody().get(0).getName());

    }

    /**
     * This test the URI that fetches users by profile set as Unassigned
     * @throws Exception
     */


    @Test
    public void shouldReturnUnassignedWhenSearchByProfile() throws Exception{

        //GIVEN four users in the test database
        assertEquals(4, userService.getAllUsersFromUserContainer().size());

        //WHEN searching for profile set as UNASSIGNED

        ParameterizedTypeReference<List<User>> listOfUnassigned = new ParameterizedTypeReference<List<User>>() {};

        actualUserList = this.restTemplate.exchange("http://localhost:" + port + "/users/profiles/UNASSIGNED", HttpMethod.GET, null, listOfUnassigned);

        //THEN the expectedUserList must contain the users

        expectedUserList = new ResponseEntity<>(userService.searchUsersByProfileName("UNASSIGNED"), HttpStatus.OK);

        assertEquals(expectedUserList.getBody().size(), actualUserList.getBody().size());
        assertEquals(1, actualUserList.getBody().size());
        assertEquals("Rui", expectedUserList.getBody().get(0).getName());

    }

    /**
     * This test the URI that fetches users by profile set as Collaborator
     * @throws Exception
     */

    @Test
    public void shouldReturnCollaboratorWhenSearchByProfile() throws Exception{

        //GIVEN four users in the test database
        assertEquals(4, userService.getAllUsersFromUserContainer().size());

        //WHEN searching for profile set as UNASSIGNED

        ParameterizedTypeReference<List<User>> listOfCollaborators = new ParameterizedTypeReference<List<User>>() {};

        actualUserList = this.restTemplate.exchange("http://localhost:" + port + "/users/profiles/COLLABORATOR", HttpMethod.GET, null, listOfCollaborators);

        //THEN the expectedUserList must contain the users

        expectedUserList = new ResponseEntity<>(userService.searchUsersByProfileName("COLLABORATOR"), HttpStatus.OK);

        assertEquals(expectedUserList.getBody().size(), actualUserList.getBody().size());
        assertEquals(2, actualUserList.getBody().size());
        assertEquals("Mike", expectedUserList.getBody().get(0).getName());
        assertEquals("Ana", expectedUserList.getBody().get(1).getName());

    }



    /**
     * This test proper user validation
     *
     */
    @Test
    public void validateUserTest() {
        //GIVEN a user with no password
        assertFalse(mike.hasPassword());

        // WHEN posting a logIn request for that user with the correct email
        UserDTO requestBody = new UserDTO("Mike", "michael@michael.com", "", "", "", "", "");
        requestBody.setPassword("wrong");

        actualUser = this.restTemplate.postForEntity("http://localhost:" + port + "/account/logIn", requestBody, User.class);


        // THEN the response entity must contain the user with 3 links, for account validation
        assertEquals(HttpStatus.OK, actualUser.getStatusCode());
        assertEquals("Mike", actualUser.getBody().getName());
        assertEquals(3, actualUser.getBody().getLinks().size());


        //AND WHEN michael is given a question + answer, and chooses the link to validate via question

        mike.setQuestion("Are You testing?");
        mike.setAnswer("Yes");
        userService.updateUser(mike);

        ResponseEntity<Link> toCheckValidation = this.restTemplate.getForEntity("http://localhost:" + port + "/account/"+mike.getUserID()+"/validate/3", Link.class);

        // THEN the response entity must contain a single link with the question

        assertEquals(HttpStatus.OK, toCheckValidation.getStatusCode());
        assertEquals("Are You testing?", toCheckValidation.getBody().getRel());

        //AND WHEN michael inputs the wrong validation answer

        String inputtedCode = "Wrong answer";

        toCheckValidation = this.restTemplate.postForEntity("http://localhost:" + port + "/account/"+mike.getUserID()+"/validate/inputCode", inputtedCode, Link.class);

        // THEN the response entity should contain Forbidden
        assertEquals(HttpStatus.FORBIDDEN, toCheckValidation.getStatusCode());

        //AND WHEN michael inputs the correct answer

        inputtedCode = "Yes";

        toCheckValidation = this.restTemplate.postForEntity("http://localhost:" + port + "/account/"+mike.getUserID()+"/validate/inputCode", inputtedCode, Link.class);

        // THEN the response entity should contain OK, and a link to his data
        assertEquals(HttpStatus.OK, toCheckValidation.getStatusCode());
        assertEquals("createPassword", toCheckValidation.getBody().getRel());

    }


    @Test
    public void basicTaskTest() {
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
        UserDTO requestBodyRui = new UserDTO("JO", "rui@gmail.com", "", "", "",  "", "");
        requestBodyRui.setPassword("wrong");
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
        UserDTO requestBodyRui = new UserDTO("JO", "rui@gmail.com", "", "", "", "", "");
        requestBodyRui.setPassword("wrong");
        taskRequestResponse = this.restTemplate.postForEntity("http://localhost:" + port + "/projects/" + projectOne.getProjectId() + "/tasks/" + taskOne.getTaskID() + "/requests/removalRequest", requestBodyRui , TaskTeamRequest.class);

        // Then
        assertEquals(HttpStatus.CREATED, taskRequestResponse.getStatusCode());

        // And When
        ResponseEntity<TaskTeamRequest> result2 = this.restTemplate.postForEntity("http://localhost:" + port + "/projects/" + projectOne.getProjectId() + "/tasks/" + taskOne.getTaskID() + "/requests/removalRequest", requestBodyRui , TaskTeamRequest.class);

        // Then
        assertEquals(HttpStatus.FORBIDDEN, result2.getStatusCode());

    }

    @Test
    public void shouldCreateReport(){

        //GIVEN a reportDto
        taskOne.addProjectCollaboratorToTask(projCollabRui);
        taskService.saveTask(taskOne);

        Report reportDto = new Report();
        reportDto.setReportDbId(1);
        reportDto.setReportedTime(30.0);
        reportDto.setTaskCollaborator(taskOne.getTaskCollaboratorByEmail(userRui.getEmail()));


        //WHEN one makes a post request using uri {{server}}/projects/{projectId}/tasks/{taskId}/reports/
        ResponseEntity<Report> createdReport = this.restTemplate.postForEntity("http://localhost:" + port +
                "/projects/" + projectOne.getProjectId() + "/tasks/" + taskOne.getTaskID() + "/reports/" ,
                reportDto, Report.class);

        //THEN a created code response 200 is returned
        assertEquals(HttpStatus.CREATED, createdReport.getStatusCode());

    }

}
