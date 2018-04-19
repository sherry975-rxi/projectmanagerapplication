package project.restControllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;



@RunWith(MockitoJUnitRunner.class)

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

    @InjectMocks
    UserService userService;
    @InjectMocks
    ProjectService projectService;
    @InjectMocks
    TaskService taskService;

    private US204AssignTaskRequestRestController controller;

    private User userPM;
    private User userTwo;
    private int userTwoId;
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

    @Before
    public void setUp() throws Exception{

        // create userPM
        userPM = userService.createUser("Ana", "ana@gmail.com", "01", "collaborator", "221238442", "Rua Porto",
                "4480", "Porto", "Porto", "Portugal");

        // create userTwo
        userTwo = userService.createUser("Joao", "joao@gmail.com", "02", "collaborator", "221238447", "Rua Porto",
                "5480", "Porto", "Porto", "Portugal");
        userTwoId = 2;

        // create userThree
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

        // initialing the rest controller
        controller = new US204AssignTaskRequestRestController(userService, taskService, projectService);

        // building mockMVC
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        //creating an expected list of project collaborators from projectOne
        List<ProjectCollaborator> projCollabsList = new ArrayList<>();
        projCollabsList.add(projCollabTwo);
        projCollabsList.add(projCollabThree);

        //creating an expected list of project collaborators of userTwo
        List<ProjectCollaborator> userTwoProjCollab = new ArrayList<>();
        userTwoProjCollab.add(projCollabTwo);

        Mockito.when(userRepository.findByEmail("joao@gmail.com")).thenReturn(Optional.of(userTwo));
        Mockito.when(userRepository.findByUserID(userTwoId)).thenReturn(userTwo);
        Mockito.when(projectsRepository.findById(projectId)).thenReturn(Optional.of(projectOne));
        Mockito.when(projCollabRepository.findAllByCollaborator(userTwo)).thenReturn(userTwoProjCollab);
        Mockito.when(projCollabRepository.findAllByProject(projectOne)).thenReturn(projCollabsList);
        Mockito.when(taskRepository.findByTaskID(taskIdOne)).thenReturn(Optional.of(taskOne));


    }
    @After
    public void tearDown() {
        controller = null;
        userPM = null;
        userTwo = null;
        userTwoId = 0;
        userThree = null;
        userThreeEmail = null;
        projectOne = null;
        projectId = null;
        taskOne = null;
        taskIdOne = null;
        taskTwo = null;
        taskIdTwo = null;
        projCollabTwo = null;
        projCollabThree = null;
    }

    @Test
    public void controllerInitializedCorrectly() {
        assertNotNull(controller);
    }

    /**
     * Given
     * TaskOne in projectOne, with no collaborators, neither requests
     *
     * When
     * Creating assignment request to taskOne from userTwo
     *
     * Then
     * It is expected to be successfully created
     *
     * @throws Exception
     */
    @Test
    public void canCreateAnAssignmentRequest() throws Exception {

        //Given
        User userDTOTwo = new User();
        userDTOTwo.setEmail("joao@gmail.com");



        //When
        ResponseEntity<?> result = controller.createAssignmentRequest(taskIdOne, projectId, userDTOTwo);

        //Then
        TaskTeamRequest assignRequest = new TaskTeamRequest();
        assignRequest.setType(0);
        assignRequest.setProjCollab(projCollabTwo);
        assignRequest.setTask(taskOne);

        ResponseEntity<?> expected = new ResponseEntity<>(assignRequest, HttpStatus.CREATED);

        assertEquals(expected,result);

    }

    /**
     * Given
     * Assignment Request created for userTwo
     *
     * When
     * Creating again assignment request that already exists for userTwo
     *
     * Then
     * Expects a METHOD_NOT_ALLOWED message
     *
     * @throws Exception
     */
    @Test
    public void canNotCreateAnAssignmentRequest() throws Exception {

        User userDTOTwo = new User();
        userDTOTwo.setEmail("joao@gmail.com");

        //Given
        /*
        mockMvc.perform(
                post("/projects/" + projectId + "/tasks/" + taskIdOne + "/CreateAssignmentRequest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userEmail", userTwoId))
                .andReturn().getResponse();
        */
        //or could be created calling the method of the rest controller
        controller.createAssignmentRequest(taskIdOne, projectId, userDTOTwo);

        //When
        ResponseEntity<?> result = controller.createAssignmentRequest(taskIdOne, projectId, userDTOTwo);

        //Then
        ResponseEntity<?> expected = new ResponseEntity<>( HttpStatus.METHOD_NOT_ALLOWED);

        assertEquals(expected,result);

    }

    /**
     * Given
     * Adding userTwo to taskOne
     *
     * When
     * Creating assignment request for userTwo but already is added to taskOne
     *
     * Then
     * Expects a METHOD_NOT_ALLOWED message
     *
     * @throws Exception
     */
    @Test
    public void canNotCreateAnAssignmentRequestTwo() throws Exception {

        User userDTOTwo = new User();
        userDTOTwo.setEmail("joao@gmail.com");

        //Given
        taskOne.addProjectCollaboratorToTask(projCollabTwo);

        //When
        ResponseEntity<?> result = controller.createAssignmentRequest(taskIdOne, projectId, userDTOTwo);

        //Then
        ResponseEntity<?> expected = new ResponseEntity<>( HttpStatus.METHOD_NOT_ALLOWED);

        assertEquals(expected,result);

    }

    /**
     * Given
     * Assignment Request created for userTwo in taskOne
     * Removal Request created for userThree in taskOne
     *
     * When
     * Asking for all existing requests in taskOne
     *
     * Then
     * Expects an ON message and a list of existing requests
     *
     * @throws Exception
     */
    @Test
    public  void retriveAllRequest() throws Exception {


        // Given
        taskOne.addProjectCollaboratorToTask(projCollabThree);
        taskOne.createTaskAssignmentRequest(projCollabTwo);
        taskOne.createTaskRemovalRequest(projCollabThree);

        // When
        ResponseEntity<List<TaskTeamRequest>> result = controller.getAllRequests(taskIdOne);

        // Then
        List expectedList = new ArrayList();
        TaskTeamRequest assign = new TaskTeamRequest();
        assign.setType(0);
        assign.setProjCollab(projCollabTwo);
        assign.setTask(taskOne);
        TaskTeamRequest removal = new TaskTeamRequest();
        removal.setType(1);
        removal.setProjCollab(projCollabThree);
        removal.setTask(taskOne);

        expectedList.add(assign);
        expectedList.add(removal);

        ResponseEntity<List<TaskTeamRequest>> expected = new ResponseEntity<>(expectedList, HttpStatus.OK);

        assertEquals(expected,result);

    }

    /**
     * Given
     * Adding userThree to taskOne
     * Assignment Request created for userTwo in taskOne
     * Removal Request created for userThree in taskOne
     *
     * When
     * Asking for all existing ASSIGNMENT requests in taskOne
     *
     * Then
     * Expects an ON message and a list of existing ASSIGNMENT requests
     *
     * @throws Exception
     */
    @Test
    public  void retriveAllAssignementRequest() throws Exception {

        // Given

        taskOne.addProjectCollaboratorToTask(projCollabThree);
        taskOne.createTaskAssignmentRequest(projCollabTwo);
        taskOne.createTaskRemovalRequest(projCollabThree);

        // When

        ResponseEntity<?> result = controller.getAllFilteredRequests(taskIdOne, "assignment");

        // Then

        List expectedList = new ArrayList();

        TaskTeamRequest assign = new TaskTeamRequest();
        assign.setType(0);
        assign.setProjCollab(projCollabTwo);
        assign.setTask(taskOne);

        expectedList.add(assign);

        ResponseEntity<?> expected = new ResponseEntity<>(expectedList, HttpStatus.OK);

        assertEquals(expected,result);

    }

    /**
     * Given
     * Adding userThree to taskOne
     * Adding userThree to taskOne
     * Removal Request created for userThree in taskOne
     *
     * When
     * Asking for all existing REMOVAL requests in taskOne
     *
     * Then
     * Expects an ON message and a list of existing REMOVAL requests
     *
     * @throws Exception
     */
    @Test
    public  void retriveAllRemovalRequest() throws Exception {

        // Given
        taskOne.addProjectCollaboratorToTask(projCollabTwo);
        taskOne.addProjectCollaboratorToTask(projCollabThree);
        taskOne.createTaskRemovalRequest(projCollabThree);

        // When

        ResponseEntity<?> result = controller.getAllFilteredRequests(taskIdOne, "removal");

        // Then

        List expectedList = new ArrayList();

        TaskTeamRequest removal = new TaskTeamRequest();
        removal.setType(1);
        removal.setProjCollab(projCollabThree);
        removal.setTask(taskOne);

        expectedList.add(removal);

        ResponseEntity<?> expected = new ResponseEntity<>(expectedList, HttpStatus.OK);

        assertEquals(expected,result);

    }




}
