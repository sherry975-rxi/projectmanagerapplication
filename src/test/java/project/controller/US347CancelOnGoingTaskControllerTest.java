/**
 * 
 */
package project.controller;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.model.Company;
import project.model.Profile;
import project.model.Project;
import project.model.ProjectCollaborator;
import project.model.Task;
import project.model.TaskCollaborator;
import project.model.User;
import project.model.taskStateInterface.Assigned;
import project.model.taskStateInterface.Cancelled;
import project.model.taskStateInterface.OnGoing;
import project.model.taskStateInterface.Planned;
import project.model.taskStateInterface.Ready;

/**
 * This class tests the methods that are called in Controller to execute the
 * action of Canceling an OnGoing Task
 * 
 * @author Group3
 *
 */
public class US347CancelOnGoingTaskControllerTest {

	// TasksFiltersController tasksFiltersController;
	Company company1;
	User user1, user2, projectManager;
	Project project1;
	ProjectCollaborator projCollab1, projCollab2;
	Task task1, task2, task3;
	TaskCollaborator taskCollab1, taskCollab2;
	Planned PlannedTestTask;
	Planned PlannedTestTask2;
	Planned PlannedTestTask3;
	Assigned AssignedTestTask;
	Assigned AssignedTestTask2;
	Assigned AssignedTestTask3;
	Ready ReadyTestTask;
	Ready ReadyTestTask2;
	Ready ReadyTestTask3;
	OnGoing onGoingTestTask;
	OnGoing onGoingTestTask2;
	OnGoing onGoingTestTask3;
	Calendar startDateTest;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar taskExpiredDeadlineDateTest;

	@Before
	public void setUp() {

		// create company
		company1 = Company.getTheInstance();

		// create users
		user1 = company1.getUsersRepository().createUser("Joe Smith", "jsmith@gmail.com", "001", "Junior Programmer",
				"930000000", "Rua da Caparica, 19", "7894-654", "Porto", "Porto", "Portugal");
		user2 = company1.getUsersRepository().createUser("John Smith", "johnsmith@gmail.com", "001", "General Manager",
				"930025000", "Rua Doutor Armando", "4455-654", "Rio Tinto", "Gondomar", "Portugal");
		projectManager = company1.getUsersRepository().createUser("Mary MacJohn", "mmacjohn@gmail.com", "003",
				"Product Manager", "930025000", "Rua Terceira, 44", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");

		// add users to company
		company1.getUsersRepository().addUserToUserRepository(user1);
		company1.getUsersRepository().addUserToUserRepository(user2);
		company1.getUsersRepository().addUserToUserRepository(projectManager);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);

		// create project and establishes collaborator projectManager as project manager
		// of project 1
		project1 = company1.getProjectsRepository().createProject("Project Management software",
				"This software main goals are ....", projectManager);

		// add project to company
		company1.getProjectsRepository().addProjectToProjectRepository(project1);

		// create project collaborators
		projCollab1 = new ProjectCollaborator(user1, 2);
		projCollab2 = new ProjectCollaborator(user2, 2);

		// add collaborators to Project
		project1.addProjectCollaboratorToProjectTeam(projCollab1);
		project1.addProjectCollaboratorToProjectTeam(projCollab2);

		// create tasks
		task1 = project1.getTaskRepository().createTask("Create class User");
		task2 = project1.getTaskRepository().createTask("Create class User");
		task3 = project1.getTaskRepository().createTask("create test for method set name in class user");

		// add tasks to task repository
		project1.getTaskRepository().addProjectTask(task1);
		project1.getTaskRepository().addProjectTask(task2);
		project1.getTaskRepository().addProjectTask(task3);

		// create a estimated Task Start Date
		startDateTest = Calendar.getInstance();

		// create a estimated Task Start Date
		estimatedTaskStartDateTest = Calendar.getInstance();
		estimatedTaskStartDateTest.set(Calendar.YEAR, 2017);
		estimatedTaskStartDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		estimatedTaskStartDateTest.set(Calendar.DAY_OF_MONTH, 25);
		estimatedTaskStartDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a estimated Task Dead line Date
		taskDeadlineDateTest = Calendar.getInstance();
		taskDeadlineDateTest.set(Calendar.YEAR, 2018);
		taskDeadlineDateTest.set(Calendar.MONTH, Calendar.JANUARY);
		taskDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// create a expired estimated Task Dead line Date
		taskExpiredDeadlineDateTest = Calendar.getInstance();
		taskExpiredDeadlineDateTest.set(Calendar.YEAR, 2017);
		taskExpiredDeadlineDateTest.set(Calendar.MONTH, Calendar.SEPTEMBER);
		taskExpiredDeadlineDateTest.set(Calendar.DAY_OF_MONTH, 29);
		taskExpiredDeadlineDateTest.set(Calendar.HOUR_OF_DAY, 14);

		// Creates State Objects planned for task.
		PlannedTestTask = new Planned(task1);
		PlannedTestTask2 = new Planned(task2);
		PlannedTestTask3 = new Planned(task3);

		// set estimated task start date and task dead line to tasks
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest);

		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest);

		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest);

		// Sets the tasks to "Planned"
		task1.setTaskState(PlannedTestTask);
		task2.setTaskState(PlannedTestTask2);
		task3.setTaskState(PlannedTestTask3);

		// create task workers
		taskCollab1 = new TaskCollaborator(projCollab1);
		taskCollab2 = new TaskCollaborator(projCollab2);

		// set active user
		task1.addTaskCollaboratorToTask(taskCollab1);
		task2.addTaskCollaboratorToTask(taskCollab2);
		task3.addTaskCollaboratorToTask(taskCollab1);

		// Creates State Objects assigned for task.
		AssignedTestTask = new Assigned(task1);
		AssignedTestTask2 = new Assigned(task2);
		AssignedTestTask3 = new Assigned(task3);

		// Sets the tasks to "Assigned"
		task1.setTaskState(AssignedTestTask);
		task2.setTaskState(AssignedTestTask2);
		task3.setTaskState(AssignedTestTask3);

		// Creates State Objects Ready for task.
		ReadyTestTask = new Ready(task1);
		ReadyTestTask2 = new Ready(task2);
		ReadyTestTask3 = new Ready(task3);

		// Sets the tasks to "Ready"
		task1.setTaskState(ReadyTestTask);
		task2.setTaskState(ReadyTestTask2);
		task3.setTaskState(ReadyTestTask3);

		// Creates State Objects OnGoing for task.
		onGoingTestTask = new OnGoing(task1);
		onGoingTestTask2 = new OnGoing(task2);
		onGoingTestTask3 = new OnGoing(task3);

		// Sets the tasks to "onGoing"
		task1.setTaskState(onGoingTestTask);
		task2.setTaskState(onGoingTestTask2);
		task3.setTaskState(onGoingTestTask3);
	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		user2 = null;
		projectManager = null;
		project1 = null;
		projCollab1 = null;
		projCollab2 = null;
		task1 = null;
		task2 = null;
		task3 = null;
		taskCollab1 = null;
		taskCollab2 = null;
		PlannedTestTask = null;
		PlannedTestTask2 = null;
		PlannedTestTask3 = null;
		AssignedTestTask = null;
		AssignedTestTask2 = null;
		AssignedTestTask3 = null;
		ReadyTestTask = null;
		ReadyTestTask2 = null;
		ReadyTestTask3 = null;
		onGoingTestTask = null;
		onGoingTestTask2 = null;
		onGoingTestTask3 = null;
		startDateTest = null;
		estimatedTaskStartDateTest = null;
		taskDeadlineDateTest = null;
		taskExpiredDeadlineDateTest = null;
	}

	/**
	 * Test method that views the task state and returns this information in a
	 * string format
	 */
	@Test
	public void testViewTaskState() {
		// create controller
		US347CancelOnGoingTaskController uS347CancelOnGoingTaskController = new US347CancelOnGoingTaskController(
				task1.getTaskID(), project1);

		assertEquals("OnGoing", uS347CancelOnGoingTaskController.viewTaskState());
	}

	/**
	 * This test verifies if an task in state OnGoing is changed to Cancelled using
	 * the controller.
	 */
	@Test
	public void testCancelOnGoingTask() {

		// create controller
		US347CancelOnGoingTaskController uS347CancelOnGoingTaskController = new US347CancelOnGoingTaskController(
				task1.getTaskID(), project1);

		// create state cancelled in task2
		Cancelled cancelledTestTask = new Cancelled(task2);

		// Sets task2 to "cancelled"
		task2.setTaskState(cancelledTestTask);

		// Sets a cancel date for the task1
		task1.setCancelDate();
		// use of control to set task1 to state cancelled
		uS347CancelOnGoingTaskController.cancelOnGoingTask();

		// asserts that task1 has state cancelled
		assertEquals("Cancelled", task1.viewTaskStateName());
	}

}
