package project.restControllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import project.model.*;
import project.model.taskstateinterface.OnGoing;
import project.model.taskstateinterface.Planned;
import project.model.taskstateinterface.TaskStateInterface;
import project.services.ProjectService;
import project.services.TaskService;
import project.services.UserService;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan({ "project.services", "project.model", "project.controllers", "project.restControllers"  })
public class US203FindPendingTasksTest {

    @Autowired
    TaskService taskService;

    @Autowired
    ProjectService projectService;

    @Autowired
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

    @Before
    public void setUp(){

        // creates two users
        owner = userService.createUser("Owner boi", "hue@hue.com", "001", "Owns projects", "0000000", "here", "there", "where", "dunno", "mars");
        mike = userService.createUser("Mike", "mike@mike.com", "002", "Tests tasks", "1111111", "here", "there", "where", "dunno", "mars");


        // creates a project with ownder as manager
        findPendingTasks = projectService.createProject("Find tasks!", "Please help me find tasks", owner);


        // creates a collaborator
        collabMike = projectService.createProjectCollaborator(mike, findPendingTasks, 10);

        // creates 3 tasks
        ongoingTask = taskService.createTask("Mike has this task", findPendingTasks);

        secondOngoingTask = taskService.createTask("Mike's got it", findPendingTasks);

        unstartedTask = taskService.createTask("Mike does NOT have this task", findPendingTasks);

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



        // saves all tasks with updated info
        taskService.saveTask(ongoingTask);
        taskService.saveTask(secondOngoingTask);
        taskService.saveTask(unstartedTask);
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


    /**
     * This test asserts all the set up data is in the database, to isolate variables when solving errors in HTTP tests below
     */
    @Test
    public void databaseContainsDataTest() {
        // asserts that owner and mike's email returns his object, while a non existing email returns null
        assertEquals(owner, userService.getUserByEmail("hue@hue.com"));
        assertEquals(mike, userService.getUserByEmail("mike@mike.com"));
        assertEquals(null, userService.getUserByEmail("huehue@hue.com"));

        // asserts all the tasks have the correct state

        assertEquals(StateEnum.ONGOING, ongoingTask.getCurrentState());
        assertEquals(StateEnum.ONGOING, secondOngoingTask.getCurrentState());
        assertEquals(StateEnum.PLANNED, unstartedTask.getCurrentState());

        //asserts Mike is in the correct tasks
        assertTrue(ongoingTask.isProjectCollaboratorActiveInTaskTeam(collabMike));
        assertTrue(secondOngoingTask.isProjectCollaboratorActiveInTaskTeam(collabMike));
        assertFalse(unstartedTask.isProjectCollaboratorActiveInTaskTeam(collabMike));
    }
}
