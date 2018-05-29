package project.integration;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.security.JWTUtil;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestProjectControllerIT {

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


    User owner, michael, userPM, userRui;

    Project projectOne, projectTwo;

    Task taskOne;

    ProjectCollaborator projCollabRui, projCollabMichael;

    ResponseEntity<Project> actualRealProject, expectedProject;

    ResponseEntity<List<Project>> actualProjectList, expectedProjectList;

    HttpHeaders auth;

    String ownerToken;

    @Before
    public void setUp() {

        // create users
        owner = userService.createUser("Owner boi", "hue@hue.com", "001", "Owns projects", "0000000", "here", "there", "where", "dunno", "mars");
        michael = userService.createUser("Mike", "michael@michael.com", "002", "Tests tasks", "1111111", "here", "there", "where", "dunno", "mars");
        userPM = userService.createUser("Ana", "ana@gmail.com", "003", "manager", "221238442", "Rua Porto","4480", "Porto", "Porto", "Portugal");
        userRui = userService.createUser("Rui", "rui@gmail.com", "004", "collaborator", "221378449", "Rua Porto","4480", "Porto", "Porto", "Portugal");

        owner.setPassword(passCoder.encode("123"));
        michael.setPassword(passCoder.encode("123"));
        userPM.setPassword(passCoder.encode("123"));
        userRui.setPassword(passCoder.encode("123"));

        owner.setUserProfile(Profile.DIRECTOR);
        michael.setUserProfile(Profile.COLLABORATOR);
        userPM.setUserProfile(Profile.COLLABORATOR);
        userRui.setUserProfile(Profile.COLLABORATOR);


        userService.updateUser(michael);
        userService.updateUser(owner);
        userService.updateUser(userPM);
        userService.updateUser(userRui);

        // create projects
        projectOne = projectService.createProject("Restful Web Service", "Implement API Rest", owner);
        projectTwo = projectService.createProject("Inactive", "tests if inactive", userPM);

        // add users to projects
        projCollabRui = projectService.createProjectCollaborator(userRui, projectOne, 20);
        projCollabMichael = projectService.createProjectCollaborator(michael, projectTwo, 5);

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
     * This tests if the basic setup for project creation integration testing works correctly
     *
     * GIVEN a single project in the database
     * WHEN a new project is created via HTTP post request
     * THEN the response entity must contain the created project, with michael as the project manager. The database must also contain 3 projects
     *
     *
     */
    @Test
    public void createProjectTest() throws Exception {
        // GIVEN
        assertEquals(2, projectService.getAllProjectsfromProjectsContainer().size());

        assertEquals(projectOne.getIdCode(), projectService.getProjectById(projectOne.getIdCode()).getProjectId());
        assertEquals(projectTwo.getIdCode(), projectService.getProjectById(projectTwo.getIdCode()).getProjectId());

        // WHEN logged in as director
        ownerToken = jwtUtil.generateToken(owner.getEmail());
        auth = new HttpHeaders();
        auth.add("Authorization", "Bearer " + ownerToken);

        Project toCreate = new Project("Create with REST", "Create it good", michael);
        actualRealProject = this.restTemplate.withBasicAuth(owner.getEmail(), "123").postForEntity("http://localhost:" + port + "/projects/",
                new HttpEntity<Project>(toCreate, auth), Project.class);

        // THEN
        assertEquals("Create with REST", actualRealProject.getBody().getName());
        assertEquals(michael, actualRealProject.getBody().getProjectManager());

        assertEquals(3, projectService.getAllProjectsfromProjectsContainer().size());
    }

    /**
     * This test verifies if the setup for returning active project works properly
     *
     * GIVEN two projects in the database: one active (Planning) and one inactive (Close)
     * WHEN sending a HTTP get request to get the active project
     * THEN the response entity must contain projectOne and status OK
     *
     */

    @Test
    public void testGetActiveProjects() {

        //GIVEN
        assertEquals(2, projectService.getAllProjectsfromProjectsContainer().size());

        projectOne.setProjectStatus(Project.PLANNING);
        projectTwo.setProjectStatus(Project.CLOSE);

        projectService.updateProject(projectOne);
        projectService.updateProject(projectTwo);

        ParameterizedTypeReference<List<Project>> listOfActiveProjects = new ParameterizedTypeReference<List<Project>>() {
        };

        //WHEN logged in as Director
        ownerToken = jwtUtil.generateToken(owner.getEmail());
        auth = new HttpHeaders();
        auth.add("Authorization", "Bearer " + ownerToken);

        actualProjectList = this.restTemplate.exchange("http://localhost:" + port + "/projects/active",
                HttpMethod.GET, new HttpEntity<String>(null, auth), listOfActiveProjects);

        expectedProjectList = new ResponseEntity<>(projectService.getActiveProjects(), HttpStatus.OK);

        //THEN

        assertEquals(expectedProjectList.getBody().size(), actualProjectList.getBody().size());
        assertEquals("Restful Web Service", actualProjectList.getBody().get(0).getName());

    }

    /**
     * This test verifies if the setup for returning project details is working properly
     *
     * GIVEN a database with 2 projects
     * WHEN sending a HTTP request to get project details by project ID
     * THEN the response entity must contain information regarding the project and status OK     *
     */



    @Test
    public void testGetProjectDetails(){

        //GIVEN
        assertEquals(2, projectService.getAllProjectsfromProjectsContainer().size());


        assertEquals(projectOne, projectService.getProjectById(projectOne.getProjectId()));

        //WHEN logged in as Director
        ownerToken = jwtUtil.generateToken(owner.getEmail());
        auth = new HttpHeaders();
        auth.add("Authorization", "Bearer " + ownerToken);

        actualRealProject = this.restTemplate.exchange("http://localhost:" + port + "/projects/" + projectOne.getProjectId(), HttpMethod.GET,
                new HttpEntity<String>(null, auth), Project.class);

        // THEN the response entity must contain Mike
        expectedProject = new ResponseEntity<>(projectOne, HttpStatus.OK);
        assertEquals(expectedProject.getBody().getName(), expectedProject.getBody().getName());

    }
}