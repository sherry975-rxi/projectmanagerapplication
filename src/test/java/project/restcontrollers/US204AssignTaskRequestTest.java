package project.restcontrollers;

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
import project.model.*;
import project.restControllers.US204AssignTaskRequestRestController;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;


import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
public class US204AssignTaskRequestTest {

    private MockMvc mockMvc;

    @Mock
    UserService userService;
    @Mock
    ProjectService projectService;
    @Mock
    TaskService taskService;

    @InjectMocks
    private US204AssignTaskRequestRestController controller;

    private User userPM;
    private User userTwo;
    private String userTwoEmail;
    private User userThree;
    private String userThreeEmail;
    private Project projectOne;
    private Integer projectId;
    private Task taskOne;
    private String taskIdOne;
    private Task taskTwo;
    private String taskIdTwo;
    private ProjectCollaborator projCollabTwo;
    private ProjectCollaborator projCollabThree;

    //This objects will be initialized by the initFields below
    private JacksonTester<Project> projectJack;
    private JacksonTester<Task> taskJack;
    private JacksonTester<TaskTeamRequest> requestJacksonTester;


    @Before
    public void setUp() throws Exception{

        JacksonTester.initFields(this, new ObjectMapper());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        // create userPM and save in DB
        userPM = userService.createUser("Ana", "ana@gmail.com", "01", "collaborator", "221238442", "Rua Porto",
                "4480", "Porto", "Porto", "Portugal");

        // create userTwo and save in DB
        userTwo = userService.createUser("Joao", "joao@gmail.com", "02", "collaborator", "221238447", "Rua Porto",
                "5480", "Porto", "Porto", "Portugal");
        userTwoEmail = "joao@gmail.com";

        // create user3
        userThree = userService.createUser("Rui", "rui@gmail.com", "03", "collaborator", "221378449", "Rua Porto",
                "4480", "Porto", "Porto", "Portugal");
        userThreeEmail = "rui@gmail.com";


        // create a project with userPM as manager
        projectOne = projectService.createProject("Restful", "Implement API Rest", userPM);
        projectId = 1;

        // create 2 tasks
        taskOne = taskService.createTask("Rest Controller", projectOne);
        taskIdOne = "1.1";

        taskTwo = taskService.createTask("Rest Controller Test", projectOne);
        taskIdTwo = "1.2";


        // add users to projectOne creating a project collaborator for each
        projCollabTwo = projectService.createProjectCollaborator(userTwo, projectOne, 20);
        projCollabThree = projectService.createProjectCollaborator(userThree, projectOne, 60);


    }


    @After
    public void clear() throws Exception{
        userPM = null;
        userTwo = null;
        projectOne = null;
        taskOne = null;
        taskTwo = null;
        projCollabTwo = null;
        projCollabThree = null;

    }
    @Test
    public void testSetUpInfo() {

        assertEquals(userTwo, userService.getUserByEmail(userTwoEmail));
        assertEquals(userThree, userService.getUserByEmail(userThreeEmail));
        assertEquals(projCollabTwo, projectService.findActiveProjectCollaborator(userTwo, projectOne));
        assertEquals(projCollabThree, projectService.findActiveProjectCollaborator(userThree, projectOne));

        //Given
        given(userService.getUserByEmail(userTwoEmail)).willReturn(userTwo);
        given(userService.getUserByEmail(userThreeEmail)).willReturn(userThree);

        given(projectService.getProjectById(projectId)).willReturn(projectOne);

        given(taskService.getTaskByTaskID(taskIdOne)).willReturn(taskOne);
        given(taskService.getTaskByTaskID(taskIdTwo)).willReturn(taskTwo);


    }

    @Test
    public void canCreateAnAssignmentRequest() throws Exception {
        //Given
        given(userService.getUserByEmail(userTwoEmail)).willReturn(userTwo);
        given(userService.getUserByEmail(userThreeEmail)).willReturn(userThree);

        given(projectService.getProjectById(projectId)).willReturn(projectOne);

        given(taskService.getTaskByTaskID(taskIdOne)).willReturn(taskOne);
        given(taskService.getTaskByTaskID(taskIdTwo)).willReturn(taskTwo);

        // when
//        MockHttpServletResponse response2 = mockMvc.perform(
//                post("/projects/" + projectId + "/tasks/" + taskIdOne + "/CreateAssignmentRequest").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

//        MockHttpServletResponse response = mockMvc.perform(
//                post("/projects/" + projectId + "/tasks/" + taskIdOne + "/CreateAssignmentRequest").contentType(MediaType.APPLICATION_JSON).header(userTwoEmail)).andReturn().getResponse();

        String uRi = "/projects/" + projectId + "/tasks/" + taskIdOne + "/CreateAssignmentRequest";
        TaskTeamRequest newReq = new TaskTeamRequest(projCollabTwo, taskOne);
        newReq.setType(TaskTeamRequest.ASSIGNMENT);
        taskOne.getPendingTaskAssignementRequests().add(newReq);

//        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.
//                post(uRi).contentType(MediaType.APPLICATION_JSON).content(requestJacksonTester.write(newReq).getJson()).header(userTwoEmail)).andReturn().getResponse();

        MockHttpServletResponse response = mockMvc.perform(
                post(uRi).contentType(MediaType.APPLICATION_JSON).content(taskJack.write(taskOne).getJson()).header(userTwoEmail)).andReturn().getResponse();


        // then
        assertEquals(response.getStatus(),HttpStatus.OK.value());
    }

}
