package project.model.costCalculationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;
import project.model.costcalculationinterface.*;

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
    Report report, report2;
    CostCalculationInterface calculationFirst, calculationLast, calculationFirstAndLAst, calculationAverage;


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

        projectCollaborator = project.createProjectCollaborator(user, 10);
        projectCollaborator.setStartDate(calendar1);
        projectCollaborator.setFinishDate(calendar2);
        task.addProjectCollaboratorToTask(projectCollaborator);

        projectCollaborator2 = project.createProjectCollaborator(user, 10);
        projectCollaborator2.setStartDate(calendar2);
        projectCollaborator2.setFinishDate(calendar3);
        task.addProjectCollaboratorToTask(projectCollaborator2);

        ProjectCollaborator projectCollaborator3 = project.createProjectCollaborator(user2, 20);
        task.addProjectCollaboratorToTask(projectCollaborator3);

        taskCollaborator = new TaskCollaborator(new ProjectCollaborator(user, 10));
        taskCollaborator2 = new TaskCollaborator(new ProjectCollaborator(user, 12));
        taskCollaborator3 = new TaskCollaborator(new ProjectCollaborator(user2, 20));

        report = new Report(taskCollaborator, calendar1);
        report2 = new Report(taskCollaborator2, calendar2);



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
    public void testFindEarliestAndLatestCollaborators(){

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

        task.createReport(taskCollaborator, Calendar.getInstance(), 5);
        task.createReport(taskCollaborator2, Calendar.getInstance(), 7);


        assertFalse(projectCollaborator.getStartDate().equals(reportB));

        /**
         * tests if the method in interface FirstCollaboratorCost() correctly calculates the project cost
         * based on the first instance of the user
         */

        double costFirstUserInstance = taskCollaborator.getProjectCollaboratorFromTaskCollaborator().getCostPerEffort();


        calculationFirst = new FirstCollaboratorCost();
        calculationFirst.updateCalculationMethod(report, collaborators);

        assertEquals(costFirstUserInstance, report.getCost(), 0.01);

        /**
         * tests if the method in interface LastCollaboratorCost() correctly calculates the project cost
         * based on the last instance of the user
         */


        double costLastUserInstance = taskCollaborator2.getProjectCollaboratorFromTaskCollaborator().getCostPerEffort();

        calculationLast = new LastCollaboratorCost();
        calculationLast.updateCalculationMethod(report2, collaborators);

        assertEquals(costLastUserInstance, report2.getCost(), 0.01);

        /**
         * tests if the method in interface FirstAndLastCollaboratorCost() correctly calculates the project cost
         * based on the first and last instance of the user (sum of costs, divided by the number of instances)
         */

        double costFirstAndLastInstance = (costFirstUserInstance + costLastUserInstance)/2;

        calculationFirstAndLAst = new FirstAndLastCollaboratorCost();
        calculationFirstAndLAst.updateCalculationMethod(report, collaborators);

        assertEquals(costFirstAndLastInstance, report.getCost(), 0.01);

        /**
         * tests if the method in interface AverageCollaboratorCost() correctly calculates the project cost
         * based on the average of first and last instance of the user
         */

        double costAverage = costFirstAndLastInstance;

        calculationAverage = new AverageCollaboratorCost();
        calculationAverage.updateCalculationMethod(report, collaborators);

        assertEquals(costAverage, report.getCost(), 0.01);

    }


}
