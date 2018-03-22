
package project.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.taskstateinterface.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Grupo 3
 *
 */
public class TaskTest {

	private User userTest;
	private Project projectTest;
	private Project projectTestSecond;
	private Task taskTest;
	private Task taskTestSecond;
	private Task taskReadyToFinishTest;
	private TaskCollaborator taskCollaborator;
	private ProjectCollaborator projectCollaborator;
	private Report report;

	@Before
	public void setUp() {

		// create user
		userTest = new User("Pedro Silva", "pedrosilva@gmail.com", "001", "Developer", "912364896");

		// create project
		projectTest = new Project("ProjectX", "Make x stuff", userTest);
		projectTest.setStartdate(Calendar.getInstance());
		projectTestSecond = new Project("ProjectZ", "Make z stuff", userTest);

		// create task
		taskTest = new Task("Make x", projectTest);
		taskReadyToFinishTest = new Task("Make j", projectTest);
		taskTestSecond = new Task("Make z", projectTest);

		// create project collaborator
		projectCollaborator = new ProjectCollaborator(userTest, 10);

		// create task collaborator
		taskCollaborator = new TaskCollaborator(projectCollaborator);

		// create report
		report = new Report(taskCollaborator, Calendar.getInstance());

		// Finish task

		taskReadyToFinishTest.setTaskID("1");

		taskReadyToFinishTest.addProjectCollaboratorToTask(projectCollaborator);

		taskReadyToFinishTest.setEstimatedTaskStartDate(Calendar.getInstance());

		taskReadyToFinishTest.setTaskDeadline(Calendar.getInstance());

		taskReadyToFinishTest.setEstimatedTaskEffort(1);

		taskReadyToFinishTest.setTaskBudget(1);

		taskReadyToFinishTest.setStartDate(Calendar.getInstance());

		// OnGoing task tasReadyToFinish

		// Finish task

		taskTestSecond.setTaskID("2");

		taskTestSecond.addProjectCollaboratorToTask(projectCollaborator);

		taskTestSecond.setEstimatedTaskStartDate(Calendar.getInstance());

		taskTestSecond.setTaskDeadline(Calendar.getInstance());

		taskTestSecond.setEstimatedTaskEffort(1);

		taskTestSecond.setTaskBudget(1);

		taskTestSecond.setStartDate(Calendar.getInstance());
	}

	@After
	public void tearDown() throws Exception {

		userTest = null;
		projectTest = null;
		projectTestSecond = null;
		taskTest = null;
		taskTestSecond = null;
		taskReadyToFinishTest = null;
		taskCollaborator = null;
		projectCollaborator = null;
		report = null;

	}

	/**
	 * Test method for {@link project.model.Task#hashCode()}.
	 */
	@Test
	public void testHashCode() {

		taskTest.setTaskID("1");
		taskTestSecond.setTaskID("2");

		assertFalse(taskTest.hashCode() == taskTestSecond.hashCode());

		int result = 3 * 31 + taskTest.getTaskID().hashCode();
		assertEquals(taskTest.hashCode(), result);
	}

	/**
	 * Test method for {@link project.model.Task#Task()}.
	 * 
	 * Empty Constructor ____
	 * 
	 */
	@Test
	public void testTask() {

		Task task = new Task();

		assertTrue(task instanceof Task);

	}

	/**
	 * Test method for
	 * {@link project.model.Task#Task(java.lang.String, project.model.Project)}.
	 * 
	 * Constructor ____
	 */
	@Test
	public void testTaskStringProject() {

		Task task = new Task("task test", projectTest);

		assertTrue(task instanceof Task);

	}

	/**
	 * Test method for {@link project.model.Task#Task(int, int, java.lang.String)}.
	 * 
	 * Constructor ____
	 */
	@Test
	public void testTaskIntIntString() {

		Task task = new Task(1, 1, "task test");

		assertTrue(task instanceof Task);

	}

	/**
	 * Test method for
	 * {@link project.model.Task#Task(java.lang.String, int, java.util.Calendar, java.util.Calendar, int)}.
	 * 
	 * Constructor to delete _____
	 */
	@Test
	public void testTaskStringIntCalendarCalendarInt() {

		Task task = new Task("task test", 1, Calendar.getInstance(), Calendar.getInstance(), 1);

		assertTrue(task instanceof Task);

	}

	/**
	 * Test method for
	 * {@link project.model.Task#Task(int, int, java.lang.String, int, java.util.Calendar, java.util.Calendar, int)}.
	 * 
	 * Constructor to delete_____
	 */
	@Test
	public void testTaskIntIntStringIntCalendarCalendarInt() {

		Task task = new Task(1, 1, "task test", 1, Calendar.getInstance(), Calendar.getInstance(), 1);

		assertTrue(task instanceof Task);

	}

	/**
	 * Test method for {@link project.model.Task#Task(project.model.Task)}.
	 * 
	 * Constructor ______
	 */
	@Test
	public void testTaskTask() {

		Task task = new Task(1, 1, "task test", 1, Calendar.getInstance(), Calendar.getInstance(), 1);

		Task taskCopy = new Task(task);

		assertTrue(taskCopy instanceof Task);

	}

	/**
	 * Test method for {@link project.model.Task#Task(project.model.Task)}.
	 * 
	 * Constructor ______
	 */
	@Test
	public void testTaskTaskWhithStartDateIntervalandDeadlineInterval() {

		taskReadyToFinishTest.setStartDateInterval(1);
		taskReadyToFinishTest.setDeadlineInterval(2);

		Task taskCopy = new Task(taskReadyToFinishTest);

		assertTrue(taskCopy instanceof Task);

	}

	/**
	 * Test method for {@link project.model.Task#getCurrentState()}.
	 */
	@Test
	public void testGetCurrentState() {

		StateEnum currentState = StateEnum.CREATED;

		taskTest.setCurrentState(currentState);

		assertEquals(currentState, taskTest.getCurrentState());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#setCurrentState(project.model.StateEnum)}.
	 */
	@Test
	public void testSetCurrentState() {

		StateEnum currentState = StateEnum.CREATED;

		taskTest.setCurrentState(currentState);

		assertEquals(currentState, taskTest.getCurrentState());
	}

	/**
	 * Test method for {@link project.model.Task#getId()}.
	 */
	@Test
	public void testGetId() {

		Long expected = 1L;

		taskTest.setId(expected);

		assertEquals(expected, taskTest.getId());

	}

	/**
	 * Test method for {@link project.model.Task#setId(java.lang.Long)}.
	 */
	@Test
	public void testSetId() {

		Long expected = 1L;

		taskTest.setId(expected);

		assertEquals(expected, taskTest.getId());
	}

	/**
	 * Test method for {@link project.model.Task#setProject(project.model.Project)}.
	 */
	@Test
	public void testSetProject() {

		taskTest.setProject(projectTestSecond);

		assertEquals(projectTestSecond, taskTest.getProject());
	}

	/**
	 * Test method for {@link project.model.Task#getProject()}.
	 */
	@Test
	public void testGetProject() {

		taskTest.setProject(projectTestSecond);

		assertEquals(projectTestSecond, taskTest.getProject());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskID}.
	 */
	@Test
	public void testSetTaskId() {

		taskTest.setTaskID("testTask");

		assertEquals("testTask", taskTest.getTaskID());

	}

	/**
	 * Test method for {@link project.model.Task#getStartDateInterval()}.
	 */
	@Test
	public void testGetStartDateInterval() {

		Integer expected = 10;

		taskReadyToFinishTest.setStartDateInterval(expected);

		assertEquals(expected, taskReadyToFinishTest.getStartDateInterval());
	}

	/**
	 * Test method for {@link project.model.Task#setStartDateInterval(int)}.
	 */
	@Test
	public void testSetStartDateInterval() {

		Integer expected = 10;

		taskReadyToFinishTest.setStartDateInterval(expected);

		assertEquals(expected, taskReadyToFinishTest.getStartDateInterval());

	}

	/**
	 * Test method for {@link project.model.Task#getDeadlineInterval()}.
	 */
	@Test
	public void testGetDeadlineInterval() {

		Integer expected = 10;

		taskReadyToFinishTest.setDeadlineInterval(expected);

		assertEquals(expected, taskReadyToFinishTest.getDeadlineInterval());
	}

	/**
	 * Test method for {@link project.model.Task#setDeadlineInterval(int)}.
	 */
	@Test
	public void testSetDeadlineInterval() {

		Integer expected = 10;

		taskReadyToFinishTest.setDeadlineInterval(expected);

		assertEquals(expected, taskReadyToFinishTest.getDeadlineInterval());
	}

	/**
	 * Test method for {@link project.model.Task#getEstimatedTaskEffort()}.
	 */
	@Test
	public void testGetEstimatedTaskEffort() {

		int expected = 10;

		taskTest.setEstimatedTaskEffort(expected);

		assertEquals(expected, taskTest.getEstimatedTaskEffort());
	}

	/**
	 * Test method for {@link project.model.Task#setEstimatedTaskEffort(int)}.
	 */
	@Test
	public void testSetEstimatedTaskEffort() {

		int expected = 10;

		taskTest.setEstimatedTaskEffort(expected);

		assertEquals(expected, taskTest.getEstimatedTaskEffort());
	}

	/**
	 * Test method for {@link project.model.Task#getEstimatedTaskStartDate()}.
	 */
	@Test
	public void testGetEstimatedTaskStartDate() {

		Calendar expected = Calendar.getInstance();

		taskTest.setEstimatedTaskStartDate(expected);

		assertEquals(expected, taskTest.getEstimatedTaskStartDate());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#setEstimatedTaskStartDate(java.util.Calendar)}.
	 */
	@Test
	public void testSetEstimatedTaskStartDate() {

		Calendar expected = Calendar.getInstance();

		taskTest.setEstimatedTaskStartDate(expected);

		assertEquals(expected, taskTest.getEstimatedTaskStartDate());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskDeadline()}.
	 */
	@Test
	public void testGetTaskDeadline() {

		Calendar expected = Calendar.getInstance();

		taskTest.setTaskDeadline(expected);

		assertEquals(expected, taskTest.getTaskDeadline());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#setTaskDeadline(java.util.Calendar)}.
	 */
	@Test
	public void testSetTaskDeadline() {

		Calendar expected = Calendar.getInstance();

		taskTest.setTaskDeadline(expected);

		assertEquals(expected, taskTest.getTaskDeadline());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskBudget()}.
	 */
	@Test
	public void testGetTaskBudget() {

		int expected = 100;

		taskTest.setTaskBudget(expected);

		assertEquals(expected, taskTest.getTaskBudget());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskBudget(int)}.
	 */
	@Test
	public void testSetTaskBudget() {

		int expected = 100;

		taskTest.setTaskBudget(expected);

		assertEquals(expected, taskTest.getTaskBudget());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskID()}.
	 */
	@Test
	public void testGetTaskID() {

		assertEquals(null, taskTest.getTaskID());
	}

	/**
	 * Test method for {@link project.model.Task#getStartDate()}.
	 */
	@Test
	public void testGetStartDate() {

		Calendar expected = Calendar.getInstance();

		taskTest.setStartDate(expected);

		assertEquals(expected, taskTest.getStartDate());
	}

	/**
	 * Test method for {@link project.model.Task#setStartDate(java.util.Calendar)}.
	 */
	@Test
	public void testSetStartDate() {

		Calendar expected = Calendar.getInstance();

		taskTest.setStartDate(expected);

		assertEquals(expected, taskTest.getStartDate());
	}

	/**
	 * Test method for {@link project.model.Task#getDescription()}.
	 */
	@Test
	public void testGetDescription() {

		assertFalse(taskTest.getDescription() == null);

	}

	/**
	 * Test method for {@link project.model.Task#getFinishDate()}.
	 */
	@Test
	public void testGetFinishDate() {

		taskTest.setFinishDate(Calendar.getInstance());

		assertFalse(taskTest.getFinishDate() == null);

	}

	/**
	 * Test method for {@link project.model.Task#setFinishDate(Calendar)}.
	 */
	@Test
	public void testSetFinishDate() {

		taskTest.setFinishDate(Calendar.getInstance());

		assertFalse(taskTest.getFinishDate() == null);

	}

	/**
	 * Test method for {@link project.model.Task#getTaskTeam()}.
	 */
	@Test
	public void testGetTaskTeam() {

		List<TaskCollaborator> expected = new ArrayList<>();
		expected.add(taskCollaborator);

		taskTest.setTaskTeam(expected);

		assertEquals(expected, taskTest.getTaskTeam());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskTeam(java.util.List)}.
	 */
	@Test
	public void testSetTaskTeam() {

		List<TaskCollaborator> expected = new ArrayList<>();
		expected.add(taskCollaborator);

		taskTest.setTaskTeam(expected);

		assertEquals(expected, taskTest.getTaskTeam());
	}

	/**
	 * Test method for {@link project.model.Task#getReports()}.
	 */
	@Test
	public void testGetReports() {

		List<Report> expected = new ArrayList<>();
		expected.add(report);

		taskTest.setReports(expected);

		assertEquals(expected, taskTest.getReports());
	}

	/**
	 * Test method for {@link project.model.Task#setReports(java.util.List)}.
	 */
	@Test
	public void testSetReports() {

		List<Report> expected = new ArrayList<>();
		expected.add(report);

		taskTest.setReports(expected);

		assertEquals(expected, taskTest.getReports());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskDependency()}.
	 */
	@Test
	public void testGetTaskDependency() {

		List<Task> expected = new ArrayList<>();
		expected.add(taskReadyToFinishTest);

		taskTest.setTaskDependency(expected);

		assertEquals(expected, taskTest.getTaskDependency());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskDependency(java.util.List)}.
	 */
	@Test
	public void testSetTaskDependency() {

		List<Task> expected = new ArrayList<>();
		expected.add(taskTestSecond);

		taskTest.setTaskDependency(expected);

		assertEquals(expected, taskTest.getTaskDependency());
	}

	/**
	 * Test method for {@link project.model.Task#isTaskFinished()}.
	 */
	@Test
	public void testIsTaskFinished() {

		taskReadyToFinishTest.markTaskAsFinished();

		assertTrue(taskReadyToFinishTest.isTaskFinished());
	}

	/**
	 * Test method for {@link project.model.Task#markTaskAsFinished()}.
	 */
	@Test
	public void testMarkTaskAsFinished() {

		taskReadyToFinishTest.addTaskCollaboratorToTask(taskCollaborator);

		assertTrue(taskReadyToFinishTest.doesTaskTeamHaveActiveUsers());

		assertFalse(taskReadyToFinishTest.isTaskFinished());

		taskReadyToFinishTest.markTaskAsFinished();

		assertTrue(taskReadyToFinishTest.isTaskFinished());

		assertFalse(taskReadyToFinishTest.doesTaskTeamHaveActiveUsers());

	}

	/**
	 * Test method for {@link project.model.Task#markTaskAsFinished()}.
	 */
	@Test
	public void testMarkTaskAsFinishedTaskStateFinished() {

		taskReadyToFinishTest.addTaskCollaboratorToTask(taskCollaborator);

		TaskStateInterface created = new Created();

		taskReadyToFinishTest.setTaskState(created);

		assertFalse(taskReadyToFinishTest.markTaskAsFinished());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#addProjectCollaboratorToTask(project.model.ProjectCollaborator)}.
	 */
	@Test
	public void testAddProjectCollaboratorToTask() {

		assertTrue(taskTest.getTaskTeam().isEmpty());

		taskTest.addProjectCollaboratorToTask(projectCollaborator);

		assertFalse(taskTest.getTaskTeam().isEmpty());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#addTaskCollaboratorToTask(project.model.TaskCollaborator)}.
	 */
	@Test
	public void testAddTaskCollaboratorToTask() {

		assertTrue(taskTest.getTaskTeam().isEmpty());

		taskTest.addTaskCollaboratorToTask(taskCollaborator);

		assertFalse(taskTest.getTaskTeam().isEmpty());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskCollaborator(project.model.ProjectCollaborator)}.
	 */
	@Test
	public void testCreateTaskCollaborator() {

		assertTrue(taskTest.getTaskTeam().isEmpty());

		taskTest.createTaskCollaborator(projectCollaborator);

		assertTrue(taskTest.getTaskTeam().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createReport(project.model.TaskCollaborator, java.util.Calendar, double)}.
	 */
	@Test
	public void testCreateReport() {

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.2);

		assertFalse(taskReadyToFinishTest.getReports().isEmpty());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#createReport(project.model.TaskCollaborator, java.util.Calendar, double)}.
	 */
	@Test
	public void testCreateReportTaskStateFinished() {

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());

		taskReadyToFinishTest.markTaskAsFinished();

		assertFalse(taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.2));

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#createReport(project.model.TaskCollaborator, java.util.Calendar, double)}.
	 */
	@Test
	public void testCreateReportTaskStateCancelled() {

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());

		taskReadyToFinishTest.cancelTask();

		assertFalse(taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.2));

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#createReport(project.model.TaskCollaborator, java.util.Calendar, double)}.
	 */
	@Test
	public void testCreateReportTaskStateStandBy() {

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());

		TaskStateInterface standBy = new StandBy();

		taskReadyToFinishTest.setTaskState(standBy);

		assertFalse(taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.2));

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#createReport(project.model.TaskCollaborator, java.util.Calendar, double)}.
	 */
	@Test
	public void testCreateReportWhitoutTaskCollaborator() {

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());

		taskReadyToFinishTest.removeAllCollaboratorsFromTaskTeam();

		assertFalse(taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.2));

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#createReport(project.model.TaskCollaborator, java.util.Calendar, double)}.
	 */
	@Test
	public void testCreateReportWhitTaskCollaborator() {

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());

		taskReadyToFinishTest.getTaskTeam().get(0).setFinishDate(Calendar.getInstance());

		assertFalse(taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.2));

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#getReportsIndexOfTaskCollaborator(java.lang.String)}.
	 */
	@Test
	public void testGetReportsIndexOfTaskCollaborator() {

		assertTrue(taskReadyToFinishTest
				.getReportsIndexOfTaskCollaborator(taskCollaborator.getTaskCollaborator().getEmail()).isEmpty());

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.1);

		assertFalse(taskReadyToFinishTest
				.getReportsIndexOfTaskCollaborator(taskCollaborator.getTaskCollaborator().getEmail()).isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#updateReportedTime(double, project.model.TaskCollaborator, int)}.
	 */
	@Test
	public void testUpdateReportedTime() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 2.0);

		assertEquals(2.0, taskReadyToFinishTest.getReports().get(0).getReportedTime(), 0.1);

		int reportToChange = taskReadyToFinishTest.getReports().get(0).getId();

		taskReadyToFinishTest.updateReportedTime(3.0, taskCollaborator, reportToChange);

		assertEquals(3.0, taskReadyToFinishTest.getReports().get(0).getReportedTime(), 0.1);

	}

	/**
	 * Test method for
	 * {@link project.model.Task#doesTaskHaveReportByGivenUser(java.lang.String)}.
	 */
	@Test
	public void testDoesTaskHaveReportByGivenUser() {

		assertFalse(
				taskReadyToFinishTest.doesTaskHaveReportByGivenUser(taskCollaborator.getTaskCollaborator().getEmail()));

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertTrue(
				taskReadyToFinishTest.doesTaskHaveReportByGivenUser(taskCollaborator.getTaskCollaborator().getEmail()));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getTaskCollaboratorByEmail(java.lang.String)}.
	 */
	@Test
	public void testGetTaskCollaboratorByEmail() {

		assertEquals(taskCollaborator,
				taskReadyToFinishTest.getTaskCollaboratorByEmail(taskCollaborator.getTaskCollaborator().getEmail()));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getActiveTaskCollaboratorByEmail(java.lang.String)}.
	 */
	@Test
	public void testGetActiveTaskCollaboratorByEmail() {

		assertEquals(taskCollaborator, taskReadyToFinishTest
				.getActiveTaskCollaboratorByEmail(taskCollaborator.getTaskCollaborator().getEmail()));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getReportedTimeByTaskCollaborator(java.lang.String)}.
	 */
	@Test
	public void testGetReportedTimeByTaskCollaborator() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertEquals(1.0, taskReadyToFinishTest
				.getReportedTimeByTaskCollaborator(taskCollaborator.getTaskCollaborator().getEmail()), 0.1);

	}

	/**
	 * Test method for {@link project.model.Task#getReporterName(java.lang.String)}.
	 */
	@Test
	public void testGetReporterName() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		String nameToCompare = taskCollaborator.getTaskCollaborator().getName();

		assertEquals(nameToCompare,
				taskReadyToFinishTest.getReporterName(taskCollaborator.getTaskCollaborator().getEmail()));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#removeProjectCollaboratorFromTask(project.model.ProjectCollaborator)}.
	 */
	@Test
	public void testRemoveProjectCollaboratorFromTask() {

		assertTrue(taskReadyToFinishTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));

		taskReadyToFinishTest.removeProjectCollaboratorFromTask(projectCollaborator);

		assertFalse(taskReadyToFinishTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));

	}

	/**
	 * Test method for {@link project.model.Task#getTimeSpentOntask()}.
	 */
	@Test
	public void testGetTimeSpentOntask() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertEquals(1.0, taskReadyToFinishTest.getTimeSpentOntask(), 0.1);

		taskReadyToFinishTest.updateReportedTime(2.0, taskCollaborator,
				taskReadyToFinishTest.getReports().get(0).getId());

		assertEquals(2.0, taskReadyToFinishTest.getTimeSpentOntask(), 0.1);

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getTimeSpentByProjectCollaboratorOntask(project.model.ProjectCollaborator)}.
	 */
	@Test
	public void testGetTimeSpentByProjectCollaboratorOntask() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertEquals(1.0, taskReadyToFinishTest.getTimeSpentByProjectCollaboratorOntask(projectCollaborator), 0.1);

	}

	/**
	 * Test method for {@link project.model.Task#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {

		assertTrue(taskTest.equals(taskTest));// same object

		Task testTask4 = null;

		assertFalse(taskTest.equals(testTask4));// null object

		assertFalse(taskTest.equals(userTest));// different classes

		Task testTask2 = new Task(1, 10, "Task 1");
		Task testTask1 = new Task(1, 20, "Task 2");

		assertFalse(testTask2.equals(testTask1));// different counter

		Task testTask3 = new Task(1, 1, "Task 1");
		testTask3.setTaskID("1");
		testTask2.setTaskID("1");

		assertTrue(testTask2.equals(testTask3));// same counter
	}

	/**
	 * Test method for
	 * {@link project.model.Task#isProjectCollaboratorInTaskTeam(project.model.ProjectCollaborator)}.
	 */
	@Test
	public void testIsProjectCollaboratorInTaskTeam() {

		assertFalse(taskTest.isProjectCollaboratorInTaskTeam(projectCollaborator));

		taskTest.addProjectCollaboratorToTask(projectCollaborator);

		assertTrue(taskTest.isProjectCollaboratorInTaskTeam(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isProjectCollaboratorActiveInTaskTeam(project.model.ProjectCollaborator)}.
	 */
	@Test
	public void testIsProjectCollaboratorActiveInTaskTeam() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertTrue(taskReadyToFinishTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));

		taskReadyToFinishTest.removeProjectCollaboratorFromTask(projectCollaborator);

		assertFalse(taskReadyToFinishTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));
	}

	/**
	 * Test method for {@link project.model.Task#doesTaskTeamHaveActiveUsers()}.
	 */
	@Test
	public void testDoesTaskTeamHaveActiveUsers() {

		assertFalse(taskTest.doesTaskTeamHaveActiveUsers());

		taskTest.addProjectCollaboratorToTask(projectCollaborator);

		assertTrue(taskTest.doesTaskTeamHaveActiveUsers());

	}

	/**
	 * Test method for {@link project.model.Task#isTaskTeamEmpty()}.
	 */
	@Test
	public void testIsTaskTeamEmpty() {

		assertTrue(taskTest.isTaskTeamEmpty());

		taskTest.addProjectCollaboratorToTask(projectCollaborator);

		assertFalse(taskTest.isTaskTeamEmpty());

	}

	/**
	 * Test method for {@link project.model.Task#getTaskCost()}.
	 */
	@Test
	public void testGetTaskCost() {

		taskTest.addProjectCollaboratorToTask(projectCollaborator);

		taskTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertEquals(10.0, taskTest.getTaskCost(), 0.1);

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskDependence(project.model.Task, int)}.
	 */
	@Test
	public void testCreateTaskDependence() {

		taskTest.setTaskID("3");

		assertFalse(taskTest.hasDependencies());

		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));

		assertTrue(taskTest.hasDependencies());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#removeTaskDependence(project.model.Task)}.
	 */
	@Test
	public void testRemoveTaskDependence() {

		taskTest.setTaskID("3");

		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));

		assertTrue(taskTest.hasDependencies());

		assertTrue(taskTest.removeTaskDependence(taskReadyToFinishTest));

		assertFalse(taskTest.hasDependencies());

	}

	/**
	 * Test method for {@link project.model.Task#hasActiveDependencies()}.
	 */
	@Test
	public void testHasActiveDependencies() {

		taskTest.setTaskID("3");

		assertFalse(taskTest.hasActiveDependencies());

		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));

		assertTrue(taskTest.hasActiveDependencies());

	}

	/**
	 * Test method for {@link project.model.Task#getTaskState()}.
	 */
	@Test
	public void testGetTaskState() {

		TaskStateInterface taskState = new Created();

		taskReadyToFinishTest.setTaskState(taskState);

		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Created);

	}

	/**
	 * Test method for {@link project.model.Task#viewTaskStateName()}.
	 */
	@Test
	public void testViewTaskStateName() {

		TaskStateInterface taskState = new Created();

		taskReadyToFinishTest.setTaskState(taskState);

		assertEquals("Created", taskReadyToFinishTest.viewTaskStateName());

	}

	/**
	 * Test method for {@link project.model.Task#viewTaskStateNameFromEnum()}.
	 */
	@Test
	public void testViewTaskStateNameFromEnum() {

		taskReadyToFinishTest.setCurrentState(StateEnum.CREATED);

		assertEquals("CREATED", taskReadyToFinishTest.viewTaskStateNameFromEnum());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#setTaskState(project.model.taskstateinterface.TaskStateInterface)}.
	 */
	@Test
	public void testSetTaskState() {

		assertTrue(taskReadyToFinishTest.getTaskState() instanceof OnGoing);

		TaskStateInterface taskState = new Created();

		taskReadyToFinishTest.setTaskState(taskState);

		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Created);

	}

	/**
	 * Test method for {@link project.model.Task#hasDependencies()}.
	 */
	@Test
	public void testHasDependencies() {

		taskTest.setTaskID("3");

		assertFalse(taskTest.hasDependencies());

		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));

		assertTrue(taskTest.hasDependencies());

	}

	/**
	 * Test method for {@link project.model.Task#removeFinishDate()}.
	 */
	@Test
	public void testRemoveFinishDate() {

		taskReadyToFinishTest.setFinishDate(Calendar.getInstance());

		assertFalse(taskReadyToFinishTest.getFinishDate() == null);

		taskReadyToFinishTest.removeFinishDate();

		assertEquals(null, taskReadyToFinishTest.getFinishDate());

	}

	/**
	 * Test method for {@link project.model.Task#cancelTask()}.
	 */
	@Test
	public void testCancelTask() {

		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

		assertEquals(null, taskReadyToFinishTest.getCancelDate());

		taskReadyToFinishTest.cancelTask();

		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

		assertFalse(taskReadyToFinishTest.getCancelDate() == null);

	}

	/**
	 * Test method for {@link project.model.Task#cancelTask()}.
	 */
	@Test
	public void testCancelTaskWhithTaskStateFinished() {

		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

		taskReadyToFinishTest.markTaskAsFinished();

		taskReadyToFinishTest.cancelTask();

		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

	}

	/**
	 * Test method for {@link project.model.Task#setCancelDate()}.
	 */
	@Test
	public void testSetCancelDate() {

		assertEquals(null, taskReadyToFinishTest.getCancelDate());

		taskReadyToFinishTest.setCancelDate();

		assertFalse(taskReadyToFinishTest.getCancelDate() == null);

	}

	/**
	 * Test method for {@link project.model.Task#getCancelDate()}.
	 */
	@Test
	public void testGetCancelDate() {

		assertEquals(null, taskReadyToFinishTest.getCancelDate());

		taskReadyToFinishTest.setCancelDate();

		assertFalse(taskReadyToFinishTest.getCancelDate() == null);

	}

	/**
	 * Test method for
	 * {@link project.model.Task#removeAllCollaboratorsFromTaskTeam()}.
	 */
	@Test
	public void testRemoveAllCollaboratorsFromTaskTeam() {

		assertFalse(taskTest.doesTaskTeamHaveActiveUsers());

		taskTest.addProjectCollaboratorToTask(projectCollaborator);

		assertTrue(taskTest.doesTaskTeamHaveActiveUsers());

		taskTest.removeAllCollaboratorsFromTaskTeam();

		assertFalse(taskTest.doesTaskTeamHaveActiveUsers());

	}

	/**
	 * Test method for {@link project.model.Task#cancelledDateClear()}.
	 */
	@Test
	public void testCancelledDateClear() {

		taskReadyToFinishTest.cancelTask();

		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

		taskReadyToFinishTest.cancelledDateClear();

		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

	}

	/**
	 * Test method for {@link project.model.Task#UnfinishTask()}.
	 */
	@Test
	public void testUnfinishTask() {

		taskReadyToFinishTest.markTaskAsFinished();

		assertTrue(taskReadyToFinishTest.isTaskFinished());

		taskReadyToFinishTest.addProjectCollaboratorToTask(projectCollaborator);

		assertTrue(taskReadyToFinishTest.UnfinishTask());

		assertFalse(taskReadyToFinishTest.isTaskFinished());

	}

	/**
	 * Test method for {@link project.model.Task#UnfinishTask()}.
	 */
	@Test
	public void testUnfinishTaskWhithTaskStateSetToOnGoing() {

		taskReadyToFinishTest.cancelTask();

		assertFalse(taskReadyToFinishTest.UnfinishTask());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getAssignementTaskTeamRequest(ProjectCollaborator)}.
	 */
	@Test
	public void testGetAssignementTaskTeamRequest() {

		taskReadyToFinishTest.createTaskAssignementRequest(projectCollaborator);

		assertTrue(taskReadyToFinishTest.getAssignementTaskTeamRequest(projectCollaborator).getProjCollab()
				.equals(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getRemovalTaskTeamRequest(ProjectCollaborator)}.
	 */
	@Test
	public void testGetRemovalTaskTeamRequest() {

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);

		assertEquals(projectCollaborator,
				taskReadyToFinishTest.getRemovalTaskTeamRequest(projectCollaborator).getProjCollab());

		taskReadyToFinishTest.deleteTaskRemovalRequest(projectCollaborator);

		assertEquals(null, taskReadyToFinishTest.getRemovalTaskTeamRequest(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#viewPendingTaskAssignementRequests()}.
	 */
	@Test
	public void testViewPendingTaskAssignementRequests() {

		assertTrue(taskReadyToFinishTest.viewPendingTaskAssignementRequests().isEmpty());

		taskReadyToFinishTest.createTaskAssignementRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.viewPendingTaskAssignementRequests().isEmpty());

	}

	/**
	 * Test method for {@link project.model.Task#viewPendingTaskRemovalRequests()}.
	 */
	@Test
	public void testViewPendingTaskRemovalRequests() {

		assertTrue(taskReadyToFinishTest.viewPendingTaskRemovalRequests().isEmpty());

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.viewPendingTaskRemovalRequests().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getPendingTaskAssignementRequests()}.
	 */
	@Test
	public void testGetPendingTaskAssignementRequests() {

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignementRequests().isEmpty());

		taskReadyToFinishTest.createTaskAssignementRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.getPendingTaskAssignementRequests().isEmpty());

	}

	/**
	 * Test method for {@link project.model.Task#getPendingTaskRemovalRequests()}.
	 */
	@Test
	public void testGetPendingTaskRemovalRequests() {

		assertTrue(taskReadyToFinishTest.getPendingTaskRemovalRequests().isEmpty());

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.getPendingTaskRemovalRequests().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskAssignementRequest(ProjectCollaborator)}.
	 */
	@Test
	public void testCreateTaskAssignementRequest() {

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignementRequests().isEmpty());

		taskReadyToFinishTest.createTaskAssignementRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.getPendingTaskAssignementRequests().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskAssignementRequest(ProjectCollaborator)}.
	 */
	@Test
	public void testCreateTaskAssignementRequestRequestsAlreadyCreated() {

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignementRequests().isEmpty());

		assertTrue(taskReadyToFinishTest.createTaskAssignementRequest(projectCollaborator));

		assertFalse(taskReadyToFinishTest.createTaskAssignementRequest(projectCollaborator));

		assertEquals(1, taskReadyToFinishTest.getPendingTaskAssignementRequests().size());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskRemovalRequest(ProjectCollaborator)}.
	 */
	@Test
	public void testCreateTaskRemovalRequest() {

		assertTrue(taskReadyToFinishTest.getPendingTaskRemovalRequests().isEmpty());

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.getPendingTaskRemovalRequests().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskRemovalRequest(ProjectCollaborator)}.
	 */
	@Test
	public void testCreateTaskRemovalRequestRequestsAlreadyCreated() {

		assertTrue(taskReadyToFinishTest.getPendingTaskRemovalRequests().isEmpty());

		assertTrue(taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator));

		assertFalse(taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator));

		assertEquals(1, taskReadyToFinishTest.getPendingTaskRemovalRequests().size());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isAssignmentRequestAlreadyCreated(ProjectCollaborator)}.
	 */
	@Test
	public void testIsAssignmentRequestAlreadyCreated() {

		assertFalse(taskReadyToFinishTest.isAssignmentRequestAlreadyCreated(projectCollaborator));

		taskReadyToFinishTest.createTaskAssignementRequest(projectCollaborator);

		assertTrue(taskReadyToFinishTest.isAssignmentRequestAlreadyCreated(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isRemovalRequestAlreadyCreated(ProjectCollaborator)}.
	 */
	@Test
	public void testIsRemovalRequestAlreadyCreated() {

		assertFalse(taskReadyToFinishTest.isRemovalRequestAlreadyCreated(projectCollaborator));

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);

		assertTrue(taskReadyToFinishTest.isRemovalRequestAlreadyCreated(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#deleteTaskRemovalRequest(ProjectCollaborator)}.
	 */
	@Test
	public void testDeleteTaskRemovalRequest() {

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);

		assertTrue(taskReadyToFinishTest.isRemovalRequestAlreadyCreated(projectCollaborator));

		taskReadyToFinishTest.deleteTaskRemovalRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.isRemovalRequestAlreadyCreated(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#deleteTaskAssignementRequest(ProjectCollaborator)}.
	 */
	@Test
	public void testDeleteTaskAssignementRequest() {

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignementRequests().isEmpty());

		taskReadyToFinishTest.createTaskAssignementRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.getPendingTaskAssignementRequests().isEmpty());

		taskReadyToFinishTest.deleteTaskAssignementRequest(projectCollaborator);

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignementRequests().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#removeAllRequestsWithASpecificTask()}.
	 */
	@Test
	public void testRemoveAllRequestsWithASpecificTask() {

		assertTrue(taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator));

		assertFalse(taskReadyToFinishTest.getPendingTaskTeamRequests().isEmpty());

		taskReadyToFinishTest.removeAllRequestsWithASpecificTask();

		assertTrue(taskReadyToFinishTest.getPendingTaskTeamRequests().isEmpty());

	}

	/**
	 * Test method for {@link project.model.Task#setPendingTaskTeamRequests(List)}.
	 */
	@Test
	public void testSetPendingTaskTeamRequests() {

		TaskTeamRequest request = new TaskTeamRequest();

		List<TaskTeamRequest> victim = new ArrayList<>();
		victim.add(request);

		taskReadyToFinishTest.setPendingTaskTeamRequests(victim);

		assertTrue(taskReadyToFinishTest.getPendingTaskTeamRequests().equals(victim));

	}

	/**
	 * Test method for {@link project.model.Task#getPendingTaskTeamRequests()}.
	 */
	@Test
	public void testGetPendingTaskTeamRequests() {

		TaskTeamRequest request = new TaskTeamRequest();

		List<TaskTeamRequest> victim = new ArrayList<>();
		victim.add(request);

		taskReadyToFinishTest.setPendingTaskTeamRequests(victim);

		assertTrue(taskReadyToFinishTest.getPendingTaskTeamRequests().equals(victim));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void testIsCreatingTaskDependencyValid() {

		TaskStateInterface created = new Created();

		taskTestSecond.setTaskState(created);

		assertTrue(taskTestSecond.isCreatingTaskDependencyValid(taskReadyToFinishTest));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void testIsCreatingTaskDependencyValidSameTask() {

		// Asserts false, because both tasks are the same
		assertFalse(taskReadyToFinishTest.isCreatingTaskDependencyValid(taskReadyToFinishTest));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void testIsCreatingTaskDependencyValidWithoutDeadline() {
		/*
		 * Its not possible to create a task dependency with a mother task without task
		 * deadline
		 */

		taskTestSecond.setTaskDeadline(null);
		assertFalse(taskReadyToFinishTest.isCreatingTaskDependencyValid(taskTestSecond));
	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void testIsCreatingTaskDependencyValidSetToOnGoing() {

		/*
		 * Its not possible to create a dependency to a daughter task that is set to
		 * onGoing
		 */
		assertFalse(taskTestSecond.isCreatingTaskDependencyValid(taskReadyToFinishTest));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void testIsCreatingTaskDependencyValidSetToCancelled() {
		/*
		 * Its not possible to create a dependency to a daughter task that is set to
		 * Cancelled
		 */
		taskTestSecond.cancelTask();

		assertFalse(taskTestSecond.isCreatingTaskDependencyValid(taskReadyToFinishTest));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void testIsCreatingTaskDependencyValidSetToReady() {
		/*
		 * Its not possible to create a dependency to a daughter task that is set to
		 * Ready
		 */
		TaskStateInterface ready = new Ready();

		taskTestSecond.setTaskState(ready);
		assertFalse(taskTestSecond.isCreatingTaskDependencyValid(taskReadyToFinishTest));
	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void testIsCreatingTaskDependencyValidSetToCancel() {
		/*
		 * Its not possible to create a dependency to a daughter task that is set to
		 * Cancelled
		 */
		taskTestSecond.cancelTask();

		assertFalse(taskTestSecond.isCreatingTaskDependencyValid(taskReadyToFinishTest));
	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void testIsCreatingTaskDependencyValidSetToStandBy() {
		/*
		 * Its not possible to create a dependency to task that is set to Standby
		 */
		TaskStateInterface standBy = new StandBy();
		taskTestSecond.setTaskState(standBy);

		assertFalse(taskTestSecond.isCreatingTaskDependencyValid(taskReadyToFinishTest));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void isCreatingTaskDependencyValidSetToFinished() {

		/*
		 * Its not possible to create a dependency to a daughter task that is set to
		 * Finished
		 */
		taskTestSecond.markTaskAsFinished();

		assertFalse(taskTestSecond.isCreatingTaskDependencyValid(taskReadyToFinishTest));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void isCreatingTaskDependencyValidSetToFinishedOtherTaskSetToCancelled() {

		/*
		 * Its not possible to create a dependency to a daughter task that is set to
		 * Cancelled
		 */
		taskTestSecond.cancelTask();

		assertFalse(taskReadyToFinishTest.isCreatingTaskDependencyValid(taskTestSecond));
	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void testIsCreatingTaskDependencyValidWithTheSameDependency() {

		TaskStateInterface created = new Created();

		taskTestSecond.setTaskState(created);

		assertTrue(taskTestSecond.createTaskDependence(taskReadyToFinishTest, 1));

		assertFalse(taskTestSecond.isCreatingTaskDependencyValid(taskReadyToFinishTest));

	}

}
