package project.restControllers;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import project.model.*;
import project.model.taskstateinterface.OnGoing;
import project.model.taskstateinterface.Planned;
import project.repository.ProjCollabRepository;
import project.repository.ProjectsRepository;
import project.repository.TaskRepository;
import project.repository.UserRepository;
import project.restcontroller.US203FindPendingTaskRestRequest;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class US203FindPendingTasksTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ProjectsRepository projectsRepository;

    @Mock
    ProjCollabRepository projCollabRepository;

    @InjectMocks
    TaskService taskService;

    @InjectMocks
    ProjectService projectService;

    @InjectMocks
    UserService userService;

    User owner;
    User mike;

    Project findPendingTasks;

    ProjectCollaborator collabMike;

    Calendar expectedStartDate;
    Calendar expectedDeadline;

    Task ongoingTask;
    Task secondOngoingTask;
    Task unstartedTask;

    List<Task> taskList;

    US203FindPendingTaskRestRequest restController;


    @Before
    public void setUp() throws Exception {

        initMocks(this);


        // creates two users
        owner = userService.createUser("Owner boi", "hue@hue.com", "001", "Owns projects", "0000000", "here", "there", "where", "dunno", "mars");
        mike = userService.createUser("Mike", "mike@mike.com", "002", "Tests tasks", "1111111", "here", "there", "where", "dunno", "mars");


        // creates a project with ownder as manager
        findPendingTasks = projectService.createProject("Find tasks!", "Please help me find tasks", owner);


        // creates a collaborator
        collabMike = projectService.createProjectCollaborator(mike, findPendingTasks, 10);


        // creates a mock task list where created tasks are added
        // this allows the task ID's to be generated normally according to the number of tasks per project
        taskList = new ArrayList<>();
        Mockito.when(taskRepository.findAllByProject(findPendingTasks)).thenReturn(taskList);


        // creates 3 tasks, adding each to the mock task list
        ongoingTask = taskService.createTask("Mike has this task", findPendingTasks);
        taskList.add(ongoingTask);


        secondOngoingTask = taskService.createTask("Mike's got it", findPendingTasks);
        taskList.add(secondOngoingTask);


        unstartedTask = taskService.createTask("Mike does NOT have this task", findPendingTasks);
        taskList.add(unstartedTask);


        // creates an expected start and finish date
        expectedStartDate= Calendar.getInstance();
        expectedStartDate.add(Calendar.MONTH, -1);
        expectedDeadline= Calendar.getInstance();
        expectedDeadline.add(Calendar.MONTH, 1);

        //adds necessary information for ongoing task and second ongoing task to enter the Ongoing
        ongoingTask.setTaskBudget(10);
        ongoingTask.setEstimatedTaskEffort(10);
        ongoingTask.setStartDate(expectedStartDate);
        ongoingTask.setTaskDeadline(expectedDeadline);
        ongoingTask.setTaskState(new Planned());
        ongoingTask.addProjectCollaboratorToTask(collabMike);
        ongoingTask.setStartDate(Calendar.getInstance());
        ongoingTask.setTaskState(new OnGoing());
        ongoingTask.setCurrentState(StateEnum.ONGOING);

        secondOngoingTask.setTaskBudget(10);
        secondOngoingTask.setEstimatedTaskEffort(10);
        secondOngoingTask.setStartDate(expectedStartDate);
        secondOngoingTask.setTaskDeadline(expectedDeadline);
        secondOngoingTask.addProjectCollaboratorToTask(collabMike);
        secondOngoingTask.setStartDate(Calendar.getInstance());
        secondOngoingTask.setTaskState(new OnGoing());
        secondOngoingTask.setCurrentState(StateEnum.ONGOING);

        // adds info for unstarted task to become Planning state
        unstartedTask.setTaskBudget(10);
        unstartedTask.setEstimatedTaskEffort(10);
        unstartedTask.setStartDate(expectedStartDate);
        unstartedTask.setTaskDeadline(expectedDeadline);
        unstartedTask.setTaskState(new Planned());
        unstartedTask.setCurrentState(StateEnum.PLANNED);



        // creates a mocked restController for unit testing
        restController = new US203FindPendingTaskRestRequest(userService, taskService);

        // creates mock returns to find Owner and Mike's data by ID number, as well as the task list
        Mockito.when(userRepository.findById(mike.getId())).thenReturn(mike);

        Mockito.when(taskRepository.findAll()).thenReturn(taskList);

    }

    @After
    public void tearDown(){
        owner = null;
        mike = null;
        findPendingTasks=null;
        collabMike=null;
        unstartedTask=null;
        secondOngoingTask=null;
        ongoingTask=null;
    }



    /*
    *
    * This tests if the returned list of pending User tasks (as String) is working correctly
    *
    *
    *
    */

    @Test
    public void us203UnitTest() {

        // given a user with two ongoing tasks (Mike)

        // when both tasks he's working on are converted to String
        String ongoingTaskString = ongoingTask.getTaskID() + " - " + ongoingTask.getDescription();
        String n2OngoingTaskString = secondOngoingTask.getTaskID() + " - " + secondOngoingTask.getDescription();

        List<String> expected = new ArrayList<>();

        expected.add(ongoingTaskString);
        expected.add(n2OngoingTaskString);

        String mikeID = String.valueOf(mike.getId());

        // then the getPendingTasks method must return those two tasks when called to search for mike's ID
        assertEquals(ResponseEntity.ok().body(expected), restController.getPendingTasks(mikeID));

    }

    /**
     *
     * This tests all the unhappy cases, such as invalid inputs and a user with no ongoing tasks
     *
     *
     */
    @Test
    public void us203UnitTestInvalidInputs() {

        // given invalid inputs, getPendingTasks must return only a List with a single entry "401 Unauthorized"
        List<String> unauthorized = new ArrayList<>();
        unauthorized.add("401 Unauthorized");

        // when inputted a non integer, then it must return 401
        assertEquals(ResponseEntity.ok().body(unauthorized), restController.getPendingTasks("Not A Number"));

        // when inputted a non existing ID, then it must return 401
        assertEquals(ResponseEntity.ok().body(unauthorized), restController.getPendingTasks("-2"));


        // given a user with no Tasks, getPendingTasks must return only a List with a single entry "You have no ongoing tasks!"
        List<String> noTasks = new ArrayList<>();
        noTasks.add("You have no ongoing tasks!");


        // when Owner's ID is converted into string and fed to get Pending Tasks
        String ownerID = String.valueOf(owner.getId());
        Mockito.when(userRepository.findById(owner.getId())).thenReturn(owner);

        // then it must return a list with the "no tasks" message
        assertEquals(ResponseEntity.ok().body(noTasks), restController.getPendingTasks(ownerID));


    }

}
