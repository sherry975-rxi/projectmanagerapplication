package project.restcontrollers;

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
import project.repository.ProjCollabRepository;
import project.repository.ProjectsRepository;
import project.repository.TaskRepository;
import project.repository.UserRepository;
import project.restcontroller.US204AssignTaskRequestRestController;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;
import project.services.exceptions.ObjectNotFoundException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
public class US204AssignTaskRequestRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ProjectsRepository projectsRepository;

    @Mock
    ProjCollabRepository projCollabRepository;

    //@Mock
    @InjectMocks
    UserService userService;
    //@Mock
    @InjectMocks
    ProjectService projectService;
    //@Mock
    @InjectMocks
    TaskService taskService;

    //@InjectMocks
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

//    //This objects will be initialized by the initFields below
//    private JacksonTester<User> userJack;
//    private JacksonTester<Project> projectJack;
//    private JacksonTester<Task> taskJack;
//    private JacksonTester<TaskTeamRequest> requestJacksonTester;
//    private JacksonTester<List<TaskTeamRequest>> requestsListJacksonTester;


    @Before
    public void setUp() throws Exception{

//        JacksonTester.initFields(this, new ObjectMapper());
//
//        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
//
//        userPM = new User("Ana", "ana@gmail.com", "01", "Project Manager", "221238442");
//        userTwo = new User("Joao", "joao@gmail.com", "02", "collaborator", "221238447");
//        userThree = new User("Rui", "rui@gmail.com", "03", "collaborator", "221378449");
//
//        /* Adds the created users to the user repository */
//        userService.addUserToUserRepositoryX(userPM);
//        userService.addUserToUserRepositoryX(userTwo);
//        userService.addUserToUserRepositoryX(userThree);
//
//        userTwoEmail = "joao@gmail.com";
//        userThreeEmail = "rui@gmail.com";
//
//        projectOne = new Project("Restful", "Implement API Rest", userPM);
//        projectId = 1;
//        projectService.addProjectToProjectContainer(projectOne);
//
//        projCollabTwo = new ProjectCollaborator (userTwo,  20);
//        projCollabTwo.setProject(projectOne);
//        projectService.addProjectCollaborator(projCollabTwo);
//
//        projCollabThree = new ProjectCollaborator (userThree,  60);
//        projCollabThree.setProject(projectOne);
//        projectService.addProjectCollaborator(projCollabThree);
//
//        //Created task1
//        taskOne = new Task("Rest Controller", projectOne);
//        taskOne.setProject(projectOne);
//        taskOne.setId(1L);
//        taskOne.setTaskID("1");
//        taskService.saveTask(taskOne);
//
//
//        //Created Task2
//        taskTwo = new Task("Rest Controller Test", projectOne);
//        taskTwo.setProject(projectOne);
//        taskTwo.setId(2L);
//        taskTwo.setTaskID("2");
//        taskService.saveTask(taskTwo);


        initMocks(this);

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

//        Mockito.when(projectService.getProjectById(projectId)).thenReturn(projectOne);
//        Mockito.when(taskService.getTaskByTaskID(taskIdOne)).thenReturn(taskOne);
//        Mockito.when(userService.getUserByEmail(userTwoEmail)).thenReturn(userTwo);
//        Mockito.when(projectService.isUserActiveInProject(userTwo, projectOne)).thenReturn(true);
//        Mockito.when(projectService.findActiveProjectCollaborator(userTwo, projectOne)).thenReturn(projCollabTwo);
//        Mockito.when(taskOne.isAssignmentRequestAlreadyCreated(projCollabTwo)).thenReturn(false);
//        Mockito.when(taskService.saveTask(taskOne)).thenReturn(taskOne);

        controller = new US204AssignTaskRequestRestController(userService, taskService, projectService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        List<ProjectCollaborator> projCollabsList = new ArrayList<>();
        projCollabsList.add(projCollabTwo);
        projCollabsList.add(projCollabThree);

        List<ProjectCollaborator> userTwoProjCollab = new ArrayList<>();
        userTwoProjCollab.add(projCollabTwo);

        Mockito.when(userRepository.findByEmail(userTwoEmail)).thenReturn(Optional.of(userTwo));
        Mockito.when(projectsRepository.findById(projectId)).thenReturn(Optional.of(projectOne));
        Mockito.when(projCollabRepository.findAllByCollaborator(userTwo)).thenReturn(userTwoProjCollab);
        Mockito.when(projCollabRepository.findAllByProject(projectOne)).thenReturn(projCollabsList);
        Mockito.when(taskRepository.findByTaskID(taskIdOne)).thenReturn(Optional.of(taskOne));


    }
    @Test
    public void controllerInitializedCorrectly() {
        assertNotNull(controller);
    }

    @Test
    public void canCreateAnAssignmentRequest() throws Exception {

//        TaskTeamRequest newReq = new TaskTeamRequest(projCollabTwo, taskOne);
//        newReq.setType(TaskTeamRequest.ASSIGNMENT);
//        taskOne.getPendingTaskAssignementRequests().add(newReq);
//
//        //Given
//        given(userService.getUserByEmail(userTwoEmail)).willReturn(userTwo);
//        given(userService.getUserByEmail(userThreeEmail)).willReturn(userThree);
//
//        given(projectService.getProjectById(projectId)).willReturn(projectOne);
//
//        given(taskService.getTaskByTaskID(taskIdOne)).willReturn(taskOne);
//        given(taskService.getTaskByTaskID(taskIdTwo)).willReturn(taskTwo);
//
//        given(projectService.findActiveProjectCollaborator(userTwo, projectOne)).willReturn(projCollabTwo);
//        given(projectService.findActiveProjectCollaborator(userThree, projectOne)).willReturn(projCollabThree);
//
//        given(projectService.isUserActiveInProject(userTwo, projectOne)).willReturn(true);
//
//        given(taskService.saveTask(taskOne)).willReturn(taskOne);



        // when
//        MockHttpServletResponse response2 = mockMvc.perform(
//                post("/projects/" + projectId + "/tasks/" + taskIdOne + "/CreateAssignmentRequest").contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse();

//        MockHttpServletResponse response = mockMvc.perform(
//                post("/projects/" + projectId + "/tasks/" + taskIdOne + "/CreateAssignmentRequest").contentType(MediaType.APPLICATION_JSON).header(userTwoEmail)).andReturn().getResponse();

        //        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.
//                post(uRi).contentType(MediaType.APPLICATION_JSON).content(requestJacksonTester.write(newReq).getJson()).header(userTwoEmail)).andReturn().getResponse();

        //String uRi = "/projects/" + projectId + "/tasks/" + taskIdOne + "/CreateAssignmentRequest";



        //given(taskOne.createTaskAssignementRequest(projCollabTwo)).willReturn(true);
//
//        MockHttpServletResponse response = mockMvc.perform(
//                post("/projects/" + projectId + "/tasks/" + taskIdOne + "/CreateAssignmentRequest").contentType(MediaType.APPLICATION_JSON).header("userEmail", userTwoEmail)).andReturn().getResponse();


        // then
        //assertEquals(response.getStatus(),HttpStatus.OK.value());
        ResponseEntity<?> expected = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(expected, controller.createRequestAddCollabToTask(taskIdOne, projectId, userTwoEmail));

//        assertEquals(response.getStatus(),HttpStatus.OK.value());



//        MockHttpServletResponse responseTwo = mockMvc.perform(
//                post("/projects/" + projectId + "/tasks/" + taskIdOne + "/CreateAssignmentRequest")
//                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(taskJack.write(taskOne).getJson())
//                        .header("userEmail", userTwoEmail))
//                .andReturn().getResponse();
//
//
//        assertEquals(HttpStatus.FORBIDDEN.value(), responseTwo.getStatus());

        ResponseEntity<?> expectedTwo = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        assertEquals(expectedTwo, controller.createRequestAddCollabToTask(taskIdOne, projectId, userTwoEmail));


    }

    @Test
    public void canNotCreateAnAssignmentRequest() throws Exception {

        //Given
        //Assignment Request created for userTwo
        mockMvc.perform(
                post("/projects/" + projectId + "/tasks/" + taskIdOne + "/CreateAssignmentRequest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userEmail", userTwoEmail))
                .andReturn().getResponse();

        //When
        // Creating again assignment request that already exists for userTwo
        ResponseEntity<?> result = controller.createRequestAddCollabToTask(taskIdOne, projectId, userTwoEmail);

        //Then
        //expects Forbidden message
        ResponseEntity<?> expected = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        assertEquals(expected,result);

    }

    @Test
    public void canNotCreateAnAssignmentRequestTwo() throws Exception {

        //Given
        //Add userTwo to taskOne
        taskOne.addProjectCollaboratorToTask(projCollabTwo);

        //When
        // Create assignment request for userTwo but already is added to taskOne
        ResponseEntity<?> result = controller.createRequestAddCollabToTask(taskIdOne, projectId, userTwoEmail);

        //Then
        //expects Forbidden message
        ResponseEntity<?> expected = new ResponseEntity<>(HttpStatus.FORBIDDEN);
        assertEquals(expected,result);

    }



}
