package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.protocol.HTTP;
import org.apache.xerces.util.HTTPInputSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.model.Project;
import project.model.Task;
import project.model.User;
import project.restcontroller.RestUserProjectsController;
import project.restcontroller.RestUserTasksController;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    private Project projectMockk;

    @InjectMocks
    private RestUserTasksController victim;

    private int userId;
    private String taskId;
    private MockMvc mvc;
    private JacksonTester<List<Task>> jacksonTasks;

    @Before
    public void setup(){
        userId = 1;
        taskId = "2";
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(victim).build();
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
    public void shouldReturnUserPendingTaskList () {
        //GIVEN a user id
        List<Task> pendingTaskList = new ArrayList<>();
        pendingTaskList.add(taskMock);
        when(userServiceMock.getUserByID(any(int.class))).thenReturn(userMock);
        when(taskServiceMock.getStartedNotFinishedUserTaskList(userMock)).thenReturn(pendingTaskList);

        //WHEN we call getPendingTasks with a valid userId
        List<Task> response = victim.getPendingTasks(userId);

        //THEN we receive a valid list of tasks
        assertEquals(pendingTaskList, response);
        verify(taskServiceMock, times(1)).getStartedNotFinishedUserTaskList(userMock);
        verify(userServiceMock, times(1)).getUserByID(userId);
    }

    @Test
    public void shouldReturnUserTask () {
        //GIVEN a taskId
        when(taskServiceMock.getTaskByID(any(Long.class))).thenReturn(taskMock);

        //WHEN we call getTaskById with a valid taskId
        Task response = victim.getTaskById(taskId);

        //THEN we receive a valid task
        assertEquals(taskMock, response);
        verify(taskServiceMock, times(1)).getTaskByID(Long.parseLong(taskId));
    }



}
