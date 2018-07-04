package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.model.*;
import project.restcontroller.RestUserTasksController;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class RestUserTasksControllerTest {
    @Mock
    private TaskService taskServiceMock;

    @Mock
    private UserService userServiceMock;

    @Mock
    private User userMock;

    @Mock
    private Task taskMock;

    @Mock
    private Project projectMock;

    @InjectMocks
    private RestUserTasksController victim;

    private int userId;
    private String taskId;
    private MockMvc mvc;
    private JacksonTester<List<Task>> jacksonTasks;
    private Task taskCreated;
    private User userRui;
    private ProjectCollaborator projectCollaborator;
    private Project project;

    @Before
    public void setup(){
        userId = 1;
        taskId = "2";
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(victim).build();

        // Create and add tasks to Task repository
        taskCreated = taskServiceMock.createTask("Remove code smells", projectMock);

        userRui = new User("Rui", "rui@gmail.com", "02", "Simples colaborador", "638192945");
        this.projectCollaborator = new ProjectCollaborator(userRui, 2);
        projectCollaborator.setProject(projectMock);

    }

    @After
    public void tearDown(){

        project = null;
        userRui = null;
        projectCollaborator = null;
        taskId = null;
        mvc = null;
    }

    /**
     * GIVEN a user id
     * WHEN we perform a get request to url /users/<usersId>/tasks/finished
     * THEN we receive a valid message and the list of tasks
     */
    @Test
    public void shouldReturnUserFinishedTaskList () {
        //GIVEN a user id
        List<Task> finishedTaskList = new ArrayList<>();
        finishedTaskList.add(taskMock);
        when(userServiceMock.getUserByID(any(int.class))).thenReturn(userMock);
        when(taskServiceMock.getAllFinishedUserTasksInDecreasingOrder(userMock)).thenReturn(finishedTaskList);

        //WHEN we call getUserFinishedTasks with a valid userId
        List<Task> response = victim.getUserFinishedTasks(userId);

        //THEN we receive a valid list of tasks
        assertEquals(finishedTaskList, response);
        verify(taskServiceMock, times(1)).getAllFinishedUserTasksInDecreasingOrder(userMock);
        verify(userServiceMock, times(1)).getUserByID(userId);
    }

    @Test
    public void shouldReturnUserPendingTaskList () throws Exception {
       /* //GIVEN a user id
        List<Task> pendingTaskList = new ArrayList<>();
        pendingTaskList.add(taskMock);
        when(userServiceMock.getUserByID(any(int.class))).thenReturn(userMock);
        when(taskServiceMock.getStartedNotFinishedUserTaskList(userMock)).thenReturn(pendingTaskList);
        when(taskMock.getActiveTaskCollaboratorByEmail(userMock.getEmail())).thenReturn(userMock);


        //WHEN we call getPendingTasks with a valid userId
        List<Task> response = victim.getPendingTasks(userId);

        //THEN we receive a valid list of tasks
        assertEquals(pendingTaskList, response);
        verify(taskServiceMock, times(1)).getStartedNotFinishedUserTaskList(userMock);
        verify(userServiceMock, times(1)).getUserByID(userId);*/

        project = new Project("test", "test", userRui);


        List<Task> pendingTaskList = new ArrayList<>();

        Task task1 = new Task("taskTest1", project);
        Task task2 = new Task("taskTest2", project);
        Task task3 = new Task("taskTest3", project);

        pendingTaskList.add(task1);
        pendingTaskList.add(task2);
        pendingTaskList.add(task3);

        Mockito.when(userServiceMock.getUserByID(anyInt())).thenReturn(userRui);
        Mockito.when(taskServiceMock.getStartedNotFinishedUserTaskList(anyObject())).thenReturn(pendingTaskList);

        //WHEN
        //when you ask for all the tasks of this particular user
        MockHttpServletResponse response = mvc.perform(get("/users/" + userRui.getUserID() + "/tasks/pending")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        victim.getPendingTasks(userId);

        //THEN
        //then a list of all your tasks is returned
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        //verify(taskServiceMock, times(2)).getStartedNotFinishedUserTaskList(userRui);




    }

    /**
     * GIVEN
     * given a certain user
     *
     * WHEN
     * when you ask for all the tasks of this particular user
     *
     * THEN
     * then a list of all your tasks is returned
     */
    @Test
    public void shouldReturnUserTasks() throws Exception {

        //GIVEN
        //given a certain user
        project = new Project("test", "test", userRui);


        List<Task> allTasks = new ArrayList<>();

        Task task1 = new Task("taskTest1", project);
        Task task2 = new Task("taskTest2", project);
        Task task3 = new Task("taskTest3", project);

        allTasks.add(task1);
        allTasks.add(task2);
        allTasks.add(task3);

        Mockito.when(userServiceMock.getUserByID(anyInt())).thenReturn(userRui);
        Mockito.when(taskServiceMock.getUserTasks(anyObject())).thenReturn(allTasks);

        //WHEN
        //when you ask for all the tasks of this particular user
        MockHttpServletResponse response = mvc.perform(get("/users/" + userRui.getUserID() + "/tasks/")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        victim.getAllTasks(userId);

        //THEN
        //then a list of all your tasks is returned
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskServiceMock, times(2)).getUserTasks(userRui);
    }

    /**
     *GIVEN
     *given a certain user
     *
     * WHEN
     * when you are asked to return the tasks of a user that is not present in the database
     *
     * THEN
     * then an error message will be sent informing you that the user was not found
     *
     * @throws Exception
     */
    @Test
    public void shouldntReturnUserTasks() throws Exception {

        //GIVEN
        //given a certain user
        Mockito.when(userServiceMock.getUserByID(anyInt())).thenReturn(null);

        //WHEN
        //when you are asked to return the tasks of a user that is not present in the database
        MockHttpServletResponse response = mvc.perform(get("/users/" + 1 + "/tasks/")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        victim.getAllTasks(1);

        //THEN
        //then an error message will be sent informing you that the user was not found
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        verify(taskServiceMock, times(0)).getUserTasks(null);

    }

    /**
     * GIVEN
     * given a certain user
     *
     * WHEN
     * when asking the time spent by this particular user on the tasks finished in the last month
     *
     * THEN
     * then the sum of the hours spent on this task is returned
     *
     */
    @Test
    public void shouldReturnTotalTimeSpentOnTasksCompletedLastMonth() throws Exception {

        //GIVEN
        //given a certain user
        project = new Project("test", "test", userRui);

        List<Task> allTasks = new ArrayList<>();

        Task task1 = new Task("taskTest1", project);
        Task task2 = new Task("taskTest2", project);
        Task task3 = new Task("taskTest3", project);

        allTasks.add(task1);
        allTasks.add(task2);
        allTasks.add(task3);

        Mockito.when(userServiceMock.getUserByID(any(int.class))).thenReturn(userRui);
        Mockito.when(taskServiceMock.getUserTasks(userRui)).thenReturn(allTasks);


        //WHEN
        //when asking the time spent by this particular user on the tasks finished in the last month
        victim.getTotalTimeSpentOnTasksCompletedLastMonth(userRui.getUserID());
        MockHttpServletResponse response = mvc.perform(get("/users/" + userRui.getUserID() + "/tasks/totaltimespent/completed/lastmonth")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        //then the sum of the hours spent on this task is returned
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(taskServiceMock, times(2)).getTotalTimeOfFinishedTasksFromUserLastMonth(userRui);
    }

    /**
     * GIVEN
     * given a user that does not exist in the database
     *
     * WHEN
     * when asking the time spent by this particular user on the tasks finished in the last month
     *
     * THEN
     * then an error message will be sent informing you that the user was not found
     *
     * @throws Exception
     */
    @Test
    public void shouldntReturnTotalTimeSpentOnTasksCompletedLastMonth() throws Exception {

        //GIVEN
        //given a user that does not exist in the database
        Mockito.when(userServiceMock.getUserByID(any(int.class))).thenReturn(null);

        //WHEN
        //when asking the time spent by this particular user on the tasks finished in the last month
        victim.getTotalTimeSpentOnTasksCompletedLastMonth(1);
        MockHttpServletResponse response = mvc.perform(get("/users/" + 1 + "/tasks/totaltimespent/completed/lastmonth")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN
        //then an error message will be sent informing you that the user was not found
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        verify(taskServiceMock, times(0)).getTotalTimeOfFinishedTasksFromUserLastMonth(null);
    }



    @Test
    public void testGetLastMonthFinishedTasks(){

        //GIVEN
        //given a user that does not exist in the database
        List<Task> userFinishedTaskList = new ArrayList<>();
        userFinishedTaskList.add(taskCreated);

        //WHEN
        //We get the finished User tasks from Last Month, the method will return the Dummy list created above
        Mockito.when(userServiceMock.getUserByID(anyInt())).thenReturn(userRui);
        Mockito.when(taskServiceMock.getFinishedUserTasksFromLastMonthInDecreasingOrder(userRui)).thenReturn(userFinishedTaskList);

        //WHEN
        //The controller gets the list of the last month user task List
        ResponseEntity<List<Task>> actualResponse = victim.getLastMonthFinishedTasks(userRui.getUserID());



        ResponseEntity<List<Task>>  expectedResponse = new ResponseEntity<>(userFinishedTaskList, HttpStatus.OK);


        //THEN
        //The expected result is that the method will return an HTTPStatus.OK

        assertEquals(actualResponse,expectedResponse);

    }
}
