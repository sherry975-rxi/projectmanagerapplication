package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.dto.TaskDTO;
import project.model.*;
import project.model.taskstateinterface.OnGoing;
import project.restcontroller.RestProjectTasksController;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class RestProjectTasksControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @Mock
    private Task taskTest;

    @Mock
    private HttpServletRequest req;



    @InjectMocks
    private RestProjectTasksController victim;

    private JacksonTester<List<Task>> jacksonProjectTeamList;
    private JacksonTester<Task> jacksonTask;
    private JacksonTester<TaskDTO> jacksonTaskDto;
    private JacksonTester<User> jacksonUser;
    private JacksonTester<TaskCollaborator> jacksonTaskCollab;

    private MockMvc mockMvc;
    private User uDaniel;
    private User uInes;
    private Project project;
    private Task task;
    private Task task2;
    private Task task3;
    private TaskDTO taskDTO;

    private List<Task> projectTasks;
    private Calendar startDate;
    private Calendar finishDate;
    private Calendar estimatedStart;
    private Calendar estimatedDeadline;

    private List<Task> expected;
    private List<TaskDTO> projectTaskdtos;
    private ResponseEntity<List<Task>> expectedResponse;

    private ProjectCollaborator projCollab;
    private TaskCollaborator taskCollaborator;

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

        taskDTO = new TaskDTO(task);

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
        projectTaskdtos = new ArrayList<>();

        projCollab = new ProjectCollaborator(uDaniel, 20);

        taskCollaborator = task.createTaskCollaborator(projCollab);

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
        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());

    }


    /**
     * This test verifies the correct initialization of the REST use controller
     */

    @Test
    public void controllerInitializedCorrectly() {
        assertNotNull(victim);
    }


    /**
     * GIVEN a task with a certain ID and no dependencies
     * WHEN a list of tasks dependencies is requested
     * THEN the response entity must contain status OK
     *
     * AND WHEN the tested task recieves a dependency
     * THEN the response entity must contain a list of tasks
     *
     * @throws Exception
     */
    @Test
    public void testGetTaskDependencies() throws Exception  {

        //GIVEN

        int projectId = 123;
        project.setProjectId(projectId);

        task3 = new Task("Task3", project);
        task3.setTaskID("50");
        task3.setEstimatedTaskStartDate(estimatedStart);
        task3.setTaskDeadline(estimatedDeadline);
        task3.setTaskBudget(2000);

        task.setProject(project);

        projectTasks = new ArrayList<>();



        when(projectService.getProjectById(projectId)).thenReturn(project);

        when(taskService.getTaskByTaskID(anyString())).thenReturn(task3);

        //confirmation that task3 does not have assigned collaborators

        assertTrue(task3.getTaskDependency().isEmpty());

        //WHEN

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/123/tasks/1/dependencies")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN

        assertEquals(HttpStatus.OK.value(),response.getStatus());


        //AND WHEN adding a dependency
        task.setEstimatedTaskStartDate(Calendar.getInstance());
        task.setTaskID("30");

        assertTrue(task3.createTaskDependence(task, 2));

        List<Task> expected = new ArrayList<>();
        expected.add(task);

        // THEN
        ResponseEntity<List<Task>> actualResponse = victim.getTaskDependencies("1", projectId);

        ResponseEntity<List<Task>> expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);
        assertEquals(expectedResponse,actualResponse);

    }


    /**
     * GIVEN three tasks belonging to a single project (task3 with NO dependencies)
     * WHEN requesting the list of possible dependencies
     * THEN the response entity must contain status OK
     *
     * AND WHEN the tested task recieves a dependency on "task"
     * THEN the response entity must contain a list of tasks, containing only "task2"
     *
     * @throws Exception
     */
    @Test
    public void testGetPossibleTaskDependencies() throws Exception  {

        //GIVEN

        task.setTaskID("30");
        task2.setTaskID("20");

        int projectId = 123;
        project.setProjectId(projectId);


        task3 = new Task("Task3", project);
        task3.setTaskID("50");
        task3.setEstimatedTaskStartDate(estimatedStart);
        task3.setTaskDeadline(estimatedDeadline);
        task3.setTaskBudget(2000);

        projectTasks = new ArrayList<>();


        projectTasks.add(task);
        projectTasks.add(task2);
        projectTasks.add(task3);

        when(projectService.getProjectById(projectId)).thenReturn(project);

        when(taskService.getTaskByTaskID(anyString())).thenReturn(task3);

        when(taskService.getProjectTasks(project)).thenReturn(projectTasks);

        //confirmation that task3 does not have assigned collaborators

        assertTrue(task3.getTaskDependency().isEmpty());

        //WHEN

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/123/tasks/50/possibleDependencies")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN

        assertEquals(HttpStatus.OK.value(),response.getStatus());


        //AND WHEN adding a dependency
        task.setEstimatedTaskStartDate(Calendar.getInstance());

        assertTrue(task3.createTaskDependence(task, 2));

        List<Task> expected = new ArrayList<>();
        expected.add(task2);

        // THEN
        ResponseEntity<List<Task>> actualResponse = victim.getPossibleTaskDependencies("50", projectId);

        ResponseEntity<List<Task>> expectedResponse = new ResponseEntity<>(expected, HttpStatus.OK);
        assertEquals(expectedResponse,actualResponse);

    }



    /**
     * GIVEN two tasks with a certain ID and no dependencies
     * WHEN making task3 dependent on task
     * THEN the response entity must contain status OK, and child "task3" must contain task as a dependency
     *  and its start date must be changed to after parent "task" deadline
     *
     * AND WHEN removing that dependency from task
     * THEN the response entity must contain ok, and child "task3" must have no dependencies
     *
     * @throws Exception
     */
    @Test
    public void testCreateAndRemoveTaskDependency() throws Exception  {

        //GIVEN
        int projectId = 123;
        project.setProjectId(projectId);

        task3 = new Task("Task3", project);
        task3.setTaskID("50");
        task3.setEstimatedTaskStartDate(estimatedStart);
        task3.setTaskDeadline(estimatedDeadline);
        task3.setTaskBudget(2000);
        projectTasks = new ArrayList<>();


        task.setProject(project);
        task.setTaskDeadline(Calendar.getInstance());
        task.setTaskID("30");


        when(projectService.getProjectById(projectId)).thenReturn(project);

        when(taskService.getTaskByTaskID("50")).thenReturn(task3);
        when(taskService.getTaskByTaskID("30")).thenReturn(task);

        //confirmation that task3 does not have assigned collaborators

        assertTrue(task3.getTaskDependency().isEmpty());
        assertFalse(task3.getEstimatedTaskStartDate().after(task.getTaskDeadline()));

        //WHEN

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/projects/123/tasks/50/createDependency/30/2")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN

        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertFalse(task3.getTaskDependency().isEmpty());
        assertTrue(task3.getTaskDependency().contains(task));
        assertTrue(task3.getEstimatedTaskStartDate().after(task.getTaskDeadline()));


        //AND WHEN

        response = mockMvc.perform(MockMvcRequestBuilders.put("/projects/123/tasks/50/removeDependency/30")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();


        // THEN
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        assertTrue(task3.getTaskDependency().isEmpty());


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

        TaskDTO taskDTO = new TaskDTO(task);
        TaskDTO taskDTO1 = new TaskDTO(task2);

        projectTaskdtos.add(taskDTO);
        projectTaskdtos.add(taskDTO1);

        //GIVEN: a project id
        int projectId = 01;
        when(projectService.getProjectById(projectId)).thenReturn(project);

        //WHEN: we perform a delete request to url /projects/<projectId>/tasks/<taskId>/finished
        when(taskService.getProjectFinishedTasksDecOrder(projectId)).thenReturn(projectTaskdtos);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/1/tasks/finished").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN: we receive a valid message with a 200 Ok and a list of the project finished tasks
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskService, times(1)).getProjectFinishedTasksDecOrder(projectId);
    }



    /**
     * GIVEN a project id
     * WHEN we perform a get request to url /projects/<projectId>/tasks/unfinished
     * THEN we receive a valid message with a 200 Ok and a list of the project unfinished tasks
     */

    @Test
    public void shouldReturnUnfinishedTasks() throws Exception{

        task.setStartDateAndState(startDate);
        task2.setStartDateAndState(startDate);

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
     * GIVEN a task id
     * WHEN we perform a get request to url /projects/<projectId>/tasks/<taskId>
     * THEN we receive a valid message with a 200 Ok and a list of the project unfinished tasks
     * @throws Exception
     */
    @Test
    public void shouldReturnTask() throws Exception {

        //GIVEN: a project id
        String  taskId = "01";

        project.setProjectId(1);
        task.setProject(project);

        //WHEN
        when(taskService.getTaskByTaskID(taskId)).thenReturn(task);
        when(taskService.getTaskDtoByTaskId(taskId)).thenReturn(taskDTO);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/1/tasks/" + taskId).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskService, times(1)).getTaskDtoByTaskId(any(String.class));

        //AND WHEN an ongoing task is marked as finished
        task.setStartDate(Calendar.getInstance());
        task.setTaskState(new OnGoing());
        task.setCurrentState(StateEnum.ONGOING);
        MockHttpServletResponse newResponse = mockMvc.perform(MockMvcRequestBuilders.patch("/projects/1/tasks/" + taskId).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN Http status should be 200 and returned Task should be finished
        assertEquals(HttpStatus.OK.value(), newResponse.getStatus());
        assertTrue(task.isTaskFinished());
    }



    /*
        Fix test due to failure because TaskStateInterface has a @JsonIgnore annotation and causes a nullPointer Exception
     */


    @Test
    public void createTask() throws Exception{

        Integer projID = 11;


        //GIVEN
        //A set of parameters to create a project
        String taskDescription = "Task Description";
        when(projectService.getProjectById(projID)).thenReturn(project);

        //WHEN
        //One creates a task
        Task taskDto = new Task(taskDescription, project);
        when(taskService.createTask(any(String.class),any(Project.class))).thenReturn(taskDto);



//        MockHttpServletResponse response = mockMvc.perform(post("/projects/" + projID + "/tasks/").contentType(MediaType.APPLICATION_JSON)
//                .content(jacksonTask.write(taskDto).getJson()))
//                .andReturn().getResponse();

        ResponseEntity <Task> response = victim.createTask(taskDto, projID);



         //THEN
        //It is expected to be successfully created
        assertEquals(HttpStatus.OK, response.getStatusCode());


        //WHEN we create taskDTO with values for the estimatedTaskEffort, TaskBudgetm estimatedTaskStartDate and taskDeadline
        Calendar startDate = Calendar.getInstance();
        Calendar deadline = Calendar.getInstance();
        Task taskDto2 = new Task(taskDescription, project);
        taskDto2.setEstimatedTaskEffort(0);
        taskDto2.setTaskBudget(0);
        taskDto2.setEstimatedTaskStartDate(startDate);
        taskDto2.setTaskDeadline(deadline);
        when(taskService.createTask(any(String.class),any(Project.class))).thenReturn(taskDto2);


        MockHttpServletResponse response2 = mockMvc.perform(post("/projects/" + projID + "/tasks/").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTask.write(taskDto2).getJson()))
                .andReturn().getResponse();


        //THEN
        //It is expected to be successfully created
        assertEquals(HttpStatus.OK.value(), response2.getStatus());


    }



    /**
     * GIVEN a project id
     * WHEN we perform a get request to url /projects/<projectId>/tasks/all
     * THEN we receive a valid message with a 200 Ok and a list of all tasks from project
     * @throws Exception
     */
    @Test
    public void shouldReturnAllTasks() throws Exception {


        projectTasks.add(task);
        projectTasks.add(task2);

        TaskDTO taskDTO = new TaskDTO(task);
        TaskDTO taskDTO1 = new TaskDTO(task2);
        projectTaskdtos.add(taskDTO);
        projectTaskdtos.add(taskDTO1);

        // GIVEN
        int projectId = 01;
        when(projectService.getProjectById(projectId)).thenReturn(project);

        // WHEN
        when(taskService.getProjectTasks(project)).thenReturn(projectTasks);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/1/tasks/all").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskService, times(1)).getProjectTasks(project);
    }


    /**
     * GIVEN a task id and project id
     * WHEN we perform a get request to url /projects/<projectId>/tasks/<taskId>/activeTeam
     * THEN we receive a valid message with a 200 Ok and a list of active task team
     * @throws Exception
     */
    @Test
    public void shouldReturnActiveTaskTeam() throws Exception {

        // GIVEN
        int projectId = 01;
        String  taskid = "01";

        // WHEN
        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(taskService.getTaskByTaskID(taskid)).thenReturn(task);

        List<TaskCollaborator> team = task.getTaskTeam();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + projectId +"/tasks/" + taskid + "/activeTeam").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskService, times(1)).getTaskByTaskID(any(String.class));


    }

    /**
     * GIVEN
     * a task id,
     * project id
     * and an available project collaborator not in the task
     *
     * WHEN
     * we perform a get request to url /projects/<projectId>/tasks/<taskId>/collabsAvailableForTask
     *
     * THEN
     * we receive a valid message with a 200 Ok and a list
     * of Project collaborators that are not assigned to a certain task
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnProjectTeamNotAddedToTask() throws Exception {

        // GIVEN
        int projectId = 01;
        String taskid = "01";

        ProjectCollaborator pcInes = new ProjectCollaborator(uInes, 20);
        List<ProjectCollaborator> team = new ArrayList<>();
        team.add(pcInes);

        // WHEN
        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(taskService.getTaskByTaskID(taskid)).thenReturn(task);
        when(projectService.getActiveProjectTeam(project)).thenReturn(team);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + projectId +"/tasks/" + taskid + "/collabsAvailableForTask").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskService, times(1)).getTaskByTaskID(any(String.class));
        verify(projectService, times(1)).getActiveProjectTeam(project);
        verify(projectService, times(1)).getProjectById(any(Integer.class));


    }

    /**
     * GIVEN a project id
     * WHEN we perform a get request to url /projects/<projectId>/tasks/notstarted
     * THEN we receive a valid message with a 200 Ok and a list of not started tasks from project
     * @throws Exception
     */
    @Test
    public void shouldReturnNotStartedTasks () throws Exception {

        // expected list of not started tasks
        projectTasks.add(task);
        projectTasks.add(task2);


        // GIVEN
        int projectId = 01;
        when(projectService.getProjectById(projectId)).thenReturn(project);

        // WHEN
        when(taskService.getProjectUnstartedTasks(project)).thenReturn(projectTasks);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/"+ projectId + "/tasks/notstarted").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskService, times(1)).getProjectUnstartedTasks(project);
    }

    /**
     * GIVEN a project id
     * WHEN we perform a get request to url /projects/<projectId>/tasks/expired
     * THEN we receive a valid message with a 200 Ok and a list of expired tasks from project
     * @throws Exception
     */
    @Test
    public void shouldReturnExpiredTasks () throws Exception {

        // mock expected list of expired tasks
        projectTasks.add(task);
        projectTasks.add(task2);


        // GIVEN
        int projectId = 01;
        when(projectService.getProjectById(projectId)).thenReturn(project);

        // WHEN
        when(taskService.getProjectExpiredTasks(project)).thenReturn(projectTasks);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/"+ projectId + "/tasks/expired").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskService, times(1)).getProjectExpiredTasks(project);
    }

    /**
     * GIVEN a project  and task id
     * WHEN we perform a get request to url /projects/<projectId>/tasks/<taskId>/collabsAvailableForTask
     * THEN we receive a valid message with a 200 Ok and task updated with collab added
     * @throws Exception
     */
    @Test
    public void shouldAddCollabToTask () throws Exception {



        ProjectCollaborator pcInes = new ProjectCollaborator(uInes, 20);
        List<ProjectCollaborator> team = new ArrayList<>();
        team.add(pcInes);
        projectService.addProjectCollaborator(pcInes);

        pcInes.setProject(project);


        // GIVEN
        int projectId = 01;
        String taskid = "01";

        when(userService.getUserByEmail(any(String.class))).thenReturn(uInes);

        when(taskService.getTaskByTaskID(taskid)).thenReturn(task);

        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(projectService.isUserActiveInProject(any(User.class), any(Project.class))).thenReturn(true);
        when(projectService.findActiveProjectCollaborator(any(User.class), any(Project.class))).thenReturn(pcInes);


        // WHEN
        when(taskService.getProjectUnstartedTasks(project)).thenReturn(projectTasks);
        when(taskService.saveTask(any(Task.class))).thenReturn(task);

        MockHttpServletResponse response = mockMvc.perform(post("/projects/" + projectId + "/tasks/"+ taskid + "/addCollab").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonUser.write(uInes).getJson()))
                .andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    /**
     * GIVEN a project  and task id
     * WHEN we perform a get request to url /projects/<projectId>/tasks/<taskId>/removeCollab
     * THEN we receive a valid message with a 200 Ok and task updated with collab removed
     * AND WHEN we perform the same request, with the task collaborator now removed
     * THEN we recieve a message with METHOD NOT ALLOWED
     * @throws Exception
     */
    @Test
    public void shouldRemoveCollabFromTask () throws Exception {



        ProjectCollaborator pcInes = new ProjectCollaborator(uInes, 20);
        pcInes.setProject(project);
        List<ProjectCollaborator> team = new ArrayList<>();
        team.add(pcInes);
        projectService.addProjectCollaborator(pcInes);

        TaskCollaborator tcInes = new TaskCollaborator(pcInes);
        tcInes.setTask(task);
        task.addTaskCollaboratorToTask(tcInes);

        // GIVEN
        int projectId = 01;
        String taskid = "01";

        when(taskService.getTaskByTaskID(taskid)).thenReturn(task);
        when(taskService.saveTask(any(Task.class))).thenReturn(task);

        // WHEN
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.patch("/projects/" + projectId + "/tasks/"+ taskid + "/removeCollab").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTaskCollab.write(tcInes).getJson()))
                .andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        //AND WHEN
        response = mockMvc.perform(MockMvcRequestBuilders.patch("/projects/" + projectId + "/tasks/"+ taskid + "/removeCollab").contentType(MediaType.APPLICATION_JSON)
                .content(jacksonTaskCollab.write(tcInes).getJson()))
                .andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), response.getStatus());


    }

    /**
     * GIVEN
     * a task id,
     * project id
     * and none available project collaborator to add to the task
     *
     * WHEN
     * we perform a get request to url /projects/<projectId>/tasks/<taskId>/collabsAvailableForTask
     *
     * THEN
     * we receive a message with a 417 EXPECTATION_FAILED
     *
     * @throws Exception
     */
    @Test
    public void shouldNotReturnProjectTeamNotAddedToTask() throws Exception {

        // GIVEN
        int projectId = 01;
        String taskid = "01";

        List<ProjectCollaborator> team = new ArrayList<>();

        //empty team
        //team.add(pcInes);

        // WHEN
        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(taskService.getTaskByTaskID(taskid)).thenReturn(task);
        when(projectService.getActiveProjectTeam(project)).thenReturn(team);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + projectId +"/tasks/" + taskid + "/collabsAvailableForTask").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.EXPECTATION_FAILED.value(), response.getStatus());
        verify(taskService, times(1)).getTaskByTaskID(any(String.class));
        verify(projectService, times(1)).getActiveProjectTeam(project);
        verify(projectService, times(1)).getProjectById(any(Integer.class));
    }

    /**
     * GIVEN
     * a task id,
     * project id
     * and the available project collaborator is the project manager
     *
     * WHEN
     * we perform a get request to url /projects/<projectId>/tasks/<taskId>/collabsAvailableForTask
     *
     * THEN
     * we receive a message with a 417 EXPECTATION_FAILED
     *
     * @throws Exception
     */
    @Test
    public void shouldNotReturnProjectTeamNotAddedToTask2() throws Exception {

        // GIVEN
        int projectId = 01;
        String taskid = "01";

        ProjectCollaborator pcDaniel = new ProjectCollaborator(uDaniel, 20);
        List<ProjectCollaborator> team = new ArrayList<>();
        team.add(pcDaniel);

        // WHEN
        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(taskService.getTaskByTaskID(taskid)).thenReturn(task);
        when(projectService.getActiveProjectTeam(project)).thenReturn(team);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + projectId +"/tasks/" + taskid + "/collabsAvailableForTask").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.EXPECTATION_FAILED.value(), response.getStatus());
        verify(taskService, times(1)).getTaskByTaskID(any(String.class));
        verify(projectService, times(1)).getActiveProjectTeam(project);
        verify(projectService, times(1)).getProjectById(any(Integer.class));


    }

    /**
     * GIVEN
     * project id
     * and the available project collaborator is the project manager
     *
     * WHEN
     * we perform a get request to url /projects/<projectId>/tasks/collabsAvailableForTask
     *
     * THEN
     * we receive a message with a 200 OK
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnProjectTeamNotAddedToAnyTask() throws Exception {

        // GIVEN
        int projectId = 01;

        ProjectCollaborator pcDaniel = new ProjectCollaborator(uDaniel, 20);
        List<ProjectCollaborator> team = new ArrayList<>();
        team.add(pcDaniel);

        // WHEN
        when(projectService.getProjectById(projectId)).thenReturn(project);
        when(taskService.isCollaboratorActiveOnAnyTask(pcDaniel)).thenReturn(true);
        when(projectService.getActiveProjectTeam(project)).thenReturn(team);
 

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/projects/" + projectId +"/tasks/collabsAvailableForTask").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskService, times(1)).isCollaboratorActiveOnAnyTask(pcDaniel);
        verify(projectService, times(1)).getActiveProjectTeam(project);
        verify(projectService, times(1)).getProjectById(any(Integer.class));


    }



    @Test
    public void isCollabActiveInTask() throws Exception{

        // GIVEN
        int projectId = 02;
        String taskId = "taskid";
        String email = "email";


        //WHEN
        when(taskService.getTaskByTaskID(any())).thenReturn(taskTest);
        when(taskTest.getActiveTaskCollaboratorByEmail(email)).thenReturn(taskCollaborator);


        ResponseEntity<String> response = victim.isTaskCollaboratorActiveInTask(projectId,taskId,email);


        //THEN

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());



        //WHEN
        //The method getActiveTaskCollabortor returns null
        when(taskTest.getActiveTaskCollaboratorByEmail(email)).thenReturn(null);

        ResponseEntity<String> responseNull = victim.isTaskCollaboratorActiveInTask(projectId,taskId,email);

        //THEN
        //The responseEntity will return a value 404 Not Found
        assertEquals(HttpStatus.NOT_FOUND.value(), responseNull.getStatusCode().value());

    }




}

