package project.integration;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestProjectControllerIntegrationTest {

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

    User owner, michael, userPM, userRui;

    Project projectOne;

    Task taskOne;

    ProjectCollaborator projCollabRui;

    ResponseEntity<Project> actualRealProject;


    @Before
    public void setUp() {

        // create users
        owner = userService.createUser("Owner boi", "hue@hue.com", "001", "Owns projects", "0000000", "here", "there", "where", "dunno", "mars");
        michael = userService.createUser("Mike", "michael@michael.com", "002", "Tests tasks", "1111111", "here", "there", "where", "dunno", "mars");
        userPM = userService.createUser("Ana", "ana@gmail.com", "003", "manager", "221238442", "Rua Porto","4480", "Porto", "Porto", "Portugal");
        userRui = userService.createUser("Rui", "rui@gmail.com", "004", "collaborator", "221378449", "Rua Porto","4480", "Porto", "Porto", "Portugal");

        userService.updateUser(michael);
        userService.updateUser(owner);
        userService.updateUser(userPM);
        userService.updateUser(userRui);

        // create projects
        projectOne = projectService.createProject("Restful Web Service", "Implement API Rest", owner);

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
     * This tests if the basic setup for project creation integration testing works correctly
     */
    @Test
    public void basicProjectTest() {
        // GIVEN a single project in the database
        assertEquals(1, projectService.getAllProjectsfromProjectsContainer().size());
        assertEquals(projectOne.getIdCode(), projectService.getProjectById(projectOne.getIdCode()).getProjectId());

        // WHEN a new project is created via HTTP post request
        Project toCreate = new Project("Create with REST", "Create it good", michael);
        actualRealProject = this.restTemplate.postForEntity("http://localhost:" + port + "/projects/", toCreate, Project.class);

        // THEN the response entity must contain the created project, with michael as the project manager. The database must also contain 2 projects
        assertEquals("Create with REST", actualRealProject.getBody().getName());
        assertEquals(michael, actualRealProject.getBody().getProjectManager());

        assertEquals(2, projectService.getAllProjectsfromProjectsContainer().size());
    }

}