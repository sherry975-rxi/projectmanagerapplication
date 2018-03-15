package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.UserService;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class US207CreateTaskReportControllerTest {

    US210GetAllFinishedUserTasksInDecreasingOrderController tasksFiltersController;
    User user1, user2;
    Project project1;
    ProjectCollaborator projCollab1, projCollab2;
    Task task1, task2;
    TaskCollaborator taskCollab1, taskCollab2;
    US207CreateTaskReportController controller;
    US207CreateTaskReportController controller2;
    UserService userContainer;
    ProjectService projectContainer;


    @Before
    public void setUp() {

        userContainer = new UserService();
        projectContainer = new ProjectService();

        // create users in company
        user2 = userContainer.createUser("João", "user2@gmail.com", "001", "Manager", "930025000",
                "rua doutor antónio", "7689-654", "porto", "porto", "portugal");
        user1 = userContainer.createUser("Juni", "user3@gmail.com", "002", "Code Monkey", "930000000",
                "rua engenheiro joão", "789-654", "porto", "porto", "portugal");

        userContainer.addUserToUserRepository(user1);
        userContainer.addUserToUserRepository(user2);

        // change profiles of users from VISITOR (default) to COLLABORATOR
        user2.setUserProfile(Profile.COLLABORATOR);
        user1.setUserProfile(Profile.COLLABORATOR);

        // create project 1 in company 1
        project1 = projectContainer.createProject("name3", "description4", user2);

        // add project 1 to company 1
        projectContainer.addProjectToProjectContainer(project1);

        // create an estimated Task Start Date
        Calendar estimatedTaskStartDateTest = Calendar.getInstance();
        estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
        estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
        estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
        estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

        // create an estimated Task Dead line Date
        Calendar taskDeadlineDateTest1 = Calendar.getInstance();
        taskDeadlineDateTest1.set(Calendar.YEAR, 2019);
        taskDeadlineDateTest1.set(Calendar.MONTH, Calendar.JANUARY);
        Calendar taskDeadlineDateTest2 = Calendar.getInstance();
        taskDeadlineDateTest2.set(Calendar.YEAR, 2019);
        taskDeadlineDateTest2.set(Calendar.MONTH, Calendar.FEBRUARY);
        Calendar taskDeadlineDateTest3 = Calendar.getInstance();
        taskDeadlineDateTest3.set(Calendar.YEAR, 2019);
        taskDeadlineDateTest3.set(Calendar.MONTH, Calendar.MARCH);
        Calendar taskDeadlineDateTest4 = Calendar.getInstance();
        taskDeadlineDateTest4.set(Calendar.YEAR, 2019);
        taskDeadlineDateTest4.set(Calendar.MONTH, Calendar.APRIL);

        // create a Date before to the previous Dead line created in order to result in
        // an expired Task
        Calendar taskExpiredDeadlineDateTest = Calendar.getInstance();
        taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
        taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
        taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
        taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

        // create tasks in project 1
        task1 = project1.getTaskRepository().createTask("Do this", 10, estimatedTaskStartDateTest,
                taskDeadlineDateTest1, 10);
        task2 = project1.getTaskRepository().createTask("Do that", 10, estimatedTaskStartDateTest,
                taskDeadlineDateTest2, 10);


        // add tasks to task repository of project 1
        project1.getTaskRepository().addTaskToProject(task1);
        project1.getTaskRepository().addTaskToProject(task2);


        // add costPerEffort to users in project 1, resulting in a Project Collaborator
        // for each one
        projCollab1 = project1.createProjectCollaborator(user1, 250);
        projCollab2 = project1.createProjectCollaborator(user2, 120);

        // associate Project Collaborators to project 1 (info user + costPerEffort)
        project1.addProjectCollaboratorToProjectTeam(projCollab1);
        project1.addProjectCollaboratorToProjectTeam(projCollab2);

        // defines finish date to task, and mark it as Finished7
        task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
        task1.setTaskDeadline(taskDeadlineDateTest1);
        task1.addProjectCollaboratorToTask(projCollab1);
        Calendar startDateTask1 = estimatedTaskStartDateTest;
        startDateTask1.add(Calendar.DAY_OF_MONTH, 60);
        task1.setStartDate(startDateTask1);

        task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
        task2.setTaskDeadline(taskDeadlineDateTest1);
        task2.addProjectCollaboratorToTask(projCollab1);
        Calendar startDateTask2 = estimatedTaskStartDateTest;
        startDateTask2.add(Calendar.DAY_OF_MONTH, 60);
        task2.setStartDate(startDateTask1);


    }

    @After
    public void tearDown() {
        userContainer = null;
        projectContainer = null;
        user1 = null;
        user2 = null;
        project1 = null;
        projCollab1 = null;
        projCollab2 = null;
        task1 = null;
        task2 = null;
        taskCollab1 = null;
        taskCollab2 = null;
        tasksFiltersController = null;
        controller = null;
    }


    /*
     * Tests the constructor
     */
    @Test
    public void testUS207CreateTaskReportControllerConstructor() {
        taskCollab1 = task1.createTaskCollaborator(projCollab1);
        task1.addTaskCollaboratorToTask(taskCollab1);


        controller = new US207CreateTaskReportController(user1.getEmail(), task1.getTaskID());

    }

    @Test
    public void testUS207GetEmail() {


        taskCollab1 = task1.createTaskCollaborator(projCollab1);
        task1.addTaskCollaboratorToTask(taskCollab1);

        controller = new US207CreateTaskReportController(user1.getEmail(), task1.getTaskID());

        assertEquals(controller.getTaskCollaboratorByEmail(user1.getEmail()), taskCollab1);

        //Tries to check for an invalid email
        assertEquals(null, controller.getTaskCollaboratorByEmail("invalidEmail"));


    }

    @Test
    public void testUS207CreateReport() {

        taskCollab1 = task1.createTaskCollaborator(projCollab1);
        task1.addTaskCollaboratorToTask(taskCollab1);

        controller = new US207CreateTaskReportController(user1.getEmail(), task1.getTaskID());
        controller2 = new US207CreateTaskReportController(user2.getEmail(), task1.getTaskID());

        assertTrue(controller.createReportController(20, Calendar.getInstance()));


    }

    @Test
    public void testUS207getReportsIndexByGivenUser() {

        /*
        Creates two TaskCollaborators
         */
        taskCollab1 = task1.createTaskCollaborator(projCollab1);
        taskCollab2 = task1.createTaskCollaborator(projCollab2);

        /*
        Adds the TaskCollaborators to the task
         */
        task1.addTaskCollaboratorToTask(taskCollab1);
        task1.addTaskCollaboratorToTask(taskCollab2);

        /*
        Creates two CreateTaskReport Controllers
         */
        controller = new US207CreateTaskReportController(user1.getEmail(), task1.getTaskID());
        controller2 = new US207CreateTaskReportController(user2.getEmail(), task1.getTaskID());


        /*
        Creates a list of integer, to compare with the list returned by the method
         */
        List<Integer> reportIndex = new ArrayList<>();


        /*
        Reports 0, 1 and 4 belongs to User1
         */
        controller.createReportController(10, Calendar.getInstance());
        controller.createReportController(20, Calendar.getInstance());
        controller2.createReportController(5, Calendar.getInstance());
        controller2.createReportController(3, Calendar.getInstance());
        controller.createReportController(9, Calendar.getInstance());


        reportIndex.add(0);
        reportIndex.add(1);
        reportIndex.add(4);

        assertEquals(reportIndex, controller.getReportsIndexByGivenUser());

    }

    @Test
    public void testUS207UpdateReport() {

         /*
        Creates two TaskCollaborators
         */
        taskCollab1 = task1.createTaskCollaborator(projCollab1);
        taskCollab2 = task1.createTaskCollaborator(projCollab2);

        /*
        Adds the TaskCollaborators to the task
         */
        task1.addTaskCollaboratorToTask(taskCollab1);
        task1.addTaskCollaboratorToTask(taskCollab2);

        /*
        Creates two CreateTaskReport Controllers
         */
        controller = new US207CreateTaskReportController(user1.getEmail(), task1.getTaskID());
        controller2 = new US207CreateTaskReportController(user2.getEmail(), task1.getTaskID());

        /*
        Creates a report
         */
        controller.createReportController(10, Calendar.getInstance());

        /*
        Checks that the report date is 10;
         */
        assertEquals(controller.getReportTimeByGivenUser(taskCollab1).get(0), 10, 0.0);


        /*
        Updates TaskReport to 30
         */
        assertTrue(controller.updateTaskReport(30, taskCollab1, 0));


         /*
        Checks that the report date is 30;
         */
        assertEquals(controller.getReportTimeByGivenUser(taskCollab1).get(0), 30, 0.0);

    }


    @Test
    public void testUS207getReportsCreationDateByGivenUser() {
          /*
        Creates two TaskCollaborators
         */
        taskCollab1 = task1.createTaskCollaborator(projCollab1);

           /*
        Adds the TaskCollaborators to the task
         */
        task1.addTaskCollaboratorToTask(taskCollab1);

           /*
        Creates two CreateTaskReport Controllers
         */
        controller = new US207CreateTaskReportController(user1.getEmail(), task1.getTaskID());

        Calendar dayOfReport = Calendar.getInstance();
          /*
        Creates a report
         */
        controller.createReportController(10, dayOfReport);

        assertEquals(controller.getReportsCreationDateByGivenUser(taskCollab1).get(0), dayOfReport);
    }

    @Test
    public void testUS207getReportUpdateDateByGivenUser() {


          /*
        Creates two TaskCollaborators
         */
        taskCollab1 = task1.createTaskCollaborator(projCollab1);

           /*
        Adds the TaskCollaborators to the task
         */
        task1.addTaskCollaboratorToTask(taskCollab1);

           /*
        Creates two CreateTaskReport Controllers
         */
        controller = new US207CreateTaskReportController(user1.getEmail(), task1.getTaskID());

        Calendar dayOfReport = Calendar.getInstance();

          /*
        Creates a report
         */
        controller.createReportController(10, dayOfReport);

        controller.updateTaskReport(20, taskCollab1, 0);

        assertEquals(controller.getReportsUpdateDateByGivenUser(taskCollab1).get(0), dayOfReport);
    }

}
