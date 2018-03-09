package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

public class ReportTest {

	User u1;
	ProjectCollaborator projectCollaborator1;
	Calendar estimatedStartDate;
	Calendar taskDeadline;
	Task t1;
	TaskCollaborator taskWorker1;
	Project p1;
	TaskContainer taskContainer;
	Report report;
	Calendar dateOfReport;
	Task task1;
	int timeToCompare;
	Report report2;

	@Before
	public void setUp() {

		u1 = new User("name", "email", "idNumber", "function", "123456789");
		projectCollaborator1 = new ProjectCollaborator(u1, 1200);
		taskWorker1 = new TaskCollaborator(projectCollaborator1);
		p1 = new Project(1, "name3", "description4", u1);
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);
		t1 = p1.getTaskRepository().createTask("description", 0, estimatedStartDate, taskDeadline, 0);
		p1.getTaskRepository().addProjectTask(t1);
		timeToCompare = 0;
		dateOfReport = Calendar.getInstance();
		task1 = new Task();
	}

	@After
	public void tearDown() {

		u1 = null;
		t1 = null;
		taskWorker1 = null;
		p1 = null;
		projectCollaborator1 = null;
		taskContainer = null;
		report = null;
		timeToCompare = 0;
		dateOfReport = null;
		report = null;
		report2 = null;
	}

	/**
	 * Tests constructor for Report
	 */
	@Test
	public void testCreateReport() {

		// Creates a new Report instance
		report2 = new Report(taskWorker1, dateOfReport);

	}

	/**
	 * Tests the setters and getters
	 */
	@Test
	public void testSettersAndGetters() {
		report = new Report();

		// Creates a new Report instance
		report.setTaskCollaborator(taskWorker1);
		report.setDateOfReport(dateOfReport);

		//Sets a time to the report
		report.setReportedTime(10);
		assertEquals(report.getReportedTime(), 10, 0.0);

		//Sets a task to the report
		report.setTask(task1);
		assertEquals(report.getTask(), task1);

		//Sets a date to the report
		report.setDateOfReport(Calendar.getInstance());
		assertEquals(report.getDateOfReport(), Calendar.getInstance());

		//Sets a date to the update
		report.setDateOfUpdate(Calendar.getInstance());
		assertEquals(report.getDateOfUpdate(), Calendar.getInstance());

		//Sets an Id
		report.setId(2);
		assertEquals(report.getId(), 2);

		//Sets a Cost
		report.setCost(10);
		assertEquals(report.getCost(), 10, 0.0);

		//Sets a Task Collaborator to the task
		report.setTaskCollaborator(taskWorker1);
		assertEquals(report.getTaskCollaborator(), taskWorker1);


	}

	/**
	 * Tests the set and get reported time methods in Report class
	 */
	@Test
	public void testGetReportedTime() {
		report = new Report();

		// Creates a new report instance;
		report.setTaskCollaborator(taskWorker1);
		report.setDateOfReport(dateOfReport);
		// Creates a new int, with the expected value to be returned by getReportedTime
		// method
		timeToCompare = 20;

		// Sets the reportTime to 20;
		report.setReportedTime(20);

		// Compares the two values
		assertEquals(report.getReportedTime(), timeToCompare, 0.0);


		report.updateReportedTime(20);
		assertEquals(report.getReportedTime(), 20, 0);
		;

	}

	/**
	 * Tests the getTaskWorker method in Report class
	 */
	@Test
	public void testGetTaskWorker() {

		// Creates a new report instance;
		report = new Report();
		report.setTaskCollaborator(taskWorker1);
		report.setDateOfReport(dateOfReport);

		// Compares the two values
		assertEquals(report.getTaskCollaborator(), taskWorker1);
	}
}
