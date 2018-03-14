package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.UserContainerService;
import project.model.*;

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
	UserContainerService myUsers;
	ProjectService myProjects;

	User user1, user2, projectManager, projectManager2;
	Project project1, project2, project3;
	ProjectCollaborator projCollab1, projCollab2;
	Task task1OnGoing, task2OnGoing, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2;
	Calendar startDateTest;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar taskExpiredDeadlineDateTest;
	US365MarkTaskAsFinishedControllerProjectManager us365controller;

	@Before
	public void setUp() {

		// create user and project container
		myUsers= new UserContainerService();
		myProjects = new ProjectService();

		// create users
		user1 = myUsers.createUser("Joe Smith", "jsmith@gmail.com", "001", "Junior Programmer",
				"930000000", "Rua da Caparica, 19", "7894-654", "Porto", "Porto", "Portugal");
		user2 = myUsers.createUser("John Smith", "johnsmith@gmail.com", "001", "General Manager",
				"930025000", "Rua Doutor Armando", "4455-654", "Rio Tinto", "Gondomar", "Portugal");
		projectManager = myUsers.createUser("Mary MacJohn", "mmacjohn@gmail.com", "003",
				"Product Manager", "930025000", "Rua Terceira, 44", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");
		projectManager2 = myUsers.createUser("John MacMary", "jmacmary2@gmail.com", "003",
				"Product Manager2", "930025356", "Rua Segunda, 45", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");

		// add users to company
		myUsers.addUserToUserRepository(user1);
		myUsers.addUserToUserRepository(user2);
		myUsers.addUserToUserRepository(projectManager);
		myUsers.addUserToUserRepository(projectManager2);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);
		projectManager2.setUserProfile(Profile.COLLABORATOR);

		// create project and establishes collaborator projectManager as project manager
		// of project 1
		project1 = myProjects.createProject("Project Management software",
				"This software main goals are ....", projectManager);
		project2 = myProjects.createProject("Project Management software",
				"This software main goals are ....", projectManager2);
		project3 = myProjects.createProject("Project Management software",
				"This software main goals are ....", projectManager);

		// add project to company
		myProjects.addProjectToProjectContainer(project1);
		myProjects.addProjectToProjectContainer(project2);
		myProjects.addProjectToProjectContainer(project3);

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
		project1.getTaskRepository().addTaskToProject(task1OnGoing);
		project1.getTaskRepository().addTaskToProject(task2OnGoing);
		project2.getTaskRepository().addTaskToProject(task3);
		project2.getTaskRepository().addTaskToProject(task4);
		project3.getTaskRepository().addTaskToProject(task5);
		project3.getTaskRepository().addTaskToProject(task6);

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


	}

	@After
	public void tearDown() {
		myUsers=null;
		myProjects= null;
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
