package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ReportTest {

	private Calendar estimatedStartDate;
	private Calendar taskDeadline;

	@InjectMocks
    private
    Task task1;

	@Mock
    private
    TaskCollaborator taskWorker1;

    private Report report;

	private Calendar dateOfReport;
	private int timeToCompare;

	@Before
	public void setUp() {
		Mockito.when(taskWorker1.getCost()).thenReturn(1);
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);
	}

	@After
	public void tearDown() {

		task1 = null;
		taskWorker1 = null;
		estimatedStartDate = null;
		taskDeadline = null;
		report = null;
		timeToCompare = 0;
		dateOfReport = null;
		report = null;
	}

	/**
	 * Tests the setters and getters
	 */
	@Ignore
	@Test
	public void testSettersAndGetters() {

        // Creates a new Report instance
        report = new Report(taskWorker1, dateOfReport);

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
