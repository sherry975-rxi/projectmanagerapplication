package project.restcontrollers;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import project.restcontroller.US372GetStartedNotFinishedProjectTasksRestController;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.exceptions.ObjectNotFoundException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(MockitoJUnitRunner.class)
public class US372GetStartedNotFinishedProjectTasksRestTest {

    @Mock
    TaskService taskService;

    @Mock
    ProjectService projectService;


    private MockMvc mockMvc;
    private Project project;
    private User pm;
    private User user;
    private ProjectCollaborator projectCollaborator;
    private Task task1;
    private Task task2;
    private Integer projectId = 1;
    private List<Task> taskList = new ArrayList<>();
    @InjectMocks
    private US372GetStartedNotFinishedProjectTasksRestController controller;

    //This method will be initialized by the initFields below
    private JacksonTester<Project> projectJack;
    private JacksonTester<Task> taskJack;

    @Before
    public void setup() {

        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        pm = new User("Joao", "jl@gmail.com", "01", "Project Manager", "555555555");

        this.project = new Project("Gestao", "controlo", pm);

        projectId = 1;

        this.projectCollaborator = new ProjectCollaborator (user,  2);
        projectCollaborator.setProject(project);

        //Created task1
        this.task1 = new Task("Desenvolver Listas", project);
        task1.setProject(project);
        task1.setId(1L);
        task1.setTaskID("1");

        //Planned task1
        task1.setEstimatedTaskStartDate(Calendar.getInstance());
        task1.setTaskDeadline(Calendar.getInstance());
        task1.addProjectCollaboratorToTask(projectCollaborator);

        //Ready task1
        task1.setEstimatedTaskEffort(3);
        task1.setTaskBudget(100);

        //Ongoing (initiated) task1
        task1.setStartDate(Calendar.getInstance());

        //Created Task2

        this.task2 = new Task("Preencher Listas", project);
        task2.setProject(project);



        this.taskList.clear();

    }

    @Test
    public void canRetrieveByIdWhenExistsTest() throws Exception {

        //Given
        given(projectService.getProjectById(projectId)).willReturn(project);
        taskList.add(task1);
        given(taskService.getProjectUnFinishedTasks(project)).willReturn(taskList);


        //When
        MockHttpServletResponse response = mockMvc.perform(get("/projects/" + projectId + "/tasks/unfinished").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //Then
        assertEquals(response.getStatus(),HttpStatus.OK.value());
        assertEquals(response.getContentAsString(), "[" + taskJack.write(task1).getJson() + "]");
    }

    @Test
    public void canRetrieveByIdWhenDoesNotExistTest() throws Exception {

        //Given
        int projectIdDoesNotExist = 2;
        given(projectService.getProjectById(projectIdDoesNotExist)).willThrow(new ObjectNotFoundException ("Project not found!"));
        taskList.add(task1);
        given(taskService.getProjectUnFinishedTasks(project)).willReturn(taskList);

        //When
        MockHttpServletResponse response = mockMvc.perform(get("/projects/" + projectId + "/tasks/unfinished").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        //Then
        //assertEquals(response.getStatus(),HttpStatus.NOT_FOUND.value());
        assertEquals(response.getContentAsString(), "[" + new String() + "]");



    }
    }


