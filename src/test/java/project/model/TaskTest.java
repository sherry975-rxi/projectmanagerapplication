
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
	private TaskCollaborator taskCollaborator2;
	private ProjectCollaborator projectCollaborator;
	private ProjectCollaborator projectCollaborator2;
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
		projectCollaborator2 = new ProjectCollaborator(userTest, 20);

		// create task collaborator
		taskCollaborator = new TaskCollaborator(projectCollaborator);
		taskCollaborator2 = new TaskCollaborator(projectCollaborator2);

		// create report
		report = new Report(taskCollaborator, Calendar.getInstance());


		// Finish task

		taskReadyToFinishTest.setTaskID("1");

		taskReadyToFinishTest.addProjectCollaboratorToTask(projectCollaborator);

		taskReadyToFinishTest.setEstimatedTaskStartDate(Calendar.getInstance());

		taskReadyToFinishTest.setTaskDeadline(Calendar.getInstance());

		taskReadyToFinishTest.setEstimatedTaskEffort(1);

		taskReadyToFinishTest.setTaskBudget(1);

		taskReadyToFinishTest.setStartDateAndState(Calendar.getInstance());

		// OnGoing task taskReadyToFinish

		// Finish task

		taskTestSecond.setTaskID("2");

		taskTestSecond.addProjectCollaboratorToTask(projectCollaborator);

		taskTestSecond.setEstimatedTaskStartDate(Calendar.getInstance());

		taskTestSecond.setTaskDeadline(Calendar.getInstance());

		taskTestSecond.setEstimatedTaskEffort(1);

		taskTestSecond.setTaskBudget(1);

		taskTestSecond.setStartDateAndState(Calendar.getInstance());
	}

	@After
	public void tearDown() {

		userTest = null;
		projectTest = null;
		projectTestSecond = null;
		taskTest = null;
		taskTestSecond = null;
		taskReadyToFinishTest = null;
		taskCollaborator = null;
		taskCollaborator2 = null;
		projectCollaborator = null;
		projectCollaborator2 = null;
		report = null;
	}

	/**
	 * Test method for {@link project.model.Task#hashCode()}.
	 */
	@Test
	public void shouldHaveSameHashCode() {

		taskTest.setTaskID("1");
		taskTestSecond.setTaskID("2");

		int result = 3 * 31 + taskTest.getTaskID().hashCode();
		assertEquals(taskTest.hashCode(), result);
	}

	/**
	 * Test method for {@link project.model.Task#hashCode()}.
	 */
	@Test
	public void shouldHaveDifferentHashCode() {

		taskTest.setTaskID("1");
		taskTestSecond.setTaskID("2");

		assertFalse(taskTest.hashCode() == taskTestSecond.hashCode());

	}

	/**
	 * Test method for {@link project.model.Task#Task()}.
	 * 
	 * Empty Constructor ____
	 * 
	 */
	@Test
	public void shouldCreateInstanceOfTask() {

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
	public void shouldCreateInstanceOfTaskWithDefaultParameters() {

		Task task = new Task("task test", projectTest);

		assertTrue(task instanceof Task);

	}

	/**
	 * Test method for {@link project.model.Task#Task(int, int, java.lang.String)}.
	 * 
	 * Constructor ____
	 */
	@Test
	public void shouldCreateInstanceOfTaskWithOtherDefaultParameters() {

		Task task = new Task(1, 1, "task test");

		assertTrue(task instanceof Task);

	}


	/**
	 * Test method for {@link project.model.Task#getCurrentState()}.
	 */
	@Test
	public void shouldGetCurrentStateCreated() {

		StateEnum currentState = StateEnum.CREATED;

		taskTest.setCurrentState(currentState);

		assertEquals(currentState, taskTest.getCurrentState());

	}

	/**
	 * Test method for {@link project.model.Task#getDbTaskId()}.
	 */
	@Test
	public void shouldGetTaskId() {

		Long expected = 1L;

		taskTest.setDbTaskId(expected);

		assertEquals(expected, taskTest.getDbTaskId());

	}

	/**
	 * Test method for {@link project.model.Task#setDbTaskId(java.lang.Long)}.
	 */
	@Test
	public void shouldSetTaskId() {

		Long expected = 1L;

		taskTest.setDbTaskId(expected);

		assertEquals(expected, taskTest.getDbTaskId());
	}

	/**
	 * Test method for {@link project.model.Task#setProject(project.model.Project)}.
	 */
	@Test
	public void shouldSetProjectinTask() {

		taskTest.setProject(projectTestSecond);

		assertEquals(projectTestSecond, taskTest.getProject());
	}

	/**
	 * Test method for {@link project.model.Task#getProject()}.
	 */
	@Test
	public void shouldGetProjectfromTask() {

		taskTest.setProject(projectTestSecond);

		assertEquals(projectTestSecond, taskTest.getProject());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskID}.
	 */
	@Test
	public void shouldSetTaskIDFromanotherTask() {

		taskTest.setTaskID("testTask");

		assertEquals("testTask", taskTest.getTaskID());

	}

	/**
	 * Test method for {@link project.model.Task#getStartDateInterval()}.
	 */
	@Test
	public void shouldGetStartDateInterval() {

		Integer expected = 10;

		taskReadyToFinishTest.setStartDateInterval(expected);

		assertEquals(expected, taskReadyToFinishTest.getStartDateInterval());
	}

	/**
	 * Test method for {@link project.model.Task#updateStartDateIntervalAndState(int)}.
	 */
	@Test
	public void shouldSetStartDateInterval() {

		Integer expected = 10;

		taskReadyToFinishTest.setStartDateInterval(expected);

		assertEquals(expected, taskReadyToFinishTest.getStartDateInterval());

	}

	/**
	 * Test method for {@link project.model.Task#getDeadlineInterval()}.
	 */
	@Test
	public void shouldGetDeadlineInterval() {

		Integer expected = 10;

		taskReadyToFinishTest.setDeadlineInterval(expected);

		assertEquals(expected, taskReadyToFinishTest.getDeadlineInterval());
	}

	/**
	 * Test method for {@link project.model.Task#updateDeadlineIntervalAndState(int)}.
	 */
	@Test
	public void shlouldSetDeadlineInterval() {

		Integer expected = 10;

		taskReadyToFinishTest.setDeadlineInterval(expected);

		assertEquals(expected, taskReadyToFinishTest.getDeadlineInterval());
	}

	/**
	 * Test method for {@link project.model.Task#getEstimatedTaskEffort()}.
	 */
	@Test
	public void shouldGetEstimatedTaskEffort() {

		int expected = 10;

		taskTest.setEstimatedTaskEffort(expected);

		assertEquals(expected, taskTest.getEstimatedTaskEffort(), 0.001);
	}

	/**
	 * Test method for {@link project.model.Task#setEstimatedTaskEffort(double)}.
	 */
	@Test
	public void shouldSetEstimatedTaskEffort() {

		taskTest.setEstimatedTaskStartDate(Calendar.getInstance());
		taskTest.setTaskDeadline(Calendar.getInstance());
		taskTest.addProjectCollaboratorToTask(projectCollaborator);
		taskTest.setTaskBudget(10);

		double expected = 10.0;

		taskTest.setEstimatedTaskEffort(expected);


		assertEquals(expected, taskTest.getEstimatedTaskEffort(), 0.01);
		assertEquals("Ready", taskTest.viewTaskStateName());

	}

	/**
	 * Test method for {@link project.model.Task#getEstimatedTaskStartDate()}.
	 */
	@Test
	public void shouldGetEstimatedTaskStartDate() {

		Calendar expected = Calendar.getInstance();

		taskTest.setEstimatedTaskStartDate(expected);

		assertEquals(expected, taskTest.getEstimatedTaskStartDate());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#setEstimatedTaskStartDate(java.util.Calendar)}.
	 */
	@Test
	public void shouldSetEstimatedTaskStartDate() {

		Calendar expected = Calendar.getInstance();

		taskTest.setEstimatedTaskStartDate(expected);

		assertEquals(expected, taskTest.getEstimatedTaskStartDate());
		assertEquals("Planned", taskTest.viewTaskStateName());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskDeadline()}.
	 */
	@Test
	public void shouldGetTaskDeadline() {

		Calendar expected = Calendar.getInstance();

		taskTest.setTaskDeadline(expected);

		assertEquals(expected, taskTest.getTaskDeadline());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#setTaskDeadline(java.util.Calendar)}.
	 */
	@Test
	public void shouldSetTaskDeadline() {

		Calendar expected = Calendar.getInstance();

		taskTest.setTaskDeadline(expected);

		assertEquals(expected, taskTest.getTaskDeadline());
		assertEquals("Planned", taskTest.viewTaskStateName());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskBudget()}.
	 */
	@Test
	public void shouldGetTaskBudget() {

		int expected = 100;

		taskTest.setTaskBudget(expected);

		assertEquals(expected, taskTest.getTaskBudget(), 0.01);
	}

	/**
	 * Test method for {@link project.model.Task#setTaskBudget(double)}.
	 */
	@Test
	public void shouldSetTaskBudget() {

		taskTest.setEstimatedTaskStartDate(Calendar.getInstance());
		taskTest.setTaskDeadline(Calendar.getInstance());
		taskTest.addProjectCollaboratorToTask(projectCollaborator);
		taskTest.setEstimatedTaskEffort(10);
		assertEquals("Planned", taskTest.viewTaskStateName());

		double expected = 100.0;

		taskTest.setTaskBudget(expected);

		assertEquals(expected, taskTest.getTaskBudget(), 0.01);
		assertEquals("Ready", taskTest.viewTaskStateName());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskID()}.
	 */
	@Test
	public void shouldGetTaskID() {

		assertEquals(null, taskTest.getTaskID());
	}

	/**
	 * Test method for {@link project.model.Task#getStartDate()}.
	 */
	@Test
	public void shouldGetStartDate() {

		Calendar expected = Calendar.getInstance();

		taskTest.setStartDateAndState(expected);

		assertEquals(expected, taskTest.getStartDate());
	}

	/**
	 * Test method for {@link project.model.Task#setStartDateAndState(java.util.Calendar)}.
	 */
	@Test
	public void shouldSetStartDate() {

		taskTest.setEstimatedTaskStartDate(Calendar.getInstance());
		taskTest.setTaskDeadline(Calendar.getInstance());
		taskTest.addProjectCollaboratorToTask(projectCollaborator);
		taskTest.setEstimatedTaskEffort(10);
		taskTest.setTaskBudget(10);

		Calendar expected = Calendar.getInstance();

		taskTest.setStartDateAndState(expected);

		assertEquals(expected, taskTest.getStartDate());
		assertEquals("OnGoing", taskTest.viewTaskStateName());
	}

	/**
	 * Test method for {@link project.model.Task#getDescription()}.
	 */
	@Test
	public void shouldGetDescription() {

		assertFalse(taskTest.getDescription() == null);

	}

	/**
	 * Test method for {@link project.model.Task#getFinishDate()}.
	 */
	@Test
	public void shouldGetFinishDate() {

		taskTest.setFinishDate(Calendar.getInstance());

		assertFalse(taskTest.getFinishDate() == null);

	}

	/**
	 * Test method for {@link project.model.Task#setFinishDate(Calendar)}.
	 */
	@Test
	public void shouldSetFinishDate() {



		taskTest.setFinishDate(Calendar.getInstance());

		assertFalse(taskTest.getFinishDate() == null);

	}

	/**
	 * Test method for {@link project.model.Task#getTaskTeam()}.
	 */
	@Test
	public void shouldGetTaskTeam() {

		List<TaskCollaborator> expected = new ArrayList<>();
		expected.add(taskCollaborator);

		taskTest.setTaskTeam(expected);

		assertEquals(expected, taskTest.getTaskTeam());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskTeam(java.util.List)}.
	 */
	@Test
	public void shouldSetTaskTeam() {

		List<TaskCollaborator> expected = new ArrayList<>();
		expected.add(taskCollaborator);

		taskTest.setTaskTeam(expected);

		assertEquals(expected, taskTest.getTaskTeam());
	}

	/**
	 * Test method for {@link project.model.Task#getReports()}.
	 */
	@Test
	public void shouldGetReports() {

		List<Report> expected = new ArrayList<>();
		expected.add(report);

		taskTest.setReports(expected);

		assertEquals(expected, taskTest.getReports());
	}

	/**
	 * Test method for {@link project.model.Task#setReports(java.util.List)}.
	 */
	@Test
	public void shouldSetReports() {

		List<Report> expected = new ArrayList<>();
		expected.add(report);

		taskTest.setReports(expected);

		assertEquals(expected, taskTest.getReports());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskDependency()}.
	 */
	@Test
	public void shouldGetTaskDependency() {

		List<Task> expected = new ArrayList<>();
		expected.add(taskReadyToFinishTest);

		taskTest.setTaskDependency(expected);

		assertEquals(expected, taskTest.getTaskDependency());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskDependency(java.util.List)}.
	 */
	@Test
	public void shouldSetTaskDependency() {

		List<Task> expected = new ArrayList<>();
		expected.add(taskTestSecond);

		taskTest.setTaskDependency(expected);

		assertEquals(expected, taskTest.getTaskDependency());
	}

	/**
	 * Test method for {@link project.model.Task#isTaskFinished()}.
	 */
	@Test
	public void shloudIsTaskFinished() {

		taskReadyToFinishTest.markTaskAsFinished();

		assertTrue(taskReadyToFinishTest.isTaskFinished());
	}

	/**
	 * Test method for {@link project.model.Task#markTaskAsFinished()}.
	 */
	@Test
	public void shouldMarkTaskAsFinished() {

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
	public void shouldMarkTaskAsFinishedTaskStateFinished() {

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
	public void shouldAddProjectCollaboratorToTask() {

		assertTrue(taskTest.getTaskTeam().isEmpty());
		assertEquals("Created", taskTest.viewTaskStateName());

		assertTrue(taskTest.addProjectCollaboratorToTask(projectCollaborator));


		assertFalse(taskTest.getTaskTeam().isEmpty());
		assertEquals("Planned", taskTest.viewTaskStateName());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#addTaskCollaboratorToTask(project.model.TaskCollaborator)}.
	 */
	@Test
	public void shouldTaskCollaboratorToTask() {

		assertTrue(taskTest.getTaskTeam().isEmpty());

		assertTrue(taskTest.addTaskCollaboratorToTask(taskCollaborator));

		assertFalse(taskTest.getTaskTeam().isEmpty());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskCollaborator(project.model.ProjectCollaborator)}.
	 */
	@Test
	public void shouldCreateTaskCollaborator() {

		assertTrue(taskTest.getTaskTeam().isEmpty());

		TaskCollaborator collaborator = taskTest.createTaskCollaborator(projectCollaborator);

		assertTrue(taskTest.getTaskTeam().isEmpty());
		assertTrue(taskTest.equals(collaborator.getTask()));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createReport(project.model.TaskCollaborator, java.util.Calendar, double)}.
	 */
	@Test
	public void shouldCreateReport() {

		assertTrue(taskReadyToFinishTest.getReports().isEmpty());

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.2);

		assertFalse(taskReadyToFinishTest.getReports().isEmpty());
		Report report = taskReadyToFinishTest.getReports().get(0);
		assertTrue(taskReadyToFinishTest.equals(report.getTask()));
	}

	/**
	 * Test method for
	 * {@link project.model.Task#createReport(project.model.TaskCollaborator, java.util.Calendar, double)}.
	 */
	@Test
	public void shouldNotCreateReportIfTaskStateFinished() {

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
	public void shouldNotCreateReportIfTaskStateCancelled() {

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
	public void shouldNotCreateReportIfTaskStateStandBy() {

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
	public void shouldNotCreateReportIfTaskWithoutTaskCollaborator() {

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
	public void shouldCreateReportIfTaskWithTaskCollaborator() {

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
	public void shouldGetReportsIndexOfTaskCollaborator() {

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
	public void shoudUpdateReportedTime() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 2.0);

		assertEquals(2.0, taskReadyToFinishTest.getReports().get(0).getReportedTime(), 0.1);

		int reportToChange = taskReadyToFinishTest.getReports().get(0).getDbId();

		taskReadyToFinishTest.updateReportedTime(3.0, taskCollaborator, reportToChange);

        assertEquals(3.0, taskReadyToFinishTest.getReports().get(0).getReportedTime(), 0.1);

		assertFalse(taskReadyToFinishTest.updateReportedTime(5.0, taskCollaborator, -7));

        assertFalse(taskReadyToFinishTest.updateReportedTime(5.0, taskCollaborator, taskReadyToFinishTest.getReports().size()));

        assertTrue(taskReadyToFinishTest.updateReportedTime(5.0, taskCollaborator, 0));
	}

	/**
	 * Test method for
	 * {@link project.model.Task#updateReportedTime(double, project.model.TaskCollaborator, int)}.
	 */
	@Test
	public void shoudNotUpdateReportedTimeIfReportIdDoesNotExist() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 2.0);

		assertEquals(2.0, taskReadyToFinishTest.getReports().get(0).getReportedTime(), 0.1);

		int reportToChange = taskReadyToFinishTest.getReports().get(0).getDbId();

		taskReadyToFinishTest.updateReportedTime(3.0, taskCollaborator, reportToChange);

		assertEquals(3.0, taskReadyToFinishTest.getReports().get(0).getReportedTime(), 0.1);

		assertFalse(taskReadyToFinishTest.updateReportedTime(5.0, taskCollaborator, -7));
	}

	/**
	 * Test method for
	 * {@link project.model.Task#updateReportedTime(double, project.model.TaskCollaborator, int)}.
	 */
	@Test
	public void shoudNotUpdateReportedTimeIfReportDoesNotExist() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 2.0);

		assertEquals(2.0, taskReadyToFinishTest.getReports().get(0).getReportedTime(), 0.1);

		int reportToChange = taskReadyToFinishTest.getReports().get(0).getDbId();

		taskReadyToFinishTest.updateReportedTime(3.0, taskCollaborator, reportToChange);

		assertEquals(3.0, taskReadyToFinishTest.getReports().get(0).getReportedTime(), 0.1);

		assertFalse(taskReadyToFinishTest.updateReportedTime(5.0, taskCollaborator, taskReadyToFinishTest.getReports().size()));
	}

	/**
	 * Test method for
	 * {@link project.model.Task#doesTaskHaveReportByGivenUser(java.lang.String)}.
	 */
	@Test
	public void shouldNotTaskHaveReportByGivenUser() {

		assertFalse(
				taskReadyToFinishTest.doesTaskHaveReportByGivenUser(taskCollaborator.getTaskCollaborator().getEmail()));

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertTrue(
				taskReadyToFinishTest.doesTaskHaveReportByGivenUser(taskCollaborator.getTaskCollaborator().getEmail()));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#doesTaskHaveReportByGivenUser(java.lang.String)}.
	 */
	@Test
	public void shouldTaskHaveReportByGivenUser() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertTrue(
				taskReadyToFinishTest.doesTaskHaveReportByGivenUser(taskCollaborator.getTaskCollaborator().getEmail()));

	}


	/**
	 * Test method for
	 * {@link project.model.Task#getTaskCollaboratorByEmail(java.lang.String)}.
	 */
	@Test
	public void shouldGetTaskCollaboratorByEmail() {

		assertEquals(taskCollaborator,
				taskReadyToFinishTest.getTaskCollaboratorByEmail(taskCollaborator.getTaskCollaborator().getEmail()));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getActiveTaskCollaboratorByEmail(java.lang.String)}.
	 */
	@Test
	public void shouldGetActiveTaskCollaboratorByEmail() {

		assertEquals(taskCollaborator, taskReadyToFinishTest
				.getActiveTaskCollaboratorByEmail(taskCollaborator.getTaskCollaborator().getEmail()));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getReportedTimeByTaskCollaborator(java.lang.String)}.
	 */
	@Test
	public void shouldGetReportedTimeByTaskCollaborator() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertEquals(1.0, taskReadyToFinishTest
				.getReportedTimeByTaskCollaborator(taskCollaborator.getTaskCollaborator().getEmail()), 0.1);

	}

	/**
	 * Test method for {@link project.model.Task#getReporterName(java.lang.String)}.
	 */
	@Test
	public void shouldGetReporterName() {

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
	public void shouldRemoveProjectCollaboratorFromTask() {

		taskTest.setEstimatedTaskStartDate(Calendar.getInstance());
		taskTest.setTaskDeadline(Calendar.getInstance());
		taskTest.addProjectCollaboratorToTask(projectCollaborator);
		taskTest.setEstimatedTaskEffort(10);
		taskTest.setTaskBudget(10);
        assertEquals("Ready", taskTest.viewTaskStateName());

		assertTrue(taskTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));

		assertTrue(taskTest.removeProjectCollaboratorFromTask(projectCollaborator));
		assertEquals("Planned", taskTest.viewTaskStateName());

		assertFalse(taskTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));


	}

	/**
	 * Test method for {@link project.model.Task#getTimeSpentOnTask()}.
	 */
	@Test
	public void shouldGetTimeSpentOnTask() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertEquals(1.0, taskReadyToFinishTest.getTimeSpentOnTask(), 0.1);

		taskReadyToFinishTest.updateReportedTime(2.0, taskCollaborator,
				taskReadyToFinishTest.getReports().get(0).getDbId());

		assertEquals(2.0, taskReadyToFinishTest.getTimeSpentOnTask(), 0.1);

	}

	/**
	 * Test method for {@link project.model.Task#getTimeSpentOnTask()}.
	 */
	@Test
	public void shouldGetTimeSpentOnTaskUponReportUpdate() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		taskReadyToFinishTest.updateReportedTime(2.0, taskCollaborator,
				taskReadyToFinishTest.getReports().get(0).getDbId());

		assertEquals(2.0, taskReadyToFinishTest.getTimeSpentOnTask(), 0.1);
	}

	/**
	 * Test method for
	 * {@link project.model.Task#getTimeSpentByProjectCollaboratorOntask(project.model.ProjectCollaborator)}.
	 */
	@Test
	public void shouldGetTimeSpentByProjectCollaboratorOnTask() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertEquals(1.0, taskReadyToFinishTest.getTimeSpentByProjectCollaboratorOntask(projectCollaborator), 0.1);

	}

	/**
	 * Test method for {@link project.model.Task#equals(java.lang.Object)}.
	 */
	@Test
	public void shouldObjectsBeEquals() {

		assertTrue(taskTest.equals(taskTest));// same object
	}

	/**
	 * Test method for {@link project.model.Task#equals(java.lang.Object)}.
	 */
	@Test
	public void shouldNOTObjectsBeEqualsIfObjectIsNUll() {

		Task testTask4 = null;

		assertFalse(taskTest.equals(testTask4));// null object
	}

	/**
	 * Test method for {@link project.model.Task#equals(java.lang.Object)}.
	 */
	@Test
	public void shouldNOTObjectsBeEqualsifFromDifferentClasses() {

		assertFalse(taskTest.equals(userTest));// different classes

	}

	/**
	 * Test method for {@link project.model.Task#equals(java.lang.Object)}.
	 */
	@Test
	public void shouldNOTObjectsBeEqualsIfWithDifferentCounters() {


		Task testTask2 = new Task(1, 10, "Task 1");
		Task testTask1 = new Task(1, 20, "Task 2");

		assertFalse(testTask2.equals(testTask1));// different counter
	}

	/**
	 * Test method for {@link project.model.Task#equals(java.lang.Object)}.
	 */
	@Test
	public void shouldObjectsBeEqualsIfWithSameCounter() {

		Task testTask2 = new Task(1, 10, "Task 1");

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
	public void shouldProjectCollaboratorBeInTaskTeam() {

		assertFalse(taskTest.isProjectCollaboratorInTaskTeam(projectCollaborator));

		taskTest.addProjectCollaboratorToTask(projectCollaborator);

		assertTrue(taskTest.isProjectCollaboratorInTaskTeam(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isProjectCollaboratorActiveInTaskTeam(project.model.ProjectCollaborator)}.
	 */
	@Test
	public void shouldProjectCollaboratorBeActiveInTaskTeam() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		assertTrue(taskReadyToFinishTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isProjectCollaboratorActiveInTaskTeam(project.model.ProjectCollaborator)}.
	 */
	@Test
	public void shouldNotProjectCollaboratorBeActiveInTaskTeamIfRemovedFromTeam() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);

		taskReadyToFinishTest.removeProjectCollaboratorFromTask(projectCollaborator);

		assertFalse(taskReadyToFinishTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));
	}



	/**
	 * Test method for {@link project.model.Task#doesTaskTeamHaveActiveUsers()}.
	 */
	@Test
	public void doesTaskTeamHaveActiveUsers() {

		assertFalse(taskTest.doesTaskTeamHaveActiveUsers());

		taskTest.addProjectCollaboratorToTask(projectCollaborator);

		assertTrue(taskTest.doesTaskTeamHaveActiveUsers());

	}

	/**
	 * Test method for {@link project.model.Task#isTaskTeamEmpty()}.
	 */
	@Test
	public void shouldTaskTeamBeEmpty() {

		assertTrue(taskTest.isTaskTeamEmpty());

		taskTest.addProjectCollaboratorToTask(projectCollaborator);

		assertFalse(taskTest.isTaskTeamEmpty());

	}

	/**
	 * Test method for {@link project.model.Task#getTaskCost()}.
	 */
	@Test
	public void shouldGetTaskCostBasedOnTheWeightedMeanOfAllReports() {

		//taskTest has 2 different projectCollaborators, which are also 2 distinct task collaborators
		taskTest.addProjectCollaboratorToTask(projectCollaborator);
		taskTest.addProjectCollaboratorToTask(projectCollaborator2);

		//Each of the task collaborators present a different report to the same task

		taskTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);
		taskTest.createReport(taskCollaborator2, Calendar.getInstance(), 2.0);

		assertEquals(50.0, taskTest.getTaskCost(), 0.1);

		//Task collaborator presents a new report to the same task

		taskTest.addProjectCollaboratorToTask(projectCollaborator);

		taskTest.createReport(taskCollaborator, Calendar.getInstance(), 1.5);

		assertEquals(65, taskTest.getTaskCost(), 0.1 );


	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskDependence(project.model.Task, int)}.
	 */
	@Test
	public void shouldCreateTaskDependence() {

		taskTest.setTaskID("3");

        Calendar date = Calendar.getInstance();
        date.set(2018, 3, 5);
        taskReadyToFinishTest.setEstimatedTaskStartDate(date);

		assertFalse(taskTest.hasDependencies());

		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));

		assertTrue(taskTest.hasDependencies());
	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskDependence(project.model.Task, int)}.
	 */
	@Test
	public void shouldNotCreateTaskDependence() {

		taskTest.setTaskID("3");
		Calendar date = Calendar.getInstance();
		date.set(2018, 3, 5);
		taskReadyToFinishTest.setEstimatedTaskStartDate(date);

		assertFalse(taskTest.hasDependencies());

		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));

		assertTrue(taskTest.hasDependencies());

		Calendar newDate = taskReadyToFinishTest.getTaskDeadline();
		newDate.add(Calendar.DATE, 1);

		assertEquals(newDate, taskTest.getEstimatedTaskStartDate());

		assertFalse(taskTest.createTaskDependence(taskReadyToFinishTest, -5));
	}

	/**
	 * Test method for
	 * {@link project.model.Task#removeTaskDependence(project.model.Task)}.
	 */
	@Test
	public void shouldRemoveTaskDependence() {

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
	public void doesHaveActiveDependencies() {

		taskTest.setTaskID("3");

		assertFalse(taskTest.hasActiveDependencies());

		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));

		assertTrue(taskTest.hasActiveDependencies());

	}

	/**
	 * Test method for {@link project.model.Task#getTaskState()}.
	 */
	@Test
	public void shouldGetTaskState() {

		TaskStateInterface taskState = new Created();

		taskReadyToFinishTest.setTaskState(taskState);

		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Created);

	}

	/**
	 * Test method for {@link project.model.Task#viewTaskStateName()}.
	 */
	@Test
	public void shouldViewTaskStateName() {

		TaskStateInterface taskState = new Created();

		taskReadyToFinishTest.setTaskState(taskState);

		assertEquals("Created", taskReadyToFinishTest.viewTaskStateName());

	}

	/**
	 * Test method for {@link project.model.Task#viewTaskStateNameFromEnum()}.
	 */
	@Test
	public void shouldViewTaskStateNameFromEnum() {

		taskReadyToFinishTest.setCurrentState(StateEnum.CREATED);

		assertEquals("CREATED", taskReadyToFinishTest.viewTaskStateNameFromEnum());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#setTaskState(project.model.taskstateinterface.TaskStateInterface)}.
	 */
	@Test
	public void shouldSetTaskState() {

		assertTrue(taskReadyToFinishTest.getTaskState() instanceof OnGoing);

		TaskStateInterface taskState = new Created();

		taskReadyToFinishTest.setTaskState(taskState);

		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Created);

	}

	/**
	 * Test method for {@link project.model.Task#hasDependencies()}.
	 */
	@Test
	public void shouldHaveDependencies() {

		taskTest.setTaskID("3");

		assertFalse(taskTest.hasDependencies());

		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));

		assertTrue(taskTest.hasDependencies());

	}

	/**
	 * Test method for {@link project.model.Task#removeFinishDate()}.
	 */
	@Test
	public void shouldRemoveFinishDate() {

		taskReadyToFinishTest.markTaskAsFinished();
		taskReadyToFinishTest.setFinishDate(Calendar.getInstance());
		assertEquals("Finished", taskReadyToFinishTest.viewTaskStateName());

		assertFalse(taskReadyToFinishTest.getFinishDate() == null);

		taskReadyToFinishTest.addProjectCollaboratorToTask(projectCollaborator);
		taskReadyToFinishTest.removeFinishDate();

		assertEquals(null, taskReadyToFinishTest.getFinishDate());
		assertEquals("OnGoing", taskReadyToFinishTest.viewTaskStateName());

	}

	/**
	 * Test method for {@link project.model.Task#cancelTask()}.
	 */
	@Test
	public void shouldCancelTaskandChangeStateToCancelled() {

		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

		assertEquals(null, taskReadyToFinishTest.getCancelDate());
		assertEquals("OnGoing", taskReadyToFinishTest.viewTaskStateName());

		assertTrue(taskReadyToFinishTest.cancelTask());

		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Cancelled);
		assertEquals("Cancelled", taskReadyToFinishTest.viewTaskStateName());
		assertTrue(taskReadyToFinishTest.getCancelDate() != null);


	}

	/**
	 * Test method for {@link project.model.Task#cancelTask()}.
	 */
	@Test
	public void shouldCancelTaskandSetCancelDAte() {

		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

		assertEquals(null, taskReadyToFinishTest.getCancelDate());

		assertTrue(taskReadyToFinishTest.cancelTask());

		assertFalse(taskReadyToFinishTest.getCancelDate() == null);

	}

	/**
	 * Test method for {@link project.model.Task#cancelTask()}.
	 */
	@Test
	public void shouldNotCancelTask() {


		assertFalse(taskTest.cancelTask());

		assertTrue(taskTest.getCancelDate() == null);
		assertFalse(taskTest.getTaskState() instanceof  Cancelled);

	}



	/**
	 * Test method for {@link project.model.Task#cancelTask()}.
	 */
	@Test
	public void shouldCancelTaskWithTaskStateFinished() {

		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

		taskReadyToFinishTest.markTaskAsFinished();

		taskReadyToFinishTest.cancelTask();

		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

	}

	/**
	 * Test method for {@link project.model.Task#assignCancelDateAsNow()}.
	 */
	@Test
	public void shouldSetCancelDate() {

		assertEquals(null, taskReadyToFinishTest.getCancelDate());

		taskReadyToFinishTest.assignCancelDateAsNow();

		assertFalse(taskReadyToFinishTest.getCancelDate() == null);

	}

	/**
	 * Test method for {@link project.model.Task#getCancelDate()}.
	 */
	@Test
	public void shouldGetCancelDate() {

		assertEquals(null, taskReadyToFinishTest.getCancelDate());

		taskReadyToFinishTest.assignCancelDateAsNow();

		assertFalse(taskReadyToFinishTest.getCancelDate() == null);

	}

	/**
	 * Test method for
	 * {@link project.model.Task#removeAllCollaboratorsFromTaskTeam()}.
	 */
	@Test
	public void shouldRemoveAllCollaboratorsFromTaskTeam() {

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
	public void shouldCancelledDateClear() {

		taskReadyToFinishTest.cancelTask();

		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

		taskReadyToFinishTest.cancelledDateClear();

		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);

	}

	/**
	 * Test method for {@link project.model.Task#markAsOnGoing()}.
	 */
	@Test
	public void isUnfinishedTask() {

		taskReadyToFinishTest.markTaskAsFinished();

		assertTrue(taskReadyToFinishTest.isTaskFinished());

		taskReadyToFinishTest.addProjectCollaboratorToTask(projectCollaborator);

		assertTrue(taskReadyToFinishTest.markAsOnGoing());

		assertFalse(taskReadyToFinishTest.isTaskFinished());

	}

	/**
	 * Test method for {@link project.model.Task#markAsOnGoing()}.
	 */
	@Test
	public void shouldGetUnfinishedTaskWithTaskStateSetToOnGoing() {

		taskReadyToFinishTest.cancelTask();

		assertFalse(taskReadyToFinishTest.markAsOnGoing());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getAssignmentTaskTeamRequest(ProjectCollaborator)}.
	 */
	@Test
	public void shouldGetAssignmentTaskTeamRequest() {

		taskReadyToFinishTest.createTaskAssignmentRequest(projectCollaborator);

		assertTrue(taskReadyToFinishTest.getAssignmentTaskTeamRequest(projectCollaborator).getProjCollab()
				.equals(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getRemovalTaskTeamRequest(ProjectCollaborator)}.
	 */
	@Test
	public void shouldGetRemovalTaskTeamRequest() {

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);

		assertEquals(projectCollaborator,
				taskReadyToFinishTest.getRemovalTaskTeamRequest(projectCollaborator).getProjCollab());
		assertEquals(RequestType.REMOVAL, taskReadyToFinishTest.getRemovalTaskTeamRequest(projectCollaborator).getType());

		taskReadyToFinishTest.rejectTaskRemovalRequest(projectCollaborator);

		assertEquals(RequestType.REMOVAL, taskReadyToFinishTest.getRemovalTaskTeamRequest(projectCollaborator).getType());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#viewPendingTaskAssignmentRequests()}.
	 */
	@Test
	public void shouldViewPendingTaskAssignmentRequests() {

		assertTrue(taskReadyToFinishTest.viewPendingTaskAssignmentRequests().isEmpty());

		taskReadyToFinishTest.createTaskAssignmentRequest(projectCollaborator);
		assertEquals(RequestType.ASSIGNMENT, taskReadyToFinishTest.getAssignmentTaskTeamRequest(projectCollaborator).getType());

		assertFalse(taskReadyToFinishTest.viewPendingTaskAssignmentRequests().isEmpty());

	}

	/**
	 * Test method for {@link project.model.Task#viewPendingTaskRemovalRequests()}.
	 */
	@Test
	public void shouldViewPendingTaskRemovalRequests() {

		assertTrue(taskReadyToFinishTest.viewPendingTaskRemovalRequests().isEmpty());

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);
		assertEquals(RequestType.REMOVAL, taskReadyToFinishTest.getRemovalTaskTeamRequest(projectCollaborator).getType());

		assertFalse(taskReadyToFinishTest.viewPendingTaskRemovalRequests().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getPendingTaskAssignmentRequests()}.
	 */
	@Test
	public void shouldGetPendingTaskAssignmentRequests() {

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());

		taskReadyToFinishTest.createTaskAssignmentRequest(projectCollaborator);
		assertEquals(RequestType.ASSIGNMENT, taskReadyToFinishTest.getAssignmentTaskTeamRequest(projectCollaborator).getType());

		assertFalse(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());

	}

	/**
	 * Test method for {@link project.model.Task#getPendingTaskRemovalRequests()}.
	 */
	@Test
	public void shouldGetPendingTaskRemovalRequests() {

		assertTrue(taskReadyToFinishTest.getPendingTaskRemovalRequests().isEmpty());

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.getPendingTaskRemovalRequests().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskAssignmentRequest(ProjectCollaborator)}.
	 */
	@Test
	public void shouldCreateTaskAssignmentRequest() {

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());

		taskReadyToFinishTest.createTaskAssignmentRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskAssignmentRequest(ProjectCollaborator)}.
	 */
	@Test
	public void shouldNotCreateTaskAssignmentRequestRequestsAlreadyCreated() {

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());

		assertTrue(taskReadyToFinishTest.createTaskAssignmentRequest(projectCollaborator));

		assertFalse(taskReadyToFinishTest.createTaskAssignmentRequest(projectCollaborator));

		assertEquals(1, taskReadyToFinishTest.getPendingTaskAssignmentRequests().size());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskRemovalRequest(ProjectCollaborator)}.
	 */
	@Test
	public void shouldCreateTaskRemovalRequest() {

		assertTrue(taskReadyToFinishTest.getPendingTaskRemovalRequests().isEmpty());

		assertTrue(taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator));

		assertFalse(taskReadyToFinishTest.getPendingTaskRemovalRequests().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#createTaskRemovalRequest(ProjectCollaborator)}.
	 */
	@Test
	public void shouldCreateTaskRemovalRequestRequestsAlreadyCreated() {

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
	public void isAssignmentRequestAlreadyCreated() {

		assertFalse(taskReadyToFinishTest.isAssignmentRequestAlreadyCreated(projectCollaborator));

		assertTrue(taskReadyToFinishTest.createTaskAssignmentRequest(projectCollaborator));

		assertTrue(taskReadyToFinishTest.isAssignmentRequestAlreadyCreated(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isRemovalRequestAlreadyCreated(ProjectCollaborator)}.
	 */
	@Test
	public void isRemovalRequestAlreadyCreated() {

		assertFalse(taskReadyToFinishTest.isRemovalRequestAlreadyCreated(projectCollaborator));

		assertTrue(taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator));

		assertTrue(taskReadyToFinishTest.isRemovalRequestAlreadyCreated(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#rejectTaskRemovalRequest(ProjectCollaborator)}.
	 */
	@Test
	public void shouldDeleteTaskRemovalRequest() {

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);

		assertTrue(taskReadyToFinishTest.isRemovalRequestAlreadyCreated(projectCollaborator));
		assertTrue(taskReadyToFinishTest.getPendingTaskRemovalRequests().get(0).getRejectDate()==null);

		taskReadyToFinishTest.rejectTaskRemovalRequest(projectCollaborator);
		assertTrue(taskReadyToFinishTest.getPendingTaskRemovalRequests().get(0).getRejectDate()!=null);

		assertTrue(taskReadyToFinishTest.isRemovalRequestAlreadyCreated(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#rejectTaskAssignmentRequest(ProjectCollaborator)}.
	 */
	@Test
	public void shouldDeleteTaskAssignmentRequest() {

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());

		assertTrue(taskReadyToFinishTest.createTaskAssignmentRequest(projectCollaborator));

		assertFalse(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());
		assertTrue(taskReadyToFinishTest.getPendingTaskAssignmentRequests().get(0).getRejectDate()==null);

		taskReadyToFinishTest.rejectTaskAssignmentRequest(projectCollaborator);

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignmentRequests().get(0).getRejectDate()!=null);


		assertFalse(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#removeAllRequestsWithASpecificTask()}.
	 */
	@Test
	public void shouldRemoveAllRequestsWithASpecificTask() {

		assertTrue(taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator));

		assertFalse(taskReadyToFinishTest.getPendingTaskTeamRequests().isEmpty());

		taskReadyToFinishTest.removeAllRequestsWithASpecificTask();

		assertTrue(taskReadyToFinishTest.getPendingTaskTeamRequests().isEmpty());

	}

	/**
	 * Test method for {@link project.model.Task#setPendingTaskTeamRequests(List)}.
	 */
	@Test
	public void shouldSetPendingTaskTeamRequests() {

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
	public void shouldGetPendingTaskTeamRequests() {

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
	public void canCreateTaskDependencyValid() {

		TaskStateInterface created = new Created();

		taskTestSecond.setTaskState(created);

		assertTrue(taskTestSecond.isCreatingTaskDependencyValid(taskReadyToFinishTest));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void canNOTCreateTaskDependencyValidSameTask() {

		// Asserts false, because both tasks are the same
		assertFalse(taskReadyToFinishTest.isCreatingTaskDependencyValid(taskReadyToFinishTest));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#isCreatingTaskDependencyValid(Task)}.
	 */
	@Test
	public void canNOTCreatingTaskDependencyValidWithoutDeadline() {
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
	public void canNOTCreateTaskDependencyValidSetToOnGoing() {

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
	public void canNOTCreateTaskDependencyValidSetToCancelled() {
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
	public void canNOTCreateTaskDependencyValidSetToReady() {
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
	public void canNOTCreateTaskDependencyValidSetToCancel() {
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
	public void canNOTCreateTaskDependencyValidSetToStandBy() {
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
	public void canNOTCreateTaskDependencyValidSetToFinished() {

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
	public void canNOTCreateTaskDependencyValidSetToFinishedOtherTaskSetToCancelled() {

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
	public void canNOTCreateTaskDependencyValidWithTheSameDependency() {

		TaskStateInterface created = new Created();

		taskTestSecond.setTaskState(created);

		assertTrue(taskTestSecond.createTaskDependence(taskReadyToFinishTest, 1));

		assertFalse(taskTestSecond.isCreatingTaskDependencyValid(taskReadyToFinishTest));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getReportsFromGivenUser(java.lang.String)}.
	 */
	@Test
	public void shouldGetReportsFromGivenUser() {

		assertTrue(taskReadyToFinishTest
				.getReportsFromGivenUser(taskCollaborator.getTaskCollaborator().getEmail()).isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#getReportsFromGivenUser(java.lang.String)}.
	 */
	@Test
	public void shouldNOTGetReportsFromGivenUser() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.1);

		assertFalse(taskReadyToFinishTest
				.getReportsFromGivenUser(taskCollaborator.getTaskCollaborator().getEmail()).isEmpty());

	}

	/**
	 * Test method for
	 * {@link project.model.Task#rejectTaskRemovalRequest(ProjectCollaborator)}.
	 */
	@Test
	public void shouldApproveTaskRemovalRequest() {

		taskReadyToFinishTest.createTaskRemovalRequest(projectCollaborator);

		assertTrue(taskReadyToFinishTest.isRemovalRequestAlreadyCreated(projectCollaborator));
		assertTrue(taskReadyToFinishTest.getPendingTaskRemovalRequests().get(0).getApprovalDate()==null);

		assertTrue(taskReadyToFinishTest.approveTaskRemovalRequest(projectCollaborator));
		assertTrue(taskReadyToFinishTest.getPendingTaskRemovalRequests().get(0).getApprovalDate()!=null);

		assertTrue(taskReadyToFinishTest.isRemovalRequestAlreadyCreated(projectCollaborator));

	}

	/**
	 * Test method for
	 * {@link project.model.Task#rejectTaskAssignmentRequest(ProjectCollaborator)}.
	 */
	@Test
	public void shouldApproveTaskAssignmentRequest() {

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());

		taskReadyToFinishTest.createTaskAssignmentRequest(projectCollaborator);

		assertFalse(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());
		assertTrue(taskReadyToFinishTest.getPendingTaskAssignmentRequests().get(0).getApprovalDate()==null);

		assertTrue(taskReadyToFinishTest.approveTaskAssignmentRequest(projectCollaborator));

		assertTrue(taskReadyToFinishTest.getPendingTaskAssignmentRequests().get(0).getApprovalDate()!=null);


		assertFalse(taskReadyToFinishTest.getPendingTaskAssignmentRequests().isEmpty());

	}
}
