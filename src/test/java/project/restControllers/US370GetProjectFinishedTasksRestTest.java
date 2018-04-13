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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.User;
import project.restcontroller.US370GetProjectFinishedTasks;
import project.services.ProjectService;
import project.services.TaskService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(MockitoJUnitRunner.class)
public class US370GetProjectFinishedTasksRestTest {

    @Mock
    private TaskService taskServiceMock;

    @Mock
    private ProjectService projectServiceMock;

    @InjectMocks
    private US370GetProjectFinishedTasks victim;

    @Mock
    private Task task1;
    @Mock
    private Task task2;

    private MockMvc mvc;
    private JacksonTester<List<Task>> jacksonTask;
    private User userDaniel;
    private User userRui;
    private Project project;
    private Integer projectId;
    private ProjectCollaborator projectCollaborator;

    private List<Task> taskList;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(victim).build();

        userDaniel = new User("Daniel", "daniel@gmail.com", "01", "ProjectManager", "987543271");
        userRui = new User("Rui", "rui@gmail.com", "02", "Simples colaborador", "638192945");
        this.project = new Project("Project", "description", userDaniel);
        projectId = 1;

        this.projectCollaborator = new ProjectCollaborator (userRui,  2);
        projectCollaborator.setProject(project);

        this.task1 = new Task("Desenvolver US1", project);
        this.task2 = new Task("Refactoring US2", project);
        task1.setProject(project);
        task2.setProject(project);
        task1.setId(1L);
        task2.setId(2L);
        task1.setTaskID("1");
        task2.setTaskID("2");

        taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
    }

    @After
    public void tearDown() {
        userDaniel = null;
        userRui = null;
        project = null;
        projectId = null;
        projectCollaborator = null;
    }

    @Test
    public void canRetrieveByIdWhenExistsTest() throws Exception{
        //given
        when(projectServiceMock.getProjectById(any(Integer.class))).thenReturn(project);
        when(taskServiceMock.getProjectFinishedTasks(any(Project.class))).thenReturn(taskList);

        //when
        MockHttpServletResponse response = mvc.perform(get("/projects/" + projectId + "/tasks/finished").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        task2.setFinishDate(null);
        task1.setFinishDate(null);

        //then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(jacksonTask.write(taskList).getJson(), response.getContentAsString());
        verify(projectServiceMock, times(1)).getProjectById(projectId);
    }
}
