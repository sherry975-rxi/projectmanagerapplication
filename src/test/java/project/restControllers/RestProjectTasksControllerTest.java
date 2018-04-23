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
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.model.Project;
import static org.junit.Assert.assertEquals;
import project.model.Task;
import project.model.User;
import project.model.taskstateinterface.OnGoing;
import project.model.taskstateinterface.Planned;
import project.restcontroller.RestProjectTasksController;

import project.services.TaskService;



import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestProjectTasksControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private RestProjectTasksController victim;

    private MockMvc mockMvc;
    private User uDaniel;
    private Project project;
    private Task task;
    private Task task2;
    List<Task> taskList;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(victim).build();
        uDaniel = new User("Daniel", "daniel@gmail.com", "01", "Arquitecto", "967387654");
        project = new Project("Project A", "Project de teste", uDaniel);
        task = new Task("Task", project);
        task.setTaskState(new OnGoing());
        task2 = new Task("Task2", project);
        task2.setTaskState(new Planned());

    }

    @After
    public void tearDown() {
        taskService = null;

        mockMvc = null;
        taskList = null;
    }

    /**
     * GIVEN: a certain task in a state that allows its deletion
     * WHEN: we perform a delete request to url /projects/<projectId>/tasks/<taskId>
     * THEN: we receive a valid message with 202 Accepted and the task list has to display one less task
     * @throws Exception
     */
    @Test
    public void shouldDeleteTask() throws Exception {

        //GIVEN: a certain task
        String taskId = "T0.1";
        when(taskService.getTaskByTaskID(taskId)).thenReturn(task);

        //WHEN: we perform a delete request to url /projects/<projectId>/tasks/<taskId>
        when(taskService.deleteTask(task)).thenReturn(true);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/projects/1/tasks/" + taskId).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //THEN: we receive a valid message with 202 Accepted and the task list has to display one less task
        assertEquals(HttpStatus.ACCEPTED.value(), response.getStatus());

    }
    
}
