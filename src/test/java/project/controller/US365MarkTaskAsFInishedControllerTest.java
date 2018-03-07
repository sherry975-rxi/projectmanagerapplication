package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.model.*;
import project.model.taskstateinterface.Assigned;
import project.model.taskstateinterface.OnGoing;
import project.model.taskstateinterface.Planned;
import project.model.taskstateinterface.Ready;

import java.util.Calendar;

import static org.junit.Assert.*;

public class US365MarkTaskAsFInishedControllerTest {

	/**
	 * This class tests the methods that are called in Controller to execute the
	 * action of Canceling an OnGoing Task
	 * 
	 * @author Group3
	 *
	 */

	// TasksFiltersController tasksFiltersController;
	Company company1;
	User user1, user2, projectManager, projectManager2;
	Project project1, project2, project3;
	ProjectCollaborator projCollab1, projCollab2;
	Task task1OnGoing, task2OnGoing, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2;
	Planned PlannedTestTask;
	Planned PlannedTestTask2;
	Planned PlannedTestTask3;
	Planned PlannedTestTask4;
	Planned PlannedTestTask5;
	Planned PlannedTestTask6;
	Assigned AssignedTestTask;
	Assigned AssignedTestTask2;
	Assigned AssignedTestTask3;
	Assigned AssignedTestTask4;
	Assigned AssignedTestTask5;
	Assigned AssignedTestTask6;
	Ready ReadyTestTask;
	Ready ReadyTestTask2;
	Ready ReadyTestTask3;
	Ready ReadyTestTask4;
	Ready ReadyTestTask5;
	Ready ReadyTestTask6;
	OnGoing onGoingTestTask;
	OnGoing onGoingTestTask2;
	OnGoing onGoingTestTask3;
	OnGoing onGoingTestTask4;
	OnGoing onGoingTestTask5;
	OnGoing onGoingTestTask6;
	Calendar startDateTest;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar taskExpiredDeadlineDateTest;
	US365MarkTaskAsFinishedControllerProjectManager us365controller;

	@Before
	public void setUp() {

		// create company
		company1 = Company.getTheInstance();

		// create users
		user1 = company1.getUsersContainer().createUser("Joe Smith", "jsmith@gmail.com", "001", "Junior Programmer",
				"930000000", "Rua da Caparica, 19", "7894-654", "Porto", "Porto", "Portugal");
		user2 = company1.getUsersContainer().createUser("John Smith", "johnsmith@gmail.com", "001", "General Manager",
				"930025000", "Rua Doutor Armando", "4455-654", "Rio Tinto", "Gondomar", "Portugal");
		projectManager = company1.getUsersContainer().createUser("Mary MacJohn", "mmacjohn@gmail.com", "003",
				"Product Manager", "930025000", "Rua Terceira, 44", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");
		projectManager2 = company1.getUsersContainer().createUser("John MacMary", "jmacmary2@gmail.com", "003",
				"Product Manager2", "930025356", "Rua Segunda, 45", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");

		// add users to company
		company1.getUsersContainer().addUserToUserRepository(user1);
		company1.getUsersContainer().addUserToUserRepository(user2);
		company1.getUsersContainer().addUserToUserRepository(projectManager);
		company1.getUsersContainer().addUserToUserRepository(projectManager2);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);
		projectManager2.setUserProfile(Profile.COLLABORATOR);

		// create project and establishes collaborator projectManager as project manager
		// of project 1
		project1 = company1.getProjectsContainer().createProject("Project Management software",
				"This software main goals are ....", projectManager);
		project2 = company1.getProjectsContainer().createProject("Project Management software",
				"This software main goals are ....", projectManager2);
		project3 = company1.getProjectsContainer().createProject("Project Management software",
				"This software main goals are ....", projectManager);

		// add project to company
		company1.getProjectsContainer().addProjectToProjectContainer(project1);
		company1.getProjectsContainer().addProjectToProjectContainer(project2);
		company1.getProjectsContainer().addProjectToProjectContainer(project3);

		// create project collaborators
		projCollab1 = new ProjectCollaborator(user1, 2);
		projCollab2 = new ProjectCollaborator(user2, 2);

		// add collaborators to Project
		project1.addProjectCollaboratorToProjectTeam(projCollab1);
		project1.addProjectCollaboratorToProjectTeam(projCollab2);
		project2.addProjectCollaboratorToProjectTeam(projCollab1);
		project2.addProjectCollaboratorToProjectTeam(projCollab2);
		project3.addProjectCollaboratorToProjectTeam(projCollab1);
		project3.addProjectCollaboratorToProjectTeam(projCollab2);

		// create tasks
		task1OnGoing = project1.getTaskRepository().createTask("Create class User");
		task2OnGoing = project1.getTaskRepository().createTask("Create class User");
		task3 = project2.getTaskRepository().createTask("create test for method set name in class user");
		task4 = project2.getTaskRepository().createTask("Create class User");
		task5 = project3.getTaskRepository().createTask("Create class User");
		task6 = project3.getTaskRepository().createTask("create test for method set name in class user");

		// add tasks to task repository
		project1.getTaskRepository().addProjectTask(task1OnGoing);
		project1.getTaskRepository().addProjectTask(task2OnGoing);
		project2.getTaskRepository().addProjectTask(task3);
		project2.getTaskRepository().addProjectTask(task4);
		project3.getTaskRepository().addProjectTask(task5);
		project3.getTaskRepository().addProjectTask(task6);

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
		PlannedTestTask = new Planned(task1OnGoing);
		PlannedTestTask2 = new Planned(task2OnGoing);
		PlannedTestTask3 = new Planned(task3);
		PlannedTestTask4 = new Planned(task4);
		PlannedTestTask5 = new Planned(task5);
		PlannedTestTask6 = new Planned(task6);

		// set estimated task start date and task dead line to tasks
		task1OnGoing.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1OnGoing.setStartDate(estimatedTaskStartDateTest);
		task1OnGoing.setTaskDeadline(taskDeadlineDateTest);

		task2OnGoing.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2OnGoing.setStartDate(estimatedTaskStartDateTest);
		task2OnGoing.setTaskDeadline(taskDeadlineDateTest);

		task3.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task3.setStartDate(estimatedTaskStartDateTest);
		task3.setTaskDeadline(taskDeadlineDateTest);

		task4.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task4.setStartDate(estimatedTaskStartDateTest);
		task4.setTaskDeadline(taskDeadlineDateTest);

		task5.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task5.setStartDate(estimatedTaskStartDateTest);
		task5.setTaskDeadline(taskDeadlineDateTest);

		task6.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task6.setStartDate(estimatedTaskStartDateTest);
		task6.setTaskDeadline(taskDeadlineDateTest);

		// Sets the tasks to "Planned"
		task1OnGoing.setTaskState(PlannedTestTask);
		task2OnGoing.setTaskState(PlannedTestTask2);
		task3.setTaskState(PlannedTestTask3);
		task4.setTaskState(PlannedTestTask4);
		task5.setTaskState(PlannedTestTask5);
		task6.setTaskState(PlannedTestTask6);

		// create task workers
		taskCollab1 = new TaskCollaborator(projCollab1);
		taskCollab2 = new TaskCollaborator(projCollab2);

		// set active user
		task1OnGoing.addTaskCollaboratorToTask(taskCollab1);
		task2OnGoing.addTaskCollaboratorToTask(taskCollab2);
		task3.addTaskCollaboratorToTask(taskCollab1);
		task4.addTaskCollaboratorToTask(taskCollab1);
		task5.addTaskCollaboratorToTask(taskCollab2);
		task6.addTaskCollaboratorToTask(taskCollab1);

		// Creates State Objects assigned for task.
		AssignedTestTask = new Assigned(task1OnGoing);
		AssignedTestTask2 = new Assigned(task2OnGoing);
		AssignedTestTask3 = new Assigned(task3);
		AssignedTestTask4 = new Assigned(task4);
		AssignedTestTask5 = new Assigned(task5);
		AssignedTestTask6 = new Assigned(task6);

		// Sets the tasks to "Assigned"
		task1OnGoing.setTaskState(AssignedTestTask);
		task2OnGoing.setTaskState(AssignedTestTask2);
		task3.setTaskState(AssignedTestTask3);
		task4.setTaskState(AssignedTestTask4);
		task5.setTaskState(AssignedTestTask5);
		task6.setTaskState(AssignedTestTask6);

		// Creates State Objects Ready for task.
		ReadyTestTask = new Ready(task1OnGoing);
		ReadyTestTask2 = new Ready(task2OnGoing);
		ReadyTestTask3 = new Ready(task3);
		ReadyTestTask4 = new Ready(task4);
		ReadyTestTask5 = new Ready(task5);
		ReadyTestTask6 = new Ready(task6);

		// Sets the tasks to "Ready"
		task1OnGoing.setTaskState(ReadyTestTask);
		task2OnGoing.setTaskState(ReadyTestTask2);
		task3.setTaskState(ReadyTestTask3);
		task4.setTaskState(ReadyTestTask4);
		task5.setTaskState(ReadyTestTask5);
		task6.setTaskState(ReadyTestTask6);

		// Creates State Objects OnGoing for task.
		onGoingTestTask = new OnGoing(task1OnGoing);
		onGoingTestTask2 = new OnGoing(task2OnGoing);

		// Sets the tasks to "onGoing"
		task1OnGoing.setTaskState(onGoingTestTask);
		task2OnGoing.setTaskState(onGoingTestTask2);

	}

	@After
	public void tearDown() {
		Company.clear();
		user1 = null;
		user2 = null;
		projectManager = null;
		projectManager2 = null;
		project1 = null;
		project2 = null;
		project3 = null;
		projCollab1 = null;
		projCollab2 = null;
		task1OnGoing = null;
		task2OnGoing = null;
		task3 = null;
		task4 = null;
		task5 = null;
		task6 = null;
		taskCollab1 = null;
		taskCollab2 = null;
		PlannedTestTask = null;
		PlannedTestTask2 = null;
		PlannedTestTask3 = null;
		PlannedTestTask4 = null;
		PlannedTestTask5 = null;
		PlannedTestTask6 = null;
		AssignedTestTask = null;
		AssignedTestTask2 = null;
		AssignedTestTask3 = null;
		AssignedTestTask4 = null;
		AssignedTestTask5 = null;
		AssignedTestTask6 = null;
		ReadyTestTask = null;
		ReadyTestTask2 = null;
		ReadyTestTask3 = null;
		ReadyTestTask4 = null;
		ReadyTestTask5 = null;
		ReadyTestTask6 = null;
		onGoingTestTask = null;
		onGoingTestTask2 = null;
		onGoingTestTask3 = null;
		onGoingTestTask4 = null;
		onGoingTestTask5 = null;
		onGoingTestTask6 = null;
		startDateTest = null;
		estimatedTaskStartDateTest = null;
		taskDeadlineDateTest = null;
		taskExpiredDeadlineDateTest = null;
		us365controller = null;
	}

	/**
	 * 
	 * this test asserts that a task starting as Ongoing can be marked as finish and
	 * given a finish date
	 * 
	 */
	@Test
	public void testSetTaskAsFinished() {

		assertFalse(task1OnGoing.isTaskFinished());
		assertTrue(task1OnGoing.getFinishDate() == null);
		assertEquals("OnGoing", task1OnGoing.viewTaskStateName());

		// create controller for ongoing task 1 (of project 1), and asserts Task1Ongoing
		// has been properly marked as finished
		us365controller = new US365MarkTaskAsFinishedControllerProjectManager(task1OnGoing.getTaskID(), project1);

		us365controller.setTaskAsFinished();

		assertTrue(task1OnGoing.isTaskFinished());
		assertTrue(task1OnGoing.getFinishDate() != null);
		assertEquals("Finished", task1OnGoing.viewTaskStateName());

		// create controller for ready task 3 (of project 2), then asserts task3 cannot
		// be marked as finished, since it's not ongoing
		us365controller = new US365MarkTaskAsFinishedControllerProjectManager(task3.getTaskID(), project2);

		us365controller.setTaskAsFinished();

		assertFalse(task3.isTaskFinished());

	}

}
