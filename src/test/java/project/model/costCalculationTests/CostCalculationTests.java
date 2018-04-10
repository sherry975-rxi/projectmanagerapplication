package project.model.costCalculationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CostCalculationTests {

    User user, user2;
    Task task;
    Project project;
    ProjectCollaborator projectCollaborator, projectCollaborator2, projectCollaborator3;
    TaskCollaborator taskCollaborator, taskCollaborator2, taskCollaborator3;
    Report report;

    List<ProjectCollaborator> collaborators;

    @Before
    public void setUp() {

        user = new User("name", "email", "idNumber", "function", "phone");
        user2 = new User("Nuno", "nuno@emai.com", "1012", "tester", "987-23");

        project = new Project("project name", "create application", user);

        task = new Task("create controller", project);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.YEAR, 2017);
        calendar1.set(Calendar.MONTH, Calendar.MARCH);
        calendar1.set(Calendar.DAY_OF_MONTH, 10);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.YEAR, 2017);
        calendar2.set(Calendar.MONTH, Calendar.JUNE);
        calendar2.set(Calendar.DAY_OF_MONTH, 24);

        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(Calendar.YEAR, 2017);
        calendar3.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar3.set(Calendar.DAY_OF_MONTH, 30);

        ProjectCollaborator projectCollaborator = project.createProjectCollaborator(user, 10);
        projectCollaborator.setStartDate(calendar1);
        projectCollaborator.setFinishDate(calendar2);
        task.addProjectCollaboratorToTask(projectCollaborator);

        ProjectCollaborator projectCollaborator2 = project.createProjectCollaborator(user, 10);
        projectCollaborator2.setStartDate(calendar2);
        projectCollaborator2.setFinishDate(calendar3);
        task.addProjectCollaboratorToTask(projectCollaborator2);

        ProjectCollaborator projectCollaborator3 = project.createProjectCollaborator(user2, 20);
        task.addProjectCollaboratorToTask(projectCollaborator3);

        taskCollaborator = new TaskCollaborator(new ProjectCollaborator(user, 10));
        taskCollaborator2 = new TaskCollaborator(new ProjectCollaborator(user, 10));
        taskCollaborator3 = new TaskCollaborator(new ProjectCollaborator(user2, 20));

        //task.addTaskCollaboratorToTask(taskCollaborator);
        //task.addTaskCollaboratorToTask(taskCollaborator2);
        //task.addTaskCollaboratorToTask(taskCollaborator3);

        Report report = new Report(taskCollaborator, calendar1);
        Report report2 = new Report(taskCollaborator2, calendar2);
        Report report3 = new Report(taskCollaborator3, calendar3);


    }

    @After
    public void tearDown() {
        user = null;
        user2 = null;
        project = null;
        task = null;
        projectCollaborator = null;
        projectCollaborator2 = null;
        projectCollaborator3 = null;
        taskCollaborator = null;
        taskCollaborator2 = null;
        taskCollaborator3 = null;
        collaborators = null;

    }


    /**
     * This test confirms that the method findEarliestCollaborator() and the method
     * findLatestCollaborator() will return, correspondingly the first and last project collaborator
     * that provided a task report
     */

    @Test
    public void testFindEarlistAndLatestCollaborators(){

        List<ProjectCollaborator> collaborators = new ArrayList<>();
        collaborators.add(projectCollaborator2);
        collaborators.add(projectCollaborator);

        task.addProjectCollaboratorToTask(projectCollaborator);
        task.addProjectCollaboratorToTask(projectCollaborator2);

        taskCollaborator = new TaskCollaborator(projectCollaborator);
        taskCollaborator2 = new TaskCollaborator(projectCollaborator2);

        assertTrue(task.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));
        assertTrue(task.isProjectCollaboratorActiveInTaskTeam(projectCollaborator2));


        //verify if projectCollaborator and projectCollaborator2 are identified as belonging to user
        assertEquals(user, projectCollaborator.getUserFromProjectCollaborator());
        assertEquals(user, projectCollaborator2.getUserFromProjectCollaborator());

        Calendar reportA = Calendar.getInstance();
        reportA.set(Calendar.YEAR, 2017);
        reportA.set(Calendar.MONTH, Calendar.MARCH);
        reportA.set(Calendar.DAY_OF_MONTH, 10);

        projectCollaborator.setStartDate(reportA);

        Calendar reportB = Calendar.getInstance();
        reportB.add(Calendar.DAY_OF_YEAR, +30); //Adds 30 days to the reportA
        projectCollaborator2.setStartDate(reportB);

        //verifies that the start date in report A is earlier that the start date in report B,
        //therefore projectCollaborator is the earliest collaborator
        assertTrue(reportA.before(reportB));

        //verifies that the start dates for both projectCollaborators are different
        //collaborator

        assertFalse(projectCollaborator.getStartDate().equals(reportB));

        //assertEquals(projectCollaborator.getStartDate().get(Calendar.DAY_OF_YEAR),
        //      victim.findEarliestCollaborator(collaborators).getStartDate().get(Calendar.DAY_OF_YEAR));


        //assertEquals(projectCollaborator2.getStartDate().get(Calendar.DAY_OF_YEAR),
        //        victim.findLatestCollaborator(collaborators).getStartDate().get(Calendar.DAY_OF_YEAR));


    }


}
