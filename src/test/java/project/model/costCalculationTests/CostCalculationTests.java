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
    TaskCollaborator taskCollaborator;
    Report report;
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


        Calendar calendarExtra = Calendar.getInstance();
        calendarExtra.set(Calendar.YEAR, 2017);
        calendarExtra.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendarExtra.set(Calendar.DAY_OF_MONTH, 24);


        projectCollaborator = project.createProjectCollaborator(user, 10);
        projectCollaborator.setStartDate(calendar1);
        projectCollaborator.setFinishDate(calendar2);
        task.addProjectCollaboratorToTask(projectCollaborator);

        projectCollaborator2 = project.createProjectCollaborator(user, 20);
        projectCollaborator2.setStartDate(calendar2);
        projectCollaborator2.setFinishDate(calendar3);
        task.addProjectCollaboratorToTask(projectCollaborator2);

        projectCollaborator3 = project.createProjectCollaborator(user, 15);
        projectCollaborator3.setStartDate(calendarExtra);
        projectCollaborator3.setFinishDate(calendarExtra);
        task.addProjectCollaboratorToTask(projectCollaborator3);

        taskCollaborator= new TaskCollaborator(projectCollaborator);
        report = new Report(taskCollaborator, Calendar.getInstance());
        report.setFirstDateOfReport(calendar1);


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
        collaborators = null;
        taskCollaborator=null;
        report=null;

    }


    /**
     * This test confirms that the method findEarliestCollaborator() and the method
     * findLatestCollaborator() will return, correspondingly the first and last project collaborator
     * that provided a task report
     */

    @Test
    public void testFindEarliestAndLatestCollaborators(){

        // GIVEN 3 collaborators active during the period of one report

        List<ProjectCollaborator> collaborators = new ArrayList<>();
        collaborators.add(projectCollaborator3);
        collaborators.add(projectCollaborator2);
        collaborators.add(projectCollaborator);


        //verify if projectCollaborator and projectCollaborator2 are identified as belonging to user
        assertEquals(user, projectCollaborator.getUserFromProjectCollaborator());
        assertEquals(user, projectCollaborator2.getUserFromProjectCollaborator());


        /**
         * tests if the method in interface FirstCollaboratorCost() correctly calculates the project cost
         * based on the first instance of the user
         */

        // WHEN the report cost is calculated according to the first active collaborator
        calculationFirst = new FirstCollaboratorCost();
        calculationFirst.updateCalculationMethod(report, collaborators);

        // THEN the report's cost must be updated to that value
        double costFirstUserInstance = projectCollaborator.getCostPerEffort();
        assertEquals(costFirstUserInstance, report.getCost(), 0.01);

        /**
         * tests if the method in interface LastCollaboratorCost() correctly calculates the project cost
         * based on the last instance of the user
         */


        // AND WHEN the report's cost is calculated according to the last collaborator instance
        calculationLast = new LastCollaboratorCost();
        calculationLast.updateCalculationMethod(report, collaborators);

        // THEN it must match collaborator 3's cost per effort
        double costLastUserInstance = projectCollaborator3.getCostPerEffort();
        assertEquals(costLastUserInstance, report.getCost(), 0.01);

        /**
         * tests if the method in interface FirstAndLastCollaboratorCost() correctly calculates the project cost
         * based on the first and last instance of the user (sum of costs, divided by the number of instances)
         */

        // AND WHEN the cost is calculated according to the average between teh first and last collaborator
        calculationFirstAndLAst = new FirstAndLastCollaboratorCost();
        calculationFirstAndLAst.updateCalculationMethod(report, collaborators);

        // THEN the report's cost must be updated to that value
        double costFirstAndLastInstance = (costFirstUserInstance + costLastUserInstance)/2;
        assertEquals(costFirstAndLastInstance, report.getCost(), 0.01);



        /**
         * tests if the method in interface AverageCollaboratorCost() correctly calculates the project cost
         * based on the average of first and last instance of the user
         */

        double costAverage = (costFirstUserInstance + costLastUserInstance + projectCollaborator2.getCostPerEffort())/3;

        // AND WHEN the cost is calculated according to the average
        calculationAverage = new AverageCollaboratorCost();
        calculationAverage.updateCalculationMethod(report, collaborators);

        // THEN the report's cost must equal the average between all collaborator instances
        assertEquals(costAverage, report.getCost(), 0.01);



        // AND WHEN the cost is reset to the first collaborator's cost
        calculationFirst = new FirstCollaboratorCost();
        calculationFirst.updateCalculationMethod(report, collaborators);

        // THEN the report's cost must return to its initial value
        assertEquals(costFirstUserInstance, report.getCost(), 0.01);


    }


}
