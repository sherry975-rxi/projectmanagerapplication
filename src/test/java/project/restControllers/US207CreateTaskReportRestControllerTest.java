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
import project.restcontroller.US207CreateTaskReportRestController;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
public class US207CreateTaskReportRestControllerTest {

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
    private US207CreateTaskReportRestController controller;

    private User userPM;
    private User userTwo;
    private String userTwoEmail;
    private int userTwoId;
    private Project projectOne;
    private Integer projectId;
    private Task taskOne;
    private String taskIdOne;
    private Task taskTwo;
    private String taskIdTwo;
    private ProjectCollaborator projCollabTwo;

    @Before
    public void setUp() {
        //initMocks(this);

        // create userPM
        userPM = userService.createUser("Ana", "ana@gmail.com", "01", "collaborator", "221238442", "Rua Porto",
                "4480", "Porto", "Porto", "Portugal");

        // create userTwo
        userTwo = userService.createUser("Joao", "joao@gmail.com", "02", "collaborator", "221238447", "Rua Porto",
                "5480", "Porto", "Porto", "Portugal");
        userTwoEmail = "joao@gmail.com";
        userTwoId = 02;

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

        //Create Task Collaborator
        taskOne.addProjectCollaboratorToTask(projCollabTwo);
        taskService.saveTask(taskOne);

        Calendar date = Calendar.getInstance();

        taskOne.createReport(taskOne.getTaskCollaboratorByEmail(userTwoEmail), date, 20.0);
        taskOne.createReport(taskOne.getTaskCollaboratorByEmail(userTwoEmail),date,35);
        taskService.saveTask(taskOne);


        // initialing the rest controller
        controller = new US207CreateTaskReportRestController(userService, taskService, projectService);

        // building mockMVC
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        //creating an expected list of project collaborators from projectOne
        List<ProjectCollaborator> projCollabsList = new ArrayList<>();
        projCollabsList.add(projCollabTwo);


        //creating an expected list of project collaborators of userTwo
        List<ProjectCollaborator> userTwoProjCollab = new ArrayList<>();
        userTwoProjCollab.add(projCollabTwo);

        Mockito.when(userRepository.findByEmail(userTwoEmail)).thenReturn(Optional.of(userTwo));
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
        userTwoEmail = null;
        userTwoId = 0;
        projectOne = null;
        projectId = null;
        taskOne = null;
        taskIdOne = null;
        taskTwo = null;
        taskIdTwo = null;
        projCollabTwo = null;
    }

    @Test
    public void controllerInitializedCorrectly() {
        assertNotNull(controller);
    }

    @Test
    public void getTaskReportFromUsers() {


        //Given
        // taskOne in projectOne, with reports created inside,
        controller.getTasksReportsFromUser(taskIdOne, projectId, userTwoId);
        //When
        //getting all reports associated to a user inside the given task
        ResponseEntity<?> result = controller.getTasksReportsFromUser(taskIdOne, projectId, userTwoId);

        //Then
        // It is expected to be successfully retrieved
        ResponseEntity<?> expected = new ResponseEntity<>(taskOne.getReportsFromGivenUser(userTwoEmail),HttpStatus.OK);
        assertEquals(expected, result);


    }

    @Test
    public void canRetrieveReportsToString() {

        //Given
        // taskOne in projectOne, with reports created inside,
        controller.getTaskStringReportsFromUser(taskIdOne, projectId, userTwoId);


        List<String> reportsList = taskOne.getReportsFromGivenUser(userTwoEmail)
                .stream().map(Report -> controller.dateToString(Report)).collect(Collectors.toList());

        //When
        // asked to retrieve all the reports from user as strings
        ResponseEntity<?> result = controller.getTaskStringReportsFromUser(taskIdOne, projectId, userTwoId);

        //Then
        // It is expected to be successfully retrieved as a string
        ResponseEntity<?> expected = new ResponseEntity<>(reportsList, HttpStatus.OK);
        assertEquals(expected, result);

    }

    @Test
    public void canCreateReports() {
        // Given
        // a reportDTO that contains a reportTime,
        Report reportDTO = new Report();
        reportDTO.setId(1);
        reportDTO.setReportedTime(20.0);

        // When
        // asked to be added to the task as a new report
        ResponseEntity<?> result  = controller.createTaskReport(reportDTO, taskIdOne, projectId, userTwoId);

        // Then
        // confirms the added report with a custom message
        ResponseEntity<?> expected = ResponseEntity.ok().body("Report created!\nINFO:" + "\nTask ID: " + taskIdOne +"\nDescription: " + taskOne.getDescription() + "\nUser: " + userTwo.getName() + "\nTime reported: " + "20.0");
    }

    @Test
    public void canUpdateReports() {
        // Given
        // a reportDTO that contains a reportTime,
        Report reportDTOUpdate = new Report();
        reportDTOUpdate.setId(0);
        reportDTOUpdate.setReportedTime(25.0);
        reportDTOUpdate.setTaskCollaborator(taskOne.getTaskCollaboratorByEmail(userTwoEmail));

        // When
        // asked to be added to the task as a update to an already existing report
        ResponseEntity<?> result  = controller.updateTaskReport(reportDTOUpdate,0, taskIdOne, projectId, userTwoId);

        // Then
        // confirms the updated report with a custom message
        ResponseEntity<?> expected = new ResponseEntity<>(taskOne.getReports().get(0) ,HttpStatus.OK);
        assertEquals(expected, result);

    }

}
