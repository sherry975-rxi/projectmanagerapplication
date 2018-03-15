package project.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import project.Services.ProjectService;
import project.Services.UserService;
import project.model.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class US205MarkTaskAsFinishedCollaboratorTest {

	/**
	 * This class tests the methods that are called in Controller to execute the
	 * action of Canceling an OnGoing Task
	 * 
	 * @author Group3
	 *
	 */

	UserService userContainer;
	ProjectService projectContainer;

	// TasksFiltersController tasksFiltersController;
	User user1, user2, projectManager, projectManager2;
	Project project1, project2, project3;
	ProjectCollaborator projCollab1, projCollab2;
	Task task1, task2, task3, task4, task5, task6;
	TaskCollaborator taskCollab1, taskCollab2;
	Calendar startDateTest;
	Calendar estimatedTaskStartDateTest;
	Calendar taskDeadlineDateTest;
	Calendar taskExpiredDeadlineDateTest;

	@Before
	public void setUp() {

		// create an UserContainer
		userContainer = new UserService();

		// create users
		user1 = userContainer.createUser("Joe Smith", "jsmith@gmail.com", "001", "Junior Programmer",
				"930000000", "Rua da Caparica, 19", "7894-654", "Porto", "Porto", "Portugal");
		user2 = userContainer.createUser("John Smith", "johnsmith@gmail.com", "001", "General Manager",
				"930025000", "Rua Doutor Armando", "4455-654", "Rio Tinto", "Gondomar", "Portugal");
		projectManager = userContainer.createUser("Mary MacJohn", "mmacjohn@gmail.com", "003",
				"Product Manager", "930025000", "Rua Terceira, 44", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");
		projectManager2 = userContainer.createUser("John MacMary", "jmacmary2@gmail.com", "003",
				"Product Manager2", "930025356", "Rua Segunda, 45", "4455-122", "Leça da Palmeira", "Matosinhos",
				"Portugal");

		// add users to company
		userContainer.addUserToUserRepository(user1);
		userContainer.addUserToUserRepository(user2);
		userContainer.addUserToUserRepository(projectManager);
		userContainer.addUserToUserRepository(projectManager2);

		// set user as collaborator
		user1.setUserProfile(Profile.COLLABORATOR);
		user2.setUserProfile(Profile.COLLABORATOR);
		projectManager.setUserProfile(Profile.COLLABORATOR);
		projectManager2.setUserProfile(Profile.COLLABORATOR);

		//Creates a ProjectContainer
		projectContainer = new ProjectService();

		// create project and establishes collaborator projectManager as project manager
		// of project 1
		project1 = projectContainer.createProject("Project Management software",
				"This software main goals are ....", projectManager);
		project2 = projectContainer.createProject("Project Management software",
				"This software main goals are ....", projectManager2);
		project3 = projectContainer.createProject("Project Management software",
				"This software main goals are ....", projectManager);

		// add project to company
		projectContainer.addProjectToProjectContainer(project1);
		projectContainer.addProjectToProjectContainer(project2);
		projectContainer.addProjectToProjectContainer(project3);

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
		task1 = project1.getTaskRepository().createTask("Create class User");
		task2 = project1.getTaskRepository().createTask("Create class User");
		task3 = project2.getTaskRepository().createTask("create test for method set name in class user");
		task4 = project2.getTaskRepository().createTask("Create class User");
		task5 = project3.getTaskRepository().createTask("Create class User");
		task6 = project3.getTaskRepository().createTask("create test for method set name in class user");

		// add tasks to task repository
		project1.getTaskRepository().addTaskToProject(task1);
		project1.getTaskRepository().addTaskToProject(task2);
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
		task1.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task1.setStartDate(estimatedTaskStartDateTest);
		task1.setTaskDeadline(taskDeadlineDateTest);

		task2.setEstimatedTaskStartDate(estimatedTaskStartDateTest);
		task2.setStartDate(estimatedTaskStartDateTest);
		task2.setTaskDeadline(taskDeadlineDateTest);

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
		task1.addTaskCollaboratorToTask(taskCollab1);
		task2.addTaskCollaboratorToTask(taskCollab2);
		task3.addTaskCollaboratorToTask(taskCollab1);
		task4.addTaskCollaboratorToTask(taskCollab1);
		task5.addTaskCollaboratorToTask(taskCollab2);
		task6.addTaskCollaboratorToTask(taskCollab1);

	}




	@After
	public void tearDown() {
		projectContainer = null;
		userContainer = null;
		user1 = null;
		user2 = null;
		projectManager = null;
		projectManager2 = null;
		project1 = null;
		project2 = null;
		project3 = null;
		projCollab1 = null;
		projCollab2 = null;
		task1 = null;
		task2 = null;
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
	}

	@Test
	public void testGetProjectsFromProjectCollaborator1() {
		// create controller
		US205MarkTaskAsFinishedCollaborator uS205MarkTaskAsFinishedCollaborator = new US205MarkTaskAsFinishedCollaborator();

		// create list of tasks to compare to taskContainer of project
		List<Project> allProjectsInTest = new ArrayList<>();

		// add task to the list allTasksInTest
		allProjectsInTest.add(project1);
		allProjectsInTest.add(project2);
		allProjectsInTest.add(project3);

		// compares the list of tasks created to compare with the list of tasks in the
		// task repository obtained by using the controller
		assertEquals(allProjectsInTest, uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1));
	}

	@Test
	public void testGetTasksFromProject1Collaborator1() {
		// create controller
		US205MarkTaskAsFinishedCollaborator uS205MarkTaskAsFinishedCollaborator = new US205MarkTaskAsFinishedCollaborator();
		uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1);

		// create list of tasks to compare to taskContainer of project
		List<Task> allTasksInProject1Test = new ArrayList<>();
		List<Task> allTasksInProject1 = uS205MarkTaskAsFinishedCollaborator
				.getUnfinishedTasksOfProjectFromCollaborator(1);

		// add task to the list allTasksInTest
		allTasksInProject1Test.add(task1);

		// compares the list of tasks created to compare with the list of tasks in the
		// task repository obtained by using the controller
		assertEquals(allTasksInProject1Test, allTasksInProject1);

	}

	@Test
	public void testSelectTask1FromProject1Manager1Finished() {
		// create controller
		US205MarkTaskAsFinishedCollaborator uS205MarkTaskAsFinishedCollaborator = new US205MarkTaskAsFinishedCollaborator();
		uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1);
		uS205MarkTaskAsFinishedCollaborator.getUnfinishedTasksOfProjectFromCollaborator(1);

		Task taskToBeMarked = uS205MarkTaskAsFinishedCollaborator.getTaskToBeMarkedFinished("1.1");

		assertEquals(task1, taskToBeMarked);
	}

	@Test
	public void testSetTask1FromProject1Manager1Finished() {
		// create controller
		US205MarkTaskAsFinishedCollaborator uS205MarkTaskAsFinishedCollaborator = new US205MarkTaskAsFinishedCollaborator();
		uS205MarkTaskAsFinishedCollaborator.getProjectsThatIAmCollaborator(user1);
		uS205MarkTaskAsFinishedCollaborator.getUnfinishedTasksOfProjectFromCollaborator(1);
		uS205MarkTaskAsFinishedCollaborator.getTaskToBeMarkedFinished("1.1");
		uS205MarkTaskAsFinishedCollaborator.markTaskAsFinished();

		assertEquals("Finished", task1.viewTaskStateName());
	}
}
