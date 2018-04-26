package project.restControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private MockMvc mvc;
    private JacksonTester<Task> jacksonProjectList;

    @Before
    public void setup(){
        userId = 1;
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(victim).build();
    }

    /**
     * GIVEN a user id
     * WHEN we perform a get request to url /users/<usersId>/tasks/finished
     * THEN we receive a valid message and the list of tasks
     * @throws Exception
     */
    @Test
    public void shouldReturnUserFinishedTasksList () throws Exception {
        //GIVEN a user id
        List<Task> finishedTaskList = new ArrayList<>();
        finishedTaskList.add(taskMock);
        when(userServiceMock.getUserByID(any(int.class))).thenReturn(userMock);
        when(taskMock.getProject()).thenReturn(projectMockk);
        when(taskServiceMock.getAllFinishedUserTasksInDecreasingOrder(userMock)).thenReturn(finishedTaskList);


        //WHEN we call getUserFinishedTasks with a valid userId
        List<Task> response = victim.getUserFinishedTasks(userId);

        //THEN we receive a valid message and the list of tasks
        assertEquals(finishedTaskList, response);
        verify(taskServiceMock, times(1)).getAllFinishedUserTasksInDecreasingOrder(userMock);
        verify(userServiceMock, times(1)).getUserByID(userId);

    }

   /* @Test
    public void shouldReturnUserPendingTasksList () throws Exception {
        //GIVEN a user id
        List<Task> pendingTaskList = new ArrayList<>();
        pendingTaskList.add(taskMock);
        when(userServiceMock.getUserByID(userId)).thenReturn(userMock);
        when(taskServiceMock.getUserPendingTasksList(userMock)).thenReturn(pendingTaskList);
        when(taskServiceMock.sortTaskListByDeadline(anyListOf(Task.class))).thenReturn(pendingTaskList);

        //WHEN we perform a get request to url /users/<usersId>/pendingtasks
        MockHttpServletResponse response = mvc.perform(get("/users/" + userId + "/tasks/pending")
                .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN we receive a valid message and the list of tasks
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jacksonProjectList.write(pendingTaskList).getJson(), response.getContentAsString());
        verify(taskServiceMock, times(1)).getAllFinishedUserTasksInDecreasingOrder(userMock);

    }*/

}
