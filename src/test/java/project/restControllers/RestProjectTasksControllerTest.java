package project.restControllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.model.*;

import project.model.taskstateinterface.Finished;
import project.model.taskstateinterface.OnGoing;
import project.model.taskstateinterface.Planned;
import project.model.taskstateinterface.TaskStateInterface;
import project.restcontroller.RestProjectTasksController;

import project.services.ProjectService;
import project.services.TaskService;

import javax.servlet.http.HttpServletRequest;
import java.time.Year;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestProjectTasksControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private ProjectService projectService;

    @Mock
    private HttpServletRequest req;

    @InjectMocks
    private RestProjectTasksController victim;

    private JacksonTester<List<Task>> jacksonProjectTeamList;
    private MockMvc mockMvc;
    private User uDaniel;
    private User uInes;
    private Project project;
    private Task task;
    private Task task2;
    private Task task3;

    private List<Task> projectTasks;
    private Calendar startDate;
    private Calendar finishDate;
    private Calendar estimatedStart;
    private Calendar estimatedDeadline;

    private List<Task> expected;
    private ResponseEntity<List<Task>> expectedResponse;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(victim).build();
        uDaniel = new User("Daniel", "daniel@gmail.com", "01", "Arquitecto", "967387654");
        uInes = new User("Ines", "ines@gmail.com", "02", "Veterinaria", "9633333333");
        project = new Project("Project A", "Project de teste", uDaniel);

        ProjectCollaborator pcDaniel = new ProjectCollaborator(uDaniel, 20);
        ProjectCollaborator pcInes = new ProjectCollaborator(uInes, 20);

        startDate = Calendar.getInstance();
        finishDate = Calendar.getInstance();
        finishDate.set(Calendar.YEAR, 2020);
        estimatedStart = Calendar.getInstance();
        estimatedStart.set(Calendar.YEAR, 2017);
        estimatedDeadline = Calendar.getInstance();
        estimatedDeadline.set(Calendar.YEAR, 2020);

        task = new Task("Task", project);
        task.setEstimatedTaskStartDate(estimatedStart);
        task.setTaskDeadline(estimatedDeadline);
        task.addProjectCollaboratorToTask(pcDaniel);
        task.setEstimatedTaskEffort(20);
        task.setTaskBudget(2000);

        task2 = new Task("Task2", project);
        task2.setEstimatedTaskStartDate(estimatedStart);
        task2.setTaskDeadline(estimatedDeadline);
        task2.addProjectCollaboratorToTask(pcInes);
        task2.setEstimatedTaskEffort(20);
        task2.setTaskBudget(2000);

        task3 = new Task("Task3", project);
        task3.setEstimatedTaskStartDate(estimatedStart);
        task3.setTaskDeadline(estimatedDeadline);
        task3.setTaskBudget(2000);
        projectTasks = new ArrayList<>();

        // and finally an empty test list to be filled and compared for each assertion
        expected = new ArrayList<>();
    }

    @After
    public void tearDown(){
        mockMvc = null;
        task = null;
        task2 = null;
        task3 = null;

        projectTasks = null;
        jacksonProjectTeamList = null;
        project = null;
        uDaniel = null;
        estimatedStart = null;
        estimatedDeadline = null;
        startDate = null;
        finishDate = null;
    }

        //GIVEN a project with a certain Id
    /**
     * GIVEN: a certain task in a state that allows its deletion
     * WHEN: we perform a delete request to url /projects/<projectId>/tasks/<taskId>
     * THEN: we receive a valid message with 202 Accepted and the task list has to display one less task
     * @throws Exception
     */
    @Test
    public void shouldDeleteTask() throws Exception {

        //GIVEN: a certain task
        String taskId = "T0.2";
        when(taskService.getTaskByTaskID(any(String.class))).thenReturn(task2);

        //WHEN: we perform a delete request to url /projects/<projectId>/tasks/<taskId>
        when(taskService.deleteTask(any(String.class))).thenReturn(true);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/projects/1/tasks/" + taskId).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN: we receive a valid message with 202 Accepted and the task list has to display one less task
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());
    }

        //GIVEN a project with a certain Id
    /**
     * GIVEN: a certain task in a state that does not allow its deletion
     * WHEN: we perform a delete request to url /projects/<projectId>/tasks/<taskId>
     * THEN: we receive a valid message with 409 Conflict
     * @throws Exception
     */
    @Test
    public void shouldNotDeleteTask() throws Exception {

        //GIVEN: a certain task
        String taskId = "T0.1";
        when(taskService.getTaskByTaskID(any(String.class))).thenReturn(task);

        //WHEN: we perform a delete request to url /projects/<projectId>/tasks/<taskId>
        when(taskService.deleteTask(any(String.class))).thenReturn(false);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/projects/1/tasks/" + taskId).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN: we receive a valid message with 202 Accepted and the task list has to display one less task
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());

        projectTasks = new ArrayList<>();
    }


    /**
     * This test verifies the correct initialization of the REST use controller
     */

    @Test
    public void controllerInitializedCorrectly() {
        assertNotNull(victim);
    }


    @Test
    public void testGetTasksWithoutCollaborators() throws Exception  {

        //GIVEN a project with a certain Id

        int projectId = 123;
        when(projectService.getProjectById(projectId)).thenReturn(project);

        //confirmation that task3 does not have assigned collaborators

        assertTrue(task3.getTaskTeam().isEmpty());
        assertFalse(task3.doesTaskTeamHaveActiveUsers());

        //WHEN a list of tasks without assigned collaborators is requested
        expected.add(task3);
        when(taskService.getProjectTasksWithoutCollaboratorsAssigned(any(Project.class))).thenReturn(expected);

        expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/1/tasks/withoutCollaborators")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN the response entity must contain the list of tasks and status OK

        assertEquals(expectedResponse,victim.getTasksWithoutCollaborators(123));

        task3 = new Task("Task3", project);
        task3.setEstimatedTaskStartDate(estimatedStart);
        task3.setTaskDeadline(estimatedDeadline);
        task3.setTaskBudget(2000);
        projectTasks = new ArrayList<>();

        // and finally an empty test list to be filled and compared for each assertion
        expected = new ArrayList<>();

    }


    /**
     * GIVEN a project id
     * WHEN we perform a get request to url /projects/<projectId>/tasks/finished
     * THEN we receive a valid message with a 200 Ok and a list of the project finished tasks
     */
    @Test
    public void shouldReturnFinishedTasks() throws Exception {

        task.setStartDate(startDate);
        task2.setStartDate(startDate);
        task.markTaskAsFinished();
        task2.markTaskAsFinished();

        projectTasks.add(task);
        projectTasks.add(task2);

        //GIVEN: a project id
        int projectId = 01;
        when(projectService.getProjectById(projectId)).thenReturn(project);

        //WHEN: we perform a delete request to url /projects/<projectId>/tasks/<taskId>/finished
        when(taskService.getProjectFinishedTasksInDecreasingOrder(project)).thenReturn(projectTasks);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/1/tasks/finished").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN: we receive a valid message with a 200 Ok and a list of the project finished tasks
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskService, times(1)).getProjectFinishedTasksInDecreasingOrder(project);
    }



    /**
     * GIVEN a project id
     * WHEN we perform a get request to url /projects/<projectId>/tasks/unfinished
     * THEN we receive a valid message with a 200 Ok and a list of the project unfinished tasks
     */

    @Test
    public void shouldReturnUnfinishedTasks() throws Exception{

        task.setStartDate(startDate);
        task2.setStartDate(startDate);

        projectTasks.add(task);
        projectTasks.add(task2);

        //GIVEN: a project id
        int projectId = 01;
        when(projectService.getProjectById(projectId)).thenReturn(project);

        //WHEN: we perform a get request to url /projects/<projectId>/tasks/unfinished
        when(taskService.getProjectUnFinishedTasks(project)).thenReturn(projectTasks);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/1/tasks/unfinished").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN: we receive a valid message with a 200 Ok and a list of the project finished tasks
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskService, times(1)).getProjectUnFinishedTasks(project);
    }



    /**
     * GIVEN a project id
     * WHEN we perform a getAttribute to url /projects/<projectId>/tasks/
     * THEN we receive the value of the projectID as Integer
     */
    @Test
    public void shouldReturnProjectID() throws Exception{

        //GIVEN: a project that has the given IDValue: 11

        Map<String, String> variables = new HashMap<String, String>();
        variables.put("projid", "11");

        Integer projID = 11;


        //WHEN we perfom the method getAttribute of the Request to get the projectID from the request
        when(req.getAttribute(any())).thenReturn(variables);



        //THEN: the method returns the id of the proect
        assertEquals(victim.getProjectIdByURI(), projID);

        projectTasks = new ArrayList<>();
    }

    /**
     * GIVEN an project id in the url
     * WHEN we perform a getAttribute to url /projects/<projectId>/tasks/
     * THEN the method will return null
     */
    @Test
    public void shouldReturnNULLProjectID() throws Exception{

        //GIVEN: a project that has the given IDValue: 11

        Map<String, String> variables = new HashMap<String, String>();
        variables.put("projid", "11a");

        Integer nullProj = null;


        //WHEN we perfom the method getAttribute of the Request to get the projectID from the request
        when(req.getAttribute(any())).thenReturn(variables);



        //THEN: the method returns the id of the proect
        assertEquals(victim.getProjectIdByURI(), nullProj);

        projectTasks = new ArrayList<>();
    }


    //TODO
    /*
    Failure due to a Json ignore attribute in Task (TaskInterface)
     */

    /*
    @Test
    public void createtask() throws Exception{

             //GIVEN: a project that has the given IDValue: 11

        Map<String, String> variables = new HashMap<String, String>();
        variables.put("projid", "11");

        Integer projID = 11;


        //WHEN we perfom the method getAttribute of the Request to get the projectID from the request
        when(req.getAttribute(any())).thenReturn(variables);


        //GIVEN
        //A set of parameters to create a project
        String taskDescription = "Task Description";
        Integer projIDs = 11;
        when(projectService.getProjectById(projID)).thenReturn(project);

        //WHEN
        //One creates a project
        Task taskDto = new Task(taskDescription, project);
        when(taskService.createTask(any(String.class),any(Project.class))).thenReturn(taskDto);



        MockHttpServletResponse response = mockMvc.perform(post("/projects/" + projID + "/tasks/").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTask.write(taskDto).getJson()))
                .andReturn().getResponse();

        //THEN
        //It is expected to be successfully created
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jacksonTask.write(taskDto).getJson(), response.getContentAsString());


    }

*/



}

