/**
 * 
 */
package project.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import project.model.taskstateinterface.Cancelled;
import project.model.taskstateinterface.Created;
import project.model.taskstateinterface.OnGoing;
import project.model.taskstateinterface.TaskStateInterface;

/**
 * @author Grupo 3
 *
 */
class TaskTest {
	
	User userTest;
	Project projectTest;
	Project projectTestSecond;
	Task taskTest;
	Task taskTestSecond;
	Task taskReadyToFinishTest;
	TaskCollaborator taskCollaborator;
	ProjectCollaborator projectCollaborator;
	Report report;
	

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		
		//create user 
		userTest = new User("Pedro Silva", "pedrosilva@gmail.com", "001", "Developer", "912364896");
		
		//create project
		projectTest = new Project("ProjectX", "Make x stuff", userTest);
		projectTest.setStartdate(Calendar.getInstance());
		projectTestSecond = new Project("ProjectZ", "Make z stuff", userTest);
		
		//create task 
		taskTest = new Task("Make x", projectTest);
		taskReadyToFinishTest = new Task("Make j", projectTest);
		taskTestSecond = new Task("Make z", projectTest);
		
		//create project collaborator 
		projectCollaborator = new ProjectCollaborator(userTest, 10);
		
		//create task collaborator 
		taskCollaborator = new TaskCollaborator(projectCollaborator);
		
		//create report
		report = new Report(taskCollaborator, Calendar.getInstance());
		
		//Finish task 
		
		taskReadyToFinishTest.addProjectCollaboratorToTask(projectCollaborator);	
		
		taskReadyToFinishTest.setEstimatedTaskStartDate(Calendar.getInstance());
		
		taskReadyToFinishTest.setTaskDeadline(Calendar.getInstance());
		
		taskReadyToFinishTest.setEstimatedTaskEffort(1);
		
		taskReadyToFinishTest.setTaskBudget(1);
		
		taskReadyToFinishTest.setStartDate(Calendar.getInstance());
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		
		userTest = null;
		projectTest = null;
		taskTest = null;	
		taskCollaborator = null;
		taskTestSecond = null;
		projectCollaborator = null;
		report = null;
	}

	/**
	 * Test method for {@link project.model.Task#hashCode()}.
	 */
	@Test
	void testHashCode() {
		
		taskTest.setTaskId("1");
		taskTestSecond.setTaskId("2");
		
		assertFalse(taskTest.hashCode() == taskTestSecond.hashCode());

        int result = 3*31 + taskTest.getTaskID().hashCode();
        assertEquals(taskTest.hashCode(), result);
	}

	/**
	 * Test method for {@link project.model.Task#Task()}.
	 * 
	 * Empty Constructor ____
	 * 
	 */
	@Test
	void testTask() {
		
		Task task = new Task();
		
		assertTrue(task instanceof Task);
		
	}

	/**
	 * Test method for {@link project.model.Task#Task(java.lang.String, project.model.Project)}.
	 * 
	 * Constructor ____
	 */
	@Test
	void testTaskStringProject() {
		
		Task task = new Task("task test", projectTest);
		
		assertTrue(task instanceof Task);
		
	}

	/**
	 * Test method for {@link project.model.Task#Task(int, int, java.lang.String)}.
	 * 
	 * Constructor ____
	 */
	@Test
	void testTaskIntIntString() {
		
		Task task = new Task(1, 1, "task test");
		
		assertTrue(task instanceof Task);
		
	}

	/**
	 * Test method for {@link project.model.Task#Task(java.lang.String, int, java.util.Calendar, java.util.Calendar, int)}.
	 * 
	 * Constructor to delete  _____
	 */
	@Test
	void testTaskStringIntCalendarCalendarInt() {
		
		Task task = new Task("task test", 1, Calendar.getInstance(), Calendar.getInstance(), 1);
		
		assertTrue(task instanceof Task);
		
	}

	/**
	 * Test method for {@link project.model.Task#Task(int, int, java.lang.String, int, java.util.Calendar, java.util.Calendar, int)}.
	 * 
	 * Constructor to delete_____
	 */
	@Test
	void testTaskIntIntStringIntCalendarCalendarInt() {
		
		Task task = new Task(1, 1, "task test", 1, Calendar.getInstance(), Calendar.getInstance(), 1);
		
		assertTrue(task instanceof Task);
		
	}

	/**
	 * Test method for {@link project.model.Task#Task(project.model.Task)}.
	 * 
	 * Constructor ______
	 */
	@Test
	void testTaskTask() {
		
		Task task = new Task(1, 1, "task test", 1, Calendar.getInstance(), Calendar.getInstance(), 1);

		Task taskCopy = new Task(task);
		
		assertTrue(taskCopy instanceof Task);
		
	}

	/**
	 * Test method for {@link project.model.Task#getCurrentState()}.
	 */
	@Test
	void testGetCurrentState() {
		
		StateEnum currentState = StateEnum.CREATED;
		
		taskTest.setCurrentState(currentState);
		
		assertEquals(currentState, taskTest.getCurrentState());
		
	}

	/**
	 * Test method for {@link project.model.Task#setCurrentState(project.model.StateEnum)}.
	 */
	@Test
	void testSetCurrentState() {
		
		StateEnum currentState = StateEnum.CREATED;
		
		taskTest.setCurrentState(currentState);
		
		assertEquals(currentState, taskTest.getCurrentState());
	}

	/**
	 * Test method for {@link project.model.Task#getId()}.
	 */
	@Test
	void testGetId() {
		
		Long expected = 001L;
		
		taskTest.setId(expected);
		
		assertEquals(expected, taskTest.getId());
		
	}

	/**
	 * Test method for {@link project.model.Task#setId(java.lang.Long)}.
	 */
	@Test
	void testSetId() {
		
		Long expected = 001L;
		
		taskTest.setId(expected);
		
		assertEquals(expected, taskTest.getId());
	}

	/**
	 * Test method for {@link project.model.Task#setProject(project.model.Project)}.
	 */
	@Test
	void testSetProject() {
		
		taskTest.setProject(projectTestSecond);
		
		assertEquals(projectTestSecond, taskTest.getProject());
	}

	/**
	 * Test method for {@link project.model.Task#getProject()}.
	 */
	@Test
	void testGetProject() {
		
		taskTest.setProject(projectTestSecond);
		
		assertEquals(projectTestSecond, taskTest.getProject());
	}
	
	/**
	 * Test method for {@link project.model.Task#setTaskId}.
	 */
	@Test
	void testSetTaskId() {
		
		taskTest.setTaskId("testTask");
		
		assertEquals("testTask", taskTest.getTaskID());
		
	}
	
	/**
	 * Test method for {@link project.model.Task#getStartDateInterval()}.
	 */
	 @Test
	void testGetStartDateInterval() {
		
		Integer expected = 10;
		
		taskReadyToFinishTest.setStartDateInterval(expected);
		
		assertEquals(expected, taskReadyToFinishTest.getStartDateInterval());
	}

	/**
	 * Test method for {@link project.model.Task#setStartDateInterval(int)}.
	 */
	@Test
	void testSetStartDateInterval() {
		
		Integer expected = 10;
		
		taskReadyToFinishTest.setStartDateInterval(expected);
		
		assertEquals(expected, taskReadyToFinishTest.getStartDateInterval());
		
	}

	/**
	 * Test method for {@link project.model.Task#getDeadlineInterval()}.
	 */
	@Test
	void testGetDeadlineInterval() {
		
		Integer expected = 10;
		
		taskReadyToFinishTest.setDeadlineInterval(expected);
		
		assertEquals(expected, taskReadyToFinishTest.getDeadlineInterval());
	}

	/**
	 * Test method for {@link project.model.Task#setDeadlineInterval(int)}.
	 */
	@Test
	void testSetDeadlineInterval() {
		
		Integer expected = 10;
		
		taskReadyToFinishTest.setDeadlineInterval(expected);
		
		assertEquals(expected, taskReadyToFinishTest.getDeadlineInterval());
	}

	/**
	 * Test method for {@link project.model.Task#getEstimatedTaskEffort()}.
	 */
	@Test
	void testGetEstimatedTaskEffort() {
		
		int expected = 10;
		
		taskTest.setEstimatedTaskEffort(expected);
		
		assertEquals(expected, taskTest.getEstimatedTaskEffort());
	}

	/**
	 * Test method for {@link project.model.Task#setEstimatedTaskEffort(int)}.
	 */
	@Test
	void testSetEstimatedTaskEffort() {

		int expected = 10;
		
		taskTest.setEstimatedTaskEffort(expected);
		
		assertEquals(expected, taskTest.getEstimatedTaskEffort());
	}

	/**
	 * Test method for {@link project.model.Task#getEstimatedTaskStartDate()}.
	 */
	@Test
	void testGetEstimatedTaskStartDate() {
		
		Calendar expected = Calendar.getInstance();
		
		taskTest.setEstimatedTaskStartDate(expected);
		
		assertEquals(expected, taskTest.getEstimatedTaskStartDate());
	}

	/**
	 * Test method for {@link project.model.Task#setEstimatedTaskStartDate(java.util.Calendar)}.
	 */
	@Test
	void testSetEstimatedTaskStartDate() {

		Calendar expected = Calendar.getInstance();
		
		taskTest.setEstimatedTaskStartDate(expected);
		
		assertEquals(expected, taskTest.getEstimatedTaskStartDate());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskDeadline()}.
	 */
	@Test
	void testGetTaskDeadline() {
		
		Calendar expected = Calendar.getInstance();
		
		taskTest.setTaskDeadline(expected);
		
		assertEquals(expected, taskTest.getTaskDeadline());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskDeadline(java.util.Calendar)}.
	 */
	@Test
	void testSetTaskDeadline() {
		
		Calendar expected = Calendar.getInstance();
		
		taskTest.setTaskDeadline(expected);
		
		assertEquals(expected, taskTest.getTaskDeadline());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskBudget()}.
	 */
	@Test
	void testGetTaskBudget() {
		
		int expected = 100;
		
		taskTest.setTaskBudget(expected);
		
		assertEquals(expected, taskTest.getTaskBudget());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskBudget(int)}.
	 */
	@Test
	void testSetTaskBudget() {

		int expected = 100;
		
		taskTest.setTaskBudget(expected);
		
		assertEquals(expected, taskTest.getTaskBudget());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskID()}.
	 */
	@Test
	void testGetTaskID() {

		String expected = null;
		
		assertEquals(expected, taskTest.getTaskID());
	}

	/**
	 * Test method for {@link project.model.Task#getStartDate()}.
	 */
	@Test
	void testGetStartDate() {

		Calendar expected = Calendar.getInstance();
		
		taskTest.setStartDate(expected);

		assertEquals(expected, taskTest.getStartDate());
	}

	/**
	 * Test method for {@link project.model.Task#setStartDate(java.util.Calendar)}.
	 */
	@Test
	void testSetStartDate() {
		
		Calendar expected = Calendar.getInstance();
		
		taskTest.setStartDate(expected);

		assertEquals(expected, taskTest.getStartDate());
	}

	/**
	 * Test method for {@link project.model.Task#getDescription()}.
	 */
	@Test
	void testGetDescription() {
		
		assertNotNull( taskTest.getDescription());		

	}

	/**
	 * Test method for {@link project.model.Task#getFinishDate()}.
	 */
	@Test
	void testGetFinishDate() {
		
		taskTest.setFinishDate(Calendar.getInstance());
		
		assertNotNull(taskTest.getFinishDate());
		
	}

	/**
	 * Test method for {@link project.model.Task#setFinishDate()}.
	 */
	@Test
	void testSetFinishDate() {
		
		taskTest.setFinishDate(Calendar.getInstance());
		
		assertNotNull(taskTest.getFinishDate());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskTeam()}.
	 */
	@Test
	void testGetTaskTeam() {
		
		List<TaskCollaborator> expected = new ArrayList<>();
		expected.add(taskCollaborator);
		
		taskTest.setTaskTeam(expected);
		
		assertEquals(expected, taskTest.getTaskTeam());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskTeam(java.util.List)}.
	 */
	@Test
	void testSetTaskTeam() {
		
		List<TaskCollaborator> expected = new ArrayList<>();
		expected.add(taskCollaborator);
		
		taskTest.setTaskTeam(expected);
		
		assertEquals(expected, taskTest.getTaskTeam());
	}

	/**
	 * Test method for {@link project.model.Task#getReports()}.
	 */
	@Test
	void testGetReports() {
		
		List<Report> expected = new ArrayList<>();
		expected.add(report);
		
		taskTest.setReports(expected);
		
		assertEquals(expected, taskTest.getReports());
	}

	/**
	 * Test method for {@link project.model.Task#setReports(java.util.List)}.
	 */
	@Test
	void testSetReports() {
	
		List<Report> expected = new ArrayList<>();
		expected.add(report);
		
		taskTest.setReports(expected);
		
		assertEquals(expected, taskTest.getReports());
	}

	/**
	 * Test method for {@link project.model.Task#getTaskDependency()}.
	 */
	@Test
	void testGetTaskDependency() {
		
		List<Task> expected = new ArrayList<>();
		expected.add(taskReadyToFinishTest);
		
		taskTest.setTaskDependency(expected);

		assertEquals(expected, taskTest.getTaskDependency());
	}

	/**
	 * Test method for {@link project.model.Task#setTaskDependency(java.util.List)}.
	 */
	@Test
	void testSetTaskDependency() {
		
		List<Task> expected = new ArrayList<>();
		expected.add(taskTestSecond);
		
		taskTest.setTaskDependency(expected);

		assertEquals(expected, taskTest.getTaskDependency());
	}

	/**
	 * Test method for {@link project.model.Task#isTaskFinished()}.
	 */
	@Test
	void testIsTaskFinished() {
		
		taskReadyToFinishTest.markTaskAsFinished();
		
		assertTrue(taskReadyToFinishTest.isTaskFinished());
	}
	
	/**
	 * Test method for {@link project.model.Task#markTaskAsFinished()}.
	 */
	@Test
	void testMarkTaskAsFinished() {

		assertFalse(taskReadyToFinishTest.isTaskFinished());
		
		taskReadyToFinishTest.markTaskAsFinished();
		
		assertTrue(taskReadyToFinishTest.isTaskFinished());
		
	}

	/**
	 * Test method for {@link project.model.Task#addProjectCollaboratorToTask(project.model.ProjectCollaborator)}.
	 */
	@Test
	void testAddProjectCollaboratorToTask() {
		
		assertTrue(taskTest.getTaskTeam().isEmpty());
		
		taskTest.addProjectCollaboratorToTask(projectCollaborator);
		
		assertFalse(taskTest.getTaskTeam().isEmpty());
	}

	/**
	 * Test method for {@link project.model.Task#addTaskCollaboratorToTask(project.model.TaskCollaborator)}.
	 */
	@Test
	void testAddTaskCollaboratorToTask() {
		
		assertTrue(taskTest.getTaskTeam().isEmpty());
		
		taskTest.addTaskCollaboratorToTask(taskCollaborator);
		
		assertFalse(taskTest.getTaskTeam().isEmpty());
	}

	/**
	 * Test method for {@link project.model.Task#createTaskCollaborator(project.model.ProjectCollaborator)}.
	 */
	@Test
	void testCreateTaskCollaborator() {
		
		assertTrue(taskTest.getTaskTeam().isEmpty());
		
		taskTest.createTaskCollaborator(projectCollaborator);
		
		assertTrue(taskTest.getTaskTeam().isEmpty());
		
	}

	/**
	 * Test method for {@link project.model.Task#createReport(project.model.TaskCollaborator, java.util.Calendar, double)}.
	 */
	@Test
	void testCreateReport() {
		
		assertTrue(taskReadyToFinishTest.getReports().isEmpty());
		
		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.2);
		
		assertFalse(taskReadyToFinishTest.getReports().isEmpty());
	}

	/**
	 * Test method for {@link project.model.Task#getReportsIndexOfTaskCollaborator(java.lang.String)}.
	 */
	@Test
	void testGetReportsIndexOfTaskCollaborator() {
		
		assertTrue(taskReadyToFinishTest.getReportsIndexOfTaskCollaborator(taskCollaborator.getTaskCollaborator().getEmail()).isEmpty());
		
		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.1);
		
		assertFalse(taskReadyToFinishTest.getReportsIndexOfTaskCollaborator(taskCollaborator.getTaskCollaborator().getEmail()).isEmpty());

	}

	/**
	 * Test method for {@link project.model.Task#updateReportedTime(double, project.model.TaskCollaborator, int)}.
	 */
	@Test
	void testUpdateReportedTime() {
		
		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 2.0);
		
		assertEquals(2.0, taskReadyToFinishTest.getReports().get(0).getReportedTime(), 0.1);
		
		int reportToChange = taskReadyToFinishTest.getReports().get(0).getId();
		
		taskReadyToFinishTest.updateReportedTime(3.0, taskCollaborator, reportToChange);
		
		assertEquals(3.0, taskReadyToFinishTest.getReports().get(0).getReportedTime(), 0.1);
		
	}

	/**
	 * Test method for {@link project.model.Task#doesTaskHaveReportByGivenUser(java.lang.String)}.
	 */
	@Test
	void testDoesTaskHaveReportByGivenUser() {
		
		assertFalse(taskReadyToFinishTest.doesTaskHaveReportByGivenUser(taskCollaborator.getTaskCollaborator().getEmail()));
		
		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);
		
		assertTrue(taskReadyToFinishTest.doesTaskHaveReportByGivenUser(taskCollaborator.getTaskCollaborator().getEmail()));

	}

	/**
	 * Test method for {@link project.model.Task#getTaskCollaboratorByEmail(java.lang.String)}.
	 */
	@Test
	void testGetTaskCollaboratorByEmail() {

		assertEquals(taskCollaborator, taskReadyToFinishTest.getTaskCollaboratorByEmail(taskCollaborator.getTaskCollaborator().getEmail()));		
	
	}

	/**
	 * Test method for {@link project.model.Task#getActiveTaskCollaboratorByEmail(java.lang.String)}.
	 */
	@Test
	void testGetActiveTaskCollaboratorByEmail() {
		
		assertEquals(taskCollaborator, taskReadyToFinishTest.getActiveTaskCollaboratorByEmail(taskCollaborator.getTaskCollaborator().getEmail()));
			
	}

	/**
	 * Test method for {@link project.model.Task#getReportedTimeByTaskCollaborator(java.lang.String)}.
	 */
	@Test
	void testGetReportedTimeByTaskCollaborator() {

		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);
		
		assertEquals(1.0, taskReadyToFinishTest.getReportedTimeByTaskCollaborator(taskCollaborator.getTaskCollaborator().getEmail()), 0.1);
		
	}

	/**
	 * Test method for {@link project.model.Task#getReporterName(java.lang.String)}.
	 */
	@Test
	void testGetReporterName() {
		
		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);
		
		String nameToCompare = taskCollaborator.getTaskCollaborator().getName();
		
		assertEquals(nameToCompare, taskReadyToFinishTest.getReporterName(taskCollaborator.getTaskCollaborator().getEmail()));
				
	}

	/**
	 * Test method for {@link project.model.Task#removeProjectCollaboratorFromTask(project.model.ProjectCollaborator)}.
	 */
	@Test
	void testRemoveProjectCollaboratorFromTask() {

		assertTrue(taskReadyToFinishTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));
		
		taskReadyToFinishTest.removeProjectCollaboratorFromTask(projectCollaborator);
		
		assertFalse(taskReadyToFinishTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));	
		
	}

	/**
	 * Test method for {@link project.model.Task#getTimeSpentOntask()}.
	 */
	@Test
	void testGetTimeSpentOntask() {
		
		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);
		
		assertEquals(1.0, taskReadyToFinishTest.getTimeSpentOntask(), 0.1);
		
		taskReadyToFinishTest.updateReportedTime(2.0, taskCollaborator, taskReadyToFinishTest.getReports().get(0).getId());
		
		assertEquals(2.0, taskReadyToFinishTest.getTimeSpentOntask(), 0.1);
		
	}

	/**
	 * Test method for {@link project.model.Task#getTimeSpentByProjectCollaboratorOntask(project.model.ProjectCollaborator)}.
	 */
	@Test
	void testGetTimeSpentByProjectCollaboratorOntask() {
		
		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);
		
		assertEquals(1.0, taskReadyToFinishTest.getTimeSpentByProjectCollaboratorOntask(projectCollaborator), 0.1);
				
	}

	/**
	 * Test method for {@link project.model.Task#equals(java.lang.Object)}.
	 */
	@Test
	void testEqualsObject() {
        
		assertTrue(taskTest.equals(taskTest));// same object
        
		Task testTask4 = null;
        
        assertFalse(taskTest.equals(testTask4));// null object
        
        assertFalse(taskTest.equals(userTest));// different classes
        
        Task testTask2 = new Task(1, 01, "Task 1");
        Task testTask1 = new Task(1, 02, "Task 2");
       
        assertFalse(testTask2.equals(testTask1));// different counter
        
        Task testTask3 = new Task(1, 1, "Task 1");
        
        assertTrue(testTask2.equals(testTask3));// same counter	
	}

	/**
	 * Test method for {@link project.model.Task#isProjectCollaboratorInTaskTeam(project.model.ProjectCollaborator)}.
	 */
	@Test
	void testIsProjectCollaboratorInTaskTeam() {
		
		assertFalse(taskTest.isProjectCollaboratorInTaskTeam(projectCollaborator));
		
		taskTest.addProjectCollaboratorToTask(projectCollaborator);
		
		assertTrue(taskTest.isProjectCollaboratorInTaskTeam(projectCollaborator));
		
		
	}

	/**
	 * Test method for {@link project.model.Task#isProjectCollaboratorActiveInTaskTeam(project.model.ProjectCollaborator)}.
	 */
	@Test
	void testIsProjectCollaboratorActiveInTaskTeam() {
		
		taskReadyToFinishTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);
		
		assertTrue(taskReadyToFinishTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));
		
		taskReadyToFinishTest.removeProjectCollaboratorFromTask(projectCollaborator);
		
		assertFalse(taskReadyToFinishTest.isProjectCollaboratorActiveInTaskTeam(projectCollaborator));
	}

	/**
	 * Test method for {@link project.model.Task#doesTaskTeamHaveActiveUsers()}.
	 */
	@Test
	void testDoesTaskTeamHaveActiveUsers() {
		
		assertFalse(taskTest.doesTaskTeamHaveActiveUsers());
		
		taskTest.addProjectCollaboratorToTask(projectCollaborator);
		
		assertTrue(taskTest.doesTaskTeamHaveActiveUsers());
		
	}

	/**
	 * Test method for {@link project.model.Task#isTaskTeamEmpty()}.
	 */
	@Test
	void testIsTaskTeamEmpty() {
		
		assertTrue(taskTest.isTaskTeamEmpty());
		
		taskTest.addProjectCollaboratorToTask(projectCollaborator);
		
		assertFalse(taskTest.isTaskTeamEmpty());
		
	}

	/**
	 * Test method for {@link project.model.Task#getTaskCost()}.
	 */
	@Test
	void testGetTaskCost() {
		
		taskTest.addProjectCollaboratorToTask(projectCollaborator);
		
		taskTest.createReport(taskCollaborator, Calendar.getInstance(), 1.0);
		
		assertEquals(10.0, taskTest.getTaskCost(), 0.1);
			
	}

	/**
	 * Test method for {@link project.model.Task#createTaskDependence(project.model.Task, int)}.
	 */
	@Test
	void testCreateTaskDependence() {
		
		taskTest.setTaskId("1");
		
		assertFalse(taskTest.hasDependencies());
		
		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));
		
		assertTrue(taskTest.hasDependencies());
	}

	/**
	 * Test method for {@link project.model.Task#removeTaskDependence(project.model.Task)}.
	 */
	@Test
	void testRemoveTaskDependence() {
		taskTest.setTaskId("1");
		
		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));
		
		assertTrue(taskTest.hasDependencies());
		
		assertTrue(taskTest.removeTaskDependence(taskReadyToFinishTest));
		
		assertFalse(taskTest.hasDependencies());
		
	}

	 /**
    *
    * This test asserts all the task dependency conditions are working correctly:
    *
    * 1 - Dependencies can only be created on tasks that haven't started yet
    *
    * 2 - Dependencies can only be created when the expected task start date is later than the mother task's deadline
    * (meaning the dependent task can't be expected to start before the mother is completed)
    *
    */
//TODO
   @Test
   public void isCreatingTaskDependencyValid() {

	   
   }

	/**
	 * Test method for {@link project.model.Task#hasActiveDependencies()}.
	 */
	@Test
	void testHasActiveDependencies() {
		
		taskTest.setTaskId("1");
		
		assertFalse(taskTest.hasActiveDependencies());
		
		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));
		
		assertTrue(taskTest.hasActiveDependencies());
		
	}

	/**
	 * Test method for {@link project.model.Task#getTaskState()}.
	 */
	@Test
	void testGetTaskState() {
		
		TaskStateInterface taskState = new Created();
		
		taskReadyToFinishTest.setTaskState(taskState);
		
		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Created);
		
	}

	/**
	 * Test method for {@link project.model.Task#viewTaskStateName()}.
	 */
	@Test
	void testViewTaskStateName() {
		
		TaskStateInterface taskState = new Created();
		
		taskReadyToFinishTest.setTaskState(taskState);

		assertEquals("Created", taskReadyToFinishTest.viewTaskStateName());
		
	}

	/**
	 * Test method for {@link project.model.Task#viewTaskStateNameFromEnum()}.
	 */
	@Test
	void testViewTaskStateNameFromEnum() {
		
		taskReadyToFinishTest.setCurrentState(StateEnum.CREATED);
		
		assertEquals("CREATED", taskReadyToFinishTest.viewTaskStateNameFromEnum());
		
	}

	/**
	 * Test method for {@link project.model.Task#setTaskState(project.model.taskstateinterface.TaskStateInterface)}.
	 */
	@Test
	void testSetTaskState() {
		
		assertTrue(taskReadyToFinishTest.getTaskState() instanceof OnGoing );
		
		TaskStateInterface taskState = new Created();
		
		taskReadyToFinishTest.setTaskState(taskState);
		
		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Created);
		
	}

	/**
	 * Test method for {@link project.model.Task#hasDependencies()}.
	 */
	@Test
	void testHasDependencies() {
		
		taskTest.setTaskId("1");
		
		assertFalse(taskTest.hasDependencies());
		
		assertTrue(taskTest.createTaskDependence(taskReadyToFinishTest, 1));
		
		assertTrue(taskTest.hasDependencies());
		
	}

	/**
	 * Test method for {@link project.model.Task#removeFinishDate()}.
	 */
	@Test
	void testRemoveFinishDate() {
		
		taskReadyToFinishTest.setFinishDate(Calendar.getInstance());
		
		assertNotEquals(null, taskReadyToFinishTest.getFinishDate());
		
		taskReadyToFinishTest.removeFinishDate();
		
		assertEquals(null, taskReadyToFinishTest.getFinishDate());
		
	}

	/**
	 * Test method for {@link project.model.Task#cancelTask()}.
	 */
	@Test
	void testCancelTask() {
		
		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);
		
		assertEquals(null, taskReadyToFinishTest.getCancelDate());
		
		taskReadyToFinishTest.cancelTask();
		
		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Cancelled);
		
		assertNotEquals(null, taskReadyToFinishTest.getCancelDate());
		
	}

	/**
	 * Test method for {@link project.model.Task#setCancelDate()}.
	 */
	@Test
	void testSetCancelDate() {
		
		assertEquals(null, taskReadyToFinishTest.getCancelDate());
		
		taskReadyToFinishTest.setCancelDate();
		
		assertNotEquals(null, taskReadyToFinishTest.getCancelDate());
		
	}

	/**
	 * Test method for {@link project.model.Task#getCancelDate()}.
	 */
	@Test
	void testGetCancelDate() {
		
		assertEquals(null, taskReadyToFinishTest.getCancelDate());
		
		taskReadyToFinishTest.setCancelDate();
		
		assertNotEquals(null, taskReadyToFinishTest.getCancelDate());
	}

	/**
	 * Test method for {@link project.model.Task#removeAllCollaboratorsFromTaskTeam()}.
	 */
	@Test
	void testRemoveAllCollaboratorsFromTaskTeam() {
		
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
	void testCancelledDateClear() {
		
		taskReadyToFinishTest.cancelTask();
		
		assertTrue(taskReadyToFinishTest.getTaskState() instanceof Cancelled);
		
		taskReadyToFinishTest.cancelledDateClear();
		
		assertFalse(taskReadyToFinishTest.getTaskState() instanceof Cancelled);
		
	}

	/**
	 * Test method for {@link project.model.Task#UnfinishTask()}.
	 */
	@Test
	void testUnfinishTask() {
		
		taskReadyToFinishTest.markTaskAsFinished();
		
		assertTrue(taskReadyToFinishTest.isTaskFinished());
		
		taskReadyToFinishTest.UnfinishTask();
		
		assertFalse(taskReadyToFinishTest.isTaskFinished());

	}

}
