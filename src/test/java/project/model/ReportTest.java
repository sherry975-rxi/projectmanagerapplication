package project.model;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportTest {

	User u1;
	ProjectCollaborator projectCollaborator1;
	Calendar estimatedStartDate;
	Calendar taskDeadline;
	Task t1;
	TaskCollaborator taskWorker1;
	Project p1;
	TaskRepository taskRepository;
	Report report;
	int timeToCompare;

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
	}

	@After
	public void tearDown() {

		u1 = null;
		t1 = null;
		taskWorker1 = null;
		p1 = null;
		projectCollaborator1 = null;
		taskRepository = null;
		report = null;
		timeToCompare = 0;
	}

	/**
	 * Tests constructor for Report
	 */
	@Test
	public void testCreateReport() {

		// Creates a new Report instance
		report = new Report(taskWorker1);

	}

	/**
	 * Tests the set and get reported time methods in Report class
	 */
	@Test
	public void testGetReportedTime() {

		// Creates a new report instance;
		report = new Report(taskWorker1);

		// Creates a new int, with the expected value to be returnet by getReportedTime
		// method
		timeToCompare = 20;

		// Sets the reportTime to 20;
		report.setReportedTime(20);

		// Compares the two values
		assertEquals(report.getReportedTime(), timeToCompare);

	}

	/**
	 * Tests the getTaskWorker method in Report class
	 */
	@Test
	public void testGetTaskWorker() {

		// Creates a new report instance;
		report = new Report(taskWorker1);

		// Compares the two values
		assertEquals(report.getTaskCollaborator(), taskWorker1);
	}
}
