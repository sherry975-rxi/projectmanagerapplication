package project.model;

import org.junit.After;
import org.junit.Before;
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
	private Calendar estimatedFinishDate;
	private Calendar taskDeadline;

	@InjectMocks
    private
    Task task1;

	@Mock
    private
    TaskCollaborator taskWorker1;

    private Report report;

	private Calendar firstDateOfReport;
	private int timeToCompare;

	@Before
	public void setUp() {
		Mockito.when(taskWorker1.getCost()).thenReturn(1.0);

		// Set task start date at 14-January-2017
		estimatedStartDate = Calendar.getInstance();
		estimatedStartDate.set(2017, Calendar.JANUARY, 14);

		//Set task finish date at 10-March-2017
		estimatedFinishDate = Calendar.getInstance();
		estimatedFinishDate.set (2017, Calendar.MARCH, 10);

		//Set task deadline at 17-November-2017
		taskDeadline = Calendar.getInstance();
		taskDeadline.set(2017, Calendar.NOVEMBER, 17);
	}

	@After
	public void tearDown() {

		task1 = null;
		taskWorker1 = null;
		estimatedStartDate = null;
		estimatedFinishDate = null;
		taskDeadline = null;
		report = null;
		timeToCompare = 0;
		firstDateOfReport = null;
		report = null;
	}

	/**
	 * Tests the setters and getters
	 */

	@Test
	public void testSettersAndGetters() {

        // Creates a new Report instance
        report = new Report(taskWorker1, firstDateOfReport);

        firstDateOfReport= Calendar.getInstance();

		//Sets a time to the report
		report.setReportedTime(10);
		assertEquals(report.getReportedTime(), 10, 0.0);

		//Sets a task to the report
		report.setTask(task1);
		assertEquals(report.getTask(), task1);

		//Sets the first date to the report
		report.setFirstDateOfReport(firstDateOfReport);
		assertEquals(report.getFirstDateOfReport(), firstDateOfReport);

		//Sets a date to the update of the report (according to US203)
		report.setDateOfUpdate(firstDateOfReport);
		assertEquals(report.getDateOfUpdate(), firstDateOfReport);

		//Sets an Id to the report
		report.setId(2);
		assertEquals(report.getDbId(), 2);

		//Sets a Cost to the report
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

		// Creates a new int, with the expected value to be returned by getReportedTime
		// method
		timeToCompare = 20;

		// Sets the reportTime to 20;
		report.setReportedTime(20);

		// Compares the two values
		assertEquals(report.getReportedTime(), timeToCompare, 0.0);


		report.updateReportedTime(20);
		assertEquals(report.getReportedTime(), 20, 0);


	}

	/**
	 * Tests the getTaskWorker method in Report class
	 */
	@Test
	public void testGetTaskWorker() {

		// Creates a new report instance;
		report = new Report();
		report.setTaskCollaborator(taskWorker1);

		// Compares the two values
		assertEquals(report.getTaskCollaborator(), taskWorker1);
	}
}
